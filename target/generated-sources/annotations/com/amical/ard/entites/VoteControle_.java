package com.amical.ard.entites;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(VoteControle.class)
public abstract class VoteControle_ {

	public static volatile SingularAttribute<VoteControle, Election> election;
	public static volatile SingularAttribute<VoteControle, Long> id;
	public static volatile SingularAttribute<VoteControle, Etudiant> etudiant;

	public static final String ELECTION = "election";
	public static final String ID = "id";
	public static final String ETUDIANT = "etudiant";

}

