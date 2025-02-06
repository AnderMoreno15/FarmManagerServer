/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import ejb.IManagerEjb;
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

/**
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
    
    @GET
    @Consumes(MediaType.APPLICATION_XML)
    public List<Manager> getManagers() {
        try {
           return (List<Manager>) managerEjb.getManagers();
        } catch (ReadException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void updateManager(Manager manager) {
        try {
            managerEjb.updateManager(manager);
        } catch (UpdateException ex) {
            throw new InternalServerErrorException(ex.getMessage());        
        }
    }
    
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
    
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void createManager(Manager manager) {
        try {
            managerEjb.setManager(manager);
        } catch (CreateException ex) {
            throw new InternalServerErrorException(ex.getMessage());        
        }
    }
    
    @GET
    @Path("search/{email}/{password}")
    @Produces(MediaType.APPLICATION_XML)
    public List<Manager> getManager(@PathParam("email") String email, @PathParam("password") String password) {
        try{
           return (List<Manager>) managerEjb.getManager(email, password);
        } catch (ReadException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }
      
    @GET
    @Path("search/{email}")
    @Produces(MediaType.APPLICATION_XML)
    public Manager getManagerByEmail(@PathParam("email") String email) {
        try{
           return (Manager) managerEjb.getManagerByEmail(email);
        } catch (ReadException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }  
    
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
