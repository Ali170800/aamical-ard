<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.amical.ard.entites.LogementEtudiant" %>
<%@ page import="com.amical.ard.entites.Etudiant" %>

<%
    List<LogementEtudiant> logements =
            (List<LogementEtudiant>) request.getAttribute("logements");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>💸 Ajouter un Paiement</title>

    <script src="https://cdn.tailwindcss.com"></script>
</head>

<body class="bg-gray-100 p-4 sm:p-6 md:p-10">

<!-- TOP BAR -->
<div class="flex justify-end mb-4">
    <a href="<%= request.getContextPath() %>/listePaiements"
       class="px-4 sm:px-5 py-2 bg-blue-700 hover:bg-blue-800 text-white text-sm font-semibold rounded-lg shadow">
        Voir les paiements
    </a>
</div>

<!-- CONTAINER -->
<div class="max-w-4xl mx-auto bg-white p-5 sm:p-8 rounded-2xl shadow">

    <h2 class="text-xl sm:text-2xl font-bold text-center text-blue-700 mb-6">
        💸 Ajouter un Paiement
    </h2>

    <!-- MESSAGE ERROR -->
    <%
        String error = (String) session.getAttribute("error");
        if (error != null) {
    %>
        <div class="bg-red-100 text-red-700 p-4 rounded-lg mb-4 text-sm sm:text-base">
            <%= error %>
        </div>
    <%
            session.removeAttribute("error");
        }
    %>

    <!-- MESSAGE SUCCESS -->
    <%
        String success = (String) session.getAttribute("success");
        if (success != null) {
    %>
        <div class="bg-green-100 text-green-700 p-4 rounded-lg mb-4 text-sm sm:text-base">
            <%= success %>
        </div>
    <%
            session.removeAttribute("success");
        }
    %>

    <form action="<%= request.getContextPath() %>/ajouterPaiement"
          method="post">

        <!-- SEARCH -->
        <input type="text"
               id="searchInput"
               class="w-full p-3 border rounded-lg mb-4 text-sm sm:text-base"
               placeholder="Rechercher par nom, prénom, téléphone..."
               onkeyup="filterStudents()">

        <!-- LISTE ETUDIANTS -->
        <label class="block mb-2 font-semibold text-sm sm:text-base">
            Sélectionnez les étudiants à payer :
        </label>

        <div id="studentsList"
             class="max-h-72 sm:max-h-96 overflow-y-auto border rounded-lg bg-gray-50 p-2">

            <%
                if (logements != null) {

                    for (LogementEtudiant l : logements) {

                        Etudiant e = l.getEtudiant();

                        if (e == null) continue;
            %>

            <div class="student-item bg-white border rounded-lg p-3 mb-2">

                <label class="flex items-center gap-3">

                    <input type="checkbox"
                           name="etudiantIds"
                           value="<%= e.getId() %>"
                           class="w-4 h-4">

                    <div class="flex flex-col sm:flex-row sm:items-center sm:gap-2">

                        <span class="font-bold text-gray-800 text-sm sm:text-base">
                            <%= e.getPrenom() %> <%= e.getNom() %>
                        </span>

                        <span class="text-gray-500 text-xs sm:text-sm">
                            <%= e.getTelephone() != null ? e.getTelephone() : "" %>
                        </span>

                    </div>

                    <small class="ml-auto text-xs text-gray-500">
                        <%= l.getAppartement() != null ? l.getAppartement().getNomAppartement() : "" %>
                    </small>

                </label>

            </div>

            <%
                    }
                }
            %>

        </div>

        <!-- MOIS -->
        <label class="block mt-5 mb-1 font-semibold text-sm sm:text-base">📅 Mois</label>

        <select name="mois"
                required
                class="w-full p-3 border rounded-lg mb-4 text-sm sm:text-base">

            <option value="">-- Sélectionner le mois --</option>

            <% for (int i = 1; i <= 12; i++) { %>
                <option value="<%= i %>"><%= i %></option>
            <% } %>

        </select>

        <!-- ANNEE -->
        <label class="block mb-1 font-semibold text-sm sm:text-base">📆 Année</label>

        <input type="number"
               name="annee"
               placeholder="ex: 2026"
               required
               class="w-full p-3 border rounded-lg mb-4 text-sm sm:text-base">

        <!-- MONTANT -->
        <label class="block mb-1 font-semibold text-sm sm:text-base">💵 Montant (FCFA)</label>

        <input type="number"
               name="montant"
               step="0.01"
               required
               class="w-full p-3 border rounded-lg mb-4 text-sm sm:text-base">

        <!-- STATUT -->
        <label class="block mb-1 font-semibold text-sm sm:text-base">✅ Statut</label>

        <select name="statut"
                required
                class="w-full p-3 border rounded-lg mb-6 text-sm sm:text-base">

            <option value="">-- Sélectionner --</option>
            <option value="PAYE">Payé</option>
            <option value="IMPAYE">Impayé</option>

        </select>

        <!-- SUBMIT -->
        <button type="submit"
                class="w-full bg-blue-700 hover:bg-blue-800 text-white font-bold py-3 rounded-xl">

            💾 Enregistrer le(s) paiement(s)

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