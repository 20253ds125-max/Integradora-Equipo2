const adminStorageKey = "gedsAdminVenues";
const defaultVenues = [
  { id: 1, name: "Hacienda Los Arcos", city: "San Miguel de Allende, Guanajuato", category: "Hacienda para gala", date: "Oct 24, 2026", status: "pending", image: "https://images.unsplash.com/photo-1518005020951-eccb494ad742?auto=format&fit=crop&w=200&q=80" },
  { id: 2, name: "Villa Brisa de Tulum", city: "Tulum, Quintana Roo", category: "Villa frente al mar", date: "Oct 21, 2026", status: "validated", image: "https://images.unsplash.com/photo-1500530855697-b586d89ba3ee?auto=format&fit=crop&w=200&q=80" },
  { id: 3, name: "Casa Puerto Escondido", city: "Puerto Escondido, Oaxaca", category: "Casa de eventos costera", date: "Oct 18, 2026", status: "rejected", image: "https://images.unsplash.com/photo-1600585154340-be6161a56a0c?auto=format&fit=crop&w=200&q=80" }
];
let venues = JSON.parse(localStorage.getItem(adminStorageKey) || JSON.stringify(defaultVenues));
if (venues.some((venue) => /Paris|Brooklyn|Switzerland|Obsidian|Marble Peak|Loft Project/i.test(`${venue.name} ${venue.city}`))) {
  venues = defaultVenues;
  localStorage.setItem(adminStorageKey, JSON.stringify(venues));
}
const labels = { pending: "Pendiente", validated: "Validado", rejected: "Rechazado" };
const rows = document.querySelector("[data-admin-rows]");
const pendingStat = document.querySelector("[data-stat-pending]");
const validStat = document.querySelector("[data-stat-valid]");
const totalStat = document.querySelector("[data-stat-total]");

function saveVenues() {
  localStorage.setItem(adminStorageKey, JSON.stringify(venues));
}

function renderStats() {
  pendingStat.textContent = String(venues.filter((venue) => venue.status === "pending").length);
  validStat.textContent = String(venues.filter((venue) => venue.status === "validated").length);
  totalStat.textContent = String(venues.length);
}

function renderRows() {
  rows.innerHTML = venues.map((venue) => `
    <tr>
      <td><div class="venue-cell"><img src="${venue.image}" alt="${venue.name}" /><div><strong>${venue.name}</strong><br /><span>${venue.city}</span></div></div></td>
      <td>${venue.category}</td>
      <td>${venue.date}</td>
      <td><span class="status ${venue.status}">${labels[venue.status]}</span></td>
      <td><div class="action-row">
      <button data-action="validated" data-id="${venue.id}">Aceptar</button>
      <button data-action="rejected" data-id="${venue.id}">Rechazar</button>
      </div></td>
    </tr>`).join("");
  renderStats();
}

rows.addEventListener("click", (event) => {
  const button = event.target.closest("button");
  if (!button) return;
  const id = Number(button.dataset.id);
  const index = venues.findIndex((venue) => venue.id === id);
  if (index < 0) return;
  if (button.dataset.action === "delete") {
    venues.splice(index, 1);
  } else if (button.dataset.action === "edit") {
    const nextName = prompt("Editar nombre del recinto", venues[index].name);
    if (nextName) venues[index].name = nextName.trim();
    venues[index].status = "pending";
  } else {
    venues[index].status = button.dataset.action;
  }
  saveVenues();
  renderRows();
});

renderRows();

