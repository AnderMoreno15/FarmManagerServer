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
    
    private int productsId;
    private int animalGroupId;
    

    public int getProductsId() {
        return productsId;
    }

    public int getAnimalGroupId() {
        return animalGroupId;
    }

    public void setProductsId(int productsId) {
        this.productsId = productsId;
    }

    public void setAnimalGroupId(int animalGroupId) {
        this.animalGroupId = animalGroupId;
    }

    public ConsumesId() {
    }
    
   
    
}
