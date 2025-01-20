package entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Consumes.class)
public abstract class Consumes_ {

	public static volatile SingularAttribute<Consumes, Date> date;
	public static volatile SingularAttribute<Consumes, Product> product;
	public static volatile SingularAttribute<Consumes, Float> consumeAmount;
	public static volatile SingularAttribute<Consumes, ConsumesId> consumesId;
	public static volatile SingularAttribute<Consumes, AnimalGroup> animalGroup;

}

