/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.animal;

import entities.Animal;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Aitziber
 */
@Local
public interface IAnimalFacade {

    public void createAnimal(Animal animal) throws CreateException;
    public void updateAnimal(Animal animal) throws UpdateException;
    public void deleteAnimalById(Long id) throws DeleteException;
    
    public List<Animal> getAllAnimals(Long managerId) throws ReadException;
    public Animal getAnimalByName(String name, Long managerId) throws ReadException;
    public List<Animal> getAnimalsByAnimalGroup(String animalGroupName, Long managerId) throws ReadException;
    public List<Animal> getAnimalsBySubespecies(String subespecies, Long managerId) throws ReadException;
    public List<Animal> getAnimalsByBirthdate(Date dateFrom, Date dateTo, Long managerId) throws ReadException;
    public List<Animal> getAnimalsByBirthdateFrom(Date dateFrom, Long managerId) throws ReadException;
    public List<Animal> getAnimalsByBirthdateTo(Date dateTo, Long managerId) throws ReadException;
   
}
