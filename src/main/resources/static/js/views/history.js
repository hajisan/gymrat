/**
 * History View
 * Se tidligere træninger
 */

import { api } from '../api.js';
import { DAYS_DA, MONTHS_SHORT_DA } from '../utils.js';

export class HistoryView {
    constructor() {
        this.workouts = [];
    }

    async render() {
        // Fetch all workouts
        await this.loadWorkouts();

        return `
            <div class="history-view">
                <header class="page-header page-header--centered">
                    <div class="page-header__content">
                        <h1 class="page-header__title">Historik</h1>
                        <p class="page-header__subtitle">Dine tidligere træninger</p>
                    </div>
                </header>

                <div class="history-content">
                    ${this.workouts.length > 0 ? this.renderWorkouts() : this.renderEmptyState()}
                </div>
            </div>
        `;
    }

    async loadWorkouts() {
        try {
            this.workouts = await api.request('/workout/history');
        } catch (error) {
            console.error('Failed to load workout history:', error);
            this.workouts = [];
        }
    }

    renderWorkouts() {
        return `
            <div class="workout-list">
                ${this.workouts.map(workout => this.renderWorkoutCard(workout)).join('')}
            </div>
        `;
    }

    renderWorkoutCard(workout) {
        const { date, timeRange } = this.formatDateTime(workout.startedAt, workout.completedAt);
        const duration = this.formatDuration(workout.startedAt, workout.completedAt);
        const notePreview = workout.note ? (workout.note.substring(0, 60) + (workout.note.length > 60 ? '...' : '')) : null;

        return `
            <div class="workout-card" data-workout-id="${workout.trainingSessionId}">
                <div class="workout-card-header">
                    <div class="workout-card-date">${date}</div>
                    <div class="workout-card-time">${timeRange}</div>
                </div>
                <div class="workout-card-meta">
                    <div class="workout-card-duration">Varighed: ${duration}</div>
                    <div class="workout-card-exercises">Øvelser: ${workout.exerciseCount}</div>
                </div>
                ${notePreview ? `
                    <div class="workout-card-note">
                        <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
                            <polyline points="14 2 14 8 20 8"></polyline>
                        </svg>
                        <span>${notePreview}</span>
                    </div>
                ` : ''}
            </div>
        `;
    }

    renderEmptyState() {
        return `
            <div class="empty-state">
                <div class="placeholder__icon">📊</div>
                <h2>Ingen træninger endnu</h2>
                <p>Dine afsluttede træninger vises her</p>
            </div>
        `;
    }

    formatDateTime(startTimeString, endTimeString) {
        const startTime = new Date(startTimeString);
        const endTime = new Date(endTimeString);

        const dayName = DAYS_DA[startTime.getDay()];
        const day = startTime.getDate();
        const month = MONTHS_SHORT_DA[startTime.getMonth()];
        const year = startTime.getFullYear();

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

    formatDuration(startTimeString, endTimeString) {
        const startTime = new Date(startTimeString);
        const endTime = new Date(endTimeString);

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
        // Add click listeners to workout cards
        document.querySelectorAll('.workout-card').forEach(card => {
            card.addEventListener('click', () => {
                const workoutId = card.dataset.workoutId;
                window.history.pushState({}, '', `/workout/${workoutId}`);
                window.dispatchEvent(new PopStateEvent('popstate'));
            });
        });
    }
}