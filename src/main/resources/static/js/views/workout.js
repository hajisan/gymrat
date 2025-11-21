/**
 * Workout View
 * Aktiv træning med øvelser og sets
 */

import { api } from '../api.js';
import { state } from '../state.js';

export class WorkoutView {
    constructor() {
        this.sessionId = null;
        this.workoutData = null;
        this.exercises = []; // Alle tilgængelige øvelser
        this.showExerciseModal = false;
    }

    async render() {
        // Start eller hent aktiv workout
        await this.initializeWorkout();

        // Hent alle øvelser til modal
        await this.loadExercises();

        return `
            <div class="workout-view">
                ${this.renderHeader()}
                ${this.renderContent()}
                ${this.showExerciseModal ? this.renderExerciseModal() : ''}
            </div>
        `;
    }

    async initializeWorkout() {
        // Check om der er en aktiv workout i state
        const activeSession = state.get('activeWorkout');

        if (activeSession) {
            this.sessionId = activeSession.trainingSessionId;
            this.workoutData = await api.request(`/workout/${this.sessionId}`);
        } else {
            // Start ny workout
            this.workoutData = await api.request('/workout/start', { method: 'POST' });
            this.sessionId = this.workoutData.trainingSessionId;

            // Gem i state
            state.set('activeWorkout', this.workoutData);
        }
    }

    async loadExercises() {
        try {
            this.exercises = await api.getAllExercises();
        } catch (error) {
            console.error('Failed to load exercises:', error);
            this.exercises = [];
        }
    }

    renderHeader() {
        const now = new Date();
        const dateStr = this.formatDate(now);
        const timeStr = this.formatTime(now);

        return `
            <header class="workout-header">
                <button class="close-workout" id="closeWorkoutBtn" aria-label="Luk">
                    <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                        <line x1="18" y1="6" x2="6" y2="18"></line>
                        <line x1="6" y1="6" x2="18" y2="18"></line>
                    </svg>
                </button>
                <h1 class="workout-title">Aktiv træning</h1>
                <div class="workout-datetime">
                    <span class="workout-date">${dateStr}</span>
                    <span class="workout-time">${timeStr}</span>
                </div>
            </header>
        `;
    }

    renderContent() {
        const hasExercises = this.workoutData?.exercises?.length > 0;

        return `
            <div class="workout-content">
                <div id="workout-exercises-container">
                    ${hasExercises ? this.renderExercises() : ''}
                </div>

                <button class="add-exercise-btn" id="addExerciseBtn">
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                        <line x1="12" y1="5" x2="12" y2="19"></line>
                        <line x1="5" y1="12" x2="19" y2="12"></line>
                    </svg>
                    <span>Tilføj øvelse</span>
                </button>

                ${hasExercises ? this.renderCompleteButton() : ''}
            </div>
        `;
    }

    renderExercises() {
        if (!this.workoutData?.exercises?.length) {
            return '';
        }

        return `
            <div class="workout-exercises">
                ${this.workoutData.exercises.map(exercise => `
                    <div class="exercise-card" data-exercise-id="${exercise.performedExerciseId}">
                        <div class="exercise-header">
                            <div>
                                <h3 class="exercise-name">${exercise.exerciseName}</h3>
                                <p class="exercise-muscle">${exercise.equipment || exercise.targetMuscleGroup}</p>
                            </div>
                            <button type="button" class="exercise-delete" data-performed-exercise-id="${exercise.performedExerciseId}" aria-label="Slet øvelse">
                                <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                    <polyline points="3 6 5 6 21 6"></polyline>
                                    <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path>
                                </svg>
                            </button>
                        </div>
                        
                        ${exercise.lastPerformed ? this.renderLastPerformed(exercise.lastPerformed) : ''}
                        
                        <div class="exercise-sets">
                            <div class="sets-header">
                                <span>Set</span>
                                <span>Vægt (kg)</span>
                                <span>Reps</span>
                            </div>
                        </div>

                        <button class="add-set-btn" data-performed-exercise-id="${exercise.performedExerciseId}">
                            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                <line x1="12" y1="5" x2="12" y2="19"></line>
                                <line x1="5" y1="12" x2="19" y2="12"></line>
                            </svg>
                            <span>Tilføj sæt</span>
                        </button>
                    </div>
                `).join('')}
            </div>
        `;
    }

    renderLastPerformed(lastData) {
        if (!lastData.averageWeight && !lastData.averageReps) {
            return '';
        }

        const weight = lastData.averageWeight ? `${Math.round(lastData.averageWeight)}kg` : '';
        const reps = lastData.averageReps ? `${lastData.averageReps} reps` : '';
        const separator = weight && reps ? ' × ' : '';

        return `
            <div class="last-performed">
                <span class="last-performed-label">Sidst:</span>
                <span class="last-performed-value">${weight}${separator}${reps}</span>
            </div>
        `;
    }

    renderCompleteButton() {
        return `
            <button class="complete-workout-btn" id="completeWorkoutBtn">
                <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <polyline points="20 6 9 17 4 12"></polyline>
                </svg>
                <span>Afslut træning</span>
            </button>
        `;
    }

    renderExerciseModal() {
        // Get list of already added exercise IDs
        const addedExerciseIds = new Set(
            this.workoutData?.exercises?.map(ex => ex.exerciseId) || []
        );

        return `
            <div class="modal-overlay" id="exerciseModal">
                <div class="modal">
                    <div class="modal-header">
                        <h2>Tilføj øvelse</h2>
                        <button class="modal-close" id="closeModalBtn" aria-label="Luk">
                            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                <line x1="18" y1="6" x2="6" y2="18"></line>
                                <line x1="6" y1="6" x2="18" y2="18"></line>
                            </svg>
                        </button>
                    </div>

                    <div class="modal-content">
                        ${this.exercises.length > 0 ? `
                            <div class="exercise-list">
                                ${this.exercises.map(exercise => {
                                    const isAdded = addedExerciseIds.has(exercise.exerciseId);
                                    return `
                                        <button class="exercise-item ${isAdded ? 'exercise-item--added' : ''}"
                                                data-exercise-id="${exercise.exerciseId}">
                                            <div>
                                                <div class="exercise-item-name">${exercise.name}</div>
                                                ${exercise.equipment ? `
                                                    <div class="exercise-item-muscle">${exercise.equipment}</div>
                                                ` : ''}
                                            </div>
                                            ${isAdded ? `
                                                <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                                    <polyline points="20 6 9 17 4 12"></polyline>
                                                </svg>
                                            ` : `
                                                <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                                    <polyline points="9 18 15 12 9 6"></polyline>
                                                </svg>
                                            `}
                                        </button>
                                    `;
                                }).join('')}
                            </div>
                        ` : `
                            <div class="empty-state">
                                <p>Ingen øvelser</p>
                            </div>
                        `}
                    </div>
                </div>
            </div>
        `;
    }

    mounted() {
        this.setupEventListeners();
    }

    setupEventListeners() {
        // Add Exercise button
        const addExerciseBtn = document.getElementById('addExerciseBtn');
        if (addExerciseBtn) {
            addExerciseBtn.addEventListener('click', () => {
                this.openExerciseModal();
            });
        }

        // Close workout button
        const closeBtn = document.getElementById('closeWorkoutBtn');
        if (closeBtn) {
            closeBtn.addEventListener('click', () => this.closeWorkout());
        }

        // Complete workout button
        const completeBtn = document.getElementById('completeWorkoutBtn');
        if (completeBtn) {
            completeBtn.addEventListener('click', () => this.completeWorkout());
        }

        // Exercise modal event listeners
        if (this.showExerciseModal) {
            this.setupModalListeners();
        }

        // Delete exercise buttons
        document.querySelectorAll('.exercise-delete').forEach(btn => {
            btn.addEventListener('click', (e) => {
                e.preventDefault();
                e.stopPropagation();
                const performedExerciseId = e.currentTarget.dataset.performedExerciseId;
                this.deleteExercise(performedExerciseId);
            });
        });
    }

    setupModalListeners() {
        // Close modal
        const closeModalBtn = document.getElementById('closeModalBtn');
        const modalOverlay = document.getElementById('exerciseModal');

        if (closeModalBtn) {
            closeModalBtn.addEventListener('click', () => this.closeExerciseModal());
        }

        if (modalOverlay) {
            modalOverlay.addEventListener('click', (e) => {
                if (e.target === modalOverlay) {
                    this.closeExerciseModal();
                }
            });
        }

        // Exercise selection
        this.setupExerciseListListeners();
    }

    setupExerciseListListeners() {
        document.querySelectorAll('.exercise-item').forEach(item => {
            item.addEventListener('click', async (e) => {
                const exerciseId = parseInt(e.currentTarget.dataset.exerciseId);
                const isAlreadyAdded = item.classList.contains('exercise-item--added');

                if (isAlreadyAdded) {
                    // Remove exercise if already added
                    await this.removeExerciseFromModal(exerciseId);
                } else {
                    // Add exercise if not added
                    await this.addExercise(exerciseId);
                }
            });
        });
    }

    async openExerciseModal() {
        this.showExerciseModal = true;

        // Re-render content directly instead of full router refresh
        const contentElement = document.getElementById('app-content');
        if (contentElement) {
            contentElement.innerHTML = await this.render();
            this.setupEventListeners();
        }
    }

    async closeExerciseModal() {
        // Add closing animation
        const modalOverlay = document.getElementById('exerciseModal');
        const modal = modalOverlay?.querySelector('.modal');

        if (modalOverlay && modal) {
            // Prevent any transitions on app-content during modal close
            const contentElement = document.getElementById('app-content');
            if (contentElement) {
                contentElement.style.transition = 'none';
            }

            // Animate both modal and overlay
            modalOverlay.classList.add('modal-overlay--closing');
            modal.classList.add('modal--closing');

            // Wait for animation to complete (350ms = 300ms animation + 50ms buffer)
            await new Promise(resolve => setTimeout(resolve, 350));
        }

        this.showExerciseModal = false;

        // Re-render content directly
        const contentElement = document.getElementById('app-content');
        if (contentElement) {
            contentElement.innerHTML = await this.render();
            // Restore transition after render
            setTimeout(() => {
                contentElement.style.transition = '';
            }, 50);
            this.setupEventListeners();
        }
    }

    async addExercise(exerciseId) {
        try {
            await api.request(`/workout/${this.sessionId}/exercises`, {
                method: 'POST',
                body: JSON.stringify({ exerciseId: parseInt(exerciseId) })
            });

            // Reload workout data
            this.workoutData = await api.request(`/workout/${this.sessionId}`);
            state.set('activeWorkout', this.workoutData);

            // Update only the workout exercises container (no modal flash!)
            const exercisesContainer = document.getElementById('workout-exercises-container');
            if (exercisesContainer) {
                const hasExercises = this.workoutData?.exercises?.length > 0;
                exercisesContainer.innerHTML = hasExercises ? this.renderExercises() : '';

                // Re-setup delete button listeners for new exercises
                document.querySelectorAll('.exercise-delete').forEach(btn => {
                    btn.addEventListener('click', (e) => {
                        e.preventDefault();
                        e.stopPropagation();
                        const performedExerciseId = e.currentTarget.dataset.performedExerciseId;
                        this.deleteExercise(performedExerciseId);
                    });
                });
            }

            // Update modal to show which exercises are now added (checkmarks)
            const modalContent = document.querySelector('#exerciseModal .modal-content');
            if (modalContent) {
                const addedExerciseIds = new Set(
                    this.workoutData?.exercises?.map(ex => ex.exerciseId) || []
                );

                // Update each exercise item's state
                document.querySelectorAll('.exercise-item').forEach(item => {
                    const itemExerciseId = parseInt(item.dataset.exerciseId);
                    const isAdded = addedExerciseIds.has(itemExerciseId);

                    if (isAdded) {
                        item.classList.add('exercise-item--added');
                        // Update icon to checkmark
                        const svg = item.querySelector('svg');
                        if (svg) {
                            svg.innerHTML = '<polyline points="20 6 9 17 4 12"></polyline>';
                        }
                    }
                });
            }
        } catch (error) {
            console.error('Failed to add exercise:', error);
            alert('Kunne ikke tilføje øvelse. Prøv igen.');
        }
    }

    async removeExerciseFromModal(exerciseId) {
        try {
            // Find the performedExerciseId for this exercise
            const performedExercise = this.workoutData?.exercises?.find(
                ex => ex.exerciseId === exerciseId
            );

            if (!performedExercise) {
                console.error('Exercise not found in workout');
                return;
            }

            // Delete the exercise (no confirmation dialog - easy to re-add)
            await api.request(`/workout/${this.sessionId}/exercises/${performedExercise.performedExerciseId}`, {
                method: 'DELETE'
            });

            // Reload workout data
            this.workoutData = await api.request(`/workout/${this.sessionId}`);
            state.set('activeWorkout', this.workoutData);

            // Update the workout exercises container in background
            const exercisesContainer = document.getElementById('workout-exercises-container');
            if (exercisesContainer) {
                const hasExercises = this.workoutData?.exercises?.length > 0;
                exercisesContainer.innerHTML = hasExercises ? this.renderExercises() : '';

                // Re-setup delete button listeners
                document.querySelectorAll('.exercise-delete').forEach(btn => {
                    btn.addEventListener('click', (e) => {
                        e.preventDefault();
                        e.stopPropagation();
                        const performedExerciseId = e.currentTarget.dataset.performedExerciseId;
                        this.deleteExercise(performedExerciseId);
                    });
                });
            }

            // Update the modal item to show it's no longer added
            const modalItem = document.querySelector(`.exercise-item[data-exercise-id="${exerciseId}"]`);
            if (modalItem) {
                modalItem.classList.remove('exercise-item--added');
                // Change icon back to arrow
                const svg = modalItem.querySelector('svg');
                if (svg) {
                    svg.innerHTML = '<polyline points="9 18 15 12 9 6"></polyline>';
                }
            }
        } catch (error) {
            console.error('Failed to remove exercise:', error);
            alert('Kunne ikke fjerne øvelse. Prøv igen.');
        }
    }

    async deleteExercise(performedExerciseId) {
        if (!confirm('Er du sikker på at du vil slette denne øvelse?')) {
            return;
        }

        try {
            await api.request(`/workout/${this.sessionId}/exercises/${performedExerciseId}`, {
                method: 'DELETE'
            });

            // Reload workout data
            this.workoutData = await api.request(`/workout/${this.sessionId}`);
            state.set('activeWorkout', this.workoutData);

            // Re-render
            const contentElement = document.getElementById('app-content');
            if (contentElement) {
                contentElement.innerHTML = await this.render();
                this.setupEventListeners();
            }
        } catch (error) {
            console.error('Failed to delete exercise:', error);
            alert('Kunne ikke slette øvelse. Prøv igen.');
        }
    }

    closeWorkout() {
        // Clear active workout from state
        state.set('activeWorkout', null);

        // Navigate to home
        window.history.back();
    }

    async completeWorkout() {
        // TODO: Phase 4 - Show note modal
        console.log('Complete workout - kommer i Phase 4');
    }

    formatDate(date) {
        const days = ['Søndag', 'Mandag', 'Tirsdag', 'Onsdag', 'Torsdag', 'Fredag', 'Lørdag'];
        const months = ['januar', 'februar', 'marts', 'april', 'maj', 'juni',
            'juli', 'august', 'september', 'oktober', 'november', 'december'];

        return `${days[date.getDay()]} ${date.getDate()}. ${months[date.getMonth()]}`;
    }

    formatTime(date) {
        const hours = String(date.getHours()).padStart(2, '0');
        const minutes = String(date.getMinutes()).padStart(2, '0');
        return `${hours}:${minutes}`;
    }

    destroy() {
        // Cleanup hvis nødvendigt
    }
}