/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import ejb.IProviderFacade;
import entities.ProviderEntity;
import java.util.List;
import exceptions.CreateException;
import exceptions.ReadException;
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
 * @author InigoFreire
 */
@Stateless
@Path("entities.providerentity")
public class ProviderEntityFacadeREST{

    @EJB
    private IProviderFacade providerFacade;

    public ProviderEntityFacadeREST() {
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML})
    public void create(ProviderEntity provider){
        try {
            providerFacade.create(provider);
        } catch (CreateException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public ProviderEntity getProviderById(@PathParam("id") Long id){
        try{
            return providerFacade.getProviderById(id);
        } catch (ReadException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }
}