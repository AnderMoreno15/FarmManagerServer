/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Animal;
import exceptions.ReadException;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
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

    @Context
    private UriInfo context;
//    
//    @Inject
//    private AnimalFacade animalFacade;
    
    @EJB
    private IAnimalFacade animalFacade;
    /**
     * Creates a new instance of AnimalREST
     */
    
    public AnimalREST() {
    }

    /**
     * Retrieves representation of an instance of services.AnimalREST
     * @return an instance of java.lang.String
     */
    @GET
    @Path("all/{clientId}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getAllAnimals(@PathParam("clientId") String clientId) {
        if (clientId == null || clientId.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("<error>Client ID is required and cannot be empty</error>")
                    .build();
        }
        
        try{
           List<Animal> animals = animalFacade.getAllAnimals(clientId);
           
            if (animals == null || animals.isEmpty()) {
                return Response.status(Response.Status.NO_CONTENT)
                        .entity("<info>No animals found for the given client ID</info>")
                        .build();
            }
            
           return Response
                   .ok(animals)
                   .build();
        } catch (ReadException e) {
            // Manejar errores durante la consulta
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
//           @ExceptionHandler(Exception.class)
//           return Response.status(Response.Status.500)
//           return Response.status(500)
                    .entity("<error>Error retrieving animals: " + e.getMessage() + "</error>")
                    .build();
        } catch (Exception e) {
            // Manejar otros errores inesperados
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("<error>An unexpected error occurred: " + e.getMessage() + "</error>")
                    .build();
        }
    }


    /**
     * PUT method for updating or creating an instance of AnimalREST
     * @param content representation for the resource
     */
//    @PUT
//    @Consumes(MediaType.APPLICATION_XML)
//    public void putXml(String content) {
//    }
//    
//    @GET
//    @Produces({"application/json"})
//    public List<Animal> getJson() throws ReadException {
//        return animalFacade.findAllAnimals();
    }

//    @GET
//    @Path("/search/{name}")
//    @Produces({"application/json"})
//    public List<Animal> findByName(@PathParam("name") String name) {
//       return animalFacade.findAnimalByName(name);
//    }

  

}
