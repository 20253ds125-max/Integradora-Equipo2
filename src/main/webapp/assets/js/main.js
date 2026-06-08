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
  return getFavorites().some((venue) => venue.id === id);
}


function saveSelectedVenue(venue) {
  localStorage.setItem("gedsSelectedVenue", JSON.stringify(venueForStorage(venue)));
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
    tag: venue.tag || "Favorito"
  };
}

function toggleFavorite(venue) {
  const favorites = getFavorites();
  const exists = favorites.some((item) => item.id === venue.id);
  const nextFavorites = exists
    ? favorites.filter((item) => item.id !== venue.id)
    : [venueForStorage(venue), ...favorites];
  saveFavorites(nextFavorites);
  renderFavorites();
  syncFavoriteButtons();
}

function renderFavorites() {
  const favorites = getFavorites();

  if (!favoritesContainer) {
    return;
  }

  if (favorites.length === 0) {
    favoritesContainer.innerHTML = `
      <article class="favorite-empty">
        <p class="eyebrow">Sin favoritos todavia</p>
        <h3>Marca el corazon de un recinto para guardarlo aqui.</h3>
        <p>Podras comparar tus espacios preferidos y regresar a ellos desde esta seccion.</p>
        <a class="text-link" href="catalogo.html">Ver catalogo &rarr;</a>
      </article>
    `;
    return;
  }

  favoritesContainer.innerHTML = favorites
    .map(
      (venue) => `
        <article class="favorite-card" style='${imageStyle(venue.image)}'>
          <button class="favorite-button active" type="button" data-favorite-id="${venue.id}" aria-label="Quitar ${venue.name} de favoritos">&hearts;</button>
          <div>
            <span>${venue.tag || "Favorito"} · &#9733; ${venue.rating}</span>
            <h3><a href="detalle-recinto.html?venue=${venue.id}" data-detail-id="${venue.id}">${venue.name}</a></h3>
            <p>${venue.location}</p>
            <strong>${venue.price} <small>${venue.unit || ""}</small></strong>
          </div>
        </article>
      `
    )
    .join("");
}

function renderFeaturedVenues() {
  featuredContainer.innerHTML = featuredVenues
    .map(
      (venue) => `
        <article class="venue-card ${venue.large ? "large" : ""}">
          <div class="venue-image" style='${imageStyle(venue.image)}'>
            <button class="favorite-button" type="button" data-venue-id="${venue.id}" aria-label="Guardar ${venue.name}">
              &#9825;
            </button>
          </div>
          <div class="venue-body">
            <div class="venue-meta">
              <span class="tag">${venue.tag}</span>
              <span>&#9733; ${venue.rating}</span>
            </div>
            <h3><a href="detalle-recinto.html?venue=${venue.id}" data-detail-id="${venue.id}">${venue.name}</a></h3>
            <p>Ubicacion: ${venue.location}</p>
            <div class="venue-footer">
              <div class="price">${venue.price} <span>${venue.unit}</span></div>
              <a class="request-link" href="detalle-recinto.html?venue=${venue.id}" data-detail-id="${venue.id}">Ver detalles</a>
            </div>
          </div>
        </article>
      `
    )
    .join("");
  syncFavoriteButtons();
}

function syncFavoriteButtons() {
  document.querySelectorAll("[data-venue-id]").forEach((button) => {
    const active = isFavorite(button.dataset.venueId);
    button.classList.toggle("active", active);
    button.innerHTML = active ? "&hearts;" : "&#9825;";
  });
}

function closeMobileMenu() {
  mobileNav.classList.remove("open");
  document.body.classList.remove("menu-open");
}

menuToggle.addEventListener("click", () => {
  const isOpen = mobileNav.classList.toggle("open");
  document.body.classList.toggle("menu-open", isOpen);
});

mobileNav.addEventListener("click", (event) => {
  if (event.target.matches("a")) {
    closeMobileMenu();
  }
});

openSearchButtons.forEach((button) => {
  button.addEventListener("click", () => {
    searchDialog.showModal();
  });
});

featuredContainer.addEventListener("click", (event) => {
  const detailLink = event.target.closest("[data-detail-id]");
  if (detailLink) {
    const selected = featuredVenues.find((item) => item.id === detailLink.dataset.detailId);
    if (selected) saveSelectedVenue(selected);
    return;
  }

  const button = event.target.closest("[data-venue-id]");
  if (!button) {
    return;
  }

  const venue = featuredVenues.find((item) => item.id === button.dataset.venueId);
  if (venue) {
    toggleFavorite(venue);
  }
});

favoritesContainer.addEventListener("click", (event) => {
  const detailLink = event.target.closest("[data-detail-id]");
  if (detailLink) {
    const selected = getFavorites().find((item) => item.id === detailLink.dataset.detailId);
    if (selected) saveSelectedVenue(selected);
    return;
  }

  const button = event.target.closest("[data-favorite-id]");
  if (!button) {
    return;
  }

  const favorites = getFavorites().filter((venue) => venue.id !== button.dataset.favoriteId);
  saveFavorites(favorites);
  renderFavorites();
  syncFavoriteButtons();
});

window.addEventListener("storage", (event) => {
  if (event.key === favoriteStorageKey) {
    renderFavorites();
    syncFavoriteButtons();
  }
});

heroSearch.addEventListener("submit", (event) => {
  event.preventDefault();
  const formData = new FormData(heroSearch);
  const place = formData.get("place") || "Mexico";
  const guests = formData.get("guests") || "tu evento";
  formStatus.textContent = `Listo: estamos preparando opciones para ${place} con ${guests} invitados.`;
  document.querySelector("#newsletter").scrollIntoView({ behavior: "smooth" });
});

newsletterForm.addEventListener("submit", (event) => {
  event.preventDefault();
  const email = new FormData(newsletterForm).get("email");
  formStatus.textContent = `Gracias. Enviaremos novedades y recintos privados a ${email}.`;
  newsletterForm.reset();
});

renderFavorites();
renderFeaturedVenues();






