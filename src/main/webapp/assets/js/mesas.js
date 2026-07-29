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

function renderTables() {
    tablesArea.innerHTML = tables.map((table) => {
        const currentGuests = table.guests || [];

        const guestsListHtml = currentGuests.length
            ? currentGuests.map((g) => `
          <li class="table-guest-item">
            <span>
              <strong>${g.name}</strong> <small style="opacity:0.7">(${g.email})</small>
              ${g.enviado ? '<small style="color: var(--green); font-weight: 700; margin-left:6px;">&#10003; Invitación enviada</small>' : ""}
            </span>
            <button type="button" class="remove-guest" onclick="removeGuestFromTable(${g.id})" title="Eliminar invitado">&times;</button>
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


async function deleteTable(tableId) {
    const table = tables.find((t) => t.id === tableId);
    if (!table) return;

    if (table.guests && table.guests.length > 0) {
        if (!confirm(`La mesa "${table.name}" tiene invitados asignados. ¿Realmente deseas eliminarla?`)) {
            return;
        }
    }

    const resultado = await apiPost({ accion: "eliminarMesa", idMesa: tableId });
    if (!resultado) return;
    if (!resultado.success) {
        alert(resultado.error || "No se pudo eliminar la mesa.");
        return;
    }
    await cargarMesas();
}

if (formLateral) {
    formLateral.addEventListener("submit", async (event) => {
        event.preventDefault();

        const targetTableId = Number(lateralMesaSelect.value);
        const table = tables.find((t) => t.id === targetTableId);
        if (!table) return;

        if (table.guests && table.guests.length >= maxGuestsPerTable) {
            alert(`La mesa "${table.name}" ya alcanzó el límite máximo de 10 invitados.`);
            return;
        }

        const resultado = await apiPost({
            accion: "agregarInvitado",
            idMesa: targetTableId,
            nombre: lateralNombre.value.trim(),
            correo: lateralCorreo.value.trim()
        });

        if (!resultado) return;
        if (!resultado.success) {
            alert(resultado.error || "No se pudo registrar al invitado.");
            return;
        }

        formLateral.reset();
        await cargarMesas();
    });
}

async function removeGuestFromTable(idInvitado) {
    const resultado = await apiPost({ accion: "eliminarInvitado", idInvitado });
    if (!resultado) return;
    if (!resultado.success) {
        alert(resultado.error || "No se pudo eliminar al invitado.");
        return;
    }
    await cargarMesas();
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
    btnConfirmarEnvio.addEventListener("click", async () => {
        const total = getTotalAssignedCount();
        if (total === 0) {
            alert("No hay invitados registrados para enviar invitaciones.");
            return;
        }

        const textoOriginal = btnConfirmarEnvio.textContent;
        btnConfirmarEnvio.textContent = "Enviando...";
        btnConfirmarEnvio.disabled = true;

        try {
            const respuesta = await fetch("enviar-invitaciones", {
                method: "POST",
                credentials: "same-origin"
            });

            if (respuesta.status === 401) {
                window.location.href = "login.jsp";
                return;
            }

            const resultado = await respuesta.json();

            if (!resultado.success) {
                alert(resultado.error || "No se pudieron enviar las invitaciones.");
                return;
            }

            if (resultado.enviados === 0 && resultado.mensaje) {
                alert(resultado.mensaje);
            } else {
                let mensaje = `¡Éxito! Se enviaron ${resultado.enviados} invitaciones digitales.`;
                if (resultado.fallidos > 0) {
                    mensaje += ` (${resultado.fallidos} no pudieron enviarse, revisa sus correos.)`;
                }
                if (resultado.modoSimulado) {
                    mensaje += "\n\nNota: el correo aún no está configurado (faltan las credenciales MAIL_* en el .env), así que este envío fue simulado y solo quedó registrado en el servidor.";
                }
                alert(mensaje);
            }

            cerrarModalEnvio();
            await cargarMesas();
        } catch (e) {
            console.error("Error enviando invitaciones:", e);
            alert("No se pudo conectar con el servidor para enviar las invitaciones.");
        } finally {
            btnConfirmarEnvio.textContent = textoOriginal;
            btnConfirmarEnvio.disabled = false;
        }
    });
}

tablesArea.addEventListener("input", (event) => {
    const input = event.target.closest("[data-table-name]");
    if (!input) return;
    const table = tables.find((t) => t.id === Number(input.dataset.tableName));
    if (table) {
        table.name = input.value;
        updateMesaSelect();
    }
});

tablesArea.addEventListener("blur", async (event) => {
    const input = event.target.closest("[data-table-name]");
    if (!input) return;
    const idMesa = Number(input.dataset.tableName);
    const nuevoNombre = input.value.trim();
    if (!nuevoNombre) return;

    const resultado = await apiPost({ accion: "renombrarMesa", idMesa, nombre: nuevoNombre });
    if (!resultado) return;
    if (!resultado.success) {
        alert(resultado.error || "No se pudo renombrar la mesa.");
        await cargarMesas();
    }
}, true);


document.querySelector("[data-add-table]").addEventListener("click", async () => {
    const siguiente = tables.length ? Math.max(...tables.map((t) => t.id)) + 1 : 1;
    const nombreSugerido = `Mesa ${String(siguiente).padStart(2, "0")}`;

    const resultado = await apiPost({ accion: "crearMesa", nombre: nombreSugerido });
    if (!resultado) return;
    if (!resultado.success) {
        alert(resultado.error || "No se pudo crear la mesa.");
        return;
    }
    await cargarMesas();
});

document.querySelectorAll("[data-save-layout]").forEach((button) => {
    button.addEventListener("click", async () => {
        // Cada acción ya se guarda de inmediato en el servidor; este botón
        // refresca desde el servidor para confirmar que todo quedó sincronizado.
        await cargarMesas();
        button.textContent = "Layout guardado";
        setTimeout(() => { button.textContent = "Guardar layout"; }, 1400);
    });
});

cargarMesas();