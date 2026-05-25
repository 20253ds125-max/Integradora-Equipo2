const favoriteStorageKey = "gedsFavorites";
const venue = {
  id: "terracotta-pavilion",
  name: "The Terracotta Pavilion",
  location: "San Miguel de Allende, Guanajuato",
  price: "$1,200",
  unit: "/ por evento",
  rating: "4.98",
  tag: "Verified",
  image: "https://images.unsplash.com/photo-1518005020951-eccb494ad742?auto=format&fit=crop&w=1400&q=90"
};

const photos = [
  "https://images.unsplash.com/photo-1518005020951-eccb494ad742?auto=format&fit=crop&w=1600&q=90",
  "https://images.unsplash.com/photo-1505693416388-ac5ce068fe85?auto=format&fit=crop&w=1200&q=90",
  "https://images.unsplash.com/photo-1500530855697-b586d89ba3ee?auto=format&fit=crop&w=1200&q=90",
  "https://images.unsplash.com/photo-1464366400600-7168b8af9bc3?auto=format&fit=crop&w=1200&q=90"
];

let currentPhoto = 0;
let guestCount = 25;

const carouselImage = document.querySelector("[data-carousel-image]");
const carouselCount = document.querySelector("[data-carousel-count]");
const thumbs = document.querySelector("[data-carousel-thumbs]");
const favoriteButton = document.querySelector("[data-detail-favorite]");
const guestCountEl = document.querySelector("[data-guest-count]");
const bookingStatus = document.querySelector("[data-booking-status]");

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
  guestCount = Math.min(50, guestCount + 1);
  guestCountEl.textContent = String(guestCount);
});

document.querySelector("[data-check-availability]").addEventListener("click", () => {
  bookingStatus.textContent = `Solicitud preparada para ${guestCount} invitados. Inicia sesion para continuar.`;
});

renderThumbs();
renderCarousel();
syncFavoriteButton();
