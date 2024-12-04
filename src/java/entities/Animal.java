/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Aitziber
 */
@Entity
@Table(name="animal",schema="farmdb")
public class Animal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name;
    private String subespecies;
    
    @Temporal(TemporalType.TIMESTAMP)
//    @JsonSerialize(as = Date.class)
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthDate;
    
    private float monthlyConsume;
    
    @ManyToOne
    @JoinColumn(name="animal_group_id")
    private AnimalGroup animalGroup;
    
    @ManyToOne
    @JoinColumn(name="species_id")
    private Species species;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubespecies() {
        return subespecies;
    }

    public void setSubespecies(String subespecies) {
        this.subespecies = subespecies;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public float getMonthlyConsume() {
        return monthlyConsume;
    }

    public void setMonthlyConsume(float monthlyConsume) {
        this.monthlyConsume = monthlyConsume;
    }

    public AnimalGroup getAnimalGroup() {
        return animalGroup;
    }

    public void setAnimalGroup(AnimalGroup animalGroup) {
        this.animalGroup = animalGroup;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

   
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Animal)) {
            return false;
        }
        Animal other = (Animal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Animal[ id=" + id + " ]";
    }
    
}
