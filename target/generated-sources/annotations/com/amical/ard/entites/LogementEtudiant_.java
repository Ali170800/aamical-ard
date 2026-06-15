package com.amical.ard.entites;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(LogementEtudiant.class)
public abstract class LogementEtudiant_ {

	public static volatile SingularAttribute<LogementEtudiant, Appartement> appartement;
	public static volatile SingularAttribute<LogementEtudiant, Integer> id;
	public static volatile SingularAttribute<LogementEtudiant, Etudiant> etudiant;

	public static final String APPARTEMENT = "appartement";
	public static final String ID = "id";
	public static final String ETUDIANT = "etudiant";

}

