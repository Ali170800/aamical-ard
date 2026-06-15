package com.amical.ard.entites;

import com.amical.ard.enums.StatutPaiement;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ParticipantCaravane.class)
public abstract class ParticipantCaravane_ {

	public static volatile SingularAttribute<ParticipantCaravane, StatutPaiement> statutPaiement;
	public static volatile SingularAttribute<ParticipantCaravane, Caravane> caravane;
	public static volatile SingularAttribute<ParticipantCaravane, Double> montant;
	public static volatile SingularAttribute<ParticipantCaravane, Integer> id;
	public static volatile SingularAttribute<ParticipantCaravane, String> nom;
	public static volatile SingularAttribute<ParticipantCaravane, String> prenom;
	public static volatile SingularAttribute<ParticipantCaravane, Integer> montantPaye;
	public static volatile SingularAttribute<ParticipantCaravane, String> email;

	public static final String STATUT_PAIEMENT = "statutPaiement";
	public static final String CARAVANE = "caravane";
	public static final String MONTANT = "montant";
	public static final String ID = "id";
	public static final String NOM = "nom";
	public static final String PRENOM = "prenom";
	public static final String MONTANT_PAYE = "montantPaye";
	public static final String EMAIL = "email";

}

