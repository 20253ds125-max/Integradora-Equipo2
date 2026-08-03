
const photos = typeof fotosDesdeBD !== "undefined" && fotosDesdeBD.length > 0
    ? fotosDesdeBD
    : [];

let currentPhoto = 0;

const $ = (q) => document.querySelector(q);

const carouselImage = $("[data-carousel-image]");
const carouselCount = $("[data-carousel-count]");
const thumbs = $("[data-carousel-thumbs]");

function renderCarousel() {
    if (!carouselImage || photos.length === 0) return;

   l
    carouselImage.src = photos[currentPhoto];

    if (carouselCount) {
        carouselCount.textContent = `${currentPhoto + 1} / ${photos.length}`;
    }

    thumbs?.querySelectorAll("img, button").forEach((el, i) => {
        el.style.opacity = i === currentPhoto ? "1" : "0.5";
        el.style.border = i === currentPhoto ? "2px solid #d63031" : "none";
    });
}

document.querySelector("[data-carousel-prev]")?.addEventListener("click", () => {
    if (photos.length === 0) return;
    currentPhoto = (currentPhoto - 1 + photos.length) % photos.length;
    renderCarousel();
});


document.querySelector("[data-carousel-next]")?.addEventListener("click", () => {
    if (photos.length === 0) return;
    currentPhoto = (currentPhoto + 1) % photos.length;
    renderCarousel();
});

thumbs?.addEventListener("click", (e) => {
    // Si hiciste clic en una miniatura
    const imgOrBtn = e.target.closest("img, button");
    if (!imgOrBtn) return;

    // Obtenemos todas las miniaturas en un arreglo para saber qué índice se presionó
    const allThumbs = Array.from(thumbs.querySelectorAll("img, button"));
    const index = allThumbs.indexOf(imgOrBtn);

    if (index !== -1) {
        currentPhoto = index;
        renderCarousel();
    }
});


renderCarousel();