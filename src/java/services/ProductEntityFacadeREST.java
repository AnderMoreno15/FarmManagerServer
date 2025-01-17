/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import ejb.IProductFacade;
import entities.ProductEntity;
import entities.ProviderEntity;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.BadRequestException;
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
@Path("productentity")
public class ProductEntityFacadeREST {

    private static final Logger LOGGER = Logger.getLogger(ProductEntityFacadeREST.class.getName());

    @EJB
    private IProductFacade productFacade;

    /**
     * Creates a new instance of ProductFacadeREST
     */
    public ProductEntityFacadeREST() {
    }

//    Example
//    <?xml version="1.0" encoding="UTF-8"?>
//    <productEntity>
//    <name>Peras</name>
//    <providerEntity>
//    <id>1</id>
//    </providerEntity>
//    <stock>88</stock>
//    </productEntity>
//    @POST
//    @Consumes({MediaType.APPLICATION_XML})
//    public void createProduct(ProductEntity product) {
//        try {
//            if (product.getProvider() != null && product.getProvider().getId() != null) {
//                LOGGER.log(Level.INFO, "Attempting to create ProductEntity with provider ID: {0}", product.getProvider().getId());
//            } else {
//                LOGGER.log(Level.WARNING, "The provided ProductEntity has a null provider or provider ID.");
//            }
//
//            LOGGER.log(Level.INFO, "Product details: Name={0}, Price={1}, Stock={2}", 
//                       new Object[]{product.getName(), product.getPrice(), product.getStock()});
//
//            productFacade.createProduct(product);
//
//            LOGGER.log(Level.INFO, "ProductEntity created successfully.");
//        } catch (CreateException ex) {
//            LOGGER.log(Level.SEVERE, "Error occurred while creating ProductEntity: {0}", ex.getMessage());
//            throw new InternalServerErrorException(ex.getMessage());
//        }
//    }
    @POST
    @Consumes({MediaType.APPLICATION_XML})
    public void createProduct(ProductEntity product) {
        try {
            if (product.getProvider() == null || product.getProvider().getId() == null) {
                throw new BadRequestException("Provider information is required.");
            }
            ProviderEntity managedProvider = productFacade.findProviderById(product.getProvider().getId());
            if (managedProvider == null) {
                throw new BadRequestException("Provider not found.");
            }
            product.setProvider(managedProvider);
            productFacade.createProduct(product);
        } catch (CreateException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML})
    public void updateProduct(ProductEntity product) {
        try {
            productFacade.updateProduct(product);
        } catch (UpdateException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    @DELETE
    @Path("delete/{id}")
    public void deleteProductById(@PathParam("id") Long id) {
        try {
            productFacade.deleteProductById(id);
        } catch (DeleteException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    @GET
    @Path("name/{name}")
    @Produces({MediaType.APPLICATION_XML})
    public ProductEntity getProductByName(@PathParam("name") String name) {
        try {
            return productFacade.getProductByName(name);
        } catch (ReadException ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    @GET
    @Path("date/{date}")
    @Produces({MediaType.APPLICATION_XML})
    public List<ProductEntity> searchByDate(@PathParam("date") String date) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date searchDate = formatter.parse(date);
            List<ProductEntity> products = productFacade.getProductByCreatedDate(searchDate);
            if (products != null && !products.isEmpty()) {
                return products;
            } else {
                return null;
            }
        } catch (Exception ex) {
            throw new InternalServerErrorException(ex.getMessage());
        }
    }
}
