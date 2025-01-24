/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import ejb.IAnimalGroupEjb;
import entities.AnimalGroup;
import entities.Manager;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
 */
@Path("animalgroup")
public class AnimalGroupFacadeREST {

    @EJB
    private IAnimalGroupEjb animalGroupEjb;

    /**
     * Creates a new instance of AnimalGroupFacadeRest
     */
    public AnimalGroupFacadeREST() {
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void createAnimalGroup(AnimalGroup animalGroup) {
        try {
            animalGroupEjb.setAnimalGroup(animalGroup);
        } catch (CreateException ex) {
            throw new InternalServerErrorException(ex.getMessage());        
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void updateAnimalGroup(AnimalGroup animalGroup) {
        try {
            animalGroupEjb.updateAnimalGroup(animalGroup);
        } catch (UpdateException ex) {
            throw new InternalServerErrorException(ex.getMessage());        
        }
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_XML)
    public void deleteAnimalGroup(AnimalGroup animalGroup) {
        try {
            animalGroupEjb.deleteAnimalGroup(animalGroup);
        } catch (DeleteException ex) {
            throw new InternalServerErrorException(ex.getMessage());        
        }
    }
    
    @DELETE
    @Path("delete/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public void deleteAnimalGroupById(@PathParam("id") Long id) {
        try {
            animalGroupEjb.deleteAnimalGroupById(id);
        } catch (DeleteException ex) {
            throw new InternalServerErrorException(ex.getMessage()); 
        }
    }
    
    @GET
    @Consumes(MediaType.APPLICATION_XML)
    public List<AnimalGroup> getAnimalGroups() {
        try {
           return (List<AnimalGroup>) animalGroupEjb.getAnimalGroups();
        } catch (ReadException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }
    
    @GET
    @Path("search/{managerId}")
    @Produces(MediaType.APPLICATION_XML)
    public List<AnimalGroup> getAnimalGroupsByManager(@PathParam("managerId") Long managerId) {
        try{
           return (List<AnimalGroup>) animalGroupEjb.getAnimalGroupsByManager(managerId);
        } catch (ReadException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }
    
    @GET
    @Path("search/{name}/{managerId}")
    @Produces(MediaType.APPLICATION_XML)
    public List<AnimalGroup> getAnimalGroupByName(@PathParam("name") String groupName, @PathParam("managerId") Long managerId) {
        try{
           return (List<AnimalGroup>) animalGroupEjb.getAnimalGroupByName(groupName, managerId);
        } catch (ReadException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }
    
}
