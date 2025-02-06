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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity representing a manager in the farm management system.
 * <p>
 * This class extends {@link UserEntity} and represents a manager of animal groups. 
 * It contains information about the manager's activity status, password, and the animal groups 
 * they manage. The class is also mapped for database persistence and includes relevant 
 * named queries to retrieve managers by different parameters such as email.
 * </p>
 * 
 * @author Ander
 */
@Entity
@DiscriminatorValue("manager")
@NamedQueries({
    @NamedQuery(
            name = "getManagers",
            query = "SELECT mg FROM Manager mg"
    ),
    @NamedQuery(
            name = "getManagerByEmail",
            query = "SELECT mg FROM Manager mg WHERE mg.email = :email"
    ),
    @NamedQuery(
            name = "getManager",
            query = "SELECT mg FROM Manager mg WHERE mg.email = :email AND mg.password = :password"
    ),
})
@XmlRootElement
public class Manager extends UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private boolean isActive;

    @NotNull
    private String password;

    @ManyToMany(mappedBy = "managers", fetch = FetchType.EAGER)
    private List<AnimalGroup> animalGroups;

    /**
     * Returns the unique identifier of the manager.
     * 
     * @return The unique identifier (ID) of the manager.
     */
    @Override
    public Long getId() {
        return super.getId();
    }

    /**
     * Sets the unique identifier of the manager.
     * 
     * @param id The new unique identifier to set.
     */
    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    /**
     * Returns the active status of the manager.
     * 
     * @return True if the manager is active, false otherwise.
     */
    public boolean isIsActive() {
        return isActive;
    }

    /**
     * Sets the active status of the manager.
     * 
     * @param isActive True if the manager should be active, false otherwise.
     */
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * Returns the password of the manager.
     * 
     * @return The password of the manager.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the manager.
     * 
     * @param password The new password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns a list of animal groups managed by the manager.
     * <p>
     * This method is marked with {@link XmlTransient} to exclude the list of animal groups 
     * from XML serialization.
     * </p>
     * 
     * @return A list of animal groups managed by the manager.
     */
    @XmlTransient
    public List<AnimalGroup> getAnimalGroups() {
        return animalGroups;
    }

    /**
     * Sets the list of animal groups managed by the manager.
     * 
     * @param animalGroups The list of animal groups to set.
     */
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
        if (!(object instanceof Manager)) {
            return false;
        }
        Manager other = (Manager) object;
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
