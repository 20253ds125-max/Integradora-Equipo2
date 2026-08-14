const servicios = [
    {
        id: 1,
        nombre: "DJ Profesional",
        categoria: "musica",
        localidad: "cdmx",
        localidadLabel: "Ciudad de México",
        descripcion: "Música personalizada durante todo tu evento.",
        precio: 15000,
        imagen: "https://picsum.photos/600/400?random=1"
    },
    {
        id: 2,
        nombre: "Mariachi",
        categoria: "musica",
        localidad: "san-miguel",
        localidadLabel: "San Miguel de Allende",
        descripcion: "Show tradicional mexicano para bodas y eventos.",
        precio: 12000,
        imagen: "https://picsum.photos/600/400?random=2"
    },
    {
        id: 3,
        nombre: "Banquete Premium",
        categoria: "catering",
        localidad: "merida",
        localidadLabel: "Mérida",
        descripcion: "Menú gourmet para eventos exclusivos.",
        precio: 22000,
        imagen: "https://picsum.photos/600/400?random=3"
    },
    {
        id: 4,
        nombre: "Fotografía Profesional",
        categoria: "foto",
        localidad: "tulum",
        localidadLabel: "Tulum",
        descripcion: "Cobertura completa de tu evento.",
        precio: 18000,
        imagen: "https://picsum.photos/600/400?random=4"
    },
    {
        id: 5,
        nombre: "Arreglos Florales",
        categoria: "decoracion",
        localidad: "cabos",
        localidadLabel: "Los Cabos",
        descripcion: "Centros de mesa y decoración personalizada.",
        precio: 10000,
        imagen: "https://picsum.photos/600/400?random=5"
    },
    {
        id: 6,
        nombre: "Iluminación Ambiental",
        categoria: "decoracion",
        localidad: "cdmx",
        localidadLabel: "Ciudad de México",
        descripcion: "Ambientes elegantes para eventos nocturnos.",
        precio: 16000,
        imagen: "https://picsum.photos/600/400?random=6"
    },
    {
        id: 7,
        nombre: "Pantalla LED",
        categoria: "video",
        localidad: "san-miguel",
        localidadLabel: "San Miguel de Allende",
        descripcion: "Visuales y proyección para montajes premium.",
        precio: 14000,
        imagen: "https://picsum.photos/600/400?random=7"
    },
    {
        id: 8,
        nombre: "Trío en Vivo",
        categoria: "musica-en-vivo",
        localidad: "tulum",
        localidadLabel: "Tulum",
        descripcion: "Música en vivo para ceremonias y cenas privadas.",
        precio: 17500,
        imagen: "https://picsum.photos/600/400?random=8"
    }
];

const favoriteStorageKey = "gedsFavorites";
const cartStorageKey = "eventOnlineEvento";

const contenedor = document.getElementById("contenedorServicios");
const buscador = document.getElementById("buscador");
const filtros = document.querySelectorAll(".filtro");

const estado = {
    categoria: "todos",
    localidad: "todas",
    query: ""
};

function normalizarTexto(texto) {
    return String(texto || "")
        .toLowerCase()
        .normalize("NFD")
        .replace(/[\u0300-\u036f]/g, "");
}

function renderizarServicios(lista) {
    contenedor.innerHTML = "";

    if (!lista.length) {
        contenedor.innerHTML = `
            <p class="empty-state">No encontramos servicios con esos filtros. Prueba otra localidad o categoría.</p>
        `;
        return;
    }

    lista.forEach((servicio) => {
        const favoritoActivo = isFavoritoServicio(servicio.id);
        contenedor.innerHTML += `
            <article class="card">
                <div class="imagen-container">
                    <img src="${servicio.imagen}" alt="${servicio.nombre}">
                    <button class="btn-favorito ${favoritoActivo ? "activo" : ""}" type="button" onclick="toggleFavorito(${servicio.id}, this)">
                        ${favoritoActivo ? "♥" : "♡"}
                    </button>
                </div>

                <div class="card-body">
                    <span class="badge">${servicio.categoria.replace("-", " ")}</span>
                    <h3>${servicio.nombre}</h3>
                    <p class="service-location">${servicio.localidadLabel}</p>
                    <p>${servicio.descripcion}</p>

                    <div class="card-footer">
                        <strong>$${servicio.precio.toLocaleString()}</strong>

                        <div class="acciones-card">
                            <button class="btn-agregar" type="button" onclick="agregarEvento(${servicio.id})">
                                Añadir al carrito
                            </button>
                        </div>
                    </div>
                </div>
            </article>
        `;
    });
}

function aplicarFiltros() {
    const texto = normalizarTexto(estado.query);

    const filtrados = servicios.filter((servicio) => {
        const coincideTexto = !texto
            || normalizarTexto(`${servicio.nombre} ${servicio.descripcion} ${servicio.localidadLabel}`).includes(texto);

        const coincideCategoria =
            estado.categoria === "todos" || servicio.categoria === estado.categoria;

        const coincideLocalidad =
            estado.localidad === "todas" || servicio.localidad === estado.localidad;

        return coincideTexto && coincideCategoria && coincideLocalidad;
    });

    renderizarServicios(filtrados);
}

function toggleFavorito(id, boton) {
    const servicio = servicios.find((item) => item.id === id);
    if (!servicio) return;

    const favorites = getFavorites();
    const exists = favorites.some((item) => item.id === String(id) && item.kind === "service");
    const next = exists
        ? favorites.filter((item) => !(item.id === String(id) && item.kind === "service"))
        : [serviceForStorage(servicio), ...favorites];

    saveFavorites(next);

    if (boton) {
        boton.classList.toggle("activo", !exists);
        boton.textContent = exists ? "♡" : "♥";
    }
}

function agregarEvento(id) {
    const servicio = servicios.find((item) => item.id === id);
    if (!servicio) return;

    if (window.GEDS_CART) {
        window.GEDS_CART.addServiceToCart(servicio);
        return;
    }

    const fallbackCart = readCart();
    const nextService = normalizeServiceForCart(servicio);
    const exists = fallbackCart.servicios.some((item) => String(item.id) === String(nextService.id));
    const nextCart = {
        ...fallbackCart,
        servicios: exists ? fallbackCart.servicios : [nextService, ...fallbackCart.servicios]
    };
    saveCart(nextCart);
    window.location.href = "mi-carrito-de-compra.html";
}

function normalizeServiceForCart(servicio) {
    return {
        id: String(servicio.id),
        name: servicio.nombre,
        descripcion: servicio.descripcion,
        price: Number(servicio.precio || 0),
        category: servicio.categoria,
        localidad: servicio.localidad,
        localidadLabel: servicio.localidadLabel,
        image: servicio.imagen
    };
}

function serviceForStorage(servicio) {
    return {
        id: String(servicio.id),
        kind: "service",
        name: servicio.nombre,
        location: servicio.localidadLabel,
        price: `$${Number(servicio.precio || 0).toLocaleString("en-US")}`,
        unit: "/servicio",
        rating: "Nuevo",
        image: servicio.imagen,
        tag: "Servicio extra"
    };
}

function getFavorites() {
    try {
        return JSON.parse(localStorage.getItem(favoriteStorageKey) || "[]");
    } catch (error) {
        return [];
    }
}

function saveFavorites(favorites) {
    localStorage.setItem(favoriteStorageKey, JSON.stringify(favorites));
}

function isFavoritoServicio(id) {
    return getFavorites().some((item) => item.id === String(id) && item.kind === "service");
}

function readCart() {
    try {
        return JSON.parse(localStorage.getItem(cartStorageKey) || "{\"recinto\":null,\"servicios\":[],\"mesas\":[]}");
    } catch (error) {
        return { recinto: null, servicios: [], mesas: [] };
    }
}

function saveCart(cart) {
    localStorage.setItem(cartStorageKey, JSON.stringify(cart));
    return cart;
}

function actualizarPanel() {
    const evento = obtenerEvento();
    const lista = document.getElementById("listaServicios");
    const totalElemento = document.getElementById("totalServicio");

    if (!lista || !totalElemento) return;

    lista.innerHTML = "";

    if (evento.servicios.length === 0) {
        lista.innerHTML = `
            <div class="carrito-vacio">
                <p>Aún no has agregado servicios.</p>
            </div>
        `;
    }

    let total = 0;

    evento.servicios.forEach((servicio) => {
        total += Number(servicio.precio || 0);

        lista.innerHTML += `
            <div class="item-carrito">
                <div>
                    <h4>${servicio.nombre}</h4>
                    <span>$${Number(servicio.precio || 0).toLocaleString()}</span>
                </div>

                <button class="btn-eliminar" type="button" onclick="eliminarServicio(${servicio.id})">
                    ✕
                </button>
            </div>
        `;
    });

    totalElemento.textContent = "$" + total.toLocaleString();
}

function eliminarServicio(id) {
    const evento = obtenerEvento();
    const servicio = evento.servicios.find((item) => item.id === id);

    evento.servicios = evento.servicios.filter((item) => item.id !== id);
    guardarEvento(evento);
    actualizarPanel();

    if (servicio) {
        mostrarToast("Servicio eliminado", servicio.nombre);
    }
}

function vaciarEvento() {
    const confirmar = confirm("¿Deseas eliminar todos los servicios del evento?");
    if (!confirmar) return;

    const evento = obtenerEvento();
    evento.servicios = [];
    guardarEvento(evento);
    actualizarPanel();

    mostrarToast("Evento vaciado", "Todos los servicios fueron eliminados");
}

function mostrarToast(titulo, mensaje, precio = "") {
    const toast = document.getElementById("toast");
    if (!toast) return;

    toast.innerHTML = `
        <div class="toast-header">
            <div class="toast-icon">✓</div>
            <div class="toast-title">${titulo}</div>
        </div>

        <div class="toast-message">${mensaje}</div>
        ${precio ? `<div class="toast-price">${precio}</div>` : ""}
        <div class="toast-progress"></div>
    `;

    toast.classList.add("show");
    clearTimeout(toast.timeout);
    toast.timeout = setTimeout(() => {
        toast.classList.remove("show");
    }, 3000);
}

filtros.forEach((boton) => {
    boton.addEventListener("click", () => {
        if (boton.dataset.categoria) {
            estado.categoria = boton.dataset.categoria;
            document.querySelectorAll("[data-categoria]").forEach((item) => item.classList.remove("activo"));
            boton.classList.add("activo");
        }

        if (boton.dataset.localidad) {
            estado.localidad = boton.dataset.localidad;
            document.querySelectorAll("[data-localidad]").forEach((item) => item.classList.remove("activo"));
            boton.classList.add("activo");
        }

        aplicarFiltros();
    });
});

buscador?.addEventListener("input", (event) => {
    estado.query = event.target.value;
    aplicarFiltros();
});

renderizarServicios(servicios);
actualizarPanel();
