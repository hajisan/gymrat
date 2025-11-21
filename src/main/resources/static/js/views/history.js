/**
 * History View
 * Se tidligere træninger
 */

export class HistoryView {
    async render() {
        return `
            <div class="history-view">
                <header class="view-header">
                    <h1>Historik</h1>
                    <p class="subtle">Dine tidligere træninger</p>
                </header>

                <div class="placeholder">
                    <div class="placeholder__icon">📊</div>
                    <h2>Historik view kommer snart!</h2>
                    <p>Her kan du se alle dine tidligere træninger</p>
                </div>
            </div>
        `;
    }

    mounted() {
        // Event listeners can be added here when needed
    }
}