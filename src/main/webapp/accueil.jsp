<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Bienvenue - Amicale AERD</title>
    <!-- Chargement différé pour éviter les blocages CORS -->
    <script src="https://cdn.tailwindcss.com" defer></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet" media="print" onload="this.media='all'">

    <link rel="manifest" href="${pageContext.request.contextPath}/manifest.json">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-title" content="AERD">
    <link rel="apple-touch-icon" href="${pageContext.request.contextPath}/icons/icon-192.jpg">

    <style>
        body { background: linear-gradient(135deg, #1e3a8a 0%, #3b82f6 100%); }
        .card { transition: all 0.4s ease; }
        .card:hover { transform: translateY(-12px); box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.4); }
    </style>
</head>
<body class="min-h-screen flex items-center justify-center p-6">
    <div class="max-w-5xl w-full">
        <!-- Contenu header -->
        <div class="text-center mb-12">
            <div class="inline-flex items-center gap-3 bg-white/20 backdrop-blur-md px-6 py-3 rounded-3xl mb-6">
                <i class="fas fa-graduation-cap text-4xl text-white"></i>
                <h1 class="text-5xl font-bold text-white tracking-tight">Amicale AERD</h1>
            </div>
            <p class="text-white/90 text-xl">Amicale des Étudiants Ressortissants de Diourbel</p>
        </div>

        <div class="grid md:grid-cols-2 gap-8 max-w-4xl mx-auto">
            <!-- Espace Admin -->
            <a href="${pageContext.request.contextPath}/login.jsp" class="card group bg-white rounded-3xl overflow-hidden shadow-xl flex flex-col h-full">
                <div class="h-2 bg-indigo-600"></div>
                <div class="p-10 flex-1 flex flex-col items-center text-center">
                    <div class="w-20 h-20 bg-indigo-100 rounded-2xl flex items-center justify-center mb-6 group-hover:scale-110 transition">
                        <i class="fas fa-user-shield text-4xl text-indigo-600"></i>
                    </div>
                    <h2 class="text-3xl font-bold text-gray-800 mb-3">Administrateur</h2>
                    <p class="text-gray-600 mb-8 leading-relaxed">Accédez à l'espace de gestion complète.</p>
                </div>
            </a>

            <!-- Espace Étudiant -->
            <a href="${pageContext.request.contextPath}/pages/connexionEtudiant.jsp" class="card group bg-white rounded-3xl overflow-hidden shadow-xl flex flex-col h-full">
                <div class="h-2 bg-teal-600"></div>
                <div class="p-10 flex-1 flex flex-col items-center text-center">
                    <div class="w-20 h-20 bg-teal-100 rounded-2xl flex items-center justify-center mb-6 group-hover:scale-110 transition">
                        <i class="fas fa-user-graduate text-4xl text-teal-600"></i>
                    </div>
                    <h2 class="text-3xl font-bold text-gray-800 mb-3">Étudiant</h2>
                    <p class="text-gray-600 mb-8 leading-relaxed">Inscrivez-vous aux caravanes et gérez vos paiements.</p>
                </div>
            </a>
        </div>
    </div>

    <button id="btnInstall" class="fixed bottom-6 right-6 bg-white text-indigo-600 px-6 py-3 rounded-full shadow-2xl font-bold hidden items-center gap-2 z-50">
        <i class="fas fa-download"></i> Installer AERD
    </button>

    <script>
        if ('serviceWorker' in navigator) {
            window.addEventListener('load', () => {
                navigator.serviceWorker.register('${pageContext.request.contextPath}/sw.js')
                    .catch(err => console.log('SW failed', err));
            });
        }
        let deferredPrompt;
        const btnInstall = document.getElementById('btnInstall');
        window.addEventListener('beforeinstallprompt', (e) => {
            e.preventDefault();
            deferredPrompt = e;
            btnInstall.classList.remove('hidden');
            btnInstall.classList.add('flex');
        });
        btnInstall.addEventListener('click', () => {
            if (deferredPrompt) {
                deferredPrompt.prompt();
                deferredPrompt.userChoice.then(() => btnInstall.classList.add('hidden'));
            }
        });
    </script>
</body>
</html>