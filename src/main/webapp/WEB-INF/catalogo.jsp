<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta name="description" content="Catalogo de recintos para eventos sociales en Mexico." />
    <title>Event Online | Catálogo de recintos</title>
    <link rel="preconnect" href="https://images.unsplash.com" />
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:wght@600;700&family=Plus+Jakarta+Sans:wght@400;600;700;800&display=swap" rel="stylesheet">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/catalogo.css?v=1.1" />
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
        <a class="host-button" href="${pageContext.request.contextPath}/app/publicar-recinto">Publicar recinto</a>
    </div>
</header>

<main class="catalog-shell">
    <aside class="filters-panel" data-filters-panel>
        <div class="filters-title">
            <h1>Filtros</h1>
            <button class="close-filters" type="button" data-close-filters aria-label="Cerrar filtros">&times;</button>
        </div>

        <label class="search-field">
            <span class="sr-only">Buscar ciudad o region</span>
            <input type="search" placeholder="Buscar en ciudad o región" data-city-search />
            <span aria-hidden="true">Ubicación</span>
        </label>

        <section class="filter-block" aria-labelledby="eventTypeTitle">

            <div class="pill-group" data-event-filters>
            </div>
        </section>

        <section class="filter-block" aria-labelledby="priceTitle">
            <div class="price-grid" data-price-filters>
                <button type="button" data-price="150">Hasta $150</button>
                <button type="button" data-price="500">Hasta $500</button>
                <button type="button" data-price="900">Hasta $900</button>
                <button type="button" data-price="2000">$900+</button>
            </div>
        </section>

        <section class="filter-block" aria-labelledby="capacityTitle">
            <h2 id="capacityTitle">Capacidad</h2>
            <div class="capacity-grid" data-capacity-filters>
                <button type="button" data-capacity="50">1-50 invitados</button>
                <button type="button" data-capacity="150">51-150 invitados</button>
                <button type="button" data-capacity="300">151-300 invitados</button>
                <button type="button" data-capacity="999">300+ invitados</button>
            </div>
        </section>


    </aside>

    <section class="catalog-content">
        <div class="catalog-heading">
            <div>
                <h1>Descubre recintos</h1>
                <p>Espacios curados en Mexico para cada ocasión</p>
            </div>

        </div>

        <div class="venue-masonry" id="catalogResults" data-catalog-results>

            <c:if test="${empty catalogo}">
                <div class="empty-state" style="grid-column: 1 / -1;">
                    <h2>No se encontraron recintos</h2>
                    <p>Por el momento no hay recintos disponibles en el catálogo.</p>
                </div>
            </c:if>

            <c:forEach var="salon" items="${catalogo}">
                <article class="catalog-card">

                    <div class="card-image">
                        <img src="${not empty salon.fotoPrincipal ? fn:trim(salon.fotoPrincipal) : 'https://via.placeholder.com/400x240?text=Sin+Foto'}"
                             alt="Foto de ${salon.nombre}"
                             onerror="this.src='https://via.placeholder.com/400x240?text=Sin+Foto';" />

                        <form action="${pageContext.request.contextPath}/app/favoritos"
                              method="post"
                              class="favorite-form">

                            <input
                                    type="hidden"
                                    name="idRecinto"
                                    value="${salon.idSalonEventos}">

                            <button class="favorite-button"
                                    type="submit"
                                    aria-label="Guardar ${salon.nombre}">

                                <c:choose>

                                    <c:when test="${salon.favorito}">
                                        &#9829;
                                    </c:when>

                                    <c:otherwise>
                                        &#9825;
                                    </c:otherwise>

                                </c:choose>

                            </button>

                        </form>
                    </div>

                    <div class="card-body">
                        <div class="card-title-row">
                            <h2>
                                <a href="${pageContext.request.contextPath}/detalleRecinto?id=${salon.idSalonEventos}">
                                        ${salon.nombre}
                                </a>
                            </h2>
                        </div>

                        <p class="location">
                            Ubicación: ${salon.ubicacion}
                        </p>

                        <div class="card-divider"></div>

                        <div class="card-footer">
                            <div class="footer-info-row">
                        <span class="capacity">
                            Hasta ${salon.capacidad} invitados
                        </span>

                                <strong class="price">
                                    <fmt:formatNumber value="${salon.precio}" type="currency" currencySymbol="$" maxFractionDigits="0"/>
                                    <span>/evento</span>
                                </strong>
                            </div>

                            <div class="card-actions">
                                <a class="details-link"
                                   href="${pageContext.request.contextPath}/detalleRecinto?id=${salon.idSalonEventos}">
                                    Ver detalles
                                </a>

                                <button class="cart-link"
                                        type="button"
                                        data-cart-venue="${salon.idSalonEventos}">
                                    Añadir al carrito
                                </button>
                            </div>
                        </div>
                    </div>
                </article>
            </c:forEach>

        </div>

        <div class="load-more-wrap">
            <button class="load-more" type="button" data-load-more>Mostrar más espacios</button>
        </div>
    </section>
</main>

<footer class="catalog-footer legal-only">&copy; 2026 Event Online Spaces. Todos los derechos reservados.</footer>

<jsp:include page="alerts.jsp" />

</body>
</html>






