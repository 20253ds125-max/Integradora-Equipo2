const EVENTO_KEY = "eventOnlineEvento";
const SELECTED_VENUE_KEY = "gedsSelectedVenue";
const BOOKING_GUESTS_KEY = "gedsBookingGuests";

const CART_FALLBACK = {
    recinto: null,
    servicios: [],
    mesas: [],
    subtotal: 0,
    serviceFee: 0,
    damageDeposit: 0,
    total: 0
};

function parseMoney(value) {
    const numeric = Number(String(value || "").replace(/[^0-9]/g, ""));
    return Number.isFinite(numeric) ? numeric : 0;
}

function normalizeVenue(venue) {
    if (!venue) return null;

    return {
        id: venue.id || "recinto",
        name: venue.name || "Recinto seleccionado",
        location: venue.location || "Ubicación pendiente",
        price: venue.price || "$0",
        unit: venue.unit || "/evento",
        rating: venue.rating || "0.0",
        image: venue.image || "",
        tag: venue.tag || "Seleccionado",
        capacity: venue.capacity || venue.guests || null
    };
}

function normalizeService(service) {
    if (!service) return null;

    return {
        id: service.id || service.nombre || service.name,
        name: service.nombre || service.name || "Servicio extra",
        descripcion: service.descripcion || service.description || "",
        price: parseMoney(service.precio || service.price || 0),
        category: service.categoria || service.category || "Servicio",
        localidad: service.localidad || service.localidadLabel || service.location || "",
        localidadLabel: service.localidadLabel || service.location || "",
        image: service.imagen || service.image || ""
    };
}

function readCart() {
    try {
        const raw = localStorage.getItem(EVENTO_KEY);
        const parsed = raw ? JSON.parse(raw) : {};
        return {
            ...CART_FALLBACK,
            ...parsed,
            recinto: normalizeVenue(parsed.recinto),
            servicios: Array.isArray(parsed.servicios)
                ? parsed.servicios.map(normalizeService).filter(Boolean)
                : []
        };
    } catch (error) {
        return { ...CART_FALLBACK };
    }
}

function recalculateCart(cart) {
    const recintoSubtotal = parseMoney(cart.recinto?.price);
    const serviciosSubtotal = (cart.servicios || []).reduce((sum, item) => sum + parseMoney(item.price), 0);
    const subtotal = recintoSubtotal + serviciosSubtotal;
    const serviceFee = subtotal > 0 ? 150 : 0;
    const damageDeposit = Math.round((subtotal + serviceFee) * 0.3);
    const total = subtotal + serviceFee + damageDeposit;

    return {
        ...cart,
        subtotal,
        serviceFee,
        damageDeposit,
        total,
        recinto: cart.recinto ? normalizeVenue(cart.recinto) : null,
        servicios: (cart.servicios || []).map(normalizeService).filter(Boolean)
    };
}

function saveCart(cart) {
    const normalized = recalculateCart(cart);
    localStorage.setItem(EVENTO_KEY, JSON.stringify(normalized));

    if (normalized.recinto) {
        localStorage.setItem(SELECTED_VENUE_KEY, JSON.stringify(normalized.recinto));
    }

    if (!localStorage.getItem(BOOKING_GUESTS_KEY)) {
        localStorage.setItem(BOOKING_GUESTS_KEY, "25");
    }

    return normalized;
}

function addVenueToCart(venue, options = {}) {
    const cart = readCart();
    const next = saveCart({
        ...cart,
        recinto: normalizeVenue(venue)
    });

    if (options.navigate !== false) {
        window.location.href = options.redirectTo || "mi-carrito-de-compra";
    }

    return next;
}

function addServiceToCart(service, options = {}) {
    const cart = readCart();
    const nextService = normalizeService(service);
    if (!nextService) return cart;

    const exists = cart.servicios.some((item) => item.id === nextService.id);
    const services = exists
        ? cart.servicios
        : [nextService, ...cart.servicios];

    const next = saveCart({
        ...cart,
        servicios: services
    });

    if (options.navigate !== false) {
        window.location.href = options.redirectTo || "mi-carrito-de-compra.html";
    }

    return next;
}

function removeCartService(id) {
    const cart = readCart();
    const next = saveCart({
        ...cart,
        servicios: cart.servicios.filter((service) => String(service.id) !== String(id))
    });
    return next;
}

function removeCartVenue() {
    const cart = readCart();
    const next = saveCart({
        ...cart,
        recinto: null
    });
    return next;
}

function clearCart() {
    const empty = saveCart({ ...CART_FALLBACK });
    return empty;
}

function getCartSummary() {
    return recalculateCart(readCart());
}

window.GEDS_CART = {
    readCart,
    saveCart,
    addVenueToCart,
    addServiceToCart,
    removeCartService,
    removeCartVenue,
    clearCart,
    getCartSummary
};
