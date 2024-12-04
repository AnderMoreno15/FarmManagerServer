/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Animal;
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
    public void removeAnimal(Animal animal) throws RemoveException;
    public Animal findAnimalById(Long id) throws ReadException;
    
    public List<Animal> findAllAnimals() throws ReadException;
    public Animal findAnimalBySubespecies(String subespecies) throws ReadException;
    
}
