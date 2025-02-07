package entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Represents a consumption record of a product by an animal group in the system.
 * The class is mapped to the "consumes" table in the database.
 * A consume record includes details about the animal group, the product consumed,
 * the amount consumed, and the date of consumption.
 * 
 * Named Queries:
 * - "findAllConsumes": Retrieve all consume records.
 * - "findConsumesByProduct": Retrieve consume records for a specific product by name.
 * - "findConsumesByAnimalGroup": Retrieve consume records for a specific animal group by name.
 * - "findConsumesByDateRange": Retrieve consume records between two specified dates.
 * - "findConsumesByDateFrom": Retrieve consume records from a specified start date.
 * - "findConsumesByDateTo": Retrieve consume records until a specified end date.
 * 
 * @author Pablo
 */
@NamedQueries({
    @NamedQuery(
        name="findAllConsumes",
        query="SELECT c FROM Consumes c "
    ),
    @NamedQuery(
        name = "findConsumesByProduct",
        query = "SELECT c FROM Consumes c WHERE product.id IN (SELECT pg.id FROM ProductEntity pg WHERE pg.name = :nameProduct)"
    ),
    @NamedQuery(
        name = "findConsumesByAnimalGroup",
        query = "SELECT c FROM Consumes c WHERE animalGroup.id IN (SELECT ag.id FROM AnimalGroup ag WHERE ag.name = :nameAnimalGroup)"
    ),
    @NamedQuery(
        name="findConsumesByDateRange",
        query="SELECT m FROM Consumes m WHERE m.date BETWEEN :dateFrom AND :dateTo ORDER BY m.date"
    ),
    @NamedQuery(
        name="findConsumesByDateFrom",
        query="SELECT m FROM Consumes m WHERE m.date >= :dateFrom ORDER BY m.date"
    ),
    @NamedQuery(
        name="findConsumesByDateTo",
        query="SELECT m FROM Consumes m WHERE m.date <= :dateTo ORDER BY m.date"
    )
})

@Entity
@Table(name="consumes", schema="farmdb")
@XmlRootElement
public class Consumes implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The composite primary key for the consume entity.
     */
    @EmbeddedId
    private ConsumesId consumesId;

    /**
     * The associated animal group for the consume record.
     */
    @MapsId("animalGroupId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="animalGroupId", updatable=false, insertable=false)
    private AnimalGroup animalGroup;

    /**
     * The associated product for the consume record.
     */
    @MapsId("productId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="productId", updatable=false, insertable=false)
    private ProductEntity product;

    /**
     * The date when the product was consumed.
     */
    @Temporal(TemporalType.DATE)
    @JsonSerialize(as=Date.class)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ssXXX")
    private Date date;

    /**
     * The amount of the product consumed.
     */
    private Float consumeAmount;

    /**
     * Gets the composite primary key of the consume entity.
     * 
     * @return The composite primary key.
     */
    public ConsumesId getConsumesId() {
        return consumesId;
    }

    /**
     * Gets the amount of the product consumed.
     * 
     * @return The consume amount.
     */
    public Float getConsumeAmount() {
        return consumeAmount;
    }

    /**
     * Gets the associated animal group for the consume record.
     * 
     * @return The animal group.
     */
    public AnimalGroup getAnimalGroup() {
        return animalGroup;
    }

    /**
     * Gets the associated product for the consume record.
     * 
     * @return The product.
     */
    public ProductEntity getProduct() {
        return product;
    }

    /**
     * Gets the date when the product was consumed.
     * 
     * @return The date of consumption.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the amount of the product consumed.
     * 
     * @param consume The consume amount.
     */
    public void setConsumeAmount(Float consume) {
        this.consumeAmount = consume;
    }

    /**
     * Sets the associated animal group for the consume record.
     * 
     * @param animalGroup The animal group to set.
     */
    public void setAnimalGroup(AnimalGroup animalGroup) {
        this.animalGroup = animalGroup;
    }

    /**
     * Sets the associated product for the consume record.
     * 
     * @param product The product to set.
     */
    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    /**
     * Sets the date of consumption for the record.
     * 
     * @param date The date to set.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Sets the composite primary key of the consume entity.
     * 
     * @param id The composite primary key.
     */
    public void setConsumesId(ConsumesId id) {
        this.consumesId = id;
    }

    /**
     * Computes the hash code for this consume entity, based on the composite primary key.
     * 
     * @return The hash code of the entity.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (consumesId != null ? consumesId.hashCode() : 0);
        return hash;
    }

    /**
     * Checks if this consume entity is equal to another entity.
     * Compares based on the composite primary key.
     * 
     * @param object The object to compare.
     * @return True if both entities are equal, false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Consumes)) {
            return false;
        }
        Consumes other = (Consumes) object;
        if ((this.consumesId == null && other.consumesId != null) || (this.consumesId != null && !this.consumesId.equals(other.consumesId))) {
            return false;
        }
        return true;
    }

    /**
     * Returns a string representation of the consume entity, including the consume amount.
     * 
     * @return A string representation of the consume entity.
     */
    @Override
    public String toString() {
        return "entities.Consumes[ consume=" + consumeAmount + " ]";
    }
}
