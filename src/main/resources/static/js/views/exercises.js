/**
 * Exercises View
 * Browse og administrer øvelser
 */

export class ExercisesView {
    async render() {
        return `
            <div class="exercises-view">
                <header class="view-header">
                    <h1>Øvelser</h1>
                    <p class="subtle">Browse og opret øvelser</p>
                </header>

                <div class="placeholder">
                    <div class="placeholder__icon">🏋️</div>
                    <h2>Øvelser view kommer snart!</h2>
                    <p>Her kan du browse og administrere dine øvelser</p>
                </div>
            </div>
        `;
    }

    mounted() {
        // Event listeners can be added here when needed
    }
}