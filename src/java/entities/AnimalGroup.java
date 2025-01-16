/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author Usuario
 */

@Entity
@Table(name="animalGroup",schema="farmdb")
@XmlRootElement
public class AnimalGroup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private Long animalGroupId;
    
    
    
    @OneToMany(cascade = ALL, mappedBy = "animalGroup")
    private List<Consumes> consumes;
    
    public Long getAnimalGroupId() {
        return animalGroupId;
    }

    public void setAnimalGroupId(Long id) {
        this.animalGroupId = id;
    }
    
}
