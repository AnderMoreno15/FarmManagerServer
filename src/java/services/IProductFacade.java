/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.ProductEntity;
import entities.ProductProviderEntity;
import entities.ProviderEntity;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author inifr
 */
@Local
public interface IProductFacade {
    public void createProduct(ProductEntity product);
    public void updateProduct(ProductEntity product);
    public void deleteProductById(Long id);
    public ProductEntity getProductByName(String name);
    public List<ProductEntity> getProductByCreatedDate(Date createdDate);    
}
