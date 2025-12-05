/**
 * Exercises View
 * Browse og administrer øvelser
 */

import { api } from '../api.js';
import { router } from '../router.js';

export class ExercisesView {
    constructor() {
        this.exercises = [];
        this.searchTerm = '';
        this.sortOrder = 'asc'; // 'asc' or 'desc'
    }

    async render() {
        await this.loadExercises();

        return `
            <div class="exercises-view">
                <header class="page-header page-header--centered">
                    <div class="page-header__content">
                        <h1 class="page-header__title">Øvelser</h1>
                        <p class="page-header__subtitle">Administrer dine øvelser</p>
                    </div>
                </header>

                <div class="exercises-controls">
                    <div class="search-box">
                        <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <circle cx="11" cy="11" r="8"></circle>
                            <path d="m21 21-4.35-4.35"></path>
                        </svg>
                        <input
                            type="text"
                            id="exerciseSearch"
                            placeholder="Søg efter øvelse..."
                            value="${this.searchTerm}"
                        />
                    </div>
                    <button type="button" class="btn-sort" id="sortBtn" title="${this.sortOrder === 'asc' ? 'Sortér Z-A' : 'Sortér A-Z'}">
                        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            ${this.sortOrder === 'asc'
                                ? '<path d="M3 6h18M3 12h12M3 18h6"/>'
                                : '<path d="M3 6h6M3 12h12M3 18h18"/>'}
                        </svg>
                    </button>
                </div>

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

        // Filter and sort exercises
        let filteredExercises = this.exercises;

        // Apply search filter
        if (this.searchTerm.trim()) {
            const searchLower = this.searchTerm.toLowerCase();
            filteredExercises = filteredExercises.filter(exercise =>
                exercise.name.toLowerCase().includes(searchLower) ||
                (exercise.equipment && exercise.equipment.toLowerCase().includes(searchLower)) ||
                (exercise.targetMuscleGroup && exercise.targetMuscleGroup.toLowerCase().includes(searchLower))
            );
        }

        // Apply sorting
        filteredExercises = [...filteredExercises].sort((a, b) => {
            const nameA = a.name.toLowerCase();
            const nameB = b.name.toLowerCase();
            if (this.sortOrder === 'asc') {
                return nameA.localeCompare(nameB);
            } else {
                return nameB.localeCompare(nameA);
            }
        });

        // Check if no results after filtering
        if (filteredExercises.length === 0) {
            return `
                <div class="empty-state">
                    <div class="empty-state-icon">🔍</div>
                    <h2>Ingen resultater</h2>
                    <p>Prøv et andet søgeord</p>
                </div>
            `;
        }

        return `
            <div class="exercises-list">
                ${filteredExercises.map(exercise => this.renderExerciseCard(exercise)).join('')}
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
        // Add search input listener
        const searchInput = document.getElementById('exerciseSearch');
        if (searchInput) {
            searchInput.addEventListener('input', (e) => {
                this.searchTerm = e.target.value;
                this.rerender();
            });
        }

        // Add sort button listener
        const sortBtn = document.getElementById('sortBtn');
        if (sortBtn) {
            sortBtn.addEventListener('click', () => {
                this.sortOrder = this.sortOrder === 'asc' ? 'desc' : 'asc';
                this.rerender();
            });
        }

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

    rerender() {
        // Find the container for the list (or empty state)
        const listContainer = document.querySelector('.exercises-list');
        const emptyState = document.querySelector('.empty-state');

        // Get the new list HTML
        const newListHtml = this.renderExercisesList();

        // Replace the list or empty state
        if (listContainer) {
            // Create a temporary container to parse the HTML
            const temp = document.createElement('div');
            temp.innerHTML = newListHtml;
            const newElement = temp.firstElementChild;

            listContainer.replaceWith(newElement);
        } else if (emptyState) {
            const temp = document.createElement('div');
            temp.innerHTML = newListHtml;
            const newElement = temp.firstElementChild;

            emptyState.replaceWith(newElement);
        }

        // Update sort button icon (without replacing the whole button)
        const sortBtn = document.getElementById('sortBtn');
        if (sortBtn) {
            sortBtn.title = this.sortOrder === 'asc' ? 'Sortér Z-A' : 'Sortér A-Z';
            sortBtn.innerHTML = `
                <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    ${this.sortOrder === 'asc'
                        ? '<path d="M3 6h18M3 12h12M3 18h6"/>'
                        : '<path d="M3 6h6M3 12h12M3 18h18"/>'}
                </svg>
            `;
        }

        // Re-add click listeners to new exercise cards
        document.querySelectorAll('.exercise-list-card').forEach(card => {
            card.addEventListener('click', () => {
                const exerciseId = card.dataset.exerciseId;
                router.navigate(`exercises/${exerciseId}`);
            });
        });
    }
}
