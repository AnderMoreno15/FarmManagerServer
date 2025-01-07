/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.AnimalGroup;
import entities.Consumes;
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
public class EJBConsumes implements ConsumesBankManagerLocal {
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
        try{
            LOGGER.info("ConsumesManager: Creating consume");
            em.persist(consume);
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "ConsumesManager: Exception creating consume:",
                    e.getMessage());
            throw new CreateException(e.getMessage());
        }
    }

    @Override
    public void updateConsume(Consumes consume) throws UpdateException {
        try{
            LOGGER.info("ConsumesManager: Updating consume");
            if(!em.contains(consume))
                em.merge(consume);
            em.flush();
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "ConsumesManager: Exception updating consume:",
                    e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }

    @Override
    public void deleteConsume(Consumes consume) throws DeleteException {
        try{
            LOGGER.info("ConsumesManager: Deleting consume");
            em.remove(em.merge(consume));
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "ConsumesManager: Exception deleting consume:",
                    e.getMessage());
            throw new DeleteException(e.getMessage());
        }
    }
    
    @Override
    public List<Consumes> getAllConsumes() throws ReadException {
        List<Consumes> consumes=null;
        try{
            LOGGER.info("ConsumesManager: Reading all consumes.");
            consumes=em.createNamedQuery("findAllConsumes").getResultList();
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "ConsumesManager: Exception reading all consumes:",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return consumes;
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
        List<Consumes> consumes=null;
        try{
            LOGGER.info("ConsumesManager: Reading consumes by animal group.");
            consumes=em.createNamedQuery("findConsumesByAnimalGroup")
                     .setParameter("animalGroupId", animalGroupId)
                     .getResultList();
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "ConsumesManager: Exception reading consumes by animalGroup .",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return consumes;
    }
    
        @Override
    public List<Consumes> getConsumesByDate(Date dateFrom, Date dateTo) throws ReadException {
        try {
            LOGGER.info("ConsumesManager: Reading consumes by date range.");
            return em.createNamedQuery("getConsumesByDateRange", Consumes.class)
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
            return em.createNamedQuery("getConsumesByDateRange", Consumes.class)
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
            LOGGER.info("ConsumesManager: Reading consumes by dateTo.");
            return em.createNamedQuery("getConsumesByDateTo", Consumes.class)
                        .setParameter("dateFrom", dateTo)
                        .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ConsumesManager: Exception reading consumes by dateTo .",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
    }
}
