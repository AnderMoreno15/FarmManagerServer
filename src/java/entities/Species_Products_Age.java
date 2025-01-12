/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Aitziber
 */
@Entity
@Table(name = "species_products_age", schema = "farmdb")
public class Species_Products_Age implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ID_Species_Products_Age id;
    
    @NotNull
    private int age;
    @NotNull
    private float amount;

    public ID_Species_Products_Age getId() {
        return id;
    }

    public void setId(ID_Species_Products_Age id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
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
        if (!(object instanceof Species_Products_Age)) {
            return false;
        }
        Species_Products_Age other = (Species_Products_Age) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Species_Products_Age[ id=" + id + " ]";
    }
    
}
