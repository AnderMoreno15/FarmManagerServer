/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author Aitziber
 */
@Embeddable
class ID_Species_Products_Age implements Serializable {

    private static final long serialVersionUID = 1L;
            
    long productId;
    long speciesId;    
}
