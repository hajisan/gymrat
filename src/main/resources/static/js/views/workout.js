/**
 * Workout View
 * Aktiv træning med øvelser og sets
 */

import { api } from '../api.js';
import { state } from '../state.js';
import { router } from '../router.js';
import { DAYS_DA, MONTHS_LONG_DA } from '../utils.js';

export class WorkoutView {
    constructor() {
        this.sessionId = null;
        this.workoutData = null;
        this.exercises = []; // Alle tilgængelige øvelser
        this.showExerciseModal = false;
        this.showNoteModal = false;
        this.workoutNote = '';

        // Rest Timer State
        this.restTimerDuration = 120; // seconds (2:00 default)
        this.restTimerRemaining = 0; // seconds left
        this.restTimerActive = false; // is running
        this.restTimerCompleted = false; // just finished
        this.restTimerInterval = null; // setInterval reference
        this.showRestTimerModal = false; // modal open/closed
        this.showCustomRestTime = false; // custom section expanded
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
                ${this.renderRestTimerButton()}
                ${this.showExerciseModal ? this.renderExerciseModal() : ''}
                ${this.showNoteModal ? this.renderNoteModal() : ''}
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
            <header class="page-header page-header--functional">
                <button class="close-workout" id="closeWorkoutBtn" aria-label="Luk">
                    <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                        <line x1="18" y1="6" x2="6" y2="18"></line>
                        <line x1="6" y1="6" x2="18" y2="18"></line>
                    </svg>
                </button>
                <div class="page-header__content">
                    <h1 class="page-header__title">Aktiv træning</h1>
                </div>
                <div class="page-header__actions">
                    <div class="workout-datetime">
                        <span class="workout-date">${dateStr}</span>
                        <span class="workout-time">${timeStr}</span>
                    </div>
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
                                ${exercise.equipment ? `<p class="exercise-muscle">${exercise.equipment}</p>` : ''}
                            </div>
                            <button type="button" class="exercise-delete" data-performed-exercise-id="${exercise.performedExerciseId}" aria-label="Slet øvelse">
                                <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                    <polyline points="3 6 5 6 21 6"></polyline>
                                    <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path>
                                </svg>
                            </button>
                        </div>
                        
                        ${exercise.lastPerformed ? this.renderLastPerformed(exercise.lastPerformed, exercise.exerciseType) : ''}
                        
                        <div class="exercise-sets" id="sets-container-${exercise.performedExerciseId}">
                            <div class="sets-header">
                                <span>Set</span>
                                <span>Side</span>
                                <span>Kg</span>
                                <span>${exercise.exerciseType === 'DURATION_BASED' ? 'Sek' : 'Reps'}</span>
                                <span></span>
                            </div>
                            ${exercise.sets && exercise.sets.length > 0 ? exercise.sets.map(set => {
                                const weightDisplay = set.weight ? set.weight.toString().replace('.', ',') : '-';
                                const valueDisplay = exercise.exerciseType === 'DURATION_BASED'
                                    ? (set.durationSeconds ? `${set.durationSeconds}s` : '-')
                                    : (set.reps || '-');
                                return `
                                <div class="set-row set-row--saved"
                                     data-set-id="${set.performedSetId}"
                                     data-set-number="${set.setNumber}"
                                     data-performed-exercise-id="${exercise.performedExerciseId}"
                                     data-exercise-type="${exercise.exerciseType || 'REP_BASED'}"
                                     data-side="${set.sideOfBody || 'BOTH'}"
                                     data-weight="${set.weight || ''}"
                                     data-reps="${set.reps || ''}"
                                     data-duration="${set.durationSeconds || ''}">
                                    <span class="set-number">${set.setNumber}</span>
                                    <span class="set-side">${set.sideOfBody === 'LEFT' ? 'V' : set.sideOfBody === 'RIGHT' ? 'H' : 'B'}</span>
                                    <span class="set-weight">${weightDisplay}</span>
                                    <span class="set-reps">${valueDisplay}</span>
                                    <button type="button" class="set-delete" data-set-id="${set.performedSetId}" aria-label="Slet sæt">
                                        <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                            <polyline points="3 6 5 6 21 6"></polyline>
                                            <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path>
                                        </svg>
                                    </button>
                                </div>
                                <div class="set-note-row">
                                    <input type="text"
                                           class="set-note-input"
                                           placeholder="Tilføj kommentar"
                                           value="${set.note || ''}"
                                           data-set-id="${set.performedSetId}">
                                </div>
                                `;
                            }).join('') : ''}
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

    renderLastPerformed(lastData, exerciseType) {
        const isDurationBased = exerciseType === 'DURATION_BASED';

        // Check if we have any data to show
        if (!lastData.averageWeight && !lastData.averageReps && !lastData.averageDuration) {
            return '';
        }

        // Format weight with comma (Danish/European format)
        const weight = lastData.averageWeight ?
            `${Math.round(lastData.averageWeight).toString().replace('.', ',')}kg` : '';

        // Show duration or reps based on exercise type
        const value = isDurationBased
            ? (lastData.averageDuration ? `${lastData.averageDuration}s` : '')
            : (lastData.averageReps ? `${lastData.averageReps} reps` : '');

        const separator = weight && value ? ' × ' : '';

        // Don't show if no data
        if (!weight && !value) {
            return '';
        }

        return `
            <div class="last-performed">
                <span class="last-performed-label">Sidst:</span>
                <span class="last-performed-value">${weight}${separator}${value}</span>
            </div>
        `;
    }

    renderCompleteButton() {
        const hasNote = this.workoutNote && this.workoutNote.trim().length > 0;
        const notePreview = hasNote ? this.workoutNote.substring(0, 50) + (this.workoutNote.length > 50 ? '...' : '') : '';

        return `
            <button class="add-note-btn ${hasNote ? 'add-note-btn--has-note' : ''}" id="addNoteBtn">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
                    <polyline points="14 2 14 8 20 8"></polyline>
                    ${hasNote ? '<polyline points="9 11 12 14 16 10"></polyline>' : '<line x1="12" y1="18" x2="12" y2="12"></line><line x1="9" y1="15" x2="15" y2="15"></line>'}
                </svg>
                <span class="note-btn-text">${hasNote ? notePreview : 'Tilføj note (valgfrit)'}</span>
            </button>

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

    renderNoteModal() {
        return `
            <div class="modal-overlay" id="noteModal">
                <div class="modal">
                    <div class="modal-header">
                        <h2>Note</h2>
                        <button class="modal-close" id="closeNoteModalBtn" aria-label="Luk">
                            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                <line x1="18" y1="6" x2="6" y2="18"></line>
                                <line x1="6" y1="6" x2="18" y2="18"></line>
                            </svg>
                        </button>
                    </div>

                    <div class="modal-content">
                        <div class="note-input-container">
                            <textarea
                                id="workoutNoteInput"
                                class="workout-note-textarea"
                                placeholder="Skriv en note om din træning..."
                                maxlength="255"
                                rows="6"
                            >${this.workoutNote}</textarea>
                            <div class="note-char-count">
                                <span id="noteCharCount">${this.workoutNote.length}</span>/255
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        `;
    }

    renderRestTimerButton() {
        const timeDisplay = this.formatRestTime(
            this.restTimerActive ? this.restTimerRemaining : this.restTimerDuration
        );
        const progress = this.getTimerProgress();
        const isComplete = this.restTimerCompleted;

        // SVG circle math: radius=36, circumference=226.19
        const radius = 36;
        const circumference = 2 * Math.PI * radius;
        const offset = circumference - (progress * circumference);

        const presets = [
            { label: '1:00', seconds: 60 },
            { label: '2:00', seconds: 120 },
            { label: '3:00', seconds: 180 }
        ];

        return `
            <div class="rest-timer-fab-container ${this.showRestTimerModal ? 'rest-timer-fab-container--expanded' : ''}"
                 id="restTimerFabContainer">
                <!-- FAB Default State -->
                <div class="rest-timer-fab-default ${this.showRestTimerModal ? 'rest-timer-fab-default--hidden' : ''}">
                    <button class="rest-timer-fab ${isComplete ? 'rest-timer-fab--complete' : ''}"
                            id="restTimerFab"
                            aria-label="Rest timer">
                        <svg class="rest-timer-ring" width="80" height="80" viewBox="0 0 80 80">
                            <circle cx="40" cy="40" r="${radius}"
                                    fill="none"
                                    stroke="rgba(255, 255, 255, 0.15)"
                                    stroke-width="3"/>
                            <circle cx="40" cy="40" r="${radius}"
                                    fill="none"
                                    stroke="${isComplete ? '#40c463' : '#FFFFFF'}"
                                    stroke-width="3"
                                    stroke-dasharray="${circumference}"
                                    stroke-dashoffset="${offset}"
                                    stroke-linecap="round"
                                    transform="rotate(-90 40 40)"
                                    class="rest-timer-progress"/>
                        </svg>
                        <span class="rest-timer-time">${timeDisplay}</span>
                    </button>
                </div>

                <!-- FAB Expanded State (Pill Content) -->
                <div class="rest-timer-fab-expanded ${this.showRestTimerModal ? 'rest-timer-fab-expanded--visible' : ''}">
                    <div class="rest-timer-expanded-content">
                        <div class="rest-timer-presets">
                            ${presets.map(preset => `
                                <button class="rest-timer-preset"
                                        data-seconds="${preset.seconds}">
                                    ${preset.label}
                                </button>
                            `).join('')}
                        </div>

                        <div class="rest-timer-custom">
                            <button class="rest-timer-custom-header ${this.showCustomRestTime ? 'rest-timer-custom-header--expanded' : ''}"
                                    id="toggleCustomRestTime">
                                <svg xmlns="http://www.w3.org/2000/svg" height="24" viewBox="0 -960 960 960" width="24" fill="currentColor">
                                    <path d="M360-840v-80h240v80H360Zm80 440h80v-240h-80v240Zm40 320q-74 0-139.5-28.5T226-186q-49-49-77.5-114.5T120-440q0-74 28.5-139.5T226-694q49-49 114.5-77.5T480-800q62 0 119 20t107 58l56-56 56 56-56 56q38 50 58 107t20 119q0 74-28.5 139.5T734-186q-49 49-114.5 77.5T480-80Zm0-80q116 0 198-82t82-198q0-116-82-198t-198-82q-116 0-198 82t-82 198q0 116 82 198t198 82Zm0-280Z"/>
                                </svg>
                            </button>
                            <div class="rest-timer-custom-body ${this.showCustomRestTime ? 'rest-timer-custom-body--expanded' : ''}">
                                <div class="rest-timer-custom-inputs">
                                    <input type="number"
                                           id="customRestMinutes"
                                           class="rest-timer-custom-input"
                                           placeholder="0"
                                           min="0"
                                           max="59"
                                           inputmode="numeric">
                                    <span class="rest-timer-colon">:</span>
                                    <input type="number"
                                           id="customRestSeconds"
                                           class="rest-timer-custom-input"
                                           placeholder="00"
                                           min="0"
                                           max="59"
                                           inputmode="numeric">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        `;
    }

    renderRestTimerModal() {
        const presets = [
            { label: '1:00', seconds: 60 },
            { label: '1:30', seconds: 90 },
            { label: '2:00', seconds: 120 },
            { label: '2:30', seconds: 150 },
            { label: '3:00', seconds: 180 },
            { label: '4:00', seconds: 240 },
            { label: '5:00', seconds: 300 }
        ];

        return `
            <div class="modal-overlay" id="restTimerModal">
                <div class="modal rest-timer-modal rest-timer-modal--expand">
                    <div class="modal-header">
                        <h2>Hviletid</h2>
                        <button class="modal-close" id="closeRestTimerModalBtn" aria-label="Luk">
                            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                <line x1="18" y1="6" x2="6" y2="18"></line>
                                <line x1="6" y1="6" x2="18" y2="18"></line>
                            </svg>
                        </button>
                    </div>

                    <div class="modal-content">
                        <div class="rest-timer-presets">
                            ${presets.map(preset => `
                                <button class="rest-timer-preset ${preset.seconds === this.restTimerDuration ? 'rest-timer-preset--active' : ''}"
                                        data-seconds="${preset.seconds}">
                                    ${preset.label}
                                </button>
                            `).join('')}
                        </div>

                        <div class="rest-timer-custom">
                            <label for="customRestTime">Brugerdefineret tid (minutter:sekunder)</label>
                            <div class="rest-timer-custom-inputs">
                                <input type="number"
                                       id="customRestMinutes"
                                       class="rest-timer-custom-input"
                                       placeholder="0"
                                       min="0"
                                       max="59"
                                       inputmode="numeric">
                                <span class="rest-timer-colon">:</span>
                                <input type="number"
                                       id="customRestSeconds"
                                       class="rest-timer-custom-input"
                                       placeholder="00"
                                       min="0"
                                       max="59"
                                       inputmode="numeric">
                            </div>
                            <button class="rest-timer-custom-save" id="saveCustomRestTime">
                                Gem brugerdefineret tid
                            </button>
                        </div>
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

        // Add Note button
        const addNoteBtn = document.getElementById('addNoteBtn');
        if (addNoteBtn) {
            addNoteBtn.addEventListener('click', () => this.openNoteModal());
        }

        // Exercise modal event listeners
        if (this.showExerciseModal) {
            this.setupModalListeners();
        }

        // Note modal event listeners
        if (this.showNoteModal) {
            this.setupNoteModalListeners();
        }

        // Rest Timer FAB expanded event listeners
        this.setupRestTimerFabListeners();

        // Delete exercise buttons
        document.querySelectorAll('.exercise-delete').forEach(btn => {
            btn.addEventListener('click', (e) => {
                e.preventDefault();
                e.stopPropagation();
                const performedExerciseId = e.currentTarget.dataset.performedExerciseId;
                this.deleteExercise(performedExerciseId);
            });
        });

        // Add set buttons
        document.querySelectorAll('.add-set-btn').forEach(btn => {
            btn.addEventListener('click', (e) => {
                e.preventDefault();
                const performedExerciseId = e.currentTarget.dataset.performedExerciseId;
                this.addSet(performedExerciseId);
            });
        });

        // Saved set rows - click to edit
        document.querySelectorAll('.set-row--saved').forEach(row => {
            row.addEventListener('click', (e) => {
                // Don't trigger edit if clicking delete button
                if (e.target.closest('.set-delete')) {
                    return;
                }
                e.preventDefault();
                this.editSet(row);
            });
        });

        // Delete set buttons
        document.querySelectorAll('.set-delete').forEach(btn => {
            btn.addEventListener('click', (e) => {
                e.preventDefault();
                e.stopPropagation();
                const setId = e.currentTarget.dataset.setId;
                this.deleteSet(setId);
            });
        });

        // Set note inputs
        document.querySelectorAll('.set-note-input').forEach(input => {
            input.addEventListener('blur', (e) => {
                const setId = e.currentTarget.dataset.setId;
                const note = e.currentTarget.value;
                this.saveNote(setId, note);
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

    async openNoteModal() {
        this.showNoteModal = true;

        // Re-render content directly instead of full router refresh
        const contentElement = document.getElementById('app-content');
        if (contentElement) {
            contentElement.innerHTML = await this.render();
            this.setupEventListeners();

            // Focus the textarea after rendering
            setTimeout(() => {
                const noteInput = document.getElementById('workoutNoteInput');
                if (noteInput) {
                    noteInput.focus();
                }
            }, 100);
        }
    }

    async closeNoteModal() {
        // Save the note text before closing
        const noteInput = document.getElementById('workoutNoteInput');
        if (noteInput) {
            this.workoutNote = noteInput.value;
        }

        // Add closing animation
        const modalOverlay = document.getElementById('noteModal');
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

            // Wait for animation to complete
            await new Promise(resolve => setTimeout(resolve, 350));
        }

        this.showNoteModal = false;

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

    setupNoteModalListeners() {
        // Close modal button
        const closeModalBtn = document.getElementById('closeNoteModalBtn');
        const modalOverlay = document.getElementById('noteModal');

        if (closeModalBtn) {
            closeModalBtn.addEventListener('click', () => this.closeNoteModal());
        }

        if (modalOverlay) {
            modalOverlay.addEventListener('click', (e) => {
                if (e.target === modalOverlay) {
                    this.closeNoteModal();
                }
            });
        }

        // Character count update
        const noteInput = document.getElementById('workoutNoteInput');
        const charCount = document.getElementById('noteCharCount');
        if (noteInput && charCount) {
            noteInput.addEventListener('input', (e) => {
                charCount.textContent = e.target.value.length;
            });
        }
    }

    setupRestTimerFabListeners() {
        // FAB click to toggle
        const fab = document.getElementById('restTimerFab');
        if (fab) {
            fab.addEventListener('click', () => {
                if (this.showRestTimerModal) {
                    this.closeRestTimerModal();
                } else {
                    this.openRestTimerModal();
                }
            });
        }

        // Toggle custom section
        const toggleCustomBtn = document.getElementById('toggleCustomRestTime');
        if (toggleCustomBtn) {
            toggleCustomBtn.addEventListener('click', () => this.toggleCustomRestTime());
        }

        // Preset buttons - auto close after selection
        document.querySelectorAll('.rest-timer-preset').forEach(btn => {
            btn.addEventListener('click', async (e) => {
                const seconds = parseInt(e.currentTarget.dataset.seconds);
                this.setRestTimerDuration(seconds);
                await this.closeRestTimerModal();
            });
        });

        // Custom time inputs - auto-save on blur
        const minutesInput = document.getElementById('customRestMinutes');
        const secondsInput = document.getElementById('customRestSeconds');

        if (minutesInput && secondsInput) {
            const saveCustomTime = async () => {
                const minutes = parseInt(minutesInput.value) || 0;
                const seconds = parseInt(secondsInput.value) || 0;
                const totalSeconds = (minutes * 60) + seconds;

                if (totalSeconds > 0) {
                    this.setRestTimerDuration(totalSeconds);
                    this.showCustomRestTime = false;
                    await this.closeRestTimerModal();
                }
            };

            minutesInput.addEventListener('blur', saveCustomTime);
            secondsInput.addEventListener('blur', saveCustomTime);
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

                // Re-setup add-set button listeners
                document.querySelectorAll('.add-set-btn').forEach(btn => {
                    btn.addEventListener('click', (e) => {
                        e.preventDefault();
                        const performedExerciseId = e.currentTarget.dataset.performedExerciseId;
                        this.addSet(performedExerciseId);
                    });
                });

                // Re-setup saved set row click listeners
                document.querySelectorAll('.set-row--saved').forEach(row => {
                    row.addEventListener('click', (e) => {
                        // Don't trigger edit if clicking delete button
                        if (e.target.closest('.set-delete')) {
                            return;
                        }
                        e.preventDefault();
                        this.editSet(row);
                    });
                });

                // Re-setup delete set button listeners
                document.querySelectorAll('.set-delete').forEach(btn => {
                    btn.addEventListener('click', (e) => {
                        e.preventDefault();
                        e.stopPropagation();
                        const setId = e.currentTarget.dataset.setId;
                        this.deleteSet(setId);
                    });
                });

                // Re-setup note input listeners
                document.querySelectorAll('.set-note-input').forEach(input => {
                    input.addEventListener('blur', (e) => {
                        const setId = e.currentTarget.dataset.setId;
                        const note = e.currentTarget.value;
                        this.saveNote(setId, note);
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

                // Re-setup add-set button listeners
                document.querySelectorAll('.add-set-btn').forEach(btn => {
                    btn.addEventListener('click', (e) => {
                        e.preventDefault();
                        const performedExerciseId = e.currentTarget.dataset.performedExerciseId;
                        this.addSet(performedExerciseId);
                    });
                });

                // Re-setup saved set row click listeners
                document.querySelectorAll('.set-row--saved').forEach(row => {
                    row.addEventListener('click', (e) => {
                        // Don't trigger edit if clicking delete button
                        if (e.target.closest('.set-delete')) {
                            return;
                        }
                        e.preventDefault();
                        this.editSet(row);
                    });
                });

                // Re-setup delete set button listeners
                document.querySelectorAll('.set-delete').forEach(btn => {
                    btn.addEventListener('click', (e) => {
                        e.preventDefault();
                        e.stopPropagation();
                        const setId = e.currentTarget.dataset.setId;
                        this.deleteSet(setId);
                    });
                });

                // Re-setup note input listeners
                document.querySelectorAll('.set-note-input').forEach(input => {
                    input.addEventListener('blur', (e) => {
                        const setId = e.currentTarget.dataset.setId;
                        const note = e.currentTarget.value;
                        this.saveNote(setId, note);
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
        this.showConfirmModal(
            'Slet øvelse?',
            'Er du sikker på at du vil slette denne øvelse?',
            async () => {
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
        );
    }

    async addSet(performedExerciseId) {
        // Check if there's already a set being edited
        const setsContainer = document.getElementById(`sets-container-${performedExerciseId}`);
        if (!setsContainer) {
            console.error('Sets container not found');
            return;
        }

        const existingEditingRow = setsContainer.querySelector('.set-row--editing');
        if (existingEditingRow) {
            // There's already a set being edited, don't add another one
            console.log('Cannot add new set while another is being edited');
            // Focus the weight input of the existing editing row
            const weightInput = existingEditingRow.querySelector('.set-weight-input');
            if (weightInput) {
                weightInput.focus();
            }
            return;
        }

        // Find the exercise
        const exercise = this.workoutData?.exercises?.find(
            ex => ex.performedExerciseId === parseInt(performedExerciseId)
        );

        if (!exercise) {
            console.error('Exercise not found');
            return;
        }

        // Calculate next set number
        const nextSetNumber = exercise.sets ? exercise.sets.length + 1 : 1;

        // Determine if this is a duration-based exercise
        const isDurationBased = exercise.exerciseType === 'DURATION_BASED';

        // Create the new set row
        const setRow = document.createElement('div');
        setRow.className = 'set-row set-row--editing';
        setRow.dataset.saving = 'false'; // Flag to prevent multiple saves
        setRow.dataset.selectedSide = 'BOTH'; // Default to BOTH
        setRow.dataset.exerciseType = exercise.exerciseType || 'REP_BASED';
        setRow.innerHTML = `
            <span class="set-number">${nextSetNumber}</span>
            <div class="side-selector">
                <button type="button" class="side-btn" data-side="LEFT">V</button>
                <button type="button" class="side-btn side-btn--active" data-side="BOTH">B</button>
                <button type="button" class="side-btn" data-side="RIGHT">H</button>
            </div>
            <input type="text"
                   inputmode="decimal"
                   class="set-input set-weight-input"
                   placeholder="0"
                   data-performed-exercise-id="${performedExerciseId}"
                   data-set-number="${nextSetNumber}">
            <input type="text"
                   inputmode="numeric"
                   class="set-input ${isDurationBased ? 'set-duration-input' : 'set-reps-input'}"
                   placeholder="${isDurationBased ? 'Sek' : '0'}"
                   data-performed-exercise-id="${performedExerciseId}"
                   data-set-number="${nextSetNumber}">
        `;

        setsContainer.appendChild(setRow);

        // Side selector button handlers
        const sideButtons = setRow.querySelectorAll('.side-btn');
        sideButtons.forEach(btn => {
            btn.addEventListener('click', (e) => {
                e.preventDefault();
                e.stopPropagation();
                // Remove active from all buttons
                sideButtons.forEach(b => b.classList.remove('side-btn--active'));
                // Add active to clicked button
                btn.classList.add('side-btn--active');
                // Store selected side
                setRow.dataset.selectedSide = btn.dataset.side;
            });
        });

        // Focus the weight input
        const weightInput = setRow.querySelector('.set-weight-input');
        const valueInput = setRow.querySelector('.set-reps-input') || setRow.querySelector('.set-duration-input');

        // Save function that both inputs will use
        const saveHandler = () => {
            // Small delay to allow for focus changes between inputs
            setTimeout(() => {
                const activeElement = document.activeElement;
                const isStillInRow = setRow.contains(activeElement);
                // Only save if focus has left the row entirely
                if (!isStillInRow) {
                    this.saveSet(performedExerciseId, nextSetNumber, setRow);
                }
            }, 100);
        };

        if (weightInput) {
            weightInput.focus();

            weightInput.addEventListener('blur', saveHandler);
            weightInput.addEventListener('keypress', (e) => {
                if (e.key === 'Enter') {
                    e.preventDefault();
                    // Move to value input (reps or duration)
                    if (valueInput) valueInput.focus();
                }
            });
        }

        if (valueInput) {
            valueInput.addEventListener('blur', saveHandler);
            valueInput.addEventListener('keypress', (e) => {
                if (e.key === 'Enter') {
                    e.preventDefault();
                    e.target.blur();
                }
            });
        }
    }

    editSet(setRow) {
        // Don't edit if already in editing mode
        if (setRow.classList.contains('set-row--editing')) {
            return;
        }

        const performedExerciseId = setRow.dataset.performedExerciseId;
        const setNumber = setRow.dataset.setNumber;
        const side = setRow.dataset.side || 'BOTH';
        const weight = setRow.dataset.weight;
        const reps = setRow.dataset.reps;
        const duration = setRow.dataset.duration;
        const exerciseType = setRow.dataset.exerciseType || 'REP_BASED';

        // Determine if this is a duration-based exercise
        const isDurationBased = exerciseType === 'DURATION_BASED';

        // Convert weight to Danish format (comma instead of dot) for display
        const weightDisplay = weight ? weight.replace('.', ',') : '';
        const valueDisplay = isDurationBased ? duration : reps;

        // Convert to editing mode
        setRow.classList.remove('set-row--saved');
        setRow.classList.add('set-row--editing');
        setRow.dataset.saving = 'false';
        setRow.dataset.selectedSide = side;

        setRow.innerHTML = `
            <span class="set-number">${setNumber}</span>
            <div class="side-selector">
                <button type="button" class="side-btn ${side === 'LEFT' ? 'side-btn--active' : ''}" data-side="LEFT">V</button>
                <button type="button" class="side-btn ${side === 'BOTH' ? 'side-btn--active' : ''}" data-side="BOTH">B</button>
                <button type="button" class="side-btn ${side === 'RIGHT' ? 'side-btn--active' : ''}" data-side="RIGHT">H</button>
            </div>
            <input type="text"
                   inputmode="decimal"
                   class="set-input set-weight-input"
                   placeholder="0"
                   value="${weightDisplay}"
                   data-performed-exercise-id="${performedExerciseId}"
                   data-set-number="${setNumber}">
            <input type="text"
                   inputmode="numeric"
                   class="set-input ${isDurationBased ? 'set-duration-input' : 'set-reps-input'}"
                   placeholder="${isDurationBased ? 'Sek' : '0'}"
                   value="${valueDisplay}"
                   data-performed-exercise-id="${performedExerciseId}"
                   data-set-number="${setNumber}">
        `;

        // Side selector button handlers
        const sideButtons = setRow.querySelectorAll('.side-btn');
        sideButtons.forEach(btn => {
            btn.addEventListener('click', (e) => {
                e.preventDefault();
                e.stopPropagation();
                // Remove active from all buttons
                sideButtons.forEach(b => b.classList.remove('side-btn--active'));
                // Add active to clicked button
                btn.classList.add('side-btn--active');
                // Store selected side
                setRow.dataset.selectedSide = btn.dataset.side;
            });
        });

        // Focus the weight input
        const weightInput = setRow.querySelector('.set-weight-input');
        const valueInput = setRow.querySelector('.set-reps-input') || setRow.querySelector('.set-duration-input');

        // Save function that both inputs will use
        const updateHandler = () => {
            // Small delay to allow for focus changes between inputs
            setTimeout(() => {
                const activeElement = document.activeElement;
                const isStillInRow = setRow.contains(activeElement);
                // Only save if focus has left the row entirely
                if (!isStillInRow) {
                    this.updateSet(performedExerciseId, setNumber, setRow);
                }
            }, 100);
        };

        if (weightInput) {
            weightInput.focus();
            // Select all text for easy editing
            weightInput.select();

            weightInput.addEventListener('blur', updateHandler);
            weightInput.addEventListener('keypress', (e) => {
                if (e.key === 'Enter') {
                    e.preventDefault();
                    // Move to value input (reps or duration)
                    if (valueInput) {
                        valueInput.focus();
                        valueInput.select();
                    }
                }
            });
        }

        if (valueInput) {
            valueInput.addEventListener('blur', updateHandler);
            valueInput.addEventListener('keypress', (e) => {
                if (e.key === 'Enter') {
                    e.preventDefault();
                    e.target.blur();
                }
            });
        }
    }

    async updateSet(performedExerciseId, setNumber, setRow) {
        // Prevent multiple saves
        if (setRow.dataset.saving === 'true') {
            return;
        }
        setRow.dataset.saving = 'true';

        const weightInput = setRow.querySelector('.set-weight-input');
        const repsInput = setRow.querySelector('.set-reps-input');
        const durationInput = setRow.querySelector('.set-duration-input');
        const selectedSide = setRow.dataset.selectedSide || 'BOTH';
        const exerciseType = setRow.dataset.exerciseType || 'REP_BASED';

        // Replace comma with dot for decimal separator (Danish/European format)
        const weight = weightInput?.value ? parseFloat(weightInput.value.replace(',', '.')) : null;
        const reps = repsInput?.value ? parseInt(repsInput.value) : null;
        const durationSeconds = durationInput?.value ? parseInt(durationInput.value) : null;

        // Don't save if weight and value (reps or duration) are both empty
        if (!weight && !reps && !durationSeconds) {
            setRow.dataset.saving = 'false';
            return;
        }

        try {
            // Call backend API to update the set (same endpoint, backend handles update)
            await api.request(`/workout/${this.sessionId}/sets`, {
                method: 'POST',
                body: JSON.stringify({
                    performedExerciseId: parseInt(performedExerciseId),
                    setNumber: parseInt(setNumber),
                    sideOfBody: selectedSide,
                    weight: weight,
                    reps: reps,
                    durationSeconds: durationSeconds,
                    completed: false
                })
            });

            const sideDisplay = selectedSide === 'LEFT' ? 'V' : selectedSide === 'RIGHT' ? 'H' : 'B';

            // Format weight with comma for display (Danish/European format)
            const weightDisplay = weight ? weight.toString().replace('.', ',') : '-';

            // Determine value display based on exercise type
            const valueDisplay = exerciseType === 'DURATION_BASED'
                ? (durationSeconds ? `${durationSeconds}s` : '-')
                : (reps || '-');

            // Reload workout data to get the set ID
            this.workoutData = await api.request(`/workout/${this.sessionId}`);
            state.set('activeWorkout', this.workoutData);

            // Find the saved set to get its ID
            const exercise = this.workoutData.exercises.find(ex => ex.performedExerciseId === parseInt(performedExerciseId));
            const savedSet = exercise?.sets?.find(s => s.setNumber === parseInt(setNumber));

            // Update the row to show saved state
            setRow.classList.remove('set-row--editing');
            setRow.classList.add('set-row--saved');
            setRow.dataset.setId = savedSet?.performedSetId || '';
            setRow.dataset.performedExerciseId = performedExerciseId;
            setRow.dataset.setNumber = setNumber;
            setRow.dataset.exerciseType = exerciseType;
            setRow.dataset.side = selectedSide;
            setRow.dataset.weight = weight || '';
            setRow.dataset.reps = reps || '';
            setRow.dataset.duration = durationSeconds || '';
            setRow.innerHTML = `
                <span class="set-number">${setNumber}</span>
                <span class="set-side">${sideDisplay}</span>
                <span class="set-weight">${weightDisplay}</span>
                <span class="set-reps">${valueDisplay}</span>
                <button type="button" class="set-delete" data-set-id="${savedSet?.performedSetId || ''}" aria-label="Slet sæt">
                    <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                        <polyline points="3 6 5 6 21 6"></polyline>
                        <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path>
                    </svg>
                </button>
            `;

            // Re-attach click listener for future edits
            setRow.addEventListener('click', (e) => {
                // Don't trigger edit if clicking delete button
                if (e.target.closest('.set-delete')) {
                    return;
                }
                e.preventDefault();
                this.editSet(setRow);
            });

            // Re-attach delete button listener
            const deleteBtn = setRow.querySelector('.set-delete');
            if (deleteBtn) {
                deleteBtn.addEventListener('click', (e) => {
                    e.preventDefault();
                    e.stopPropagation();
                    const setId = e.currentTarget.dataset.setId;
                    this.deleteSet(setId);
                });
            }

            // Check if note row already exists (from editing existing set)
            let noteRow = setRow.nextElementSibling;
            if (noteRow && noteRow.classList.contains('set-note-row')) {
                // Update existing note row
                const noteInput = noteRow.querySelector('.set-note-input');
                if (noteInput) {
                    noteInput.value = savedSet?.note || '';
                    noteInput.dataset.setId = savedSet?.performedSetId || '';
                }
            } else {
                // Add new note input row after this set row
                noteRow = document.createElement('div');
                noteRow.className = 'set-note-row';
                noteRow.innerHTML = `
                    <input type="text"
                           class="set-note-input"
                           placeholder="Tilføj kommentar"
                           value="${savedSet?.note || ''}"
                           data-set-id="${savedSet?.performedSetId || ''}">
                `;
                setRow.parentNode.insertBefore(noteRow, setRow.nextSibling);
            }

            // Attach note input listener
            const noteInput = noteRow.querySelector('.set-note-input');
            if (noteInput) {
                // Remove old listeners (if any) by cloning and replacing
                const newNoteInput = noteInput.cloneNode(true);
                noteInput.parentNode.replaceChild(newNoteInput, noteInput);

                newNoteInput.addEventListener('blur', (e) => {
                    const setId = e.currentTarget.dataset.setId;
                    const note = e.currentTarget.value;
                    this.saveNote(setId, note);
                });
            }

            // Auto-start rest timer after set is updated
            this.startRestTimer();

        } catch (error) {
            console.error('Failed to update set. Full error:', error);
            console.error('Error response:', error.response);
            setRow.dataset.saving = 'false';
            alert(`Kunne ikke opdatere sæt. Fejl: ${error.message || 'Ukendt fejl'}`);
        }
    }

    async saveSet(performedExerciseId, setNumber, setRow) {
        // Prevent multiple saves
        if (setRow.dataset.saving === 'true') {
            return;
        }
        setRow.dataset.saving = 'true';

        const weightInput = setRow.querySelector('.set-weight-input');
        const repsInput = setRow.querySelector('.set-reps-input');
        const durationInput = setRow.querySelector('.set-duration-input');
        const selectedSide = setRow.dataset.selectedSide || 'BOTH';
        const exerciseType = setRow.dataset.exerciseType || 'REP_BASED';

        // Replace comma with dot for decimal separator (Danish/European format)
        const weight = weightInput?.value ? parseFloat(weightInput.value.replace(',', '.')) : null;
        const reps = repsInput?.value ? parseInt(repsInput.value) : null;
        const durationSeconds = durationInput?.value ? parseInt(durationInput.value) : null;

        // Don't save if weight and value (reps or duration) are both empty
        if (!weight && !reps && !durationSeconds) {
            setRow.dataset.saving = 'false';
            return;
        }

        try {
            // Call backend API to log the set
            await api.request(`/workout/${this.sessionId}/sets`, {
                method: 'POST',
                body: JSON.stringify({
                    performedExerciseId: parseInt(performedExerciseId),
                    setNumber: setNumber,
                    sideOfBody: selectedSide,
                    weight: weight,
                    reps: reps,
                    durationSeconds: durationSeconds,
                    completed: false
                })
            });

            const sideDisplay = selectedSide === 'LEFT' ? 'V' : selectedSide === 'RIGHT' ? 'H' : 'B';

            // Format weight with comma for display (Danish/European format)
            const weightDisplay = weight ? weight.toString().replace('.', ',') : '-';

            // Determine value display based on exercise type
            const valueDisplay = exerciseType === 'DURATION_BASED'
                ? (durationSeconds ? `${durationSeconds}s` : '-')
                : (reps || '-');

            // Reload workout data to get the set ID
            this.workoutData = await api.request(`/workout/${this.sessionId}`);
            state.set('activeWorkout', this.workoutData);

            // Find the saved set to get its ID
            const exercise = this.workoutData.exercises.find(ex => ex.performedExerciseId === parseInt(performedExerciseId));
            const savedSet = exercise?.sets?.find(s => s.setNumber === parseInt(setNumber));

            // Update the row to show saved state
            setRow.classList.remove('set-row--editing');
            setRow.classList.add('set-row--saved');
            setRow.dataset.setId = savedSet?.performedSetId || '';
            setRow.dataset.performedExerciseId = performedExerciseId;
            setRow.dataset.setNumber = setNumber;
            setRow.dataset.exerciseType = exerciseType;
            setRow.dataset.side = selectedSide;
            setRow.dataset.weight = weight || '';
            setRow.dataset.reps = reps || '';
            setRow.dataset.duration = durationSeconds || '';
            setRow.innerHTML = `
                <span class="set-number">${setNumber}</span>
                <span class="set-side">${sideDisplay}</span>
                <span class="set-weight">${weightDisplay}</span>
                <span class="set-reps">${valueDisplay}</span>
                <button type="button" class="set-delete" data-set-id="${savedSet?.performedSetId || ''}" aria-label="Slet sæt">
                    <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                        <polyline points="3 6 5 6 21 6"></polyline>
                        <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path>
                    </svg>
                </button>
            `;

            // Re-attach click listener for future edits
            setRow.addEventListener('click', (e) => {
                // Don't trigger edit if clicking delete button
                if (e.target.closest('.set-delete')) {
                    return;
                }
                e.preventDefault();
                this.editSet(setRow);
            });

            // Re-attach delete button listener
            const deleteBtn = setRow.querySelector('.set-delete');
            if (deleteBtn) {
                deleteBtn.addEventListener('click', (e) => {
                    e.preventDefault();
                    e.stopPropagation();
                    const setId = e.currentTarget.dataset.setId;
                    this.deleteSet(setId);
                });
            }

            // Add note input row after this set row
            const noteRow = document.createElement('div');
            noteRow.className = 'set-note-row';
            noteRow.innerHTML = `
                <input type="text"
                       class="set-note-input"
                       placeholder="Tilføj kommentar"
                       value="${savedSet?.note || ''}"
                       data-set-id="${savedSet?.performedSetId || ''}">
            `;
            setRow.parentNode.insertBefore(noteRow, setRow.nextSibling);

            // Attach note input listener
            const noteInput = noteRow.querySelector('.set-note-input');
            if (noteInput) {
                noteInput.addEventListener('blur', (e) => {
                    const setId = e.currentTarget.dataset.setId;
                    const note = e.currentTarget.value;
                    this.saveNote(setId, note);
                });
            }

            // Auto-start rest timer after set is saved
            this.startRestTimer();

        } catch (error) {
            console.error('Failed to save set. Full error:', error);
            console.error('Error response:', error.response);
            setRow.dataset.saving = 'false';
            alert(`Kunne ikke gemme sæt. Fejl: ${error.message || 'Ukendt fejl'}`);
        }
    }

    async deleteSet(setId) {
        this.showConfirmModal(
            'Slet sæt?',
            'Er du sikker på at du vil slette dette sæt?',
            async () => {
                try {
                    await api.request(`/workout/${this.sessionId}/sets/${setId}`, {
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
                    console.error('Failed to delete set:', error);
                    alert('Kunne ikke slette sæt. Prøv igen.');
                }
            }
        );
    }

    async saveNote(setId, note) {
        try {
            // Find the set in workout data
            let targetSet = null;
            let targetExercise = null;

            for (const exercise of this.workoutData.exercises) {
                const set = exercise.sets?.find(s => s.performedSetId === parseInt(setId));
                if (set) {
                    targetSet = set;
                    targetExercise = exercise;
                    break;
                }
            }

            if (!targetSet || !targetExercise) {
                console.error('Set not found');
                return;
            }

            // Update the set with the note (send all existing data plus updated note)
            await api.request(`/workout/${this.sessionId}/sets`, {
                method: 'POST',
                body: JSON.stringify({
                    performedExerciseId: targetExercise.performedExerciseId,
                    setNumber: targetSet.setNumber,
                    sideOfBody: targetSet.sideOfBody,
                    weight: targetSet.weight,
                    reps: targetSet.reps,
                    durationSeconds: targetSet.durationSeconds,
                    note: note || null, // Send null if empty string
                    completed: false
                })
            });

            // Reload workout data to stay in sync
            this.workoutData = await api.request(`/workout/${this.sessionId}`);
            state.set('activeWorkout', this.workoutData);

        } catch (error) {
            console.error('Failed to save note:', error);
            alert('Kunne ikke gemme kommentar. Prøv igen.');
        }
    }

    closeWorkout() {
        // Clear active workout from state
        state.set('activeWorkout', null);

        // Navigate to home
        window.history.back();
    }

    async completeWorkout() {
        try {
            // Save note if modal is open
            if (this.showNoteModal) {
                const noteInput = document.getElementById('workoutNoteInput');
                if (noteInput) {
                    this.workoutNote = noteInput.value;
                }
            }

            // Call API to complete the workout
            await api.request(`/workout/${this.sessionId}/complete`, {
                method: 'POST',
                body: JSON.stringify({
                    note: this.workoutNote || null
                })
            });

            // Clear active workout from state
            state.set('activeWorkout', null);

            // Show confirmation toast
            this.showToast('Træning gemt!');

            // Navigate to home after a brief delay to show toast
            setTimeout(() => {
                router.navigate('home');
            }, 500);
        } catch (error) {
            console.error('Failed to complete workout:', error);
            alert('Kunne ikke gemme træning. Prøv igen.');
        }
    }

    formatDate(date) {
        return `${DAYS_DA[date.getDay()]} ${date.getDate()}. ${MONTHS_LONG_DA[date.getMonth()]}`;
    }

    formatTime(date) {
        const hours = String(date.getHours()).padStart(2, '0');
        const minutes = String(date.getMinutes()).padStart(2, '0');
        return `${hours}:${minutes}`;
    }

    showToast(message) {
        // Create toast element
        const toast = document.createElement('div');
        toast.className = 'toast';
        toast.textContent = message;

        // Add to body
        document.body.appendChild(toast);

        // Remove after animation completes (3 seconds total)
        setTimeout(() => {
            toast.remove();
        }, 3000);
    }

    showConfirmModal(title, message, onConfirm) {
        // Create modal overlay
        const overlay = document.createElement('div');
        overlay.className = 'confirm-overlay';

        // Create modal dialog
        const dialog = document.createElement('div');
        dialog.className = 'confirm-dialog';

        dialog.innerHTML = `
            <h2 class="confirm-title">${title}</h2>
            <p class="confirm-message">${message}</p>
            <div class="confirm-actions">
                <button type="button" class="confirm-btn confirm-btn-cancel" id="confirmCancel">Annuller</button>
                <button type="button" class="confirm-btn confirm-btn-confirm" id="confirmDelete">Slet</button>
            </div>
        `;

        overlay.appendChild(dialog);
        document.body.appendChild(overlay);

        // Prevent dialog clicks from closing overlay
        dialog.addEventListener('click', (e) => {
            e.stopPropagation();
        });

        // Handle cancel
        const cancelBtn = document.getElementById('confirmCancel');
        if (cancelBtn) {
            cancelBtn.addEventListener('click', (e) => {
                e.preventDefault();
                e.stopPropagation();
                overlay.remove();
            });
        }

        // Handle confirm
        const confirmBtn = document.getElementById('confirmDelete');
        if (confirmBtn) {
            confirmBtn.addEventListener('click', (e) => {
                e.preventDefault();
                e.stopPropagation();
                overlay.remove();
                onConfirm();
            });
        }

        // Close on overlay click (but not dialog click)
        overlay.addEventListener('click', (e) => {
            if (e.target === overlay) {
                overlay.remove();
            }
        });
    }

    // ============================================
    // Rest Timer Methods
    // ============================================

    formatRestTime(seconds) {
        const mins = Math.floor(seconds / 60);
        const secs = seconds % 60;
        return `${String(mins).padStart(2, '0')}:${String(secs).padStart(2, '0')}`;
    }

    getTimerProgress() {
        if (!this.restTimerActive || this.restTimerDuration === 0) {
            return 0;
        }
        return this.restTimerRemaining / this.restTimerDuration;
    }

    startRestTimer() {
        // Stop any existing timer
        this.stopRestTimer();

        // Set remaining time to full duration
        this.restTimerRemaining = this.restTimerDuration;
        this.restTimerActive = true;
        this.restTimerCompleted = false; // Reset completed state

        // Update UI immediately
        this.updateRestTimerUI();

        // Start countdown
        this.restTimerInterval = setInterval(() => {
            this.restTimerRemaining--;

            if (this.restTimerRemaining <= 0) {
                this.restTimerRemaining = 0;
                this.restTimerCompleted = true; // Mark as completed
                this.stopRestTimer();
                // Timer complete - trigger visual feedback
                this.onRestTimerComplete();
            }

            this.updateRestTimerUI();
        }, 1000);
    }

    stopRestTimer() {
        if (this.restTimerInterval) {
            clearInterval(this.restTimerInterval);
            this.restTimerInterval = null;
        }
        this.restTimerActive = false;
    }

    resetRestTimer() {
        this.stopRestTimer();
        this.restTimerRemaining = 0;
        this.restTimerCompleted = false;
        this.updateRestTimerUI();
    }

    updateRestTimerUI() {
        const fab = document.getElementById('restTimerFab');
        if (!fab) return;

        const timeDisplay = this.formatRestTime(
            this.restTimerActive ? this.restTimerRemaining : this.restTimerDuration
        );
        const progress = this.getTimerProgress();
        const isComplete = this.restTimerCompleted;

        // Update time display
        const timeElement = fab.querySelector('.rest-timer-time');
        if (timeElement) {
            timeElement.textContent = timeDisplay;
        }

        // Update progress ring
        const progressCircle = fab.querySelector('.rest-timer-progress');
        if (progressCircle) {
            const radius = 36;
            const circumference = 2 * Math.PI * radius;
            const offset = circumference - (progress * circumference);
            progressCircle.setAttribute('stroke-dashoffset', offset);
            progressCircle.setAttribute('stroke', isComplete ? '#40c463' : '#FFFFFF');
        }

        // Update complete state
        if (isComplete) {
            fab.classList.add('rest-timer-fab--complete');
        } else {
            fab.classList.remove('rest-timer-fab--complete');
        }
    }

    onRestTimerComplete() {
        // Vibrate if supported
        if ('vibrate' in navigator) {
            navigator.vibrate([200, 100, 200]);
        }

        // Visual feedback already handled by updateRestTimerUI (green pulse)
    }

    async openRestTimerModal() {
        this.showRestTimerModal = true;

        // Create transparent overlay
        const overlay = document.createElement('div');
        overlay.className = 'rest-timer-overlay';
        overlay.id = 'restTimerOverlay';
        overlay.addEventListener('click', () => this.closeRestTimerModal());
        document.body.appendChild(overlay);

        const container = document.getElementById('restTimerFabContainer');
        const defaultState = container?.querySelector('.rest-timer-fab-default');
        const expandedState = container?.querySelector('.rest-timer-fab-expanded');

        if (container) {
            container.classList.add('rest-timer-fab-container--expanded');
        }
        if (defaultState) {
            defaultState.classList.add('rest-timer-fab-default--hidden');
        }
        if (expandedState) {
            expandedState.classList.add('rest-timer-fab-expanded--visible');
        }
    }

    async closeRestTimerModal() {
        const container = document.getElementById('restTimerFabContainer');
        const defaultState = container?.querySelector('.rest-timer-fab-default');
        const expandedState = container?.querySelector('.rest-timer-fab-expanded');
        const overlay = document.getElementById('restTimerOverlay');
        const customHeader = document.getElementById('toggleCustomRestTime');
        const customBody = customHeader?.parentElement?.querySelector('.rest-timer-custom-body');

        // Remove overlay
        if (overlay) {
            overlay.remove();
        }

        if (container) {
            container.classList.remove('rest-timer-fab-container--expanded');
            container.classList.remove('rest-timer-fab-container--custom-open');
        }
        if (defaultState) {
            defaultState.classList.remove('rest-timer-fab-default--hidden');
        }
        if (expandedState) {
            expandedState.classList.remove('rest-timer-fab-expanded--visible');
        }

        // Reset custom time section
        if (customHeader) {
            customHeader.classList.remove('rest-timer-custom-header--expanded');
        }
        if (customBody) {
            customBody.classList.remove('rest-timer-custom-body--expanded');
        }
        this.showCustomRestTime = false;

        // Wait for animation before updating state
        await new Promise(resolve => setTimeout(resolve, 400));
        this.showRestTimerModal = false;
    }

    setRestTimerDuration(seconds) {
        this.restTimerDuration = seconds;

        // If timer is not running, update display
        if (!this.restTimerActive) {
            this.updateRestTimerUI();
        }
    }

    toggleCustomRestTime() {
        this.showCustomRestTime = !this.showCustomRestTime;

        const container = document.getElementById('restTimerFabContainer');
        const header = document.getElementById('toggleCustomRestTime');
        const body = header?.parentElement?.querySelector('.rest-timer-custom-body');

        if (this.showCustomRestTime) {
            container?.classList.add('rest-timer-fab-container--custom-open');
            header?.classList.add('rest-timer-custom-header--expanded');
            body?.classList.add('rest-timer-custom-body--expanded');
        } else {
            container?.classList.remove('rest-timer-fab-container--custom-open');
            header?.classList.remove('rest-timer-custom-header--expanded');
            body?.classList.remove('rest-timer-custom-body--expanded');
        }
    }

    destroy() {
        // Stop timer when leaving view
        this.stopRestTimer();
    }
}