/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ander
 */
@Entity
@Table(name = "manager", schema = "farmdb")
@DiscriminatorValue("manager")
@XmlRootElement
public class ManagerEntity extends UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @NotNull
    private boolean isActive;
    @NotNull
    private String password;
    //@ManyToOne
    //private List<PurchaseEntity> purchases;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<AnimalGroupEntity> animalGroup;

    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
//
//    public List<PurchaseEntity> getPurchases() {
//        return purchases;
//    }
//
//    public void setPurchases(List<PurchaseEntity> purchases) {
//        this.purchases = purchases;
//    }

    @XmlTransient
    public List<AnimalGroupEntity> getAnimalGroup() {
        return animalGroup;
    }

    public void setAnimalGroup(List<AnimalGroupEntity> animalGroup) {
        this.animalGroup = animalGroup;
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
        if (!(object instanceof ManagerEntity)) {
            return false;
        }
        ManagerEntity other = (ManagerEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.ManagerEntity[ id=" + id + " ]";
    }

}
