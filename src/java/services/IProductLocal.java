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
public interface IProductLocal {
    public void createProduct(ProductEntity product);
    public void updateProduct(ProductEntity product);
    public void deleteProduct(ProductEntity product);
    public ProductEntity findProductByName(String name);
    public ProductEntity findProductByCreatedDate(Date createdDate);
    public List<ProductEntity> findAllProducts();
    
}
