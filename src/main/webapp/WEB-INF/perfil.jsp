<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Perfil | Event Online</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/perfil.css?v=1.2.2"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/catalogo.css?V1.0.1"/>

    <link rel="preconnect" href="https://fonts.googleapis.com"/>
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin/>
    <link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:wght@600;700&family=Plus+Jakarta+Sans:wght@400;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>
<header class="site-header">
    <div class="brand-group">
        <a class="brand" href="${pageContext.request.contextPath}/">Event Online</a>
    </div>

    <nav class="top-nav" aria-label="Navegación principal">
        <a href="${pageContext.request.contextPath}/catalogo">Recintos</a>
        <a href="${pageContext.request.contextPath}/extraServices">Servicios</a>
        <a class="active" href="${pageContext.request.contextPath}/app/perfil">Perfil</a>
    </nav>

    <div class="header-actions">
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

<main class="profile-page">
    <aside class="profile-rail glass-panel">
        <div class="profile-identity">
            <div class="profile-info">
                <p class="eyebrow">MI CUENTA</p>
                <h2 data-user-display>${usuario.nombre}</h2>
            </div>

            <img data-avatar
                 src="${pageContext.request.contextPath}/assets/logo.png"
                 alt="Logotipo Event Online">
        </div>

        <div class="rail-actions">
            <button class="ui-button ui-button--solid" type="button" data-scroll-to="profile-personal">Datos personales</button>
            <button class="ui-button ui-button--ghost" type="button" data-scroll-to="profile-resser">Mis publicaciones</button>
            <button class="ui-button ui-button--ghost" type="button" data-scroll-to="profile-bookings">Ver reservas</button>
            <button class="ui-button ui-button--ghost" type="button" data-scroll-to="profile-favorites">Ver favoritos</button>
            <a class="ui-button ui-button--ghost" href="${pageContext.request.contextPath}/mi-carrito-de-compra">Ir al carrito</a>
        </div>
    </aside>
    <section class="profile-main">


        <section class="panel-stack">
            <article class="panel-card glass-panel" id="profile-personal" data-panel>
                <div class="panel-head">
                    <div>
                        <p class="eyebrow">Perfil</p>
                        <h2>Datos personales</h2>
                    </div>
                </div>

                <form action="${pageContext.request.contextPath}/app/editarPerfil"
                      method="post"
                      class="profile-form">
                    <label>
                        Nombre completo
                        <input type="text" name="name" value="${usuario.nombre}" data-field="name" disabled required placeholder="Ingresa tu nombre">
                    </label>
                    <label>
                        Correo electrónico
                        <input type="email" name="email" value="${usuario.email}" data-field="email" disabled required placeholder="Ingrea tu correo">
                    </label>
                    <label>
                        Teléfono
                        <input type="tel" name="telefono" value="${usuario.telefono}" data-field="phone" disabled required placeholder="Ingresa tu teléfono">
                    </label>
                    <label>
                        Ciudad
                        <input type="text" name="ciudad" value="${usuario.ciudad}" data-field="city" disabled required placeholder="Ingresa tu ciudad">
                    </label>

                    <div class="form-actions span-2">
                        <button class="ui-button ui-button--solid" type="button" data-edit-profile>Editar perfil</button>
                        <button
                                class="ui-button ui-button--ghost"
                                type="submit"
                                data-save-profile
                                style="display: none;">
                            Guardar cambios
                        </button>
                    </div>
                </form>
            </article>
            <article class="panel-card glass-panel hidden" id="profile-resser" data-panel>
                <div class="panel-head">
                    <div>
                        <p class="eyebrow">Mis publicaciones</p>
                        <h2>Publicaciones recientes</h2>
                    </div>
                </div>

                <div class="cards-grid cards-grid--favorites">

                    <c:choose>

                        <c:when test="${empty publicaciones}">

                            <p>Aún no has publicado ningún recinto.</p>

                        </c:when>

                        <c:otherwise>

                            <c:forEach var="salon" items="${publicaciones}">

                                <article class="catalog-card">

                                    <div class="card-image">

                                        <img
                                                src="${not empty salon.fotoPrincipal ? salon.fotoPrincipal : 'https://via.placeholder.com/400x240?text=Sin+Foto'}"
                                                alt="Foto de ${salon.nombre}"
                                                onerror="this.src='https://via.placeholder.com/400x240?text=Sin+Foto';" />

                                    </div>

                                    <div class="card-body">

                                        <div class="card-title-row">

                                            <h2>${salon.nombre}</h2>

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
                                                    <fmt:formatNumber
                                                            value="${salon.precio}"
                                                            type="currency"
                                                            currencySymbol="$"
                                                            maxFractionDigits="0"/>
                                                    <span>/evento</span>
                                                </strong>

                                            </div>

                                            <div class="card-actions">

                                                <a class="details-link"
                                                   href="${pageContext.request.contextPath}/detalleRecinto?id=${salon.idSalonEventos}">
                                                    Ver detalles
                                                </a>


                                            </div>

                                        </div>

                                    </div>

                                </article>

                            </c:forEach>

                        </c:otherwise>

                    </c:choose>

                </div>
            </article>

            <article class="panel-card glass-panel hidden" id="profile-bookings" data-panel>

                <div class="panel-head">
                    <div>
                        <p class="eyebrow">Reservas</p>
                        <h2>Reservas recientes</h2>
                    </div>
                </div>

                <div class="cards-grid">
                    <c:choose>
                        <c:when test="${empty reservas}">
                            <p>Aun no tienes reservas. Cuando reserves un recinto, aparecerá aquí.</p>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="reserva" items="${reservas}">
                                <article class="catalog-card">
                                    <div class="card-image">
                                        <img
                                                src="${not empty reserva.urlPortada ? reserva.urlPortada : 'https://via.placeholder.com/400x240?text=Sin+Foto'}"
                                                alt="Foto de ${reserva.nombreSalon}"
                                                onerror="this.src='https://via.placeholder.com/400x240?text=Sin+Foto';" />
                                    </div>
                                    <div class="card-body">
                                        <div class="card-title-row">
                                            <h2>${not empty reserva.nombreSalon ? reserva.nombreSalon : 'Reserva de servicios'}</h2>
                                            <span class="badge">${reserva.estado}</span>
                                        </div>
                                        <p class="location">${reserva.ubicacion}</p>
                                        <p>Fecha del evento: <strong>${reserva.fechaEvento}</strong></p>
                                        <p>Total: <strong>$${reserva.total}</strong></p>

                                        <c:if test="${reserva.gestionMesasDisponible}">
                                            <a class="ui-button ui-button--primary"
                                               style="margin-top:10px;display:inline-block;"
                                               href="${pageContext.request.contextPath}/mesas?idReserva=${reserva.idReserva}">
                                                Editar mesas e invitados
                                            </a>
                                        </c:if>
                                        <c:if test="${not reserva.gestionMesasDisponible and reserva.idPublicacion != null}">
                                            <p style="font-size:0.8rem;color:var(--muted, #888);margin-top:8px;">
                                                Podrás gestionar mesas cuando el pago de esta reserva esté confirmado.
                                            </p>
                                        </c:if>
                                    </div>
                                </article>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </div>

            </article>

            <article class="panel-card glass-panel hidden"
                     id="profile-favorites"
                     data-panel>

                <div class="panel-head">
                    <div>
                        <p class="eyebrow">Favoritos</p>
                        <h2>Recintos guardados</h2>
                    </div>
                </div>

                <div class="cards-grid cards-grid--favorites">

                    <c:choose>

                        <c:when test="${empty favoritos}">

                            <p>Aun no has guardado nada en favoritos</p>

                        </c:when>

                        <c:otherwise>

                            <c:forEach var="salon" items="${favoritos}">

                                <article class="catalog-card">

                                    <div class="card-image">

                                        <img
                                                src="${not empty salon.fotoPrincipal ? salon.fotoPrincipal : 'https://via.placeholder.com/400x240?text=Sin+Foto'}"
                                                alt="Foto de ${salon.nombre}"
                                                onerror="this.src='https://via.placeholder.com/400x240?text=Sin+Foto';" />

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
                                                    <fmt:formatNumber
                                                            value="${salon.precio}"
                                                            type="currency"
                                                            currencySymbol="$"
                                                            maxFractionDigits="0"/>
                                                    <span>/evento</span>
                                                </strong>

                                            </div>

                                            <div class="card-actions">
                                                <a class="details-link" href="${pageContext.request.contextPath}/detalleRecinto?id=${salon.idSalonEventos}">
                                                    Ver detalles
                                                </a>

                                                <form method="post" action="${pageContext.request.contextPath}/app/favoritos">
                                                    <input type="hidden" name="idRecinto" value="${salon.idSalonEventos}">
                                                    <button type="submit" class="cart-link">
                                                        Quitar de favoritos
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
            </article>

            <article class="panel-card glass-panel hidden" id="profile-cart" data-panel>
                <div class="panel-head">
                    <div>
                        <p class="eyebrow">Carrito</p>
                        <h2>Recinto y servicios seleccionados</h2>
                    </div>
                    <span class="status-pill" data-cart-status>Listo para pagar</span>
                </div>

                <div class="cart-layout">
                    <div class="cart-summary" data-cart-summary></div>
                    <div class="cart-breakdown">
                        <div class="breakdown-row">
                            <span>Subtotal recinto y servicios</span>
                            <strong data-cart-subtotal>$0.00</strong>
                        </div>
                        <div class="breakdown-row">
                            <span>Depósito por daños o incidencias (30%)</span>
                            <strong data-cart-deposit>$0.00</strong>
                        </div>
                        <div class="breakdown-row total">
                            <span>Total estimado</span>
                            <strong data-cart-total>$0.00</strong>
                        </div>
                        <p class="helper-text">El depósito se agrega como garantía para cualquier daño o problema ocasionado.</p>
                        <a class="ui-button ui-button--solid ui-button--full" href="pago.html" data-go-payment>Ir al método de pago</a>
                    </div>
                </div>
            </article>
        </section>
    </section>
</main>

<div class="modal-overlay" id="modalEditarRecinto">

    <div class="modal-content">

        <div class="modal-header">
            <h2>Editar recinto</h2>

            <button
                    type="button"
                    class="modal-close"
                    id="cerrarModal">
                &times;
            </button>
        </div>

        <form id="formEditarRecinto">

            <label>
                Nombre del recinto
                <input type="text" id="editNombre">
            </label>

            <label>
                Ubicación
                <input type="text" id="editUbicacion">
            </label>

            <label>
                Precio
                <input type="number" id="editPrecio">
            </label>

            <div class="modal-actions">

                <button
                        type="button"
                        class="ui-button ui-button--ghost"
                        id="cancelarModal">
                    Cancelar
                </button>

                <button
                        type="submit"
                        class="ui-button ui-button--solid">
                    Guardar cambios
                </button>

            </div>

        </form>

    </div>

</div>
<footer class="catalog-footer legal-only">&copy; 2026 Event Online Spaces. Todos los derechos reservados.</footer>

<script src="${pageContext.request.contextPath}/assets/js/perfil.js?v=1.2"></script>
<jsp:include page="alerts.jsp" />

</body>
</html>