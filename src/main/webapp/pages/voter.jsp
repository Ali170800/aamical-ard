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
    <h1 class="text-3xl font-bold mb-8"><%= election.getTitre() %></h1>
    <div id="candidats-list" class="space-y-4">
        <% for(CandidatElection c : election.getCandidats()) { %>
            <div class="bg-white p-6 rounded-xl shadow border flex justify-between items-center">
                <div>
                    <p class="font-bold text-lg"><%= c.getPrenom() %> <%= c.getNom() %></p>
                    <div id="stats-<%= c.getId() %>" class="text-sm font-bold text-indigo-600">0 votes</div>
                </div>
                <button onclick="voter('<%= c.getId() %>')" class="bg-indigo-600 text-white px-6 py-2 rounded-lg font-bold hover:bg-indigo-700">Voter</button>
            </div>
        <% } %>
    </div>

<script>
async function voter(candidatId) {
    const res = await fetch('<%= request.getContextPath() %>/api/voter?candidatId=' + candidatId, { method: 'POST' });
    const data = await res.json();
    if(data.success) {
        alert("Vote enregistré !");
        updateScores();
    } else {
        alert(data.message);
    }
}

async function updateScores() {
    const res = await fetch('<%= request.getContextPath() %>/api/resultats?electionId=<%= election.getId() %>');
    const data = await res.json();
    data.forEach(c => {
        document.getElementById('stats-' + c.id).innerText = c.votes + ' votes (' + c.pct + '%)';
    });
}
setInterval(updateScores, 3000); // Temps réel
</script>
</body>
</html>