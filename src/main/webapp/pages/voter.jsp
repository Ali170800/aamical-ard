<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.amical.ard.entites.*, com.amical.ard.dao.*, com.amical.ard.utils.EntityManagerHelper, jakarta.persistence.EntityManager" %>
<%
    Election election = (Election) request.getAttribute("election");
    String eId = request.getParameter("electionId");
    long totalVotes = 0;

    if (election == null && eId != null && !eId.isEmpty()) {
        EntityManager em = EntityManagerHelper.getEntityManager();
        election = new ElectionDAO(em).trouverParId(Long.parseLong(eId));
        if (election != null && election.getCandidats() != null) {
            election.getCandidats().size();
            // Calcul du total des votes pour cette élection spécifique
            VoteElectionDAO vDao = new VoteElectionDAO(em);
            for (CandidatElection c : election.getCandidats()) {
                totalVotes += vDao.compterVotesPourCandidat(c.getId(), election.getId());
            }
        }
        em.close();
    }
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Vote - <%= election != null ? election.getTitre() : "Chargement..." %></title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="p-10 bg-gray-50">
    <div class="max-w-2xl mx-auto">
        <% String status = request.getParameter("status");
           if("success".equals(status)) { %><div class="bg-green-100 text-green-700 p-4 rounded mb-6">✅ Vote pris en compte !</div><% }
           else if("deja".equals(status)) { %><div class="bg-red-100 text-red-700 p-4 rounded mb-6">❌ Déjà voté.</div><% } %>

        <h1 class="text-3xl font-bold mb-8"><%= election != null ? election.getTitre() : "Chargement..." %></h1>

        <div class="space-y-4 mb-12">
            <% if (election != null && election.getCandidats() != null) {
                for(CandidatElection c : election.getCandidats()) { %>
                <div class="bg-white p-6 rounded-xl shadow border flex justify-between items-center">
                    <p class="font-bold text-lg"><%= c.getPrenom() %> <%= c.getNom() %></p>
                    <form action="<%= request.getContextPath() %>/api/voter" method="POST">
                        <input type="hidden" name="electionId" value="<%= election.getId() %>">
                        <input type="hidden" name="candidatId" value="<%= c.getId() %>">
                        <button type="submit" class="bg-indigo-600 text-white px-6 py-2 rounded-lg font-bold hover:bg-indigo-700">Voter</button>
                    </form>
                </div>
            <% } } %>
        </div>

        <div class="bg-white p-8 rounded-3xl shadow-sm border border-gray-200">
            <h2 class="text-xl font-black mb-6">Résultats en temps réel</h2>
            <div class="space-y-6">
                <%
                   if (election != null && election.getCandidats() != null) {
                       EntityManager emResultats = EntityManagerHelper.getEntityManager();
                       VoteElectionDAO vDaoRes = new VoteElectionDAO(emResultats);
                       for(CandidatElection c : election.getCandidats()) {
                           // Appel avec les deux paramètres requis
                           long nbVoix = vDaoRes.compterVotesPourCandidat(c.getId(), election.getId());
                           int pourcentage = (totalVotes > 0) ? (int)((nbVoix * 100) / totalVotes) : 0;
                %>
                    <div>
                        <div class="flex justify-between text-xs font-bold mb-2">
                            <span><%= c.getPrenom() %> <%= c.getNom() %></span>
                            <span><%= pourcentage %>% (<%= nbVoix %> voix)</span>
                        </div>
                        <div class="w-full bg-gray-100 rounded-full h-4">
                            <div class="bg-indigo-600 h-4 rounded-full" style="width: <%= pourcentage %>%"></div>
                        </div>
                    </div>
                <%     }
                       emResultats.close();
                   }
                %>
            </div>
        </div>
    </div>
</body>
</html>