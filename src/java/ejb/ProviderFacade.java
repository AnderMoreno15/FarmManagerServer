/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.ProviderEntity;
import exceptions.CreateException;
import exceptions.ReadException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 *
 * @author inifr
 */
@Stateless
public class ProviderFacade implements IProviderFacade {

    @PersistenceContext(unitName = "FarmManagerPU")
    private EntityManager em;

    public void create(ProviderEntity provider) throws CreateException{
        try{
            em.persist(provider);
        }catch(Exception e){
            throw new CreateException(e.getMessage());
        }    
    }

    public ProviderEntity getProviderById(Long id) throws ReadException{
        try{
                return em.createNamedQuery("getProviderById", ProviderEntity.class)
                .setParameter("id", id)
                .getSingleResult();
        }catch (Exception e){
            throw new ReadException(e.getMessage());
        }
    }
    
}
