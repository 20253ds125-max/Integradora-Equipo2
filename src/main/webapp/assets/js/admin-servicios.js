const adminServicesKey = "gedsAdminServices";

// Mapeo directo con las columnas de tu BD (PUBLICACION_SERVICIO_EXTRA)
const defaultServices = [
    {
        ID_SE: 1,
        NOMBRE_SERVICIO: "Catering Gourmet Premium",
        DESCRIPCION: "Banquetes de 4 tiempos con menú internacional, personal de servicio y loza fina.",
        PRECIO: 1250.00,
        URL_FOTO: "https://images.unsplash.com/photo-1555244162-803834f70033?auto=format&fit=crop&w=200&q=80",
        ID_USUARIO: 302,
        status: "pending"
    },
    {
        ID_SE: 2,
        NOMBRE_SERVICIO: "Audio Profesional e Iluminación",
        DESCRIPCION: "Sistema de sonido lineal, cabina de DJ iluminada y 16 lámparas robóticas LED.",
        PRECIO: 8900.00,
        URL_FOTO: "https://images.unsplash.com/photo-1516450360452-9312f5e86fc7?auto=format&fit=crop&w=200&q=80",
        ID_USUARIO: 105,
        status: "validated"
    },
    {
        ID_SE: 3,
        NOMBRE_SERVICIO: "Decoración y Arreglos Florales",
        DESCRIPCION: "Centros de mesa con flores de estación, arco principal y decoración del camino.",
        PRECIO: 4500.00,
        URL_FOTO: "https://images.unsplash.com/photo-1526047932273-341f2a7631f9?auto=format&fit=crop&w=200&q=80",
        ID_USUARIO: 411,
        status: "rejected"
    }
];

// Cargar de LocalStorage o usar default
let services = JSON.parse(localStorage.getItem(adminServicesKey));
if (!services || services.length === 0) {
    services = defaultServices;
    localStorage.setItem(adminServicesKey, JSON.stringify(services));
}

// Referencias al DOM
const tbody = document.querySelector('[data-admin-rows]');
const statPending = document.querySelector('[data-stat-pending]');
const statValid = document.querySelector('[data-stat-valid]');
const statTotal = document.querySelector('[data-stat-total]');

// Formateador de moneda para el precio
const formatCurrency = (amount) => {
    return new Intl.NumberFormat('es-MX', {
        style: 'currency',
        currency: 'MXN'
    }).format(amount);
};

// Traductor y asignador de clases de estado
const getStatusInfo = (status) => {
    switch(status) {
        case 'pending': return { label: 'Pendiente', className: 'pending' };
        case 'validated': return { label: 'Validado', className: 'validated' };
        case 'rejected': return { label: 'Rechazado', className: 'rejected' };
        default: return { label: 'Desconocido', className: '' };
    }
};

// Renderizado principal de la tabla
const renderTable = () => {
    tbody.innerHTML = '';

    let pendingCount = 0;
    let validCount = 0;

    services.forEach(service => {
        if (service.status === 'pending') pendingCount++;
        if (service.status === 'validated') validCount++;

        const statusInfo = getStatusInfo(service.status);
        const tr = document.createElement('tr');

        tr.innerHTML = `
            <td>
                <div class="venue-cell">
                    <img src="${service.URL_FOTO}" alt="${service.NOMBRE_SERVICIO}">
                    <div>
                        <div style="font-family: 'Montserrat', sans-serif; font-weight: 600; color: var(--ink); font-size: 14px; margin-bottom: 3px; letter-spacing: -0.2px;">
                            ${service.NOMBRE_SERVICIO}
                        </div>
                        <div style="font-family: 'Montserrat', sans-serif; font-weight: 500; color: var(--muted); font-size: 13px;">
                            ID: #SE-${service.ID_SE}
                        </div>
                    </div>
                </div>
            </td>
            <td style="font-family: 'Montserrat', sans-serif; font-weight: 600; color: var(--ink); font-size: 14px; width: 140px; min-width: 140px;">
                ${formatCurrency(service.PRECIO)}
            </td>
            <td style="width: 160px; min-width: 160px;">
                <span class="status ${statusInfo.className}" style="font-family: 'Montserrat', sans-serif;">${statusInfo.label}</span>
            </td>
           
            <td style="width: 200px; min-width: 200px; text-align: right;">
                <div class="action-row" style="justify-content: flex-end; display: flex; gap: 8px;">
                    <button onclick="updateStatus(${service.ID_SE}, 'validated')" style="font-family: 'Montserrat', sans-serif;">Aceptar</button>
                    <button class="delete" onclick="updateStatus(${service.ID_SE}, 'rejected')" style="font-family: 'Montserrat', sans-serif;">Rechazar</button>
                </div>
            </td>
        `;
        tbody.appendChild(tr);
    });

    // Actualizar contadores si las referencias existen en el DOM
    if(statPending) statPending.textContent = pendingCount;
    if(statValid) statValid.textContent = validCount;
    if(statTotal) statTotal.textContent = services.length;
};

// Función global para actualizar el estado desde los botones
window.updateStatus = (id, newStatus) => {
    const serviceIndex = services.findIndex(s => s.ID_SE === id);
    if (serviceIndex !== -1) {
        services[serviceIndex].status = newStatus;
        // Guardar el cambio
        localStorage.setItem(adminServicesKey, JSON.stringify(services));
        // Volver a dibujar la tabla y contadores
        renderTable();
    }
};

// Inicializar cuando el DOM esté listo
document.addEventListener('DOMContentLoaded', renderTable);