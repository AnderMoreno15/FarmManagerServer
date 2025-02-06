package services.consume;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import entities.Consumes;
import entities.ConsumesId;
import exceptions.CreateException;
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
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Response;

/**
 * RESTful service for managing consume entities.
 * Provides endpoints for CRUD operations (Create, Read, Update, Delete) on consumes.
 * The service interacts with a backend EJB to handle the business logic.
 * 
 * @author Pablo
 */
@Path("consumes")
public class ConsumesFacadeREST {

    // Injecting the local EJB to handle the consume business logic
    @EJB(name = "services.EJBConsumes")
    private ConsumesManagerLocal ejb;

    /**
     * Extracts the primary key (composite key) from the URI path segment.
     * The composite key consists of productId and animalGroupId.
     * 
     * @param pathSegment URI path segment containing matrix parameters.
     * @return ConsumesId The primary key for the consume entity.
     */
    private ConsumesId getPrimaryKey(PathSegment pathSegment) {
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

    /**
     * Default constructor for the service class.
     */
    public ConsumesFacadeREST() {
    }

    private static final java.util.logging.Logger LOGGER =
            java.util.logging.Logger.getLogger("Farm manager server side");

    /**
     * Creates a new consume record.
     * 
     * @param entity The consume entity to create.
     * @throws CreateException If an error occurs during creation.
     */
    @POST
    @javax.ws.rs.Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void createConsume(Consumes entity) throws CreateException {
        ejb.createConsume(entity);
    }

    /**
     * Updates an existing consume record.
     * 
     * @param consume The consume entity with updated data.
     */
    @PUT
    @javax.ws.rs.Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateConsume(Consumes consume) {
        try {
            ejb.updateConsume(consume);
        } catch (UpdateException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    /**
     * Deletes a consume record based on productId and animalGroupId.
     * 
     * @param productIdStr The product ID as a string.
     * @param animalGroupIdStr The animal group ID as a string.
     * @return Response A Response object indicating the result of the delete operation.
     */
    @DELETE
    @Path("Delete/{productId}/{animalGroupId}")
    @javax.ws.rs.Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response deleteConsumes(@PathParam("productId") String productIdStr,
                                   @PathParam("animalGroupId") String animalGroupIdStr) {
        try {
            LOGGER.info("Attempting to delete consume with productId: " + productIdStr + " and animalGroupId: " + animalGroupIdStr);

            // Convert String to Long
            Long productId = Long.parseLong(productIdStr);
            Long animalGroupId = Long.parseLong(animalGroupIdStr);

            // Find the consume using the composite key
            Consumes consume = ejb.findConsumeByProductAndAnimalGroup(productId, animalGroupId);

            if (consume != null) {
                // If consume exists, delete it
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

    /**
     * Retrieves a list of all consume records.
     * 
     * @return A list of all consume entities.
     * @throws ReadException If an error occurs during the read operation.
     */
    @GET
    @Path("All")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Consumes> getAllConsumes() throws ReadException {
        try {
            return ejb.getAllConsumes();
        } catch (ReadException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    /**
     * Retrieves a list of consumes related to a specific product.
     * 
     * @param nameProduct The name of the product to search for.
     * @return A list of consume entities associated with the product.
     * @throws ReadException If an error occurs during the read operation.
     */
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("Producto/{nameProduct}")
    public List<Consumes> findConsumesByProduct(@PathParam("nameProduct") String nameProduct) throws ReadException {
        try {
            return ejb.findConsumesByProduct(nameProduct);
        } catch (ReadException e) {
            return null;
        }
    }

    /**
     * Retrieves a list of consumes related to a specific animal group.
     * 
     * @param nameAnimalGroup The name of the animal group to search for.
     * @return A list of consume entities associated with the animal group.
     * @throws ReadException If an error occurs during the read operation.
     */
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("AnimalGroup/{nameAnimalGroup}")
    public List<Consumes> findConsumesByAnimalGroup(@PathParam("nameAnimalGroup") String nameAnimalGroup) throws ReadException {
        try {
            return ejb.findConsumesByAnimalGroup(nameAnimalGroup);
        } catch (ReadException e) {
            return null;
        }
    }

    /**
     * Retrieves a list of consumes that occurred within a specific date range.
     * 
     * @param dateFrom The start date (inclusive).
     * @param dateTo The end date (inclusive).
     * @return A list of consume entities within the specified date range.
     * @throws ReadException If an error occurs during the read operation.
     */
    @GET
    @Path("Rango/{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @JsonSerialize(as = Date.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
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

    /**
     * Retrieves a list of consumes that occurred after the specified date.
     * 
     * @param dateFrom The start date (inclusive).
     * @return A list of consume entities that occurred after the specified date.
     * @throws ReadException If an error occurs during the read operation.
     */
    @GET
    @Path("Desde/{from}")
    @JsonSerialize(as = Date.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Consumes> getConsumesByDateFrom(@PathParam("from") String dateFrom) throws ReadException {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter.parse(dateFrom);
            return ejb.getConsumesByDateFrom(date);
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
    }

    /**
     * Retrieves a list of consumes that occurred before the specified date.
     * 
     * @param dateTo The end date (inclusive).
     * @return A list of consume entities that occurred before the specified date.
     * @throws ReadException If an error occurs during the read operation.
     */
    @GET
    @Path("Hasta/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @JsonSerialize(as = Date.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
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
