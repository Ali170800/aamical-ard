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
	public static volatile SingularAttribute<FichierJoint, TypeFichier> typeFichier;
	public static volatile SingularAttribute<FichierJoint, Reunion> reunion;
	public static volatile SingularAttribute<FichierJoint, Caravane> caravane;
	public static volatile SingularAttribute<FichierJoint, Integer> id;
	public static volatile SingularAttribute<FichierJoint, Date> dateUpload;
	public static volatile SingularAttribute<FichierJoint, String> nomFichier;

	public static final String CHEMIN_FICHIER = "cheminFichier";
	public static final String TYPE_FICHIER = "typeFichier";
	public static final String REUNION = "reunion";
	public static final String CARAVANE = "caravane";
	public static final String ID = "id";
	public static final String DATE_UPLOAD = "dateUpload";
	public static final String NOM_FICHIER = "nomFichier";

}

