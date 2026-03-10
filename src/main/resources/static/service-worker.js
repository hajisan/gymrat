const CACHE_NAME = 'gymrat-v1';

// Static assets der caches ved install (app shell)
const STATIC_ASSETS = [
    '/',
    '/index.html',
    '/css/base.css',
    '/css/components.css',
    '/css/exercises.css',
    '/css/history.css',
    '/css/home.css',
    '/css/login.css',
    '/css/main.css',
    '/css/stats.css',
    '/css/workout-detail.css',
    '/css/workout.css',
    '/js/api.js',
    '/js/app.js',
    '/js/router.js',
    '/js/state.js',
    '/js/utils.js',
    '/js/views/exercise-detail.js',
    '/js/views/exercises.js',
    '/js/views/history.js',
    '/js/views/home.js',
    '/js/views/stats.js',
    '/js/views/workout-detail.js',
    '/js/views/workout.js',
    '/manifest.json',
    '/favicon.ico',
    '/icons/app_icon.png',
];

// Install: cache alle static assets
self.addEventListener('install', event => {
    event.waitUntil(
        caches.open(CACHE_NAME).then(cache => cache.addAll(STATIC_ASSETS))
    );
    self.skipWaiting();
});

// Activate: ryd gamle caches fra tidligere versioner
self.addEventListener('activate', event => {
    event.waitUntil(
        caches.keys().then(keys =>
            Promise.all(
                keys.filter(key => key !== CACHE_NAME).map(key => caches.delete(key))
            )
        )
    );
    clients.claim();
});

self.addEventListener('fetch', event => {
    const url = new URL(event.request.url);

    // API-kald: altid network-first — data ændrer sig løbende
    if (url.pathname.startsWith('/api/')) {
        event.respondWith(fetch(event.request));
        return;
    }

    // CDN (Chart.js m.m.): cache-first
    if (url.origin !== self.location.origin) {
        event.respondWith(
            caches.match(event.request).then(cached => {
                if (cached) return cached;
                return fetch(event.request).then(response => {
                    const clone = response.clone();
                    caches.open(CACHE_NAME).then(cache => cache.put(event.request, clone));
                    return response;
                });
            })
        );
        return;
    }

    // Static assets: cache-first
    event.respondWith(
        caches.match(event.request).then(cached => {
            if (cached) return cached;
            return fetch(event.request).then(response => {
                const clone = response.clone();
                caches.open(CACHE_NAME).then(cache => cache.put(event.request, clone));
                return response;
            });
        })
    );
});
