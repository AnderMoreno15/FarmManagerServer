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
 * Stateless session bean that implements the ConsumesManagerLocal interface.
 * This class provides the business logic for managing consumes in the system.
 * It includes methods for creating, updating, deleting, and retrieving consume records.
 * The methods interact with the EntityManager to perform CRUD operations on the consume entities.
 * 
 * @author Pablo
 */
@Stateless
public class EJBConsumes implements ConsumesManagerLocal {

    /**
     * Logger for logging important events and errors.
     */
    private static final Logger LOGGER = Logger.getLogger("Farm manager server side");

    /**
     * Entity manager used to interact with the persistence context.
     */
    @PersistenceContext(unitName = "FarmManagerPU")
    private EntityManager em;

    /**
     * Persists the given object in the database.
     * 
     * @param object The object to be persisted.
     */
    public void persist(Object object) {
        em.persist(object);
    }

    /**
     * Creates a new consume record in the database.
     * Ensures the referenced AnimalGroup and Product entities are managed before persisting the consume.
     * 
     * @param consume The consume entity to be created.
     * @throws CreateException If any error occurs while creating the consume.
     */
    @Override
    public void createConsume(Consumes consume) throws CreateException {
        try {
            if (consume == null) {
                throw new CreateException("Consume entity cannot be null");
            }

            AnimalGroup managedAnimalGroup = em.find(AnimalGroup.class, consume.getAnimalGroup().getId());
            ProductEntity managedProduct = em.find(ProductEntity.class, consume.getProduct().getId());

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

    /**
     * Updates an existing consume record in the database.
     * If the consume is not already managed, it merges the consume entity.
     * 
     * @param consume The consume entity with updated data.
     * @throws UpdateException If an error occurs while updating the consume.
     */
    @Override
    public void updateConsume(Consumes consume) throws UpdateException {
        try {
            if (!em.contains(consume)) {
                em.merge(consume);
            }
            em.flush();
        } catch (Exception e) {
            throw new UpdateException(e.getMessage());
        }
    }

    /**
     * Finds a consume record using the product ID and animal group ID.
     * 
     * @param productId The ID of the product.
     * @param animalGroupId The ID of the animal group.
     * @return The consume entity matching the provided product and animal group IDs, or null if not found.
     * @throws ReadException If an error occurs while retrieving the consume record.
     */
    @Override
    public Consumes findConsumeByProductAndAnimalGroup(Long productId, Long animalGroupId) throws ReadException {
        try {
            LOGGER.info("Searching for consume with productId: " + productId + " and animalGroupId: " + animalGroupId);

            List<Consumes> consumes = em.createQuery("SELECT c FROM Consumes c WHERE c.consumesId.productId = :productId AND c.consumesId.animalGroupId = :animalGroupId", Consumes.class)
                                        .setParameter("productId", productId)
                                        .setParameter("animalGroupId", animalGroupId)
                                        .getResultList();

            return consumes.isEmpty() ? null : consumes.get(0);
        } catch (Exception e) {
            LOGGER.severe("Error searching consume: " + e.getMessage());
            throw new ReadException();
        }
    }



    @Override
    public Consumes findConsume(ConsumesId consumesId) throws ReadException {
        try {
            return em.find(Consumes.class, consumesId);
        } catch (Exception e) {
            throw new ReadException("Error finding consume: " + e.getMessage());
        }
    }

    @Override
    public void deleteConsume(Consumes consume) throws DeleteException {
        try {
            consume = em.merge(consume); 
            em.remove(consume);
        } catch (Exception e) {
            throw new DeleteException("Error deleting consume: " + e.getMessage());
        }
    }


    /**
     * Retrieves all consume records from the database.
     * 
     * @return A list of all consume entities.
     * @throws ReadException If an error occurs while retrieving the consume records.
     */
    @Override
    public List<Consumes> getAllConsumes() throws ReadException {
        List<Consumes> consumes = null;
        try {
            consumes = em.createNamedQuery("findAllConsumes", Consumes.class).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ConsumesManager: Exception reading consumes All.", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return consumes;
    }

    /**
     * Retrieves a list of consume records associated with a specific product.
     * 
     * @param nameProduct The name of the product.
     * @return A list of consume entities associated with the specified product.
     * @throws ReadException If an error occurs while retrieving the consume records.
     */
    @Override
    public List<Consumes> findConsumesByProduct(String nameProduct) throws ReadException {
        List<Consumes> consumes = null;
        try {
            consumes = em.createNamedQuery("findConsumesByProduct")
                        .setParameter("nameProduct", nameProduct)
                        .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ConsumesManager: Exception reading consumes byProduct.", e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return consumes;
    }

    /**
     * Retrieves a list of consume records associated with a specific animal group.
     * 
     * @param nameAnimalGroup The name of the animal group.
     * @return A list of consume entities associated with the specified animal group.
     * @throws ReadException If an error occurs while retrieving the consume records.
     */
    @Override
    public List<Consumes> findConsumesByAnimalGroup(String nameAnimalGroup) throws ReadException {
        List<Consumes> consumes = null;
        try {
            consumes = em.createNamedQuery("findConsumesByAnimalGroup")
                        .setParameter("nameAnimalGroup", nameAnimalGroup)
                        .getResultList();
        } catch (Exception e) {
            LOGGER.severe("Error completo: " + e.toString());
            throw new ReadException(e.getMessage());
        }
        return consumes;
    }

    /**
     * Retrieves a list of consume records that occurred within a specific date range.
     * 
     * @param dateFrom The start date of the range.
     * @param dateTo The end date of the range.
     * @return A list of consume entities within the specified date range.
     * @throws ReadException If an error occurs while retrieving the consume records.
     */
    @Override
    public List<Consumes> getConsumesByDate(Date dateFrom, Date dateTo) throws ReadException {
        try {
            return em.createNamedQuery("findConsumesByDateRange")
                        .setParameter("dateFrom", dateFrom)
                        .setParameter("dateTo", dateTo)
                        .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ConsumesManager: Exception reading consumes by dateRange.", e.getMessage());
            throw new ReadException(e.getMessage());
        }
    }

    /**
     * Retrieves a list of consume records that occurred from a specific start date.
     * 
     * @param dateFrom The start date of the range.
     * @return A list of consume entities starting from the specified date.
     * @throws ReadException If an error occurs while retrieving the consume records.
     */
    @Override
    public List<Consumes> getConsumesByDateFrom(Date dateFrom) throws ReadException {
        try {
            return em.createNamedQuery("findConsumesByDateFrom")
                        .setParameter("dateFrom", dateFrom)
                        .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ConsumesManager: Exception reading consumes by dateFrom.", e.getMessage());
            throw new ReadException(e.getMessage());
        }
    }

    /**
     * Retrieves a list of consume records that occurred before a specific end date.
     * 
     * @param dateTo The end date of the range.
     * @return A list of consume entities that occurred before the specified end date.
     * @throws ReadException If an error occurs while retrieving the consume records.
     */
    @Override
    public List<Consumes> getConsumesByDateTo(Date dateTo) throws ReadException {
        try {
            return em.createNamedQuery("findConsumesByDateTo")
                        .setParameter("dateTo", dateTo)
                        .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ConsumesManager: Exception reading consumes by dateTo.", e.getMessage());
            throw new ReadException(e.getMessage());
        }
    }
}
