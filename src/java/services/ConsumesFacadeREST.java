/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.AnimalGroup;
import entities.Consumes;
import entities.ConsumesId;
import entities.Product;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;

/**
 *
 * @author Usuario
 */

@Path("entities.consumes")

public class ConsumesFacadeREST  {
    
  @EJB
   private ConsumesBankManagerLocal ejb;
  
  
    private ConsumesId getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;productsId=productsIdValue;animalGroupId=animalGroupIdValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        entities.ConsumesId key = new entities.ConsumesId();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> productsId = map.get("productsId");
        if (productsId != null && !productsId.isEmpty()) {
            key.setProductsId(new java.lang.Long(productsId.get(0)));
        }
        java.util.List<String> animalGroupId = map.get("animalGroupId");
        if (animalGroupId != null && !animalGroupId.isEmpty()) {
            key.setAnimalGroupId(new java.lang.Long(animalGroupId.get(0)));
        }
        return key;
    }
 
    public ConsumesFacadeREST() {
    }

    //Faltan Excepcionesss

    @POST
    //@Override
    @javax.ws.rs.Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Consumes entity) throws CreateException{
       try{ ejb.createConsume(entity);
       }catch(CreateException e){}
       }
    

    @PUT
    @javax.ws.rs.Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateConsume(Consumes entity) throws UpdateException {
            try{ ejb.updateConsume(entity);
       }catch(UpdateException e){}
       }
    
    @DELETE
    public void deleteConsume(Consumes entity) throws DeleteException {
          try{ ejb.deleteConsume(entity);
       }catch(DeleteException e){}
       }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Consumes> getAllConsumes() throws ReadException{
       try{ 
           return ejb.getAllConsumes();
       }catch(ReadException e){}
      return null;
       }
    
        @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Consumes> findConsumesByProduct(Product product) throws ReadException{
    try{ 
        return ejb.findConsumesByProduct(product);
       }catch(ReadException e){}
      return null;
       }
    
       @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
     public List<Consumes> findConsumesByAnimalGroup(AnimalGroup animalGroup) throws ReadException{
    try{ 
        return ejb.findConsumesByAnimalGroup(animalGroup);
       }catch(ReadException e){}
      return null;
       }
    
      
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
     public List<Consumes> getConsumesByDate(@PathParam("from")Date dateFrom, @PathParam("to")Date dateTo) throws ReadException{
     try{ 
         return ejb.getConsumesByDate(dateFrom, dateTo);
       }catch(ReadException e){}
      return null;
       }
     @GET
    @Path("{from}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Consumes> getConsumesByDateFrom(@PathParam("from")Date dateFrom) throws ReadException{   
    try{
        return ejb.getConsumesByDateFrom(dateFrom);
       }catch(ReadException e){}
      return null;
       }
   @GET
    @Path("{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Consumes> getConsumesByDateto(@PathParam("to")Date dateto) throws ReadException{   
    try{
        return ejb.getConsumesByDateTo(dateto);
       }catch(ReadException e){}
      return null;
       }
    
}
