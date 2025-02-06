/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.animal;

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

    @Override
    public List<Animal> getAllAnimals(Long managerId) throws ReadException {
        List<Animal> animals;
        try{
            animals = em.createNamedQuery("getAllAnimals", Animal.class)
                        .setParameter("managerId", managerId)
                        .getResultList();
        }catch(Exception e){
            throw new ReadException("Error retrieving animals for manager with ID: " + managerId + ". Details: " + e.getMessage());
        }
        return animals;
    }
    
    @Override
    public Animal getAnimalByName(String name, Long managerId) throws ReadException {
        try{
            return em.createNamedQuery("getAnimalByName", Animal.class)
                    .setParameter("name", name)
                    .setParameter("managerId", managerId)
                    .getSingleResult();
               
        }catch(Exception e){
            throw new ReadException(e.getMessage());
        }
    }

    @Override
    public List<Animal> getAnimalsByAnimalGroup(String animalGroupName, Long managerId) throws ReadException {
        List<Animal> animals;
        try{
            animals = em.createNamedQuery("getAnimalsByAnimalGroup", Animal.class)
                        .setParameter("animalGroupName", animalGroupName)
                        .setParameter("managerId", managerId)
                        .getResultList();
        }catch(Exception e){
            throw new ReadException("Error retrieving animals for Group: " + animalGroupName + ". Details: " + e.getMessage());
        }
        return animals;
    }

    @Override
    public List<Animal> getAnimalsBySubespecies(String subespecies, Long managerId) throws ReadException {
        List<Animal> animals;
        try {
            animals = em.createNamedQuery("getAnimalsBySubespecies", Animal.class)
                        .setParameter("subespecies", subespecies)
                        .setParameter("managerId", managerId)
                        .getResultList();
        } catch (Exception e) {
            throw new ReadException("Error retrieving animals for Subespecies: " + subespecies + ". Details: " + e.getMessage());
        }
        return animals;
    }

    
    @Override
    public List<Animal> getAnimalsByBirthdate(Date dateFrom, Date dateTo, Long managerId) throws ReadException {
        List<Animal> animals;
        try {
            animals = em.createNamedQuery("getAnimalsByBirthdateRange", Animal.class)
                        .setParameter("dateFrom", dateFrom)
                        .setParameter("dateTo", dateTo)
                        .setParameter("managerId", managerId)
                        .getResultList();
        } catch (Exception e) {
            throw new ReadException("Error retrieving animals for the date range. Details: " + e.getMessage());
        }
        return animals;
    }

    @Override
    public List<Animal> getAnimalsByBirthdateFrom(Date dateFrom, Long managerId) throws ReadException {
        List<Animal> animals;
        try {
            animals = em.createNamedQuery("getAnimalsByBirthdateFrom", Animal.class)
                        .setParameter("dateFrom", dateFrom)
                        .setParameter("managerId", managerId)
                        .getResultList();
        } catch (Exception e) {
            throw new ReadException("Error retrieving animals born from date: " + dateFrom + ". Details: " + e.getMessage());
        }
        return animals;
    }

    @Override
    public List<Animal> getAnimalsByBirthdateTo(Date dateTo, Long managerId) throws ReadException {
        List<Animal> animals;
        try {
            animals = em.createNamedQuery("getAnimalsByBirthdateTo", Animal.class)
                        .setParameter("dateTo", dateTo)
                        .setParameter("managerId", managerId)
                        .getResultList();
        } catch (Exception e) {
            throw new ReadException("Error retrieving animals born until date: " + dateTo + ". Details: " + e.getMessage());
        }
        return animals;
    }
}
