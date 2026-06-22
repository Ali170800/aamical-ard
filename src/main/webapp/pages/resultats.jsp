<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr" class="h-full bg-gray-50">
<head>
    <meta charset="UTF-8">
    <title>Résultats - ${election.titre}</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
</head>
<body class="p-10">

<div class="max-w-4xl mx-auto">
    <!-- Header -->
    <div class="mb-10">
        <a href="<%= request.getContextPath() %>/admin/dashboard-elections" class="text-indigo-600 text-xs font-bold hover:underline mb-4 block">
            <i class="fas fa-arrow-left mr-2"></i> Retour au tableau de bord
        </a>
        <h1 class="text-3xl font-black text-gray-800">${election.titre}</h1>
        <p class="text-gray-500 mt-2">${election.description}</p>
    </div>

    <!-- Stats Globales -->
    <div class="grid grid-cols-1 md:grid-cols-2 gap-6 mb-10">
        <div class="bg-indigo-600 p-6 rounded-2xl text-white shadow-lg shadow-indigo-200">
            <p class="text-indigo-200 text-xs font-bold uppercase mb-1">Nombre total de votants</p>
            <h3 class="text-4xl font-black">${totalVotes}</h3>
        </div>
        <div class="bg-white p-6 rounded-2xl border border-gray-100 shadow-sm">
            <p class="text-gray-400 text-xs font-bold uppercase mb-1">État du scrutin</p>
            <h3 class="text-xl font-bold text-gray-800">${estTermine ? 'Terminé' : 'En cours'}</h3>
        </div>
    </div>

    <!-- Liste Candidats -->
    <div class="bg-white p-8 rounded-3xl shadow-sm border border-gray-100">
        <h2 class="text-lg font-black text-gray-800 mb-6">Scores des candidats</h2>

        <div class="space-y-6">
            <c:forEach var="candidat" items="${resultatsCandidats}">
                <div>
                    <div class="flex justify-between text-xs font-bold text-gray-700 mb-2">
                        <span>${candidat.nom} ${candidat.prenom}</span>
                        <span>${candidat.pourcentage}% (${candidat.nbVoix} voix)</span>
                    </div>
                    <div class="w-full bg-gray-100 rounded-full h-4">
                        <div class="bg-indigo-600 h-4 rounded-full transition-all duration-700"
                             style="width: ${candidat.pourcentage}%"></div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>

</body>
</html>