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

    /**
     * Hent specifik øvelse
     */
    async getExercise(id) {
        return this.request(`/exercises/${id}`);
    }

    /**
     * Opret ny øvelse
     */
    async createExercise(exerciseData) {
        return this.request('/exercises', {
            method: 'POST',
            body: JSON.stringify(exerciseData)
        });
    }

    /**
     * Opdater øvelse
     */
    async updateExercise(id, exerciseData) {
        return this.request(`/exercises/${id}`, {
            method: 'PUT',
            body: JSON.stringify(exerciseData)
        });
    }

    /**
     * Slet øvelse
     */
    async deleteExercise(id) {
        return this.request(`/exercises/${id}`, {
            method: 'DELETE'
        });
    }

    // ============================================
    // TRAINING SESSION ENDPOINTS
    // ============================================

    /**
     * Hent alle træningssessioner
     */
    async getAllTrainingSessions() {
        return this.request('/training-sessions');
    }

    /**
     * Hent specifik træningssession
     */
    async getTrainingSession(id) {
        return this.request(`/training-sessions/${id}`);
    }

    /**
     * Opret ny træningssession
     */
    async createTrainingSession(sessionData) {
        return this.request('/training-sessions', {
            method: 'POST',
            body: JSON.stringify(sessionData)
        });
    }

    /**
     * Opdater træningssession
     */
    async updateTrainingSession(id, sessionData) {
        return this.request(`/training-sessions/${id}`, {
            method: 'PUT',
            body: JSON.stringify(sessionData)
        });
    }

    /**
     * Slet træningssession
     */
    async deleteTrainingSession(id) {
        return this.request(`/training-sessions/${id}`, {
            method: 'DELETE'
        });
    }

    /**
     * Hent træningssessioner i datointerval
     */
    async getTrainingSessionsByDateRange(startDate, endDate) {
        return this.request(`/training-sessions/date-range?startDate=${startDate}&endDate=${endDate}`);
    }

    // ============================================
    // PERFORMED EXERCISE ENDPOINTS
    // ============================================

    /**
     * Opret performed exercise
     */
    async createPerformedExercise(performedExerciseData) {
        return this.request('/performed-exercises', {
            method: 'POST',
            body: JSON.stringify(performedExerciseData)
        });
    }

    /**
     * Hent performed exercises for en session
     */
    async getPerformedExercisesBySession(sessionId) {
        return this.request(`/performed-exercises/session/${sessionId}`);
    }

    // ============================================
    // PERFORMED SET ENDPOINTS
    // ============================================

    /**
     * Opret performed set
     */
    async createPerformedSet(performedSetData) {
        return this.request('/performed-sets', {
            method: 'POST',
            body: JSON.stringify(performedSetData)
        });
    }

    /**
     * Hent performed sets for en performed exercise
     */
    async getPerformedSetsByExercise(performedExerciseId) {
        return this.request(`/performed-sets/exercise/${performedExerciseId}`);
    }
}

export const api = new ApiService();