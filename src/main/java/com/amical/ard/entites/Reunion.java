package com.amical.ard.entites;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reunion")
public class Reunion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String titre;

    @Column(name = "date_reunion")
    @Temporal(TemporalType.DATE)
    private Date dateReunion;

    @Column
    private String lieu;

    @Column(columnDefinition = "TEXT")
    private String description;

    // Constructeur vide
    public Reunion() {}

    // Getters et Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Date getDateReunion() {
        return dateReunion;
    }

    public void setDateReunion(Date dateReunion) {
        this.dateReunion = dateReunion;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
