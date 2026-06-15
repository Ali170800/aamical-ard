<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="fr" class="h-full bg-gray-50">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>
        Tableau de Bord PCS - Amicale AERD
    </title>

    <script src="https://cdn.tailwindcss.com"></script>

    <link
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"
            rel="stylesheet">

    <style>

        .hover-card {
            transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
        }

        .hover-card:hover {

            transform: translateY(-4px);

            box-shadow:
                    0 20px 25px -5px rgb(0 0 0 / 0.1),
                    0 8px 10px -6px rgb(0 0 0 / 0.1);
        }

        .menu-item {
            transition: all 0.2s ease;
        }

        .hidden-menu {
            display: none !important;
        }

        .log-item {
            transition: all 0.2s;
        }

    </style>

</head>

<body class="h-full font-sans antialiased text-gray-700 flex">

<!-- SIDEBAR -->

<aside class="w-64 bg-gray-900 text-white flex flex-col h-screen fixed top-0 left-0 shadow-xl z-10">

    <!-- HEADER -->

    <div class="h-20 flex items-center px-6 border-b border-gray-800">

        <div class="flex items-center gap-3">

            <div class="bg-emerald-600 text-white font-black text-xs w-10 h-10 rounded-xl flex items-center justify-center">

                PCS

            </div>

            <div>

                <h1 class="text-sm font-bold">
                    Commission Sociale
                </h1>

                <span class="text-[10px] text-emerald-400">
                    PCS
                </span>

            </div>

        </div>

    </div>

    <!-- RECHERCHE -->

    <div class="px-4 pt-5 pb-4">

        <div class="relative">

            <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">

                <i class="fas fa-search text-gray-400"></i>

            </div>

            <input type="text"
                   id="menuSearch"
                   class="w-full bg-gray-800 border border-gray-700 focus:border-emerald-500 rounded-xl py-3 pl-10 pr-4 text-sm text-white placeholder-gray-400 focus:outline-none"
                   placeholder="Rechercher dans le menu...">

        </div>

    </div>

    <!-- MENU -->

    <nav id="sidebarMenu"
         class="flex-grow py-4 px-3 space-y-1 overflow-y-auto">

        <!-- LOGEMENTS -->

        <div class="px-3 py-2 text-xs font-bold text-gray-400 uppercase tracking-widest">

            Logements & Paiements

        </div>

        <a href="<%= request.getContextPath() %>/pages/jouter-appartement.jsp"
           class="menu-item flex items-center gap-3 px-4 py-3 rounded-xl hover:bg-gray-800 text-sm">

            <i class="fas fa-building w-5"></i>

            Ajouter Appartement

        </a>

        <a href="<%= request.getContextPath() %>/listeAppartements"
           class="menu-item flex items-center gap-3 px-4 py-3 rounded-xl hover:bg-gray-800 text-sm">

            <i class="fas fa-list w-5"></i>

            Liste Appartements

        </a>

        <a href="<%= request.getContextPath() %>/formulaire-logement"
           class="menu-item flex items-center gap-3 px-4 py-3 rounded-xl hover:bg-gray-800 text-sm">

            <i class="fas fa-bed w-5"></i>

            Ajouter Logement

        </a>

        <a href="<%= request.getContextPath() %>/liste-logements"
           class="menu-item flex items-center gap-3 px-4 py-3 rounded-xl hover:bg-gray-800 text-sm">

            <i class="fas fa-users w-5"></i>

            Étudiants Logés

        </a>

        <a href="<%= request.getContextPath() %>/afficherFormulairePaiement"
           class="menu-item flex items-center gap-3 px-4 py-3 rounded-xl hover:bg-gray-800 text-sm">

            <i class="fas fa-wallet w-5"></i>

            Ajouter Paiement

        </a>

        <a href="<%= request.getContextPath() %>/listePaiements"
           class="menu-item flex items-center gap-3 px-4 py-3 rounded-xl hover:bg-gray-800 text-sm">

            <i class="fas fa-money-bill-wave w-5"></i>

            Liste Paiements

        </a>

        <a href="<%= request.getContextPath() %>/paiement-par-etudiant"
           class="menu-item flex items-center gap-3 px-4 py-3 rounded-xl hover:bg-gray-800 text-sm">

            <i class="fas fa-eye w-5"></i>

            Paiements Étudiant

        </a>

        <!-- COMMUNAUTÉ -->

        <div class="px-3 py-2 mt-6 text-xs font-bold text-gray-400 uppercase tracking-widest">

            Communauté

        </div>

        <a href="<%= request.getContextPath() %>/upload/ajouterPublication.jsp"
           class="menu-item flex items-center gap-3 px-4 py-3 rounded-xl hover:bg-gray-800 text-sm">

            <i class="fas fa-image w-5"></i>

            Ajouter publication

        </a>

        <a href="<%= request.getContextPath() %>/liste-publications"
           class="menu-item flex items-center gap-3 px-4 py-3 rounded-xl hover:bg-gray-800 text-sm">

            <i class="fas fa-newspaper w-5"></i>

            Voir publications

        </a>

        <!-- PDF -->

        <div class="px-3 py-2 mt-6 text-xs font-bold text-gray-400 uppercase tracking-widest">

            Documents PDF

        </div>

        <a href="<%= request.getContextPath() %>/fichiers/liste"
           class="menu-item flex items-center gap-3 px-4 py-3 rounded-xl hover:bg-gray-800 text-sm">

            <i class="fas fa-file-pdf w-5"></i>

            Gestion des PDF

        </a>

        <!-- COMMUNICATION -->

        <div class="px-3 py-2 mt-6 text-xs font-bold text-gray-400 uppercase tracking-widest">

            Communications

        </div>

        <a href="<%= request.getContextPath() %>/email/loges"
           class="menu-item flex items-center gap-3 px-4 py-3 rounded-xl hover:bg-gray-800 text-sm">

            <i class="fas fa-envelope w-5"></i>

            Email Étudiants Logés

        </a>

        <a href="<%= request.getContextPath() %>/emails/bureau"
           class="menu-item flex items-center gap-3 px-4 py-3 rounded-xl hover:bg-gray-800 text-sm">

            <i class="fas fa-envelope-open w-5"></i>

            Email Membres Bureau

        </a>

        <a href="<%= request.getContextPath() %>/emails/etudiants"
           class="menu-item flex items-center gap-3 px-4 py-3 rounded-xl hover:bg-gray-800 text-sm">

            <i class="fas fa-paper-plane w-5"></i>

            Email Tous Étudiants

        </a>

    </nav>

    <!-- LOGOUT -->

    <div class="p-4 border-t border-gray-800">

        <a href="<%= request.getContextPath() %>/LogoutServlet"
           class="flex items-center gap-3 px-4 py-3 text-red-400 hover:bg-red-900/30 rounded-xl text-sm">

            <i class="fas fa-sign-out-alt"></i>

            Déconnexion

        </a>

    </div>

</aside>

<!-- MAIN -->

<main class="flex-1 ml-64 p-10">

    <div class="max-w-7xl mx-auto">

        <!-- HEADER PRINCIPAL -->

        <div class="flex justify-between items-start mb-10">

            <div>

                <h1 class="text-5xl font-black text-gray-800 leading-tight">

                    Espace Commission Sociale (PCS)

                </h1>

                <p class="text-gray-600 mt-4 text-lg">

                    Bienvenue,
                    <strong>
                        ${utilisateurConnecte.prenom}
                        ${utilisateurConnecte.nom}
                    </strong>

                </p>

                <p class="text-gray-500 mt-4">

                    Vous gérez uniquement les logements et les paiements.

                </p>

            </div>

            <div class="text-sm text-gray-500">

                Rôle :
                <span class="font-semibold text-emerald-600">

                    ${utilisateurConnecte.role}

                </span>

            </div>

        </div>

        <!-- CARTES -->

        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">

            <a href="<%= request.getContextPath() %>/formulaire-logement"
               class="hover-card bg-white p-8 rounded-3xl shadow flex flex-col items-center text-center border border-transparent hover:border-emerald-200">

                <div class="w-16 h-16 bg-emerald-100 rounded-2xl flex items-center justify-center mb-6">

                    <i class="fas fa-bed text-4xl text-emerald-600"></i>

                </div>

                <h3 class="font-bold text-xl">

                    Ajouter un Logement

                </h3>

            </a>

            <a href="<%= request.getContextPath() %>/liste-logements"
               class="hover-card bg-white p-8 rounded-3xl shadow flex flex-col items-center text-center border border-transparent hover:border-emerald-200">

                <div class="w-16 h-16 bg-emerald-100 rounded-2xl flex items-center justify-center mb-6">

                    <i class="fas fa-users text-4xl text-emerald-600"></i>

                </div>

                <h3 class="font-bold text-xl">

                    Étudiants Logés

                </h3>

            </a>

            <a href="<%= request.getContextPath() %>/afficherFormulairePaiement"
               class="hover-card bg-white p-8 rounded-3xl shadow flex flex-col items-center text-center border border-transparent hover:border-emerald-200">

                <div class="w-16 h-16 bg-emerald-100 rounded-2xl flex items-center justify-center mb-6">

                    <i class="fas fa-wallet text-4xl text-emerald-600"></i>

                </div>

                <h3 class="font-bold text-xl">

                    Ajouter un Paiement

                </h3>

            </a>

            <a href="<%= request.getContextPath() %>/listePaiements"
               class="hover-card bg-white p-8 rounded-3xl shadow flex flex-col items-center text-center border border-transparent hover:border-emerald-200">

                <div class="w-16 h-16 bg-emerald-100 rounded-2xl flex items-center justify-center mb-6">

                    <i class="fas fa-money-bill-wave text-4xl text-emerald-600"></i>

                </div>

                <h3 class="font-bold text-xl">

                    Paiements

                </h3>

            </a>

            <a href="<%= request.getContextPath() %>/upload/ajouterPublication.jsp"
               class="hover-card bg-white p-8 rounded-3xl shadow flex flex-col items-center text-center border border-transparent hover:border-blue-200">

                <div class="w-16 h-16 bg-blue-100 rounded-2xl flex items-center justify-center mb-6">

                    <i class="fas fa-image text-4xl text-blue-600"></i>

                </div>

                <h3 class="font-bold text-xl">

                    Ajouter publication

                </h3>

            </a>

            <a href="<%= request.getContextPath() %>/liste-publications"
               class="hover-card bg-white p-8 rounded-3xl shadow flex flex-col items-center text-center border border-transparent hover:border-pink-200">

                <div class="w-16 h-16 bg-pink-100 rounded-2xl flex items-center justify-center mb-6">

                    <i class="fas fa-newspaper text-4xl text-pink-600"></i>

                </div>

                <h3 class="font-bold text-xl">

                    Voir publications

                </h3>

            </a>

        </div>

        <!-- LOGS -->

        <div class="mt-12 bg-white p-6 rounded-3xl shadow">

            <h3 class="text-lg font-semibold mb-5 flex items-center gap-2">

                <i class="fas fa-history text-emerald-600"></i>

                Dernières actions

            </h3>

            <c:if test="${empty dernieresActions}">

                <p class="text-gray-500 italic py-4">

                    Aucune action enregistrée pour le moment.

                </p>

            </c:if>

            <c:if test="${not empty dernieresActions}">

                <div class="space-y-3 max-h-96 overflow-y-auto pr-2">

                    <c:forEach var="log" items="${dernieresActions}">

                        <div class="log-item flex gap-4 bg-gray-50 p-4 rounded-2xl">

                            <div class="w-10 h-10 bg-emerald-100 text-emerald-600 rounded-xl flex items-center justify-center">

                                <i class="fas fa-user"></i>

                            </div>

                            <div class="flex-1">

                                <div class="flex justify-between">

                                    <span class="font-medium text-gray-800">

                                        ${log.nomUtilisateur}

                                    </span>

                                    <span class="text-xs text-gray-500">

                                        ${log.dateAction.toLocalDate()} à ${log.dateAction.toLocalTime()}

                                    </span>

                                </div>

                                <p class="text-emerald-700 font-medium">

                                    ${log.action}

                                </p>

                                <p class="text-sm text-gray-600">

                                    ${log.details}

                                </p>

                            </div>

                        </div>

                    </c:forEach>

                </div>

            </c:if>

        </div>

    </div>

</main>

<!-- SCRIPT -->

<script>

    const searchInput =
            document.getElementById('menuSearch');

    const menuItems =
            document.querySelectorAll('.menu-item');

    searchInput.addEventListener('input', function () {

        const term =
                this.value.toLowerCase().trim();

        menuItems.forEach(item => {

            const itemText =
                    item.textContent.toLowerCase();

            if (term === '') {

                item.classList.remove('hidden-menu');

            } else if (itemText.includes(term)) {

                item.classList.remove('hidden-menu');

            } else {

                item.classList.add('hidden-menu');
            }
        });
    });

    searchInput.addEventListener('keydown', function (e) {

        if (e.key === "Escape") {

            this.value = '';

            menuItems.forEach(item =>
                    item.classList.remove('hidden-menu')
            );

            this.focus();
        }
    });

</script>

</body>
</html>