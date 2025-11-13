/**
 * GymRat SPA - Main Application
 * Håndterer routing, state og view rendering
 */

import { router } from './router.js';
import { state } from './state.js';
import { api } from './api.js';

// Initialize app
class App {
    constructor() {
        this.init();
    }

    async init() {
        // Setup router
        router.init();

        // Setup tab bar navigation
        this.setupTabBar();

        // Setup global event listeners
        this.setupGlobalListeners();

        console.log('🏋️ GymRat SPA initialized');
    }

    setupTabBar() {
        const tabs = document.querySelectorAll('.tab');

        tabs.forEach(tab => {
            tab.addEventListener('click', (e) => {
                e.preventDefault();
                const route = tab.dataset.tab;

                // Update active state
                this.updateTabBarState(route);

                // Navigate
                router.navigate(route);
            });
        });
    }

    updateTabBarState(route) {
        const tabs = document.querySelectorAll('.tab');
        tabs.forEach(tab => {
            if (tab.dataset.tab === route) {
                tab.classList.add('tab--active');
            } else {
                tab.classList.remove('tab--active');
            }
        });
    }

    setupGlobalListeners() {
        // Handle back button
        window.addEventListener('popstate', () => {
            router.handleRoute();
        });

        // Handle clicks on internal links
        document.addEventListener('click', (e) => {
            if (e.target.matches('[data-link]')) {
                e.preventDefault();
                const route = e.target.getAttribute('href') || e.target.dataset.link;
                router.navigate(route);
            }
        });
    }
}

// Start app when DOM is ready
if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', () => new App());
} else {
    new App();
}