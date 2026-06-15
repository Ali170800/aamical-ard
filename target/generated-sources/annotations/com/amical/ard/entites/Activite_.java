package com.amical.ard.entites;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.Date;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Activite.class)
public abstract class Activite_ {

	public static volatile SingularAttribute<Activite, Date> dateActivite;
	public static volatile SingularAttribute<Activite, Integer> id;
	public static volatile SingularAttribute<Activite, String> nom;
	public static volatile SingularAttribute<Activite, String> lieu;

	public static final String DATE_ACTIVITE = "dateActivite";
	public static final String ID = "id";
	public static final String NOM = "nom";
	public static final String LIEU = "lieu";

}

