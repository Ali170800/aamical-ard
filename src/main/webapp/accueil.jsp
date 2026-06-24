<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Amicale AERD</title>

    <!-- Tailwind -->
    <script src="https://cdn.tailwindcss.com"></script>

    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v6.5.0/css/all.css">

    <!-- PWA -->
    <link rel="manifest" href="${pageContext.request.contextPath}/manifest.json">

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="theme-color" content="#1e3a8a">

    <style>
        body {
            background: linear-gradient(135deg, #1e3a8a, #3b82f6);
        }

        .card {
            transition: 0.3s;
        }

        .card:hover {
            transform: translateY(-10px);
        }
    </style>
</head>

<body class="min-h-screen flex items-center justify-center p-6">

<div class="text-center">

    <h1 class="text-5xl font-bold text-white mb-10">Amicale AERD</h1>

    <div class="grid md:grid-cols-2 gap-6">

        <a href="${pageContext.request.contextPath}/login.jsp"
           class="card bg-white p-8 rounded-2xl shadow-xl">
            <i class="fas fa-user-shield text-3xl text-indigo-600"></i>
            <h2 class="text-2xl font-bold mt-3">Administrateur</h2>
        </a>

        <a href="${pageContext.request.contextPath}/pages/connexionEtudiant.jsp"
           class="card bg-white p-8 rounded-2xl shadow-xl">
            <i class="fas fa-user-graduate text-3xl text-indigo-600"></i>
            <h2 class="text-2xl font-bold mt-3">Étudiant</h2>
        </a>

    </div>
</div>

<!-- 🔥 BOUTON INSTALL -->
<button id="installBtn"
        class="fixed bottom-6 right-6 bg-white text-indigo-600 px-6 py-3 rounded-full shadow-xl hidden z-50">
    📲 Installer AERD
</button>

<script>
let deferredPrompt;
const installBtn = document.getElementById("installBtn");

// SERVICE WORKER
if ('serviceWorker' in navigator) {
    navigator.serviceWorker.register('${pageContext.request.contextPath}/sw.js')
        .then(() => console.log("SW OK"))
        .catch(err => console.log("SW ERROR", err));
}

// Afficher bouton install quand Chrome autorise
window.addEventListener("beforeinstallprompt", (e) => {
    e.preventDefault();
    deferredPrompt = e;

    installBtn.classList.remove("hidden");
});

// Click bouton install
installBtn.addEventListener("click", async () => {
    if (!deferredPrompt) return;

    deferredPrompt.prompt();

    const result = await deferredPrompt.userChoice;

    if (result.outcome === "accepted") {
        console.log("App installée");
    }

    deferredPrompt = null;
    installBtn.classList.add("hidden");
});
</script>

</body>
</html>