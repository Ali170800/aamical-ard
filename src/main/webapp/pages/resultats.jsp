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
    <div class="mb-10">
        <h1 class="text-3xl font-black text-gray-800">${election.titre}</h1>
    </div>

    <!-- Stats Globales -->
    <div class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-10">
        <div class="bg-indigo-600 p-6 rounded-2xl text-white shadow-lg">
            <p class="text-indigo-200 text-xs font-bold uppercase">Total votants</p>
            <h3 class="text-4xl font-black mt-2">${totalVotes}</h3>
        </div>
        <div class="bg-white p-6 rounded-2xl border border-gray-100 shadow-sm">
            <p class="text-gray-400 text-xs font-bold uppercase">État</p>
            <h3 class="text-xl font-bold text-gray-800">${estTermine ? 'Terminé' : 'En cours'}</h3>
        </div>
        <div class="bg-white p-6 rounded-2xl border border-gray-100 shadow-sm">
            <p class="text-gray-400 text-xs font-bold uppercase">Temps restant</p>
            <h3 class="text-xl font-bold text-indigo-600 mt-2">
                <i class="fas fa-hourglass-half mr-2"></i>${tempsRestant}
            </h3>
        </div>
    </div>

    <!-- Scores -->
    <div class="bg-white p-8 rounded-3xl shadow-sm border border-gray-100">
        <h2 class="text-lg font-black text-gray-800 mb-6">Scores des candidats</h2>
        <div class="space-y-6">
            <c:forEach var="c" items="${resultatsCandidats}">
                <div>
                    <div class="flex justify-between text-xs font-bold text-gray-700 mb-2">
                        <span>${c.prenom} ${c.nom}</span>
                        <span>${c.pourcentage}% (${c.nbVoix} voix)</span>
                    </div>
                    <div class="w-full bg-gray-100 rounded-full h-4">
                        <div class="bg-indigo-600 h-4 rounded-full" style="width: ${c.pourcentage}%"></div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
</body>
</html>