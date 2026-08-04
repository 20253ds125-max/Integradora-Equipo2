<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Perfil | Event Online</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/perfil.css?v=1.1"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/catalogo.css"/>

    <link rel="preconnect" href="https://fonts.googleapis.com"/>
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin/>
    <link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:wght@600;700&family=Plus+Jakarta+Sans:wght@400;600;700;800&display=swap" rel="stylesheet">
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
    </div>
</header>

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
            <a class="ui-button ui-button--ghost" href="mi-carrito-de-compra.html">Ir al carrito</a>
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
                        <input type="text" name="name" value="${usuario.nombre}" data-field="name" disabled>
                    </label>
                    <label>
                        Correo electrónico
                        <input type="email" name="email" value="${usuario.email}" data-field="email" disabled>
                    </label>
                    <label>
                        Teléfono
                        <input type="tel" name="telefono" value="${usuario.telefono}" data-field="phone" disabled>
                    </label>
                    <label>
                        Ciudad
                        <input type="text" name="ciudad" value="${usuario.ciudad}" data-field="city" disabled>
                    </label>

                    <div class="form-actions span-2">
                        <button class="ui-button ui-button--solid" type="button" data-edit-profile>Editar perfil</button>
                        <button
                                class="ui-button ui-button--ghost"
                                type="submit"
                                data-save-profile
                                hidden>
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

                <div class="cards-grid" data-publications-list></div>
            </article>

            <article class="panel-card glass-panel hidden" id="profile-bookings" data-panel>

                <div class="panel-head">
                    <div>
                        <p class="eyebrow">Reservas</p>
                        <h2>Reservas recientes</h2>
                    </div>
                </div>

                <div class="cards-grid" data-real-bookings-list></div>

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

                                                <a class="details-link"
                                                   href="${pageContext.request.contextPath}/detalleRecinto?id=${salon.idSalonEventos}">
                                                    Ver detalles
                                                </a>

                                                <form method="post"
                                                      action="${pageContext.request.contextPath}/app/favoritos">

                                                    <input type="hidden"
                                                           name="idRecinto"
                                                           value="${salon.idSalonEventos}">

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


<script src="${pageContext.request.contextPath}/assets/js/perfil.js"></script>
<jsp:include page="alerts.jsp" />

</body>
</html>

