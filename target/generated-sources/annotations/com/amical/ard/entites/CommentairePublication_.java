package com.amical.ard.entites;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CommentairePublication.class)
public abstract class CommentairePublication_ {

	public static volatile SingularAttribute<CommentairePublication, Long> utilisateurId;
	public static volatile SingularAttribute<CommentairePublication, LocalDateTime> dateCommentaire;
	public static volatile SingularAttribute<CommentairePublication, Long> id;
	public static volatile SingularAttribute<CommentairePublication, Long> publicationId;
	public static volatile SingularAttribute<CommentairePublication, String> commentaire;

	public static final String UTILISATEUR_ID = "utilisateurId";
	public static final String DATE_COMMENTAIRE = "dateCommentaire";
	public static final String ID = "id";
	public static final String PUBLICATION_ID = "publicationId";
	public static final String COMMENTAIRE = "commentaire";

}

