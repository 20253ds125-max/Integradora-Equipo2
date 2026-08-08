<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Servicios Extra | Event Online</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/services.css">

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:wght@500;600;700&family=Montserrat:wght@400;500;600;700&display=swap" rel="stylesheet">
</head>
<body>

<header class="catalog-header">
    <div class="brand-group">
        <a class="brand" href="${pageContext.request.contextPath}/">Event Online</a>
    </div>

    <nav class="top-nav" aria-label="Navegación">
        <a href="${pageContext.request.contextPath}/catalogo">Recintos</a>
        <a class="active" href="${pageContext.request.contextPath}/extraServices">Servicios</a>
        <a href="${pageContext.request.contextPath}/app/perfil">Perfil</a>
    </nav>

    <div class="header-actions">
        <a class="host-button" href="${pageContext.request.contextPath}/app/publicarServicio">Publicar servicio</a>
    </div>
</header>

<section class="catalogo-servicios">
    <aside class="sidebar">
        <input type="text" id="buscador" placeholder="Buscar servicio...">

        <div class="grupo-filtro">
            <h3>Categoría</h3>
            <div class="filter-options">
                <button class="filtro activo" data-categoria="todos" type="button">Todos</button>
                <button class="filtro" data-categoria="musica" type="button">Música</button>
                <button class="filtro" data-categoria="catering" type="button">Catering</button>
                <button class="filtro" data-categoria="decoracion" type="button">Decoración</button>
                <button class="filtro" data-categoria="foto" type="button">Fotografía</button>
                <button class="filtro" data-categoria="video" type="button">Video</button>
                <button class="filtro" data-categoria="dj" type="button">DJ</button>
            </div>
        </div>
    </aside>

    <main class="contenido">
        <div class="encabezado">
            <h1>Servicios Extra</h1>
            <p>Complementa tu evento con experiencias únicas</p>
        </div>

        <div class="grid-servicios">
            <c:choose>
                <c:when test="${empty catalogoServicios}">
                    <p class="empty-state">No encontramos servicios disponibles en este momento.</p>
                </c:when>

                <c:otherwise>
                    <c:forEach var="servicio" items="${catalogoServicios}">
                        <article class="card">
                            <div class="imagen-container">
                                <img src="${servicio.urlFoto}" alt="${servicio.nombreServicio}">
                                <button class="btn-favorito" type="button">♡</button>
                            </div>

                            <div class="card-body">
                                <span class="badge">${servicio.tipo}</span>
                                <h3>${servicio.nombreServicio}</h3>
                                <p>${servicio.descripcion}</p>

                                <div class="card-footer">
                                    <strong>
                                        <fmt:formatNumber value="${servicio.precio}" type="currency" currencySymbol="$" maxFractionDigits="2"/>
                                    </strong>

                                    <div class="acciones-card">
                                        <form action="${pageContext.request.contextPath}/agregarCarrito" method="POST" style="margin: 0;">
                                            <input type="hidden" name="idServicio" value="${servicio.idServicio}">
                                            <button class="btn-agregar" type="submit">
                                                Añadir al carrito
                                            </button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </article>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </main>
</section>

<footer class="main-footer">© 2026 Event Online. Todos los derechos reservados.</footer>
<jsp:include page="alerts.jsp" />
</body>
</html>