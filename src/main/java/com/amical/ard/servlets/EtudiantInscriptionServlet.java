package com.amical.ard.servlets;

import com.amical.ard.dao.EtudiantDAO;
import com.amical.ard.entites.Etudiant;
import com.amical.ard.services.EmailService;
import com.amical.ard.utils.EntityManagerHelper;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/etudiant/inscription")
public class EtudiantInscriptionServlet extends HttpServlet {

    private final EmailService emailService = new EmailService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/pages/inscriptionEtudiant.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String prenom = request.getParameter("prenom");
        String nom = request.getParameter("nom");
        String email = request.getParameter("email");
        String telephone = request.getParameter("telephone");
        String sexe = request.getParameter("sexe");
        String filiere = request.getParameter("filiere");
        String niveau = request.getParameter("niveau");
        String anneeUniversitaire = request.getParameter("anneeUniversitaire");
        String adresse = request.getParameter("adresse");
        String numeroUrgence = request.getParameter("numeroUrgence");
        String pathologie = request.getParameter("pathologie");

        if (prenom == null || nom == null || email == null ||
                prenom.trim().isEmpty() || nom.trim().isEmpty() ||
                email.trim().isEmpty()) {

            request.setAttribute("erreur", "Les champs Prénom, Nom et Email sont obligatoires.");
            doGet(request, response);
            return;
        }

        String codeValidation = UUID.randomUUID().toString().substring(0, 6).toUpperCase();

        // Utilisation du Helper global partagé par le Filtre
        EntityManager em = EntityManagerHelper.getEntityManager();
        EtudiantDAO dao = new EtudiantDAO(em);

        try {
            List<Etudiant> existants = em.createQuery(
                            "SELECT e FROM Etudiant e WHERE LOWER(TRIM(e.email)) = LOWER(TRIM(:email))", Etudiant.class)
                    .setParameter("email", email.trim().toLowerCase())
                    .getResultList();

            if (!existants.isEmpty()) {
                request.setAttribute("erreur", "Un compte avec cet email existe déjà.");
                doGet(request, response);
                return;
            }

            Etudiant etudiant = new Etudiant();
            etudiant.setPrenom(prenom.trim());
            etudiant.setNom(nom.trim());
            etudiant.setEmail(email.trim().toLowerCase());
            etudiant.setTelephone(telephone);
            etudiant.setSexe(sexe);
            etudiant.setFiliere(filiere);
            etudiant.setNiveau(niveau);
            etudiant.setAnneeUniversitaire(anneeUniversitaire);
            etudiant.setAdresse(adresse);
            etudiant.setNumeroUrgence(numeroUrgence);
            etudiant.setPathologie(pathologie);

            etudiant.setMotDePasse(null);
            etudiant.setStatut("PENDING");
            etudiant.setCodeValidation(codeValidation);

            // Suppression du begin/commit manuel : le Filtre gère la transaction
            dao.ajouter(etudiant);

            // Email
            String message = "Code de validation : " + codeValidation;
            emailService.envoyerEmail(etudiant.getEmail(), "Activation compte", message);

            request.setAttribute("email", etudiant.getEmail());
            request.setAttribute("success", "Inscription réussie ! Vérifiez votre email.");
            request.getRequestDispatcher("/pages/verificationCode.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erreur", "Erreur lors de l'inscription.");
            doGet(request, response);
        }
        // Suppression du finally { em.close(); } : le Filtre ferme la connexion
    }
}