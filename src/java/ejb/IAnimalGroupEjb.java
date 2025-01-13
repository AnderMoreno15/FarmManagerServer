/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.AnimalGroup;
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
public interface IAnimalGroupEjb {

    public void createAnimalGroup(AnimalGroup animalGroup) throws CreateException;

    public void updateAnimalGroup(AnimalGroup animalGroup) throws UpdateException;

    public void deleteAnimalGroup(AnimalGroup animalGroup) throws DeleteException;

//    // Gets
    public List<AnimalGroup> getAnimalGroupsByManager(ManagerEntity manager) throws ReadException;

    public AnimalGroup getAnimalGroupsByName(String name) throws ReadException;
//
//    // This methods are done int IAnimalFecade and IConsumeFecade
//    public int getConsumesByAnimalGroup(AnimalGroup animalGroup) throws ReadException;
//    
//    public int getAnimalsByAnimalGroup(AnimalGroup animalGroup) throws ReadException;
}
