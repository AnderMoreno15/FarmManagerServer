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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
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
 *
 * @author Pablo
 */
                                       
@NamedQueries({
    
@NamedQuery(
        name="findAllConsumes",
        query="SELECT c FROM Consumes c "
        ),
@NamedQuery(
        name="findConsumesByProduct",
            query="SELECT m FROM Consumes m WHERE m.product.id = :productId"
        ),
@NamedQuery(
        name="findConsumesByAnimalGroup",
            query="SELECT m FROM Consumes m WHERE m.animalGroup.id = :animalGroupId"
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

//@NamedQuery(
//    name="findAllConsumes",
//    query = "SELECT c FROM Consumes c WHERE c.animalGroup.id IN " +
//           "(SELECT agm.animalGroupId FROM AnimalGroupManager agm WHERE agm.managerId = :managerId)"
//)

//@NamedQuery(
//    name="findAllConsumes",
//    query = "SELECT c FROM Consumes c, AnimalGroupManager agm " +
//           "WHERE c.animalGroup.id = agm.animalGroupId " +
//           "AND agm.managerId = :managerId"
//)


@Entity
@Table(name="consumes",schema="farmdb")
@XmlRootElement
public class Consumes implements Serializable {
   
    
     private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    private ConsumesId consumesId;
    
   
    @MapsId("animalGroupId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="animalGroupId",updatable=false,insertable=false)
    private AnimalGroup animalGroup;
    
    
    @MapsId("productId")
    @ManyToOne(fetch = FetchType.LAZY)   
    @JoinColumn(name="productId",updatable=false,insertable=false)
    private ProductEntity product;
    
    
    @Temporal(TemporalType.DATE)
    @JsonSerialize(as=Date.class)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ssXXX")
    private Date date;
    
    
    private Float consumeAmount;  
    
   
    public ConsumesId getConsumesId() {
        return consumesId;
    }

    
    public Float getConsumeAmount() {
        return consumeAmount;
    }

   
    public AnimalGroup getAnimalGroup() {
        return animalGroup;
    }
    
  
    public ProductEntity getProduct() {
        return product;
    }
   
    public Date getDate() {
        return date;
    }

    public void setConsumeAmount(Float consume) {
        this.consumeAmount = consume;
    }

    public void setAnimalGroup(AnimalGroup animalGroup) {
        this.animalGroup = animalGroup;
    }

    public void setProduct(ProductEntity product) {  
    this.product = product;
    }


    public void setDate(Date date) {
        this.date = date;
    }
    

    public void setConsumesId(ConsumesId id) {
        this.consumesId = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (consumesId != null ? consumesId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Consumes)) {
            return false;
        }
        Consumes other = (Consumes) object;
        if ((this.consumesId == null && other.consumesId != null) || (this.consumesId != null && !this.consumesId.equals(other.consumesId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Consumes[ consume=" + consumeAmount + " ]";
    }
    
}
