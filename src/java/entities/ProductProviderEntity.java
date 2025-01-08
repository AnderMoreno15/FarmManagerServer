/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author InigoFreire
 */
@Entity
@Table(name="product_provider", schema="farmdb")
@XmlRootElement
public class ProductProviderEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private ProductProviderId id;
    @MapsId("productId")
    @ManyToMany(fetch=FetchType.EAGER)
    private ProductEntity product;
    @MapsId("providerId")
    @ManyToMany(fetch=FetchType.EAGER)
    private ProviderEntity provider;

    public ProductProviderId getId() {
        return id;
    }

    public void setId(ProductProviderId id) {
        this.id = id;
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
        if (!(object instanceof ProductProviderEntity)) {
            return false;
        }
        ProductProviderEntity other = (ProductProviderEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.ProductProviderEntity[ id=" + id + " ]";
    }
    
}
