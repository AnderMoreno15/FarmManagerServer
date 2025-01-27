/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.Manager;
import exceptions.CreateException;
import exceptions.ReadException;
import exceptions.UpdateException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Ander
 */
@Stateless
public class ManagerEjb implements IManagerEjb {

    @PersistenceContext(unitName = "FarmManagerPU")
    private EntityManager em;

    @Override
    public void setManager(Manager manager) throws CreateException {
        try {
            em.persist(manager);
        } catch (Exception e) {
            throw new CreateException(e.getMessage());
        }
    }
    
    @Override
    public List<Manager> getManagers() throws ReadException {
        try {
            return em.createNamedQuery("getManagers", Manager.class)
                    .getResultList();
        } catch (Exception e) {
            throw new ReadException("Error retrieving managers. Details: " + e.getMessage());
        }
    }

    @Override
    public Manager getManager(String email, String password) throws ReadException {
        try {
            return (Manager) em.createNamedQuery("getManager", Manager.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
    }
    
    @Override
    public Manager getManagerByEmail(String email) throws ReadException {
        try {
            return (Manager) em.createNamedQuery("getManagerByEmail", Manager.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
    }

    @Override
    public void updateManager(Manager manager) throws UpdateException {
        try {
            if (!em.contains(manager)) {
                em.merge(manager);
            }
            em.flush();
        } catch (Exception e) {
            throw new UpdateException(e.getMessage());
        }
    }

}
