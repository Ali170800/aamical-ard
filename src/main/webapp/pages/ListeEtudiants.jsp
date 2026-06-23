<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Liste des Élections</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 p-8">

<div class="max-w-4xl mx-auto">
    <h1 class="text-3xl font-bold mb-6">Liste des Élections</h1>

    <c:forEach var="election" items="${listeElections}">
        <div class="bg-white p-6 rounded-lg shadow mb-4">
            <h2 class="font-bold text-xl">${election.titre}</h2>
            <p class="text-gray-600 mb-4">ID : ${election.id}</p>

            <!-- C'est ce formulaire qui fait marcher le vote -->
            <form action="${pageContext.request.contextPath}/api/voter" method="POST">
                <input type="hidden" name="electionId" value="${election.id}">

                <!-- Demander l'ID du candidat (ou sélectionnez-le ici) -->
                <label class="block text-sm font-bold mb-1">ID du candidat :</label>
                <input type="number" name="candidatId" required class="border p-2 rounded mb-2 w-full">

                <button type="submit" class="bg-indigo-600 text-white px-4 py-2 rounded font-bold hover:bg-indigo-700">
                    Voter maintenant
                </button>
            </form>
        </div>
    </c:forEach>
</div>

</body>
</html>