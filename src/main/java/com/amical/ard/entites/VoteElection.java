package com.amical.ard.entites;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vote_election")
public class VoteElection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "election_id", nullable = false)
    private Election election;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidat_id", nullable = false)
    private CandidatElection candidat;

    private LocalDateTime dateVote;

    public VoteElection() {}

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Election getElection() { return election; }
    public void setElection(Election election) { this.election = election; }

    public CandidatElection getCandidat() { return candidat; }
    public void setCandidat(CandidatElection candidat) { this.candidat = candidat; }

    public LocalDateTime getDateVote() { return dateVote; }
    public void setDateVote(LocalDateTime dateVote) { this.dateVote = dateVote; }
}