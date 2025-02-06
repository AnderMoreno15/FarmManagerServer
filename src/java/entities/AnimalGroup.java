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
import java.util.List;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/*
 * Entity representing an animal group in the farm management system.
 * <p>
 * This class represents a group of animals within the system, containing information such as 
 * the group's name, area, description, creation date, associated animals, consumption data, 
 * and managers. It includes mappings for database persistence and various relationships with 
 * other entities like {@link Animal}, {@link Consumes}, and {@link Manager}.
 * </p>
 * 
 * @author Ander
 */
@Entity
@Table(name = "animalGroup", schema = "farmdb")
@NamedQueries({
    @NamedQuery(
            name = "getAnimalGroups",
            query = "SELECT ag FROM AnimalGroup ag"
    ),
    @NamedQuery(
            name = "getAnimalGroupsByManager",
            query = "SELECT ag FROM AnimalGroup ag JOIN ag.managers m WHERE m.id = :managerId"
    ),
    @NamedQuery(
            name = "getAnimalGroupsByName",
            query = "SELECT ag FROM AnimalGroup ag JOIN ag.managers m WHERE ag.name LIKE :name AND m.id = :managerId"
    ),
})
@XmlRootElement
public class AnimalGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String area;

    @NotNull
    private String description;

    @NotNull
    @JsonSerialize(as = Date.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @OneToMany(cascade = ALL, mappedBy = "animalGroup", fetch = FetchType.LAZY)
    private List<Animal> animals;

    @OneToMany(cascade = ALL, mappedBy = "animalGroup", fetch = FetchType.LAZY)
    private List<Consumes> consumes;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "animalgroup_manager",
            schema = "farmdb",
            joinColumns = @JoinColumn(name = "animalgroupId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "managerId", referencedColumnName = "id")
    )
    private List<Manager> managers;

    /**
     * Returns the unique identifier of the animal group.
     * 
     * @return The unique identifier (ID) of the animal group.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the animal group.
     * 
     * @param id The new unique identifier to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the name of the animal group.
     * 
     * @return The name of the animal group.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the animal group.
     * 
     * @param name The new name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the area of the animal group.
     * 
     * @return The area of the animal group.
     */
    public String getArea() {
        return area;
    }

    /**
     * Sets the area of the animal group.
     * 
     * @param area The new area to set.
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * Returns the description of the animal group.
     * 
     * @return The description of the animal group.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the animal group.
     * 
     * @param description The new description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the creation date of the animal group.
     * 
     * @return The creation date of the animal group.
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the creation date of the animal group.
     * 
     * @param creationDate The new creation date to set.
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Returns a list of animals in the animal group.
     * <p>
     * This method is marked with {@link XmlTransient} to exclude the list of animals from XML 
     * serialization.
     * </p>
     * 
     * @return A list of animals in the animal group.
     */
    @XmlTransient
    public List<Animal> getAnimals() {
        return animals;
    }

    /**
     * Sets the list of animals for the animal group.
     * 
     * @param animals The list of animals to set.
     */
    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }

    /**
     * Returns a list of consumption data for the animal group.
     * <p>
     * This method is marked with {@link XmlTransient} to exclude the list of consumption data 
     * from XML serialization.
     * </p>
     * 
     * @return A list of consumption data for the animal group.
     */
    @XmlTransient
    public List<Consumes> getConsumes() {
        return consumes;
    }

    /**
     * Sets the list of consumption data for the animal group.
     * 
     * @param consumes The list of consumption data to set.
     */
    public void setConsumes(List<Consumes> consumes) {
        this.consumes = consumes;
    }

    /**
     * Returns a list of managers associated with the animal group.
     * 
     * @return A list of managers associated with the animal group.
     */
    public List<Manager> getManagers() {
        return managers;
    }

    /**
     * Sets the list of managers associated with the animal group.
     * 
     * @param managers The list of managers to set.
     */
    public void setManagers(List<Manager> managers) {
        this.managers = managers;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof AnimalGroup)) {
            return false;
        }
        AnimalGroup other = (AnimalGroup) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Animal Group[ id=" + id + " ]";
    }
}
