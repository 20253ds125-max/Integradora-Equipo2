const favoriteStorageKey = "gedsFavorites";
const selectedVenueKey = "gedsSelectedVenue";

const venueCatalog = {
  "villa-laura": {
    id: "villa-laura",
    name: "Jardin Encanto Avandaro",
    location: "Valle de Bravo, Estado de Mexico",
    price: "$850",
    unit: "/evento",
    rating: "4.9",
    image: "https://images.unsplash.com/photo-1519167758481-83f550bb49b3?auto=format&fit=crop&w=1400&q=90",
    photos: [
      "https://images.unsplash.com/photo-1519167758481-83f550bb49b3?auto=format&fit=crop&w=1600&q=90",
      "https://images.unsplash.com/photo-1519225421980-715cb0215aed?auto=format&fit=crop&w=1200&q=90",
      "https://images.unsplash.com/photo-1464366400600-7168b8af9bc3?auto=format&fit=crop&w=1200&q=90",
      "https://images.unsplash.com/photo-1505236858219-8359eb29e329?auto=format&fit=crop&w=1200&q=90"
    ]
  },
  "palais-marbre": {
    id: "palais-marbre",
    name: "Hacienda Los Arcos",
    location: "San Miguel de Allende, Guanajuato",
    price: "$1,200",
    unit: "/evento",
    rating: "5.0",
    image: "https://images.unsplash.com/photo-1519671482749-fd09be7ccebf?auto=format&fit=crop&w=1400&q=90",
    photos: [
      "https://images.unsplash.com/photo-1519671482749-fd09be7ccebf?auto=format&fit=crop&w=1600&q=90",
      "https://images.unsplash.com/photo-1518005020951-eccb494ad742?auto=format&fit=crop&w=1200&q=90",
      "https://images.unsplash.com/photo-1511795409834-ef04bbd61622?auto=format&fit=crop&w=1200&q=90",
      "https://images.unsplash.com/photo-1464366400600-7168b8af9bc3?auto=format&fit=crop&w=1200&q=90"
    ]
  },
  "apex-skyline": {
    id: "apex-skyline",
    name: "Terraza Mar de Cortes",
    location: "Los Cabos, Baja California Sur",
    price: "$600",
    unit: "/evento",
    rating: "4.9",
    image: "https://images.unsplash.com/photo-1517638851339-a711cfcf3279?auto=format&fit=crop&w=1400&q=90",
    photos: [
      "https://images.unsplash.com/photo-1517638851339-a711cfcf3279?auto=format&fit=crop&w=1600&q=90",
      "https://images.unsplash.com/photo-1500530855697-b586d89ba3ee?auto=format&fit=crop&w=1200&q=90",
      "https://images.unsplash.com/photo-1505693416388-ac5ce068fe85?auto=format&fit=crop&w=1200&q=90",
      "https://images.unsplash.com/photo-1519225421980-715cb0215aed?auto=format&fit=crop&w=1200&q=90"
    ]
  },
  "serenity-pavilion": {
    id: "serenity-pavilion",
    name: "Pabellon Cenote Azul",
    location: "Tulum, Quintana Roo",
    price: "$520",
    unit: "/dia",
    rating: "4.8",
    image: "https://images.unsplash.com/photo-1505693416388-ac5ce068fe85?auto=format&fit=crop&w=1400&q=90",
    photos: [
      "https://images.unsplash.com/photo-1505693416388-ac5ce068fe85?auto=format&fit=crop&w=1600&q=90",
      "https://images.unsplash.com/photo-1500530855697-b586d89ba3ee?auto=format&fit=crop&w=1200&q=90",
      "https://images.unsplash.com/photo-1518005020951-eccb494ad742?auto=format&fit=crop&w=1200&q=90",
      "https://images.unsplash.com/photo-1464366400600-7168b8af9bc3?auto=format&fit=crop&w=1200&q=90"
    ]
  },
  "glass-foundry": {
    id: "glass-foundry",
    name: "Casa Puerto Escondido",
    location: "Puerto Escondido, Oaxaca",
    price: "$450",
    unit: "/evento",
    rating: "4.7",
    image: "https://images.unsplash.com/photo-1600585154340-be6161a56a0c?auto=format&fit=crop&w=1400&q=90",
    photos: [
      "https://images.unsplash.com/photo-1600585154340-be6161a56a0c?auto=format&fit=crop&w=1600&q=90",
      "https://images.unsplash.com/photo-1600566753086-00f18fb6b3ea?auto=format&fit=crop&w=1200&q=90",
      "https://images.unsplash.com/photo-1600607687939-ce8a6c25118c?auto=format&fit=crop&w=1200&q=90",
      "https://images.unsplash.com/photo-1600210492493-0946911123ea?auto=format&fit=crop&w=1200&q=90"
    ]
  },
  "casa-jacaranda": {
    id: "casa-jacaranda",
    name: "Casona Jacaranda",
    location: "Merida, Yucatan",
    price: "$760",
    unit: "/evento",
    rating: "4.8",
    image: "https://images.unsplash.com/photo-1464366400600-7168b8af9bc3?auto=format&fit=crop&w=1400&q=90",
    photos: [
      "https://images.unsplash.com/photo-1464366400600-7168b8af9bc3?auto=format&fit=crop&w=1600&q=90",
      "https://images.unsplash.com/photo-1519167758481-83f550bb49b3?auto=format&fit=crop&w=1200&q=90",
      "https://images.unsplash.com/photo-1511795409834-ef04bbd61622?auto=format&fit=crop&w=1200&q=90",
      "https://images.unsplash.com/photo-1519225421980-715cb0215aed?auto=format&fit=crop&w=1200&q=90"
    ]
  },
  "terraza-nube": {
    id: "terraza-nube",
    name: "Terraza Reforma 360",
    location: "Ciudad de Mexico, CDMX",
    price: "$700",
    unit: "/evento",
    rating: "4.6",
    image: "https://images.unsplash.com/photo-1505236858219-8359eb29e329?auto=format&fit=crop&w=1400&q=90",
    photos: [
      "https://images.unsplash.com/photo-1505236858219-8359eb29e329?auto=format&fit=crop&w=1600&q=90",
      "https://images.unsplash.com/photo-1517638851339-a711cfcf3279?auto=format&fit=crop&w=1200&q=90",
      "https://images.unsplash.com/photo-1505693416388-ac5ce068fe85?auto=format&fit=crop&w=1200&q=90",
      "https://images.unsplash.com/photo-1518005020951-eccb494ad742?auto=format&fit=crop&w=1200&q=90"
    ]
  },
  "hacienda-solara": {
    id: "hacienda-solara",
    name: "Hacienda Sol de Bernal",
    location: "Bernal, Queretaro",
    price: "$980",
    unit: "/evento",
    rating: "4.9",
    image: "https://images.unsplash.com/photo-1511795409834-ef04bbd61622?auto=format&fit=crop&w=1400&q=90",
    photos: [
      "https://images.unsplash.com/photo-1511795409834-ef04bbd61622?auto=format&fit=crop&w=1600&q=90",
      "https://images.unsplash.com/photo-1519671482749-fd09be7ccebf?auto=format&fit=crop&w=1200&q=90",
      "https://images.unsplash.com/photo-1519167758481-83f550bb49b3?auto=format&fit=crop&w=1200&q=90",
      "https://images.unsplash.com/photo-1505236858219-8359eb29e329?auto=format&fit=crop&w=1200&q=90"
    ]
  },
  "glass-chalet": {
    id: "glass-chalet",
    name: "Refugio Nevado de Monterreal",
    location: "Arteaga, Coahuila",
    price: "$1,200",
    unit: "/ por dia",
    rating: "4.9",
    image: "https://images.unsplash.com/photo-1604014237800-1c9102c219da?auto=format&fit=crop&w=1400&q=85",
    photos: [
      "https://images.unsplash.com/photo-1604014237800-1c9102c219da?auto=format&fit=crop&w=1600&q=90",
      "https://images.unsplash.com/photo-1600585154340-be6161a56a0c?auto=format&fit=crop&w=1200&q=90",
      "https://images.unsplash.com/photo-1518005020951-eccb494ad742?auto=format&fit=crop&w=1200&q=90",
      "https://images.unsplash.com/photo-1505236858219-8359eb29e329?auto=format&fit=crop&w=1200&q=90"
    ]
  },
  "villa-terra": {
    id: "villa-terra",
    name: "Villa Brisa de Tulum",
    location: "Tulum, Quintana Roo",
    price: "$850",
    unit: "/ por dia",
    rating: "4.8",
    image: "https://images.unsplash.com/photo-1500530855697-b586d89ba3ee?auto=format&fit=crop&w=900&q=85",
    photos: [
      "https://images.unsplash.com/photo-1500530855697-b586d89ba3ee?auto=format&fit=crop&w=1600&q=90",
      "https://images.unsplash.com/photo-1505693416388-ac5ce068fe85?auto=format&fit=crop&w=1200&q=90",
      "https://images.unsplash.com/photo-1519225421980-715cb0215aed?auto=format&fit=crop&w=1200&q=90",
      "https://images.unsplash.com/photo-1464366400600-7168b8af9bc3?auto=format&fit=crop&w=1200&q=90"
    ]
  },
  "hacienda-aurora": {
    id: "hacienda-aurora",
    name: "Hacienda Aurora Colonial",
    location: "San Miguel de Allende, Guanajuato",
    price: "$980",
    unit: "/ por dia",
    rating: "4.7",
    image: "https://images.unsplash.com/photo-1505236858219-8359eb29e329?auto=format&fit=crop&w=900&q=85",
    photos: [
      "https://images.unsplash.com/photo-1505236858219-8359eb29e329?auto=format&fit=crop&w=1600&q=90",
      "https://images.unsplash.com/photo-1519671482749-fd09be7ccebf?auto=format&fit=crop&w=1200&q=90",
      "https://images.unsplash.com/photo-1511795409834-ef04bbd61622?auto=format&fit=crop&w=1200&q=90",
      "https://images.unsplash.com/photo-1519167758481-83f550bb49b3?auto=format&fit=crop&w=1200&q=90"
    ]
  },
  "terracotta-pavilion": {
    id: "terracotta-pavilion",
    name: "Hacienda Los Arcos",
    location: "San Miguel de Allende, Guanajuato",
    price: "$1,200",
    unit: "/ por evento",
    rating: "4.98",
    image: "https://images.unsplash.com/photo-1518005020951-eccb494ad742?auto=format&fit=crop&w=1400&q=90",
    photos: [
      "https://images.unsplash.com/photo-1518005020951-eccb494ad742?auto=format&fit=crop&w=1600&q=90",
      "https://images.unsplash.com/photo-1505693416388-ac5ce068fe85?auto=format&fit=crop&w=1200&q=90",
      "https://images.unsplash.com/photo-1500530855697-b586d89ba3ee?auto=format&fit=crop&w=1200&q=90",
      "https://images.unsplash.com/photo-1464366400600-7168b8af9bc3?auto=format&fit=crop&w=1200&q=90"
    ]
  }
};

const urlVenueId = new URLSearchParams(window.location.search).get("venue");
const storedVenue = JSON.parse(localStorage.getItem(selectedVenueKey) || "null");
const venue = venueCatalog[urlVenueId] || (storedVenue && { ...storedVenue, ...venueCatalog[storedVenue.id] }) || venueCatalog["terracotta-pavilion"];
const basePrice = Number(String(venue.price || "$1,200").replace(/[^0-9]/g, "")) || 1200;
const serviceFee = 150;
const photos = venue.photos || [venue.image, venueCatalog["terracotta-pavilion"].photos[1], venueCatalog["terracotta-pavilion"].photos[2], venueCatalog["terracotta-pavilion"].photos[3]];

let currentPhoto = 0;
let guestCount = Number(localStorage.getItem("gedsBookingGuests") || "25");

const carouselImage = document.querySelector("[data-carousel-image]");
const carouselCount = document.querySelector("[data-carousel-count]");
const thumbs = document.querySelector("[data-carousel-thumbs]");
const favoriteButton = document.querySelector("[data-detail-favorite]");
const guestCountEl = document.querySelector("[data-guest-count]");
const title = document.querySelector(".venue-detail h1");
const location = document.querySelector(".venue-detail .location");
const ratingPill = document.querySelector(".rating-pill");
const priceValue = document.querySelector(".price-row strong");
const priceUnit = document.querySelector(".price-row span");
const rentalLine = document.querySelector(".cost-list p:first-child strong");
const totalLine = document.querySelector(".cost-list .total strong");

function money(value) {
  return `$${value.toLocaleString("en-US")}.00`;
}

function imageStyle(url) {
  return `--thumb: url("${url}")`;
}

function getFavorites() {
  return JSON.parse(localStorage.getItem(favoriteStorageKey) || "[]");
}

function saveFavorites(favorites) {
  localStorage.setItem(favoriteStorageKey, JSON.stringify(favorites));
}

function isFavorite() {
  return getFavorites().some((item) => item.id === venue.id);
}

function syncFavoriteButton() {
  const active = isFavorite();
  favoriteButton.classList.toggle("active", active);
  favoriteButton.innerHTML = active ? "&hearts;" : "&#9825;";
}

function toggleFavorite() {
  const favorites = getFavorites();
  const exists = favorites.some((item) => item.id === venue.id);
  const nextFavorites = exists ? favorites.filter((item) => item.id !== venue.id) : [venue, ...favorites];
  saveFavorites(nextFavorites);
  syncFavoriteButton();
}

function renderSelectedVenue() {
  document.title = `GEDS | ${venue.name}`;
  title.textContent = venue.name;
  location.textContent = venue.location;
  ratingPill.innerHTML = `<span>&#9733;</span> ${venue.rating || "4.9"} <small>(128 reviews)</small>`;
  priceValue.textContent = venue.price || money(basePrice).replace(".00", "");
  priceUnit.textContent = venue.unit || "/ por evento";
  rentalLine.textContent = money(basePrice);
  totalLine.textContent = money(basePrice + serviceFee);
  guestCountEl.textContent = String(guestCount);
  localStorage.setItem(selectedVenueKey, JSON.stringify({ ...venue, basePrice }));
}

function renderCarousel() {
  carouselImage.src = photos[currentPhoto];
  carouselCount.textContent = `${currentPhoto + 1} / ${photos.length}`;
  thumbs.querySelectorAll("button").forEach((button, index) => {
    button.classList.toggle("active", index === currentPhoto);
  });
}

function renderThumbs() {
  thumbs.innerHTML = photos
    .map((photo, index) => `<button type="button" style='${imageStyle(photo)}' data-photo-index="${index}" aria-label="Ver foto ${index + 1}"></button>`)
    .join("");
}

document.querySelector("[data-carousel-prev]").addEventListener("click", () => {
  currentPhoto = (currentPhoto - 1 + photos.length) % photos.length;
  renderCarousel();
});

document.querySelector("[data-carousel-next]").addEventListener("click", () => {
  currentPhoto = (currentPhoto + 1) % photos.length;
  renderCarousel();
});

thumbs.addEventListener("click", (event) => {
  const button = event.target.closest("[data-photo-index]");
  if (!button) return;
  currentPhoto = Number(button.dataset.photoIndex);
  renderCarousel();
});

favoriteButton.addEventListener("click", toggleFavorite);

document.querySelector("[data-guest-minus]").addEventListener("click", () => {
  guestCount = Math.max(1, guestCount - 1);
  guestCountEl.textContent = String(guestCount);
});

document.querySelector("[data-guest-plus]").addEventListener("click", () => {
  guestCount = Math.min(500, guestCount + 1);
  guestCountEl.textContent = String(guestCount);
});

const availabilityLink = document.querySelector("[data-check-availability]");
availabilityLink.addEventListener("click", () => {
  localStorage.setItem("gedsBookingGuests", String(guestCount));
  localStorage.setItem(selectedVenueKey, JSON.stringify({ ...venue, basePrice }));
});

renderSelectedVenue();
renderThumbs();
renderCarousel();
syncFavoriteButton();



