<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!doctype html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta name="description" content="Catalogo de recintos para eventos sociales en Mexico." />
    <title>Event Online | Catálogo de recintos</title>
    <link rel="preconnect" href="https://images.unsplash.com" />
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link
            href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:wght@600;700&family=Montserrat:wght@400;500;600;700;800&display=swap"
            rel="stylesheet"
    />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/catalogo.css" />
</head>
<body>
<header class="site-header">
    <div class="brand-group">

        <a class="brand" href="${pageContext.request.contextPath}/index.jsp">Event Online</a>
    </div>

    <nav class="top-nav" aria-label="Navegación">
        <a class="active" href="app/catalogo">Recintos</a>
        <a href="app/extraServices">Servicios</a>
        <a href="app/perfil">Perfil</a>
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

        <div class="venue-masonry" id="catalogResults" data-catalog-results></div>

        <div class="load-more-wrap">
            <button class="load-more" type="button" data-load-more>Mostrar más espacios</button>
        </div>
    </section>
</main>

<footer class="catalog-footer legal-only">&copy; 2026 Event Online Spaces. Todos los derechos reservados.</footer>

<script src="${pageContext.request.contextPath}/assets/js/cart.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/catalogo.js"></script>
</body>
</html>






