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
                <header class="page-header page-header--home page-header--centered">
                    <div class="page-header__content">
                        <h1 class="page-header__title">
                            GYMRAT
                        </h1>
                    </div>
                    <div class="page-header__actions">
                        <button class="cta" id="startWorkoutBtn">
                            <svg width="18" height="18" viewBox="0 0 24 24" fill="currentColor">
                                <path d="M7 7h2v10H7V7zm8 0h2v10h-2V7zM11 5h2v14h-2V5z" />
                            </svg>
                            <span>Start træning</span>
                        </button>
                    </div>
                </header>

                ${this.renderWeekStats()}
                ${this.renderTrainingCalendar()}
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
                        <div class="stat__header">
                            <div class="stat__icon" aria-hidden="true">
                                <svg viewBox="0 0 24 24" fill="currentColor">
                                    <path d="M13 3s-1 2-1 4 2 3 2 5-2 4-5 4-5-2-5-5 2-6 6-8c-1 2-1 3 1 5 0-2 2-4 2-5z"/>
                                </svg>
                            </div>
                            <div class="stat__label">Træninger</div>
                        </div>
                        <div class="stat__value">${stats.trainings}</div>
                    </article>

                    <article class="stat">
                        <div class="stat__header">
                            <div class="stat__icon" aria-hidden="true">
                                <svg viewBox="0 0 24 24" fill="currentColor">
                                    <path d="M4 17h3l3-10 4 14 2-6h4" stroke="currentColor" stroke-width="2" fill="none"/>
                                </svg>
                            </div>
                            <div class="stat__label">Sæt</div>
                        </div>
                        <div class="stat__value">${stats.sets}</div>
                    </article>

                    <article class="stat">
                        <div class="stat__header">
                            <div class="stat__icon" aria-hidden="true">
                                <svg viewBox="0 0 24 24" fill="currentColor">
                                    <path d="M3 10h4v10H3V10zm7-6h4v16h-4V4zm7 9h4v7h-4v-7z"/>
                                </svg>
                            </div>
                            <div class="stat__label">Volumen</div>
                        </div>
                        <div class="stat__value">${this.formatVolume(stats.volumeKg)}</div>
                    </article>
                </div>
            </section>
        `;
    }

    renderTrainingCalendar() {
        if (this.loading) {
            return '';
        }

        // Get training days with volume from data
        const trainingDays = this.data?.trainingDays || {};

        // Store for later use in click handlers
        this.calendarDays = [];

        // Generate last 365 days (52 weeks)
        const days = [];
        const today = new Date();
        today.setHours(0, 0, 0, 0);

        // Calculate the start date to align with Monday
        const startDate = new Date(today);
        startDate.setDate(startDate.getDate() - 364);
        let dayOfWeek = startDate.getDay(); // 0 = Sunday, 1 = Monday, etc.

        // Convert to Monday = 0, Sunday = 6
        dayOfWeek = dayOfWeek === 0 ? 6 : dayOfWeek - 1;

        // Pad the beginning with empty cells to align with Monday
        for (let i = 0; i < dayOfWeek; i++) {
            days.push(null);
        }

        // Add the actual 365 days
        for (let i = 364; i >= 0; i--) {
            const date = new Date(today);
            date.setDate(date.getDate() - i);

            // Format date in local timezone to match backend LocalDate format
            const year = date.getFullYear();
            const month = String(date.getMonth() + 1).padStart(2, '0');
            const day = String(date.getDate()).padStart(2, '0');
            const dateString = `${year}-${month}-${day}`;

            const trainingData = trainingDays[dateString];
            const volume = trainingData ? trainingData.volumeKg : 0;
            const sessionId = trainingData ? trainingData.trainingSessionId : null;
            const isToday = i === 0;

            days.push({
                date: dateString,
                dateObj: date,
                volume: volume,
                sessionId: sessionId,
                level: this.getVolumeLevel(volume, trainingDays),
                isToday: isToday
            });
        }

        // Store days for click handlers
        this.calendarDays = days;

        // Get month labels - one per column
        const monthLabels = this.getMonthLabelsPerColumn(days);

        return `
            <section class="section">
                <h2 class="section__title">Habit Tracker</h2>
                <div class="training-calendar">
                    <div class="calendar-wrapper">
                        <div class="calendar-months">
                            ${monthLabels.join('')}
                        </div>
                        <div class="calendar-body">
                            <div class="calendar-days">
                                <span>Man</span>
                                <span>Tir</span>
                                <span>Ons</span>
                                <span>Tor</span>
                                <span>Fre</span>
                                <span>Lør</span>
                                <span>Søn</span>
                            </div>
                            <div class="calendar-squares">
                                ${days.map((day, index) => {
                                    if (!day) return `<div class="calendar-square calendar-square--empty"></div>`;
                                    const hasTraining = day.level > 0;
                                    return `<div class="calendar-square calendar-square--level-${day.level} ${day.isToday ? 'calendar-square--today' : ''} ${hasTraining ? 'calendar-square--clickable' : ''}"
                                         data-day-index="${index}"
                                         title="${this.formatCalendarTooltip(day)}">
                                    </div>`;
                                }).join('')}
                            </div>
                        </div>
                    </div>
                    <div class="calendar-legend">
                        <span class="calendar-legend-text">Mindre</span>
                        <div class="calendar-legend-items">
                            <div class="calendar-square calendar-square--level-0 calendar-square--sample"></div>
                            <div class="calendar-square calendar-square--level-1 calendar-square--sample"></div>
                            <div class="calendar-square calendar-square--level-2 calendar-square--sample"></div>
                            <div class="calendar-square calendar-square--level-3 calendar-square--sample"></div>
                            <div class="calendar-square calendar-square--level-4 calendar-square--sample"></div>
                        </div>
                        <span class="calendar-legend-text">Mere</span>
                    </div>
                </div>
            </section>
        `;
    }

    getVolumeLevel(volume, allTrainingDays) {
        if (volume === 0) return 0;

        // Get all non-zero volumes from training data
        const volumes = Object.values(allTrainingDays)
            .map(data => data.volumeKg)
            .filter(v => v > 0);

        if (volumes.length === 0) return 0;

        // Calculate quartiles
        volumes.sort((a, b) => a - b);
        const q1 = volumes[Math.floor(volumes.length * 0.25)];
        const q2 = volumes[Math.floor(volumes.length * 0.50)];
        const q3 = volumes[Math.floor(volumes.length * 0.75)];

        // Assign level based on quartile
        if (volume <= q1) return 1;
        if (volume <= q2) return 2;
        if (volume <= q3) return 3;
        return 4;
    }

    getMonthLabelsPerColumn(allDays) {
        const labels = [];
        let lastMonth = null;

        // Process in columns (every 7 days = 1 column)
        for (let col = 0; col < allDays.length; col += 7) {
            const firstDayOfColumn = allDays[col];

            if (!firstDayOfColumn) {
                labels.push('<span></span>');
                continue;
            }

            const month = firstDayOfColumn.dateObj.getMonth();

            // Only show label if this is the first column of this month
            if (month !== lastMonth) {
                labels.push(`<span>${this.getMonthName(month)}</span>`);
                lastMonth = month;
            } else {
                labels.push('<span></span>');
            }
        }

        return labels;
    }

    getMonthName(monthIndex) {
        const months = ['Jan', 'Feb', 'Mar', 'Apr', 'Maj', 'Jun', 'Jul', 'Aug', 'Sep', 'Okt', 'Nov', 'Dec'];
        return months[monthIndex];
    }

    formatCalendarTooltip(day) {
        const date = day.dateObj.toLocaleDateString('da-DK', {
            weekday: 'short',
            year: 'numeric',
            month: 'short',
            day: 'numeric'
        });

        if (day.volume === 0) {
            return `${date}: Ingen træning`;
        }

        return `${date}: ${Math.round(day.volume).toLocaleString('da-DK')} kg volumen`;
    }

    renderLastTraining() {
        if (this.loading) {
            return this.renderSkeletonLastTraining();
        }

        const recentTrainings = this.data?.recentTrainings || [];

        // If no trainings exist
        if (recentTrainings.length === 0) {
            return `
                <section class="section">
                    <h2 class="section__title">Seneste træninger</h2>
                    <article class="card last">
                        <p class="subtle">Ingen træning endnu</p>
                    </article>
                </section>
            `;
        }

        // Render all training cards
        const trainingCards = recentTrainings.map(training => {
            const { date, timeRange } = this.formatDateTime(training.startedAt, training.completedAt);
            const duration = this.formatDuration(training.startedAt, training.completedAt);
            const notePreview = training.note ? (training.note.substring(0, 60) + (training.note.length > 60 ? '...' : '')) : null;

            return `
                <div class="workout-card" data-workout-id="${training.trainingSessionId}">
                    <div class="workout-card-header">
                        <div class="workout-card-date">${date}</div>
                        <div class="workout-card-time">${timeRange}</div>
                    </div>
                    <div class="workout-card-meta">
                        <div class="workout-card-duration">Varighed: ${duration}</div>
                        <div class="workout-card-exercises">Øvelser: ${training.exerciseCount}</div>
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
        }).join('');

        return `
            <section class="section">
                <h2 class="section__title">Seneste træninger</h2>
                ${trainingCards}
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
                <h2 class="section__title">Seneste træninger</h2>
                ${[1, 2, 3, 4].map(() => `
                    <article class="card last skeleton">
                        <div class="skeleton-title"></div>
                        <div class="skeleton-text"></div>
                    </article>
                `).join('')}
            </section>
        `;
    }

    formatVolume(kg) {
        if (!kg || kg === 0) return '0 kg';

        // Format med tusindtalsseparator
        return `${Math.round(kg).toLocaleString('da-DK')} kg`;
    }

    formatDateTime(startTimeString, endTimeString) {
        const startTime = new Date(startTimeString);
        const endTime = new Date(endTimeString);
        const days = ['Søndag', 'Mandag', 'Tirsdag', 'Onsdag', 'Torsdag', 'Fredag', 'Lørdag'];
        const months = ['jan', 'feb', 'mar', 'apr', 'maj', 'jun', 'jul', 'aug', 'sep', 'okt', 'nov', 'dec'];

        const dayName = days[startTime.getDay()];
        const day = startTime.getDate();
        const month = months[startTime.getMonth()];
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

        // Add click listeners to all workout cards
        const workoutCards = document.querySelectorAll('.workout-card');
        workoutCards.forEach(workoutCard => {
            workoutCard.addEventListener('click', () => {
                const workoutId = workoutCard.dataset.workoutId;
                window.history.pushState({}, '', `/workout/${workoutId}`);
                window.dispatchEvent(new PopStateEvent('popstate'));
            });
        });

        // Add click listeners to calendar squares with training
        const calendarSquares = document.querySelectorAll('.calendar-square--clickable');
        calendarSquares.forEach(square => {
            square.addEventListener('click', (e) => {
                const dayIndex = parseInt(square.dataset.dayIndex);
                const day = this.calendarDays[dayIndex];
                if (day && day.volume > 0) {
                    this.showDayPopup(day);
                }
            });
        });

        // Auto-scroll calendar to current date (end of timeline) with smooth animation
        const calendarWrapper = document.querySelector('.calendar-wrapper');
        if (calendarWrapper) {
            // Use setTimeout to ensure DOM is fully rendered
            setTimeout(() => {
                calendarWrapper.scrollTo({
                    left: calendarWrapper.scrollWidth,
                    behavior: 'smooth'
                });
            }, 100);
        }
    }

    showDayPopup(day) {
        // Format date nicely
        const dateFormatted = day.dateObj.toLocaleDateString('da-DK', {
            weekday: 'long',
            year: 'numeric',
            month: 'long',
            day: 'numeric'
        });

        const volumeFormatted = Math.round(day.volume).toLocaleString('da-DK');

        // Create popup HTML
        const popup = document.createElement('div');
        popup.className = 'calendar-popup-overlay';
        popup.innerHTML = `
            <div class="calendar-popup">
                <div class="calendar-popup-header">
                    <h3>Træningsdag</h3>
                    <button class="calendar-popup-close" id="closeCalendarPopup">
                        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <line x1="18" y1="6" x2="6" y2="18"></line>
                            <line x1="6" y1="6" x2="18" y2="18"></line>
                        </svg>
                    </button>
                </div>
                <div class="calendar-popup-content">
                    <div class="calendar-popup-date">
                        <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <rect x="3" y="4" width="18" height="18" rx="2" ry="2"></rect>
                            <line x1="16" y1="2" x2="16" y2="6"></line>
                            <line x1="8" y1="2" x2="8" y2="6"></line>
                            <line x1="3" y1="10" x2="21" y2="10"></line>
                        </svg>
                        <span>${dateFormatted}</span>
                    </div>
                    <div class="calendar-popup-volume">
                        <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <path d="M7 7h2v10H7V7zm8 0h2v10h-2V7zM11 5h2v14h-2V5z"/>
                        </svg>
                        <span>Volumen: <strong>${volumeFormatted} kg</strong></span>
                    </div>
                    <button class="calendar-popup-btn" id="viewWorkoutBtn">
                        <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"></path>
                            <circle cx="12" cy="12" r="3"></circle>
                        </svg>
                        <span>Se træning</span>
                    </button>
                </div>
            </div>
        `;

        document.body.appendChild(popup);

        // Add close handlers
        const closeBtn = popup.querySelector('#closeCalendarPopup');
        const closePopup = () => popup.remove();

        closeBtn.addEventListener('click', closePopup);
        popup.addEventListener('click', (e) => {
            if (e.target === popup) closePopup();
        });

        // Add navigation handler
        const viewBtn = popup.querySelector('#viewWorkoutBtn');
        if (viewBtn && day.sessionId) {
            viewBtn.addEventListener('click', () => {
                closePopup();
                window.history.pushState({}, '', `/workout/${day.sessionId}`);
                window.dispatchEvent(new PopStateEvent('popstate'));
            });
        }
    }

    destroy() {
        // Cleanup hvis nødvendigt
    }
}