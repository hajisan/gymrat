// Hydrate forsiden fra backend (hurtig, fejltolerant)
async function loadHome() {
    try {
        const res = await fetch('/api/home/summary', { headers: {Accept:'application/json'} });
        if (!res.ok) throw new Error('Netværk');
        const data = await res.json();
        // forventet shape:
        // { week: { trainings: 3, sets: 6, volumeKg: 5190 }, last: { title: "I dag", notes: "..." } }
        document.getElementById('w_trainings').textContent = data.week?.trainings ?? '0';
        document.getElementById('w_sets').textContent      = data.week?.sets ?? '0';
        document.getElementById('w_volume').textContent    = (data.week?.volumeKg ?? 0) + ' kg';
        document.getElementById('last_title').textContent  = data.last?.title ?? '–';
        document.getElementById('last_notes').textContent  = data.last?.notes || 'Ingen noter';
    } catch {
        // vis cached/placeholder værdier – ingen blocking
    }
}
loadHome();

// CTA: start træning (kan være route change eller modal)
document.getElementById('startWorkoutBtn')?.addEventListener('click', () => {
    // fx: location.href = '/workout.html';
    console.log('Start træning');
});

// Tabbar aktivt state (ingen router endnu)
document.querySelectorAll('.tabbar .tab').forEach(btn => {
    btn.addEventListener('click', () => {
        document.querySelectorAll('.tabbar .tab').forEach(t => t.classList.remove('tab--active'));
        btn.classList.add('tab--active');
        // her kan du skifte view/component
    }, { passive: true });
});