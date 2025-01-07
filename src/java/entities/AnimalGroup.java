/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Usuario
 */

@Entity
@Table(schema="farmdb",name="AnimalGroup")
@XmlRootElement
public class AnimalGroup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long animalGroupId;

    public Long getId() {
        return animalGroupId;
    }

    public void setId(Long id) {
        this.animalGroupId = id;
    }
    
}
