package com.amical.ard.entites;

import com.amical.ard.entites.FichierJoint.TypeFichier;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.Date;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(FichierJoint.class)
public abstract class FichierJoint_ {

	public static volatile SingularAttribute<FichierJoint, String> cheminFichier;
<<<<<<< HEAD
=======
	public static volatile SingularAttribute<FichierJoint, String> titre;
>>>>>>> cc8371319608e422e08ccbcba184f02a94198195
	public static volatile SingularAttribute<FichierJoint, TypeFichier> typeFichier;
	public static volatile SingularAttribute<FichierJoint, Reunion> reunion;
	public static volatile SingularAttribute<FichierJoint, Caravane> caravane;
	public static volatile SingularAttribute<FichierJoint, Integer> id;
	public static volatile SingularAttribute<FichierJoint, Date> dateUpload;
	public static volatile SingularAttribute<FichierJoint, String> nomFichier;
<<<<<<< HEAD

	public static final String CHEMIN_FICHIER = "cheminFichier";
=======
	public static volatile SingularAttribute<FichierJoint, String> nomAuteur;
	public static volatile SingularAttribute<FichierJoint, String> roleAuteur;

	public static final String CHEMIN_FICHIER = "cheminFichier";
	public static final String TITRE = "titre";
>>>>>>> cc8371319608e422e08ccbcba184f02a94198195
	public static final String TYPE_FICHIER = "typeFichier";
	public static final String REUNION = "reunion";
	public static final String CARAVANE = "caravane";
	public static final String ID = "id";
	public static final String DATE_UPLOAD = "dateUpload";
	public static final String NOM_FICHIER = "nomFichier";
<<<<<<< HEAD
=======
	public static final String NOM_AUTEUR = "nomAuteur";
	public static final String ROLE_AUTEUR = "roleAuteur";
>>>>>>> cc8371319608e422e08ccbcba184f02a94198195

}

