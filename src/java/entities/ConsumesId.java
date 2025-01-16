/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Pablo
 */
@Embeddable
public class ConsumesId implements Serializable {
    
     @Column(name = "animalGroup_animalGroupId")
    private Long animalGroupId;
     @Column(name = "product_productId") 
    private Long productId;

    
    
    //private Long productId;
    
    // Constructor vacío necesario
    public ConsumesId() {
    }
    
    // Constructor con parámetros
    public ConsumesId(Long productId, Long animalGroupId) {
        //this.productId = productId;
        
        this.animalGroupId = animalGroupId;
        this.productId= productId;
    }
    
    // Getters y setters con nombres que coincidan exactamente
    public Long getAnimalGroupId() {
        return animalGroupId;
    }
    
    public void setAnimalGroupId(Long animalGroupId) {
        this.animalGroupId = animalGroupId;
    }
    public void setProductId(Long productId) {
        this.productId =productId;
    }

    public Long getProductId() {
        return productId;
    }
    
//    public Long getProductId() {
//        return productId;
//    }
    
//    public void setProductId(Long productId) {
//        this.productId = productId;
//    }
    
    // Equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConsumesId that = (ConsumesId) o;
        return Objects.equals(animalGroupId, that.animalGroupId) &&
               Objects.equals(productId, that.productId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(animalGroupId, productId);
    }
}

    
    
   
    

