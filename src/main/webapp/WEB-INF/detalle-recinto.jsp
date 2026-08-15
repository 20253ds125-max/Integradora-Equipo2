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

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/detalle.css?v=6.4" />
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

        <!-- Contenedor necesario para que el menú flote en el lugar correcto -->
        <div class="dropdown-container">

            <!-- TU BOTÓN EXACTO -->
            <button class="icon-button menu-toggle" type="button" id="btnMenu" aria-label="Abrir menú">
                <span aria-hidden="true"></span>
            </button>

            <!-- El menú flotante -->
            <div class="dropdown-menu" id="menuDesplegable">
                <a href="carrito.jsp">
                    <svg viewBox="0 0 24 24" width="18" height="18" stroke="currentColor" stroke-width="2" fill="none" stroke-linecap="round" stroke-linejoin="round"><circle cx="9" cy="21" r="1"></circle><circle cx="20" cy="21" r="1"></circle><path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"></path></svg>
                    Carrito
                </a>

                <a href="contacto.jsp">
                    <svg viewBox="0 0 24 24" width="18" height="18" stroke="currentColor" stroke-width="2" fill="none" stroke-linecap="round" stroke-linejoin="round"><path d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z"></path><polyline points="22,6 12,13 2,6"></polyline></svg>
                    Contáctanos
                </a>

                <div class="dropdown-divider"></div>

                <a href="logout.jsp" class="logout-link">
                    <svg viewBox="0 0 24 24" width="18" height="18" stroke="currentColor" stroke-width="2" fill="none" stroke-linecap="round" stroke-linejoin="round"><path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"></path><polyline points="16 17 21 12 16 7"></polyline><line x1="21" y1="12" x2="9" y2="12"></line></svg>
                    Cerrar sesión
                </a>
            </div>

        </div>
    </div>
    </div>
</header>

<main>

    <c:if test="${not empty error}">
        <div style="background-color: #f8d7da; color: #721c24; padding: 10px; text-align: center; border-radius: 8px; margin-bottom: 20px;">
                ${error}
        </div>
    </c:if>

    <div class="back-navigation">
        <a href="${pageContext.request.contextPath}/catalogo" class="btn-back">
            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round">
                <line x1="19" y1="12" x2="5" y2="12"></line>
                <polyline points="12 19 5 12 12 5"></polyline>
            </svg>
            Regresar al catálogo
        </a>
    </div>

    <section class="gallery-section" aria-label="Galeria del recinto">
        <div class="carousel" data-carousel>
            <button class="carousel-control prev" type="button" data-carousel-prev aria-label="Foto anterior">‹</button>

            <img class="carousel-bg"
                 data-carousel-bg
                 src="${salonDetalles.fotos[0]}"
                 alt=""
                 aria-hidden="true" />

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

                <div id="descContent" class="desc-collapsible">
                    <p id="descText" style="white-space: pre-wrap; margin: 0; color: var(--muted); font-size: 1.18rem; line-height: 1.65;">${salonDetalles.descripcion}</p>
                    <div id="descFade" class="desc-fade"></div>
                </div>

                <button type="button" id="btnToggleDesc" class="btn-toggle-desc" style="display: none;">
                    <span>Leer más</span> ▾
                </button>

                <p style="margin-top: 18px;"><strong>Capacidad máxima:</strong> ${salonDetalles.capacidad} invitados.</p>
            </section>

            <section class="services-section">
                <h2>Servicios recomendados</h2>
                <div class="services-grid" id="randomServices">
                </div>
            </section>
        </article>

        <aside class="booking-panel" id="bookingPanel">

            <c:choose>

                <c:when test="${not empty sessionScope.UsuarioLog && sessionScope.UsuarioLog.rol eq 'ADMIN' && param.modo eq 'review'}">

                    <div class="price-row">
                        <strong>$${salonDetalles.precio}</strong>
                        <span>/ precio propuesto</span>
                    </div>

                    <div style="margin: 1.5rem 0; padding: 1rem; background: #f8f9fa; border-radius: 8px; border: 1px solid #e9ecef;">
                        <h3 style="margin-top: 0; font-size: 1.1rem; color: #333;">Modo Revisión Administrador</h3>
                        <p class="panel-note" style="margin-bottom: 15px;">
                            Verifica la información e imágenes del recinto antes de aprobar o rechazar la solicitud.
                        </p>

                        <div class="cost-list">
                            <p><span>Precio por evento</span><strong>$${salonDetalles.precio}</strong></p>
                            <p><span>Capacidad máxima</span><strong>${salonDetalles.capacidad} personas</strong></p>
                        </div>
                    </div>

                    <div style="display: flex; flex-direction: column; gap: 10px;">
                        <form action="${pageContext.request.contextPath}/AprobarRecintoServlet" method="POST" style="margin: 0;">
                            <input type="hidden" name="idRecinto" value="${salonDetalles.idSalonEventos}">
                            <button type="submit" class="special-button" style="background-color: #2e7d32; width: 100%; border: none; cursor: pointer;">
                                ✓ Aprobar Recinto
                            </button>
                        </form>

                        <form action="${pageContext.request.contextPath}/RechazarRecintoServlet" method="POST" style="margin: 0;">
                            <input type="hidden" name="idRecinto" value="${salonDetalles.idSalonEventos}">
                            <button type="submit" class="special-button" style="background-color: #c62828; width: 100%; border: none; cursor: pointer;">
                                ✕ Rechazar Recinto
                            </button>
                        </form>

                        <a href="${pageContext.request.contextPath}/adminRecintos"
                           style="text-align: center; margin-top: 8px; color: #666; text-decoration: underline; font-size: 0.9rem;">
                            ← Volver al Panel
                        </a>
                    </div>
                </c:when>

                <c:otherwise>
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

                        <div id="mensajeDisponibilidad" class="status-message"></div>
                    </section>

                    <div class="cost-list">
                        <p><span>Renta del recinto</span><strong>$${salonDetalles.precio}</strong></p>
                        <p class="total"><span>Total</span><strong>$${salonDetalles.precio}</strong></p>
                    </div>

                    <form method="post" action="${pageContext.request.contextPath}/carritoAgregar">
                        <input type="hidden" name="idPublicacionEventos" value="${salonDetalles.idSalonEventos}">
                        <button type="submit" id="btnAnadirCarrito" class="special-button disabled-link">
                            Añadir al carrito
                        </button>
                    </form>
                    <p class="panel-note">No se realizará ningún cargo todavía.</p>
                </c:otherwise>
            </c:choose>

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

<script src="${pageContext.request.contextPath}/assets/js/detalle.js?v=6.4"></script>
<jsp:include page="alerts.jsp" />
<script>
    document.addEventListener('DOMContentLoaded', function() {
        const btnMenu = document.getElementById('btnMenu');
        const menuDesplegable = document.getElementById('menuDesplegable');

        if (btnMenu && menuDesplegable) {

            // 1. Abrir/Cerrar al hacer clic en el botón del perfil
            btnMenu.addEventListener('click', function(evento) {
                // Esto evita que el clic "traspase" y cierre el menú de inmediato
                evento.stopPropagation();
                menuDesplegable.classList.toggle('show');
            });

            // 2. Cerrar el menú si haces clic en cualquier otro lado de la página
            document.addEventListener('click', function(evento) {
                // Si el clic NO fue dentro del menú ni en el botón...
                if (!menuDesplegable.contains(evento.target) && !btnMenu.contains(evento.target)) {
                    // ...entonces escóndelo
                    menuDesplegable.classList.remove('show');
                }
            });
        }
    });
</script>
</body>
</html>