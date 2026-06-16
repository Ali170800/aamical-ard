package com.amical.ard.entites;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ActionLog.class)
public abstract class ActionLog_ {

	public static volatile SingularAttribute<ActionLog, String> role;
	public static volatile SingularAttribute<ActionLog, LocalDateTime> dateAction;
	public static volatile SingularAttribute<ActionLog, String> nomUtilisateur;
	public static volatile SingularAttribute<ActionLog, Integer> utilisateurId;
	public static volatile SingularAttribute<ActionLog, String> action;
	public static volatile SingularAttribute<ActionLog, String> details;
	public static volatile SingularAttribute<ActionLog, Long> id;

	public static final String ROLE = "role";
	public static final String DATE_ACTION = "dateAction";
	public static final String NOM_UTILISATEUR = "nomUtilisateur";
	public static final String UTILISATEUR_ID = "utilisateurId";
	public static final String ACTION = "action";
	public static final String DETAILS = "details";
	public static final String ID = "id";

}

