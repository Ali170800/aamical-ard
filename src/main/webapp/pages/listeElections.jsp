<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr" class="h-full bg-gray-50">
<head>
    <meta charset="UTF-8">
    <title>Élections disponibles</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="p-10">
    <div class="max-w-4xl mx-auto">
        <h1 class="text-3xl font-black mb-8">Élections disponibles</h1>
        <div class="space-y-6">
            <c:choose>
                <c:when test="${not empty listeElections}">
                    <c:forEach var="election" items="${listeElections}">
                        <div class="bg-white p-6 rounded-2xl shadow-lg border border-gray-100 flex justify-between items-center">
                            <div>
                                <h2 class="font-black text-xl text-gray-900">${election.titre}</h2>
                                <p class="text-sm text-gray-500 mt-1">Du ${election.dateDebut} au ${election.dateFin}</p>
                            </div>
                            <!-- Redirection corrigée vers voter.jsp -->
                            <a href="${pageContext.request.contextPath}/pages/voter.jsp?electionId=${election.id}"
                               class="bg-indigo-600 text-white px-6 py-3 rounded-xl font-bold hover:bg-indigo-700">
                                Voter maintenant
                            </a>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <p class="text-center text-gray-500">Aucune élection en cours.</p>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</body>
</html>