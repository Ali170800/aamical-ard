const CACHE_NAME = 'aerd-v1';
const ASSETS = [
  '/acceuil.jsp',
  '/styles.css',
  '/icons/icon-192.png'
];

self.addEventListener('install', (event) => {
  event.waitUntil(caches.open(CACHE_NAME).then((cache) => cache.addAll(ASSETS)));
});

self.addEventListener('fetch', (event) => {
  event.respondWith(
    caches.match(event.request).then((response) => response || fetch(event.request))
  );
});