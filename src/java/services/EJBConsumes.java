/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.AnimalGroup;
import entities.Consumes;
import entities.ConsumesId;
import static entities.ConsumesId_.animalGroupId;
import static entities.ConsumesId_.productId;
import entities.Product;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Pablo
 */
@Stateless
public class EJBConsumes implements ConsumesManagerLocal {
    /**
     * Logger for the class.
     */
    private static final Logger LOGGER =
            Logger.getLogger("Farm manager server side");
    /**
     * Entity manager object.
     */
    @PersistenceContext(unitName = "FarmManagerPU")
    private EntityManager em;
 

    public void persist(Object object) {
        em.persist(object);
    }

//   @Override
//    public void createConsume(Consumes consume) throws CreateException {
//        try {
//            // Obtener las referencias gestionadas de Product y AnimalGroup
//            Product product = em.getReference(Product.class, consume.getProduct().getProductId());
//            AnimalGroup animalGroup = em.getReference(AnimalGroup.class, 
//                consume.getAnimalGroup().getAnimalGroupId());
//            
//            // Establecer las referencias gestionadas
//            consume.setProduct(product);
//            consume.setAnimalGroup(animalGroup);
//            
//            em.persist(consume);
//        } catch (Exception e) {
//            throw new CreateException(e.getMessage());
//        }
//    }
//      @Override
//    public void createConsume(Consumes consume) throws CreateException {
//        try {
//            if (consume == null) {
//                throw new CreateException("Consume entity cannot be null");
//            }
//
//            // Validar que los objetos necesarios no sean nulos
//            if (consume.getProduct() == null || consume.getProduct().getProductId() == null) {
//                throw new CreateException("Product ID cannot be null");
//            }
//            
//            if (consume.getAnimalGroup() == null || consume.getAnimalGroup().getAnimalGroupId() == null) {
//                throw new CreateException("AnimalGroup ID cannot be null");
//            }
//
//            // Buscar las entidades existentes
//            Product product = em.find(Product.class, consume.getProduct().getProductId());
//            AnimalGroup animalGroup = em.find(AnimalGroup.class, consume.getAnimalGroup().getAnimalGroupId());
//            
//            if (product == null || animalGroup == null) {
//                throw new CreateException("Referenced Product or AnimalGroup not found in database");
//            }
//
//            // Establecer las referencias
//            consume.setProduct(product);
//            consume.setAnimalGroup(animalGroup);
//            
//            // Persistir
//            em.persist(consume);
//            em.flush();
//            
//        } catch (Exception e) {
//            LOGGER.severe("Error creating consume: " + e.getMessage());
//            throw new CreateException(e.getMessage());
//        }
//    }
    
    // ConsumesId consumeId = new consumesId(animalGroupId,productId)


   @Override
    public void createConsume(Consumes consume) throws CreateException {
        try {
            em.persist(consume);
        } catch (Exception e) {
            throw new CreateException(e.getMessage());
        }
    }
    
public void updateConsume(Consumes consume) throws UpdateException {
   try{
       if (!em.contains(consume)){
           em.merge(consume);
       }
       em.flush();
   
   }catch(Exception e){
   throw new UpdateException(e.getMessage());}   
   }




    @Override
    public void deleteConsume(Long consumeId) throws DeleteException {
        try{
            
           Consumes consumes = em.find(Consumes.class, consumeId);
           if (consumes==null){throw new DeleteException("consume with id "+consumeId+" not found");}
           em.remove(consumes);
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "ConsumesManager: Exception deleting consume:",
                    e.getMessage());
            throw new DeleteException(e.getMessage());
        }
    }
    
@Override
public List<Consumes> getAllConsumes() throws ReadException {
    try {
        LOGGER.info("ConsumesManager: Reading all consumes.");
        return em.createQuery("SELECT c FROM Consumes c", Consumes.class)
                 .getResultList();
    } catch(Exception e) {
        LOGGER.severe("Error completo: " + e.toString());
        throw new ReadException(e.getMessage());
    }
}

    
    
    @Override
    public List<Consumes> findConsumesByProduct(Long productId) throws ReadException {
        List<Consumes> consumes=null;
        try{
            LOGGER.info("ConsumesManager: Reading consumes by product.");
            consumes=em.createNamedQuery("findConsumesByProduct")
                     .setParameter("productId", productId)
                     .getResultList();
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "ConsumesManager: Exception reading consumes byProduct .",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return consumes;
    }
    
   @Override
public List<Consumes> findConsumesByAnimalGroup(Long animalGroupId) throws ReadException {
    List<Consumes> consumes = null;
    try {
        LOGGER.info("ConsumesManager: Reading consumes by animal group. ID: " + animalGroupId);
        consumes = em.createNamedQuery("findConsumesByAnimalGroup")
                   .setParameter("animalGroupId", animalGroupId)
                   .getResultList();
        LOGGER.info("ConsumesManager: Found " + (consumes != null ? consumes.size() : "0") + " results");
    } catch(Exception e) {
        LOGGER.severe("Error completo: " + e.toString());
        throw new ReadException(e.getMessage());
    }
    return consumes;
}

    
        @Override
    public List<Consumes> getConsumesByDate(Date dateFrom, Date dateTo) throws ReadException {
        try {
            LOGGER.info("ConsumesManager: Reading consumes by date range.");
            return em.createNamedQuery("findConsumesByDateRange")
                        .setParameter("dateFrom", dateFrom)
                        .setParameter("dateTo", dateTo)
                        .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ConsumesManager: Exception reading consumes by dateRange .",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
    }
        @Override
    public List<Consumes> getConsumesByDateFrom(Date dateFrom) throws ReadException {
        try {
            LOGGER.info("ConsumesManager: Reading consumes by dateFrom.");
            return em.createNamedQuery("findConsumesByDateFrom")
                        .setParameter("dateFrom", dateFrom)
                        .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ConsumesManager: Exception reading consumes by dateFrom .",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
    }
         @Override
    public List<Consumes> getConsumesByDateTo(Date dateTo) throws ReadException {
        try {
            LOGGER.info("ConsumesManager: Reading consumes by dateTo");
            return em.createNamedQuery("findConsumesByDateTo")
                        .setParameter("dateTo", dateTo)
                        .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ConsumesManager: Exception reading consumes by dateTo.",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
    }
}
