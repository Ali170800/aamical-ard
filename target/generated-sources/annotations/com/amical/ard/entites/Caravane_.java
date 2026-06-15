package com.amical.ard.entites;

import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDate;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Caravane.class)
public abstract class Caravane_ {

	public static volatile SingularAttribute<Caravane, LocalDate> date;
	public static volatile SingularAttribute<Caravane, Double> montant;
	public static volatile SingularAttribute<Caravane, Integer> id;
	public static volatile SingularAttribute<Caravane, String> nom;
	public static volatile ListAttribute<Caravane, ParticipantCaravane> participants;

	public static final String DATE = "date";
	public static final String MONTANT = "montant";
	public static final String ID = "id";
	public static final String NOM = "nom";
	public static final String PARTICIPANTS = "participants";

}

