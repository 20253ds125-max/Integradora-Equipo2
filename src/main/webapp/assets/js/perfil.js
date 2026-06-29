 (function () {
    const profileKey = "gedsProfile";
    const bookingsKey = "gedsBookings";
    const favoritesKey = "gedsFavorites";
    const selectedVenueKey = "gedsSelectedVenue";
    const cartKey = "eventOnlineEvento";

    const defaultProfile = {
    name: "María Fernanda",
    email: "maria.fernanda@email.com",
    phone: "+52 55 1234 5678",
    city: "Ciudad de México",
    address: "Av. Reforma 123, CDMX"
};

    const fallbackVenues = {
    "villa-laura": {
    id: "villa-laura",
    name: "Jardín Encanto Avándaro",
    location: "Valle de Bravo, Estado de México",
    price: "$850",
    unit: "/evento",
    rating: "4.9",
    image: "https://images.unsplash.com/photo-1519167758481-83f550bb49b3?auto=format&fit=crop&w=1400&q=90",
    tag: "Bosque"
},
    "palais-marbre": {
    id: "palais-marbre",
    name: "Hacienda Los Arcos",
    location: "San Miguel de Allende, Guanajuato",
    price: "$1,200",
    unit: "/evento",
    rating: "5.0",
    image: "https://images.unsplash.com/photo-1519671482749-fd09be7ccebf?auto=format&fit=crop&w=1400&q=90",
    tag: "Hacienda"
},
    "apex-skyline": {
    id: "apex-skyline",
    name: "Terraza Mar de Cortés",
    location: "Los Cabos, Baja California Sur",
    price: "$600",
    unit: "/evento",
    rating: "4.9",
    image: "https://images.unsplash.com/photo-1517638851339-a711cfcf3279?auto=format&fit=crop&w=1400&q=90",
    tag: "Vista al mar"
},
    "serenity-pavilion": {
    id: "serenity-pavilion",
    name: "Pabellón Cenote Azul",
    location: "Tulum, Quintana Roo",
    price: "$520",
    unit: "/día",
    rating: "4.8",
    image: "https://images.unsplash.com/photo-1505693416388-ac5ce068fe85?auto=format&fit=crop&w=1400&q=90",
    tag: "Riviera Maya"
},
    "glass-foundry": {
    id: "glass-foundry",
    name: "Casa Puerto Escondido",
    location: "Puerto Escondido, Oaxaca",
    price: "$450",
    unit: "/evento",
    rating: "4.7",
    image: "https://images.unsplash.com/photo-1600585154340-be6161a56a0c?auto=format&fit=crop&w=1400&q=90",
    tag: "Costa"
},
    "casa-jacaranda": {
    id: "casa-jacaranda",
    name: "Casona Jacaranda",
    location: "Mérida, Yucatán",
    price: "$760",
    unit: "/evento",
    rating: "4.8",
    image: "https://images.unsplash.com/photo-1464366400600-7168b8af9bc3?auto=format&fit=crop&w=1400&q=90",
    tag: "Colonial"
},
    "terraza-nube": {
    id: "terraza-nube",
    name: "Terraza Reforma 360",
    location: "Ciudad de México, CDMX",
    price: "$700",
    unit: "/evento",
    rating: "4.6",
    image: "https://images.unsplash.com/photo-1505236858219-8359eb29e329?auto=format&fit=crop&w=1400&q=90",
    tag: "Urbano"
},
    "hacienda-solara": {
    id: "hacienda-solara",
    name: "Hacienda Sol de Bernal",
    location: "Bernal, Querétaro",
    price: "$980",
    unit: "/evento",
    rating: "4.9",
    image: "https://images.unsplash.com/photo-1511795409834-ef04bbd61622?auto=format&fit=crop&w=1400&q=90",
    tag: "Pueblo mágico"
}
};

    const profileForm = document.querySelector("[data-profile-form]");
    const profileMode = document.querySelector("[data-profile-mode]");
    const editButton = document.querySelector("[data-edit-profile]");
    const saveButton = document.querySelector("[data-save-profile]");
    const cancelButton = document.querySelector("[data-cancel-profile]");
    const tabs = document.querySelectorAll("[data-tab]");
    const panels = document.querySelectorAll("[data-panel]");
    const scrollButtons = document.querySelectorAll("[data-scroll-to]");
    const bookingsList = document.querySelector("[data-bookings-list]");
    const favoritesList = document.querySelector("[data-favorites-list]");
    const cartSummary = document.querySelector("[data-cart-summary]");
    const cartSubtotal = document.querySelector("[data-cart-subtotal]");
    const cartDeposit = document.querySelector("[data-cart-deposit]");
    const cartTotal = document.querySelector("[data-cart-total]");
    const cartStatus = document.querySelector("[data-cart-status]");
    const countBookings = document.querySelector("[data-count-bookings]");
    const countFavorites = document.querySelector("[data-count-favorites]");
    const countCart = document.querySelector("[data-count-cart]");
    const userDisplay = document.querySelector("[data-user-display]");
    const userEmail = document.querySelector("[data-user-email]");

    const money = (value) =>
    `$${Number(value || 0).toLocaleString("en-US", { minimumFractionDigits: 2, maximumFractionDigits: 2 })}`;

    const readJSON = (key, fallback) => {
    try {
    const raw = localStorage.getItem(key);
    return raw ? JSON.parse(raw) : fallback;
} catch (error) {
    return fallback;
}
};

    const writeJSON = (key, value) => {
    localStorage.setItem(key, JSON.stringify(value));
};

    const profile = { ...defaultProfile, ...readJSON(profileKey, {}) };

    function parsePrice(value) {
    const numeric = Number(String(value || "").replace(/[^0-9]/g, ""));
    return Number.isFinite(numeric) && numeric > 0 ? numeric : 0;
}

    function resolveVenueInput(value) {
    if (!value) return null;
    if (typeof value === "string") {
    const byId = fallbackVenues[value];
    if (byId) return byId;
    const match = Object.values(fallbackVenues).find((venue) => venue.name === value);
    return match || { name: value, location: "Ubicación pendiente", price: "$0", unit: "/evento", rating: "0.0", image: "", tag: "Seleccionado" };
}

    return value;
}

    function toVenueCard(venue) {
    const source = resolveVenueInput(venue);
    if (!source) return null;
    const normalized = fallbackVenues[source.id] || {};
    return {
    id: source.id || normalized.id || "recinto",
    kind: source.kind || normalized.kind || "venue",
    name: source.name || normalized.name || "Recinto seleccionado",
    location: source.location || normalized.location || "Ubicación no disponible",
    price: source.price || normalized.price || "$0",
    unit: source.unit || normalized.unit || "/evento",
    rating: source.rating || normalized.rating || "0.0",
    image: source.image || normalized.image || "",
    tag: source.tag || normalized.tag || "Seleccionado"
};
}

    function getBookings() {
    return readJSON(bookingsKey, []);
}

    function getFavorites() {
    return readJSON(favoritesKey, []).map(toVenueCard).filter(Boolean);
}

    function getCartVenue() {
    const savedVenue = readJSON(selectedVenueKey, null);
    const eventCart = readJSON(cartKey, null);
    return toVenueCard((eventCart && eventCart.recinto) || savedVenue || null);
}

    function getCartServices() {
    const eventCart = readJSON(cartKey, null);
    const services = Array.isArray(eventCart?.servicios) ? eventCart.servicios : [];
    return services
    .map((service) => ({
    id: service.id || service.nombre || service.name,
    name: service.nombre || service.name || "Servicio extra",
    price: parsePrice(service.precio || service.price),
    category: service.categoria || service.category || "Servicio"
}))
    .filter((service) => service.id);
}

    function setActiveTab(id) {
    tabs.forEach((button) => button.classList.toggle("is-active", button.dataset.tab === id));
    panels.forEach((panel) => panel.classList.toggle("hidden", panel.id !== id));
}

    function renderProfile() {
    const profileValues = {
    name: profile.name || defaultProfile.name,
    email: profile.email || defaultProfile.email,
    phone: profile.phone || defaultProfile.phone,
    city: profile.city || defaultProfile.city,
    address: profile.address || defaultProfile.address
};

    document.querySelectorAll("[data-field]").forEach((input) => {
    input.value = profileValues[input.dataset.field] || "";
});

    userDisplay.textContent = profileValues.name;
    userEmail.textContent = profileValues.email;

    countBookings.textContent = String(getBookings().length);
    countFavorites.textContent = String(getFavorites().length);

    const cartVenue = getCartVenue();
    const cartServices = getCartServices();
    countCart.textContent = String((cartVenue ? 1 : 0) + cartServices.length);

    renderBookings();
    renderFavorites();
    renderCart();
}

    function renderBookings() {
    const bookings = getBookings();
    if (!bookings.length) {
    bookingsList.innerHTML = `
                <p class="empty-state">Aún no tienes reservas guardadas. Cuando confirmes un recinto, aparecerá aquí.</p>
            `;
    return;
}

    bookingsList.innerHTML = bookings
    .slice(0, 6)
    .map((booking) => {
    const venue = toVenueCard(booking.venueDetails || booking.venue || booking.recinto || booking);
    const date = booking.paidAt
    ? new Date(booking.paidAt).toLocaleDateString("es-MX", { dateStyle: "medium" })
    : "Fecha pendiente";
    const total = booking.total || (parsePrice(venue.price) * 1.3);

    return `
                    <article class="booking-card">
                        <img src="${venue.image}" alt="${venue.name}">
                        <div class="booking-body">
                            <span class="booking-tag">${venue.tag}</span>
                            <h3>${venue.name}</h3>
                            <p class="booking-meta">${venue.location}</p>
                            <p class="booking-meta">${date} · ${booking.guests || 0} invitados</p>
                            <strong class="booking-price">${money(total)}</strong>
                            <div class="booking-actions">
                                <a class="ui-button ui-button--ghost" href="ticket.html">Ver ticket</a>
                                <a class="ui-button ui-button--solid" href="pago.html">Pagar de nuevo</a>
                            </div>
                        </div>
                    </article>
                `;
})
    .join("");
}

    function renderFavorites() {
    const favorites = getFavorites();
    if (!favorites.length) {
    favoritesList.innerHTML = `
                <p class="empty-state">Aún no has guardado favoritos. Puedes marcarlos con el corazón desde catálogo, detalle o servicios extra.</p>
            `;
    return;
}

    favoritesList.innerHTML = favorites
    .map(
    (venue) => `
                    <article class="favorite-card">
                        <img src="${venue.image}" alt="${venue.name}">
                        <div class="favorite-body">
                            <span class="favorite-tag">${venue.tag} · ★ ${venue.rating}</span>
                            <h3>${venue.name}</h3>
                            <p class="favorite-meta">${venue.location}</p>
                            <strong class="favorite-price">${venue.price} <small>${venue.unit}</small></strong>
                            <div class="favorite-actions">
                                <a class="ui-button ui-button--ghost" href="detalle-recinto.html?venue=${venue.id}">Ver recinto</a>
                                <button class="ui-button ui-button--solid" type="button" data-remove-favorite="${venue.id}">Quitar</button>
                            </div>
                        </div>
                    </article>
                `
    )
    .join("");
}

    function renderCart() {
    const cartVenue = getCartVenue();
    const cartServices = getCartServices();
    const venueSubtotal = parsePrice(cartVenue?.price);
    const servicesTotal = cartServices.reduce((sum, service) => sum + service.price, 0);
    const subtotal = venueSubtotal + servicesTotal;
    const deposit = Math.round(subtotal * 0.3);
    const total = subtotal + deposit;

    cartSubtotal.textContent = money(subtotal);
    cartDeposit.textContent = money(deposit);
    cartTotal.textContent = money(total);

    if (!cartVenue && !cartServices.length) {
    cartStatus.textContent = "Sin selección";
    cartSummary.innerHTML = `
                <div class="cart-summary-empty empty-state">
                    <p>No hay recinto ni servicios seleccionados todavía.</p>
                    <a class="ui-button ui-button--solid" href="catalogo.html">Elegir un recinto</a>
                </div>
            `;
    return;
}

    cartStatus.textContent = "Listo para pagar";
    cartSummary.innerHTML = `
            <div class="cart-venue">
                <img src="${cartVenue?.image || "https://images.unsplash.com/photo-1518005020951-eccb494ad742?auto=format&fit=crop&w=1400&q=90"}" alt="${cartVenue?.name || "Recinto seleccionado"}">
                <div>
                    <span class="booking-tag">${cartVenue?.tag || "Seleccionado"}</span>
                    <h3>${cartVenue?.name || "Recinto seleccionado"}</h3>
                    <p class="booking-meta">${cartVenue?.location || "Ubicación pendiente"}</p>
                    <p class="booking-meta">${cartVenue ? `${cartVenue.price} ${cartVenue.unit}` : "Sin tarifa definida"}</p>
                </div>
            </div>
            <div class="cart-services">
                ${cartServices.length
    ? cartServices.map((service) => `
                        <div class="cart-service">
                            <div>
                                <strong>${service.name}</strong>
                                <span>${service.category}</span>
                            </div>
                            <strong>${money(service.price)}</strong>
                        </div>
                    `).join("")
    : `<p class="booking-meta">No hay servicios extra añadidos.</p>`
}
            </div>
        `;
}

    function toggleEditing(enabled) {
    document.querySelectorAll("[data-field]").forEach((input) => {
    input.disabled = !enabled;
});

    profileMode.textContent = enabled ? "Edición activa" : "Solo lectura";
    editButton.hidden = enabled;
    saveButton.hidden = !enabled;
    cancelButton.hidden = !enabled;
}

    tabs.forEach((button) => {
    button.addEventListener("click", () => {
    setActiveTab(button.dataset.tab);
    document.getElementById(button.dataset.tab)?.scrollIntoView({ behavior: "smooth", block: "start" });
});
});

    scrollButtons.forEach((button) => {
    button.addEventListener("click", () => {
    const target = document.getElementById(button.dataset.scrollTo);
    if (target) {
    setActiveTab(target.id);
    target.scrollIntoView({ behavior: "smooth", block: "start" });
}
});
});

    editButton.addEventListener("click", () => toggleEditing(true));

    cancelButton.addEventListener("click", () => {
    toggleEditing(false);
    renderProfile();
});

    profileForm.addEventListener("submit", (event) => {
    event.preventDefault();
    const values = Object.fromEntries(new FormData(profileForm).entries());
    writeJSON(profileKey, values);
    Object.assign(profile, values);
    toggleEditing(false);
    renderProfile();
});

    document.addEventListener("click", (event) => {
    const removeButton = event.target.closest("[data-remove-favorite]");
    if (!removeButton) return;

    const favorites = getFavorites().filter((venue) => venue.id !== removeButton.dataset.removeFavorite);
    writeJSON(favoritesKey, favorites);
    renderProfile();
});

    toggleEditing(false);
    setActiveTab("profile-personal");
    renderProfile();

    window.addEventListener("storage", renderProfile);
    window.addEventListener("focus", renderProfile);
})();