/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Animal;
import exceptions.ReadException;
import java.util.List;
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

/**
 * REST Web Service
 *
 * @author Aitziber
 */
@Path("animal")
public class AnimalREST {

    @Context
    private UriInfo context;
    
    @Inject
    private AnimalFacade animalFacade;

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
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of AnimalREST
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
    
    @GET
    @Produces({"application/json"})
    public List<Animal> getJson() throws ReadException {
        return animalFacade.findAllAnimals();
    }

//    @GET
//    @Path("/search/{name}")
//    @Produces({"application/json"})
//    public List<Animal> findByName(@PathParam("name") String name) {
//       return animalFacade.findAnimalByName(name);
//    }

}
