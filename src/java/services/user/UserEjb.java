/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.user;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Ander
 */
@Stateless
public class UserEjb implements IUserEjb {

    @PersistenceContext(unitName = "FarmManagerPU")
    private EntityManager em;

//    @Override
//    public void setUser(UserEntity user) throws CreateException {
//        try {
//            em.persist(user);
//        } catch (Exception e) {
//            throw new CreateException(e.getMessage());
//        }
//    }
//
//    @Override
//    public UserEntity getUser(String email) throws ReadException {
//        try {
//            return em.createNamedQuery("getUser", UserEntity.class)
//                    .setParameter("email", email)
//                    .getSingleResult();
//        } catch (Exception e) {
//            throw new ReadException(e.getMessage());
//        }
//    }
}
