package com.amical.ard.entites;

import com.amical.ard.enums.StatutPaiement;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ParticipantCaravane.class)
public abstract class ParticipantCaravane_ {

	public static volatile SingularAttribute<ParticipantCaravane, StatutPaiement> statutPaiement;
	public static volatile SingularAttribute<ParticipantCaravane, LocalDateTime> dateInscription;
	public static volatile SingularAttribute<ParticipantCaravane, Caravane> caravane;
	public static volatile SingularAttribute<ParticipantCaravane, Double> montant;
	public static volatile SingularAttribute<ParticipantCaravane, Integer> id;
	public static volatile SingularAttribute<ParticipantCaravane, String> sourceInscription;
	public static volatile SingularAttribute<ParticipantCaravane, String> nom;
	public static volatile SingularAttribute<ParticipantCaravane, String> prenom;
	public static volatile SingularAttribute<ParticipantCaravane, Integer> montantPaye;
	public static volatile SingularAttribute<ParticipantCaravane, String> email;
	public static volatile SingularAttribute<ParticipantCaravane, String> numeroChaise;

	public static final String STATUT_PAIEMENT = "statutPaiement";
	public static final String DATE_INSCRIPTION = "dateInscription";
	public static final String CARAVANE = "caravane";
	public static final String MONTANT = "montant";
	public static final String ID = "id";
	public static final String SOURCE_INSCRIPTION = "sourceInscription";
	public static final String NOM = "nom";
	public static final String PRENOM = "prenom";
	public static final String MONTANT_PAYE = "montantPaye";
	public static final String EMAIL = "email";
	public static final String NUMERO_CHAISE = "numeroChaise";

}

