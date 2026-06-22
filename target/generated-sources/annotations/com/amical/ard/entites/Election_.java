package com.amical.ard.entites;

import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Election.class)
public abstract class Election_ {

	public static volatile ListAttribute<Election, CandidatElection> candidats;
	public static volatile SingularAttribute<Election, LocalDateTime> dateCreation;
	public static volatile SingularAttribute<Election, LocalDateTime> dateDebut;
	public static volatile SingularAttribute<Election, String> titre;
	public static volatile SingularAttribute<Election, String> description;
	public static volatile SingularAttribute<Election, Long> id;
	public static volatile SingularAttribute<Election, String> poste;
	public static volatile SingularAttribute<Election, LocalDateTime> dateFin;

	public static final String CANDIDATS = "candidats";
	public static final String DATE_CREATION = "dateCreation";
	public static final String DATE_DEBUT = "dateDebut";
	public static final String TITRE = "titre";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String POSTE = "poste";
	public static final String DATE_FIN = "dateFin";

}

