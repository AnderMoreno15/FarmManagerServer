/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Animal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Aitziber
 */
@Stateless
public class AnimalFacade implements IAnimalFacade {

    @PersistenceContext(unitName = "ServerSidePU")
    private EntityManager em;

    public List<Animal> findAll() {
      return em.createQuery("select a from Animal a").getResultList();
    }

    public List<Animal> findByName(String name) {
      return em.createQuery("select a from Animal a where UPPER(a.name) LIKE :animalName").
                             setParameter("animalName", "%" + name.toUpperCase() + "%").getResultList();
    }

    @Override
    public void createAnimal(Animal animal) throws CreateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateAnimal(Animal animal) throws UpdateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeAnimal(Animal animal) throws RemoveException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Animal findAnimalById(Long id) throws ReadException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Animal> findAllAnimals() throws ReadException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Animal findAnimalBySubespecies(String subespecies) throws ReadException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
