package com.amical.ard.entites;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CandidatElection.class)
public abstract class CandidatElection_ {

	public static volatile SingularAttribute<CandidatElection, String> filiere;
	public static volatile SingularAttribute<CandidatElection, Election> election;
	public static volatile SingularAttribute<CandidatElection, String> photo;
	public static volatile SingularAttribute<CandidatElection, String> description;
	public static volatile SingularAttribute<CandidatElection, Long> id;
	public static volatile SingularAttribute<CandidatElection, String> nom;
	public static volatile SingularAttribute<CandidatElection, String> prenom;
	public static volatile SingularAttribute<CandidatElection, String> promotion;

	public static final String FILIERE = "filiere";
	public static final String ELECTION = "election";
	public static final String PHOTO = "photo";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String NOM = "nom";
	public static final String PRENOM = "prenom";
	public static final String PROMOTION = "promotion";

}

