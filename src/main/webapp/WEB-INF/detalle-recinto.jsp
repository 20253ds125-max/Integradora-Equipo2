<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta name="description" content="Detalle de recinto para eventos sociales en GEDS." />

    <title>Event Online | ${salonDetalles.nombre != null ? salonDetalles.nombre : 'Detalle del recinto'}</title>

    <link rel="preconnect" href="https://images.unsplash.com" />
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:wght@600;700&family=Plus+Jakarta+Sans:wght@400;600;700;800&display=swap" rel="stylesheet">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/detalle.css?v=6.2" />
</head>

<body>

<header class="site-header">
    <div class="brand-group">
        <a class="brand" href="${pageContext.request.contextPath}/">Event Online</a>
    </div>

    <nav class="top-nav" aria-label="Navegación principal">
        <a class="active" href="${pageContext.request.contextPath}/catalogo">Recintos</a>
        <a href="${pageContext.request.contextPath}/extraServices">Servicios</a>
        <a href="${pageContext.request.contextPath}/app/perfil">Perfil</a>
    </nav>

    <div class="header-actions">
        <a href="${pageContext.request.contextPath}/mi-carrito-de-compra">Carrito</a>
    </div>
</header>

<main>

    <c:if test="${not empty error}">
        <div style="background-color: #f8d7da; color: #721c24; padding: 10px; text-align: center; border-radius: 8px; margin-bottom: 20px;">
                ${error}
        </div>
    </c:if>

    <section class="gallery-section" aria-label="Galeria del recinto">
        <div class="carousel" data-carousel>
            <button class="carousel-control prev" type="button" data-carousel-prev aria-label="Foto anterior">‹</button>

            <%-- CAPA 1: Fondo difuminado (decorativo) --%>
            <img class="carousel-bg"
                 data-carousel-bg
                 src="${salonDetalles.fotos[0]}"
                 alt=""
                 aria-hidden="true" />

            <%-- CAPA 2: Imagen principal nítida y COMPLETA (sin recorte) --%>
            <img class="carousel-main"
                 data-carousel-main
                 src="${salonDetalles.fotos[0]}"
                 alt="Fotografía de ${salonDetalles.nombre != null ? salonDetalles.nombre : 'el recinto'}"
                 onerror="this.onerror=null; this.src='https://placehold.co/1200x675?text=Sin+Foto';" />

            <button class="carousel-control next" type="button" data-carousel-next aria-label="Foto siguiente">›</button>
            <div class="carousel-count" data-carousel-count></div>
        </div>

        <div class="thumbnail-row" data-carousel-thumbs>
            <c:forEach var="foto" items="${salonDetalles.fotos}">
                <img src="${foto}"
                     alt="Miniatura del recinto"
                     onerror="this.style.display='none';" />
            </c:forEach>
        </div>
    </section>

    <section class="detail-layout">

        <article class="venue-detail">
            <div class="title-row">
                <div>
                    <h1>${salonDetalles.nombre}</h1>
                    <p class="location">${salonDetalles.ubicacion}</p>
                </div>
            </div>

            <section class="content-block">
                <h2>Acerca del lugar</h2>
                <p style="white-space: pre-wrap;">${salonDetalles.descripcion}</p>
                <p><strong>Capacidad máxima:</strong> ${salonDetalles.capacidad} invitados.</p>
            </section>

            <section class="services-section">
                <h2>Servicios recomendados</h2>
                <div class="services-grid" id="randomServices">
                </div>
            </section>
        </article>

        <aside class="booking-panel" id="bookingPanel">
            <div class="price-row">
                <strong>$${salonDetalles.precio}</strong>
                <span>/ por evento</span>
                <button class="favorite-button" type="button" data-detail-favorite aria-label="Agregar a favoritos">♡</button>
            </div>

            <section>
                <h2>Comprobar disponibilidad</h2>

                <div class="availability-checker">
                    <label for="fechaEvento">Fecha de tu evento</label>
                    <input type="date" id="fechaEvento" name="fechaEvento" required />
                    <button type="button" id="btnVerificar" class="btn-check">
                        Verificar fecha
                    </button>
                </div>

                <!-- Contenedor para el mensaje de respuesta -->
                <div id="mensajeDisponibilidad" class="status-message"></div>
            </section>

            <div class="cost-list">
                <!-- Precios Calculados -->
                <p><span>Renta del recinto</span><strong>$${salonDetalles.precio}</strong></p>
                <p><span>Servicio de limpieza</span><strong>$150.00</strong></p>
                <p class="total"><span>Total</span><strong>$${salonDetalles.precio + 150}</strong></p>
            </div>

            <form id="formAnadirCarrito" action="${pageContext.request.contextPath}/carritoAgregar" method="post">
                <input type="hidden" name="idPublicacionEventos" value="${salonDetalles.idSalonEventos}" />
                <button id="btnAnadirCarrito" type="submit" class="special-button disabled-link" disabled>
                    Añadir al carrito
                </button>
            </form>

            <p class="panel-note">No se realizará ningún cargo todavía.</p>
        </aside>

    </section>

</main>

<script>
    const fotosDesdeBD = [
        <c:forEach var="foto" items="${salonDetalles.fotos}">
        "${foto}",
        </c:forEach>
    ];

    const precioBaseBD = ${salonDetalles.precio};
    const nombreRecintoBD = "${salonDetalles.nombre}";
</script>

<footer class="catalog-footer legal-only">&copy; 2026 Event Online Spaces. Todos los derechos reservados.</footer>

<script src="${pageContext.request.contextPath}/assets/js/detalle.js?v=6.2"></script>
<jsp:include page="alerts.jsp" />
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const fechaInput = document.getElementById("fechaEvento");
        const btnVerificar = document.getElementById("btnVerificar");
        const btnAnadirCarrito = document.getElementById("btnAnadirCarrito"); // Ahora sí lo encontrará
        const mensajeDiv = document.getElementById("mensajeDisponibilidad");

        if (fechaInput) {
            const manana = new Date();
            manana.setDate(manana.getDate() + 1);
            const yyyy = manana.getFullYear();
            const mm = String(manana.getMonth() + 1).padStart(2, '0');
            const dd = String(manana.getDate()).padStart(2, '0');
            fechaInput.setAttribute("min", `${yyyy}-${mm}-${dd}`);

            fechaInput.addEventListener("change", function() {
                // Validación de seguridad antes de usar classList
                if (btnAnadirCarrito) {
                    btnAnadirCarrito.classList.add("disabled-link");
                    btnAnadirCarrito.disabled = true;
                }
                if (mensajeDiv) {
                    mensajeDiv.style.display = "none";
                }
            });
        }

        if (btnVerificar && fechaInput) {
            btnVerificar.addEventListener("click", function () {
                const fechaSeleccionada = fechaInput.value;
                const idRecinto = "${salonDetalles.idSalonEventos}";

                if (!fechaSeleccionada) {
                    mostrarMensaje("Por favor, selecciona una fecha primero.", false);
                    return;
                }

                const textoOriginal = btnVerificar.innerText;
                btnVerificar.innerText = "Verificando...";
                btnVerificar.disabled = true;

                fetch(`${pageContext.request.contextPath}/verificarFecha?fecha=${fechaSeleccionada}&idRecinto=${idRecinto}`)
                    .then(response => response.json())
                    .then(data => {
                        if (!data.disponible) {
                            mostrarMensaje("¡La fecha está disponible! Ya puedes añadir al carrito.", true);
                            if (btnAnadirCarrito) {
                                btnAnadirCarrito.classList.remove("disabled-link");
                                btnAnadirCarrito.disabled = false;
                            }
                        } else {
                            mostrarMensaje("Lo sentimos, esta fecha ya está ocupada.", false);
                            if (btnAnadirCarrito) {
                                btnAnadirCarrito.classList.add("disabled-link");
                                btnAnadirCarrito.disabled = true;
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
    });
</script>
</body>
</html>