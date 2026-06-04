const guestsStorageKey = "gedsGuests";
const seatingStorageKey = "gedsSeatingPlan";
const seatsPerTable = 10;

const defaultGuests = [
  { id: 1, name: "Sophia Laurent", group: "Familia" },
  { id: 2, name: "Marcus Wentworth", group: "Amigos" },
  { id: 3, name: "Elena Aristone", group: "Trabajo" },
  { id: 4, name: "Thomas Carlyle", group: "Familia" },
  { id: 5, name: "Lucia Bianchi", group: "VIP" },
  { id: 6, name: "Nora Castillo", group: "Amigos" },
  { id: 7, name: "Adrian Molina", group: "Familia" },
  { id: 8, name: "Camila Robles", group: "Amigos" },
  { id: 9, name: "Diego Herrera", group: "Trabajo" },
  { id: 10, name: "Valeria Santos", group: "VIP" }
];

const defaultTables = [
  { id: 1, name: "Mesa 01 Imperial", x: 13, y: 24, seats: emptySeats() },
  { id: 2, name: "Mesa 02 Heritage", x: 66, y: 24, seats: emptySeats() },
  { id: 3, name: "Mesa 03 Terraza", x: 39, y: 58, seats: emptySeats() }
];

let guests = JSON.parse(localStorage.getItem(guestsStorageKey) || JSON.stringify(defaultGuests));
let tables = normalizeTables(JSON.parse(localStorage.getItem(seatingStorageKey) || JSON.stringify(defaultTables)));
let selectedGuestId = null;

const guestList = document.querySelector("[data-guest-list]");
const tablesArea = document.querySelector("[data-tables-area]");
const guestSearch = document.querySelector("[data-guest-search]");
const assignedCount = document.querySelector("[data-assigned-count]");
const totalCount = document.querySelector("[data-total-count]");
const meterBar = document.querySelector("[data-meter-bar]");
const guestImport = document.querySelector("[data-guest-import]");
const importGuestsButton = document.querySelector("[data-import-guests]");
const clearGuestsButton = document.querySelector("[data-clear-guests]");
const importStatus = document.querySelector("[data-guest-import-status]");

function emptySeats() {
  return Array.from({ length: seatsPerTable }, () => null);
}

function normalizeTables(sourceTables) {
  return sourceTables.map((table) => {
    const seats = Array.isArray(table.seats) ? table.seats.slice(0, seatsPerTable) : [];
    while (seats.length < seatsPerTable) seats.push(null);
    return { ...table, seats };
  });
}

function saveGuests() {
  localStorage.setItem(guestsStorageKey, JSON.stringify(guests));
}

function savePlan() {
  tables = normalizeTables(tables);
  localStorage.setItem(seatingStorageKey, JSON.stringify(tables));
}

function assignedIds() {
  return tables.flatMap((table) => table.seats).filter(Boolean);
}

function initials(name) {
  return name.split(" ").filter(Boolean).map((part) => part[0]).join("").slice(0, 2).toUpperCase();
}

function guestById(id) {
  return guests.find((guest) => guest.id === id);
}

function nextGuestId() {
  return guests.length ? Math.max(...guests.map((guest) => guest.id)) + 1 : 1;
}

function parseGuestLine(line, id) {
  const [namePart, groupPart] = line.split(/[-,|]/).map((part) => part.trim());
  return { id, name: namePart, group: groupPart || "Invitado del cliente" };
}

function renderGuests(filter = "") {
  const assigned = assignedIds();
  const visibleGuests = guests.filter((guest) => guest.name.toLowerCase().includes(filter.toLowerCase()) || guest.group.toLowerCase().includes(filter.toLowerCase()));

  guestList.innerHTML = visibleGuests.length ? visibleGuests.map((guest) => `
    <article class="guest-card ${assigned.includes(guest.id) ? "assigned" : ""} ${selectedGuestId === guest.id ? "selected" : ""}" draggable="true" data-guest-id="${guest.id}">
      <div class="guest-avatar">${initials(guest.name)}</div>
      <div><strong>${guest.name}</strong><span>${guest.group}</span></div>
    </article>
  `).join("") : '<p class="empty-guests">No hay invitados con ese nombre.</p>';

  assignedCount.textContent = String(assigned.length);
  totalCount.textContent = String(guests.length);
  meterBar.style.width = `${guests.length ? Math.round((assigned.length / guests.length) * 100) : 0}%`;
}

function seatStyle(index) {
  const angle = -90 + (360 / seatsPerTable) * index;
  return `--seat-angle:${angle}deg`;
}

function renderTables() {
  tables = normalizeTables(tables);
  tablesArea.innerHTML = tables.map((table) => `
    <article class="table-card" data-table="${table.id}" style="left:${table.x}%; top:${table.y}%">
      <input value="${table.name}" data-table-name="${table.id}" aria-label="Nombre de mesa" />
      <span class="table-capacity">10 lugares</span>
      ${table.seats.map((seat, index) => {
        const guest = seat ? guestById(seat) : null;
        return `<button class="seat ${guest ? "filled" : ""}" type="button" style="${seatStyle(index)}" data-table-id="${table.id}" data-seat-index="${index}" title="${guest ? guest.name : "Asiento vacio"}"><span class="seat-label">${guest ? initials(guest.name) : index + 1}</span></button>`;
      }).join("")}
    </article>
  `).join("");
}

function assignGuestToSeat(guestId, tableId, seatIndex) {
  tables = tables.map((table) => ({
    ...table,
    seats: table.seats.map((current) => current === guestId ? null : current)
  }));
  const table = tables.find((item) => item.id === tableId);
  table.seats[seatIndex] = guestId;
  selectedGuestId = null;
  savePlan();
  renderTables();
  renderGuests(guestSearch.value);
}

function clearSeat(tableId, seatIndex) {
  const table = tables.find((item) => item.id === tableId);
  table.seats[seatIndex] = null;
  savePlan();
  renderTables();
  renderGuests(guestSearch.value);
}

function importGuestList() {
  const lines = guestImport.value.split(/\r?\n/).map((line) => line.trim()).filter(Boolean);
  if (!lines.length) {
    importStatus.textContent = "Pega al menos un nombre para cargar la lista.";
    return;
  }

  let id = nextGuestId();
  const existingNames = new Set(guests.map((guest) => guest.name.toLowerCase()));
  const imported = lines
    .map((line) => parseGuestLine(line, id++))
    .filter((guest) => guest.name && !existingNames.has(guest.name.toLowerCase()));

  guests = [...guests, ...imported];
  saveGuests();
  guestImport.value = "";
  importStatus.textContent = `${imported.length} invitados cargados. Selecciona uno y asignalo a una mesa.`;
  renderGuests(guestSearch.value);
}

function clearGuestList() {
  if (!confirm("Esto limpiara invitados y asignaciones de mesas. Deseas continuar?")) return;
  guests = [];
  tables = tables.map((table) => ({ ...table, seats: emptySeats() }));
  selectedGuestId = null;
  saveGuests();
  savePlan();
  importStatus.textContent = "Lista limpia. Pega una nueva lista del cliente para comenzar.";
  renderTables();
  renderGuests();
}

guestList.addEventListener("click", (event) => {
  const card = event.target.closest("[data-guest-id]");
  if (!card) return;
  selectedGuestId = Number(card.dataset.guestId);
  renderGuests(guestSearch.value);
});

guestList.addEventListener("dragstart", (event) => {
  const card = event.target.closest("[data-guest-id]");
  if (!card) return;
  event.dataTransfer.setData("text/plain", card.dataset.guestId);
});

tablesArea.addEventListener("dragover", (event) => {
  if (event.target.closest(".seat")) event.preventDefault();
});

tablesArea.addEventListener("drop", (event) => {
  const seat = event.target.closest(".seat");
  if (!seat) return;
  const guestId = Number(event.dataTransfer.getData("text/plain"));
  if (!guestId) return;
  assignGuestToSeat(guestId, Number(seat.dataset.tableId), Number(seat.dataset.seatIndex));
});

tablesArea.addEventListener("click", (event) => {
  const seat = event.target.closest(".seat");
  if (!seat) return;
  const tableId = Number(seat.dataset.tableId);
  const seatIndex = Number(seat.dataset.seatIndex);
  const table = tables.find((item) => item.id === tableId);
  if (selectedGuestId) {
    assignGuestToSeat(selectedGuestId, tableId, seatIndex);
    return;
  }
  if (table.seats[seatIndex]) {
    clearSeat(tableId, seatIndex);
  }
});

tablesArea.addEventListener("input", (event) => {
  const input = event.target.closest("[data-table-name]");
  if (!input) return;
  tables.find((table) => table.id === Number(input.dataset.tableName)).name = input.value;
  savePlan();
});

guestSearch.addEventListener("input", (event) => renderGuests(event.target.value));
importGuestsButton.addEventListener("click", importGuestList);
clearGuestsButton.addEventListener("click", clearGuestList);

document.querySelector("[data-add-table]").addEventListener("click", () => {
  const id = tables.length ? Math.max(...tables.map((table) => table.id)) + 1 : 1;
  tables.push({ id, name: `Mesa ${String(id).padStart(2, "0")}`, x: 14 + ((id * 17) % 58), y: 18 + ((id * 13) % 46), seats: emptySeats() });
  savePlan();
  renderTables();
});

document.querySelectorAll("[data-save-layout], [data-save-layout-bottom]").forEach((button) => {
  button.addEventListener("click", () => {
    saveGuests();
    savePlan();
    button.textContent = "Layout guardado";
    setTimeout(() => { button.textContent = "Guardar layout"; }, 1400);
  });
});

savePlan();
renderTables();
renderGuests();
