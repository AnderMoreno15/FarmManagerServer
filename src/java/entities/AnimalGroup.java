/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

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

/**
 *
 * @author Ander
 */
@Entity
@Table(name = "animalGroup", schema = "farmdb")
@NamedQueries({
    @NamedQuery(
            name = "getAnimalGroups",
            query = "SELECT ag FROM AnimalGroup ag"
    )
    ,
    @NamedQuery(
            name = "getAnimalGroupsByManager",
            query = "SELECT ag FROM AnimalGroup ag JOIN ag.managers m WHERE m.id = :managerId"
    )
    ,
    @NamedQuery(
            name = "getAnimalGroupsByName",
            query = "SELECT ag FROM AnimalGroup ag JOIN ag.managers m WHERE ag.name = :name AND m.id = :managerId"
    ),})
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
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @OneToMany(cascade = ALL, mappedBy = "animalGroup")
    private List<Animal> animals;
//    @OneToMany(cascade = ALL, mappedBy = "animalGroup")
//    private List<Consumes> consumes;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "animalgroup_manager",
            schema = "farmdb",
            joinColumns = @JoinColumn(name = "animalgroupId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "managerId", referencedColumnName = "id")
    )
    private List<Manager> managers;

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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @XmlTransient
    public List<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }

//    public List<ConsumeEntity> getConsumes() {
//        return consumes;
//    }
//
//    public void setConsumes(List<ConsumeEntity> consumes) {
//        this.consumes = consumes;
//    }
    @XmlTransient
    public List<Manager> getManagers() {
        return managers;
    }

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
        // TODO: Warning - this method won't work in the case the id fields are not set
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
