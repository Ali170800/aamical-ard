<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Gestion des PDF - AERD</title>

    <script src="https://cdn.tailwindcss.com"></script>

    <style>
        body {
            background: #f3f4f6;
        }
    </style>
</head>

<body class="min-h-screen p-8">

<div class="max-w-7xl mx-auto">

    <!-- HEADER -->
    <div class="flex justify-between items-center mb-8">

        <div>
            <h1 class="text-4xl font-black text-gray-800">
                Gestion des PDF
            </h1>

            <p class="text-gray-600 mt-2">
                Archivage des bilans, rapports, comptes rendus et documents officiels
            </p>
        </div>

        <!-- AJOUT -->
        <a href="${pageContext.request.contextPath}/fichiers/upload"
           class="bg-indigo-600 hover:bg-indigo-700 text-white px-6 py-3 rounded-2xl shadow font-semibold">
            + Ajouter un PDF
        </a>

    </div>

    <!-- MESSAGE -->
    <c:if test="${not empty success}">
        <div class="bg-green-100 text-green-700 p-4 rounded-xl mb-5">
            ${success}
        </div>
    </c:if>

    <c:if test="${not empty erreur}">
        <div class="bg-red-100 text-red-700 p-4 rounded-xl mb-5">
            ${erreur}
        </div>
    </c:if>

    <!-- AUCUN FICHIER -->
    <c:if test="${empty fichiers}">

        <div class="bg-white rounded-3xl shadow p-12 text-center">

            <div class="text-6xl mb-5">
                📄
            </div>

            <h2 class="text-2xl font-bold text-gray-700 mb-3">
                Aucun document PDF disponible
            </h2>

            <p class="text-gray-500 mb-6">
                Aucun bilan, rapport ou compte rendu n’a encore été ajouté.
            </p>

            <a href="${pageContext.request.contextPath}/fichiers/upload"
               class="bg-indigo-600 hover:bg-indigo-700 text-white px-6 py-3 rounded-2xl shadow font-semibold">
                Ajouter le premier PDF
            </a>

        </div>

    </c:if>

    <!-- LISTE -->
    <c:if test="${not empty fichiers}">

        <div class="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-6">

            <c:forEach var="fichier" items="${fichiers}">

                <div class="bg-white rounded-3xl shadow p-6 flex flex-col justify-between">

                    <div>

                        <div class="text-5xl mb-5 text-red-600">
                            📕
                        </div>

                        <h2 class="text-xl font-bold text-gray-800 mb-4">
                            ${fichier.titre}
                        </h2>

                        <div class="space-y-2 text-sm text-gray-600">

                            <p>
                                <strong>Nom fichier :</strong>
                                ${fichier.nomFichier}
                            </p>

                            <p>
                                <strong>Auteur :</strong>
                                ${fichier.nomAuteur}
                            </p>

                            <p>
                                <strong>Commission :</strong>
                                ${fichier.roleAuteur}
                            </p>

                            <p>
                                <strong>Type :</strong>
                                ${fichier.typeFichier}
                            </p>

                            <p>
                                <strong>Date :</strong>

                                <fmt:formatDate
                                        value="${fichier.dateUpload}"
                                        pattern="dd/MM/yyyy HH:mm"/>
                            </p>

                        </div>

                    </div>

                    <!-- ACTIONS -->
                    <div class="flex gap-3 mt-6">

                        <!-- OUVRIR -->
                        <a href="${pageContext.request.contextPath}/fichiers/ouvrir?id=${fichier.id}"
                           target="_blank"
                           class="flex-1 text-center bg-blue-600 hover:bg-blue-700 text-white py-2 rounded-xl">
                            Ouvrir
                        </a>

                        <!-- MODIFIER -->
                        <a href="${pageContext.request.contextPath}/fichiers/modifier?id=${fichier.id}"
                           class="flex-1 text-center bg-green-600 hover:bg-green-700 text-white py-2 rounded-xl">
                            Modifier
                        </a>

                        <!-- SUPPRIMER -->
                        <a href="${pageContext.request.contextPath}/fichiers/supprimer?id=${fichier.id}"
                           onclick="return confirm('Supprimer ce document PDF ?')"
                           class="flex-1 text-center bg-red-600 hover:bg-red-700 text-white py-2 rounded-xl">
                            Supprimer
                        </a>

                    </div>

                </div>

            </c:forEach>

        </div>

    </c:if>

    <!-- RETOUR -->
    <div class="mt-10">

        <a href="${pageContext.request.contextPath}/redirect-to-dashboard"
           class="inline-block bg-gray-800 hover:bg-black text-white px-6 py-3 rounded-2xl">
            ← Retour au dashboard
        </a>

    </div>

</div>

</body>
</html>