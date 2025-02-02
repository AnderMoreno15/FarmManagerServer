/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;



import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ejb.ConsumesManagerLocal;
import entities.Animal;
import entities.AnimalGroup;
import entities.Consumes;
import entities.ConsumesId;
import entities.ProductEntity;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;
import java.text.ParseException;
import javax.ejb.EJB;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Response;
import org.hibernate.Hibernate;



/**
 *
 * @author Pablo
 */

@Path("consumes")

public class ConsumesFacadeREST  {
    
 @EJB (name = "services.EJBConsumes")
   private ConsumesManagerLocal ejb;
   
  
    private ConsumesId getPrimaryKey(PathSegment pathSegment) {
        
        
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;animalGroupId=animalGroupIdValue;productId=productIdValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        entities.ConsumesId key = new entities.ConsumesId();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> animalGroupId = map.get("animalGroupId");
        if (animalGroupId != null && !animalGroupId.isEmpty()) {
            key.setAnimalGroupId(new java.lang.Long(animalGroupId.get(0)));
        }
        java.util.List<String> productId = map.get("productId");
        if (productId != null && !productId.isEmpty()) {
            key.setProductId(new java.lang.Long(productId.get(0)));
        }
        return key;
    }
 
    public ConsumesFacadeREST() {
    }
     private static final java.util.logging.Logger LOGGER =
            java.util.logging.Logger.getLogger("Farm manager server side");

     
    @POST
    @javax.ws.rs.Consumes(MediaType.APPLICATION_XML)
    public void createConsume(Consumes entity) throws CreateException {
        ejb.createConsume(entity);}

    
    @PUT
    @javax.ws.rs.Consumes(MediaType.APPLICATION_XML)
    public void updateConsume(Consumes consume) {
        try {
            ejb.updateConsume(consume);
        } catch (UpdateException ex) {
            throw new InternalServerErrorException(ex.getMessage());        
        }
    }
        

  
    @DELETE
@Path("{productId}/{animalGroupId}")
public Response deleteConsumes(@PathParam("productId") String productIdStr, 
                               @PathParam("animalGroupId") String animalGroupIdStr) {
    try {
        LOGGER.info("Attempting to delete consume with productId: " + productIdStr + " and animalGroupId: " + animalGroupIdStr);

        // Convierte los String a Long
        Long productId = Long.parseLong(productIdStr);
        Long animalGroupId = Long.parseLong(animalGroupIdStr);

        // Busca el consumo usando la clave compuesta
        Consumes consume = ejb.findConsumeByProductAndAnimalGroup(productId, animalGroupId);

        if (consume != null) {
            // Si se encuentra el consumo, lo eliminamos
            ejb.deleteConsume(consume);
            LOGGER.info("Successfully deleted consume.");
            return Response.noContent().build(); // 204 No Content
        } else {
            LOGGER.warning("Consume not found with productId: " + productId + " and animalGroupId: " + animalGroupId);
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Consume not found with productId: " + productId + " and animalGroupId: " + animalGroupId)
                           .build();
        }
    } catch (NumberFormatException e) {
        LOGGER.severe("Invalid ID format: " + e.getMessage());
        return Response.status(Response.Status.BAD_REQUEST).entity("Invalid ID format.").build();
    } catch (Exception e) {
        LOGGER.severe("Error deleting consume: " + e.getMessage());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error deleting consume.").build();
    }
}



  @GET
@Path("All")
@Produces(MediaType.APPLICATION_XML)
public List<Consumes> getAllConsumes() throws ReadException {
    try{
        
           return ejb.getAllConsumes();
        } catch (ReadException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("Producto/{nameProduct}")
    public List<Consumes> findConsumesByProduct(@PathParam("nameProduct")String nameProduct) throws ReadException{
    try{ 
        return ejb.findConsumesByProduct(nameProduct);
       }catch(ReadException e){}
      return null;
       }
    
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("AnimalGroup/{nameAnimalGroup}")   
     public List<Consumes> findConsumesByAnimalGroup(@PathParam("nameAnimalGroup")String nameAnimalGroup) throws ReadException{
    try{ 
        return ejb.findConsumesByAnimalGroup(nameAnimalGroup);
       }catch(ReadException e){}
      return null;
       }
    
      
    @GET
    @Path("Rango/{from}/{to}")
    @Produces(MediaType.APPLICATION_XML)
    @JsonSerialize(as=Date.class)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ssXXX")
    
    public List<Consumes> getConsumesByDate(@PathParam("from") String dateFrom, 
                                       @PathParam("to") String dateTo) throws ReadException {
    try {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date from = formatter.parse(dateFrom);
        Date to = formatter.parse(dateTo);
        return ejb.getConsumesByDate(from, to);
    } catch (Exception e) {
        throw new ReadException(e.getMessage());
    }
}

   @GET
   @Path("Desde/{from}")
   @JsonSerialize(as=Date.class)
   @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ssXXX")
   @Produces(MediaType.APPLICATION_XML)
    public List<Consumes> getConsumesByDateFrom(@PathParam("from") String dateFrom) throws ReadException {   
    try {
        // Convertir String a Date
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(dateFrom);
        return ejb.getConsumesByDateFrom(date);
    } catch(Exception e) {
        throw new ReadException(e.getMessage());
    }
}



    @GET
    @Path("Hasta/{to}")
    @Produces(MediaType.APPLICATION_XML)
    @JsonSerialize(as=Date.class)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ssXXX")
    public List<Consumes> getConsumesByDateTo(@PathParam("to") String dateTo) throws ReadException {   
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter.parse(dateTo);
            
            List<Consumes> result = ejb.getConsumesByDateTo(date);
            if (result == null) {
                throw new ReadException("No se encontraron resultados");
            }
            return result;
        } catch (ParseException e) {
            throw new ReadException("Error en el formato de fecha. Use: yyyy-MM-dd");
        } catch (ReadException e) {
            throw e;
        } catch (Exception e) {
            throw new ReadException("Error inesperado: " + e.getMessage());
        }
    }
    }



