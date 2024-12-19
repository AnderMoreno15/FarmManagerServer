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
import javax.ejb.Local;

/**
 *
 * @author Ander
 */
@Local
public interface IAnimalGroupFacade {

    public void createAnimal(AnimalGroupEntity animalGroup) throws CreateException;

    public void updateAnimal(AnimalGroupEntity animalGroup) throws UpdateException;

    public void deleteAnimal(AnimalGroupEntity animalGroup) throws DeleteException;

    // Gets
    public List<AnimalGroupEntity> getAnimalGroups(ManagerEntity manager) throws ReadException;

    public AnimalGroupEntity getAnimalGroupsByName(String name) throws ReadException;

    // This methods are done int IAnimalFecade and IConsumeFecade
    // public int getConsumesByAnimalGroup(AnimalGroupEntity animalGroup) throws ReadException;
    
    // public int getAnimalsByAnimalGroup(AnimalGroupEntity animalGroup) throws ReadException;
}
