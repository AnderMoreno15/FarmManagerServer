/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Animal;
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
 * @author Aitziber
 */
@Stateless
public class AnimalFacade implements IAnimalFacade {

    @PersistenceContext(unitName = "ServerSidePU")
    private EntityManager em;

//    public List<Animal> findAll() {
//      return em.createQuery("select a from Animal a").getResultList();
//    }
//
//    public List<Animal> findByName(String name) {
//      return em.createQuery("select a from Animal a where UPPER(a.name) LIKE :animalName").
//                             setParameter("animalName", "%" + name.toUpperCase() + "%").getResultList();
//    }

    @Override
    public void createAnimal(Animal animal) throws CreateException {
        try{
            em.persist(animal);
        }catch(Exception e){
            throw new CreateException(e.getMessage());
        }
    }

    @Override
    public void updateAnimal(Animal animal) throws UpdateException {
        try{
            if(!em.contains(animal))
                em.merge(animal);
            em.flush();
        }catch(Exception e){
            throw new UpdateException(e.getMessage());
        }
    }

    @Override
    public void removeAnimal(Animal animal) throws DeleteException {
        try{
            em.remove(em.merge(animal));
        }catch(Exception e){
            throw new DeleteException(e.getMessage());
        }
    }

    @Override
    public Animal findAnimalById(Long id) throws ReadException {
        Animal animal;
        try{
            animal=em.find(Animal.class, id);
        }catch(Exception e){
            throw new ReadException(e.getMessage());
        }
        return animal;
    }

    @Override
    public List<Animal> findAllAnimals() throws ReadException {
        List<Animal> animals;
        try{
            animals=em.createNamedQuery("findAllAccounts").getResultList();
        }catch(Exception e){
            throw new ReadException(e.getMessage());
        }
        return animals;
    }

    @Override
    public List<Animal> findAnimalsBySubespecies(String subespecies) throws ReadException {
        List<Animal> animals;
        try {
            animals = em.createNamedQuery("findAnimalsBySubespecies", Animal.class)
                        .setParameter("subespecies", subespecies)
                        .getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return animals;
    }

    @Override
    public List<Animal> findAllAnimalsByAnimalGroup(Long animal_group_id) throws ReadException {
        List<Animal> animals;
        try {
             animals=em.createNamedQuery("findAnimalsByAnimalGroup", Animal.class)
                        .setParameter("animalGroup", em.find(AnimalGroup.class, animal_group_id))
                        .getResultList();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
        return animals;
    }
}
