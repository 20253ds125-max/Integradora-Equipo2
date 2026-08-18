<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!DOCTYPE html>
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

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/catalogo.css?v=1.2.3" />
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
        <a class="host-button" href="${pageContext.request.contextPath}/app/publicar-recinto">Publicar recinto</a>
        <c:if test="${not empty sessionScope.UsuarioLog}">
            <a href="${pageContext.request.contextPath}/mi-carrito-de-compra"
               aria-label="Carrito de compras"
               style="display: inline-flex; align-items: center; justify-content: center; width: 40px; height: 40px; color: var(--ink, #222); text-decoration: none;">
                <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <circle cx="9" cy="21" r="1"></circle>
                    <circle cx="20" cy="21" r="1"></circle>
                    <path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"></path>
                </svg>
            </a>
        </c:if>
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
        <a href="${pageContext.request.contextPath}/cerrarSesion" id="cerrarSe" class="cerrar">Cerrar sesion</a>
    </c:if>
</nav>

<main class="catalog-shell">
    <aside class="filters-panel" data-filters-panel>
        <div class="filters-title">
            <h1>Filtros</h1>
            <button class="close-filters" type="button" data-close-filters aria-label="Cerrar filtros">&times;</button>
        </div>

        <!-- Botón para restablecer todos los filtros -->
        <div style="margin-bottom: 1rem;">
            <button type="button" id="btnTodos" class="btn-todos" style="width: 100%; padding: 0.5rem; cursor: pointer;">
                Todos los recintos
            </button>
        </div>

        <label class="search-field">
            <span class="sr-only">Buscar ciudad o region</span>
            <input type="search" placeholder="Buscar en ciudad o región" data-city-search />
            <span aria-hidden="true">Ubicación</span>
        </label>

        <!-- Contenedor del Tag dinámico para la búsqueda activa -->
        <div id="activeTagSearch" class="active-tag-container" style="display: none; margin-top: 0.5rem; align-items: center; gap: 0.5rem;">
            <span class="tag-label" style="background: #e0e0e0; padding: 0.2rem 0.6rem; border-radius: 12px; font-size: 0.85rem; display: inline-flex; align-items: center; gap: 0.4rem;">
                <span id="activeTagText"></span>
                <button type="button" id="btnBorrarTagSearch" style="background: none; border: none; font-weight: bold; cursor: pointer; line-height: 1;" aria-label="Borrar búsqueda">&times;</button>
            </span>
        </div>

        <section class="filter-block" aria-labelledby="eventTypeTitle">
            <div class="pill-group" data-event-filters></div>
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
                <article class="catalog-card"
                         data-venue-card
                         data-name="${fn:toLowerCase(salon.nombre)}"
                         data-location="${fn:toLowerCase(salon.ubicacion)}"
                         data-price="${salon.precio}"
                         data-capacity="${salon.capacidad}">
                    <div class="card-image">
                        <img src="${not empty salon.fotoPrincipal ? fn:trim(salon.fotoPrincipal) : 'https://via.placeholder.com/400x240?text=Sin+Foto'}"
                             alt="Foto de ${salon.nombre}"
                             onerror="this.src='https://via.placeholder.com/400x240?text=Sin+Foto';" />

                        <form action="${pageContext.request.contextPath}/app/favoritos" method="post" class="favorite-form">
                            <input type="hidden" name="idRecinto" value="${salon.idSalonEventos}">
                            <button class="favorite-button" type="submit" aria-label="Guardar ${salon.nombre}">
                                <c:choose>
                                    <c:when test="${salon.favorito}">&#9829;</c:when>
                                    <c:otherwise>&#9825;</c:otherwise>
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

                        <p class="location">Ubicación: ${salon.ubicacion}</p>
                        <div class="card-divider"></div>

                        <div class="card-footer">
                            <div class="footer-info-row">
                                <span class="capacity">Hasta ${salon.capacidad} invitados</span>
                                <strong class="price">
                                    <fmt:formatNumber value="${salon.precio}" type="currency" currencySymbol="$" maxFractionDigits="0"/>
                                    <span>/evento</span>
                                </strong>
                            </div>

                            <div class="card-actions">
                                <a class="details-link" href="${pageContext.request.contextPath}/detalleRecinto?id=${salon.idSalonEventos}">
                                    Ver detalles
                                </a>

                                <form action="${pageContext.request.contextPath}/carritoAgregar" method="post" class="cart-form" style="display: inline-block;">
                                    <input type="hidden" name="idPublicacionEventos" value="${salon.idSalonEventos}">
                                    <button class="cart-link" type="submit" aria-label="Añadir ${salon.nombre} al carrito">
                                        Añadir al carrito
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </article>
            </c:forEach>
        </div>

    </section>
</main>

<footer class="catalog-footer legal-only">&copy; 2026 Event Online Spaces. Todos los derechos reservados.</footer>
<jsp:include page="alerts.jsp" />

<!-- Script Unificado de Filtrado y Menú -->
<script>
    document.addEventListener("DOMContentLoaded", () => {
        // Elementos DOM
        const inputSearch = document.querySelector('[data-city-search]');
        const btnTodos = document.getElementById("btnTodos");
        const activeTagSearch = document.getElementById("activeTagSearch");
        const activeTagText = document.getElementById("activeTagText");
        const btnBorrarTagSearch = document.getElementById("btnBorrarTagSearch");

        const priceButtons = document.querySelectorAll('[data-price-filters] button');
        const capacityButtons = document.querySelectorAll('[data-capacity-filters] button');
        const cards = document.querySelectorAll('[data-venue-card]');

        // Estado del filtro
        let state = {
            query: "",
            price: null,
            capacity: null
        };

        function normalizar(str) {
            return str ? str.toLowerCase().normalize("NFD").replace(/[\u0300-\u036f]/g, "").trim() : '';
        }

        // Aplicar filtros en tiempo real
        function aplicarFiltros() {
            const q = normalizar(state.query);

            cards.forEach(card => {
                const name = normalizar(card.dataset.name || card.querySelector('h2')?.textContent || '');
                const location = normalizar(card.dataset.location || card.querySelector('.location')?.textContent || '');
                const price = parseFloat(card.dataset.price) || 0;
                const capacity = parseInt(card.dataset.capacity, 10) || 0;

                // 1. Buscador texto
                const coincideTexto = !q || name.includes(q) || location.includes(q);

                // 2. Rango de precio
                let coincidePrecio = true;
                if (state.price === "150") coincidePrecio = price <= 150;
                else if (state.price === "500") coincidePrecio = price <= 500;
                else if (state.price === "900") coincidePrecio = price <= 900;
                else if (state.price === "2000") coincidePrecio = price > 900;

                // 3. Capacidad
                let coincideCapacidad = true;
                if (state.capacity === "50") coincideCapacidad = capacity <= 50;
                else if (state.capacity === "150") coincideCapacidad = capacity > 50 && capacity <= 150;
                else if (state.capacity === "300") coincideCapacidad = capacity > 150 && capacity <= 300;
                else if (state.capacity === "999") coincideCapacidad = capacity > 300;

                card.style.display = (coincideTexto && coincidePrecio && coincideCapacidad) ? "" : "none";
            });

            // Actualizar Tag de Búsqueda Activa
            if (state.query && activeTagSearch && activeTagText) {
                activeTagText.textContent = state.query;
                activeTagSearch.style.display = "inline-flex";
            } else if (activeTagSearch) {
                activeTagSearch.style.display = "none";
            }
        }

        // Botón Reset "Todos los recintos"
        if (btnTodos) {
            btnTodos.addEventListener("click", (e) => {
                e.preventDefault();
                state.query = "";
                state.price = null;
                state.capacity = null;

                if (inputSearch) inputSearch.value = "";

                // Limpiar resaltado de botones
                priceButtons.forEach(b => {
                    b.style.fontWeight = "normal";
                    b.style.borderColor = "";
                });
                capacityButtons.forEach(b => {
                    b.style.fontWeight = "normal";
                    b.style.borderColor = "";
                });

                aplicarFiltros();
            });
        }

        // Filtro por Precio
        priceButtons.forEach(btn => {
            btn.addEventListener("click", (e) => {
                e.preventDefault();
                const val = btn.dataset.price;

                if (state.price === val) {
                    state.price = null;
                    btn.style.fontWeight = "normal";
                    btn.style.borderColor = "";
                } else {
                    state.price = val;
                    priceButtons.forEach(b => {
                        b.style.fontWeight = "normal";
                        b.style.borderColor = "";
                    });
                    btn.style.fontWeight = "bold";
                    btn.style.borderColor = "#000";
                }
                aplicarFiltros();
            });
        });

        // Filtro por Capacidad
        capacityButtons.forEach(btn => {
            btn.addEventListener("click", (e) => {
                e.preventDefault();
                const val = btn.dataset.capacity;

                if (state.capacity === val) {
                    state.capacity = null;
                    btn.style.fontWeight = "normal";
                    btn.style.borderColor = "";
                } else {
                    state.capacity = val;
                    capacityButtons.forEach(b => {
                        b.style.fontWeight = "normal";
                        b.style.borderColor = "";
                    });
                    btn.style.fontWeight = "bold";
                    btn.style.borderColor = "#000";
                }
                aplicarFiltros();
            });
        });

        // Buscador de texto
        if (inputSearch) {
            inputSearch.addEventListener("input", (e) => {
                state.query = e.target.value;
                aplicarFiltros();
            });
        }

        // Borrar tag de búsqueda
        if (btnBorrarTagSearch) {
            btnBorrarTagSearch.addEventListener("click", () => {
                state.query = "";
                if (inputSearch) inputSearch.value = "";
                aplicarFiltros();
            });
        }

        // Menú Móvil y Cierre de Sesión
        const btnMenu = document.querySelector("[data-menu-toggle]");
        const menuFlotante = document.querySelector("[data-mobile-nav]");

        if (btnMenu && menuFlotante) {
            btnMenu.addEventListener("click", function(e) {
                e.preventDefault();
                e.stopPropagation();
                menuFlotante.classList.toggle("open");
            });

            menuFlotante.addEventListener("click", function(e) {
                if (e.target.tagName === "A") {
                    menuFlotante.classList.remove("open");
                }
            });

            document.addEventListener("click", function(e) {
                if (!menuFlotante.contains(e.target) && !btnMenu.contains(e.target)) {
                    menuFlotante.classList.remove("open");
                }
            });
        }

        const cerrarSe = document.getElementById("cerrarSe");
        if (cerrarSe) {
            cerrarSe.addEventListener('click', function (e) {
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
    });
</script>
</body>
</html>