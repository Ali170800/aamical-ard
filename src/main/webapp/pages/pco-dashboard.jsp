<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%
    // 🔐 Sécurité : vérifier session
    if (session.getAttribute("utilisateurConnecte") == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="fr" class="h-full bg-gray-50">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tableau de Bord PCO - Amicale AERD</title>

    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">

    <style>
        .hover-card { transition: all 0.3s ease; }
        .hover-card:hover { transform: translateY(-5px); box-shadow: 0 15px 25px rgba(0,0,0,0.1); }
        .menu-item { transition: all 0.2s ease; }
        .log-item { transition: all 0.2s; }
    </style>
</head>

<body class="h-full font-sans antialiased text-gray-700 flex">

<!-- BOUTON HAMBURGER MOBILE -->
<button id="mobileMenuBtn" class="lg:hidden fixed top-4 left-4 z-50 p-2 bg-gray-900 text-white rounded-lg">
    <i class="fas fa-bars"></i>
</button>

<!-- SIDEBAR -->
<aside id="sidebar" class="w-64 bg-gray-900 text-white flex flex-col h-screen fixed top-0 left-0 shadow-xl z-40 transform -translate-x-full lg:translate-x-0 transition-transform duration-300">

    <!-- Logo -->
    <div class="h-20 flex items-center px-6 border-b border-gray-800">
        <div class="flex items-center gap-3">
            <div class="bg-indigo-600 w-10 h-10 flex items-center justify-center rounded-xl font-bold">PCO</div>
            <div>
                <h1 class="text-sm font-bold">Commission Organisation</h1>
                <span class="text-xs text-indigo-400">PCO</span>
            </div>
        </div>
    </div>

    <!-- MENU -->
    <nav class="flex-grow py-4 px-3 space-y-1 overflow-y-auto">
        <a href="<%= request.getContextPath() %>/pco-dashboard" class="menu-item flex items-center gap-3 px-4 py-3 hover:bg-gray-800 rounded-xl"><i class="fas fa-home"></i> Tableau de bord</a>

        <div class="px-3 py-2 text-xs text-gray-400 uppercase mt-2">Communauté</div>
        <a href="<%= request.getContextPath() %>/pages/publication/ajouterPublication.jsp" class="menu-item flex items-center gap-3 px-4 py-3 hover:bg-gray-800 rounded-xl"><i class="fas fa-image"></i> Ajouter publication</a>
        <a href="<%= request.getContextPath() %>/liste-publications" class="menu-item flex items-center gap-3 px-4 py-3 hover:bg-gray-800 rounded-xl"><i class="fas fa-newspaper"></i> Voir publications</a>

        <div class="px-3 py-2 text-xs text-gray-400 uppercase mt-2">Caravanes</div>
        <a href="<%= request.getContextPath() %>/caravane/creer" class="menu-item flex items-center gap-3 px-4 py-3 hover:bg-gray-800 rounded-xl"><i class="fas fa-route"></i> Créer une caravane</a>
        <a href="<%= request.getContextPath() %>/caravane/lister" class="menu-item flex items-center gap-3 px-4 py-3 hover:bg-gray-800 rounded-xl"><i class="fas fa-list"></i> Liste des caravanes</a>
        <a href="<%= request.getContextPath() %>/participants/selectionner" class="menu-item flex items-center gap-3 px-4 py-3 hover:bg-gray-800 rounded-xl"><i class="fas fa-users"></i> Participants</a>
        <a href="<%= request.getContextPath() %>/participant/ajouter" class="menu-item flex items-center gap-3 px-4 py-3 hover:bg-gray-800 rounded-xl"><i class="fas fa-user-plus"></i> Ajouter participant</a>

        <div class="px-3 py-2 mt-6 text-xs text-gray-400 uppercase">Documents PDF</div>
        <a href="<%= request.getContextPath() %>/fichiers/liste" class="menu-item flex items-center gap-3 px-4 py-3 hover:bg-gray-800 rounded-xl"><i class="fas fa-file-pdf"></i> Gestion des PDF</a>
        <a href="<%= request.getContextPath() %>/pages/fichiers/uploadFichier.jsp" class="menu-item flex items-center gap-3 px-4 py-3 hover:bg-gray-800 rounded-xl"><i class="fas fa-upload"></i> Ajouter un PDF</a>

        <div class="px-3 py-2 mt-6 text-xs text-gray-400 uppercase">Emails</div>
        <a href="<%= request.getContextPath() %>/email/loges" class="menu-item flex items-center gap-3 px-4 py-3 hover:bg-gray-800 rounded-xl"><i class="fas fa-envelope"></i> Étudiants logés</a>
        <a href="<%= request.getContextPath() %>/emails/bureau" class="menu-item flex items-center gap-3 px-4 py-3 hover:bg-gray-800 rounded-xl"><i class="fas fa-envelope-open"></i> Bureau</a>
        <a href="<%= request.getContextPath() %>/emails/etudiants" class="menu-item flex items-center gap-3 px-4 py-3 hover:bg-gray-800 rounded-xl"><i class="fas fa-paper-plane"></i> Tous les étudiants</a>
    </nav>

    <div class="p-4 border-t border-gray-800">
        <a href="<%= request.getContextPath() %>/LogoutServlet" class="flex items-center gap-3 px-4 py-3 text-red-400 hover:bg-red-900/30 rounded-xl"><i class="fas fa-sign-out-alt"></i> Déconnexion</a>
    </div>
</aside>

<!-- MAIN -->
<main class="flex-1 lg:ml-64 p-4 lg:p-10 bg-gray-50 min-h-screen transition-all">
    <div class="max-w-5xl mx-auto mt-12 lg:mt-0">
        <!-- HEADER -->
        <div class="flex flex-col sm:flex-row justify-between items-start sm:items-center mb-8 gap-4">
            <div>
                <h1 class="text-2xl sm:text-4xl font-black text-gray-800">Espace PCO</h1>
                <p class="text-gray-600">Bienvenue <strong>${utilisateurConnecte.prenom} ${utilisateurConnecte.nom}</strong></p>
            </div>
            <div class="text-sm text-gray-500">Rôle : <span class="font-semibold text-indigo-600">${utilisateurConnecte.role}</span></div>
        </div>

        <p class="text-gray-600 mb-10">Vous gérez les caravanes, participants et communications.</p>

        <!-- CARTES -->
        <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
            <a href="<%= request.getContextPath() %>/upload/ajouterPublication.jsp" class="hover-card bg-white p-8 rounded-3xl shadow text-center"><i class="fas fa-image text-4xl text-pink-600 mb-4"></i><h3 class="font-bold text-xl">Ajouter publication</h3></a>
            <a href="<%= request.getContextPath() %>/liste-publications" class="hover-card bg-white p-8 rounded-3xl shadow text-center"><i class="fas fa-newspaper text-4xl text-green-600 mb-4"></i><h3 class="font-bold text-xl">Voir publications</h3></a>
            <a href="<%= request.getContextPath() %>/caravane/creer" class="hover-card bg-white p-8 rounded-3xl shadow text-center"><i class="fas fa-route text-4xl text-indigo-600 mb-4"></i><h3 class="font-bold text-xl">Créer caravane</h3></a>
            <a href="<%= request.getContextPath() %>/caravane/lister" class="hover-card bg-white p-8 rounded-3xl shadow text-center"><i class="fas fa-list text-4xl text-indigo-600 mb-4"></i><h3 class="font-bold text-xl">Voir caravanes</h3></a>
            <a href="<%= request.getContextPath() %>/participant/ajouter" class="hover-card bg-white p-8 rounded-3xl shadow text-center"><i class="fas fa-user-plus text-4xl text-indigo-600 mb-4"></i><h3 class="font-bold text-xl">Ajouter participant</h3></a>
        </div>

        <!-- LOGS -->
        <div class="mt-12 bg-white p-6 rounded-3xl shadow">
            <h3 class="text-lg font-semibold mb-5 flex items-center gap-2"><i class="fas fa-history text-indigo-600"></i> Dernières actions</h3>
            <c:if test="${empty dernieresActions}"><p class="text-gray-500 italic py-4">Aucune action enregistrée pour le moment.</p></c:if>
            <c:if test="${not empty dernieresActions}">
                <div class="space-y-3 max-h-96 overflow-y-auto pr-2">
                    <c:forEach var="log" items="${dernieresActions}">
                        <div class="log-item flex gap-4 bg-gray-50 p-4 rounded-2xl">
                            <div class="w-10 h-10 bg-indigo-100 text-indigo-600 rounded-xl flex items-center justify-center"><i class="fas fa-user"></i></div>
                            <div class="flex-1">
                                <div class="flex justify-between"><span class="font-medium text-gray-800">${log.nomUtilisateur}</span><span class="text-xs text-gray-500">${log.dateAction.toString().replace('T', ' ').substring(0,16)}</span></div>
                                <p class="text-indigo-700 font-medium">${log.action}</p>
                                <p class="text-sm text-gray-600">${log.details}</p>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:if>
        </div>
    </div>
</main>

<script>
    const mobileMenuBtn = document.getElementById('mobileMenuBtn');
    const sidebar = document.getElementById('sidebar');
    mobileMenuBtn.addEventListener('click', () => {
        sidebar.classList.toggle('-translate-x-full');
    });
</script>
</body>
</html>