/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Animal;
import entities.AnimalGroup;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;

/**
 *
 * @author Aitziber
 */
@Stateless
public class AnimalFacade implements IAnimalFacade {

    @PersistenceContext(unitName = "FarmManagerPU")
    private EntityManager em;


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
    public void deleteAnimal(Animal animal) throws DeleteException {
        try{
            em.remove(em.merge(animal));
        }catch(Exception e){
            throw new DeleteException(e.getMessage());
        }
    }
    
    @Override
    public void deleteAnimalById(Long id) throws DeleteException {
        try {
            Animal animal = em.find(Animal.class, id);
            if (animal == null) {
                throw new DeleteException("Animal with ID " + id + " not found.");
            }
            em.remove(animal);
        }catch(Exception e){
            throw new DeleteException(e.getMessage());
        }
    }
    
//    //get
//
//    @Override
//    public List<Animal> getAllAnimals(Long clientId) throws ReadException {
//        try{
//            return em.createNamedQuery("getAllAnimals", Animal.class)
//                .setParameter("clientId", clientId)
//                .getResultList();
//        }catch(Exception e){
//            throw new ReadException("Error retrieving animals for client ID: " + clientId + ". Details: " + e.getMessage());
//        }
//    }
    
    @Override
    public Animal getAnimalByName(String name) throws ReadException {
        try{
            return em.createNamedQuery("getAnimalByName", Animal.class)
                .setParameter("name", name)
                .getSingleResult();
               
        }catch(Exception e){
            throw new ReadException(e.getMessage());
        }
    }

    @Override
    public List<Animal> getAnimalsByAnimalGroup(String animalGroupName) throws ReadException {
        List<Animal> animals;
        try{
            animals = em.createNamedQuery("getAnimalsByAnimalGroup", Animal.class)
                .setParameter("animalGroupName", animalGroupName)
                .getResultList();
        }catch(Exception e){
            throw new ReadException("Error retrieving animals for Group: " + animalGroupName + ". Details: " + e.getMessage());
        }
        return animals;
    }

    @Override
    public List<Animal> getAnimalsBySubespecies(String subespecies) throws ReadException {
        List<Animal> animals;
        try {
            animals = em.createNamedQuery("getAnimalsBySubespecies", Animal.class)
                        .setParameter("subespecies", subespecies)
                        .getResultList();
        } catch (Exception e) {
            throw new ReadException("Error retrieving animals for Subespecies: " + subespecies + ". Details: " + e.getMessage());
        }
        return animals;
    }

    
    @Override
    public List<Animal> getAnimalsByBirthdate(Date dateFrom, Date dateTo) throws ReadException {
        List<Animal> animals;
        try {
            animals = em.createNamedQuery("getAnimalsByBirthdateRange", Animal.class)
                        .setParameter("dateFrom", dateFrom)
                        .setParameter("dateTo", dateTo)
                        .getResultList();
        } catch (Exception e) {
            throw new ReadException("Error retrieving animals for the date range. Details: " + e.getMessage());
        }
        return animals;
    }

    @Override
    public List<Animal> getAnimalsByBirthdateFrom(Date dateFrom) throws ReadException {
        List<Animal> animals;
        try {
            animals = em.createNamedQuery("getAnimalsByBirthdateFrom", Animal.class)
                        .setParameter("dateFrom", dateFrom)
                        .getResultList();
        } catch (Exception e) {
            throw new ReadException("Error retrieving animals born from date: " + dateFrom + ". Details: " + e.getMessage());
        }
        return animals;
    }

    @Override
    public List<Animal> getAnimalsByBirthdateTo(Date dateTo) throws ReadException {
        List<Animal> animals;
        try {
            animals = em.createNamedQuery("getAnimalsByBirthdateTo", Animal.class)
                        .setParameter("dateTo", dateTo)
                        .getResultList();
        } catch (Exception e) {
            throw new ReadException("Error retrieving animals born until date: " + dateTo + ". Details: " + e.getMessage());
        }
        return animals;
    }
}
