/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import ejb.IUserEjb;
import entities.UserEntity;
import exceptions.CreateException;
import exceptions.ReadException;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Ander
 */
@Path("userentity")
public class UserEntityFacadeREST {

    @EJB
    private IUserEjb userEjb;

    /**
     * Creates a new instance of UserEntityFacadeREST
     */
    public UserEntityFacadeREST() {
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void createAnimalGroup(UserEntity user) {
        try {
            userEjb.setUser(user);
        } catch (CreateException ex) {
            throw new InternalServerErrorException(ex.getMessage());        
        }
    }
    
    @GET
    @Path("search/{email}/{password}")
    @Produces(MediaType.APPLICATION_XML)
    public UserEntity getUser(@PathParam("email") String email, @PathParam("password") String password) {
        try{
           return (UserEntity) userEjb.getUser(email, password);
        } catch (ReadException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }
    
}
