<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr" class="h-full bg-gray-50">
<head>
    <meta charset="UTF-8">
    <title>Élections en cours - Espace Étudiant</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="p-10">
    <div class="max-w-4xl mx-auto">
        <h1 class="text-3xl font-black mb-8">Élections disponibles</h1>

        <div class="space-y-6">
            <c:choose>
                <%-- Vérifie si la liste n'est pas nulle et contient des éléments --%>
                <c:when test="${not empty listeElections}">
                    <c:forEach var="election" items="${listeElections}">
                        <div class="bg-white p-6 rounded-2xl shadow-lg border border-gray-100 flex justify-between items-center">
                            <div>
                                <h2 class="font-black text-xl text-gray-900">${election.titre}</h2>
                                <p class="text-sm text-gray-500 mt-1">
                                    Du ${election.dateDebut} au ${election.dateFin}
                                </p>
                            </div>
                            <%-- Lien corrigé : utilise bien le contextPath pour éviter les erreurs 404 --%>
                            <a href="${pageContext.request.contextPath}/etudiant/voter?electionId=${election.id}"
                               class="bg-indigo-600 text-white px-6 py-3 rounded-xl font-bold hover:bg-indigo-700 transition-colors">
                                Voter maintenant
                            </a>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="text-center py-20 bg-white rounded-2xl border-2 border-dashed border-gray-200">
                        <p class="text-gray-500 font-bold">Aucune élection en cours pour le moment.</p>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</body>
</html>