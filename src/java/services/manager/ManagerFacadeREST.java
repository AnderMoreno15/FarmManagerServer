/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.manager;

import entities.Manager;
import exceptions.CreateException;
import exceptions.ReadException;
import exceptions.UpdateException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/*
 * RESTful service for managing manager entities in the farm management system.
 * <p>
 * This service provides endpoints for creating, updating, and retrieving manager data.
 * It interacts with the underlying business logic via the {@link IManagerEjb} interface 
 * and handles CRUD operations for manager entities.
 * </p>
 * 
 * @author Ander
 * @author Aitziber
 */
@Stateless
@Path("manager")
public class ManagerFacadeREST {

    @EJB
    private IManagerEjb managerEjb;

    @PersistenceContext(unitName = "FarmManagerPU")
    private EntityManager em;

    public ManagerFacadeREST() {
    }

    /**
     * Retrieves all managers from the system.
     * 
     * @return A list of {@link Manager} objects representing all managers in the system.
     * @throws InternalServerErrorException If an error occurs during the retrieval process.
     */
    @GET
    @Consumes(MediaType.APPLICATION_XML)
    public List<Manager> getManagers() {
        try {
            return (List<Manager>) managerEjb.getManagers();
        } catch (ReadException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    /**
     * Updates an existing manager's information.
     * 
     * @param manager The {@link Manager} object containing the updated data.
     * @throws InternalServerErrorException If an error occurs during the update process.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void updateManager(Manager manager) {
        try {
            managerEjb.updateManager(manager);
        } catch (UpdateException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    /**
     * Resets the password for a manager.
     * 
     * @param manager The {@link Manager} object whose password needs to be reset.
     * @throws InternalServerErrorException If an error occurs during the password reset process.
     */
    @PUT
    @Path("reset")
    @Consumes(MediaType.APPLICATION_XML)
    public void resetPassword(Manager manager) {
        try {
            managerEjb.resetPassword(manager);
        } catch (UpdateException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    /**
     * Creates a new manager in the system.
     * 
     * @param manager The {@link Manager} object containing the manager's data to be created.
     * @throws InternalServerErrorException If an error occurs during the creation process.
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void createManager(Manager manager) {
        try {
            managerEjb.setManager(manager);
        } catch (CreateException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    /**
     * Searches for a manager using their email and password.
     * 
     * @param email    The email of the manager.
     * @param password The password of the manager.
     * @return A list of {@link Manager} objects matching the email and password.
     * @throws InternalServerErrorException If an error occurs during the search process.
     */
    @GET
    @Path("search/{email}/{password}")
    @Produces(MediaType.APPLICATION_XML)
    public List<Manager> getManager(@PathParam("email") String email, @PathParam("password") String password) {
        try {
            return (List<Manager>) managerEjb.getManager(email, password);
        } catch (ReadException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    /**
     * Retrieves a manager by their email.
     * 
     * @param email The email of the manager to be retrieved.
     * @return A {@link Manager} object matching the given email.
     * @throws InternalServerErrorException If an error occurs during the retrieval process.
     */
    @GET
    @Path("search/{email}")
    @Produces(MediaType.APPLICATION_XML)
    public Manager getManagerByEmail(@PathParam("email") String email) {
        try {
            return (Manager) managerEjb.getManagerByEmail(email);
        } catch (ReadException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    /**
     * Signs in a manager using their email and password.
     * 
     * @param manager The {@link Manager} object containing the email and password to sign in.
     * @return The {@link Manager} object corresponding to the signed-in manager.
     * @throws InternalServerErrorException If an error occurs during the sign-in process.
     */
    @POST
    @Path("signin")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Manager signIn(Manager manager) {
        try {
            return managerEjb.signIn(manager.getEmail(), manager.getPassword());
        } catch (ReadException ex) {
            throw new InternalServerErrorException("Sign-in failed: " + ex.getMessage());
        }
    }

    /**
     * Signs up a new manager in the system.
     * 
     * @param manager The {@link Manager} object containing the data to be used for sign-up.
     * @throws InternalServerErrorException If an error occurs during the sign-up process.
     */
    @POST
    @Path("signup")
    @Consumes(MediaType.APPLICATION_XML)
    public void signUp(Manager manager) {
        try {
            managerEjb.signUp(manager);
        } catch (CreateException ex) {
            throw new InternalServerErrorException("Sign-up failed: " + ex.getMessage());
        }
    }

}
