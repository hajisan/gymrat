/**
 * Stats View
 * Display workout statistics and progress charts
 */

import { api } from '../api.js';

export class StatsView {
    constructor() {
        this.workouts = [];
        this.exercises = [];
        this.selectedExerciseId = null;
        this.chart = null;
        this.chartLoaded = false;
    }

    async render() {
        // Load Chart.js if not already loaded
        if (!this.chartLoaded) {
            await this.loadChartJS();
        }

        // Load data
        await this.loadData();

        // Auto-select first exercise if available
        if (this.exercises.length > 0 && !this.selectedExerciseId) {
            this.selectedExerciseId = this.exercises[0].exerciseId;
        }

        return `
            <div class="stats-view">
                <header class="page-header page-header--centered">
                    <div class="page-header__content">
                        <h1 class="page-header__title">Statistik</h1>
                        <p class="page-header__subtitle">Se stats for øvelser + mere</p>
                    </div>
                </header>

                ${this.renderOverviewStats()}
                ${this.renderExerciseSelector()}
                ${this.renderChart()}
            </div>
        `;
    }

    async loadChartJS() {
        return new Promise((resolve, reject) => {
            // Check if Chart.js is already loaded
            if (window.Chart) {
                this.chartLoaded = true;
                resolve();
                return;
            }

            const script = document.createElement('script');
            script.src = 'https://cdn.jsdelivr.net/npm/chart.js@4.4.1/dist/chart.umd.min.js';
            script.onload = () => {
                this.chartLoaded = true;
                resolve();
            };
            script.onerror = () => reject(new Error('Failed to load Chart.js'));
            document.head.appendChild(script);
        });
    }

    async loadData() {
        try {
            // Load workout summaries first
            const workoutSummaries = await api.request('/workout/history');

            // Fetch detailed workout data for each session (includes exercises and sets)
            const detailedWorkouts = await Promise.all(
                workoutSummaries.map(summary =>
                    api.request(`/workout/${summary.trainingSessionId}`)
                )
            );

            this.workouts = detailedWorkouts;

            // Load exercises
            this.exercises = await api.request('/exercises');
        } catch (error) {
            console.error('Failed to load stats data:', error);
            this.workouts = [];
            this.exercises = [];
        }
    }

    renderOverviewStats() {
        const stats = this.calculateOverviewStats();

        return `
            <div class="stats-overview">
                <div class="stat-card">
                    <div class="stat-value">${stats.totalWorkouts}</div>
                    <div class="stat-label">Træninger</div>
                </div>
                <div class="stat-card">
                    <div class="stat-value">${stats.streak}</div>
                    <div class="stat-label">Dages streak</div>
                </div>
                <div class="stat-card">
                    <div class="stat-value">${this.formatVolume(stats.totalVolume)}</div>
                    <div class="stat-label">Total volumen</div>
                </div>
            </div>
        `;
    }

    calculateOverviewStats() {
        const totalWorkouts = this.workouts.length;
        const streak = this.calculateStreak();
        const totalVolume = this.calculateTotalVolume();

        return { totalWorkouts, streak, totalVolume };
    }

    calculateStreak() {
        if (this.workouts.length === 0) return 0;

        // Sort workouts by date (newest first)
        const sortedWorkouts = [...this.workouts].sort((a, b) =>
            new Date(b.completedAt) - new Date(a.completedAt)
        );

        let streak = 0;
        let currentDate = new Date();
        currentDate.setHours(0, 0, 0, 0);

        for (const workout of sortedWorkouts) {
            const workoutDate = new Date(workout.completedAt);
            workoutDate.setHours(0, 0, 0, 0);

            const daysDiff = Math.floor((currentDate - workoutDate) / (1000 * 60 * 60 * 24));

            if (daysDiff === streak) {
                streak++;
                currentDate = workoutDate;
            } else if (daysDiff > streak + 1) {
                break;
            }
        }

        return streak;
    }

    calculateTotalVolume() {
        let volume = 0;

        this.workouts.forEach(workout => {
            workout.exercises?.forEach(exercise => {
                if (exercise.exerciseType === 'REP_BASED') {
                    exercise.sets?.forEach(set => {
                        if (set.weight && set.reps) {
                            volume += set.weight * set.reps;
                        }
                    });
                }
            });
        });

        return volume;
    }

    formatVolume(volume) {
        if (volume >= 1000000) {
            return `${(volume / 1000000).toFixed(1)}M kg`;
        } else if (volume >= 1000) {
            return `${(volume / 1000).toFixed(1)}K kg`;
        }
        return `${Math.round(volume)} kg`;
    }

    renderExerciseSelector() {
        if (this.exercises.length === 0) {
            return `
                <div class="stats-empty">
                    <p>Ingen øvelser fundet. Opret øvelser for at se statistik.</p>
                </div>
            `;
        }

        const selectedExercise = this.exercises.find(ex => ex.exerciseId === this.selectedExerciseId);

        return `
            <div class="exercise-selector">
                <label>Vælg øvelse</label>
                <button type="button" class="exercise-selector-btn" id="exerciseSelectorBtn">
                    <span>${selectedExercise ? selectedExercise.name : 'Vælg øvelse'}</span>
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                        <polyline points="6 9 12 15 18 9"></polyline>
                    </svg>
                </button>
            </div>
            ${this.renderExerciseModal()}
        `;
    }

    renderExerciseModal() {
        return `
            <div class="modal-overlay" id="exerciseModal" style="display: none;">
                <div class="modal">
                    <div class="modal-header">
                        <h2>Vælg øvelse</h2>
                        <button type="button" class="modal-close" id="closeExerciseModal">
                            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                <line x1="18" y1="6" x2="6" y2="18"></line>
                                <line x1="6" y1="6" x2="18" y2="18"></line>
                            </svg>
                        </button>
                    </div>
                    <div class="modal-content">
                        ${this.exercises.map(exercise => `
                            <button
                                type="button"
                                class="exercise-option ${exercise.exerciseId === this.selectedExerciseId ? 'exercise-option--selected' : ''}"
                                data-exercise-id="${exercise.exerciseId}"
                            >
                                <div class="exercise-option-info">
                                    <span class="exercise-option-name">${exercise.name}</span>
                                    ${exercise.equipment ? `<span class="exercise-option-equipment">${exercise.equipment}</span>` : ''}
                                </div>
                                ${exercise.exerciseId === this.selectedExerciseId ? `
                                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5">
                                        <polyline points="20 6 9 17 4 12"></polyline>
                                    </svg>
                                ` : ''}
                            </button>
                        `).join('')}
                    </div>
                </div>
            </div>
        `;
    }

    renderChart() {
        if (this.exercises.length === 0) {
            return '';
        }

        return `
            <div class="chart-container">
                <h2>Vægtprogression</h2>
                <canvas id="progressChart"></canvas>
            </div>
        `;
    }

    mounted() {
        // Add exercise selector button listener
        const selectorBtn = document.getElementById('exerciseSelectorBtn');
        const modal = document.getElementById('exerciseModal');

        if (selectorBtn && modal) {
            selectorBtn.addEventListener('click', () => {
                modal.style.display = 'flex';
                // Prevent body scroll when modal is open
                document.body.style.overflow = 'hidden';
            });
        }

        // Add close button listener
        const closeBtn = document.getElementById('closeExerciseModal');
        if (closeBtn && modal) {
            closeBtn.addEventListener('click', () => {
                this.closeExerciseModal();
            });
        }

        // Add overlay click listener to close modal
        if (modal) {
            modal.addEventListener('click', (e) => {
                if (e.target === modal) {
                    this.closeExerciseModal();
                }
            });
        }

        // Add exercise option listeners
        const exerciseOptions = document.querySelectorAll('.exercise-option');
        exerciseOptions.forEach(option => {
            option.addEventListener('click', async () => {
                const exerciseId = parseInt(option.dataset.exerciseId);
                this.selectedExerciseId = exerciseId;

                // Close modal
                this.closeExerciseModal();

                // Update the view
                const contentElement = document.getElementById('app-content');
                if (contentElement) {
                    contentElement.innerHTML = await this.render();
                    this.mounted();
                }
            });
        });

        // Initialize chart
        if (this.selectedExerciseId) {
            this.updateChart();
        }
    }

    closeExerciseModal() {
        const modal = document.getElementById('exerciseModal');
        if (modal) {
            modal.style.display = 'none';
            document.body.style.overflow = '';
        }
    }

    async updateChart() {
        const chartData = this.getExerciseProgressData(this.selectedExerciseId);

        // Destroy existing chart
        if (this.chart) {
            this.chart.destroy();
        }

        const ctx = document.getElementById('progressChart');
        if (!ctx) return;

        this.chart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: chartData.labels,
                datasets: [{
                    label: 'Vægt (kg)',
                    data: chartData.data,
                    borderColor: '#007A2EC6', // Line color (the main progression line)
                    backgroundColor: 'rgba(0,122,46,0.31)', // Fill area under the line
                    tension: 0.4,
                    fill: true,
                    pointRadius: 5,
                    pointHoverRadius: 7,
                    pointBackgroundColor: '#007A2EC6', // Dot fill color
                    pointBorderColor: 'rgb(218,218,218)', // Dot border/outline color
                    pointBorderWidth: 2 // dot border
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: true,
                plugins: {
                    legend: {
                        display: false
                    },
                    tooltip: {
                        backgroundColor: 'rgba(0,0,0,0.76)', // Tooltip background color
                        padding: 12,
                        cornerRadius: 8,
                        displayColors: false,
                        titleColor: '#FFFFFF', // Tooltip title text color (date)
                        bodyColor: '#FFFFFF', // Tooltip body text color (weight value)
                        callbacks: {
                            title: (items) => {
                                return items[0].label;
                            },
                            label: (item) => {
                                return `${item.parsed.y} kg`;
                            }
                        }
                    }
                },
                scales: {
                    y: {
                        beginAtZero: false,
                        ticks: {
                            callback: (value) => `${value} kg`,
                            color: '#86868B' // Y-axis label text color (weight labels)
                        },
                        grid: {
                            color: 'rgba(134, 134, 139, 0.2)' // Y-axis horizontal grid lines
                        }
                    },
                    x: {
                        ticks: {
                            color: '#86868B' // X-axis label text color (date labels)
                        },
                        grid: {
                            display: false
                        }
                    }
                }
            }
        });
    }

    getExerciseProgressData(exerciseId) {
        const dataPoints = [];

        // Find all workouts containing this exercise
        this.workouts.forEach(workout => {
            const exercise = workout.exercises?.find(ex => ex.exerciseId === exerciseId);

            if (exercise) {
                if (exercise.exerciseType === 'REP_BASED') {
                    // Calculate average weight for this workout
                    const weights = exercise.sets
                        ?.filter(set => set.weight)
                        .map(set => set.weight) || [];

                    if (weights.length > 0) {
                        const avgWeight = weights.reduce((sum, w) => sum + w, 0) / weights.length;
                        const date = new Date(workout.completedAt);

                        dataPoints.push({
                            date: date,
                            weight: avgWeight,
                            label: this.formatChartDate(date)
                        });
                    }
                }
            }
        });

        // Sort by date
        dataPoints.sort((a, b) => a.date - b.date);

        return {
            labels: dataPoints.map(dp => dp.label),
            data: dataPoints.map(dp => dp.weight)
        };
    }

    formatChartDate(date) {
        const day = date.getDate();
        const month = date.getMonth() + 1;
        return `${day}/${month}`;
    }

    destroy() {
        // Clean up chart
        if (this.chart) {
            this.chart.destroy();
            this.chart = null;
        }
    }
}
