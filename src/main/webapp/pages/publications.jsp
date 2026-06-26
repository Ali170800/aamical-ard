<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, com.amical.ard.entites.*, java.time.format.DateTimeFormatter" %>

<%
    List<Publication> publications = (List<Publication>) request.getAttribute("publications");
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    Utilisateur user = (Utilisateur) session.getAttribute("utilisateurConnecte");
    Etudiant etudiant = (Etudiant) session.getAttribute("etudiantConnecte");

    boolean peutAjouter = (user != null);

    // LOGIQUE DE REDIRECTION PAR RÔLE
    String urlSortie = "login.jsp"; // Par défaut
    if (etudiant != null) {
        urlSortie = "pages/espaceEtudiant.jsp";
    } else if (user != null) {
        String role = user.getRole();
        if ("ADMIN".equals(role)) {
            urlSortie = "pages/cahier.jsp";
        } else if ("PCO".equals(role)) {
            urlSortie = "pages/pco-dashboard.jsp";
        } else if ("PCS".equals(role)) {
            urlSortie = "pages/pcs-dashboard.jsp";
        } else {
            urlSortie = "pages/espaceAdmin.jsp";
        }
    }
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Communauté AERD</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
</head>

<body class="bg-gray-100">

<div class="max-w-4xl mx-auto py-6 sm:py-10 px-4 relative">

    <!-- BOUTON SORTIE CONDITIONNEL -->
    <% if (user != null || etudiant != null) { %>
    <div class="absolute top-6 right-4">
        <a href="<%= request.getContextPath() %>/<%= urlSortie %>"
           class="bg-gray-200 text-gray-700 px-4 py-2 rounded-xl font-bold hover:bg-gray-300 transition text-sm">
           ⬅ Sortie
        </a>
    </div>
    <% } %>

    <!-- HEADER -->
    <div class="flex flex-col sm:flex-row sm:justify-between sm:items-center gap-4 mb-8 mt-12 sm:mt-0">
        <h1 class="text-2xl sm:text-3xl font-bold text-gray-800">
            Fil d'actualité
        </h1>

        <% if (peutAjouter) { %>
            <a href="<%= request.getContextPath() %>/upload/ajouterPublication.jsp"
               class="bg-blue-600 text-white px-5 sm:px-6 py-2 rounded-xl font-bold hover:bg-blue-700 shadow-lg transition text-center">
                + Ajouter publication
            </a>
        <% } %>
    </div>

    <!-- PUBLICATIONS -->
    <% if (publications != null && !publications.isEmpty()) { %>
        <% for (Publication p : publications) {
            String auteur = (String) request.getAttribute("auteur_publication_" + p.getId());
            String role = (String) request.getAttribute("role_auteur_" + p.getId());
            String datePub = (String) request.getAttribute("date_publication_" + p.getId());
            Long auteurId = p.getAuteurId();
        %>

        <div class="bg-white rounded-3xl shadow-lg mb-8 sm:mb-10 overflow-hidden">
            <img src="<%= p.getImage() %>" class="w-full h-auto object-contain bg-gray-50" alt="Image">

            <div class="p-4 sm:p-6">
                <div class="flex flex-col sm:flex-row sm:justify-between sm:items-start gap-4 mb-4">
                    <div class="flex items-center">
                        <i class="fas fa-user-circle text-2xl sm:text-3xl text-blue-600 mr-3"></i>
                        <div>
                            <p class="font-bold text-gray-900 break-words">
                                <%= (auteur != null) ? auteur : "Utilisateur" %>
                                <% if (role != null && !role.isEmpty()) { %>
                                    <span class="text-gray-400 font-normal">-</span>
                                    <span class="text-blue-600 text-xs sm:text-sm font-semibold uppercase tracking-wider">
                                        <%= role %>
                                    </span>
                                <% } %>
                            </p>
                            <p class="text-xs text-gray-400 mt-1"><%= (datePub != null) ? datePub : "" %></p>
                        </div>
                    </div>

                    <% if (user != null && auteurId != null && user.getId().longValue() == auteurId.longValue()) { %>
                        <form action="<%= request.getContextPath() %>/admin/supprimer-publication" method="POST" onsubmit="return confirm('Supprimer ?');" class="self-start sm:self-auto">
                            <input type="hidden" name="id" value="<%= p.getId() %>">
                            <button type="submit" class="text-red-500 hover:text-red-700 font-bold text-sm">
                                <i class="fas fa-trash"></i> Supprimer
                            </button>
                        </form>
                    <% } %>
                </div>

                <p class="text-gray-700 text-base sm:text-lg mb-4 break-words"><%= p.getDescription() != null ? p.getDescription() : "" %></p>

                <div class="flex flex-col sm:flex-row gap-4 sm:gap-6 border-t pt-4">
                    <button onclick="liker('<%= p.getId() %>')" class="font-bold text-red-500 text-sm sm:text-base text-left sm:text-center">
                        <i class="fas fa-heart"></i> J’aime (<span id="like-count-<%= p.getId() %>"><%= p.getNombreLikes() %></span>)
                    </button>
                    <button onclick="document.getElementById('com-<%= p.getId() %>').classList.toggle('hidden')" class="font-bold text-blue-600 text-sm sm:text-base text-left sm:text-center">
                        <i class="fas fa-comments"></i> Commentaires (<%= (p.getCommentaires() != null) ? p.getCommentaires().size() : 0 %>)
                    </button>
                </div>

                <!-- COMMENTAIRES -->
                <div id="com-<%= p.getId() %>" class="hidden mt-6 bg-gray-50 p-4 rounded-2xl">
                    <form action="<%= request.getContextPath() %>/etudiant/commenter-publication" method="POST" class="mb-4">
                        <input type="hidden" name="publicationId" value="<%= p.getId() %>">
                        <textarea name="commentaire" required placeholder="Écrire un commentaire..." class="w-full p-3 border rounded-xl text-sm sm:text-base" rows="2"></textarea>
                        <button type="submit" class="mt-2 bg-blue-600 text-white px-4 py-2 rounded-lg text-sm">Publier</button>
                    </form>

                    <div class="space-y-3 max-h-60 overflow-y-auto">
                        <% if (p.getCommentaires() != null) {
                            for (CommentairePublication c : p.getCommentaires()) { %>
                                <div class="border-b pb-2">
                                    <p class="font-bold text-sm text-blue-800">
                                        <%= request.getAttribute("auteur_commentaire_" + c.getId()) != null ? request.getAttribute("auteur_commentaire_" + c.getId()) : "Utilisateur" %>
                                    </p>
                                    <p class="text-gray-700 text-sm break-words"><%= c.getCommentaire() %></p>
                                    <p class="text-[10px] text-gray-400"><%= c.getDateCommentaire() != null ? c.getDateCommentaire().format(dtf) : "" %></p>
                                </div>
                        <% } } %>
                    </div>
                </div>
            </div>
        </div>
        <% } %>
    <% } %>
</div>

<script>
async function liker(id) {
    const res = await fetch('<%= request.getContextPath() %>/like-publication', {
        method: 'POST',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: 'publicationId=' + id
    });
    if (res.ok) {
        const d = await res.json();
        document.getElementById('like-count-' + id).innerText = d.nouveauTotal;
    }
}
</script>
</body>
</html>