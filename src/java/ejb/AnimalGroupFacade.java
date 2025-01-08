/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.AnimalGroupEntity;
import entities.ManagerEntity;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Ander
 */
@Stateless
public class AnimalGroupFacade implements IAnimalGroupFacade {

    @PersistenceContext(unitName = "FarmManagerPU")
    private EntityManager em;

    @Override
    public void createAnimal(AnimalGroupEntity animalGroup) throws CreateException {
        try {
            em.persist(animalGroup);
        } catch (Exception e) {
            throw new CreateException(e.getMessage());
        }
    }

    @Override
    public void updateAnimal(AnimalGroupEntity animalGroup) throws UpdateException {
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
    public void deleteAnimal(AnimalGroupEntity animalGroup) throws DeleteException {
        try {
            em.remove(em.merge(animalGroup));
        } catch (Exception e) {
            throw new DeleteException(e.getMessage());
        }
    }

//    @Override
//    public List<AnimalGroupEntity> getAnimalGroups(ManagerEntity manager) throws ReadException {
//        try {
//            return em.createNamedQuery("getAnimalGroups", AnimalGroupEntity.class)
//                    .setParameter("managerId", manager.getId())
//                    .getResultList();
//        } catch (Exception e) {
//            throw new ReadException("Error retrieving animal groups for manager ID: " + manager.getId() + ". Details: " + e.getMessage());
//        }
//    }
//
//    @Override
//    public AnimalGroupEntity getAnimalGroupsByName(String name) throws ReadException {
//        try {
//            return em.find(AnimalGroupEntity.class, name);
//        } catch (Exception e) {
//            throw new ReadException(e.getMessage());
//        }
//    }
// This methods are done int AnimalFecade and ConsumeFecade
//
//    @Override
//    public int getConsumesByAnimalGroup(AnimalGroupEntity animalGroup) throws ReadException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public int getAnimalsByAnimalGroup(AnimalGroupEntity animalGroup) throws ReadException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

}
