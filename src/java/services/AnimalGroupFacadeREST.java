/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import ejb.IAnimalGroupEjb;
import entities.AnimalGroup;
import entities.ManagerEntity;
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
@Path("animalgroupentity")
public class AnimalGroupFacadeREST {

    @EJB
    private IAnimalGroupEjb animalGroupEjb;

    /**
     * Creates a new instance of AnimalGroupFacadeRest
     */
    public AnimalGroupFacadeREST() {
    }

//    Example
//    <animalGroupEntity>
//    <name>Lions</name>
//    <area>Savannah</area>
//    <description>Group of lions roaming freely</description>
//    <creationDate>2025-01-10T12:00:00</creationDate>
//    </animalGroupEntity>

    
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void createAnimalGroup(AnimalGroup animalGroup) {
        try {
            animalGroupEjb.createAnimalGroup(animalGroup);
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

    // gets
    
    @GET
    @Path("name/{name}")
    @Produces(MediaType.APPLICATION_XML)
    public AnimalGroup getAnimalGroupsByName(@PathParam("name") String name) {
        try{
           return (AnimalGroup) animalGroupEjb.getAnimalGroupsByName(name);
        } catch (ReadException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }
    
//    @GET
//    @Path("manager/{manager}")
//    @Produces(MediaType.APPLICATION_XML)
//    public AnimalGroup getAnimalGroupsByManager(@PathParam("manager") ManagerEntity manager) {
//        try{
//           return (AnimalGroup) animalGroupEjb.getAnimalGroupsByManager(manager);
//        } catch (ReadException ex) {
//            throw new InternalServerErrorException(ex.getMessage());
//        }
//    }
    
//    @GET
//    @Path("{id}")
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public AnimalGroup find(@PathParam("id") Long id) {
//        return super.find(id);
//    }
//
//    @GET
//    @Override
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public List<AnimalGroupEntity> findAll() {
//        return super.findAll();
//    }
//
//    @GET
//    @Path("{from}/{to}")
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public List<AnimalGroupEntity> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
//        return super.findRange(new int[]{from, to});
//    }
//
//    @GET
//    @Path("count")
//    @Produces(MediaType.TEXT_PLAIN)
//    public String countREST() {
//        return String.valueOf(super.count());
//    }
//
//    @Override
//    protected EntityManager getEntityManager() {
//        return em;
//    }
    
}
