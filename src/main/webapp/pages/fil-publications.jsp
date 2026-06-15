
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.amical.ard.entites.Publication" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
List<Publication> publications =
(List<Publication>) request.getAttribute("publications");

boolean estAdmin =
        session.getAttribute("utilisateurConnecte") != null;
%>

<!DOCTYPE html>

<html lang="fr">

<head>

<meta charset="UTF-8">

<title>
    Communauté AERD
</title>

<script src="https://cdn.tailwindcss.com"></script>

<link
        href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"
        rel="stylesheet">

<style>

    body {
        background: #f3f4f6;
    }

    .publication-card {
        transition: 0.3s;
    }

    .publication-card:hover {
        transform: translateY(-3px);
    }

</style>

</head>

<body>

<div class="max-w-4xl mx-auto py-10 px-4">

<!-- HEADER -->

<div class="flex justify-between items-center mb-10">

    <div>

        <h1 class="text-4xl font-black text-gray-800">
            Fil communautaire AERD
        </h1>

        <p class="text-gray-500 mt-2">
            Publications, activités, événements et graduations
        </p>

    </div>

    <!-- ADMIN -->

    <% if (estAdmin) { %>

    <a href="<%= request.getContextPath() %>/upload/ajouterPublication.jsp"
       class="bg-blue-600 hover:bg-blue-700 text-white px-6 py-3 rounded-2xl font-semibold shadow">

        <i class="fas fa-plus mr-2"></i>

        Nouvelle publication

    </a>

    <% } %>

</div>

<!-- LISTE DES PUBLICATIONS -->

<% if (publications != null && !publications.isEmpty()) { %>

    <% for (Publication publication : publications) { %>

        <div class="publication-card bg-white rounded-3xl shadow-lg overflow-hidden mb-10">

            <!-- IMAGE -->

            <div class="bg-black flex items-center justify-center">
<img
    src="<%= request.getContextPath() %>/uploads/<%= publication.getImage() %>"
    class="w-full h-auto">
     </div>

            <!-- CONTENU -->

            <div class="p-6">

                <!-- DESCRIPTION -->

                <p class="text-gray-800 text-lg leading-relaxed mb-6">
                    <%= publication.getDescription() %>
                </p>

                <!-- DATE + SUPPRESSION -->

                <div class="flex items-center justify-between border-t pt-4">

                    <div class="text-sm text-gray-500">

                        <i class="fas fa-clock mr-1"></i>

                        Publication du :
                        <%= publication.getDatePublication() %>

                    </div>

                    <!-- SUPPRESSION ADMIN -->

                    <c:if test="${utilisateurConnecte.role == 'ADMIN'}">

                        <a href="<%= request.getContextPath() %>/admin/supprimer-publication?id=<%= publication.getId() %>"
                           onclick="return confirm('Voulez-vous vraiment supprimer cette publication ?')"
                           class="bg-red-600 hover:bg-red-700 text-white px-4 py-2 rounded-xl text-xs font-bold">

                            <i class="fas fa-trash mr-1"></i>

                            Supprimer

                        </a>

                    </c:if>

                </div>

                <!-- ACTIONS -->

                <div class="flex items-center gap-6 mt-6">

                    <!-- LIKE -->

                    <button
                            class="flex items-center gap-2 text-blue-600 hover:text-blue-800 font-semibold">

                        <i class="fas fa-thumbs-up"></i>

                        J’aime

                    </button>

                    <!-- COMMENTAIRES -->

                    <button
                            class="flex items-center gap-2 text-green-600 hover:text-green-800 font-semibold">

                        <i class="fas fa-comment"></i>

                        Commentaires

                    </button>

                </div>

            </div>

        </div>

    <% } %>

<% } else { %>

    <!-- AUCUNE PUBLICATION -->

    <div class="bg-white rounded-3xl shadow p-16 text-center">

        <div class="text-6xl text-gray-300 mb-6">

            <i class="fas fa-image"></i>

        </div>

        <h2 class="text-2xl font-bold text-gray-700 mb-3">
            Aucune publication disponible
        </h2>

        <p class="text-gray-500">
            Les publications de la communauté apparaîtront ici.
        </p>

    </div>

<% } %>

</div>

</body>

</html>
```
