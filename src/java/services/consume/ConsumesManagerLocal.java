package services.consume;

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
import javax.ejb.Local;
import javax.ws.rs.core.Response;

/**
 * Local interface for the Consume Manager. This interface defines the methods
 * to handle CRUD operations related to the 'Consume' entity. These methods 
 * support creation, update, deletion, and retrieval of consume records.
 * The operations interact with the database and business logic to manage consumes.
 * 
 * @author Pablo
 */
@Local
public interface ConsumesManagerLocal {

    /**
     * Creates a new consume record in the system.
     * 
     * @param consume The consume entity to be created.
     * @throws CreateException If an error occurs while creating the consume.
     */
    public void createConsume(Consumes consume) throws CreateException;

    /**
     * Updates an existing consume record in the system.
     * 
     * @param consume The consume entity with updated data.
     * @throws UpdateException If an error occurs while updating the consume.
     */
    public void updateConsume(Consumes consume) throws UpdateException;

    /**
     * Deletes an existing consume record from the system.
     * 
     * @param consume The consume entity to be deleted.
     * @throws DeleteException If an error occurs while deleting the consume.
     */
    public void deleteConsume(Consumes consume) throws DeleteException;

    /**
     * Retrieves all consume records from the system.
     * 
     * @return A list of all consume entities.
     * @throws ReadException If an error occurs while retrieving consume records.
     */
    public List<Consumes> getAllConsumes() throws ReadException;

    /**
     * Finds a consume record based on the product ID and animal group ID.
     * 
     * @param productId The ID of the product.
     * @param animalGroupId The ID of the animal group.
     * @return The consume entity matching the specified product ID and animal group ID.
     * @throws ReadException If an error occurs while reading the consume record.
     */
    public Consumes findConsumeByProductAndAnimalGroup(Long productId, Long animalGroupId) throws ReadException;

    /**
     * Retrieves a list of consume records associated with a specific product.
     * 
     * @param nameProduct The name of the product.
     * @return A list of consume entities associated with the specified product.
     * @throws ReadException If an error occurs while retrieving consume records.
     */
    public List<Consumes> findConsumesByProduct(String nameProduct) throws ReadException;

    /**
     * Retrieves a list of consume records associated with a specific animal group.
     * 
     * @param nameAnimalGroup The name of the animal group.
     * @return A list of consume entities associated with the specified animal group.
     * @throws ReadException If an error occurs while retrieving consume records.
     */
    public List<Consumes> findConsumesByAnimalGroup(String nameAnimalGroup) throws ReadException;

    /**
     * Retrieves a list of consume records that occurred within a specific date range.
     * 
     * @param dateFrom The start date of the range (inclusive).
     * @param dateTo The end date of the range (inclusive).
     * @return A list of consume entities within the specified date range.
     * @throws ReadException If an error occurs while retrieving consume records.
     */
    public List<Consumes> getConsumesByDate(Date dateFrom, Date dateTo) throws ReadException;

    /**
     * Retrieves a list of consume records that occurred from a specific start date.
     * 
     * @param dateFrom The start date (inclusive).
     * @return A list of consume entities that occurred from the specified start date.
     * @throws ReadException If an error occurs while retrieving consume records.
     */
    public List<Consumes> getConsumesByDateFrom(Date dateFrom) throws ReadException;

    /**
     * Retrieves a list of consume records that occurred before a specific end date.
     * 
     * @param dateTo The end date (inclusive).
     * @return A list of consume entities that occurred before the specified end date.
     * @throws ReadException If an error occurs while retrieving consume records.
     */
    public List<Consumes> getConsumesByDateTo(Date dateTo) throws ReadException;
}
