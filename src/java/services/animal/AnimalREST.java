/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.animal;

import entities.Animal;
import entities.AnimalGroup;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.BadRequestException;
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
    @Path("delete/{id}")
    public void deleteAnimalById(@PathParam("id") Long id) {
        try {
            animalFacade.deleteAnimalById(id);
        } catch (DeleteException ex) {
            throw new InternalServerErrorException(ex.getMessage()); 
        }
    }
    
    
    @GET
    @Path("all/{managerId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Animal> getAllAnimals(@PathParam("managerId") Long managerId) {
        try{
           return animalFacade.getAllAnimals(managerId);
        } catch (ReadException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }
    
    @GET
    @Path("name/{name}/{managerId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Animal getAnimalByName(@PathParam("name") String name, @PathParam("managerId") Long managerId) {
        try{
           return (Animal) animalFacade.getAnimalByName(name, managerId);
        } catch (ReadException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }
    
    @GET
    @Path("group/{animalGroupName}/{managerId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Animal> getAnimalsByAnimalGroup(@PathParam("animalGroupName") String animalGroupName, @PathParam("managerId") Long managerId) {
        try{
           return animalFacade.getAnimalsByAnimalGroup(animalGroupName, managerId);
        } catch (ReadException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }
    
    @GET
    @Path("subespecies/{subespecies}/{managerId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Animal> getAnimalsBySubespecies(@PathParam("subespecies") String subespecies, @PathParam("managerId") Long managerId) {
        try{
           return animalFacade.getAnimalsBySubespecies(subespecies, managerId);
        } catch (ReadException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }
    
    @GET
    @Path("between/{dateFrom}/{dateTo}/{managerId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Animal> getAnimalsByBirthdate(@PathParam("dateFrom") String dateFromStr, @PathParam("dateTo") String dateToStr, @PathParam("managerId") Long managerId) {
        try {
            Date dateFrom = parseDate(dateFromStr);
            Date dateTo = parseDate(dateToStr);
            return animalFacade.getAnimalsByBirthdate(dateFrom, dateTo, managerId);
        } catch (ReadException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    @GET
    @Path("from/{dateFrom}/{managerId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Animal> getAnimalsByBirthdateFrom(@PathParam("dateFrom") String dateFromStr, @PathParam("managerId") Long managerId) {
        try {
            Date dateFrom = parseDate(dateFromStr);
            return animalFacade.getAnimalsByBirthdateFrom(dateFrom, managerId);
        } catch (ReadException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    @GET
    @Path("to/{dateTo}/{managerId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Animal> getAnimalsByBirthdateTo(@PathParam("dateTo") String dateToStr, @PathParam("managerId") Long managerId) {
        try {
            Date dateTo = parseDate(dateToStr);
            return animalFacade.getAnimalsByBirthdateTo(dateTo, managerId);
        } catch (ReadException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }
    
    private Date parseDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(dateStr);
        } catch (ParseException ex) {
            throw new BadRequestException("Invalid date format. Expected yyyy-MM-dd.");
        }
    }
}