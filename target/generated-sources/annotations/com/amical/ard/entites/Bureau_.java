package com.amical.ard.entites;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Bureau.class)
public abstract class Bureau_ {

	public static volatile SingularAttribute<Bureau, String> numero;
	public static volatile SingularAttribute<Bureau, String> anneeMandat;
	public static volatile SingularAttribute<Bureau, String> roleBureau;
	public static volatile SingularAttribute<Bureau, Integer> id;
	public static volatile SingularAttribute<Bureau, String> nom;
	public static volatile SingularAttribute<Bureau, String> prenom;
	public static volatile SingularAttribute<Bureau, String> email;

	public static final String NUMERO = "numero";
	public static final String ANNEE_MANDAT = "anneeMandat";
	public static final String ROLE_BUREAU = "roleBureau";
	public static final String ID = "id";
	public static final String NOM = "nom";
	public static final String PRENOM = "prenom";
	public static final String EMAIL = "email";

}

