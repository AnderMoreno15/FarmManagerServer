/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.manager;

import config.ConfigReader;
import encryption.PasswordService;
import encryption.SymmetricDecryptor;
import encryption.UserAuthService;
import static encryption.UserAuthService.logger;
import entities.Manager;
import exceptions.CreateException;
import exceptions.ReadException;
import exceptions.UpdateException;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import mailing.MailingService;

/**
 *
 * @author Ander
 * @author Aitziber
 */
@Stateless
public class ManagerEjb implements IManagerEjb {
    
    Logger logger = Logger.getLogger(ManagerEjb.class.getName());

    @PersistenceContext(unitName = "FarmManagerPU")
    private EntityManager em;

    @Override
    public void setManager(Manager manager) throws CreateException {
        try {
            em.persist(manager);
        } catch (Exception e) {
            throw new CreateException(e.getMessage());
        }
    }

    @Override
    public List<Manager> getManagers() throws ReadException {
        try {
            return em.createNamedQuery("getManagers", Manager.class)
                    .getResultList();
        } catch (Exception e) {
            throw new ReadException("Error retrieving managers. Details: " + e.getMessage());
        }
    }

    @Override
    public List<Manager> getManager(String email, String password) throws ReadException {
        try {
            return (List<Manager>) em.createNamedQuery("getManager", Manager.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
    }

    @Override
    public Manager getManagerByEmail(String email) throws ReadException {
        try {
            List<Manager> result = em.createNamedQuery("getManagerByEmail", Manager.class)
            .setParameter("email", email)
            .getResultList();
        
            if (result.isEmpty()) {
                return null;
            }

            return result.get(0);
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
    }

    @Override
    public void updateManager(Manager manager) throws UpdateException {
        try {
            if (!em.contains(manager)) {
                em.merge(manager);
            }
            em.flush();
        } catch (Exception e) {
            throw new UpdateException(e.getMessage());
        }
    }

    @Override
    public void resetPassword(Manager manager) throws UpdateException {
        try {
           String newPassword = PasswordService.getNewPassword();
           String encryptedPassword = UserAuthService.hashPassword(newPassword);
           manager.setPassword(encryptedPassword);
           if (!em.contains(manager)) {
               em.merge(manager);
           }
           em.flush();
           
           MailingService mailingService = new MailingService();
           String body = "<html><body>"
                   + "<p>Hello " + manager.getName() + ",</p>"
                   + "<p>Your new password is: <b>" + newPassword + "</b></p>"
                   + "<p>Please make sure to store this information securely.</p>"
                   + "</body></html>";
           String plainEmail = SymmetricDecryptor.decrypt(manager.getEmail());
           boolean emailSent = mailingService.sendEmail(plainEmail, "Farm App - Password reset", body);

           if (emailSent) {
               logger.info("Password restore email successfully sent to: " + plainEmail);
           } else {
               logger.warning("Failed to send email to: " + plainEmail);
           }

       } catch (Exception e) {
           logger.severe("Error resetting password: " + e.getMessage());
           throw new UpdateException("Error resetting password. Details: " + e.getMessage());
       }
    }

    public Manager signIn(String email, String password) throws ReadException {
        try {
            Manager manager = getManagerByEmail(email);
            if (manager == null) {
                logger.warning("Manager not found.");
                return null;
            }
            String inputPlainPassword = SymmetricDecryptor.decrypt(password);

            if (UserAuthService.verifyPassword(inputPlainPassword, manager.getPassword())) {
                logger.info("Sign-in successful for: " + email);
                return manager;
            } else {
                logger.warning("Incorrect password for: " + email);
                return null;
            }
        } catch (Exception e) {
            logger.severe("Error during sign-in: " + e.getMessage());
            throw new ReadException("Error during sign-in. Details: " + e.getMessage());
        }
    }
    
    public void signUp(Manager manager) throws CreateException {
        try {
            String encryptedFromClientPassword = manager.getPassword();
            
            CompletableFuture<String> decryptedPasswordFuture = CompletableFuture.supplyAsync(() -> {
                try {
                    return SymmetricDecryptor.decrypt(encryptedFromClientPassword);
                } catch (Exception e) {
                    logger.severe("Error al desencriptar la contraseña: " + e.getMessage());
                    return null;
                }
            });

            String plainPassword = decryptedPasswordFuture.get();
            
            if (plainPassword == null || plainPassword.isEmpty()) {
                throw new CreateException("Error: la contraseña desencriptada es nula o vacía.");
            }

            String encryptedPassword = UserAuthService.hashPassword(plainPassword);
            manager.setPassword(encryptedPassword);

            em.persist(manager);
            em.flush();

            logger.info("Manager registrado con éxito.");

        } catch (InterruptedException | ExecutionException e) {
            logger.severe("Error en el hilo de desencriptación: " + e.getMessage());
            throw new CreateException("Error en el proceso de sign-up. Details: " + e.getMessage());
        } catch (Exception e) {
            logger.severe("Error durante el sign-up: " + e.getMessage());
            throw new CreateException("Error durante el sign-up. Details: " + e.getMessage());
        }
    }
}
