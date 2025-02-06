/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.manager;

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

/*
 * EJB class for managing Manager entities.
 * <p>
 * This stateless session bean provides methods for creating, updating, deleting, retrieving, 
 * and authenticating managers in the system. It also includes functionality for resetting a manager's password 
 * and sending an email with the new password.
 * </p>
 * 
 * @author Ander
 * @author Aitziber
 */
@Stateless
public class ManagerEjb implements IManagerEjb {

    Logger logger = Logger.getLogger(ManagerEjb.class.getName());

    @PersistenceContext(unitName = "FarmManagerPU")
    private EntityManager em;

    /**
     * Creates and persists a new manager.
     * <p>
     * This method is used to create a new {@link Manager} entity in the database. 
     * It throws a {@link CreateException} if an error occurs during the creation process.
     * </p>
     * @param manager The manager to be created.
     * @throws CreateException If an error occurs during the creation.
     */
    @Override
    public void setManager(Manager manager) throws CreateException {
        try {
            em.persist(manager);
        } catch (Exception e) {
            throw new CreateException(e.getMessage());
        }
    }

    /**
     * Retrieves all managers from the system.
     * <p>
     * This method retrieves a list of all {@link Manager} entities from the database.
     * It throws a {@link ReadException} if there is an error during the retrieval process.
     * </p>
     * @return A list of all managers.
     * @throws ReadException If an error occurs during the retrieval.
     */
    @Override
    public List<Manager> getManagers() throws ReadException {
        try {
            return em.createNamedQuery("getManagers", Manager.class)
                    .getResultList();
        } catch (Exception e) {
            throw new ReadException("Error retrieving managers. Details: " + e.getMessage());
        }
    }

    /**
     * Retrieves a manager by email and password.
     * <p>
     * This method retrieves a {@link Manager} entity that matches the given email and password.
     * It throws a {@link ReadException} if there is an error during the retrieval process.
     * </p>
     * @param email The manager's email.
     * @param password The manager's password.
     * @return A list containing the matching manager(s).
     * @throws ReadException If an error occurs during the retrieval.
     */
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

    /**
     * Retrieves a manager by their email address.
     * <p>
     * This method retrieves a {@link Manager} entity using the provided email.
     * If no manager is found, it returns null. Throws a {@link ReadException} if there is an error.
     * </p>
     * @param email The manager's email address.
     * @return The manager corresponding to the provided email, or null if not found.
     * @throws ReadException If an error occurs during the retrieval.
     */
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

    /**
     * Updates an existing manager in the system.
     * <p>
     * This method updates an existing {@link Manager} entity in the database.
     * It throws an {@link UpdateException} if there is an error during the update process.
     * </p>
     * @param manager The manager to be updated.
     * @throws UpdateException If an error occurs during the update.
     */
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
    
    /**
     * Resets the manager's password.
     * <p>
     * This method generates a new password for the manager, updates the password in the system, 
     * and sends an email with the new password.
     * It throws an {@link UpdateException} if there is an error during the process.
     * </p>
     * @param manager The manager whose password is to be reset.
     * @throws UpdateException If an error occurs during the password reset.
     */
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

    /**
     * Signs in a manager by verifying their email and password.
     * <p>
     * This method checks if the manager's email exists in the system and verifies the password.
     * It returns the manager if the credentials are correct, otherwise returns null.
     * It throws a {@link ReadException} if there is an error during the sign-in process.
     * </p>
     * @param email The manager's email.
     * @param password The manager's password.
     * @return The manager if the credentials are correct, otherwise null.
     * @throws ReadException If an error occurs during sign-in.
     */
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

    /**
     * Registers a new manager in the system.
     * <p>
     * This method adds a new {@link Manager} entity to the database after checking if the email already exists.
     * It throws a {@link CreateException} if the manager already exists or if an error occurs during the registration process.
     * </p>
     * @param manager The manager to be registered.
     * @throws CreateException If an error occurs during the registration.
     */
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
