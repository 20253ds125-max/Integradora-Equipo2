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

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/detalle.css?v=1.1" />
</head>

<body>

<header class="site-header">
    <div class="brand-group">
        <a class="brand" href="${pageContext.request.contextPath}/">Event Online</a>
    </div>

    <nav class="top-nav" aria-label="Navegación principal">
        <a class="active" href="${pageContext.request.contextPath}/catalogo">Recintos</a>
        <a href="${pageContext.request.contextPath}/app/extraServices">Servicios</a>
        <a href="${pageContext.request.contextPath}/app/perfil">Perfil</a>
    </nav>

    <div class="header-actions">
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
            <button class="carousel-control prev" type="button" data-carousel-prev>‹</button>

            <img data-carousel-image src="${salonDetalles.fotos[0]}" alt="Fotografia principal del recinto" onerror="this.onerror=null; this.src='https://placehold.co/800x600?text=Sin+Foto';"/>

            <button class="carousel-control next" type="button" data-carousel-next>›</button>
            <div class="carousel-count" data-carousel-count></div>
        </div>

        <div class="thumbnail-row" data-carousel-thumbs>
            <c:forEach var="foto" items="${salonDetalles.fotos}">
                <img src="${foto}" alt="Miniatura" style="height: 60px; border-radius: 4px; cursor: pointer; object-fit: cover;" onerror="this.style.display='none';" />
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
                <button class="favorite-button" type="button" data-detail-favorite>♡</button>
            </div>

            <section>
                <h2>Selecciona tu fecha</h2>
                <div class="calendar-card">
                    <div class="calendar-head">
                        <strong>Octubre 2026</strong>
                        <div>
                            <button type="button">‹</button>
                            <button type="button">›</button>
                        </div>
                    </div>
                    <div class="calendar-grid">
                        <span>L</span><span>M</span><span>M</span><span>J</span><span>V</span><span>S</span><span>D</span>
                        <button type="button">29</button>
                        <button type="button">30</button>
                        <button type="button">1</button>
                        <button type="button">2</button>
                        <button type="button">3</button>
                        <button type="button">4</button>
                        <button type="button">5</button>
                    </div>
                </div>
            </section>

            <label class="select-field">
                <span>Duración</span>
                <select>
                    <option>Día completo (8:00 AM - 10:00 PM)</option>
                    <option>Medio día</option>
                    <option>Evento nocturno</option>
                </select>
            </label>

            <section class="guest-control">
                <span>Invitados</span>
                <div>
                    <button type="button" data-guest-minus>-</button>
                    <strong><span data-guest-count>25</span> guests</strong>
                    <button type="button" data-guest-plus>+</button>
                </div>
            </section>

            <div class="cost-list">
                <!-- Precios Calculados -->
                <p><span>Renta del recinto</span><strong>$${salonDetalles.precio}</strong></p>
                <p><span>Servicio de limpieza</span><strong>$150.00</strong></p>
                <p class="total"><span>Total</span><strong>$${salonDetalles.precio + 150}</strong></p>
            </div>

            <a class="special-button" href="${pageContext.request.contextPath}/carrito">
                Añadir al carrito
            </a>

            <p class="panel-note">No se realizará ningún cargo todavía.</p>
        </aside>

    </section>

</main>

<footer class="rights-footer">
    &copy; 2026 Event Online Spaces. Todos los derechos reservados.
</footer>


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

<script src="${pageContext.request.contextPath}/assets/js/detalle.js"></script>
</body>
</html>
