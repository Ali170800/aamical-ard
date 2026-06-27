package com.amical.ard.entites;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Notification.class)
public abstract class Notification_ {

	public static volatile SingularAttribute<Notification, LocalDateTime> dateCreation;
	public static volatile SingularAttribute<Notification, Long> utilisateurId;
	public static volatile SingularAttribute<Notification, Boolean> estLu;
	public static volatile SingularAttribute<Notification, Long> id;
	public static volatile SingularAttribute<Notification, String> message;

	public static final String DATE_CREATION = "dateCreation";
	public static final String UTILISATEUR_ID = "utilisateurId";
	public static final String EST_LU = "estLu";
	public static final String ID = "id";
	public static final String MESSAGE = "message";

}

