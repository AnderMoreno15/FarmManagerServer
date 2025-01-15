/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.AnimalGroup;
import entities.FarmManager;
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
public interface IAnimalGroupEjb {

    public void setAnimalGroup(AnimalGroup animalGroup) throws CreateException;

    public void updateAnimalGroup(AnimalGroup animalGroup) throws UpdateException;

    public void deleteAnimalGroup(AnimalGroup animalGroup) throws DeleteException;

    // Extra
    // 
    public List<AnimalGroup> getAnimalGroupsByManager(FarmManager manager) throws ReadException;
    
    // For testing purposes (done)
    public List<AnimalGroup> getAnimalGroups() throws ReadException;

    // Does't return a List because there can be just one with the same name (done)
    public AnimalGroup getAnimalGroupByName(String groupName, Long managerId) throws ReadException;
    
    // done
    public void deleteAnimalGroupById(Long id) throws DeleteException;

    // This methods should be done in IAnimalFacade and IConsumeFacade
    //public List<Consume> getConsumesByAnimalGroup(Long id) throws ReadException; 
    //public List<Animal> getAnimalsByAnimalGroup(String name) throws ReadException;
}
