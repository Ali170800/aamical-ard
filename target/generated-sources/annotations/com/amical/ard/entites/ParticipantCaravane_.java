package com.amical.ard.entites;

import com.amical.ard.enums.StatutPaiement;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
<<<<<<< HEAD
=======
import java.time.LocalDateTime;
>>>>>>> cc8371319608e422e08ccbcba184f02a94198195
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ParticipantCaravane.class)
public abstract class ParticipantCaravane_ {

	public static volatile SingularAttribute<ParticipantCaravane, StatutPaiement> statutPaiement;
<<<<<<< HEAD
	public static volatile SingularAttribute<ParticipantCaravane, Caravane> caravane;
	public static volatile SingularAttribute<ParticipantCaravane, Double> montant;
	public static volatile SingularAttribute<ParticipantCaravane, Integer> id;
=======
	public static volatile SingularAttribute<ParticipantCaravane, LocalDateTime> dateInscription;
	public static volatile SingularAttribute<ParticipantCaravane, Caravane> caravane;
	public static volatile SingularAttribute<ParticipantCaravane, Double> montant;
	public static volatile SingularAttribute<ParticipantCaravane, Integer> id;
	public static volatile SingularAttribute<ParticipantCaravane, String> sourceInscription;
>>>>>>> cc8371319608e422e08ccbcba184f02a94198195
	public static volatile SingularAttribute<ParticipantCaravane, String> nom;
	public static volatile SingularAttribute<ParticipantCaravane, String> prenom;
	public static volatile SingularAttribute<ParticipantCaravane, Integer> montantPaye;
	public static volatile SingularAttribute<ParticipantCaravane, String> email;
<<<<<<< HEAD

	public static final String STATUT_PAIEMENT = "statutPaiement";
	public static final String CARAVANE = "caravane";
	public static final String MONTANT = "montant";
	public static final String ID = "id";
=======
	public static volatile SingularAttribute<ParticipantCaravane, String> numeroChaise;

	public static final String STATUT_PAIEMENT = "statutPaiement";
	public static final String DATE_INSCRIPTION = "dateInscription";
	public static final String CARAVANE = "caravane";
	public static final String MONTANT = "montant";
	public static final String ID = "id";
	public static final String SOURCE_INSCRIPTION = "sourceInscription";
>>>>>>> cc8371319608e422e08ccbcba184f02a94198195
	public static final String NOM = "nom";
	public static final String PRENOM = "prenom";
	public static final String MONTANT_PAYE = "montantPaye";
	public static final String EMAIL = "email";
<<<<<<< HEAD
=======
	public static final String NUMERO_CHAISE = "numeroChaise";
>>>>>>> cc8371319608e422e08ccbcba184f02a94198195

}

