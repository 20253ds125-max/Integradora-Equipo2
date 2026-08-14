const photos = typeof fotosDesdeBD !== "undefined" && fotosDesdeBD.length > 0
    ? fotosDesdeBD
    : [];

let currentPhoto = 0;

const $ = (q) => document.querySelector(q);

const carouselMain  = $("[data-carousel-main]");
const carouselBg    = $("[data-carousel-bg]");
const carouselCount = $("[data-carousel-count]");
const thumbs        = $("[data-carousel-thumbs]");
const carouselEl    = document.querySelector(".carousel");

const THUMB_TRANSFORM = "w_200,h_120,c_fill,g_auto,q_auto:good,f_auto";

function optimizarUrl(url, transform) {
    if (typeof url !== "string") return url;
    const marker = "/upload/";
    const idx = url.indexOf(marker);
    if (idx === -1) return url;
    const before = url.slice(0, idx + marker.length);
    let after = url.slice(idx + marker.length);

    const versionMatch = after.match(/^v\d+\//);
    if (!versionMatch) {
        const slashIdx = after.indexOf("/");
        if (slashIdx !== -1 && after.slice(0, slashIdx).includes(",")) {
            after = after.slice(slashIdx + 1);
        }
    }
    return `${before}${transform}/${after}`;
}

function medirBanner() {
    const rect = carouselEl.getBoundingClientRect();
    const dpr = Math.min(window.devicePixelRatio || 1, 2);
    return {
        w: Math.min(1600, Math.max(1, Math.round(rect.width * dpr))),
        h: Math.min(900,  Math.max(1, Math.round(rect.height * dpr))),
    };
}

function renderCarousel() {
    if (!carouselMain || photos.length === 0) return;

    const { w, h } = medirBanner();

    const bgTransform   = `w_${w},h_${h},c_fill,g_auto,q_auto:eco,f_auto`;
    const mainTransform = `q_auto:good,f_auto`;

    const mainSrc = optimizarUrl(photos[currentPhoto], mainTransform);
    const bgSrc   = optimizarUrl(photos[currentPhoto], bgTransform);

    carouselMain.style.opacity = "0";

    const showImage = () => {
        carouselMain.style.opacity = "1";
    };

    carouselMain.onload = showImage;

    carouselMain.src = mainSrc;


    if (carouselMain.complete) {
        showImage();
    }

    if (carouselBg) {
        carouselBg.src = bgSrc;
    }

    if (carouselCount) {
        carouselCount.textContent = `${currentPhoto + 1} / ${photos.length}`;
    }


    thumbs?.querySelectorAll("img").forEach((el, i) => {
        el.style.opacity = i === currentPhoto ? "1" : "0.5";
        el.style.borderColor = i === currentPhoto ? "var(--clay)" : "transparent";
    });
}


thumbs?.querySelectorAll("img").forEach((img) => {
    img.src = optimizarUrl(img.getAttribute("src"), THUMB_TRANSFORM);
});


let resizeTimer;
window.addEventListener("resize", () => {
    clearTimeout(resizeTimer);
    resizeTimer = setTimeout(renderCarousel, 200);
});


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
    const imgOrBtn = e.target.closest("img, button");
    if (!imgOrBtn) return;

    const allThumbs = Array.from(thumbs.querySelectorAll("img, button"));
    const index = allThumbs.indexOf(imgOrBtn);

    if (index !== -1) {
        currentPhoto = index;
        renderCarousel();
    }
});

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

renderCarousel();
document.addEventListener("DOMContentLoaded", function () {
    const fechaInput = document.getElementById("fechaEvento");
    const btnVerificar = document.getElementById("btnVerificar");
    const btnAnadirCarrito = document.getElementById("btnAnadirCarrito");
    const mensajeDiv = document.getElementById("mensajeDisponibilidad");

    if (fechaInput) {
        ["change", "input"].forEach(nombreEvento => {
            fechaInput.addEventListener(nombreEvento, function () {
                const valor = this.value.trim();

                if (btnAnadirCarrito) {
                    btnAnadirCarrito.classList.add("disabled-link");
                }
                if (mensajeDiv) {
                    mensajeDiv.style.display = "none";
                }

                if (!valor) return;

                let fechaSeleccionada;

                if (valor.includes("-")) {
                    const partes = valor.split("-");
                    fechaSeleccionada = new Date(partes[0], partes[1] - 1, partes[2]);
                } else if (valor.includes("/")) {
                    const partes = valor.split("/");
                    fechaSeleccionada = new Date(partes[2], partes[1] - 1, partes[0]);
                } else {
                    fechaSeleccionada = new Date(valor);
                }

                if (isNaN(fechaSeleccionada.getTime())) return;

                const manana = new Date();
                manana.setDate(manana.getDate() + 1);
                manana.setHours(0, 0, 0, 0);

                if (fechaSeleccionada < manana) {
                    Swal.fire({
                        title: '¡Atención!',
                        text: 'La fecha del evento debe ser a partir del día de mañana. Por favor, selecciona una fecha válida.',
                        icon: 'warning',
                        confirmButtonText: 'Entendido',
                        confirmButtonColor: '#855221',
                        borderRadius: '12px'
                    });

                    this.value = "";
                }
            });
        });
    }

    if (btnVerificar && fechaInput) {
        btnVerificar.addEventListener("click", function () {

            const inputPublicacion = document.querySelector('input[name="idPublicacionEventos"]');
            const inputRecintoAdmin = document.querySelector('input[name="idRecinto"]');
            const idRecinto = inputPublicacion ? inputPublicacion.value : (inputRecintoAdmin ? inputRecintoAdmin.value : "");

            const fechaSeleccionada = fechaInput.value;

            if (!fechaSeleccionada) {
                Swal.fire({
                    title: '¡Atención!',
                    text: 'Por favor, selecciona una fecha primero.',
                    icon: 'info',
                    confirmButtonText: 'Entendido',
                    confirmButtonColor: '#855221',
                    borderRadius: '12px'
                });
                return;
            }

            const textoOriginal = btnVerificar.innerText;
            btnVerificar.innerText = "Verificando...";
            btnVerificar.disabled = true;

            const contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2)) || "";

            fetch(`${contextPath}/verificarFecha?fecha=${fechaSeleccionada}&idRecinto=${idRecinto}`)
                .then(response => response.json())
                .then(data => {
                    if (!data.disponible) {
                        mostrarMensaje("¡La fecha está disponible! Ya puedes añadir al carrito.", true);
                        if (btnAnadirCarrito) {
                            btnAnadirCarrito.classList.remove("disabled-link");
                        }
                    } else {
                        mostrarMensaje("Lo sentimos, esta fecha ya está ocupada.", false);
                        if (btnAnadirCarrito) {
                            btnAnadirCarrito.classList.add("disabled-link");
                        }
                    }
                })
                .catch(error => {
                    console.error("Error:", error);
                    mostrarMensaje("Error de conexión. Intenta de nuevo.", false);
                })
                .finally(() => {
                    btnVerificar.innerText = textoOriginal;
                    btnVerificar.disabled = false;
                });
        });
    }

    function mostrarMensaje(texto, esExito) {
        if (mensajeDiv) {
            mensajeDiv.innerText = texto;
            mensajeDiv.style.display = "block";
            mensajeDiv.className = esExito ? "status-message status-available" : "status-message status-unavailable";
        }
    }

    const descContent = document.getElementById("descContent");
    const descText = document.getElementById("descText");
    const btnToggleDesc = document.getElementById("btnToggleDesc");
    const descFade = document.getElementById("descFade");

    if (descContent && descText && btnToggleDesc && descFade) {

        const collapseHeight = 70;

        const checkDescriptionHeight = () => {
            const textHeight = descText.scrollHeight;

            if (textHeight > collapseHeight) {
                descContent.style.maxHeight = collapseHeight + "px";
                btnToggleDesc.style.display = "inline-block";
                descFade.style.display = "block";
            } else {
                descContent.style.maxHeight = "none";
                btnToggleDesc.style.display = "none";
                descFade.style.display = "none";
            }
        };

        checkDescriptionHeight();

        btnToggleDesc.addEventListener("click", function () {
            const isExpanded = descContent.classList.contains("expanded");

            if (isExpanded) {
                descContent.classList.remove("expanded");
                descContent.style.maxHeight = collapseHeight + "px";
                btnToggleDesc.innerText = "Leer más";
                descFade.style.display = "block";
            } else {
                descContent.classList.add("expanded");
                descContent.style.maxHeight = descText.scrollHeight + "px";
                btnToggleDesc.innerText = "Leer menos";
                descFade.style.display = "none";
            }
        });
    }
});
