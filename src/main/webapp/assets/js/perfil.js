(function () {
    const profileKey = "gedsProfile";
    const bookingsKey = "gedsBookings";
    const favoritesKey = "gedsFavorites";
    const selectedVenueKey = "gedsSelectedVenue";
    const cartKey = "eventOnlineEvento";
    const publicationsList = document.querySelector("[data-publications-list]");
    const realBookingsList = document.querySelector("[data-real-bookings-list]");

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

    function renderBookings() {
        const bookings = getBookings();


        if (publicationsList) {
            if (!bookings.length) {
                publicationsList.innerHTML = `
                <p class="empty-state">Aún no tienes publicaciones guardadas. Cuando publiques un recinto, aparecerá aquí.</p>
            `;
            } else {
                publicationsList.innerHTML = bookings.slice(0, 6).map(booking => {
                    const venue = toVenueCard(booking.venueDetails || booking.venue || booking.recinto || booking);
                    const date = booking.paidAt ? new Date(booking.paidAt).toLocaleDateString("es-MX", { dateStyle: "medium" }) : "Fecha pendiente";
                    const total = booking.total || (parsePrice(venue.price) * 1.3);

                    return `
                    <article class="booking-card">
                        <img src="${venue.image}" alt="${venue.name}">
                        <div class="booking-body">
                            <span class="booking-tag" style="background: #eef2f7; color: #475569;">Publicado</span>
                            <h3>${venue.name}</h3>
                            <p class="booking-meta">${venue.location}</p>
                            <p class="booking-meta">Creado el ${date}</p>
                            <strong class="booking-price">${money(total)}</strong>
                            <div class="booking-actions">
                             <button
                             type="button"
                             class="ui-button ui-button--ghost abrir-modal-edicion"
                             data-venue-id="${venue.id}"
                             data-venue-name="${venue.name}"
                             data-venue-location="${venue.location}"
                             data-venue-price="${parsePrice(venue.price)}">Editar recinto</button>
                             <a class="ui-button ui-button--ghost">Reportar daños</a>
                            </div>
                        </div>
                    </article>
                `;
                }).join("");
            }
        }


        if (realBookingsList) {
            if (!bookings.length) {
                realBookingsList.innerHTML = `
                <p class="empty-state">Aún no tienes reservas guardadas. Cuando confirmes un recinto, aparecerá aquí.</p>
            `;
            } else {
                realBookingsList.innerHTML = bookings.slice(0, 6).map(booking => {
                    const venue = toVenueCard(booking.venueDetails || booking.venue || booking.recinto || booking);
                    const date = booking.paidAt ? new Date(booking.paidAt).toLocaleDateString("es-MX", { dateStyle: "medium" }) : "Fecha pendiente";
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
                                <a class="ui-button ui-button--ghost" href="ticket.html?id=${booking.id || ''}">Ver ticket</a>
                                <a class="ui-button ui-button--solid" href="pago.html?id=${booking.id || ''}">Pagar de nuevo</a>
                            </div>
                        </div>
                    </article>
                `;
                }).join("");
            }
        }
    }

    function renderFavorites() {
        if (!favoritesList) return;
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
                                <button  class="ui-button ui-button--solid" type="button" data-remove-favorite="${venue.id}">Quitar</button>
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

        if (cartSubtotal) cartSubtotal.textContent = money(subtotal);
        if (cartDeposit) cartDeposit.textContent = money(deposit);
        if (cartTotal) cartTotal.textContent = money(total);

        if (!cartSummary) return;

        if (!cartVenue && !cartServices.length) {
            if (cartStatus) cartStatus.textContent = "Sin selección";
            cartSummary.innerHTML = `
                <div class="cart-summary-empty empty-state">
                    <p>No hay recinto ni servicios seleccionados todavía.</p>
                    <a class="ui-button ui-button--solid" href="catalogo.html">Elegir un recinto</a>
                </div>
            `;
            return;
        }

        if (cartStatus) cartStatus.textContent = "Listo para pagar";
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

            if (input.name === "email") {
                input.disabled = true;
                return;
            }

            input.disabled = !enabled;
        });

        if (profileMode) profileMode.textContent = enabled ? "Edición activa" : "Solo lectura";
        if (editButton) editButton.hidden = enabled;
        if (saveButton) saveButton.hidden = !enabled;
        if (cancelButton) cancelButton.hidden = !enabled;
    }

    tabs.forEach((button) => {
        button.addEventListener("click", () => {
            const targetId = button.dataset.tab;
            cambiarSeccionActiva(targetId);
        });
    });

    scrollButtons.forEach((button) => {
        button.addEventListener("click", () => {
            const targetId = button.dataset.scrollTo;
            cambiarSeccionActiva(targetId);
        });
    });

    function cambiarSeccionActiva(targetId) {
        const targetPanel = document.getElementById(targetId);
        if (!targetPanel) return;


        panels.forEach((panel) => {
            panel.classList.toggle("hidden", panel.id !== targetId);
        });


        scrollButtons.forEach((btn) => {
            if (btn.dataset.scrollTo === targetId) {
                btn.classList.add("ui-button--solid");
                btn.classList.remove("ui-button--ghost");
            } else {
                btn.classList.add("ui-button--ghost");
                btn.classList.remove("ui-button--solid");
            }
        });


        tabs.forEach((btn) => {
            btn.classList.toggle("is-active", btn.dataset.tab === targetId);
        });


        targetPanel.scrollIntoView({ behavior: "smooth", block: "start" });
    }


    cambiarSeccionActiva("profile-personal");

    if (editButton) editButton.addEventListener("click", () => toggleEditing(true));

    if (cancelButton) {
        cancelButton.addEventListener("click", () => {
            toggleEditing(false);
            //renderProfile();
        });
    }

    /*if (profileForm) {
        profileForm.addEventListener("submit", (event) => {
            event.preventDefault();
            const values = Object.fromEntries(new FormData(profileForm).entries());
            writeJSON(profileKey, values);
            Object.assign(profile, values);
            toggleEditing(false);
            renderProfile();
        });
    }*/

    document.addEventListener("click", (event) => {
        const removeButton = event.target.closest("[data-remove-favorite]");
        if (!removeButton) return;

        const favorites = getFavorites().filter((venue) => venue.id !== removeButton.dataset.removeFavorite);
        writeJSON(favoritesKey, favorites);
        //renderProfile();
    });

    toggleEditing(false);
    setActiveTab("profile-personal");
    //renderProfile();

    //window.addEventListener("storage", renderProfile);
    //window.addEventListener("focus", renderProfile);
    const modal = document.getElementById(
        "modalEditarRecinto"
    );

    const cerrarModal = document.getElementById(
        "cerrarModal"
    );

    const cancelarModal = document.getElementById(
        "cancelarModal"
    );

    let recintoEditando = null;

    document.addEventListener("click", (e) => {

        const boton = e.target.closest(".abrir-modal-edicion");

        if (!boton) return;

        recintoEditando = boton.dataset.venueId;

        document.getElementById("editNombre").value =
            boton.dataset.venueName;

        document.getElementById("editUbicacion").value =
            boton.dataset.venueLocation;

        document.getElementById("editPrecio").value =
            boton.dataset.venuePrice;

        modal.classList.add("active");
    });

    cerrarModal.addEventListener("click", () => {
        modal.classList.remove("active");
    });

    cancelarModal.addEventListener("click", () => {
        modal.classList.remove("active");
    });

    modal.addEventListener("click", (e) => {

        if (e.target === modal) {
            modal.classList.remove("active");
        }

    });

    document
        .getElementById("formEditarRecinto")
        .addEventListener("submit", (e) => {

            e.preventDefault();

            const nuevoNombre =
                document.getElementById("editNombre").value;

            const nuevaUbicacion =
                document.getElementById("editUbicacion").value;

            const nuevoPrecio =
                document.getElementById("editPrecio").value;

            const bookings = getBookings();


            bookings[0].venueDetails.name = nuevoNombre;
            bookings[0].venueDetails.location = nuevaUbicacion;
            bookings[0].venueDetails.price = `$${nuevoPrecio}`;
            bookings[0].total= parseInt(nuevoPrecio);

            writeJSON(bookingsKey, bookings);

            console.log(
                JSON.parse(localStorage.getItem("gedsBookings"))
            );

            renderBookings();

            modal.classList.remove("active");
        });
})();

//menu desplegable WUUU :)
document.addEventListener("DOMContentLoaded", () => {
    const menuToggle = document.querySelector("[data-menu-toggle]");
    const mobileNav = document.querySelector("[data-mobile-nav]");

    if (menuToggle && mobileNav) {
        menuToggle.addEventListener("click", (e) => {
            e.stopPropagation();
            mobileNav.classList.toggle("open");
            document.body.classList.toggle("menu-open");
        });

        mobileNav.addEventListener("click", (e) => {
            if (e.target.tagName === "A") {
                mobileNav.classList.remove("open");
                document.body.classList.remove("menu-open");
            }
        });

        document.addEventListener("click", (e) => {
            if (!mobileNav.contains(e.target) && !menuToggle.contains(e.target)) {
                mobileNav.classList.remove("open");
                document.body.classList.remove("menu-open");
            }
        });
    }
    const cerrarSe = document.getElementById("cerrarSe");

    if(cerrarSe){
        cerrarSe.addEventListener('click',function (e){
            e.preventDefault();

            const direccion = this.getAttribute("href");
            Swal.fire({
                title: '¿Cerrar sesión?',
                text: '¿Estás seguro de que deseas salir de tu cuenta?',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonText: 'Sí, salir',
                cancelButtonText: 'Cancelar',
                confirmButtonColor: '#855221',
                cancelButtonColor: '#6c757d',
                borderRadius: '12px'
            }).then((result) => {
                if (result.isConfirmed) {
                    window.location.href = direccion;
                }
            });
        });
    }
});

document.addEventListener("DOMContentLoaded", () => {
    const btnEditar = document.querySelector('[data-edit-profile]');
    const btnGuardar = document.querySelector('[data-save-profile]');

    if (btnEditar && btnGuardar) {

        btnEditar.addEventListener('click', () => {

            btnEditar.style.display = 'none';

            btnGuardar.style.display = 'inline-block';


        });
    }
});

//Validacion letras y numeros :>
document.addEventListener("DOMContentLoaded", () => {
    const inputsLetras = document.querySelectorAll('.solo-letras');
    inputsLetras.forEach(input => {
        input.addEventListener('input', function() {
            this.value = this.value.replace(/[^a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\s]/g, '');
        });
    });

    const inputsNumeros = document.querySelectorAll('.solo-numeros');
    inputsNumeros.forEach(input => {
        input.addEventListener('input', function() {
            this.value = this.value.replace(/[^0-9]/g, '');
        });
    });
});