<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.amical.ard.entites.Etudiant" %>
<%@ page import="com.amical.ard.entites.Appartement" %>

<%
    List<Etudiant> etudiants = (List<Etudiant>) request.getAttribute("etudiants");
    List<Appartement> appartements = (List<Appartement>) request.getAttribute("appartements");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Loger des étudiants</title>

    <script src="https://cdn.tailwindcss.com"></script>
</head>

<body class="bg-gradient-to-r from-cyan-50 to-white p-4 sm:p-6 md:p-10">

<!-- TOP BUTTON -->
<div class="flex justify-end mb-4">
    <a href="<%= request.getContextPath() %>/redirect-to-dashboard"
       class="px-4 sm:px-5 py-2 bg-teal-700 hover:bg-teal-800 text-white text-sm font-semibold rounded-lg shadow">
        ← Retour au menu
    </a>
</div>

<!-- CONTAINER -->
<div class="max-w-3xl mx-auto bg-white rounded-2xl shadow-xl p-5 sm:p-8">

    <h2 class="text-xl sm:text-2xl font-bold text-center text-teal-700 mb-6">
        🛏 Loger des étudiants
    </h2>

    <form method="post" action="ajouter-logement">

        <!-- SEARCH -->
        <input type="text"
               id="searchInput"
               class="w-full p-3 border rounded-lg mb-4 text-sm sm:text-base"
               placeholder="Rechercher par nom, prénom ou téléphone..."
               onkeyup="filterStudents()">

        <!-- STUDENTS -->
        <label class="block mb-2 font-semibold text-sm sm:text-base">
            Sélectionnez les étudiants à loger :
        </label>

        <div id="studentsList"
             class="max-h-72 sm:max-h-96 overflow-y-auto border rounded-lg bg-gray-50 p-2">

            <% if (etudiants != null) {
                for (Etudiant e : etudiants) { %>

                <div class="student-item bg-white border rounded-lg p-3 mb-2">

                    <label class="flex items-center gap-3 cursor-pointer">

                        <input type="checkbox"
                               name="etudiantIds"
                               value="<%= e.getId() %>"
                               class="w-4 h-4">

                        <div class="flex flex-col sm:flex-row sm:items-center sm:gap-3">

                            <span class="font-bold text-gray-800 text-sm sm:text-base">
                                <%= e.getPrenom() %> <%= e.getNom() %>
                            </span>

                            <span class="text-gray-500 text-xs sm:text-sm">
                                <%= e.getTelephone() != null && !e.getTelephone().trim().isEmpty()
                                        ? e.getTelephone()
                                        : "N/A" %>
                            </span>

                        </div>

                    </label>

                </div>

            <% } } %>

        </div>

        <!-- APPARTEMENT -->
        <label class="block mt-5 mb-2 font-semibold text-sm sm:text-base">
            Choisir l'appartement :
        </label>

        <select name="appartementId"
                required
                class="w-full p-3 border rounded-lg text-sm sm:text-base">

            <option value="">-- Sélectionner un appartement --</option>

            <% if (appartements != null) {
                for (Appartement a : appartements) { %>

                <option value="<%= a.getId() %>">
                    <%= a.getNomAppartement() %>
                    <% if (a.getDescription() != null && !a.getDescription().trim().isEmpty()) { %>
                        - <%= a.getDescription() %>
                    <% } %>
                </option>

            <% } } %>

        </select>

        <!-- SUBMIT -->
        <button type="submit"
                class="w-full mt-6 bg-teal-600 hover:bg-teal-700 text-white font-bold py-3 rounded-xl text-sm sm:text-base">

            ✅ Loger les étudiants sélectionnés

        </button>

    </form>

</div>

<script>
function filterStudents() {
    const input = document.getElementById("searchInput").value.toLowerCase();
    const items = document.querySelectorAll(".student-item");

    items.forEach(item => {
        const text = item.textContent.toLowerCase();
        item.style.display = text.includes(input) ? "block" : "none";
    });
}
</script>

</body>
</html>