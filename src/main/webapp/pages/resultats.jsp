<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="fr" class="h-full bg-gray-50">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Résultats - ${election.titre}</title>

    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
</head>

<body class="p-4 sm:p-6 md:p-10">

<div class="max-w-4xl mx-auto relative">

    <!-- Bouton Sortie ajouté -->
    <div class="absolute top-0 right-0">
        <a href="${pageContext.request.contextPath}/pages/dashboard-elections"
           class="bg-gray-200 text-gray-700 px-4 py-2 rounded-xl font-bold hover:bg-gray-300 transition text-sm">
           ⬅ Sortie
        </a>
    </div>

    <!-- TITRE -->
    <div class="mb-6 sm:mb-10 mt-12 sm:mt-0">
        <h1 class="text-2xl sm:text-3xl font-black text-gray-800 break-words">
            ${election.titre}
        </h1>
    </div>

    <!-- STATS GLOBAL -->
    <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-4 sm:gap-6 mb-8 sm:mb-10">

        <!-- TOTAL VOTANTS -->
        <div class="bg-indigo-600 p-5 sm:p-6 rounded-2xl text-white shadow-lg">

            <p class="text-indigo-200 text-[10px] sm:text-xs font-bold uppercase">
                Total votants
            </p>

            <h3 class="text-3xl sm:text-4xl font-black mt-2">
                ${totalVotes}
            </h3>

        </div>

        <!-- ETAT -->
        <div class="bg-white p-5 sm:p-6 rounded-2xl border border-gray-100 shadow-sm">

            <p class="text-gray-400 text-[10px] sm:text-xs font-bold uppercase">
                État
            </p>

            <h3 class="text-lg sm:text-xl font-bold text-gray-800 break-words">
                ${estTermine ? 'Terminé' : 'En cours'}
            </h3>

        </div>

        <!-- TEMPS RESTANT -->
        <div class="bg-white p-5 sm:p-6 rounded-2xl border border-gray-100 shadow-sm">

            <p class="text-gray-400 text-[10px] sm:text-xs font-bold uppercase">
                Temps restant
            </p>

            <h3 class="text-lg sm:text-xl font-bold text-indigo-600 mt-2 break-words flex items-center gap-2">
                <i class="fas fa-hourglass-half"></i>
                <span>${tempsRestant}</span>
            </h3>

        </div>

    </div>

    <!-- RESULTATS -->
    <div class="bg-white p-5 sm:p-6 md:p-8 rounded-3xl shadow-sm border border-gray-100">

        <h2 class="text-base sm:text-lg font-black text-gray-800 mb-6">
            Scores des candidats
        </h2>

        <div class="space-y-5 sm:space-y-6">

            <c:forEach var="c" items="${resultatsCandidats}">

                <div>

                    <div class="flex flex-col sm:flex-row sm:justify-between gap-1 text-xs font-bold text-gray-700 mb-2">

                        <span class="break-words">
                            ${c.prenom} ${c.nom}
                        </span>

                        <span class="break-words">
                            ${c.pourcentage}% (${c.nbVoix} voix)
                        </span>

                    </div>

                    <div class="w-full bg-gray-100 rounded-full h-4 overflow-hidden">

                        <div class="bg-indigo-600 h-4 rounded-full transition-all duration-500"
                             style="width: ${c.pourcentage}%"></div>

                    </div>

                </div>

            </c:forEach>

        </div>

    </div>

</div>

</body>
</html>