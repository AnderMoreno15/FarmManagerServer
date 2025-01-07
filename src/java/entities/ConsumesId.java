/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author Pablo
 */
@Embeddable
public class ConsumesId implements Serializable{
    
    private long productsId;
    private long animalGroupId;
    

    public long getProductsId() {
        return productsId;
    }

    public long getAnimalGroupId() {
        return animalGroupId;
    }

    public void setProductsId(long productsId) {
        this.productsId = productsId;
    }

    public void setAnimalGroupId(long animalGroupId) {
        this.animalGroupId = animalGroupId;
    }

    public ConsumesId() {
    }
    
    
    
   
    
}
