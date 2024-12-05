/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author inifr
 */
@Embeddable
public class ProductProviderId implements Serializable {

    private Long productId;
    private Long providerId;

    public ProductProviderId(){}
    
    public ProductProviderId(Long productId, Long providerId){
        this.productId=productId;
        this.providerId=providerId;
    }
    
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productId != null ? productId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProductProviderId other = (ProductProviderId) obj;
        if (!Objects.equals(this.productId, other.productId)) {
            return false;
        }
        if (!Objects.equals(this.providerId, other.providerId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CustomerAccounId{" + "accountId=" + productId + ", customerId=" + providerId + '}';
    }    
}