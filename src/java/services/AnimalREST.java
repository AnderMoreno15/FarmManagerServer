/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Animal;
import entities.AnimalGroup;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;
import java.util.Date;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * RESTful service for Animals.
 *
 * @author Aitziber
 */
@Path("animal")
public class AnimalREST {
    
    @EJB
    private IAnimalFacade animalFacade;
    
    /**
     * Creates a new instance of AnimalREST
     */
    public AnimalREST() {
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void createAnimal(Animal animal) {
        try {
            animalFacade.createAnimal(animal);
        } catch (CreateException ex) {
            throw new InternalServerErrorException(ex.getMessage());        
        }
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void updateAnimal(Animal animal) {
        try {
            animalFacade.updateAnimal(animal);
        } catch (UpdateException ex) {
            throw new InternalServerErrorException(ex.getMessage());        
        }
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_XML)
    public void deleteAnimal(Animal animal) {
        try {
            animalFacade.deleteAnimal(animal);
        } catch (DeleteException ex) {
            throw new InternalServerErrorException(ex.getMessage());        
        }
    }
    
//    @GET
//    @Path("all/{clientId}")
//    @Produces(MediaType.APPLICATION_XML)
//    public Animal getAllAnimals(@PathParam("clientId") Long clientId) {
//        try{
//           return (Animal) animalFacade.getAllAnimals(clientId);
//        } catch (ReadException ex) {
//            throw new InternalServerErrorException(ex.getMessage());
//        }
//    }
    
    @GET
    @Path("name/{name}")
    @Produces(MediaType.APPLICATION_XML)
    public Animal getAnimalByName(@PathParam("name") String name) {
        try{
           return (Animal) animalFacade.getAnimalByName(name);
        } catch (ReadException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }
    
    @GET
    @Path("group/{animalGroup}")
    @Produces(MediaType.APPLICATION_XML)
    public Animal getAnimalsByAnimalGroup(@PathParam("animalGroup") AnimalGroup animalGroup) {
        try{
           return (Animal) animalFacade.getAnimalsByAnimalGroup(animalGroup);
        } catch (ReadException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }
    
    @GET
    @Path("subespecies/{subespecies}")
    @Produces(MediaType.APPLICATION_XML)
    public Animal getAnimalsBySubespecies(@PathParam("subespecies") String subespecies) {
        try{
           return (Animal) animalFacade.getAnimalsBySubespecies(subespecies);
        } catch (ReadException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }
    
    @GET
    @Path("between/{dateFrom}/{dateTo}")
    @Produces(MediaType.APPLICATION_XML)
    public Animal getAnimalsByBirthdate(@PathParam("dateFrom") Date dateFrom, @PathParam("dateTo") Date dateTo) {
        try{
           return (Animal) animalFacade.getAnimalsByBirthdate(dateFrom, dateTo);
        } catch (ReadException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }
    
    @GET
    @Path("from/{dateFrom}")
    @Produces(MediaType.APPLICATION_XML)
    public Animal getAnimalsByBirthdateFrom(@PathParam("dateFrom") Date dateFrom) {
        try{
           return (Animal) animalFacade.getAnimalsByBirthdateFrom(dateFrom);
        } catch (ReadException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }
    
    @GET
    @Path("to/{dateTo}")
    @Produces(MediaType.APPLICATION_XML)
    public Animal getAnimalsByBirthdateTo(@PathParam("dateTo") Date dateTo) {
        try{
           return (Animal) animalFacade.getAnimalsByBirthdateTo(dateTo);
        } catch (ReadException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }
}