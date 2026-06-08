const favoriteStorageKey = "gedsFavorites";

const venues = [
  {
    id: "villa-laura",
    name: "Jardin Encanto Avandaro",
    location: "Valle de Bravo, Estado de Mexico",
    rating: "4.9",
    capacity: 120,
    price: "$850",
    priceNumber: 850,
    unit: "/evento",
    type: "wedding",
    size: "tall",
    featured: true,
    tag: "Bosque",
    image: "https://images.unsplash.com/photo-1519167758481-83f550bb49b3?auto=format&fit=crop&w=900&q=90"
  },
  {
    id: "palais-marbre",
    name: "Hacienda Los Arcos",
    location: "San Miguel de Allende, Guanajuato",
    rating: "5.0",
    capacity: 500,
    price: "$1,200",
    priceNumber: 1200,
    unit: "/evento",
    type: "gala",
    size: "small",
    tag: "Hacienda",
    image: "https://images.unsplash.com/photo-1519671482749-fd09be7ccebf?auto=format&fit=crop&w=900&q=90"
  },
  {
    id: "apex-skyline",
    name: "Terraza Mar de Cortes",
    location: "Los Cabos, Baja California Sur",
    rating: "4.9",
    capacity: 80,
    price: "$600",
    priceNumber: 600,
    unit: "/evento",
    type: "corporate",
    size: "small",
    tag: "Vista al mar",
    image: "https://images.unsplash.com/photo-1517638851339-a711cfcf3279?auto=format&fit=crop&w=900&q=90"
  },
  {
    id: "serenity-pavilion",
    name: "Pabellon Cenote Azul",
    location: "Tulum, Quintana Roo",
    rating: "4.8",
    capacity: 60,
    price: "$520",
    priceNumber: 520,
    unit: "/dia",
    type: "private",
    size: "tall",
    tag: "Riviera Maya",
    image: "https://images.unsplash.com/photo-1505693416388-ac5ce068fe85?auto=format&fit=crop&w=900&q=90"
  },
  {
    id: "glass-foundry",
    name: "Casa Puerto Escondido",
    location: "Puerto Escondido, Oaxaca",
    rating: "4.7",
    capacity: 250,
    price: "$450",
    priceNumber: 450,
    unit: "/evento",
    type: "corporate",
    size: "medium",
    tag: "Costa",
    image: "https://images.unsplash.com/photo-1600585154340-be6161a56a0c?auto=format&fit=crop&w=900&q=90"
  },
  {
    id: "casa-jacaranda",
    name: "Casona Jacaranda",
    location: "Merida, Yucatan",
    rating: "4.8",
    capacity: 180,
    price: "$760",
    priceNumber: 760,
    unit: "/evento",
    type: "wedding",
    size: "small",
    tag: "Colonial",
    image: "https://images.unsplash.com/photo-1464366400600-7168b8af9bc3?auto=format&fit=crop&w=900&q=90"
  },
  {
    id: "terraza-nube",
    name: "Terraza Reforma 360",
    location: "Ciudad de Mexico, CDMX",
    rating: "4.6",
    capacity: 140,
    price: "$700",
    priceNumber: 700,
    unit: "/evento",
    type: "private",
    size: "small",
    tag: "Urbano",
    image: "https://images.unsplash.com/photo-1505236858219-8359eb29e329?auto=format&fit=crop&w=900&q=90"
  },
  {
    id: "hacienda-solara",
    name: "Hacienda Sol de Bernal",
    location: "Bernal, Queretaro",
    rating: "4.9",
    capacity: 340,
    price: "$980",
    priceNumber: 980,
    unit: "/evento",
    type: "gala",
    size: "medium",
    tag: "Pueblo Magico",
    image: "https://images.unsplash.com/photo-1511795409834-ef04bbd61622?auto=format&fit=crop&w=900&q=90"
  }
];

const state = {
  filter: "all",
  capacity: null,
  maxPrice: null,
  query: "",
  visible: Number.POSITIVE_INFINITY
};

const results = document.querySelector("[data-catalog-results]");
const eventFilters = document.querySelector("[data-event-filters]");
const capacityFilters = document.querySelector("[data-capacity-filters]");
const priceFilters = document.querySelector("[data-price-filters]");
const citySearch = document.querySelector("[data-city-search]");
const loadMore = document.querySelector("[data-load-more]");
const filtersPanel = document.querySelector("[data-filters-panel]");
const sidebarToggle = document.querySelector("[data-sidebar-toggle]");
const closeFilters = document.querySelector("[data-close-filters]");
const focusSearch = document.querySelector("[data-focus-search]");

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
  renderVenues();
}

function matchesCapacity(venue) {
  if (!state.capacity) return true;
  const cap = Number(state.capacity);
  if (cap === 50) return venue.capacity <= 50;
  if (cap === 150) return venue.capacity > 50 && venue.capacity <= 150;
  if (cap === 300) return venue.capacity > 150 && venue.capacity <= 300;
  return venue.capacity > 300;
}

function matchesPrice(venue) {
  if (!state.maxPrice) return true;
  const maxPrice = Number(state.maxPrice);
  if (maxPrice === 2000) return venue.priceNumber > 900;
  return venue.priceNumber <= maxPrice;
}

function filteredVenues() {
  return venues.filter((venue) => {
    const matchesType = state.filter === "all" || venue.type === state.filter;
    const query = state.query.trim().toLowerCase();
    const matchesQuery = !query || `${venue.name} ${venue.location}`.toLowerCase().includes(query);
    return matchesType && matchesCapacity(venue) && matchesPrice(venue) && matchesQuery;
  });
}

function venueTemplate(venue) {
  const active = isFavorite(venue.id);
  return `
    <article class="catalog-card ${venue.size}">
      <div class="card-image" style='${imageStyle(venue.image)}'>
        <button class="favorite-button ${active ? "active" : ""}" type="button" data-venue-id="${venue.id}" aria-label="Guardar ${venue.name}">${active ? "&hearts;" : "&#9825;"}</button>
        ${venue.featured ? '<span class="featured-tag">Featured</span>' : ""}
      </div>
      <div class="card-body">
        <div class="card-title-row">
          <h2><a href="detalle-recinto.html?venue=${venue.id}" data-detail-id="${venue.id}">${venue.name}</a></h2>
          <span class="rating">&#9733; ${venue.rating}</span>
        </div>
        <p class="location">Ubicacion: ${venue.location}</p>
        <div class="card-divider"></div>
        <div class="card-footer">
          <span class="capacity">Hasta ${venue.capacity} invitados</span>
          <strong class="price">${venue.price}<span>${venue.unit}</span></strong>
          <a class="details-link" href="detalle-recinto.html?venue=${venue.id}" data-detail-id="${venue.id}">Ver detalles</a>
        </div>
      </div>
    </article>
  `;
}

function renderVenues() {
  const filtered = filteredVenues();
  const visibleVenues = filtered.slice(0, state.visible);

  results.innerHTML = visibleVenues.length
    ? visibleVenues.map(venueTemplate).join("")
    : '<p class="empty-state">No encontramos recintos con esos filtros. Prueba otra ciudad, capacidad o tipo de evento.</p>';

  loadMore.hidden = state.visible >= filtered.length;
}

function setActiveButton(container, selector, value) {
  container.querySelectorAll("button").forEach((button) => {
    button.classList.toggle("active", button.dataset[selector] === value);
  });
}

function closeSidebar() {
  filtersPanel.classList.remove("open");
  document.body.classList.remove("filters-open");
}

sidebarToggle.addEventListener("click", () => {
  filtersPanel.classList.add("open");
  document.body.classList.add("filters-open");
});

closeFilters.addEventListener("click", closeSidebar);

focusSearch.addEventListener("click", () => {
  filtersPanel.classList.add("open");
  document.body.classList.add("filters-open");
  citySearch.focus();
});

eventFilters.addEventListener("click", (event) => {
  const button = event.target.closest("button");
  if (!button) return;
  state.filter = button.dataset.filter;
  state.visible = Number.POSITIVE_INFINITY;
  setActiveButton(eventFilters, "filter", state.filter);
  renderVenues();
});

capacityFilters.addEventListener("click", (event) => {
  const button = event.target.closest("button");
  if (!button) return;
  state.capacity = state.capacity === button.dataset.capacity ? null : button.dataset.capacity;
  capacityFilters.querySelectorAll("button").forEach((item) => {
    item.classList.toggle("active", item === button && Boolean(state.capacity));
  });
  state.visible = Number.POSITIVE_INFINITY;
  renderVenues();
});

priceFilters.addEventListener("click", (event) => {
  const button = event.target.closest("button");
  if (!button) return;
  state.maxPrice = state.maxPrice === button.dataset.price ? null : button.dataset.price;
  priceFilters.querySelectorAll("button").forEach((item) => {
    item.classList.toggle("active", item === button && Boolean(state.maxPrice));
  });
  state.visible = Number.POSITIVE_INFINITY;
  renderVenues();
});

citySearch.addEventListener("input", (event) => {
  state.query = event.target.value;
  state.visible = Number.POSITIVE_INFINITY;
  renderVenues();
});

loadMore.addEventListener("click", () => {
  state.visible += 3;
  renderVenues();
});

results.addEventListener("click", (event) => {
  const detailLink = event.target.closest("[data-detail-id]");
  if (detailLink) {
    const selected = venues.find((item) => item.id === detailLink.dataset.detailId);
    if (selected) saveSelectedVenue(selected);
    return;
  }

  const button = event.target.closest("[data-venue-id]");
  if (!button) return;
  const venue = venues.find((item) => item.id === button.dataset.venueId);
  if (venue) {
    toggleFavorite(venue);
  }
});

const footerNewsletter = document.querySelector("[data-footer-newsletter]");
if (footerNewsletter) {
  footerNewsletter.addEventListener("submit", (event) => {
    event.preventDefault();
    event.currentTarget.reset();
  });
}

renderVenues();






