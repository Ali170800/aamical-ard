<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="fr" class="h-full bg-gray-50">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Suivi des Scrutins - AERD</title>

    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
</head>

<body class="p-4 sm:p-6 md:p-10">

<div class="max-w-6xl mx-auto">

    <h1 class="text-2xl sm:text-3xl font-black text-gray-800 mb-2 break-words">
        Tableau de bord des votes
    </h1>

    <p class="text-sm sm:text-base text-gray-500 mb-6 sm:mb-8">
        Suivi en temps réel de la participation aux élections.
    </p>

    <div class="grid grid-cols-1 gap-4 sm:gap-6">

        <c:choose>
            <c:when test="${not empty listeElections}">

                <c:forEach var="election" items="${listeElections}">

                    <div class="bg-white p-4 sm:p-6 rounded-3xl shadow-sm border border-gray-100">

                        <!-- HEADER CARD -->
                        <div class="flex flex-col sm:flex-row sm:justify-between sm:items-start gap-3 mb-6">

                            <div>
                                <h2 class="text-lg sm:text-xl font-bold text-gray-800 break-words">
                                    ${election.titre}
                                </h2>

                                <p class="text-[10px] sm:text-xs text-gray-400 uppercase font-bold tracking-wider mt-1">
                                    Du ${election.dateDebut} au ${election.dateFin}
                                </p>
                            </div>

                            <span class="self-start sm:self-auto px-3 py-1 bg-green-100 text-green-700 text-[10px] font-black rounded-full uppercase">
                                En cours
                            </span>

                        </div>

                        <!-- PROGRESS -->
                        <div class="space-y-2">

                            <div class="flex flex-col sm:flex-row sm:justify-between gap-1 text-xs font-bold text-gray-600">
                                <span>Participation</span>

                                <span class="break-words">
                                    ${empty election.tauxParticipation ? 0 : election.tauxParticipation}%
                                    (${election.nbVotes} / ${election.totalEtudiants})
                                </span>
                            </div>

                            <div class="w-full bg-gray-100 rounded-full h-3 overflow-hidden">
                                <div class="bg-indigo-600 h-3 rounded-full transition-all duration-500"
                                     style="width: ${empty election.tauxParticipation ? 0 : election.tauxParticipation}%"></div>
                            </div>

                        </div>

                        <!-- ACTIONS -->
                        <div class="mt-6 pt-6 border-t border-gray-50 flex flex-col sm:flex-row gap-3">

                            <a href="${pageContext.request.contextPath}/admin/resultats?id=${election.id}"
                               class="text-[10px] sm:text-xs font-bold uppercase py-2 px-4 bg-gray-900 text-white rounded-lg hover:bg-indigo-600 transition-colors text-center">

                                Voir les résultats détaillés

                            </a>

                            <a href="${pageContext.request.contextPath}/admin/supprimer-election?id=${election.id}"
                               onclick="return confirm('Êtes-vous sûr de vouloir supprimer cette élection ? Cette action est irréversible.')"
                               class="text-[10px] sm:text-xs font-bold uppercase py-2 px-4 bg-red-50 text-red-600 rounded-lg hover:bg-red-600 hover:text-white transition-colors text-center">

                                Supprimer

                            </a>

                        </div>

                    </div>

                </c:forEach>

            </c:when>

            <c:otherwise>

                <div class="text-center py-16 sm:py-20 bg-white rounded-3xl border-2 border-dashed border-gray-200">
                    <p class="text-gray-500 font-bold text-sm sm:text-base">
                        Aucune élection trouvée.
                    </p>
                </div>

            </c:otherwise>

        </c:choose>

    </div>

</div>

</body>
</html>