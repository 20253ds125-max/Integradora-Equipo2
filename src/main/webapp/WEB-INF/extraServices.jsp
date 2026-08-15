<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Servicios Extra | Event Online</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/services.css?v=1.2.1">

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



<!-- para filtros -->
<script>
    document.addEventListener("DOMContentLoaded", () => {
        // 1. Localiza el campo de texto del buscador
        const inputBusqueda = document.querySelector('input[type="text"], input[type="search"]');

        // 2. Localiza los botones o enlaces de las categorías
        const botonesFiltro = document.querySelectorAll('button, a');

        // Categorías permitidas para filtrar
        const categoriasValidas = ["Todos", "Música", "Catering", "Decoración", "Fotografía", "Video", "DJ"];

        botonesFiltro.forEach(boton => {
            const texto = boton.innerText.trim();

            if (categoriasValidas.includes(texto)) {
                boton.addEventListener('click', (e) => {
                    e.preventDefault();

                    const busqueda = inputBusqueda ? inputBusqueda.value.trim() : '';

                    // Redirige enviando la categoría y el texto del buscador al Servlet
                    window.location.href = '${pageContext.request.contextPath}/extraServices?categoria='
                        + encodeURIComponent(texto)
                        + '&q=' + encodeURIComponent(busqueda);
                });
            }
        });

        // Escucha la tecla Enter en la barra de búsqueda
        if (inputBusqueda) {
            inputBusqueda.addEventListener('keypress', (e) => {
                if (e.key === 'Enter') {
                    e.preventDefault();
                    const busqueda = inputBusqueda.value.trim();
                    window.location.href = '${pageContext.request.contextPath}/extraServices?q=' + encodeURIComponent(busqueda);
                }
            });
        }
    });

    //menu desplegable WUUU :)
    document.addEventListener("DOMContentLoaded", () => {
        const menuToggle = document.querySelector("[data-menu-toggle]");
        const mobileNav = document.querySelector("[data-mobile-nav]");

        if (menuToggle && mobileNav) {
            menuToggle.addEventListener("click", (e) => {
                e.stopPropagation();
                mobileNav.classList.toggle("open");
                document.body.classList.toggle("menu-open");
            });

            mobileNav.addEventListener("click", (e) => {
                if (e.target.tagName === "A") {
                    mobileNav.classList.remove("open");
                    document.body.classList.remove("menu-open");
                }
            });

            document.addEventListener("click", (e) => {
                if (!mobileNav.contains(e.target) && !menuToggle.contains(e.target)) {
                    mobileNav.classList.remove("open");
                    document.body.classList.remove("menu-open");
                }
            });
            const cerrarSe = document.getElementById("cerrarSe");

            if(cerrarSe){
                cerrarSe.addEventListener('click',function (e){
                    e.preventDefault();

                    const direccion = this.getAttribute("href");
                    Swal.fire({
                        title: '¿Cerrar sesión?',
                        text: '¿Estás seguro de que deseas salir de tu cuenta?',
                        icon: 'warning',
                        showCancelButton: true,
                        confirmButtonText: 'Sí, salir',
                        cancelButtonText: 'Cancelar',
                        confirmButtonColor: '#855221',
                        cancelButtonColor: '#6c757d',
                        borderRadius: '12px'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            window.location.href = direccion;
                        }
                    });
                });
            }
        }
    });
</script>

<footer class="main-footer">© 2026 Event Online. Todos los derechos reservados.</footer>
<jsp:include page="alerts.jsp" />
</body>
</html>