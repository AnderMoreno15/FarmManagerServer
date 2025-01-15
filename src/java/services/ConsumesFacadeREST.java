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
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
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
import org.jboss.logging.Logger;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ws.rs.InternalServerErrorException;
import org.jboss.weld.bean.builtin.AbstractFacade;


/**
 *
 * @author Pablo
 */

@Path("entities.consumes")

public class ConsumesFacadeREST {
    
 @EJB (name = "services.EJBConsumes")
   private ConsumesManagerLocal ejb;
   
  
    private ConsumesId getPrimaryKey(PathSegment pathSegment) {
        
        
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;animalGroupId=animalGroupIdValue;productName=productNameValue'.
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

    //Faltan Excepcionesss

// @POST
//@Path("{ProductId}/{AnimalGroupId}")
//@javax.ws.rs.Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//public void create(@PathParam("ProductId") Long productId,
//                  @PathParam("AnimalGroupId") Long animalGroupId,
//                  Consumes consume) throws CreateException {
//    try {
//        // Crear el ID compuesto
//        ConsumesId id = new ConsumesId(productId, animalGroupId);
//        consume.setId(id);
//        
//        // Crear referencias mínimas
//        Product product = new Product();
//        product.setProductId(productId);
//        AnimalGroup animalGroup = new AnimalGroup();
//        animalGroup.setAnimalGroupId(animalGroupId);
//        
//        consume.setProduct(product);
//        consume.setAnimalGroup(animalGroup);
//        
//        ejb.createConsume(consume);
//    } catch (Exception e) {
//        LOGGER.severe("Error creating consume: " + e.getMessage());
//        throw new CreateException(e.getMessage());
//    }
//}
    @POST
    @javax.ws.rs.Consumes({MediaType.APPLICATION_XML})
    public void createConsume(Consumes entity) throws CreateException {
        ejb.createConsume(entity);}



    @PUT
    @Path("{productId}/{animalGroupId}")
    @javax.ws.rs.Consumes({MediaType.APPLICATION_XML})
public void updateConsume(@PathParam("productId") Long productId,
                         @PathParam("animalGroupId") Long animalGroupId,
                         Consumes consume) throws UpdateException {
    try {
        ConsumesId consumeId = new ConsumesId(productId,animalGroupId);
        LOGGER.severe("updating consume Restful level : ");
        consume.setConsumesId(consumeId);
        ejb.updateConsume(consume);
    }catch(UpdateException e){
        LOGGER.severe("Error updating consume Rest: " + e.getMessage());
        throw new UpdateException(e.getMessage());
    }
        
}


    
    @DELETE
    @Path("{consumeId}")
    public void deleteConsume(@PathParam("consumesId") Long consumeId) throws DeleteException {
    try {
        
        LOGGER.severe("deleting consume Restful level : ");
        
        ejb.deleteConsume(consumeId);
    }catch(Exception e){
        LOGGER.severe("Error deleting consume Rest: " + e.getMessage());
        throw new InternalServerErrorException(e.getMessage());
    }
    }
    @GET
    @Produces({MediaType.APPLICATION_XML})
    public List<Consumes> getAllConsumes() throws ReadException{
       try{ 
           return ejb.getAllConsumes();
       }catch(ReadException e){}
      return null;
       }
    
    @GET
    @Produces({MediaType.APPLICATION_XML})
    @Path("Producto/{ProductName}")
    public List<Consumes> findConsumesByProduct(@PathParam("ProductId")Long productId) throws ReadException{
    try{ 
        return ejb.findConsumesByProduct(productId);
       }catch(ReadException e){}
      return null;
       }
    
    
    @GET
    @Produces({MediaType.APPLICATION_XML})
    @Path("Animal/{animalGroupId}")   
     public List<Consumes> findConsumesByAnimalGroup(@PathParam("AnimalGroupId")Long animalGroupId) throws ReadException{
    try{ 
        return ejb.findConsumesByAnimalGroup(animalGroupId);
       }catch(ReadException e){}
      return null;
       }
    
      
    @GET
    @Path("Rango/{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
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
   @Produces({MediaType.APPLICATION_XML})
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
    @Produces({MediaType.APPLICATION_XML})
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

//@GET
//@Path("/products")
//@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//public List<Product> getAllProducts() {
//    return ejb.getAllProducts();
//}
//
//@GET
//@Path("/animalgroups")
//@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//public List<AnimalGroup> getAllAnimalGroups() {
//    return ejb.getAllAnimalGroups();
//}

