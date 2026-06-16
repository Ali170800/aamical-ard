package com.amical.ard.servlets;

import com.amical.ard.dao.EtudiantDAO;
import com.amical.ard.entites.Etudiant;
import com.amical.ard.services.EmailService;
import com.amical.ard.utils.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/ajouter-etudiant")
public class AjoutEtudiantServlet extends HttpServlet {

    private EmailService emailService;

    @Override
    public void init() {
        emailService = new EmailService(); // Une seule fois à l'initialisation
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Récupération des champs du formulaire
        String prenom = request.getParameter("prenom");
        String nom = request.getParameter("nom");
        String sexe = request.getParameter("sexe");
        String filiere = request.getParameter("filiere");
        String niveau = request.getParameter("niveau");
        String anneeUniversitaire = request.getParameter("anneeUniversitaire");
        String telephone = request.getParameter("telephone");
        String numeroUrgence = request.getParameter("numeroUrgence");
        String pathologie = request.getParameter("pathologie");
        String adresse = request.getParameter("adresse");
        String email = request.getParameter("email");

        // Validation minimale
        if (prenom == null || nom == null || email == null ||
            prenom.trim().isEmpty() || nom.trim().isEmpty() || email.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Les champs prénom, nom et email sont obligatoires.");
            return;
        }

        // Création de l'objet Etudiant
        Etudiant etudiant = new Etudiant();
        etudiant.setPrenom(prenom);
        etudiant.setNom(nom);
        etudiant.setSexe(sexe);
        etudiant.setFiliere(filiere);
        etudiant.setNiveau(niveau);
        etudiant.setAnneeUniversitaire(anneeUniversitaire);
        etudiant.setTelephone(telephone);
        etudiant.setNumeroUrgence(numeroUrgence);
        etudiant.setPathologie(pathologie);
        etudiant.setAdresse(adresse);
        etudiant.setEmail(email);

        // Enregistrement en base
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            EtudiantDAO dao = new EtudiantDAO(em);
            dao.ajouter(etudiant);
        } finally {
            em.close();
        }

        // Envoi d’un email personnalisé à cet étudiant
        String sujet = "Bienvenue dans l'amicale de Diourbel";
        String corpsMessage = String.format(
            "Bonjour %s %s,\n\n" +
            "Nous vous confirmons que vous êtes désormais inscrit dans l'amicale des étudiants de Diourbel.\n" +
            "Votre identifiant d'étudiant est : %d\n\n" +
            "Bienvenue parmi nous !\n\n" +
            "L'équipe de l'amicale.\n\n" +
            "Ce nouveau système a été développé par Alioune Diouf, étudiant en ingénierie informatique.",
            prenom, nom, etudiant.getId()
        );

        try {
            emailService.envoyerEmail(email, sujet, corpsMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ✅ Redirection vers la liste des étudiants
        response.sendRedirect(request.getContextPath() + "/etudiants");
    }
}