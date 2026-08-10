const photos = typeof fotosDesdeBD !== "undefined" && fotosDesdeBD.length > 0
    ? fotosDesdeBD
    : [];

let currentPhoto = 0;

const $ = (q) => document.querySelector(q);

const carouselMain  = $("[data-carousel-main]");    // imagen nítida (contain)
const carouselBg    = $("[data-carousel-bg]");      // imagen de fondo (blur)
const carouselCount = $("[data-carousel-count]");
const thumbs        = $("[data-carousel-thumbs]");
const carouselEl    = document.querySelector(".carousel");

/* Tamaño de miniaturas */
const THUMB_TRANSFORM = "w_200,h_120,c_fill,g_auto,q_auto:good,f_auto";

/**
 * Inyecta transformaciones de Cloudinary en una URL.
 * Si la URL no es de Cloudinary, la devuelve sin cambios.
 */
function optimizarUrl(url, transform) {
    if (typeof url !== "string") return url;
    const marker = "/upload/";
    const idx = url.indexOf(marker);
    if (idx === -1) return url;
    const before = url.slice(0, idx + marker.length);
    let after = url.slice(idx + marker.length);
    // Si ya tiene transformaciones previas (no empieza con "v"), las saltamos
    const versionMatch = after.match(/^v\d+\//);
    if (!versionMatch) {
        const slashIdx = after.indexOf("/");
        if (slashIdx !== -1 && after.slice(0, slashIdx).includes(",")) {
            after = after.slice(slashIdx + 1);
        }
    }
    return `${before}${transform}/${after}`;
}

/**
 * Mide el tamaño real en pantalla del banner.
 * DPR limitado a 2x, dimensiones máximas 1600×900.
 */
function medirBanner() {
    const rect = carouselEl.getBoundingClientRect();
    const dpr = Math.min(window.devicePixelRatio || 1, 2);
    return {
        w: Math.min(1600, Math.max(1, Math.round(rect.width * dpr))),
        h: Math.min(900,  Math.max(1, Math.round(rect.height * dpr))),
    };
}

/**
 * Renderiza ambas capas del carrusel:
 *  - Fondo difuminado (c_fill → llena todo, se difumina con CSS)
 *  - Imagen principal  (c_limit → se ajusta SIN recortar)
 */
function renderCarousel() {
    if (!carouselMain || photos.length === 0) return;

    const { w, h } = medirBanner();

    // Fondo: c_fill (rellena todo, se verá difuminado con CSS)
    const bgTransform   = `w_${w},h_${h},c_fill,g_auto,q_auto:eco,f_auto`;
    // Principal: solo calidad y formato, SIN redimensionar ni recortar.
    // El CSS (object-fit: contain) se encarga de mostrarla completa.
    const mainTransform = `q_auto:good,f_auto`;

    const mainSrc = optimizarUrl(photos[currentPhoto], mainTransform);
    const bgSrc   = optimizarUrl(photos[currentPhoto], bgTransform);

    // Transición suave
    carouselMain.style.opacity = "0";

    const showImage = () => {
        carouselMain.style.opacity = "1";
    };

    carouselMain.onload = showImage;

    carouselMain.src = mainSrc;

    // Si la imagen ya estaba cacheada, onload no dispara — forzar
    if (carouselMain.complete) {
        showImage();
    }

    if (carouselBg) {
        carouselBg.src = bgSrc;
    }

    if (carouselCount) {
        carouselCount.textContent = `${currentPhoto + 1} / ${photos.length}`;
    }

    // Actualiza estado visual de las miniaturas
    thumbs?.querySelectorAll("img").forEach((el, i) => {
        el.style.opacity = i === currentPhoto ? "1" : "0.5";
        el.style.borderColor = i === currentPhoto ? "var(--clay)" : "transparent";
    });
}

// Optimiza las miniaturas una sola vez al cargar la página
thumbs?.querySelectorAll("img").forEach((img) => {
    img.src = optimizarUrl(img.getAttribute("src"), THUMB_TRANSFORM);
});

// Recalcula al cambiar el tamaño de ventana
let resizeTimer;
window.addEventListener("resize", () => {
    clearTimeout(resizeTimer);
    resizeTimer = setTimeout(renderCarousel, 200);
});

// Botón anterior
document.querySelector("[data-carousel-prev]")?.addEventListener("click", () => {
    if (photos.length === 0) return;
    currentPhoto = (currentPhoto - 1 + photos.length) % photos.length;
    renderCarousel();
});

// Botón siguiente
document.querySelector("[data-carousel-next]")?.addEventListener("click", () => {
    if (photos.length === 0) return;
    currentPhoto = (currentPhoto + 1) % photos.length;
    renderCarousel();
});

// Clic en miniaturas
thumbs?.addEventListener("click", (e) => {
    const imgOrBtn = e.target.closest("img, button");
    if (!imgOrBtn) return;

    const allThumbs = Array.from(thumbs.querySelectorAll("img, button"));
    const index = allThumbs.indexOf(imgOrBtn);

    if (index !== -1) {
        currentPhoto = index;
        renderCarousel();
    }
});

// Navegación con teclado
document.addEventListener("keydown", (e) => {
    if (photos.length === 0) return;
    if (e.key === "ArrowLeft") {
        currentPhoto = (currentPhoto - 1 + photos.length) % photos.length;
        renderCarousel();
    } else if (e.key === "ArrowRight") {
        currentPhoto = (currentPhoto + 1) % photos.length;
        renderCarousel();
    }
});

// Render inicial
renderCarousel();
