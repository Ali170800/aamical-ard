package com.amical.ard.entites;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(VoteElection.class)
public abstract class VoteElection_ {

	public static volatile SingularAttribute<VoteElection, Election> election;
	public static volatile SingularAttribute<VoteElection, LocalDateTime> dateVote;
	public static volatile SingularAttribute<VoteElection, CandidatElection> candidat;
	public static volatile SingularAttribute<VoteElection, Long> id;

	public static final String ELECTION = "election";
	public static final String DATE_VOTE = "dateVote";
	public static final String CANDIDAT = "candidat";
	public static final String ID = "id";

}

