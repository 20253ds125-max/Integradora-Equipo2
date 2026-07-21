const favoriteStorageKey = "gedsFavorites";

const featuredVenues = [
    {
        id: "glass-chalet",
        name: "Refugio Nevado de Monterreal",
        location: "Arteaga, Coahuila",
        price: "$1,200",
        unit: "/ por dia",
        tag: "Sierra",
        rating: "4.9",
        large: true,
        image:
            "https://images.unsplash.com/photo-1604014237800-1c9102c219da?auto=format&fit=crop&w=1400&q=85"
    },
    {
        id: "villa-terra",
        name: "Villa Brisa de Tulum",
        location: "Tulum, Quintana Roo",
        price: "$850",
        unit: "/ por dia",
        tag: "Playa",
        rating: "4.8",
        image:
            "https://images.unsplash.com/photo-1500530855697-b586d89ba3ee?auto=format&fit=crop&w=900&q=85"
    },
    {
        id: "hacienda-aurora",
        name: "Hacienda Aurora Colonial",
        location: "San Miguel de Allende, Guanajuato",
        price: "$980",
        unit: "/ por dia",
        tag: "Historico",
        rating: "4.7",
        image:
            "https://images.unsplash.com/photo-1505236858219-8359eb29e329?auto=format&fit=crop&w=900&q=85"
    }
];

const venueDirectory = {
    "villa-laura": { name: "Jardin Encanto Avandaro", location: "Valle de Bravo, Estado de Mexico", price: "$850", unit: "/evento", rating: "4.9", tag: "Bosque", image: "https://images.unsplash.com/photo-1519167758481-83f550bb49b3?auto=format&fit=crop&w=900&q=90" },
    "palais-marbre": { name: "Hacienda Los Arcos", location: "San Miguel de Allende, Guanajuato", price: "$1,200", unit: "/evento", rating: "5.0", tag: "Hacienda", image: "https://images.unsplash.com/photo-1519671482749-fd09be7ccebf?auto=format&fit=crop&w=900&q=90" },
    "apex-skyline": { name: "Terraza Mar de Cortes", location: "Los Cabos, Baja California Sur", price: "$600", unit: "/evento", rating: "4.9", tag: "Vista al mar", image: "https://images.unsplash.com/photo-1517638851339-a711cfcf3279?auto=format&fit=crop&w=900&q=90" },
    "serenity-pavilion": { name: "Pabellon Cenote Azul", location: "Tulum, Quintana Roo", price: "$520", unit: "/dia", rating: "4.8", tag: "Riviera Maya", image: "https://images.unsplash.com/photo-1505693416388-ac5ce068fe85?auto=format&fit=crop&w=900&q=90" },
    "glass-foundry": { name: "Casa Puerto Escondido", location: "Puerto Escondido, Oaxaca", price: "$450", unit: "/evento", rating: "4.7", tag: "Costa", image: "https://images.unsplash.com/photo-1600585154340-be6161a56a0c?auto=format&fit=crop&w=900&q=90" },
    "casa-jacaranda": { name: "Casona Jacaranda", location: "Merida, Yucatan", price: "$760", unit: "/evento", rating: "4.8", tag: "Colonial", image: "https://images.unsplash.com/photo-1464366400600-7168b8af9bc3?auto=format&fit=crop&w=900&q=90" },
    "terraza-nube": { name: "Terraza Reforma 360", location: "Ciudad de Mexico, CDMX", price: "$700", unit: "/evento", rating: "4.6", tag: "Urbano", image: "https://images.unsplash.com/photo-1505236858219-8359eb29e329?auto=format&fit=crop&w=900&q=90" },
    "hacienda-solara": { name: "Hacienda Sol de Bernal", location: "Bernal, Queretaro", price: "$980", unit: "/evento", rating: "4.9", tag: "Pueblo Magico", image: "https://images.unsplash.com/photo-1511795409834-ef04bbd61622?auto=format&fit=crop&w=900&q=90" },
    "glass-chalet": { name: "Refugio Nevado de Monterreal", location: "Arteaga, Coahuila", price: "$1,200", unit: "/ por dia", rating: "4.9", tag: "Sierra", image: "https://images.unsplash.com/photo-1604014237800-1c9102c219da?auto=format&fit=crop&w=1400&q=85" },
    "villa-terra": { name: "Villa Brisa de Tulum", location: "Tulum, Quintana Roo", price: "$850", unit: "/ por dia", rating: "4.8", tag: "Playa", image: "https://images.unsplash.com/photo-1500530855697-b586d89ba3ee?auto=format&fit=crop&w=900&q=85" },
    "hacienda-aurora": { name: "Hacienda Aurora Colonial", location: "San Miguel de Allende, Guanajuato", price: "$980", unit: "/ por dia", rating: "4.7", tag: "Historico", image: "https://images.unsplash.com/photo-1505236858219-8359eb29e329?auto=format&fit=crop&w=900&q=85" }
};

const favoritesContainer = document.querySelector("[data-favorites]");
const featuredContainer = document.querySelector("[data-featured]");
const menuToggle = document.querySelector("[data-menu-toggle]");
const mobileNav = document.querySelector("[data-mobile-nav]");
const searchDialog = document.querySelector("[data-search-dialog]");
const openSearchButtons = document.querySelectorAll("[data-open-search]");
const newsletterForm = document.querySelector("[data-newsletter-form]");
const formStatus = document.querySelector("[data-form-status]");
const heroSearch = document.querySelector("[data-hero-search]");

function imageStyle(url) {
    return `--image: url("${url}")`;
}

function getFavorites() {
    return JSON.parse(localStorage.getItem(favoriteStorageKey) || "[]");
}

function saveFavorites(favorites) {
    localStorage.setItem(favoriteStorageKey, JSON.stringify(favorites));
}

function isFavorite(id) {
    return getFavorites().some((v) => v.id === id);
}

function venueForStorage(venue) {
    return {
        id: venue.id,
        name: venue.name,
        location: venue.location,
        price: venue.price,
        unit: venue.unit,
        rating: venue.rating,
        image: venue.image,
        tag: venue.tag
    };
}

function toggleFavorite(venue) {
    const favorites = getFavorites();
    const exists = favorites.some((v) => v.id === venue.id);

    const updated = exists
        ? favorites.filter((v) => v.id !== venue.id)
        : [venueForStorage(venue), ...favorites];

    saveFavorites(updated);
    renderFavorites();
    syncFavoriteButtons();
}

function renderFavorites() {
    if (!favoritesContainer) return;

    const favorites = getFavorites().filter((item) => item.kind !== "service");

    if (favorites.length === 0) {
        favoritesContainer.innerHTML = `
      <article class="favorite-empty">
        <p class="eyebrow">Sin favoritos todavía</p>
        <h3>Guarda recintos para verlos aquí</h3>
        <p>Podrás compararlos fácilmente después.</p>
        <a class="text-link" href="catalogo.html">Ver catálogo →</a>
      </article>
    `;
        return;
    }

    favoritesContainer.innerHTML = favorites
        .map(
            (v) => `
      <article class="favorite-card" style='${imageStyle(v.image)}'>
        <button class="favorite-button active" data-favorite-id="${v.id}">♥</button>
        <div>
          <span>${v.tag} · ★ ${v.rating}</span>
          <h3>${v.name}</h3>
          <p>${v.location}</p>
          <strong>${v.price} <small>${v.unit}</small></strong>
        </div>
      </article>
    `
        )
        .join("");
}

function renderFeaturedVenues() {
    featuredContainer.innerHTML = featuredVenues
        .map(
            (v) => `
      <article class="venue-card ${v.large ? "large" : ""}">
        <div class="venue-image" style='${imageStyle(v.image)}'>
          <button class="favorite-button" data-venue-id="${v.id}">♡</button>
        </div>
        <div class="venue-body">
          <h3>${v.name}</h3>
          <p>${v.location}</p>
          <a href="detalle-recinto.html"${v.id} class="text-link venue-card-button" type="button" data-cart-venue="${v.id}">
            Ver Detalles
          </a>
          <div class="price">
    ${v.price}
    <span>${v.unit}</span>
</div>
        </div>
      </article>
    `
        )
        .join("");

    syncFavoriteButtons();
}

function syncFavoriteButtons() {
    document.querySelectorAll("[data-venue-id]").forEach((btn) => {
        const active = isFavorite(btn.dataset.venueId);
        btn.classList.toggle("active", active);
        btn.innerHTML = active ? "♥" : "♡";
    });
}

menuToggle?.addEventListener("click", () => {
    mobileNav.classList.toggle("open");
    document.body.classList.toggle("menu-open");
});

mobileNav?.addEventListener("click", (e) => {
    if (e.target.tagName === "A") {
        mobileNav.classList.remove("open");
        document.body.classList.remove("menu-open");
    }
});

openSearchButtons.forEach((btn) => {
    btn.addEventListener("click", () => searchDialog?.showModal());
});

featuredContainer?.addEventListener("click", (e) => {
    const cartBtn = e.target.closest("[data-cart-venue]");
    if (cartBtn && window.GEDS_CART) {
        const venue = featuredVenues.find((v) => v.id === cartBtn.dataset.cartVenue);
        if (venue) window.GEDS_CART.addVenueToCart(venue);
        return;
    }

    const btn = e.target.closest("[data-venue-id]");
    if (!btn) return;

    const venue = featuredVenues.find((v) => v.id === btn.dataset.venueId);
    if (venue) toggleFavorite(venue);
});

favoritesContainer?.addEventListener("click", (e) => {
    const btn = e.target.closest("[data-favorite-id]");
    if (!btn) return;

    const updated = getFavorites().filter(
        (v) => v.id !== btn.dataset.favoriteId
    );

    saveFavorites(updated);
    renderFavorites();
    syncFavoriteButtons();
});

heroSearch?.addEventListener("submit", (event) => {
    event.preventDefault();

    const formData = new FormData(heroSearch);

    const place = (formData.get("place") || "").toLowerCase();
    const guests = parseInt(formData.get("guests")) || 0;

    if (!place && !guests) return;

    const allVenues = [
        ...featuredVenues,
        ...Object.entries(venueDirectory).map(([id, venue]) => ({
            id,
            ...venue
        }))
    ];

    const results = allVenues.filter((venue) => {
        const matchesPlace =
            venue.location.toLowerCase().includes(place) ||
            venue.name.toLowerCase().includes(place);

        const matchesGuests =
            guests === 0 || guests <= 1500;

        return matchesPlace && matchesGuests;
    });

    renderSearchResults(results);
});

function initCalendar() {
    const calendar = document.querySelector("#calendar");

    if (!calendar || !window.flatpickr) return;

    window.flatpickr(calendar, {
        dateFormat: "Y-m-d",
        minDate: "today",
        disableMobile: true,
        altInput: true,
        altFormat: "F j, Y"
    });
}

document.addEventListener("DOMContentLoaded", () => {
    renderFeaturedVenues();
    renderFavorites();
    initCalendar();
});
function renderSearchResults(venues) {
    const container = document.querySelector("[data-featured]");

    if (!container) return;

    if (!venues.length) {
        container.innerHTML = `
            <p style="padding:20px;">
                No se encontraron recintos con esos filtros.
            </p>
        `;
        return;
    }

    container.innerHTML = venues.map((venue) => `
        <article class="venue-card">

            <div class="venue-image" style="${imageStyle(venue.image)}">

                <button 
                    class="favorite-button ${isFavorite(venue.id) ? "active" : ""}" 
                    data-venue-id="${venue.id}">
                    ${isFavorite(venue.id) ? "♥" : "♡"}
                </button>

            </div>


            <div class="venue-body">

                <span class="tag">
                    ${venue.tag || ""}
                </span>

                <h3>${venue.name}</h3>

                <p>${venue.location}</p>


                <button 
                    class="text-link venue-cart-button"
                    type="button"
                    data-cart-venue="${venue.id}">
                    Añadir al carrito
                </button>


                <div class="venue-footer">
                    <div class="price">
                        ${venue.price}
                        <span>
                            ${venue.unit || ""}
                        </span>
                    </div>
                </div>

            </div>

        </article>
    `).join("");

    syncFavoriteButtons();
}






