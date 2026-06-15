package com.amical.ard.entites;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CompteRendu.class)
public abstract class CompteRendu_ {

	public static volatile SingularAttribute<CompteRendu, Reunion> reunion;
	public static volatile SingularAttribute<CompteRendu, Integer> id;
	public static volatile SingularAttribute<CompteRendu, String> contenu;

	public static final String REUNION = "reunion";
	public static final String ID = "id";
	public static final String CONTENU = "contenu";

}

