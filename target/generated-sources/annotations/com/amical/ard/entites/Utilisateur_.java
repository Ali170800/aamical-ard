package com.amical.ard.entites;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Utilisateur.class)
public abstract class Utilisateur_ {

	public static volatile SingularAttribute<Utilisateur, String> motDePasse;
	public static volatile SingularAttribute<Utilisateur, Integer> id;
	public static volatile SingularAttribute<Utilisateur, String> login;

	public static final String MOT_DE_PASSE = "motDePasse";
	public static final String ID = "id";
	public static final String LOGIN = "login";

}

