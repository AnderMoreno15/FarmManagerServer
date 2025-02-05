/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.animalGroup;

import entities.AnimalGroup;
import entities.Manager;
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
 *
 * @author Ander
 */
@Stateless
public class AnimalGroupEjb implements IAnimalGroupEjb {

    private static final Logger LOGGER = Logger.getLogger(AnimalGroupEjb.class.getName());

    @PersistenceContext(unitName = "FarmManagerPU")
    private EntityManager em;

    @Override
    public void setAnimalGroup(AnimalGroup animalGroup) throws CreateException {
        try {
            em.persist(animalGroup);
        } catch (Exception e) {
            throw new CreateException(e.getMessage());
        }
    }

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

    @Override
    public void deleteAnimalGroup(AnimalGroup animalGroup) throws DeleteException {
        try {
            em.remove(em.merge(animalGroup));
        } catch (Exception e) {
            throw new DeleteException(e.getMessage());
        }
    }

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

    @Override
    public List<AnimalGroup> getAnimalGroups() throws ReadException {
        try {
            return em.createNamedQuery("getAnimalGroups", AnimalGroup.class)
                    .getResultList();
        } catch (Exception e) {
            throw new ReadException("Error retrieving animal groups. Details: " + e.getMessage());
        }
    }

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
