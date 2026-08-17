<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta name="description" content="Event Online, plataforma para descubrir y reservar espacios para eventos sociales en México." />
    <title>Event Online | Gestión de eventos sociales</title>

    <link rel="preconnect" href="https://images.unsplash.com" />
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />

    <link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:wght@600;700&family=Montserrat:wght@400;500;600;700;800&display=swap" rel="stylesheet"/>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css?v=1.0.1" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>

<body>
<header class="site-header" data-header>
    <a class="brand" href="${pageContext.request.contextPath}/" aria-label="Ir al inicio">Event Online</a>

    <nav class="desktop-nav" aria-label="Navegación principal">
        <a href="${pageContext.request.contextPath}/catalogo">Recintos</a>
        <a href="${pageContext.request.contextPath}/extraServices">Servicios</a>
        <a href="${pageContext.request.contextPath}/app/perfil">Perfil</a>
    </nav>

    <div class="header-actions">
        <button class="icon-button menu-toggle" type="button" data-menu-toggle aria-label="Abrir menú">
            <span aria-hidden="true"></span>
        </button>
    </div>
</header>

<nav class="mobile-nav" data-mobile-nav aria-label="Navegación móvil">

    <c:if test="${empty sessionScope.UsuarioLog}">
    <a href="${pageContext.request.contextPath}/app/login">Iniciar sesión o registrarte</a>
    </c:if>
    <a href="${pageContext.request.contextPath}/contacto-equipo">Contacta al equipo</a>
    <c:if test="${sessionScope.UsuarioLog.rol eq 'ADMIN' }">
    <a href="${pageContext.request.contextPath}/adminRecintos">Administrador</a>
    </c:if>
    <c:if test="${not empty sessionScope.UsuarioLog}">
        <a href="${pageContext.request.contextPath}/mi-carrito-de-compra" >Carrito</a>
    </c:if>
    <c:if test="${not empty sessionScope.UsuarioLog}">
        <a href="${pageContext.request.contextPath}/cerrarSesion" id="cerrarSe" class="cerrar">Cerrar sesion</a>
    </c:if>
</nav>

<main>

    <section class="hero" id="inicio" aria-label="Búsqueda de recintos">
        <div class="hero-media" role="img" aria-label="Recinto moderno iluminado al atardecer"></div>
        <div class="hero-content">
            <h1>Descubre recintos extraordinarios en México</h1>
            <p>
                Explora espacios seleccionados para bodas, celebraciones y reuniones que merecen
                algo realmente especial.
            </p>

            <form action="${pageContext.request.contextPath}/bus" class="search-panel" data-hero-search method="get">
                <div class="field">
                    <label for="place">Dónde</label>
                    <div class="input-wrapper">
                        <input
                                type="text"
                                id="lugar"
                                name="lugar"
                                placeholder="Ciudad, playa, hacienda..."
                                autocomplete="off"
                        />
                    </div>
                </div>

                <div class="field">
                    <label for="calendar">Cuándo</label>
                    <div class="input-wrapper">
                        <input
                                type="text"
                                id="calendar"
                                name="fecha"
                                placeholder="Selecciona fecha" />
                    </div>
                </div>

                <div class="field">
                    <label for="guests">Invitados</label>
                    <div class="input-wrapper">
                        <input
                                type="number"
                                id="invitados"
                                name="invitados"
                                min="1"
                                max="1500"
                                placeholder="Cantidad"
                        />
                    </div>
                </div>

                <button type="submit" class="search-btn" aria-label="Buscar">
                    <i class="fa-solid fa-magnifying-glass" style="color: #4f403b !important;"></i>
                    <span style="color: #4f403b !important;">Buscar</span>
                </button>
            </form>
        </div>
    </section>


    <section class="section featured-section" id="recintos">
        <div class="section-container">
            <div class="section-heading">
                <p class="eyebrow">Recintos destacados</p>
                <h2>Espacios excepcionales dignos de celebrarse</h2>
            </div>


            <div class="featured-grid" data-featured></div>
        </div>
    </section>

    <section class="about-section" id="nosotros">
        <div class="section-container">
            <div class="about-grid">
                <div class="about-copy">
                    <p class="eyebrow gold">Sobre nosotros</p>

                    <h2>Simplificamos la gestión y reserva de espacios para eventos</h2>

                    <p>
                        En Event Online conectamos personas con los mejores recintos para
                        bodas, fiestas, reuniones y celebraciones especiales en México.
                        Nuestra plataforma permite descubrir, comparar y reservar espacios
                        de manera rápida, segura y organizada.
                    </p>

                    <p>
                        Ayudamos a anfitriones, organizadores y empresas a encontrar el
                        lugar ideal mientras optimizamos la administración de reservas,
                        disponibilidad y atención personalizada.
                    </p>

                    <div class="button-row">
                        <a class="primary-button" href="${pageContext.request.contextPath}/catalogo">Explorar recintos</a>
                        <a class="ghost-button" href="${pageContext.request.contextPath}/contacto-equipo">Contactar equipo</a>
                    </div>
                </div>

                <div class="about-media">
                    <img
                            src="https://images.unsplash.com/photo-1511578314322-379afb476865?auto=format&fit=crop&w=1200&q=85"
                            alt="Equipo organizando un evento"
                    />

                    <article class="about-card">
                        <h3>Gestión inteligente</h3>
                        <p>
                            Reservas y organización en un solo lugar. Centralizamos la búsqueda de recintos, administración de
                            eventos y contacto con clientes para ofrecer una experiencia
                            moderna y eficiente.
                        </p>
                    </article>
                </div>
            </div>

            <div class="contact-banner">
                <div class="contact-title">
                    <h3>Contáctanos</h3>
                    <p>Estamos a su disposición para cualquier asunto que ocurra</p>
                </div>
                <div class="contact-info">
                    <p><strong>Horarios:</strong> Lunes a sábado 9am-16pm</p>
                    <p><strong>Email:</strong> eventonline@gmail.com</p>
                    <p><strong>Teléfono:</strong> 5519873454</p>
                </div>
            </div>
        </div>
    </section>
</main>

<footer class="site-footer legal-only">
    &copy; 2026 Event Online Spaces. Todos los derechos reservados.
</footer>

<dialog class="search-dialog" data-search-dialog>
    <form method="dialog">
        <button class="close-button" type="submit" aria-label="Cerrar">&times;</button>
        <p class="eyebrow">Búsqueda rápida</p>
        <h2>Encuentra tu siguiente espacio</h2>
        <label>
            <span>Destino o tipo de evento</span>
            <input type="search" placeholder="Ej. Boda en Valle de Bravo" />
        </label>
        <button class="primary-button" type="submit">Buscar recintos</button>
    </form>
</dialog>

<jsp:include page="alerts.jsp" />
<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
<script src="${pageContext.request.contextPath}/assets/js/main.js?v=1.1.2"></script>
</body>
</html>