package com.amical.ard.entites;

import com.amical.ard.entites.EmailProgramme.StatutEnvoi;
import com.amical.ard.entites.EmailProgramme.TypeEmail;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.Date;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EmailProgramme.class)
public abstract class EmailProgramme_ {

	public static volatile SingularAttribute<EmailProgramme, Date> dateEnvoi;
	public static volatile SingularAttribute<EmailProgramme, TypeEmail> typeEmail;
	public static volatile SingularAttribute<EmailProgramme, StatutEnvoi> statutEnvoi;
	public static volatile SingularAttribute<EmailProgramme, Integer> id;
	public static volatile SingularAttribute<EmailProgramme, String> sujet;
	public static volatile SingularAttribute<EmailProgramme, String> message;

	public static final String DATE_ENVOI = "dateEnvoi";
	public static final String TYPE_EMAIL = "typeEmail";
	public static final String STATUT_ENVOI = "statutEnvoi";
	public static final String ID = "id";
	public static final String SUJET = "sujet";
	public static final String MESSAGE = "message";

}

