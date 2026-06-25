<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="fr" class="h-full bg-gray-50">

<head>

    <meta charset="UTF-8">

    <!-- Responsive -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Élections disponibles</title>

    <script src="https://cdn.tailwindcss.com"></script>

</head>

<body class="min-h-screen bg-gray-50 p-4 sm:p-6 md:p-8 lg:p-10">

<div class="max-w-4xl mx-auto">

    <h1 class="text-2xl sm:text-3xl font-black mb-6 sm:mb-8">
        Élections disponibles
    </h1>

    <div class="space-y-4 sm:space-y-6">

        <c:choose>

            <c:when test="${not empty listeElections}">

                <c:forEach var="election" items="${listeElections}">

                    <div class="bg-white p-4 sm:p-6 rounded-2xl shadow-lg border border-gray-100">

                        <div class="flex flex-col md:flex-row md:justify-between md:items-center gap-4">

                            <div class="min-w-0">

                                <h2 class="font-black text-lg sm:text-xl text-gray-900 break-words">
                                    ${election.titre}
                                </h2>

                                <p class="text-sm text-gray-500 mt-1 break-words">
                                    Du ${election.dateDebut} au ${election.dateFin}
                                </p>

                            </div>

                            <!-- Redirection corrigée vers voter.jsp -->
                            <a href="${pageContext.request.contextPath}/pages/voter.jsp?electionId=${election.id}"
                               class="w-full md:w-auto text-center bg-indigo-600 text-white px-6 py-3 rounded-xl font-bold hover:bg-indigo-700 transition-colors duration-300">

                                Voter maintenant

                            </a>

                        </div>

                    </div>

                </c:forEach>

            </c:when>

            <c:otherwise>

                <div class="bg-white rounded-2xl shadow p-6 text-center">

                    <p class="text-gray-500">
                        Aucune élection en cours.
                    </p>

                </div>

            </c:otherwise>

        </c:choose>

    </div>

</div>

</body>
</html>