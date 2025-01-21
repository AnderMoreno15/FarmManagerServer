/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import ejb.IProviderFacade;
import entities.ProviderEntity;
import exceptions.ReadException;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author InigoFreire
 */
@Path("providerentity")
public class ProviderEntityFacadeREST {
    
    @EJB
    private IProviderFacade providerFacade;

    public ProviderEntityFacadeREST(){
    }
    
    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_XML)
    public List<ProviderEntity> getAllSpecies() {
        try{
           return providerFacade.getAllProviders();
        } catch (ReadException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }
    
}
