/**
 * Simple Client-Side Router
 * Håndterer navigation mellem views uden page reload
 */

import { HomeView } from './views/home.js';
import { WorkoutView } from './views/workout.js';
import { HistoryView } from './views/history.js';
import { ExercisesView } from './views/exercises.js';

class Router {
    constructor() {
        this.routes = {
            'home': HomeView,
            'workout': WorkoutView,
            'history': HistoryView,
            'exercises': ExercisesView
        };
        this.currentView = null;
        this.contentElement = null;
    }

    init() {
        // Get content container
        this.contentElement = document.getElementById('app-content');

        if (!this.contentElement) {
            console.error('❌ Content element #app-content not found');
            return;
        }

        // Load initial route
        this.handleRoute();
    }

    navigate(route) {
        // Update URL without reload
        const url = route === 'home' ? '/' : `/${route}`;
        window.history.pushState({ route }, '', url);

        // Render view
        this.handleRoute();
    }

    handleRoute() {
        // Get current route from URL
        const path = window.location.pathname;
        let route = 'home';

        if (path !== '/') {
            route = path.substring(1); // Remove leading slash
        }

        // Check if route exists
        if (!this.routes[route]) {
            console.warn(`⚠️ Route '${route}' not found, redirecting to home`);
            route = 'home';
        }

        // Render view
        this.renderView(route);
    }

    async renderView(route) {
        const ViewClass = this.routes[route];

        if (!ViewClass) {
            console.error(`❌ View for route '${route}' not found`);
            return;
        }

        try {
            // Update tab bar active state
            this.updateTabBar(route);

            // Destroy previous view if exists
            if (this.currentView && this.currentView.destroy) {
                this.currentView.destroy();
            }

            // Create and render new view
            this.currentView = new ViewClass();
            const content = await this.currentView.render();

            // Update DOM with fade effect
            this.contentElement.style.opacity = '0';

            setTimeout(() => {
                this.contentElement.innerHTML = content;
                this.contentElement.style.opacity = '1';

                // Call mounted lifecycle hook if exists
                if (this.currentView.mounted) {
                    this.currentView.mounted();
                }
            }, 150);

        } catch (error) {
            console.error(`❌ Error rendering view '${route}':`, error);
            this.contentElement.innerHTML = `
                <div class="error-view">
                    <p>Der opstod en fejl 😕</p>
                    <button onclick="location.reload()">Genindlæs</button>
                </div>
            `;
        }
    }

    updateTabBar(route) {
        // Map routes to tab data-tab values
        const routeToTab = {
            'home': 'home',
            'workout': 'workout',
            'history': 'history',
            'exercises': 'exercises'
        };

        const tabName = routeToTab[route] || 'home';

        document.querySelectorAll('.tab').forEach(tab => {
            if (tab.dataset.tab === tabName) {
                tab.classList.add('tab--active');
            } else {
                tab.classList.remove('tab--active');
            }
        });
    }
}

export const router = new Router();