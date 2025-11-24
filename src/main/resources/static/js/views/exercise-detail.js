/**
 * Exercise Detail View
 * Create or edit an exercise
 */

import { api } from '../api.js';
import { router } from '../router.js';

export class ExerciseDetailView {
    constructor() {
        this.exercise = null;
        this.exerciseId = null;
        this.isNew = false;
    }

    async render() {
        // Get exercise ID from URL
        const pathParts = window.location.pathname.split('/');
        const idPart = pathParts[pathParts.length - 1];

        this.isNew = idPart === 'new';

        if (!this.isNew) {
            this.exerciseId = idPart;
            await this.loadExercise();

            if (!this.exercise) {
                return `
                    <div class="exercise-detail-view">
                        <div class="empty-state">
                            <h2>Øvelse ikke fundet</h2>
                            <button class="btn-primary" onclick="history.back()">Tilbage</button>
                        </div>
                    </div>
                `;
            }
        } else {
            // Initialize empty exercise for creation
            this.exercise = {
                name: '',
                equipment: '',
                exerciseType: 'REP_BASED'
            };
        }

        return `
            <div class="exercise-detail-view">
                ${this.renderHeader()}
                ${this.renderForm()}
            </div>
        `;
    }

    async loadExercise() {
        try {
            this.exercise = await api.request(`/exercises/${this.exerciseId}`);
        } catch (error) {
            console.error('Failed to load exercise:', error);
            this.exercise = null;
        }
    }

    renderHeader() {
        return `
            <header class="exercise-detail-header">
                <button type="button" class="back-button" id="backButton">
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                        <polyline points="15 18 9 12 15 6"></polyline>
                    </svg>
                    Tilbage
                </button>
                <h1>${this.isNew ? 'Ny øvelse' : 'Rediger øvelse'}</h1>
                ${!this.isNew ? `
                    <button type="button" class="btn-delete-header" id="deleteExerciseButton">
                        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <polyline points="3 6 5 6 21 6"></polyline>
                            <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path>
                        </svg>
                    </button>
                ` : ''}
            </header>
        `;
    }

    renderForm() {
        return `
            <form class="exercise-form" id="exerciseForm">
                <div class="form-group">
                    <label for="exerciseName">Navn *</label>
                    <input
                        type="text"
                        id="exerciseName"
                        name="name"
                        value="${this.exercise.name || ''}"
                        placeholder="F.eks. Bænkpres"
                        required
                        maxlength="100"
                    />
                </div>

                <div class="form-group">
                    <label for="exerciseEquipment">Udstyr</label>
                    <input
                        type="text"
                        id="exerciseEquipment"
                        name="equipment"
                        value="${this.exercise.equipment || ''}"
                        placeholder="F.eks. Vægtstang"
                    />
                </div>

                <div class="form-group">
                    <label for="exerciseMuscleGroup">Muskelgruppe</label>
                    <input
                        type="text"
                        id="exerciseMuscleGroup"
                        name="targetMuscleGroup"
                        value="${this.exercise.targetMuscleGroup || ''}"
                        placeholder="F.eks. Bryst"
                    />
                </div>

                <div class="form-group">
                    <label>Type *</label>
                    <div class="type-buttons">
                        <button
                            type="button"
                            class="type-btn ${this.exercise.exerciseType === 'REP_BASED' ? 'type-btn-active' : ''}"
                            data-type="REP_BASED"
                        >
                            Reps
                        </button>
                        <button
                            type="button"
                            class="type-btn ${this.exercise.exerciseType === 'DURATION_BASED' ? 'type-btn-active' : ''}"
                            data-type="DURATION_BASED"
                        >
                            Tid
                        </button>
                    </div>
                    <input type="hidden" id="exerciseType" name="exerciseType" value="${this.exercise.exerciseType || 'REP_BASED'}" />
                </div>

                <button type="submit" class="btn-save" id="saveButton">
                    ${this.isNew ? 'Opret øvelse' : 'Gem ændringer'}
                </button>
            </form>
        `;
    }

    mounted() {
        // Add back button listener
        const backButton = document.getElementById('backButton');
        if (backButton) {
            backButton.addEventListener('click', () => {
                router.navigate('exercises');
            });
        }

        // Add type button listeners
        const typeButtons = document.querySelectorAll('.type-btn');
        const typeHiddenInput = document.getElementById('exerciseType');

        typeButtons.forEach(btn => {
            btn.addEventListener('click', () => {
                // Remove active class from all buttons
                typeButtons.forEach(b => b.classList.remove('type-btn-active'));

                // Add active class to clicked button
                btn.classList.add('type-btn-active');

                // Update hidden input value
                if (typeHiddenInput) {
                    typeHiddenInput.value = btn.dataset.type;
                }
            });
        });

        // Add form submit listener
        const form = document.getElementById('exerciseForm');
        if (form) {
            form.addEventListener('submit', (e) => {
                e.preventDefault();
                this.saveExercise();
            });
        }

        // Add delete button listener
        const deleteButton = document.getElementById('deleteExerciseButton');
        if (deleteButton) {
            deleteButton.addEventListener('click', () => {
                this.confirmDelete();
            });
        }
    }

    async saveExercise() {
        const form = document.getElementById('exerciseForm');
        const formData = new FormData(form);

        const exerciseData = {
            name: formData.get('name'),
            equipment: formData.get('equipment') || null,
            targetMuscleGroup: formData.get('targetMuscleGroup') || null,
            exerciseType: formData.get('exerciseType')
        };

        try {
            if (this.isNew) {
                // Create new exercise
                await api.request('/exercises', {
                    method: 'POST',
                    body: JSON.stringify(exerciseData)
                });
                this.showToast('Øvelse oprettet!');
            } else {
                // Update existing exercise
                await api.request(`/exercises/${this.exerciseId}`, {
                    method: 'PUT',
                    body: JSON.stringify(exerciseData)
                });
                this.showToast('Ændringer gemt!');
            }

            // Navigate back to exercises list after a brief delay
            setTimeout(() => {
                router.navigate('exercises');
            }, 500);
        } catch (error) {
            console.error('Failed to save exercise:', error);
            alert('Der opstod en fejl ved gemning af øvelsen');
        }
    }

    confirmDelete() {
        this.showConfirmModal(
            'Slet øvelse?',
            'Er du sikker på, at du vil slette denne øvelse? Dette kan ikke fortrydes.',
            () => this.deleteExercise()
        );
    }

    async deleteExercise() {
        try {
            await api.request(`/exercises/${this.exerciseId}`, {
                method: 'DELETE'
            });

            // Show success toast
            this.showToast('Øvelse slettet!');

            // Navigate back to exercises list after a brief delay
            setTimeout(() => {
                router.navigate('exercises');
            }, 500);
        } catch (error) {
            console.error('Failed to delete exercise:', error);
            alert('Der opstod en fejl ved sletning af øvelsen');
        }
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
