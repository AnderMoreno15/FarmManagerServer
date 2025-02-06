/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.Manager;
import exceptions.CreateException;
import exceptions.ReadException;
import exceptions.UpdateException;
import java.util.List;
import javax.ejb.Local;

/*
 * Interface for managing Manager entities.
 * <p>
 * This interface defines the methods that must be implemented for managing 
 * {@link Manager} entities in the system, including creating, updating, deleting, retrieving, 
 * and authenticating managers. It also includes functionality for resetting a manager's password 
 * and handling sign-in/sign-up processes.
 * </p>
 * 
 * @author Ander
 * @author Aitziber
 */
@Local
public interface IManagerEjb {

    /**
     * Creates and persists a new manager.
     * <p>
     * This method is used to create a new {@link Manager} entity in the system. 
     * It throws a {@link CreateException} if an error occurs during the creation process.
     * </p>
     * @param manager The manager to be created.
     * @throws CreateException If an error occurs during the creation.
     */
    public void setManager(Manager manager) throws CreateException;
    
    /**
     * Retrieves all managers from the system.
     * <p>
     * This method retrieves a list of all {@link Manager} entities stored in the system.
     * It throws a {@link ReadException} if there is an error during the retrieval process.
     * </p>
     * @return A list of all managers.
     * @throws ReadException If an error occurs during the retrieval.
     */
    public List<Manager> getManagers() throws ReadException;

    /**
     * Retrieves a manager by email and password.
     * <p>
     * This method retrieves a list of {@link Manager} entities matching the provided 
     * email and password. It throws a {@link ReadException} if there is an error during the process.
     * </p>
     * @param email The manager's email address.
     * @param password The manager's password.
     * @return A list of matching managers.
     * @throws ReadException If an error occurs during the retrieval.
     */
    public List<Manager> getManager(String email, String password) throws ReadException;
    
    /**
     * Retrieves a manager by their email address.
     * <p>
     * This method retrieves a {@link Manager} entity by the provided email address.
     * It throws a {@link ReadException} if there is an error during the retrieval process.
     * </p>
     * @param email The manager's email address.
     * @return The manager with the given email, or null if not found.
     * @throws ReadException If an error occurs during the retrieval.
     */
    public Manager getManagerByEmail(String email) throws ReadException;
    
    /**
     * Updates an existing manager's information.
     * <p>
     * This method updates the information of an existing {@link Manager} entity in the system. 
     * It throws an {@link UpdateException} if an error occurs during the update process.
     * </p>
     * @param manager The manager whose information is to be updated.
     * @throws UpdateException If an error occurs during the update.
     */
    public void updateManager(Manager manager) throws UpdateException;
    
    /**
     * Resets a manager's password.
     * <p>
     * This method generates a new password for the manager and updates their record in the system. 
     * It throws an {@link UpdateException} if there is an error during the password reset.
     * </p>
     * @param manager The manager whose password is to be reset.
     * @throws UpdateException If an error occurs during the reset process.
     */
    public void resetPassword(Manager manager) throws UpdateException;
    
    /**
     * Signs in a manager using their email and password.
     * <p>
     * This method checks the provided email and password against the system. 
     * If valid, it returns the corresponding {@link Manager} entity.
     * It throws a {@link ReadException} if there is an error during the sign-in process.
     * </p>
     * @param email The manager's email address.
     * @param password The manager's password.
     * @return The signed-in manager, or null if authentication fails.
     * @throws ReadException If an error occurs during sign-in.
     */
    public Manager signIn(String email, String password) throws ReadException;
    
    /**
     * Registers a new manager in the system.
     * <p>
     * This method creates and persists a new {@link Manager} entity in the system. 
     * It throws a {@link CreateException} if a manager with the same email already exists or if there is an error during registration.
     * </p>
     * @param manager The manager to be registered.
     * @throws CreateException If an error occurs during the registration process.
     */
    public void signUp(Manager manager) throws CreateException;
}
