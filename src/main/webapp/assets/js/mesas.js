const maxGuestsPerTable = 10;

let tables = [];

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


async function apiGet() {
    const respuesta = await fetch("mesas-api", { credentials: "same-origin" });
    if (respuesta.status === 401) {
        window.location.href = "login.jsp";
        return null;
    }
    return respuesta.json();
}

async function apiPost(parametros) {
    const cuerpo = new URLSearchParams(parametros);
    const respuesta = await fetch("mesas-api", {
        method: "POST",
        credentials: "same-origin",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: cuerpo
    });
    if (respuesta.status === 401) {
        window.location.href = "login.jsp";
        return null;
    }
    return respuesta.json();
}

async function cargarMesas() {
    try {
        const datos = await apiGet();
        if (!datos) return;
        if (!datos.success) {
            alert(datos.error || "No se pudieron cargar las mesas.");
            return;
        }
        tables = (datos.mesas || []).map((m) => ({
            id: m.idMesa,
            name: m.nombre,
            capacidad: m.capacidad,
            guests: (m.invitados || []).map((g) => ({
                id: g.idInvitado,
                name: g.nombre,
                email: g.correo,
                enviado: g.invitacionEnviada
            }))
        }));
        renderTables();
    } catch (e) {
        console.error("Error cargando mesas:", e);
        alert("No se pudo conectar con el servidor para cargar tus mesas.");
    }
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
