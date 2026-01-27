/**
 * API Service
 * Håndterer alle HTTP requests til Spring Boot backend
 */

class ApiService {
    constructor() {
        this.baseUrl = '/api';
    }

    /**
     * Generic fetch wrapper med error handling
     */
    async request(endpoint, options = {}) {
        const url = `${this.baseUrl}${endpoint}`;

        const config = {
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                ...options.headers
            },
            ...options
        };

        try {
            const response = await fetch(url, config);

            if (!response.ok) {
                throw new Error(`HTTP ${response.status}: ${response.statusText}`);
            }

            // Handle 204 No Content (e.g., DELETE requests)
            if (response.status === 204 || response.headers.get('content-length') === '0') {
                return null;
            }

            return await response.json();
        } catch (error) {
            console.error(`❌ API Error [${endpoint}]:`, error);
            throw error;
        }
    }

    // ============================================
    // HOME ENDPOINTS
    // ============================================

    /**
     * Hent forside summary (ugestatistik + seneste træning)
     */
    async getHomeSummary() {
        return this.request('/home/summary');
    }

    // ============================================
    // EXERCISE ENDPOINTS
    // ============================================

    /**
     * Hent alle øvelser
     */
    async getAllExercises() {
        return this.request('/exercises');
    }
}

export const api = new ApiService();