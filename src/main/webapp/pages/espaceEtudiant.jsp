<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Espace Étudiant - Amicale AERD</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">

    <script>
        function toggleNotifications() {
            var menu = document.getElementById("menu-notifications");
            menu.style.display = (menu.style.display === "none" || menu.style.display === "") ? "block" : "none";
            if (menu.style.display === "block") chargerNotifications();
        }

        function chargerNotifications() {
            fetch('${pageContext.request.contextPath}/get-notifications')
            .then(response => response.json())
            .then(data => {
                let liste = document.getElementById("liste-notifications");
                let badge = document.getElementById("badge-notification");
                liste.innerHTML = "";
                if (data.length > 0) {
                    badge.style.display = "flex";
                    badge.textContent = data.length;
                    data.forEach(n => {
                        let li = document.createElement("li");
                        li.className = "p-3 border-b text-sm text-slate-700 hover:bg-slate-50";
                        li.textContent = n.message;
                        liste.appendChild(li);
                    });
                } else {
                    liste.innerHTML = "<li class='p-3 text-sm text-slate-400'>Aucune notification</li>";
                    badge.style.display = "none";
                }
            });
        }
        setInterval(chargerNotifications, 5000);

        window.onclick = function(event) {
            var menu = document.getElementById("menu-notifications");
            var button = document.querySelector('[onclick="toggleNotifications()"]');
            if (event.target !== menu && event.target !== button && !button.contains(event.target)) {
                menu.style.display = "none";
            }
        }
    </script>
</head>

<body class="bg-slate-50 font-sans text-slate-800 min-h-screen">

<div class="max-w-6xl mx-auto p-4 sm:p-6 md:p-8 space-y-6 md:space-y-8">

<div class="bg-gradient-to-r from-indigo-600 via-blue-600 to-indigo-700 text-white p-6 sm:p-8 rounded-3xl shadow-xl flex flex-col md:flex-row justify-between items-start md:items-center gap-6">

    <div>
        <span class="inline-block bg-white/20 text-indigo-100 text-xs font-semibold uppercase px-3 py-1 rounded-full mb-3">Espace Étudiant</span>
        <h1 class="text-2xl sm:text-3xl md:text-4xl font-extrabold">${etudiantConnecte.prenom} ${etudiantConnecte.nom}</h1>
        <p class="text-indigo-100 text-sm mt-3 flex flex-wrap items-center gap-2">
            🎓 <span class="font-medium">${etudiantConnecte.filiere}</span>
            <span class="hidden sm:inline h-1 w-1 bg-indigo-300 rounded-full"></span>
            Niveau : <span class="font-bold text-white">${etudiantConnecte.niveau}</span>
        </p>
    </div>

    <div class="flex items-center gap-4 w-full md:w-auto">
        <div class="relative flex items-center">
            <button onclick="toggleNotifications()" class="text-white hover:text-indigo-200 text-xl p-2 relative">
                <i class="fas fa-bell"></i>
                <span id="badge-notification" style="display:none" class="absolute top-0 right-0 bg-red-500 text-[10px] w-5 h-5 rounded-full flex items-center justify-center font-bold border-2 border-indigo-600">0</span>
            </button>

            <div id="menu-notifications"
                 style="display:none"
                 class="absolute top-12 right-0 w-[90vw] sm:w-80 bg-white rounded-2xl shadow-2xl border border-slate-100 z-[100] overflow-hidden text-slate-800">
                <div class="bg-slate-100 p-4 font-bold text-slate-700 text-sm flex justify-between">
                    <span>Notifications</span>
                    <button onclick="toggleNotifications()" class="text-slate-400 hover:text-slate-600"><i class="fas fa-times"></i></button>
                </div>
                <ul id="liste-notifications" class="max-h-80 overflow-y-auto"></ul>
            </div>
        </div>

        <a href="${pageContext.request.contextPath}/etudiant/logout"
           class="bg-white text-red-600 px-5 py-3 rounded-2xl font-bold w-full md:w-auto text-center hover:bg-red-50 transition flex items-center justify-center gap-2">
            <i class="fas fa-sign-out-alt"></i> Déconnexion
        </a>
    </div>
</div>

<div class="bg-white p-6 sm:p-8 rounded-3xl shadow-sm border">
    <div class="flex flex-col md:flex-row justify-between items-start md:items-center gap-4">
        <div>
            <h2 class="text-xl sm:text-2xl font-black">🌍 Communauté AERD</h2>
            <p class="text-slate-500 text-sm mt-2">Découvrez les activités et publications.</p>
        </div>
        <a href="${pageContext.request.contextPath}/liste-publications" class="bg-indigo-600 text-white px-6 py-3 rounded-2xl font-bold w-full md:w-auto text-center">Accéder au fil communautaire</a>
    </div>
</div>

<div class="bg-white p-6 sm:p-8 rounded-3xl shadow-sm border">
    <h2 class="text-lg sm:text-xl font-bold mb-6">👤 Informations personnelles</h2>
    <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4 sm:gap-6">
        <div class="p-4 bg-slate-50 rounded-2xl"><p class="text-xs text-slate-400">Identifiant</p><p class="font-bold text-indigo-700">#${etudiantConnecte.id}</p></div>
        <div class="p-4 bg-slate-50 rounded-2xl"><p class="text-xs text-slate-400">Nom</p><p class="font-bold">${etudiantConnecte.nom}</p></div>
        <div class="p-4 bg-slate-50 rounded-2xl"><p class="text-xs text-slate-400">Prénom</p><p class="font-bold">${etudiantConnecte.prenom}</p></div>
        <div class="p-4 bg-slate-50 rounded-2xl"><p class="text-xs text-slate-400">Filière</p><p class="font-bold text-indigo-700">${etudiantConnecte.filiere}</p></div>
        <div class="p-4 bg-slate-50 rounded-2xl"><p class="text-xs text-slate-400">Niveau</p><p class="font-bold">${etudiantConnecte.niveau}</p></div>
    </div>
</div>

<div class="bg-white p-6 sm:p-8 rounded-3xl shadow-sm border">
    <h2 class="text-lg sm:text-xl font-bold mb-6">🏠 Mon Logement</h2>
    <c:if test="${not empty etudiantLogement and not empty etudiantLogement.appartement}">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4 sm:gap-6">
            <div class="p-5 bg-indigo-50 rounded-2xl"><p class="text-xs text-indigo-600">Appartement</p><p class="text-xl sm:text-2xl font-black">${etudiantLogement.appartement.nomAppartement}</p></div>
            <div class="p-5 bg-slate-50 rounded-2xl"><p class="text-xs text-slate-400">Adresse</p><p class="text-sm sm:text-base">${etudiantLogement.appartement.description}</p></div>
        </div>
    </c:if>
    <c:if test="${empty etudiantLogement}"><div class="p-4 bg-red-50 text-red-700 rounded-2xl">Vous n'êtes pas encore logé(e).</div></c:if>
</div>

<div class="bg-white p-6 sm:p-8 rounded-3xl shadow-sm border">
    <h2 class="text-lg sm:text-xl font-bold mb-6">💰 Mes Paiements</h2>
    <c:if test="${not empty mesPaiements}">
        <div class="overflow-x-auto">
            <table class="w-full min-w-[500px] text-sm">
                <thead><tr class="bg-slate-50"><th class="p-3 text-left">Mois</th><th class="p-3 text-left">Montant</th><th class="p-3 text-left">Statut</th></tr></thead>
                <tbody>
                <c:forEach var="p" items="${mesPaiements}">
                    <tr class="border-t">
                        <td class="p-3">${p.mois}/${p.annee}</td>
                        <td class="p-3 font-bold">${p.montant} FCFA</td>
                        <td class="p-3"><c:choose><c:when test="${p.statut == 'PAYE'}"><span class="bg-green-100 text-green-700 px-3 py-1 rounded-full text-xs">Payé</span></c:when><c:otherwise><span class="bg-red-100 text-red-700 px-3 py-1 rounded-full text-xs">Non Payé</span></c:otherwise></c:choose></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>
</div>

<div class="bg-white p-6 sm:p-8 rounded-3xl shadow-sm border text-center">
    <h2 class="text-xl font-bold">🗳️ Élections & Votes</h2>
    <p class="text-slate-500 text-sm mt-2">Participez aux votes.</p>
    <a href="${pageContext.request.contextPath}/etudiant/elections" class="mt-4 inline-block bg-violet-600 text-white px-6 py-3 rounded-2xl font-bold w-full md:w-auto">Accéder aux scrutins</a>
</div>

<div class="bg-white p-6 sm:p-8 rounded-3xl shadow-sm border text-center">
    <h2 class="text-xl font-bold">🚌 Caravanes</h2>
    <p class="text-slate-500 text-sm mt-2">Consultez les caravanes disponibles.</p>
    <a href="${pageContext.request.contextPath}/etudiant/caravanes" class="mt-4 inline-block bg-indigo-600 text-white px-6 py-3 rounded-2xl font-bold w-full md:w-auto">Voir les caravanes</a>
</div>

</div>
</body>
</html>