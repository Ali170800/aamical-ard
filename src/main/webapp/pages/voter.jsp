<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.amical.ard.entites.*" %>
<%@ page import="com.amical.ard.dao.ElectionDAO" %>
<%@ page import="com.amical.ard.utils.EntityManagerHelper" %>
<%@ page import="jakarta.persistence.EntityManager" %>
<%
    // LOGIQUE DE CHARGEMENT
    Election election = (Election) request.getAttribute("election");
    String eId = request.getParameter("electionId");

    if (election == null && eId != null && !eId.isEmpty()) {
        EntityManager em = EntityManagerHelper.getEntityManager();
        election = new ElectionDAO(em).trouverParId(Long.parseLong(eId));

        // CORRECTION : Chargement forcé de la collection pour éviter LazyInitializationException
        if (election != null && election.getCandidats() != null) {
            election.getCandidats().size();
        }

        em.close();
    }
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Vote</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="p-10 bg-gray-50">
    <div class="max-w-2xl mx-auto">

        <!-- AFFICHAGE DES MESSAGES -->
        <% String status = request.getParameter("status");
           if("success".equals(status)) { %>
               <div class="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded mb-6">✅ Votation réussie !</div>
        <% } else if("deja".equals(status)) { %>
               <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-6">❌ Attention : Tu as déjà voté.</div>
        <% } %>

        <h1 class="text-3xl font-bold mb-8 text-gray-800">
            <%= election != null ? election.getTitre() : "Chargement en cours..." %>
        </h1>

        <!-- DÉBOGAGE : Affiche le nombre de candidats -->
        <p class="text-sm text-gray-400 mb-4">
            Nombre de candidats détectés : <%= (election != null && election.getCandidats() != null) ? election.getCandidats().size() : "Aucun" %>
        </p>

        <div class="space-y-4">
            <% if (election != null && election.getCandidats() != null) {
                for(CandidatElection c : election.getCandidats()) { %>
                <div class="bg-white p-6 rounded-xl shadow border flex justify-between items-center">
                    <p class="font-bold text-lg"><%= c.getPrenom() %> <%= c.getNom() %></p>
                    <form action="<%= request.getContextPath() %>/api/voter" method="POST">
                        <input type="hidden" name="electionId" value="<%= election.getId() %>">
                        <input type="hidden" name="candidatId" value="<%= c.getId() %>">
                        <button type="submit" class="bg-indigo-600 text-white px-6 py-2 rounded-lg font-bold hover:bg-indigo-700">Voter maintenant</button>
                    </form>
                </div>
            <% } } else if (election == null) { %>
                <p class="text-gray-500">Aucune élection trouvée. Vérifie l'ID dans l'URL.</p>
            <% } %>
        </div>
    </div>
</body>
</html>