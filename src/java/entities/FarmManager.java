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

/**
 *
 * @author Ander
 */
@Entity
@DiscriminatorValue("farmManager")
public class FarmManager extends FarmUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @NotNull
    private boolean isActive;
    @NotNull
    private String password;
    //@ManyToOne
    //private List<PurchaseEntity> purchases;
    @ManyToMany(mappedBy = "managers", fetch = FetchType.EAGER)
    private List<AnimalGroup> animalGroups;

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

    public List<AnimalGroup> getAnimalGroups() {
        return animalGroups;
    }

    public void setAnimalGroups(List<AnimalGroup> animalGroups) {
        this.animalGroups = animalGroups;
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
        if (!(object instanceof FarmManager)) {
            return false;
        }
        FarmManager other = (FarmManager) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Manager[ id=" + id + " ]";
    }

}
