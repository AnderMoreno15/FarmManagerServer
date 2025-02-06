
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity representing a user in the farm management system.
 * <p>
 * This class serves as a base class for different types of users in the system, 
 * such as managers. It contains common properties shared across user types, 
 * including personal information like name, email, phone, city, and address.
 * The class uses the SINGLE_TABLE inheritance strategy, where all user types are 
 * stored in the same table with a discriminator column indicating the type of user.
 * </p>
 * 
 * @author Ander
 */
@Entity
@Table(name = "farmUser", schema = "farmdb")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "USER_TYPE", discriminatorType = DiscriminatorType.STRING)
@XmlRootElement
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @NotNull
    protected String name;

    @Column(unique = true)
    @NotNull
    protected String email;

    protected String phone;

    @NotNull
    protected String city;

    @NotNull
    protected String zip;

    protected String street;

    /**
     * Returns the unique identifier of the user.
     * 
     * @return The unique identifier (ID) of the user.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the user.
     * 
     * @param id The new unique identifier to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the name of the user.
     * 
     * @return The name of the user.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     * 
     * @param name The new name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the email of the user.
     * 
     * @return The email of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     * 
     * @param email The new email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the phone number of the user.
     * 
     * @return The phone number of the user.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the user.
     * 
     * @param phone The new phone number to set.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Returns the city of the user.
     * 
     * @return The city of the user.
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city of the user.
     * 
     * @param city The new city to set.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Returns the postal zip code of the user.
     * 
     * @return The postal zip code of the user.
     */
    public String getZip() {
        return zip;
    }

    /**
     * Sets the postal zip code of the user.
     * 
     * @param zip The new postal zip code to set.
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * Returns the street address of the user.
     * 
     * @return The street address of the user.
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets the street address of the user.
     * 
     * @param street The new street address to set.
     */
    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof UserEntity)) {
            return false;
        }
        UserEntity other = (UserEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.UserEntity[ id=" + id + " ]";
    }
}