package com.amical.ard.entites;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Publication.class)
public abstract class Publication_ {

	public static volatile SingularAttribute<Publication, String> image;
	public static volatile SingularAttribute<Publication, String> auteurType;
	public static volatile SingularAttribute<Publication, String> auteurNom;
	public static volatile SingularAttribute<Publication, String> auteurRole;
	public static volatile SingularAttribute<Publication, LocalDateTime> datePublication;
	public static volatile SingularAttribute<Publication, String> description;
	public static volatile SingularAttribute<Publication, String> typeMedia;
	public static volatile SingularAttribute<Publication, Long> id;
	public static volatile SingularAttribute<Publication, String> video;
	public static volatile SingularAttribute<Publication, String> auteurPrenom;
	public static volatile SingularAttribute<Publication, Long> auteurId;

	public static final String IMAGE = "image";
	public static final String AUTEUR_TYPE = "auteurType";
	public static final String AUTEUR_NOM = "auteurNom";
	public static final String AUTEUR_ROLE = "auteurRole";
	public static final String DATE_PUBLICATION = "datePublication";
	public static final String DESCRIPTION = "description";
	public static final String TYPE_MEDIA = "typeMedia";
	public static final String ID = "id";
	public static final String VIDEO = "video";
	public static final String AUTEUR_PRENOM = "auteurPrenom";
	public static final String AUTEUR_ID = "auteurId";

}

