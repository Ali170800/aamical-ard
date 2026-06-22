<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.amical.ard.entites.*" %>
<% Election election = (Election) request.getAttribute("election"); %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Vote - <%= election.getTitre() %></title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="p-10 bg-gray-50">
    <div class="max-w-2xl mx-auto">
        <h1 class="text-3xl font-bold mb-8 text-gray-800"><%= election.getTitre() %></h1>
        <div id="candidats-list" class="space-y-4">
            <% for(CandidatElection c : election.getCandidats()) { %>
                <div class="bg-white p-6 rounded-xl shadow border flex justify-between items-center">
                    <div>
                        <p class="font-bold text-lg text-gray-700"><%= c.getPrenom() %> <%= c.getNom() %></p>
                        <div id="stats-<%= c.getId() %>" class="text-sm font-bold text-indigo-600 mt-1">0 votes</div>
                    </div>
                    <button onclick="voter('<%= c.getId() %>')"
                            class="bg-indigo-600 text-white px-6 py-2 rounded-lg font-bold hover:bg-indigo-700 transition-colors">
                        Voter
                    </button>
                </div>
            <% } %>
        </div>
    </div>

<script>
async function voter(candidatId) {
    // Envoi des DEUX IDs nécessaires à la Servlet
    const url = '<%= request.getContextPath() %>/api/voter?candidatId=' + candidatId + '&electionId=<%= election.getId() %>';

    try {
        const res = await fetch(url, { method: 'POST' });
        const data = await res.json();

        if(data.success) {
            alert("✅ " + data.message);
            updateScores();
        } else {
            alert("❌ " + data.message);
        }
    } catch (e) {
        alert("Erreur de connexion au serveur.");
    }
}

async function updateScores() {
    const res = await fetch('<%= request.getContextPath() %>/api/resultats?electionId=<%= election.getId() %>');
    const data = await res.json();
    data.forEach(c => {
        const el = document.getElementById('stats-' + c.id);
        if(el) el.innerText = c.votes + ' votes (' + c.pct + '%)';
    });
}
// Mise à jour automatique toutes les 3 secondes
setInterval(updateScores, 3000);
</script>
</body>
</html>