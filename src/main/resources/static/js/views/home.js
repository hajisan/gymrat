/**
 * Home View
 * Forside med ugestatistik og seneste træning
 */

import { api } from '../api.js';
import { state } from '../state.js';
import { router } from '../router.js';

export class HomeView {
    constructor() {
        this.data = null;
        this.loading = true;
    }

    async render() {
        // Fetch data from backend
        await this.loadData();

        return `
            <div class="home">
                <header class="home__head">
                    <h1 class="brand">GymRat 💪</h1>
                    <p class="subtle">Klar til at træne?</p>

                    <button class="cta" id="startWorkoutBtn">
                        <svg width="18" height="18" viewBox="0 0 24 24" fill="currentColor">
                            <path d="M7 7h2v10H7V7zm8 0h2v10h-2V7zM11 5h2v14h-2V5z" />
                        </svg>
                        <span>Start træning</span>
                    </button>
                </header>

                ${this.renderWeekStats()}
                ${this.renderLastTraining()}
            </div>
        `;
    }

    renderWeekStats() {
        if (this.loading) {
            return this.renderSkeletonStats();
        }

        const stats = this.data?.weekStats || { trainings: 0, sets: 0, volumeKg: 0 };

        return `
            <section class="section">
                <h2 class="section__title">Denne uge</h2>
                <div class="stats">
                    <article class="stat">
                        <div class="stat__icon" aria-hidden="true">
                            <svg viewBox="0 0 24 24" fill="currentColor">
                                <path d="M13 3s-1 2-1 4 2 3 2 5-2 4-5 4-5-2-5-5 2-6 6-8c-1 2-1 3 1 5 0-2 2-4 2-5z"/>
                            </svg>
                        </div>
                        <div class="stat__label">Træninger</div>
                        <div class="stat__value">${stats.trainings}</div>
                    </article>

                    <article class="stat">
                        <div class="stat__icon" aria-hidden="true">
                            <svg viewBox="0 0 24 24" fill="currentColor">
                                <path d="M4 17h3l3-10 4 14 2-6h4" stroke="currentColor" stroke-width="2" fill="none"/>
                            </svg>
                        </div>
                        <div class="stat__label">Sæt</div>
                        <div class="stat__value">${stats.sets}</div>
                    </article>

                    <article class="stat">
                        <div class="stat__icon" aria-hidden="true">
                            <svg viewBox="0 0 24 24" fill="currentColor">
                                <path d="M3 10h4v10H3V10zm7-6h4v16h-4V4zm7 9h4v7h-4v-7z"/>
                            </svg>
                        </div>
                        <div class="stat__label">Volumen</div>
                        <div class="stat__value">${this.formatVolume(stats.volumeKg)}</div>
                    </article>
                </div>
            </section>
        `;
    }

    renderLastTraining() {
        if (this.loading) {
            return this.renderSkeletonLastTraining();
        }

        const lastTraining = this.data?.lastTraining || { date: 'Ingen træning', note: '' };

        return `
            <section class="section">
                <h2 class="section__title">Seneste træning</h2>
                <article class="card last">
                    <div class="last__row">
                        <div class="last__title">${lastTraining.date}</div>
                    </div>
                    <p class="subtle">${lastTraining.note || 'Ingen noter'}</p>
                </article>
            </section>
        `;
    }

    renderSkeletonStats() {
        return `
            <section class="section">
                <h2 class="section__title">Denne uge</h2>
                <div class="stats">
                    ${[1, 2, 3].map(() => `
                        <article class="stat skeleton">
                            <div class="skeleton-icon"></div>
                            <div class="skeleton-text"></div>
                            <div class="skeleton-value"></div>
                        </article>
                    `).join('')}
                </div>
            </section>
        `;
    }

    renderSkeletonLastTraining() {
        return `
            <section class="section">
                <h2 class="section__title">Seneste træning</h2>
                <article class="card last skeleton">
                    <div class="skeleton-title"></div>
                    <div class="skeleton-text"></div>
                </article>
            </section>
        `;
    }

    formatVolume(kg) {
        if (!kg || kg === 0) return '0 kg';

        // Format med tusindtalsseparator
        return `${Math.round(kg).toLocaleString('da-DK')} kg`;
    }

    async loadData() {
        try {
            this.loading = true;
            this.data = await api.getHomeSummary();

            // Save til state for caching
            state.set('homeSummary', this.data);

            this.loading = false;
        } catch (error) {
            console.error('Failed to load home data:', error);
            this.loading = false;

            // Use cached data if available
            this.data = state.get('homeSummary');
        }
    }

    mounted() {
        // Setup event listeners efter render
        const startBtn = document.getElementById('startWorkoutBtn');
        if (startBtn) {
            startBtn.addEventListener('click', async () => {
                // Navigate to workout view
                const { router } = await import('../router.js');
                router.navigate('workout');
            });
        }

        // Add touch feedback
        this.addTouchFeedback();
    }

    addTouchFeedback() {
        const cta = document.querySelector('.cta');
        if (cta) {
            cta.addEventListener('touchstart', () => {
                cta.style.transform = 'scale(0.98)';
            }, { passive: true });

            cta.addEventListener('touchend', () => {
                cta.style.transform = 'scale(1)';
            }, { passive: true });
        }
    }

    destroy() {
        // Cleanup hvis nødvendigt
    }
}