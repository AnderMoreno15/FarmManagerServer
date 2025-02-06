/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.animalGroup;

import entities.AnimalGroup;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;
import java.util.List;
import javax.ejb.EJB;
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
 * RESTful service for managing animal group entities in the farm management system.
 * <p>
 * This service provides endpoints for creating, updating, retrieving, and deleting animal groups.
 * It interacts with the underlying business logic via the {@link IAnimalGroupEjb} interface 
 * and handles CRUD operations for animal group entities.
 * </p>
 * 
 * @author Ander
 */
@Path("animalgroup")
public class AnimalGroupFacadeREST {

    @EJB
    private IAnimalGroupEjb animalGroupEjb;

    /**
     * Creates a new instance of AnimalGroupFacadeRest.
     */
    public AnimalGroupFacadeREST() {
    }

    /**
     * Creates a new animal group in the system.
     * 
     * @param animalGroup The {@link AnimalGroup} object containing the data for the new group.
     * @throws InternalServerErrorException If an error occurs during the creation process.
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void createAnimalGroup(AnimalGroup animalGroup) {
        try {
            animalGroupEjb.setAnimalGroup(animalGroup);
        } catch (CreateException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    /**
     * Updates an existing animal group in the system.
     * 
     * @param animalGroup The {@link AnimalGroup} object containing the updated data.
     * @throws InternalServerErrorException If an error occurs during the update process.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void updateAnimalGroup(AnimalGroup animalGroup) {
        try {
            animalGroupEjb.updateAnimalGroup(animalGroup);
        } catch (UpdateException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    /**
     * Deletes an animal group from the system.
     * 
     * @param animalGroup The {@link AnimalGroup} object to be deleted.
     * @throws InternalServerErrorException If an error occurs during the deletion process.
     */
    @DELETE
    @Consumes(MediaType.APPLICATION_XML)
    public void deleteAnimalGroup(AnimalGroup animalGroup) {
        try {
            animalGroupEjb.deleteAnimalGroup(animalGroup);
        } catch (DeleteException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    /**
     * Deletes an animal group by its ID.
     * 
     * @param id The ID of the animal group to be deleted.
     * @throws InternalServerErrorException If an error occurs during the deletion process.
     */
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

    /**
     * Retrieves all animal groups in the system.
     * 
     * @return A list of {@link AnimalGroup} objects representing all animal groups.
     * @throws InternalServerErrorException If an error occurs during the retrieval process.
     */
    @GET
    @Consumes(MediaType.APPLICATION_XML)
    public List<AnimalGroup> getAnimalGroups() {
        try {
            return (List<AnimalGroup>) animalGroupEjb.getAnimalGroups();
        } catch (ReadException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    /**
     * Retrieves all animal groups managed by a specific manager.
     * 
     * @param managerId The ID of the manager whose animal groups need to be retrieved.
     * @return A list of {@link AnimalGroup} objects belonging to the specified manager.
     * @throws InternalServerErrorException If an error occurs during the retrieval process.
     */
    @GET
    @Path("search/{managerId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<AnimalGroup> getAnimalGroupsByManager(@PathParam("managerId") Long managerId) {
        try {
            return (List<AnimalGroup>) animalGroupEjb.getAnimalGroupsByManager(managerId);
        } catch (ReadException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    /**
     * Retrieves an animal group by its name and the manager ID.
     * 
     * @param groupName The name of the animal group to be retrieved.
     * @param managerId The ID of the manager who manages the animal group.
     * @return A list of {@link AnimalGroup} objects matching the name and manager ID.
     * @throws InternalServerErrorException If an error occurs during the retrieval process.
     */
    @GET
    @Path("search/{name}/{managerId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<AnimalGroup> getAnimalGroupByName(@PathParam("name") String groupName, @PathParam("managerId") Long managerId) {
        try {
            return (List<AnimalGroup>) animalGroupEjb.getAnimalGroupByName(groupName, managerId);
        } catch (ReadException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }
}
