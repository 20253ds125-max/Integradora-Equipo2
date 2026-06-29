const favoriteStorageKey = "gedsFavorites";
const selectedVenueKey = "gedsSelectedVenue";

/* ================= VENUES ================= */
const venueCatalog = { /* ❗ lo dejo igual (muy largo) */ };

/* ================= VENUE SELECCIONADO ================= */
const urlVenueId = new URLSearchParams(window.location.search).get("venue");
const storedVenue = JSON.parse(localStorage.getItem(selectedVenueKey) || "null");

const venue =
    venueCatalog[urlVenueId] ||
    (storedVenue && venueCatalog[storedVenue.id]) ||
    venueCatalog["terracotta-pavilion"];

/* ================= PRECIOS ================= */
const basePrice =
    Number(String(venue.price || "$1200").replace(/[^0-9]/g, "")) || 1200;

const serviceFee = 150;

/* ================= GALERÍA ================= */
const photos =
    venue.photos?.length
        ? venue.photos
        : [
            venue.image,
            venueCatalog["terracotta-pavilion"].photos[1],
            venueCatalog["terracotta-pavilion"].photos[2],
            venueCatalog["terracotta-pavilion"].photos[3],
        ];

/* ================= ESTADO ================= */
let currentPhoto = 0;
let guestCount = Number(localStorage.getItem("gedsBookingGuests") || "25");

/* ================= ELEMENTOS DOM ================= */
const $ = (q) => document.querySelector(q);

const carouselImage = $("[data-carousel-image]");
const carouselCount = $("[data-carousel-count]");
const thumbs = $("[data-carousel-thumbs]");

const favoriteButton = $("[data-detail-favorite]");
const guestCountEl = $("[data-guest-count]");

const title = $(".venue-detail h1");
const locationEl = $(".venue-detail .location");

const priceValue = $(".price-row strong");
const priceUnit = $(".price-row span");

const rentalLine = document.querySelector(".cost-list p:first-child strong");
const totalLine = document.querySelector(".cost-list .total strong");

/* ================= HELPERS ================= */
const money = (v) => `$${Number(v).toLocaleString("en-US")}.00`;

const imageStyle = (url) => `--thumb: url("${url}")`;

/* ================= FAVORITOS ================= */
function getFavorites() {
    return JSON.parse(localStorage.getItem(favoriteStorageKey) || "[]");
}

function saveFavorites(list) {
    localStorage.setItem(favoriteStorageKey, JSON.stringify(list));
}

function venueForStorage(source) {
    if (!source) return null;
    return {
        id: source.id,
        kind: source.kind || "venue",
        name: source.name,
        location: source.location,
        price: source.price,
        unit: source.unit || "/evento",
        rating: source.rating || "0.0",
        image: source.image || "",
        tag: source.tag || "Seleccionado"
    };
}

function isFavorite() {
    return getFavorites().some((v) => v.id === venue.id);
}

function syncFavoriteButton() {
    if (!favoriteButton) return;

    const active = isFavorite();
    favoriteButton.classList.toggle("active", active);
    favoriteButton.innerHTML = active ? "♥" : "♡";
}

function toggleFavorite() {
    const favorites = getFavorites();
    const exists = favorites.some((v) => v.id === venue.id);

    const next = exists
        ? favorites.filter((v) => v.id !== venue.id)
        : [venueForStorage(venue), ...favorites];

    saveFavorites(next);
    syncFavoriteButton();
}

/* ================= RENDER VENUE ================= */
function renderSelectedVenue() {
    if (title) title.textContent = venue.name;
    if (locationEl) locationEl.textContent = venue.location;

    if (priceValue) priceValue.textContent = venue.price;
    if (priceUnit) priceUnit.textContent = venue.unit || "/evento";

    if (rentalLine) rentalLine.textContent = money(basePrice);
    if (totalLine) totalLine.textContent = money(basePrice + serviceFee);

    if (guestCountEl) guestCountEl.textContent = String(guestCount);

    localStorage.setItem(
        selectedVenueKey,
        JSON.stringify({ ...venue, basePrice })
    );
}

/* ================= CAROUSEL ================= */
function renderCarousel() {
    if (!carouselImage) return;

    carouselImage.src = photos[currentPhoto];
    if (carouselCount)
        carouselCount.textContent = `${currentPhoto + 1} / ${photos.length}`;

    thumbs?.querySelectorAll("button").forEach((btn, i) => {
        btn.classList.toggle("active", i === currentPhoto);
    });
}

function renderThumbs() {
    if (!thumbs) return;

    thumbs.innerHTML = photos
        .map(
            (p, i) => `
      <button type="button"
        style="${imageStyle(p)}"
        data-photo-index="${i}"
        aria-label="Ver foto ${i + 1}">
      </button>
    `
        )
        .join("");
}

/* ================= EVENTS ================= */
document
    .querySelector("[data-carousel-prev]")
    ?.addEventListener("click", () => {
        currentPhoto = (currentPhoto - 1 + photos.length) % photos.length;
        renderCarousel();
    });

document
    .querySelector("[data-carousel-next]")
    ?.addEventListener("click", () => {
        currentPhoto = (currentPhoto + 1) % photos.length;
        renderCarousel();
    });

thumbs?.addEventListener("click", (e) => {
    const btn = e.target.closest("[data-photo-index]");
    if (!btn) return;

    currentPhoto = Number(btn.dataset.photoIndex);
    renderCarousel();
});

favoriteButton?.addEventListener("click", toggleFavorite);

document
    .querySelector("[data-guest-minus]")
    ?.addEventListener("click", () => {
        guestCount = Math.max(1, guestCount - 1);
        if (guestCountEl) guestCountEl.textContent = guestCount;
    });

document
    .querySelector("[data-guest-plus]")
    ?.addEventListener("click", () => {
        guestCount = Math.min(500, guestCount + 1);
        if (guestCountEl) guestCountEl.textContent = guestCount;
    });

document
    .querySelector("[data-check-availability]")
    ?.addEventListener("click", () => {
        localStorage.setItem("gedsBookingGuests", String(guestCount));
        localStorage.setItem(
            selectedVenueKey,
            JSON.stringify({ ...venue, basePrice })
        );
    });

/* ================= SERVICIOS ================= */
const servicios = [
    {
        nombre: "Fotografía Premium",
        categoria: "Fotografía",
        precio: "$4,500",
        imagen:
            "https://images.unsplash.com/photo-1511285560929-80b456fea0bc?q=80&w=1200",
    },
    {
        nombre: "DJ Experience",
        categoria: "DJ",
        precio: "$6,000",
        imagen:
            "https://images.unsplash.com/photo-1493225457124-a3eb161ffa5f?q=80&w=1200",
    },
    {
        nombre: "Catering Gourmet",
        categoria: "Catering",
        precio: "$12,000",
        imagen:
            "https://images.unsplash.com/photo-1555244162-803834f70033?q=80&w=1200",
    },
    {
        nombre: "Decoración Floral",
        categoria: "Decoración",
        precio: "$5,200",
        imagen:
            "https://images.unsplash.com/photo-1520854221256-17451cc331bf?q=80&w=1200",
    },
    {
        nombre: "Música en Vivo",
        categoria: "Música",
        precio: "$8,000",
        imagen:
            "https://images.unsplash.com/photo-1501386761578-eac5c94b800a?q=80&w=1200",
    },
];

const container = document.getElementById("randomServices");

function renderServices() {
    if (!container) return;

    const random = [...servicios]
        .sort(() => Math.random() - 0.5)
        .slice(0, 3);

    container.innerHTML = random
        .map(
            (s) => `
      <div class="service-card">
        <img src="${s.imagen}" alt="${s.nombre}" loading="lazy">
        <div class="service-info">
          <h3>${s.nombre}</h3>
          <p>${s.categoria}</p>
          <div class="service-price">${s.precio}</div>
        </div>
      </div>
    `
        )
        .join("");
}


renderSelectedVenue();
renderThumbs();
renderCarousel();
renderServices();
syncFavoriteButton();
