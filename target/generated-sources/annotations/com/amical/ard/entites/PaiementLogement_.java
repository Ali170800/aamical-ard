package com.amical.ard.entites;

import com.amical.ard.enums.StatutPaiement;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PaiementLogement.class)
public abstract class PaiementLogement_ {

	public static volatile SingularAttribute<PaiementLogement, Double> montant;
	public static volatile SingularAttribute<PaiementLogement, Integer> id;
	public static volatile SingularAttribute<PaiementLogement, Integer> annee;
	public static volatile SingularAttribute<PaiementLogement, LogementEtudiant> logementEtudiant;
	public static volatile SingularAttribute<PaiementLogement, StatutPaiement> statut;
	public static volatile SingularAttribute<PaiementLogement, Integer> mois;

	public static final String MONTANT = "montant";
	public static final String ID = "id";
	public static final String ANNEE = "annee";
	public static final String LOGEMENT_ETUDIANT = "logementEtudiant";
	public static final String STATUT = "statut";
	public static final String MOIS = "mois";

}

