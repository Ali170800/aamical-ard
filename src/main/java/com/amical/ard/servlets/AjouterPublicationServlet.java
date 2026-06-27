package com.amical.ard.servlets;

import com.amical.ard.dao.NotificationDAO;
import com.amical.ard.dao.PublicationDAO;
import com.amical.ard.dao.EtudiantDAO;
import com.amical.ard.entites.Publication;
import com.amical.ard.entites.Utilisateur;
import com.amical.ard.entites.Etudiant;
import com.amical.ard.entites.Notification;
import com.amical.ard.utils.EntityManagerHelper;
import com.amical.ard.utils.CloudinaryUtil;
import com.cloudinary.utils.ObjectUtils;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@WebServlet("/admin/ajouter-publication")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 15
)
public class AjouterPublicationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Utilisateur admin = (Utilisateur) session.getAttribute("utilisateurConnecte");

        if (admin == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try {
            // AJOUT DE L'EntityManager pour qu'il soit reconnu partout dans la méthode
            EntityManager em = EntityManagerHelper.getEntityManager();

            // Infos auteur
            Integer auteurId = admin.getId();
            String nom = admin.getNom();
            String prenom = admin.getPrenom();
            String role = admin.getRole();

            // 1. Récupération du Part
            Part imagePart = request.getPart("image");

            // 2. Conversion du flux en tableau d'octets
            byte[] fileContent = imagePart.getInputStream().readAllBytes();

            // 3. Envoi vers Cloudinary
            Map uploadResult = CloudinaryUtil.getCloudinary().uploader().upload(
                    fileContent,
                    ObjectUtils.asMap(
                            "resource_type", "image",
                            "folder", "publications"
                    )
            );

            String imageUrl = (String) uploadResult.get("secure_url");

            // 4. Création et sauvegarde de la Publication
            Publication publication = new Publication();
            publication.setDescription(request.getParameter("description"));
            publication.setImage(imageUrl);
            publication.setAuteurId(auteurId != null ? auteurId.longValue() : 0L);
            publication.setAuteurType("ADMIN");
            publication.setAuteurNom(nom);
            publication.setAuteurPrenom(prenom);
            publication.setAuteurRole(role);
            publication.setDatePublication(LocalDateTime.now());

            PublicationDAO dao = new PublicationDAO(em);
            dao.ajouter(publication);

            // 5. Création des notifications pour tous les étudiants
            String nomAuteur = admin.getPrenom() + " " + admin.getNom();
            EtudiantDAO etudiantDAO = new EtudiantDAO(em);
            NotificationDAO notifDAO = new NotificationDAO(em);

            List<Etudiant> tousLesEtudiants = etudiantDAO.listerTous();
            for(Etudiant e : tousLesEtudiants) {
                Notification n = new Notification();
                n.setUtilisateurId(e.getId());
                n.setMessage(nomAuteur + " a ajouté une nouvelle publication.");
                n.setDateCreation(LocalDateTime.now());
                n.setEstLu(false);
                notifDAO.ajouter(n);
            }

            response.sendRedirect(request.getContextPath() + "/liste-publications");

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Erreur lors de l'ajout de la publication : " + e.getMessage(), e);
        }
    }
}