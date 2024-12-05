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
import javax.ejb.Local;

/**
 *
 * @author Aitziber
 */
@Local
public interface IAnimalFacade {

    public void createAnimal(Animal animal) throws CreateException;
    public void updateAnimal(Animal animal) throws UpdateException;
    public void removeAnimal(Animal animal) throws DeleteException;
    public Animal findAnimalById(Long id) throws ReadException;
    
    public List<Animal> findAllAnimals() throws ReadException;
    public List<Animal> findAllAnimalsByAnimalGroup(Long animal_group_id) throws ReadException;
    public List<Animal> findAnimalsBySubespecies(String subespecies) throws ReadException;
    
}
