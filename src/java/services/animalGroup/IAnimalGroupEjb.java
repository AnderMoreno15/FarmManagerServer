/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.animalGroup;

import entities.AnimalGroup;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;
import java.util.List;
import javax.ejb.Local;

/**
 * Interface for managing animal groups in the system.
 * <p>
 * This interface defines methods for creating, updating, deleting, and retrieving animal groups from the database.
 * It also allows querying animal groups based on their associated manager and other properties.
 * Each method may throw specific exceptions to indicate errors that occur during these operations.
 * </p>
 * 
 * @author Ander
 * @version 1.0
 */
@Local
public interface IAnimalGroupEjb {

    /**
     * Creates and persists a new animal group.
     * <p>
     * This method is used to persist an {@link AnimalGroup} entity in the database.
     * It throws a {@link CreateException} if there is an error during the creation process.
     * </p>
     * @param animalGroup The animal group to be created.
     * @throws CreateException If an error occurs during creation.
     */
    public void setAnimalGroup(AnimalGroup animalGroup) throws CreateException;

    /**
     * Updates an existing animal group.
     * <p>
     * This method is used to update an existing {@link AnimalGroup} entity in the database.
     * It throws an {@link UpdateException} if there is an error during the update process.
     * </p>
     * @param animalGroup The animal group to be updated.
     * @throws UpdateException If an error occurs during the update.
     */
    public void updateAnimalGroup(AnimalGroup animalGroup) throws UpdateException;

    /**
     * Deletes a specific animal group.
     * <p>
     * This method removes the given {@link AnimalGroup} entity from the database.
     * It throws a {@link DeleteException} if there is an error during the deletion process.
     * </p>
     * @param animalGroup The animal group to be deleted.
     * @throws DeleteException If an error occurs during the deletion.
     */
    public void deleteAnimalGroup(AnimalGroup animalGroup) throws DeleteException;

    /**
     * Retrieves a list of animal groups managed by a specific manager.
     * <p>
     * This method returns all animal groups associated with a given manager's ID.
     * It throws a {@link ReadException} if there is an error during the retrieval.
     * </p>
     * @param managerId The ID of the manager whose animal groups are to be retrieved.
     * @return A list of animal groups managed by the specified manager.
     * @throws ReadException If an error occurs while retrieving the animal groups.
     */
    public List<AnimalGroup> getAnimalGroupsByManager(Long managerId) throws ReadException;

    /**
     * Retrieves a list of all animal groups.
     * <p>
     * This method returns all the animal groups from the database.
     * It throws a {@link ReadException} if there is an error during the retrieval.
     * </p>
     * @return A list of all animal groups.
     * @throws ReadException If an error occurs while retrieving the animal groups.
     */
    public List<AnimalGroup> getAnimalGroups() throws ReadException;

    /**
     * Retrieves a list of animal groups filtered by name and manager ID.
     * <p>
     * This method returns animal groups that match the given group name and manager ID.
     * It throws a {@link ReadException} if there is an error during the retrieval.
     * </p>
     * @param groupName The name of the animal group to search for.
     * @param managerId The ID of the manager to filter the results.
     * @return A list of animal groups matching the criteria.
     * @throws ReadException If an error occurs while retrieving the animal groups.
     */
    public List<AnimalGroup> getAnimalGroupByName(String groupName, Long managerId) throws ReadException;

    /**
     * Deletes an animal group by its ID.
     * <p>
     * This method removes the animal group associated with the given ID from the database.
     * It throws a {@link DeleteException} if there is an error during the deletion process.
     * </p>
     * @param id The ID of the animal group to be deleted.
     * @throws DeleteException If an error occurs during the deletion.
     */
    public void deleteAnimalGroupById(Long id) throws DeleteException;
}
