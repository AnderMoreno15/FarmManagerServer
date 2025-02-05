/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import encryption.PasswordService;
import encryption.UserAuthService;
import entities.Manager;
import exceptions.CreateException;
import exceptions.ReadException;
import exceptions.UpdateException;
import java.util.List;
import java.util.logging.Logger;
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

           boolean emailSent = mailingService.sendEmail(manager.getEmail(), "Farm App - Password reset", body);

           if (emailSent) {
               logger.info("Password restore email successfully sent to: " + manager.getEmail());
           } else {
               logger.warning("Failed to send email to: " + manager.getEmail());
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
                logger.warning("Manager with email " + email + " not found.");
                return null;
            }

            if (UserAuthService.verifyPassword(password, manager.getPassword())) {
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
    
    @Override
    public void signUp(Manager manager) throws CreateException {
        try {

            Manager existingManager = getManagerByEmail(manager.getEmail());
            if (existingManager != null) {
                logger.warning("Manager with email " + manager.getEmail() + " already exists.");
                throw new CreateException("Manager with this email already exists.");
            }

            String encryptedPassword = UserAuthService.hashPassword(manager.getPassword());

            manager.setPassword(encryptedPassword);

            em.persist(manager);
            em.flush();


        } catch (Exception e) {
            logger.severe("Error during sign-up: " + e.getMessage());
            throw new CreateException("Error during sign-up. Details: " + e.getMessage());
        }
    }
   
}
