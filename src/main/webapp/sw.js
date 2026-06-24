const CACHE_NAME = 'aerd-v1';

// Liste des fichiers à mettre en cache immédiatement
const ASSETS = [
  './acceuil.jsp',
  './icons/icon-192.jpg',
  './icons/icon-512.jpg',
  'https://cdn.tailwindcss.com',
  'https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css'
];

// 1. Installation : Mise en cache initiale
self.addEventListener('install', (event) => {
  event.waitUntil(
    caches.open(CACHE_NAME).then((cache) => {
      console.log('[Service Worker] Mise en cache des ressources essentielles');
      // On utilise addAll, mais si une ressource échoue, on continue
      return cache.addAll(ASSETS);
    })
  );
  // Force l'activation immédiate sans attendre la fermeture des onglets
  self.skipWaiting();
});

// 2. Activation : Nettoyage des anciens caches obsolètes
self.addEventListener('activate', (event) => {
  event.waitUntil(
    caches.keys().then((cacheNames) => {
      return Promise.all(
        cacheNames.map((cache) => {
          if (cache !== CACHE_NAME) {
            console.log('[Service Worker] Suppression de l\'ancien cache :', cache);
            return caches.delete(cache);
          }
        })
      );
    })
  );
  // Prend le contrôle des pages ouvertes immédiatement
  self.clients.claim();
});

// 3. Interception des requêtes : Stratégie "Cache First, falling back to Network"
self.addEventListener('fetch', (event) => {
  event.respondWith(
    caches.match(event.request).then((response) => {
      // Retourne le fichier du cache s'il existe, sinon fait une requête réseau
      return response || fetch(event.request).catch(() => {
        // Optionnel : Ajouter ici une page "offline.html" si le réseau échoue
        console.log('[Service Worker] Erreur réseau, aucune ressource en cache trouvée');
      });
    })
  );
});