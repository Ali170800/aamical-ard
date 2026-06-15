package com.amical.ard.entites;

import com.amical.ard.entites.EmailEtudiant.StatutLu;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.Date;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EmailEtudiant.class)
public abstract class EmailEtudiant_ {

	public static volatile SingularAttribute<EmailEtudiant, Date> dateEnvoiEffective;
	public static volatile SingularAttribute<EmailEtudiant, StatutLu> statutLu;
	public static volatile SingularAttribute<EmailEtudiant, EmailProgramme> emailProgramme;
	public static volatile SingularAttribute<EmailEtudiant, Integer> id;
	public static volatile SingularAttribute<EmailEtudiant, Etudiant> etudiant;

	public static final String DATE_ENVOI_EFFECTIVE = "dateEnvoiEffective";
	public static final String STATUT_LU = "statutLu";
	public static final String EMAIL_PROGRAMME = "emailProgramme";
	public static final String ID = "id";
	public static final String ETUDIANT = "etudiant";

}

