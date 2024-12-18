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
    public void deleteAnimal(Animal animal) throws DeleteException;
    
    public Animal getAnimalByName(String name) throws ReadException;
    public List<Animal> getAllAnimals(String clientId) throws ReadException;
    public List<Animal> getAnimalsByAnimalGroup(AnimalGroup animalGroup);
    public List<Animal> getAnimalsBySubespecies(String subespecies);
    public List<Animal> getAnimalsByBirthdate(Date dateFrom, Date dateTo) throws ReadException;
    public List<Animal> getAnimalsByBirthdateFrom(Date dateFrom) throws ReadException;
    public List<Animal> getAnimalsByBirthdateTo(Date dateTo) throws ReadException;

    //estos métodos existirán en el cliente pero llamarán a createAnimal y a deleteAnimal(las veces que sea)
    //createDefaultAnimal()
    //deleteAnimals(lista)    
}
