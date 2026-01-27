/**
 * Workout Detail View
 * Read-only view of a completed workout
 */

import { api } from '../api.js';
import { router } from '../router.js';
import { DAYS_DA, MONTHS_SHORT_DA } from '../utils.js';

export class WorkoutDetailView {
    constructor() {
        this.workout = null;
        this.workoutId = null;
    }

    async render() {
        // Get workout ID from URL
        const pathParts = window.location.pathname.split('/');
        this.workoutId = pathParts[pathParts.length - 1];

        // Fetch workout data
        await this.loadWorkout();

        if (!this.workout) {
            return `
                <div class="workout-detail-view">
                    <div class="empty-state">
                        <h2>Træning ikke fundet</h2>
                        <button class="btn-primary" onclick="history.back()">Tilbage</button>
                    </div>
                </div>
            `;
        }

        return `
            <div class="workout-detail-view">
                ${this.renderHeader()}
                ${this.workout.note ? this.renderNote() : ''}
                ${this.renderExercises()}
                ${this.renderSummary()}
            </div>
        `;
    }

    async loadWorkout() {
        try {
            this.workout = await api.request(`/workout/${this.workoutId}`);
        } catch (error) {
            console.error('Failed to load workout:', error);
            this.workout = null;
        }
    }

    renderHeader() {
        const { date, timeRange } = this.formatDateTime(this.workout.startedAt);
        const duration = this.formatDuration(this.workout.startedAt);

        return `
            <header class="page-header page-header--detail">
                <button class="back-button" id="backButton">
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                        <polyline points="15 18 9 12 15 6"></polyline>
                    </svg>
                    Tilbage
                </button>

                <div class="page-header__content">
                    <h1 class="page-header__title">${date}</h1>
                    <p class="page-header__subtitle">${timeRange}</p>
                    <p class="page-header__subtitle">Varighed: ${duration}</p>
                </div>
            </header>
        `;
    }

    renderNote() {
        // Estimate if note will span more than 3 lines (roughly 100 characters on iPhone 12 mini)
        const isLongNote = this.workout.note.length > 100;

        return `
            <div class="workout-note-section ${isLongNote ? 'has-more' : ''}" id="noteSection">
                <div class="workout-note-header">
                    <span class="note-label">NOTE</span>
                    ${isLongNote ? `
                        <svg class="note-expand-icon" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <polyline points="6 9 12 15 18 9"></polyline>
                        </svg>
                    ` : ''}
                </div>
                <div class="workout-note-content">
                    <p>${this.workout.note}</p>
                </div>
            </div>
        `;
    }

    renderExercises() {
        if (!this.workout.exercises || this.workout.exercises.length === 0) {
            return '<p class="subtle">Ingen øvelser i denne træning</p>';
        }

        return `
            <div class="workout-detail-exercises">
                ${this.workout.exercises.map(exercise => this.renderExercise(exercise)).join('')}
            </div>
        `;
    }

    renderExercise(exercise) {
        return `
            <div class="exercise-detail-card">
                <div class="exercise-detail-header">
                    <h3>${exercise.exerciseName}</h3>
                    <p class="exercise-detail-equipment">${exercise.equipment}</p>
                </div>

                <div class="exercise-detail-sets">
                    ${exercise.sets.map(set => this.renderSet(set, exercise.exerciseType)).join('')}
                </div>
            </div>
        `;
    }

    renderSet(set, exerciseType) {
        const isDurationBased = exerciseType === 'DURATION_BASED';
        const sideLabel = set.sideOfBody !== 'BOTH' ? ` (${set.sideOfBody === 'LEFT' ? 'Venstre' : 'Højre'})` : '';

        let performanceText = '';
        if (isDurationBased) {
            performanceText = `${set.durationSeconds}s`;
        } else {
            const parts = [];
            if (set.weight) parts.push(`${set.weight} kg`);
            if (set.reps) parts.push(`${set.reps} reps`);
            performanceText = parts.join(' × ');
        }

        return `
            <div class="set-detail-row">
                <div class="set-detail-number">Sæt ${set.setNumber}${sideLabel}</div>
                <div class="set-detail-performance">${performanceText}</div>
                ${set.note ? `<div class="set-detail-note">${set.note}</div>` : ''}
            </div>
        `;
    }

    renderSummary() {
        const totalExercises = this.workout.exercises.length;
        const totalSets = this.workout.exercises.reduce((sum, ex) => sum + ex.sets.length, 0);

        // Calculate total volume (weight × reps)
        let totalVolume = 0;
        this.workout.exercises.forEach(exercise => {
            if (exercise.exerciseType === 'REP_BASED') {
                exercise.sets.forEach(set => {
                    if (set.weight && set.reps) {
                        totalVolume += set.weight * set.reps;
                    }
                });
            }
        });

        return `
            <div class="workout-detail-summary">
                <h3>Sammendrag</h3>
                <div class="stats-bar">
                    <div class="stats-bar__item">
                        <svg viewBox="0 0 24 24" fill="none">
                            <path d="M13 3c0 0-1 2-1 4s2 3 2 5-2 4-5 4-5-2-5-5 2-6 6-8" stroke="currentColor" stroke-width="2"/>
                            <path d="M14 8c0-2 2-4 2-5" stroke="currentColor" stroke-width="2"/>
                        </svg>
                        <span class="stats-bar__value">${totalExercises}</span>
                        <span class="stats-bar__label">øvelse${totalExercises !== 1 ? 'r' : ''}</span>
                    </div>
                    <div class="stats-bar__divider"></div>
                    <div class="stats-bar__item">
                        <svg viewBox="0 0 24 24" fill="none">
                            <path d="M4 17h3l3-10 4 14 2-6h4" stroke="currentColor" stroke-width="2"/>
                        </svg>
                        <span class="stats-bar__value">${totalSets}</span>
                        <span class="stats-bar__label">sæt</span>
                    </div>
                    ${totalVolume > 0 ? `
                        <div class="stats-bar__divider"></div>
                        <div class="stats-bar__item">
                            <svg viewBox="0 0 24 24" fill="none">
                                <rect x="3" y="13" width="4" height="8" stroke="currentColor" stroke-width="2"/>
                                <rect x="10" y="4" width="4" height="17" stroke="currentColor" stroke-width="2"/>
                                <rect x="17" y="9" width="4" height="12" stroke="currentColor" stroke-width="2"/>
                            </svg>
                            <span class="stats-bar__value">${this.formatVolume(totalVolume)}</span>
                            <span class="stats-bar__label">volumen</span>
                        </div>
                    ` : ''}
                </div>

                <button type="button" class="btn-delete" id="deleteWorkoutButton">
                    <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                        <polyline points="3 6 5 6 21 6"></polyline>
                        <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path>
                    </svg>
                    Slet træning
                </button>
            </div>
        `;
    }

    formatVolume(volumeKg) {
        if (volumeKg >= 1000) {
            return `${(volumeKg / 1000).toFixed(1)} t`;
        }
        return `${Math.round(volumeKg)} kg`;
    }

    formatDateTime(startTimeString) {
        const startTime = new Date(startTimeString);

        const dayName = DAYS_DA[startTime.getDay()];
        const day = startTime.getDate();
        const month = MONTHS_SHORT_DA[startTime.getMonth()];
        const year = startTime.getFullYear();

        // Get both start and end times from the workout object
        const endTime = new Date(this.workout.completedAt);

        const startHours = String(startTime.getHours()).padStart(2, '0');
        const startMinutes = String(startTime.getMinutes()).padStart(2, '0');
        const startTimeFormatted = `${startHours}:${startMinutes}`;

        const endHours = String(endTime.getHours()).padStart(2, '0');
        const endMinutes = String(endTime.getMinutes()).padStart(2, '0');
        const endTimeFormatted = `${endHours}:${endMinutes}`;

        return {
            date: `${dayName} ${day}. ${month} ${year}`,
            timeRange: `${startTimeFormatted} - ${endTimeFormatted}`
        };
    }

    formatDuration(startTimeString) {
        const startTime = new Date(startTimeString);
        const endTime = new Date(this.workout.completedAt);

        const durationMs = endTime - startTime;
        const durationMinutes = Math.floor(durationMs / 1000 / 60);

        const hours = Math.floor(durationMinutes / 60);
        const minutes = durationMinutes % 60;

        if (hours > 0) {
            const hourText = hours === 1 ? 'time' : 'timer';
            return `${hours} ${hourText} ${minutes} minutter`;
        } else {
            return `${minutes} minutter`;
        }
    }

    mounted() {
        // Add back button listener
        const backButton = document.getElementById('backButton');
        if (backButton) {
            backButton.addEventListener('click', () => {
                // Use browser back to return to previous page (could be home or history)
                window.history.back();
            });
        }

        // Add note expand/collapse listener
        const noteSection = document.getElementById('noteSection');
        if (noteSection && noteSection.classList.contains('has-more')) {
            noteSection.addEventListener('click', () => {
                noteSection.classList.toggle('expanded');
            });
        }

        // Add delete button listener
        const deleteButton = document.getElementById('deleteWorkoutButton');
        if (deleteButton) {
            deleteButton.addEventListener('click', (e) => {
                e.preventDefault();
                e.stopPropagation();
                this.confirmDelete();
            });
        }
    }

    confirmDelete() {
        this.showConfirmModal(
            'Slet træning?',
            'Er du sikker på, at du vil slette denne træning? Dette kan ikke fortrydes.',
            () => this.deleteWorkout()
        );
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

    async deleteWorkout() {
        try {
            await api.request(`/workout/${this.workoutId}`, {
                method: 'DELETE'
            });

            // Show success toast
            this.showToast('Træning slettet!');

            // Navigate back to history after a brief delay
            setTimeout(() => {
                router.navigate('history');
            }, 500);
        } catch (error) {
            console.error('Failed to delete workout:', error);
            alert('Der opstod en fejl ved sletning af træningen');
        }
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
}
