/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.ProviderEntity;
import exceptions.ReadException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author InigoFreire
 */
@Stateless
public class ProviderFacade implements IProviderFacade{
    
    @PersistenceContext(unitName = "FarmManagerPU")
    private EntityManager em;
     
    @Override
    public List<ProviderEntity> getAllProviders() throws ReadException {
        List<ProviderEntity> providers;
        try{
            providers = em.createNamedQuery("getAllProviders", ProviderEntity.class)
                .getResultList();
        }catch(Exception e){
            throw new ReadException("Error retrieving species. Details: " + e.getMessage());
        }
        return providers;
    }
    
}
