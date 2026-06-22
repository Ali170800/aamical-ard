package com.amical.ard.servlets;

import com.amical.ard.dao.PublicationDAO;
import com.amical.ard.entites.Publication;
import com.amical.ard.utils.EntityManagerHelper;
import com.amical.ard.utils.CloudinaryUtil;
import com.cloudinary.utils.ObjectUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@WebServlet("/admin/ajouter-publication")
@MultipartConfig
public class AjouterPublicationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Object admin = session.getAttribute("utilisateurConnecte");

        if (admin == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try {
            // Infos auteur
            Integer auteurId = (Integer) admin.getClass().getMethod("getId").invoke(admin);
            String nom = (String) admin.getClass().getMethod("getNom").invoke(admin);
            String prenom = (String) admin.getClass().getMethod("getPrenom").invoke(admin);
            String role = (String) admin.getClass().getMethod("getRole").invoke(admin);

            // Upload Cloudinary
            Part imagePart = request.getPart("image");
            Map uploadResult = CloudinaryUtil.getCloudinary().uploader().upload(
                    imagePart.getInputStream(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("secure_url");

            // Sauvegarde Publication
            Publication publication = new Publication();
            publication.setDescription(request.getParameter("description"));
            publication.setImage(imageUrl); // URL Cloudinary
            publication.setAuteurId(auteurId.longValue());
            publication.setAuteurType("ADMIN");
            publication.setAuteurNom(nom);
            publication.setAuteurPrenom(prenom);
            publication.setAuteurRole(role);
            publication.setDatePublication(LocalDateTime.now());

            // Utilisation du filtre : Pas de em.close() ici
            PublicationDAO dao = new PublicationDAO(EntityManagerHelper.getEntityManager());
            dao.ajouter(publication);

            response.sendRedirect(request.getContextPath() + "/liste-publications");

        } catch (Exception e) {
            throw new ServletException("Erreur lors de l'ajout de la publication", e);
        }
    }
}