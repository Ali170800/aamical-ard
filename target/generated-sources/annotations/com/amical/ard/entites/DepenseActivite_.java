package com.amical.ard.entites;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DepenseActivite.class)
public abstract class DepenseActivite_ {

	public static volatile SingularAttribute<DepenseActivite, Activite> activite;
	public static volatile SingularAttribute<DepenseActivite, String> libelle;
	public static volatile SingularAttribute<DepenseActivite, BigDecimal> montant;
	public static volatile SingularAttribute<DepenseActivite, Integer> id;

	public static final String ACTIVITE = "activite";
	public static final String LIBELLE = "libelle";
	public static final String MONTANT = "montant";
	public static final String ID = "id";

}

