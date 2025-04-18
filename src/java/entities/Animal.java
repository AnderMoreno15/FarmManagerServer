/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Aitziber
 */
@Entity
@Table(name="animal", schema="farmdb")
@NamedQueries({
    @NamedQuery(name = "getAllAnimals", 
                query = "SELECT a FROM Animal a JOIN a.animalGroup ag JOIN ag.managers m WHERE m.id = :managerId ORDER BY a.name DESC"),

    @NamedQuery(name = "getAnimalByName", 
                query = "SELECT a FROM Animal a JOIN a.animalGroup ag JOIN ag.managers m WHERE a.name = :name AND m.id = :managerId"),

    @NamedQuery(name = "getAnimalsByAnimalGroup", 
                query = "SELECT a FROM Animal a JOIN a.animalGroup ag JOIN ag.managers m WHERE ag.name = :animalGroupName AND m.id = :managerId ORDER BY a.name DESC"),

    @NamedQuery(name = "getAnimalsBySubespecies", 
                query = "SELECT a FROM Animal a JOIN a.animalGroup ag JOIN ag.managers m WHERE a.subespecies = :subespecies AND m.id = :managerId ORDER BY a.name DESC"),

    @NamedQuery(name = "getAnimalsByBirthdateRange", 
                query = "SELECT a FROM Animal a JOIN a.animalGroup ag JOIN ag.managers m WHERE a.birthdate BETWEEN :dateFrom AND :dateTo AND m.id = :managerId ORDER BY a.birthdate"),

    @NamedQuery(name = "getAnimalsByBirthdateFrom", 
                query = "SELECT a FROM Animal a JOIN a.animalGroup ag JOIN ag.managers m WHERE a.birthdate >= :dateFrom AND m.id = :managerId ORDER BY a.birthdate"),

    @NamedQuery(name = "getAnimalsByBirthdateTo", 
                query = "SELECT a FROM Animal a JOIN a.animalGroup ag JOIN ag.managers m WHERE a.birthdate <= :dateTo AND m.id = :managerId ORDER BY a.birthdate")
})

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Animal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotNull
    private String name;
    
    @NotNull
    private String subespecies;
    
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(as=Date.class)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ssXXX")
    @NotNull
    private Date birthdate;
    
    private float monthlyConsume;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="animal_group_id")
    private AnimalGroup animalGroup;
    
    @ManyToOne
    @JoinColumn(name="species_id")
    private Species species;

    
    public Animal(){
    }
  
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
   
    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
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
