/**
 * Exercises View
 * Browse og administrer øvelser
 */

import { api } from '../api.js';
import { router } from '../router.js';

export class ExercisesView {
    constructor() {
        this.exercises = [];
    }

    async render() {
        await this.loadExercises();

        return `
            <div class="exercises-view">
                <header class="exercises-header">
                    <h1>Øvelser</h1>
                    <p class="exercises-subtitle">Administrer dine øvelser</p>
                </header>

                ${this.renderExercisesList()}

                <div class="exercises-footer">
                    <button type="button" class="btn-create-exercise" id="createExerciseBtn">
                        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <line x1="12" y1="5" x2="12" y2="19"></line>
                            <line x1="5" y1="12" x2="19" y2="12"></line>
                        </svg>
                        Opret øvelse
                    </button>
                </div>
            </div>
        `;
    }

    async loadExercises() {
        try {
            this.exercises = await api.request('/exercises');
        } catch (error) {
            console.error('Failed to load exercises:', error);
            this.exercises = [];
        }
    }

    renderExercisesList() {
        if (this.exercises.length === 0) {
            return `
                <div class="empty-state">
                    <div class="empty-state-icon">🏋️</div>
                    <h2>Ingen øvelser endnu</h2>
                    <p>Opret din første øvelse for at komme i gang</p>
                </div>
            `;
        }

        return `
            <div class="exercises-list">
                ${this.exercises.map(exercise => this.renderExerciseCard(exercise)).join('')}
            </div>
        `;
    }

    renderExerciseCard(exercise) {
        const typeLabel = exercise.exerciseType === 'DURATION_BASED' ? 'Tid' : 'Reps';

        return `
            <div class="exercise-list-card" data-exercise-id="${exercise.exerciseId}">
                <div class="exercise-card-main">
                    <h3 class="exercise-card-name">${exercise.name}</h3>
                    ${exercise.equipment ? `<p class="exercise-card-equipment">${exercise.equipment}</p>` : ''}
                    ${exercise.targetMuscleGroup ? `<p class="exercise-card-muscle">${exercise.targetMuscleGroup}</p>` : ''}
                </div>
                <div class="exercise-card-meta">
                    <span class="exercise-type-badge">${typeLabel}</span>
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                        <polyline points="9 18 15 12 9 6"></polyline>
                    </svg>
                </div>
            </div>
        `;
    }

    mounted() {
        // Add click listeners to exercise cards
        document.querySelectorAll('.exercise-list-card').forEach(card => {
            card.addEventListener('click', () => {
                const exerciseId = card.dataset.exerciseId;
                router.navigate(`exercises/${exerciseId}`);
            });
        });

        // Add create exercise button listener
        const createBtn = document.getElementById('createExerciseBtn');
        if (createBtn) {
            createBtn.addEventListener('click', () => {
                router.navigate('exercises/new');
            });
        }
    }
}
