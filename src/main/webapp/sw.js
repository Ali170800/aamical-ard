const CACHE_NAME = 'aerd-v1';
const ASSETS = [
  '/manifest.json',
  '/icons/icon-192.jpg',
  '/icons/icon-512.jpg'
];

self.addEventListener('install', (e) => {
  e.waitUntil(caches.open(CACHE_NAME).then(cache => cache.addAll(ASSETS)));
});

self.addEventListener('fetch', (e) => {
  // On ne met pas en cache la page JSP, on va chercher sur le réseau directement
  if (e.request.url.includes('.jsp')) {
    e.respondWith(fetch(e.request));
  } else {
    e.respondWith(caches.match(e.request).then(res => res || fetch(e.request)));
  }
});