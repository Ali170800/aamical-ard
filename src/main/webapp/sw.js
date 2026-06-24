const CACHE_NAME = 'aerd-v1';

// Liste uniquement des fichiers LOCAUX.
// Ne mettez JAMAIS de liens externes (CDN) ici.
const ASSETS = [
  '/',
  '/accueil.jsp',
  '/manifest.json',
  '/icons/icon-192.jpg',
  '/icons/icon-512.jpg'
];

self.addEventListener('install', (event) => {
  event.waitUntil(
    caches.open(CACHE_NAME).then((cache) => {
      return cache.addAll(ASSETS);
    })
  );
  self.skipWaiting();
});

self.addEventListener('activate', (event) => {
  event.waitUntil(
    caches.keys().then((cacheNames) => {
      return Promise.all(
        cacheNames.filter(name => name !== CACHE_NAME).map(name => caches.delete(name))
      );
    })
  );
  self.clients.claim();
});

self.addEventListener('fetch', (event) => {
  event.respondWith(
    caches.match(event.request).then((response) => {
      // Stratégie : Cache d'abord, puis réseau si non trouvé
      return response || fetch(event.request);
    })
  );
});