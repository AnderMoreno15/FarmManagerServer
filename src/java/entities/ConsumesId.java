package entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents the composite primary key for the Consumes entity.
 * The key is composed of two fields: `animalGroupId` and `productId`.
 * This class is used to uniquely identify a consumption record by combining
 * the animal group and product consumed.
 * 
 * The class implements `Serializable` to support entity persistence and 
 * uses `equals` and `hashCode` methods to ensure proper comparison of key instances.
 * 
 * @author Pablo
 */
@Embeddable
public class ConsumesId implements Serializable {
    
    /**
     * The ID of the associated animal group.
     */
    @Column(name = "animalGroup_animalGroupId")
    private Long animalGroupId;

    /**
     * The ID of the associated product.
     */
    @Column(name = "product_productId")
    private Long productId;

    /**
     * Default constructor for ConsumesId.
     */
    public ConsumesId() {
    }

    /**
     * Constructor for ConsumesId with specified animal group ID and product ID.
     * 
     * @param productId The ID of the product consumed.
     * @param animalGroupId The ID of the animal group.
     */
    public ConsumesId(Long productId, Long animalGroupId) {
        this.productId = productId;
        this.animalGroupId = animalGroupId;
    }

    /**
     * Gets the ID of the associated animal group.
     * 
     * @return The animal group ID.
     */
    public Long getAnimalGroupId() {
        return animalGroupId;
    }

    /**
     * Sets the ID of the associated animal group.
     * 
     * @param animalGroupId The animal group ID to set.
     */
    public void setAnimalGroupId(Long animalGroupId) {
        this.animalGroupId = animalGroupId;
    }

    /**
     * Gets the ID of the associated product.
     * 
     * @return The product ID.
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * Sets the ID of the associated product.
     * 
     * @param productId The product ID to set.
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * Compares this composite primary key with another object for equality.
     * The keys are considered equal if both the `animalGroupId` and `productId` are equal.
     * 
     * @param o The object to compare.
     * @return True if both keys are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConsumesId that = (ConsumesId) o;
        return Objects.equals(animalGroupId, that.animalGroupId) &&
               Objects.equals(productId, that.productId);
    }

    /**
     * Computes the hash code for this composite primary key, based on both the `animalGroupId`
     * and `productId`.
     * 
     * @return The hash code of the composite key.
     */
    @Override
    public int hashCode() {
        return Objects.hash(animalGroupId, productId);
    }
}
