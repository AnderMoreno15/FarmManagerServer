/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Species;
import exceptions.ReadException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Aitziber
 */
public class SpeciesFacade implements ISpeciesFacade{
    
    @PersistenceContext(unitName = "FarmManagerPU")
    private EntityManager em;
     
    @Override
    public List<Species> getAllSpecies() throws ReadException {
        List<Species> species;
        try{
            species = em.createNamedQuery("getAllSpecies", Species.class)
                .getResultList();
        }catch(Exception e){
            throw new ReadException("Error retrieving species. Details: " + e.getMessage());
        }
        return species;
    }
    
}
