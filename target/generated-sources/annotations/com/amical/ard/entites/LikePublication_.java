package com.amical.ard.entites;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(LikePublication.class)
public abstract class LikePublication_ {

	public static volatile SingularAttribute<LikePublication, LocalDateTime> dateLike;
	public static volatile SingularAttribute<LikePublication, Long> utilisateurId;
	public static volatile SingularAttribute<LikePublication, Long> id;
	public static volatile SingularAttribute<LikePublication, Long> publicationId;

	public static final String DATE_LIKE = "dateLike";
	public static final String UTILISATEUR_ID = "utilisateurId";
	public static final String ID = "id";
	public static final String PUBLICATION_ID = "publicationId";

}

