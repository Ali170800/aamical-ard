package com.amical.ard.entites;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Appartement.class)
public abstract class Appartement_ {

	public static volatile SingularAttribute<Appartement, String> nomAppartement;
	public static volatile SingularAttribute<Appartement, String> description;
	public static volatile SingularAttribute<Appartement, Integer> id;

	public static final String NOM_APPARTEMENT = "nomAppartement";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";

}

