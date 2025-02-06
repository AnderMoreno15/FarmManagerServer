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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Stateless session bean that provides operations for managing AnimalGroup entities.
 * <p>
 * This class offers methods to create, update, delete, and retrieve animal groups in the system. 
 * It interacts with the EntityManager to perform persistence operations and handles exceptions related to 
 * each operation by throwing custom exceptions (CreateException, UpdateException, DeleteException, ReadException).
 * </p>
 *
 * @author Ander
 * @version 1.0
 */
@Stateless
public class AnimalGroupEjb implements IAnimalGroupEjb {

    private static final Logger LOGGER = Logger.getLogger(AnimalGroupEjb.class.getName());

    @PersistenceContext(unitName = "FarmManagerPU")
    private EntityManager em;

    /**
     * Creates and persists a new animal group.
     * <p>
     * This method takes an {@link AnimalGroup} object and persists it in the database. If the operation fails, it throws a {@link CreateException}.
     * </p>
     *
     * @param animalGroup The animal group to be created.
     * @throws CreateException If an error occurs during creation.
     */
    @Override
    public void setAnimalGroup(AnimalGroup animalGroup) throws CreateException {
        try {
            em.persist(animalGroup);
        } catch (Exception e) {
            throw new CreateException(e.getMessage());
        }
    }

    /**
     * Updates an existing animal group.
     * <p>
     * This method checks if the {@link AnimalGroup} exists in the persistence context and either updates the existing entity or merges it into the context. If an error occurs during the update, an {@link UpdateException} is thrown.
     * </p>
     *
     * @param animalGroup The animal group to be updated.
     * @throws UpdateException If an error occurs during the update.
     */
    @Override
    public void updateAnimalGroup(AnimalGroup animalGroup) throws UpdateException {
        try {
            if (!em.contains(animalGroup)) {
                em.merge(animalGroup);
            }
            em.flush();
        } catch (Exception e) {
            throw new UpdateException(e.getMessage());
        }
    }

    /**
     * Deletes a specific animal group.
     * <p>
     * This method removes the given {@link AnimalGroup} entity from the database. If an error occurs during the deletion, a {@link DeleteException} is thrown.
     * </p>
     *
     * @param animalGroup The animal group to be deleted.
     * @throws DeleteException If an error occurs during the deletion.
     */
    @Override
    public void deleteAnimalGroup(AnimalGroup animalGroup) throws DeleteException {
        try {
            em.remove(em.merge(animalGroup));
        } catch (Exception e) {
            throw new DeleteException(e.getMessage());
        }
    }

    /**
     * Deletes an animal group by its ID.
     * <p>
     * This method finds the {@link AnimalGroup} by its ID, and if found, deletes it. If the animal group does not exist, a {@link DeleteException} is thrown.
     * </p>
     *
     * @param id The ID of the animal group to be deleted.
     * @throws DeleteException If an error occurs during the deletion.
     */
    @Override
    public void deleteAnimalGroupById(Long id) throws DeleteException {
        try {
            LOGGER.log(Level.INFO, "Attempting to delete AnimalGroup with ID: {0}", id);

            AnimalGroup animalGroup = em.find(AnimalGroup.class, id);
            if (animalGroup == null) {
                LOGGER.log(Level.WARNING, "AnimalGroup with ID {0} not found.", id);
                throw new DeleteException("Animal Group with ID " + id + " not found.");
            }
            em.remove(animalGroup);
            LOGGER.log(Level.INFO, "AnimalGroup with ID {0} successfully deleted.", id);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error occurred while deleting AnimalGroup with ID: " + id, e);
            throw new DeleteException(e.getMessage());
        }
    }

    /**
     * Retrieves a list of all animal groups.
     * <p>
     * This method returns all the animal groups from the database. If an error occurs during the retrieval, a {@link ReadException} is thrown.
     * </p>
     *
     * @return A list of all animal groups.
     * @throws ReadException If an error occurs while retrieving the animal groups.
     */
    @Override
    public List<AnimalGroup> getAnimalGroups() throws ReadException {
        try {
            return em.createNamedQuery("getAnimalGroups", AnimalGroup.class)
                    .getResultList();
        } catch (Exception e) {
            throw new ReadException("Error retrieving animal groups. Details: " + e.getMessage());
        }
    }

    /**
     * Retrieves a list of animal groups managed by a specific manager.
     * <p>
     * This method returns all animal groups associated with a given manager's ID. If an error occurs during the retrieval, a {@link ReadException} is thrown.
     * </p>
     *
     * @param managerId The ID of the manager whose animal groups are to be retrieved.
     * @return A list of animal groups managed by the specified manager.
     * @throws ReadException If an error occurs while retrieving the animal groups.
     */
    @Override
    public List<AnimalGroup> getAnimalGroupsByManager(Long managerId) throws ReadException {
        try {
            return em.createNamedQuery("getAnimalGroupsByManager", AnimalGroup.class)
                    .setParameter("managerId", managerId)
                    .getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
    }

    /**
     * Retrieves a list of animal groups filtered by name and manager ID.
     * <p>
     * This method returns animal groups that match the given group name and manager ID. If an error occurs during the retrieval, a {@link ReadException} is thrown.
     * </p>
     *
     * @param groupName The name of the animal group to search for.
     * @param managerId The ID of the manager to filter the results.
     * @return A list of animal groups matching the criteria.
     * @throws ReadException If an error occurs while retrieving the animal groups.
     */
    @Override
    public List<AnimalGroup> getAnimalGroupByName(String groupName, Long managerId) throws ReadException {
        try {
            return em.createNamedQuery("getAnimalGroupsByName", AnimalGroup.class)
                    .setParameter("name", "%" + groupName + "%")
                    .setParameter("managerId", managerId)
                    .getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
    }

}
