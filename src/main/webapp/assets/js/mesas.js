const seatingStorageKey = "gedsSeatingPlan";
const maxGuestsPerTable = 10;

const defaultTables = [
    { id: 1, name: "Mesa 01", guests: [] },
    { id: 2, name: "Mesa 02", guests: [] },
    { id: 3, name: "Mesa 03", guests: [] }
];

let tables = JSON.parse(localStorage.getItem(seatingStorageKey) || JSON.stringify(defaultTables));

const tablesArea = document.querySelector("[data-tables-area]");
const formLateral = document.getElementById("form-lateral-invitado");
const lateralNombre = document.getElementById("lateral-nombre");
const lateralCorreo = document.getElementById("lateral-correo");
const lateralMesaSelect = document.getElementById("lateral-mesa-select");
const totalGlobalCount = document.getElementById("total-global-count");

const modalEnvio = document.getElementById("modal-envio");
const btnAbrirEnvio = document.getElementById("btn-abrir-envio");
const btnCerrarEnvio = document.getElementById("btn-cerrar-envio");
const btnCancelarEnvio = document.getElementById("btn-cancelar-envio");
const btnConfirmarEnvio = document.getElementById("btn-confirmar-envio");
const modalTotalNotificar = document.getElementById("modal-total-notificar");

function savePlan() {
    localStorage.setItem(seatingStorageKey, JSON.stringify(tables));
}

function getTotalAssignedCount() {
    return tables.reduce((acc, table) => acc + (table.guests ? table.guests.length : 0), 0);
}

function updateMesaSelect() {
    if (!lateralMesaSelect) return;
    lateralMesaSelect.innerHTML = tables.map(table => `
        <option value="${table.id}">${table.name} (${table.guests ? table.guests.length : 0}/${maxGuestsPerTable})</option>
    `).join("");
}

function renderTables() {
    tablesArea.innerHTML = tables.map((table) => {
        const currentGuests = table.guests || [];

        const guestsListHtml = currentGuests.length
            ? currentGuests.map((g, index) => `
          <li class="table-guest-item">
            <span><strong>${g.name}</strong> <small style="opacity:0.7">(${g.email})</small></span>
            <button type="button" class="remove-guest" onclick="removeGuestFromTable(${table.id}, ${index})" title="Eliminar invitado">&times;</button>
          </li>
        `).join("")
            : `<li style="color: var(--muted); font-size: 0.75rem; text-align: center; padding: 12px 0;">Sin invitados aún</li>`;

        return `
      <article class="table-card" data-table="${table.id}">
      
        <div class="table-header-group">
          <div style="display: flex; justify-content: space-between; align-items: center; width: 100%; gap: 8px;">
            <input value="${table.name}" data-table-name="${table.id}" aria-label="Nombre de mesa" style="flex: 1; text-align: left;" />
            <button type="button" class="btn-delete-table" onclick="deleteTable(${table.id})" title="Eliminar esta mesa">&times;</button>
          </div>
          <span class="table-capacity">${currentGuests.length} / ${maxGuestsPerTable} Asignados</span>
        </div>
        
        <ul class="table-guest-list">
          ${guestsListHtml}
        </ul>
      </article>
    `;
    }).join("");

    if (totalGlobalCount) {
        totalGlobalCount.textContent = String(getTotalAssignedCount());
    }
    updateMesaSelect();
}

function deleteTable(tableId) {
    const table = tables.find((t) => t.id === tableId);
    if (!table) return;

    if (table.guests && table.guests.length > 0) {
        if (!confirm(`La mesa "${table.name}" tiene invitados asignados. ¿Realmente deseas eliminarla?`)) {
            return;
        }
    }

    tables = tables.filter((t) => t.id !== tableId);
    savePlan();
    renderTables();
}

if (formLateral) {
    formLateral.addEventListener("submit", (event) => {
        event.preventDefault();

        const targetTableId = Number(lateralMesaSelect.value);
        const table = tables.find((t) => t.id === targetTableId);
        if (!table) return;

        if (!table.guests) table.guests = [];

        if (table.guests.length >= maxGuestsPerTable) {
            alert(`La mesa "${table.name}" ya alcanzó el límite máximo de 10 invitados.`);
            return;
        }

        table.guests.push({
            name: lateralNombre.value.trim(),
            email: lateralCorreo.value.trim()
        });

        savePlan();
        renderTables();
        formLateral.reset();
    });
}

function removeGuestFromTable(tableId, guestIndex) {
    const table = tables.find((t) => t.id === tableId);
    if (table && table.guests) {
        table.guests.splice(guestIndex, 1);
        savePlan();
        renderTables();
    }
}

if (btnAbrirEnvio) {
    btnAbrirEnvio.addEventListener("click", () => {
        const total = getTotalAssignedCount();
        if (modalTotalNotificar) modalTotalNotificar.textContent = String(total);
        modalEnvio.style.display = "flex";
    });
}

function cerrarModalEnvio() {
    modalEnvio.style.display = "none";
}

if (btnCerrarEnvio) btnCerrarEnvio.addEventListener("click", cerrarModalEnvio);
if (btnCancelarEnvio) btnCancelarEnvio.addEventListener("click", cerrarModalEnvio);

if (btnConfirmarEnvio) {
    btnConfirmarEnvio.addEventListener("click", () => {
        const total = getTotalAssignedCount();
        if (total === 0) {
            alert("No hay invitados registrados para enviar invitaciones.");
            return;
        }
        alert(`¡Éxito! Se han enviado ${total} invitaciones digitales por correo con sus pases de mesa.`);
        cerrarModalEnvio();
    });
}

tablesArea.addEventListener("input", (event) => {
    const input = event.target.closest("[data-table-name]");
    if (!input) return;
    const table = tables.find((t) => t.id === Number(input.dataset.tableName));
    if (table) {
        table.name = input.value;
        savePlan();
        updateMesaSelect();
    }
});

document.querySelector("[data-add-table]").addEventListener("click", () => {
    const id = tables.length ? Math.max(...tables.map((t) => t.id)) + 1 : 1;
    tables.push({
        id,
        name: `Mesa ${String(id).padStart(2, "0")}`,
        guests: []
    });
    savePlan();
    renderTables();
});

document.querySelectorAll("[data-save-layout]").forEach((button) => {
    button.addEventListener("click", () => {
        savePlan();
        button.textContent = "Layout guardado";
        setTimeout(() => { button.textContent = "Guardar layout"; }, 1400);
    });
});

renderTables();