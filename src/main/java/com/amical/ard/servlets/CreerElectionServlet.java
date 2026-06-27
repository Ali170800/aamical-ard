package com.amical.ard.servlets;

import com.amical.ard.entites.*;
import com.amical.ard.dao.EtudiantDAO; // Import ajouté
import com.amical.ard.dao.NotificationDAO; // Import ajouté
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/admin/creer-election")
public class CreerElectionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/pages/creer-election.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // On récupère l'EntityManager ouvert par le filtre
        EntityManager em = (EntityManager) request.getAttribute("em");

        String titre = request.getParameter("titre");
        String[] noms = request.getParameterValues("nom");
        String[] prenoms = request.getParameterValues("prenom");
        LocalDateTime debut = LocalDateTime.parse(request.getParameter("dateDebut"));
        LocalDateTime fin = LocalDateTime.parse(request.getParameter("dateFin"));

        try {
            em.getTransaction().begin();

            Election e = new Election();
            e.setTitre(titre);
            e.setDateDebut(debut);
            e.setDateFin(fin);
            em.persist(e);

            if (noms != null) {
                for (int i = 0; i < noms.length; i++) {
                    CandidatElection c = new CandidatElection();
                    c.setNom(noms[i]);
                    c.setPrenom(prenoms[i]);
                    c.setElection(e);
                    em.persist(c);
                }
            }

            em.getTransaction().commit();

            // --- DEBUT DE LA LOGIQUE DE NOTIFICATION ---
            try {
                EtudiantDAO etudiantDAO = new EtudiantDAO(em);
                NotificationDAO notifDAO = new NotificationDAO(em);
                List<Etudiant> tousLesEtudiants = etudiantDAO.listerTous();

                String messageNotif = "Une nouvelle élection est ouverte : " + titre;

                for (Etudiant etudiant : tousLesEtudiants) {
                    Notification n = new Notification();
                    n.setUtilisateurId(etudiant.getId());
                    n.setMessage(messageNotif);
                    n.setDateCreation(LocalDateTime.now());
                    n.setEstLu(false);
                    notifDAO.ajouter(n);
                }
            } catch (Exception notifEx) {
                // On ne bloque pas la redirection si la notification échoue
                notifEx.printStackTrace();
            }
            // --- FIN DE LA LOGIQUE DE NOTIFICATION ---

            response.sendRedirect(request.getContextPath() + "/pages/dashboard-elections");

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/pages/creer-election?error=1");
        }
    }
}