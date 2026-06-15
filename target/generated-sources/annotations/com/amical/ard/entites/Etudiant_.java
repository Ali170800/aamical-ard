package com.amical.ard.entites;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Etudiant.class)
public abstract class Etudiant_ {

	public static volatile SingularAttribute<Etudiant, String> photoProfil;
	public static volatile SingularAttribute<Etudiant, Appartement> appartement;
	public static volatile SingularAttribute<Etudiant, String> sexe;
	public static volatile SingularAttribute<Etudiant, String> telephone;
	public static volatile SingularAttribute<Etudiant, String> nom;
	public static volatile SingularAttribute<Etudiant, String> niveau;
	public static volatile SingularAttribute<Etudiant, String> numeroUrgence;
	public static volatile SingularAttribute<Etudiant, String> filiere;
	public static volatile SingularAttribute<Etudiant, String> anneeUniversitaire;
	public static volatile SingularAttribute<Etudiant, String> adresse;
	public static volatile SingularAttribute<Etudiant, String> pathologie;
	public static volatile SingularAttribute<Etudiant, Long> id;
	public static volatile SingularAttribute<Etudiant, String> prenom;
	public static volatile SingularAttribute<Etudiant, String> email;

	public static final String PHOTO_PROFIL = "photoProfil";
	public static final String APPARTEMENT = "appartement";
	public static final String SEXE = "sexe";
	public static final String TELEPHONE = "telephone";
	public static final String NOM = "nom";
	public static final String NIVEAU = "niveau";
	public static final String NUMERO_URGENCE = "numeroUrgence";
	public static final String FILIERE = "filiere";
	public static final String ANNEE_UNIVERSITAIRE = "anneeUniversitaire";
	public static final String ADRESSE = "adresse";
	public static final String PATHOLOGIE = "pathologie";
	public static final String ID = "id";
	public static final String PRENOM = "prenom";
	public static final String EMAIL = "email";

}

