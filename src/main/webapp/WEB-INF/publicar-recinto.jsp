<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%
jakarta.servlet.http.HttpSession sesion = request.getSession(false);
if (sesion == null || sesion.getAttribute("UsuarioLog") == null) {
request.setAttribute("error", "Por favor, inicia sesión para publicar tu recinto.");
request.getRequestDispatcher("login.jsp").forward(request, response);
return;
}
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta name="description" content="Publica un recinto nuevo en GEDS." />
    <title>Event Online | Publicar recinto</title>
    <link rel="preconnect" href="https://images.unsplash.com" />
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:wght@600;700&family=Plus+Jakarta+Sans:wght@400;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/publicar.css?v=6.4" />
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
        <a href="${pageContext.request.contextPath}/cerrarSesion" id="cerrarSe">Cerrar sesion</a>
    </c:if>

</nav>


<main class="publish-shell">
    <section class="publish-intro">
        <h1>Publica tu recinto</h1>
        <p>Comparte la elegancia de tu espacio con nuestra comunidad curada. Completa los detalles para iniciar la revision del concierge.</p>
    </section>

    <form class="publish-layout" data-publish-form method="post" action="${pageContext.request.contextPath}/publicar-recinto" enctype="multipart/form-data">
        <section class="form-stack">
            <article class="panel">
                <h2>Identidad del recinto</h2>
                <label>
                    <span>Nombre del recinto</span>
                    <input type="text" name="venueName" placeholder="Ej. Villa d'Este Pavilion" required />
                </label>
                <label>
                    <span>Ubicación</span>
                    <input type="text" name="location" placeholder="Calle, ciudad, estado" required />
                </label>
                <label>
                    <span>Descripción</span>
                    <textarea name="description" placeholder="Describe la historia, estilo arquitectonico y atmosfera..." required></textarea>
                </label>
            </article>

            <article class="panel">
                <h2>Capacidad</h2>
                <div class="capacity-row">
                    <label class="mini-card">
                        <span>Capacidad máxima del salón: </span>
                        <input type="number" name="seated" min="0" placeholder="0" />
                    </label>

                        <label class="mini-card">

                            <span>Precio </span>

                            <div class="price-input">
                                <span>$</span>
                                <input
                                        type="number"
                                        name="precio"
                                        min="0"
                                        step="100"
                                        placeholder="0"
                                />
                            </div>
                            <small>Por evento</small>
                        </label>
                </div>
            </article>
        </section>

        <aside class="publish-aside">
            <article class="panel photos-panel">
                <h2>Fotos del recinto</h2>
                <label class="upload-box">
                    <input type="file" accept="image/png,image/jpeg" multiple data-photo-input name="photos"/>
                    <strong>Click o arrastra para subir</strong>
                    <span>JPEG o PNG de alta resolucion</span>
                </label>
                <div class="photo-grid" data-photo-grid>
                    <div class="photo-thumb filled"></div>
                    <div class="photo-thumb"></div>
                    <div class="photo-thumb"></div>
                </div>
                <button class="primary-button" type="submit">Enviar a revisión</button>
                <p class="aside-note">Tu publicación será revisada por el equipo de planeación en 24 a 48 horas.</p>
            </article>
            <article class="tip">
                <h2>Tip concierge</h2>
                <p>Los recintos con fotografía profesional y descripción clara reciben mas solicitudes premium.</p>
            </article>
        </aside>
    </form>
    <p class="form-status" data-form-status role="status" aria-live="polite"></p>
</main>

<footer class="catalog-footer legal-only">&copy; 2026 Event Online Spaces. Todos los derechos reservados.</footer>

<script src="${pageContext.request.contextPath}/assets/js/publicar.js?v=1.1.1"></script>
<jsp:include page="alerts.jsp" />
</body>
</html>