/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Species;
import exceptions.ReadException;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * RESTful service for Species.
 *
 * @author Aitziber
 */
@Path("species")
public class SpeciesREST {
    
    @EJB
    private ISpeciesFacade speciesFacade;
    
    /**
     * Creates a new instance of SpeciesREST
     */
    public SpeciesREST() {
    }
    
    @GET
    @Path("all}")
    @Produces(MediaType.APPLICATION_XML)
    public List<Species> getAllSpecies() {
        try{
           return speciesFacade.getAllSpecies();
        } catch (ReadException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }
    
}
