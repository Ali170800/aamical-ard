<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="fr" class="h-full bg-gray-50">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tableau de Bord - Amicale AERD</title>

    <script src="https://cdn.tailwindcss.com"></script>

    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"
          rel="stylesheet">

    <style>

        .hover-card {
            transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
        }

        .hover-card:hover {

            transform: translateY(-2px);

            box-shadow:
                    0 10px 15px -3px rgba(0, 0, 0, 0.05),
                    0 4px 6px -2px rgba(0, 0, 0, 0.02);

            border-color: #4338ca;
        }

        .menu-item {
            transition: all 0.2s ease;
        }

        .hidden-menu {
            display: none !important;
        }

        #menuSearch {
            transition: all 0.2s ease;
        }

        #menuSearch:focus {
            box-shadow: 0 0 0 3px rgba(99, 102, 241, 0.2);
        }

    </style>

</head>

<body class="h-full font-sans antialiased text-gray-700 flex">

<aside class="w-64 bg-gray-900 text-white flex flex-col h-screen fixed top-0 left-0 shadow-xl z-10">

    <div class="h-20 flex items-center px-6 border-b border-gray-800">

        <div class="flex items-center gap-3">

            <div class="bg-indigo-600 text-white font-black text-xs w-10 h-10 rounded-xl flex items-center justify-center shadow-md shadow-indigo-600/30">

                AERD

            </div>

            <div>

                <h1 class="text-sm font-bold leading-tight text-gray-100">

                    AERD

                </h1>

                <span class="text-[9px] text-indigo-400 tracking-widest uppercase font-bold">

                    Baol la deuk

                </span>

            </div>

        </div>

    </div>

    <div class="px-4 pt-5 pb-4">

        <div class="relative">

            <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">

                <i class="fas fa-search text-gray-400"></i>

            </div>

            <input
                    type="text"
                    id="menuSearch"
                    class="w-full bg-gray-800 border border-gray-700 focus:border-indigo-500 rounded-xl py-3 pl-10 pr-4 text-sm text-white placeholder-gray-400 focus:outline-none"
                    placeholder="Rechercher dans le menu...">

        </div>

    </div>

    <nav id="sidebarMenu"
         class="flex-grow py-2 px-4 space-y-1 overflow-y-auto">

        <div class="px-2 pb-1.5 text-[9px] font-black text-gray-500 uppercase tracking-wider">

            Étudiants & Logements

        </div>

        <a href="<%= request.getContextPath() %>/pages/ajoutetu/AjouterEtudiant.jsp"
           class="menu-item flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-gray-800 hover:text-indigo-400 text-xs font-semibold transition-all">

            <i class="fas fa-user-plus text-gray-400 w-4 text-center"></i>

            Ajouter Étudiant

        </a>

        <a href="<%= request.getContextPath() %>/etudiants"
           class="menu-item flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-gray-800 hover:text-indigo-400 text-xs font-semibold transition-all">

            <i class="fas fa-list text-gray-400 w-4 text-center"></i>

            Liste des Étudiants

        </a>

        <a href="<%= request.getContextPath() %>/pages/jouter-appartement.jsp"
           class="menu-item flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-gray-800 hover:text-indigo-400 text-xs font-semibold transition-all">

            <i class="fas fa-plus text-gray-400 w-4 text-center"></i>

            Ajouter Appartement

        </a>

        <a href="<%= request.getContextPath() %>/listeAppartements"
           class="menu-item flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-gray-800 hover:text-indigo-400 text-xs font-semibold transition-all">

            <i class="fas fa-list text-gray-400 w-4 text-center"></i>

            Liste des Appartements

        </a>

        <a href="<%= request.getContextPath() %>/formulaire-logement"
           class="menu-item flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-gray-800 hover:text-indigo-400 text-xs font-semibold transition-all">

            <i class="fas fa-bed text-gray-400 w-4 text-center"></i>

            Ajouter un Logement

        </a>

        <a href="<%= request.getContextPath() %>/liste-logements"
           class="menu-item flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-gray-800 hover:text-indigo-400 text-xs font-semibold transition-all">

            <i class="fas fa-clipboard-list text-gray-400 w-4 text-center"></i>

            Étudiants Logés

        </a>

        <div class="px-2 pt-4 pb-1.5 text-[9px] font-black text-gray-500 uppercase tracking-wider">

            Paiements

        </div>

        <a href="<%= request.getContextPath() %>/afficherFormulairePaiement"
           class="menu-item flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-gray-800 hover:text-indigo-400 text-xs font-semibold transition-all">

            <i class="fas fa-wallet text-gray-400 w-4 text-center"></i>

            Ajouter un Paiement

        </a>

        <a href="<%= request.getContextPath() %>/listePaiements"
           class="menu-item flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-gray-800 hover:text-indigo-400 text-xs font-semibold transition-all">

            <i class="fas fa-file-invoice-dollar text-gray-400 w-4 text-center"></i>

            Liste des Paiements

        </a>

        <a href="<%= request.getContextPath() %>/paiement-par-etudiant"
           class="menu-item flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-gray-800 hover:text-indigo-400 text-xs font-semibold transition-all">

            <i class="fas fa-eye text-gray-400 w-4 text-center"></i>

            Paiements Étudiant

        </a>

        <div class="px-2 pt-4 pb-1.5 text-[9px] font-black text-gray-500 uppercase tracking-wider">

            Caravanes & Activités

        </div>

        <a href="<%= request.getContextPath() %>/caravane/creer"
           class="menu-item flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-gray-800 hover:text-indigo-400 text-xs font-semibold transition-all">

            <i class="fas fa-route text-gray-400 w-4 text-center"></i>

            Créer une Caravane

        </a>

        <a href="<%= request.getContextPath() %>/participant/ajouter"
           class="menu-item flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-gray-800 hover:text-indigo-400 text-xs font-semibold transition-all">

            <i class="fas fa-user-plus text-gray-400 w-4 text-center"></i>

            Ajouter un participant

        </a>

        <a href="<%= request.getContextPath() %>/caravane/lister"
           class="menu-item flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-gray-800 hover:text-indigo-400 text-xs font-semibold transition-all">

            <i class="fas fa-list-alt text-gray-400 w-4 text-center"></i>

            Lister les caravanes

        </a>

        <a href="<%= request.getContextPath() %>/participants/selectionner"
           class="menu-item flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-gray-800 hover:text-indigo-400 text-xs font-semibold transition-all">

            <i class="fas fa-clipboard-list text-gray-400 w-4 text-center"></i>

            Voir participants

        </a>

        <a href="<%= request.getContextPath() %>/ajouterActivite"
           class="menu-item flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-gray-800 hover:text-indigo-400 text-xs font-semibold transition-all">

            <i class="fas fa-plus text-gray-400 w-4 text-center"></i>

            Ajouter une Activité

        </a>

        <a href="<%= request.getContextPath() %>/formulaireDepense"
           class="menu-item flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-gray-800 hover:text-indigo-400 text-xs font-semibold transition-all">

            <i class="fas fa-coins text-gray-400 w-4 text-center"></i>

            Ajouter une Dépense

        </a>

        <a href="<%= request.getContextPath() %>/listeActivites"
           class="menu-item flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-gray-800 hover:text-indigo-400 text-xs font-semibold transition-all">

            <i class="fas fa-tasks text-gray-400 w-4 text-center"></i>

            Liste des Activités

        </a>

        <div class="px-2 pt-4 pb-1.5 text-[9px] font-black text-gray-500 uppercase tracking-wider">

            Bureau & Communications

        </div>

        <a href="<%= request.getContextPath() %>/bureau/ajouter"
           class="menu-item flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-gray-800 hover:text-indigo-400 text-xs font-semibold transition-all">

            <i class="fas fa-user-tie text-gray-400 w-4 text-center"></i>

            Ajouter membre bureau

        </a>

        <a href="<%= request.getContextPath() %>/bureau/selectionner"
           class="menu-item flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-gray-800 hover:text-indigo-400 text-xs font-semibold transition-all">

            <i class="fas fa-eye text-gray-400 w-4 text-center"></i>

            Voir membres du bureau

        </a>

        <a href="<%= request.getContextPath() %>/email/loges"
           class="menu-item flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-gray-800 hover:text-indigo-400 text-xs font-semibold transition-all">

            <i class="fas fa-envelope text-gray-400 w-4 text-center"></i>

            Email aux étudiants logés

        </a>

        <a href="<%= request.getContextPath() %>/emails/bureau"
           class="menu-item flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-gray-800 hover:text-indigo-400 text-xs font-semibold transition-all">

            <i class="fas fa-envelope-open text-gray-400 w-4 text-center"></i>

            Email au bureau

        </a>

        <a href="<%= request.getContextPath() %>/emails/etudiants"
           class="menu-item flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-gray-800 hover:text-indigo-400 text-xs font-semibold transition-all">

            <i class="fas fa-envelope-open-text text-gray-400 w-4 text-center"></i>

            Email tous les étudiants

        </a>

        <div class="px-2 pt-4 pb-1.5 text-[9px] font-black text-gray-500 uppercase tracking-wider">

            Élections & Votes

        </div>

        <a href="<%= request.getContextPath() %>/admin/creer-election"
           class="menu-item flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-gray-800 hover:text-indigo-400 text-xs font-semibold transition-all">

            <i class="fas fa-vote-yea text-gray-400 w-4 text-center"></i>

            Créer une élection

        </a>

        <a href="<%= request.getContextPath() %>/admin/dashboard-elections"
           class="menu-item flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-gray-800 hover:text-indigo-400 text-xs font-semibold transition-all">

            <i class="fas fa-chart-pie text-gray-400 w-4 text-center"></i>

            Tableau de bord votes

        </a>

        <div class="px-2 pt-4 pb-1.5 text-[9px] font-black text-gray-500 uppercase tracking-wider">

            Communauté

        </div>

        <a href="<%= request.getContextPath() %>/pages/publication/ajouterPublication.jsp"
           class="menu-item flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-gray-800 hover:text-indigo-400 text-xs font-semibold transition-all">

            <i class="fas fa-image text-gray-400 w-4 text-center"></i>

            Ajouter une publication

        </a>

        <a href="<%= request.getContextPath() %>/liste-publications"
           class="menu-item flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-gray-800 hover:text-indigo-400 text-xs font-semibold transition-all">

            <i class="fas fa-newspaper text-gray-400 w-4 text-center"></i>

            Voir les publications

        </a>

        <div class="px-2 pt-4 pb-1.5 text-[9px] font-black text-gray-500 uppercase tracking-wider">

            Outils

        </div>

        <a href="<%= request.getContextPath() %>/statistiques"
           class="menu-item flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-gray-800 hover:text-indigo-400 text-xs font-semibold transition-all">

            <i class="fas fa-chart-line text-gray-400 w-4 text-center"></i>

            Statistiques

        </a>

        <a href="<%= request.getContextPath() %>/pages/importEtudiants.jsp"
           class="menu-item flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-gray-800 hover:text-indigo-400 text-xs font-semibold transition-all">

            <i class="fas fa-file-import text-gray-400 w-4 text-center"></i>

            Import

        </a>

        <div class="pt-4 border-t border-gray-800 mt-4">

            <a href="<%= request.getContextPath() %>/LogoutServlet"
               class="menu-item flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-red-500/20 hover:text-red-400 text-xs font-semibold text-red-500 transition-all">

                <i class="fas fa-sign-out-alt w-4 text-center"></i>

                Se déconnecter

            </a>

        </div>

    </nav>

</aside>

<main class="flex-grow ml-64 p-10 bg-gray-50 min-h-screen">

    <div class="flex justify-end items-center mb-8">

        <div class="flex items-center gap-6">

            <a href="<%= request.getContextPath() %>/notifications"
               class="notification-bell relative cursor-pointer p-2 hover:bg-gray-100 rounded-full transition-colors">

                <i class="fas fa-bell text-2xl text-gray-600 hover:text-indigo-600"></i>

                <span id="notificationBadge"
                      class="absolute -top-1 -right-1 bg-red-500 text-white text-[10px] font-bold min-w-[18px] h-[18px] flex items-center justify-center rounded-full shadow">

                    ${empty sessionScope.nombreNotifications ? 0 : sessionScope.nombreNotifications}

                </span>

            </a>

            <div class="flex items-center gap-3">

                <div class="text-right">

                    <p class="text-sm font-semibold text-gray-800">

                        Administrateur

                    </p>

                    <p class="text-xs text-gray-500">

                        Connecté

                    </p>

                </div>

                <div class="w-9 h-9 bg-indigo-100 text-indigo-600 rounded-full flex items-center justify-center font-bold">

                    AD

                </div>

            </div>

        </div>

    </div>

    <div class="bg-gradient-to-r from-indigo-600 to-indigo-800 text-white rounded-3xl shadow-xl shadow-indigo-600/10 p-10 mb-10 flex flex-col md:flex-row justify-between items-start md:items-center gap-6">

        <div class="max-w-2xl">

            <h1 class="text-3xl font-black tracking-tight mb-3 text-gray-50">

                Bienvenue sur la plateforme AERD

            </h1>

            <p class="text-indigo-200 font-medium text-xs leading-relaxed">

                Gérez l'ensemble des fonctionnalités de l'Amicale ARD à partir du menu latéral.

            </p>

        </div>

        <div class="flex items-center gap-3">

            <span class="inline-flex items-center gap-2 px-4 py-2 bg-white/10 backdrop-blur-md border border-white/10 rounded-xl text-xs font-bold text-white shadow-inner">

                <i class="fas fa-circle text-[8px] text-green-400 animate-pulse"></i>

                Système en ligne

            </span>

        </div>

    </div>

    <h2 class="text-sm font-black text-gray-800 uppercase tracking-wider mb-6">

        Raccourcis Principaux

    </h2>

    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">

        <div class="bg-white p-6 rounded-2xl border border-gray-200/80 shadow-xl shadow-gray-200/40 hover-card">

            <div class="flex items-center gap-4 mb-5">

                <div class="w-12 h-12 rounded-2xl bg-violet-50 text-violet-600 flex items-center justify-center text-base shadow-inner">

                    <i class="fas fa-vote-yea"></i>

                </div>

                <div>

                    <h2 class="text-xs font-black text-gray-800 leading-tight mb-0.5">

                        Élections

                    </h2>

                    <span class="text-[9px] font-bold text-gray-400 uppercase tracking-wider">

                        Module de vote

                    </span>

                </div>

            </div>

            <a href="<%= request.getContextPath() %>/admin/creer-election"
               class="block text-center px-3 py-2.5 bg-gray-900 hover:bg-indigo-600 text-white text-[10px] font-bold rounded-xl transition-colors">

                ➕ Créer une élection

            </a>

        </div>

        <div class="bg-white p-6 rounded-2xl border border-gray-200/80 shadow-xl shadow-gray-200/40 hover-card">

            <div class="flex items-center gap-4 mb-5">

                <div class="w-12 h-12 rounded-2xl bg-indigo-50 text-indigo-600 flex items-center justify-center text-base shadow-inner">

                    <i class="fas fa-user-graduate"></i>

                </div>

                <div>

                    <h2 class="text-xs font-black text-gray-800 leading-tight mb-0.5">

                        Étudiant

                    </h2>

                    <span class="text-[9px] font-bold text-gray-400 uppercase tracking-wider">

                        Nouvelle inscription

                    </span>

                </div>

            </div>

            <a href="<%= request.getContextPath() %>/pages/ajoutetu/AjouterEtudiant.jsp"
               class="block text-center px-3 py-2.5 bg-gray-900 hover:bg-indigo-600 text-white text-[10px] font-bold rounded-xl transition-colors">

                ➕ Ajouter un étudiant

            </a>

        </div>

        <div class="bg-white p-6 rounded-2xl border border-gray-200/80 shadow-xl shadow-gray-200/40 hover-card">

            <div class="flex items-center gap-4 mb-5">

                <div class="w-12 h-12 rounded-2xl bg-teal-50 text-teal-600 flex items-center justify-center text-base shadow-inner">

                    <i class="fas fa-bed"></i>

                </div>

                <div>

                    <h2 class="text-xs font-black text-gray-800 leading-tight mb-0.5">

                        Logement

                    </h2>

                    <span class="text-[9px] font-bold text-gray-400 uppercase tracking-wider">

                        Gestion

                    </span>

                </div>

            </div>

            <a href="<%= request.getContextPath() %>/formulaire-logement"
               class="block text-center px-3 py-2.5 bg-gray-900 hover:bg-indigo-600 text-white text-[10px] font-bold rounded-xl transition-colors">

                ➕ Ajouter un logement

            </a>

        </div>

        <div class="bg-white p-6 rounded-2xl border border-gray-200/80 shadow-xl shadow-gray-200/40 hover-card">

            <div class="flex items-center gap-4 mb-5">

                <div class="w-12 h-12 rounded-2xl bg-amber-50 text-amber-600 flex items-center justify-center text-base shadow-inner">

                    <i class="fas fa-wallet"></i>

                </div>

                <div>

                    <h2 class="text-xs font-black text-gray-800 leading-tight mb-0.5">

                        Paiement

                    </h2>

                    <span class="text-[9px] font-bold text-gray-400 uppercase tracking-wider">

                        Comptabilité

                    </span>

                </div>

            </div>

            <a href="<%= request.getContextPath() %>/afficherFormulairePaiement"
               class="block text-center px-3 py-2.5 bg-gray-900 hover:bg-indigo-600 text-white text-[10px] font-bold rounded-xl transition-colors">

                ➕ Ajouter un paiement

            </a>

        </div>

    </div>

    <footer class="mt-20 text-center text-xs text-gray-400 border-t border-gray-200/60 pt-10">

        &copy; 2026 Amicale ARD -

        <span class="text-indigo-600 font-black">

            Baol la deuk

        </span>

        . Tous droits réservés.

    </footer>

</main>

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