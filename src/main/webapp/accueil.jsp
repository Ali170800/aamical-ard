<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Bienvenue - Amicale AERD</title>
    <script src="https://cdn.tailwindcss.com" crossorigin="anonymous" defer></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">

    <link rel="manifest" href="${pageContext.request.contextPath}/manifest.json">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-capable" content="yes">
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
        <div class="text-center mb-12">
            <h1 class="text-5xl font-bold text-white">Amicale AERD</h1>
        </div>
        <div class="grid md:grid-cols-2 gap-8 max-w-4xl mx-auto">
            <a href="${pageContext.request.contextPath}/login.jsp" class="card bg-white p-10 rounded-3xl text-center">
                <h2 class="text-3xl font-bold">Administrateur</h2>
            </a>
            <a href="${pageContext.request.contextPath}/pages/connexionEtudiant.jsp" class="card bg-white p-10 rounded-3xl text-center">
                <h2 class="text-3xl font-bold">Étudiant</h2>
            </a>
        </div>
    </div>

    <button id="btnInstall" class="fixed bottom-6 right-6 bg-white text-indigo-600 px-6 py-3 rounded-full shadow-2xl font-bold hidden z-50">
        <i class="fas fa-download"></i> Installer AERD
    </button>

    <script>
        // Enregistrement corrigé avec le chemin dynamique
        if ('serviceWorker' in navigator) {
            window.addEventListener('load', () => {
                navigator.serviceWorker.register('${pageContext.request.contextPath}/sw.js')
                    .catch(err => console.log('SW erreur:', err));
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