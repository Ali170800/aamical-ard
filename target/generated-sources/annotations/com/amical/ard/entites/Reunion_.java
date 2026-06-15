package com.amical.ard.entites;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.Date;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Reunion.class)
public abstract class Reunion_ {

	public static volatile SingularAttribute<Reunion, Date> dateReunion;
	public static volatile SingularAttribute<Reunion, String> titre;
	public static volatile SingularAttribute<Reunion, String> description;
	public static volatile SingularAttribute<Reunion, Integer> id;
	public static volatile SingularAttribute<Reunion, String> lieu;

	public static final String DATE_REUNION = "dateReunion";
	public static final String TITRE = "titre";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String LIEU = "lieu";

}

