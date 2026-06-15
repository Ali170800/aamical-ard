package com.amical.ard.servlets;

import com.amical.ard.dao.BureauDAO;
import com.amical.ard.dao.EtudiantDAO;
import com.amical.ard.entites.Bureau;
import com.amical.ard.entites.Etudiant;
import com.amical.ard.services.EmailService;
import com.amical.ard.utils.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/bureau/ajouter")
public class AjouterBureauServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            EtudiantDAO etudiantDAO = new EtudiantDAO(em);

            List<Etudiant> tousEtudiants = etudiantDAO.listerTous();

            String recherche = request.getParameter("recherche");

            List<Etudiant> etudiantsFiltres = tousEtudiants;
            if (recherche != null && !recherche.trim().isEmpty()) {
                String term = recherche.toLowerCase().trim();
                etudiantsFiltres = tousEtudiants.stream()
                        .filter(e -> {
                            String nom = e.getNom() != null ? e.getNom().toLowerCase() : "";
                            String prenom = e.getPrenom() != null ? e.getPrenom().toLowerCase() : "";
                            String email = e.getEmail() != null ? e.getEmail().toLowerCase() : "";
                            return nom.contains(term) || prenom.contains(term) || email.contains(term);
                        })
                        .collect(Collectors.toList());
            }

            request.setAttribute("etudiants", etudiantsFiltres);
            request.setAttribute("recherche", recherche);

            request.getRequestDispatcher("/pages/ajouterBureau.jsp").forward(request, response);

        } finally {
            em.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String etudiantIdStr = request.getParameter("etudiantId");
        String roleBureau = request.getParameter("roleBureau");
        String anneeMandat = request.getParameter("anneeMandat");

        if (etudiantIdStr == null || roleBureau == null || anneeMandat == null) {
            request.setAttribute("erreur", "Tous les champs sont obligatoires.");
            doGet(request, response);
            return;
        }

        Integer etudiantId = Integer.valueOf(etudiantIdStr);

        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            EtudiantDAO etudiantDAO = new EtudiantDAO(em);
            BureauDAO bureauDAO = new BureauDAO(em);

            Etudiant etudiant = etudiantDAO.trouverParId(etudiantId.longValue());
            if (etudiant == null) {
                request.setAttribute("erreur", "Étudiant introuvable.");
                doGet(request, response);
                return;
            }

            Bureau bureau = new Bureau();
            bureau.setNom(etudiant.getNom());
            bureau.setPrenom(etudiant.getPrenom());
            bureau.setEmail(etudiant.getEmail());
            bureau.setNumero(etudiant.getTelephone());
            bureau.setRoleBureau(roleBureau);
            bureau.setAnneeMandat(anneeMandat);

            bureauDAO.ajouter(bureau);

            // Envoi d'email
            String sujet = "🎉 Félicitations ! Vous êtes membre du bureau";
            String message = "Bonjour " + etudiant.getPrenom() + " " + etudiant.getNom() + ",\n\n"
                    + "Nous avons le plaisir de vous informer que vous avez été nommé(e) au poste de "
                    + roleBureau + " pour le mandat " + anneeMandat + ".\n\n"
                    + "Nous comptons sur votre engagement.\n\nCordialement,\nL'équipe de l'Amicale.";

            new EmailService().envoyerEmail(etudiant.getEmail(), sujet, message);

            request.setAttribute("success", "Membre du bureau ajouté avec succès !");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erreur", "Erreur lors de l'ajout : " + e.getMessage());
        } finally {
            em.close();
        }

        doGet(request, response);   // Recharge la page
    }
}