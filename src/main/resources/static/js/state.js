/**
 * Global State Management
 * Simpel reactive state med observer pattern
 */

class State {
    constructor() {
        this.data = {
            // Home data
            homeSummary: null,

            // User preferences
            preferences: this.loadPreferences(),

            // Current workout session (hvis i gang)
            activeWorkout: null,

            // Cache
            exercises: [],
            trainingSessions: []
        };

        this.listeners = new Map();
    }

    /**
     * Get state value
     */
    get(key) {
        return this.data[key];
    }

    /**
     * Set state value og notify listeners
     */
    set(key, value) {
        this.data[key] = value;
        this.notify(key, value);
    }

    /**
     * Update nested state
     */
    update(key, updates) {
        this.data[key] = {
            ...this.data[key],
            ...updates
        };
        this.notify(key, this.data[key]);
    }

    /**
     * Subscribe til state changes
     */
    subscribe(key, callback) {
        if (!this.listeners.has(key)) {
            this.listeners.set(key, []);
        }
        this.listeners.get(key).push(callback);

        // Return unsubscribe function
        return () => {
            const callbacks = this.listeners.get(key);
            const index = callbacks.indexOf(callback);
            if (index > -1) {
                callbacks.splice(index, 1);
            }
        };
    }

    /**
     * Notify alle listeners for en key
     */
    notify(key, value) {
        const callbacks = this.listeners.get(key);
        if (callbacks) {
            callbacks.forEach(callback => callback(value));
        }
    }

    /**
     * Load preferences fra localStorage
     */
    loadPreferences() {
        try {
            const stored = localStorage.getItem('gymrat_preferences');
            return stored ? JSON.parse(stored) : {
                theme: 'light',
                units: 'kg'
            };
        } catch (error) {
            console.error('Failed to load preferences:', error);
            return {
                theme: 'light',
                units: 'kg'
            };
        }
    }

    /**
     * Save preferences til localStorage
     */
    savePreferences(preferences) {
        try {
            localStorage.setItem('gymrat_preferences', JSON.stringify(preferences));
            this.set('preferences', preferences);
        } catch (error) {
            console.error('Failed to save preferences:', error);
        }
    }

    /**
     * Clear all state (logout etc.)
     */
    clear() {
        this.data = {
            homeSummary: null,
            preferences: this.loadPreferences(),
            activeWorkout: null,
            exercises: [],
            trainingSessions: []
        };
        this.notify('*', null);
    }
}

export const state = new State();