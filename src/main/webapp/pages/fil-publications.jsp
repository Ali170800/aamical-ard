<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.amical.ard.entites.Publication" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    List<Publication> publications = (List<Publication>) request.getAttribute("publications");
    boolean estAdmin = session.getAttribute("utilisateurConnecte") != null;
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Communauté AERD</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
    <style>
        .publication-card { transition: 0.3s; }
        .publication-card:hover { transform: translateY(-3px); }
    </style>
</head>
<body class="bg-gray-100">

<div class="max-w-4xl mx-auto py-10 px-4">
    <div class="flex justify-between items-center mb-10">
        <div>
            <h1 class="text-4xl font-black text-gray-800">Fil communautaire AERD</h1>
        </div>
        <% if (estAdmin) { %>
        <a href="<%= request.getContextPath() %>/upload/ajouterPublication.jsp" class="bg-blue-600 hover:bg-blue-700 text-white px-6 py-3 rounded-2xl font-semibold shadow">
            <i class="fas fa-plus mr-2"></i> Nouvelle publication
        </a>
        <% } %>
    </div>

    <% if (publications != null && !publications.isEmpty()) { %>
        <% for (Publication publication : publications) { %>
        <div class="publication-card bg-white rounded-3xl shadow-lg overflow-hidden mb-10">
            <img src="<%= request.getContextPath() %>/uploads/<%= publication.getImage() %>" class="w-full h-auto">

            <div class="p-6">
                <p class="text-gray-800 text-lg mb-6"><%= publication.getDescription() %></p>

                <div class="flex items-center gap-6 mt-6">
                    <button id="like-btn-<%= publication.getId() %>"
                            onclick="likerPublication(<%= publication.getId() %>)"
                            class="flex items-center gap-2 text-blue-600 font-semibold transition">
                        <i class="fas fa-thumbs-up"></i> <span>J'aime</span>
                    </button>

                    <button onclick="ajouterCommentaire(<%= publication.getId() %>)"
                            class="flex items-center gap-2 text-green-600 font-semibold transition">
                        <i class="fas fa-comment"></i> <span>Commenter</span>
                    </button>
                </div>

                <div id="zone-commentaires-<%= publication.getId() %>" class="mt-4 space-y-2"></div>
            </div>
        </div>
        <% } %>
    <% } %>
</div>

<script>
    function likerPublication(pubId) {
        // UI Instantanée
        const btn = document.getElementById('like-btn-' + pubId);
        btn.classList.replace('text-blue-600', 'text-blue-900');
        btn.querySelector('span').innerText = 'Liké';

        // Appel silencieux
        fetch('<%= request.getContextPath() %>/like-publication', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: 'publicationId=' + pubId
        });
    }

    function ajouterCommentaire(pubId) {
        const texte = prompt("Votre commentaire :");
        if (!texte || texte.trim() === '') return;

        // UI Instantanée
        const zone = document.getElementById('zone-commentaires-' + pubId);
        const div = document.createElement('div');
        div.className = "bg-gray-100 p-3 rounded-xl text-sm";
        div.innerText = texte;
        zone.appendChild(div);

        // Appel silencieux
        fetch('<%= request.getContextPath() %>/etudiant/commenter-publication', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: 'publicationId=' + pubId + '&commentaire=' + encodeURIComponent(texte)
        });
    }
</script>
</body>
</html>