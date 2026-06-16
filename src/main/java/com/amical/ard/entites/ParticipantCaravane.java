package com.amical.ard.entites;

import com.amical.ard.enums.StatutPaiement;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "participant_caravane")
public class ParticipantCaravane {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false, length = 100)
    private String prenom;

    @Column(nullable = false)
    private Double montant;

    @Column(name = "montant_paye")
    private Integer montantPaye = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut_paiement", nullable = false)
    private StatutPaiement statutPaiement = StatutPaiement.Non_Paye;

    @ManyToOne
    @JoinColumn(name = "id_caravane", nullable = false)
    private Caravane caravane;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "source_inscription", nullable = false)
    private String sourceInscription = "ETUDIANT";

    @Column(name = "numero_chaise", length = 20)
    private String numeroChaise;

    @Column(name = "date_inscription")
    private LocalDateTime dateInscription;

    // ====================== CONSTRUCTEURS ======================
    public ParticipantCaravane() {}

    // ====================== GETTERS & SETTERS ======================
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public Double getMontant() { return montant; }
    public void setMontant(Double montant) { this.montant = montant; }

    public Integer getMontantPaye() { return montantPaye; }
    public void setMontantPaye(Integer montantPaye) { this.montantPaye = montantPaye; }

    public StatutPaiement getStatutPaiement() { return statutPaiement; }
    public void setStatutPaiement(StatutPaiement statutPaiement) { this.statutPaiement = statutPaiement; }

    public Caravane getCaravane() { return caravane; }
    public void setCaravane(Caravane caravane) { this.caravane = caravane; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSourceInscription() { return sourceInscription; }
    public void setSourceInscription(String sourceInscription) { this.sourceInscription = sourceInscription; }

    public String getNumeroChaise() { return numeroChaise; }
    public void setNumeroChaise(String numeroChaise) { this.numeroChaise = numeroChaise; }

    public LocalDateTime getDateInscription() { return dateInscription; }
    public void setDateInscription(LocalDateTime dateInscription) { this.dateInscription = dateInscription; }

    // ====================== MÉTHODE FORMATÉE ======================
    /**
     * Retourne la date d'inscription formatée pour l'affichage dans les JSP
     */
    public String getDateInscriptionFormatted() {
        if (dateInscription == null) {
            return "Non disponible";
        }
        return dateInscription.format(DateTimeFormatter.ofPattern("dd/MM/yyyy à HH:mm"));
    }
}