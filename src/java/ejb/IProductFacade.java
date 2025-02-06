/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.ProductEntity;
import entities.ProviderEntity;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author inifr
 */
@Local
public interface IProductFacade {
    public void createProduct(ProductEntity product) throws CreateException;
    public void updateProduct(ProductEntity product) throws UpdateException;
    public void deleteProductById(Long id) throws DeleteException;
    public ProductEntity getProductByName(String name) throws ReadException;
    public List<ProductEntity> getProductByCreatedDate(Date createdDate) throws ReadException;
    public ProviderEntity findProviderById(Long id);
    public List<ProductEntity> findAll() throws ReadException;
}
