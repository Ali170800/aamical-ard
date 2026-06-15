package com.amical.ard.servlets;

import com.amical.ard.dao.EtudiantDAO;
import com.amical.ard.entites.Etudiant;
import com.amical.ard.services.EmailService;
import com.amical.ard.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.InputStream;

@WebServlet("/importer-etudiants")
@MultipartConfig
public class ImportEtudiantsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Part fichier = request.getPart("fichier");
        InputStream inputStream = fichier.getInputStream();

        List<Etudiant> etudiants = new ArrayList<>();
        EntityManager em = null;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(fichier.getInputStream()))) {

            String ligneCSV;
            boolean premiereLigne = true;

            while ((ligneCSV = reader.readLine()) != null) {
                if (premiereLigne) {
                    premiereLigne = false;
                    continue;
                }

                String[] ligne = ligneCSV.split(",");

                if (ligne.length < 12) {
                    System.out.println("Ligne ignorée (colonnes insuffisantes) : " + ligneCSV);
                    continue;
                }

                String[] data = new String[11];
                System.arraycopy(ligne, 1, data, 0, 11);

                Etudiant etudiant = new Etudiant();
                etudiant.setPrenom(data[0].trim());
                etudiant.setNom(data[1].trim());
                etudiant.setSexe(data[2].trim());
                etudiant.setFiliere(data[3].trim());
                etudiant.setNiveau(data[4].trim());
                etudiant.setAnneeUniversitaire(data[5].trim());
                etudiant.setTelephone(data[6].trim());
                etudiant.setNumeroUrgence(data[7].trim());
                etudiant.setPathologie(data[8].trim());
                etudiant.setAdresse(data[9].trim());
                etudiant.setEmail(data[10].trim());

                etudiants.add(etudiant);
            }

            em = JpaUtil.getEntityManagerFactory().createEntityManager();
            EtudiantDAO etudiantDAO = new EtudiantDAO(em);
            EmailService emailService = new EmailService();

            for (Etudiant e : etudiants) {
                em.getTransaction().begin();
                etudiantDAO.ajouter(e);
                em.getTransaction().commit();

                String sujet = "Bienvenue dans l'amicale de Diourbel";
                String message = "Bonjour " + e.getPrenom() + " " + e.getNom() + ",\n\n" +
                        "Nous vous confirmons que vous êtes désormais inscrit dans l'amicale des étudiants de Diourbel.\n" +
                        "Votre identifiant d'étudiant est : " + e.getId() + "\n\n" +
                        "Bienvenue parmi nous !\n\n" +
                        "L'équipe de l'amicale.\n\n" +
                        "Ce nouveau système a été développé par Alioune Diouf, étudiant en ingénierie informatique.";

                try {
                    emailService.envoyerEmail(e.getEmail(), sujet, message);
                    System.out.println("Email envoyé à : " + e.getEmail());
                } catch (Exception ex) {
                    System.err.println("Échec de l'envoi de l'e-mail à " + e.getEmail());
                    ex.printStackTrace();
                }
            }

            request.setAttribute("message", "Importation et envoi des e-mails réussis !");
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("message", "Erreur lors de l'importation des étudiants.");
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        request.getRequestDispatcher("pages/importEtudiants.jsp").forward(request, response);
    }
}