/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.consume;

import entities.Animal;
import entities.AnimalGroup;
import entities.Consumes;
import entities.ConsumesId;
import entities.ProductEntity;
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
import javax.ws.rs.core.Response;

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

    @Override
public void createConsume(Consumes consume) throws CreateException {
    try {
        if (consume == null) {
            throw new CreateException("Consume entity cannot be null");
        }

//        AnimalGroup managedAnimalGroup = em.find(AnimalGroup.class, 
//            consume.getAnimalGroup().getAnimalGroupId());
//        Product managedProduct = em.find(Product.class, 
//            consume.getProduct().getProductId());

            AnimalGroup managedAnimalGroup = em.find(AnimalGroup.class, 
                consume.getAnimalGroup().getId());
            ProductEntity managedProduct = em.find(ProductEntity.class, 
                consume.getProduct().getId());

        if (managedAnimalGroup == null || managedProduct == null) {
            throw new CreateException("Referenced AnimalGroup or Product not found");
        }
 
        consume.setAnimalGroup(managedAnimalGroup);
        consume.setProduct(managedProduct);

        em.persist(consume);
        em.flush();
        
    } catch (Exception e) {
        throw new CreateException(e.getMessage());
    }
}
    
    @Override
   public void updateConsume(Consumes consume) throws UpdateException {
   try{
       if (!em.contains(consume)){
           em.merge(consume);
       }
       em.flush();
   
   }catch(Exception e){
   throw new UpdateException(e.getMessage());}   
   }
   
     /**
     * Busca un consumo utilizando productId y animalGroupId
     */
    public Consumes findConsumeByProductAndAnimalGroup(Long productId, Long animalGroupId) throws ReadException {
        try {
            LOGGER.info("Searching for consume with productId: " + productId + " and animalGroupId: " + animalGroupId);

            // Buscamos el consumo en la base de datos
            List<Consumes> consumes = em.createQuery("SELECT c FROM Consumes c WHERE c.consumesId.productId = :productId AND c.consumesId.animalGroupId = :animalGroupId", Consumes.class)
                                        .setParameter("productId", productId)
                                        .setParameter("animalGroupId", animalGroupId)
                                        .getResultList();

            // Retornamos el primer resultado si existe
            return consumes.isEmpty() ? null : consumes.get(0);

        } catch (Exception e) {
            LOGGER.severe("Error searching consume: " + e.getMessage());
            throw new ReadException();
        }
    }

    /**
     * Elimina un consumo
     */
    public void deleteConsume(Consumes consume) throws DeleteException{
        try {
            LOGGER.info("Deleting consume with productId: " + consume.getConsumesId().getProductId() + " and animalGroupId: " + consume.getConsumesId().getAnimalGroupId());
            em.remove(consume);
            LOGGER.info("Consume deleted successfully.");
        } catch (Exception e) {
            LOGGER.severe("Error deleting consume: " + e.getMessage());
            throw new DeleteException();
        }
    }





    @Override
    public List<Consumes> getAllConsumes() throws ReadException {
      List<Consumes> consumes=null;
        try{   
            System.out.println("-----------------------------");
            consumes=em.createNamedQuery("findAllConsumes", Consumes.class).getResultList();
            System.out.println("------------------------- after calling get all ------------");
      }catch(Exception e){
      LOGGER.log(Level.SEVERE, "ConsumesManager: Exception reading consumes All .",
                    e.getMessage());
            throw new ReadException(e.getMessage());
         }
         return consumes;
    }
  
    
    @Override
    public List<Consumes> findConsumesByProduct(String nameProduct) throws ReadException {
        List<Consumes> consumes=null;
        try{
            LOGGER.info("ConsumesManager: Reading consumes by product.");
            consumes=em.createNamedQuery("findConsumesByProduct")
                     .setParameter("nameProduct", nameProduct)
                     .getResultList();
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "ConsumesManager: Exception reading consumes byProduct .",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return consumes;
    }
    
   @Override
   public List<Consumes> findConsumesByAnimalGroup(String nameAnimalGroup) throws ReadException {
    List<Consumes> consumes = null;
    try {
        LOGGER.info("ConsumesManager: Reading consumes by animal group name: " + nameAnimalGroup);
        consumes = em.createNamedQuery("findConsumesByAnimalGroup")
                   .setParameter("nameAnimalGroup", nameAnimalGroup)
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
