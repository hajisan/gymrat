/**
 * Utility functions og konstanter
 * Delte hjælpefunktioner til brug på tværs af views
 */

// Danske ugedage (søndag = 0)
export const DAYS_DA = ['Søndag', 'Mandag', 'Tirsdag', 'Onsdag', 'Torsdag', 'Fredag', 'Lørdag'];

// Danske måneder (kort format)
export const MONTHS_SHORT_DA = ['jan', 'feb', 'mar', 'apr', 'maj', 'jun', 'jul', 'aug', 'sep', 'okt', 'nov', 'dec'];

// Danske måneder (lang format)
export const MONTHS_LONG_DA = ['januar', 'februar', 'marts', 'april', 'maj', 'juni', 'juli', 'august', 'september', 'oktober', 'november', 'december'];

// Danske måneder (title case for kalender)
export const MONTHS_TITLE_DA = ['Jan', 'Feb', 'Mar', 'Apr', 'Maj', 'Jun', 'Jul', 'Aug', 'Sep', 'Okt', 'Nov', 'Dec'];

/**
 * Formaterer dato til dansk format: "Mandag 1. jan 2025"
 * @param {Date} date - Dato objekt
 * @returns {string} Formateret dato string
 */
export function formatDateDa(date) {
    const day = DAYS_DA[date.getDay()];
    const dayNum = date.getDate();
    const month = MONTHS_SHORT_DA[date.getMonth()];
    const year = date.getFullYear();
    return `${day} ${dayNum}. ${month} ${year}`;
}

/**
 * Formaterer dato til kort dansk format: "1. jan"
 * @param {Date} date - Dato objekt
 * @returns {string} Formateret dato string
 */
export function formatDateShortDa(date) {
    const dayNum = date.getDate();
    const month = MONTHS_SHORT_DA[date.getMonth()];
    return `${dayNum}. ${month}`;
}