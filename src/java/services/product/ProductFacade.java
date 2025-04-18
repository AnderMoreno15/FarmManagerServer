/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.product;

import entities.ProductEntity;
import entities.ProviderEntity;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author InigoFreire
 */
@Stateless
public class ProductFacade implements IProductFacade {

    @PersistenceContext(unitName = "FarmManagerPU")
    private EntityManager em;

    @Override
    public void createProduct(ProductEntity product) throws CreateException {
        try {
            em.persist(product);
        } catch (Exception e) {
            throw new CreateException(e.getMessage());
        }
    }

    @Override
    public void updateProduct(ProductEntity product) throws UpdateException {
        try {
            if (!em.contains(product)) {
                em.merge(product);
            }
            em.flush();
        } catch (Exception e) {
            throw new UpdateException(e.getMessage());
        }
    }

    @Override
    public void deleteProductById(Long id) throws DeleteException {
        try {
            ProductEntity product = em.find(ProductEntity.class, id);
            if (product == null) {
                throw new DeleteException("Product with ID " + id + " not found.");
            }
            em.remove(product);
        } catch (Exception e) {
            throw new DeleteException(e.getMessage());
        }
    }

    @Override
    public ProductEntity getProductByName(String name) throws ReadException {
        try {
            return em.createNamedQuery("getProductByName", ProductEntity.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
    }

    @Override
    public List<ProductEntity> getProductByCreatedDate(Date createdDate) throws ReadException {
        List<ProductEntity> products;
        try {
            products = em.createNamedQuery("getProductByCreatedDate", ProductEntity.class)
                    .setParameter("date", createdDate)
                    .getResultList();

        } catch (Exception e) {
            throw new ReadException("Error retrieving products for the date range. Details: " + e.getMessage());
        }
        return products;
    }

    public ProviderEntity findProviderById(Long id) {
        return em.find(ProviderEntity.class, id);
    }

    @Override
    public List<ProductEntity> findAll() throws ReadException {
        try {
            return em.createQuery("SELECT p FROM ProductEntity p", ProductEntity.class)
                    .getResultList();
        } catch (Exception e) {
            throw new ReadException("Error retrieving all products: " + e.getMessage());
        }
    }

}
