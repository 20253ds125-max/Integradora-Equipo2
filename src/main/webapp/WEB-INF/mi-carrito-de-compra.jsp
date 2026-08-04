<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!doctype html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Mi carrito de compra | Event Online</title>
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:wght@600;700&family=Montserrat:wght@400;500;600;700;800&display=swap" rel="stylesheet" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/carrito.css" />
</head>
<body>
<header class="site-header">
    <div class="brand-block">
        <a class="brand" href="${pageContext.request.contextPath}/">Event Online</a>
    </div>

    <nav class="top-nav" aria-label="Navegación principal">
        <a href="${pageContext.request.contextPath}/catalogo">Recintos</a>
        <a href="${pageContext.request.contextPath}/extraServices">Servicios</a>
        <a href="${pageContext.request.contextPath}/">Inicio</a>
    </nav>

    <div class="header-actions">
        <a class="cart-pill" href="${pageContext.request.contextPath}/app/perfil">Perfil</a>
    </div>
</header>

<main class="shop-shell">
    <h1>Carrito de compras</h1>

    <section class="shop-grid">
        <article class="shop-panel">
            <p class="eyebrow">Espacio de evento</p>
            <h2>Productos añadidos</h2>

            <c:choose>
                <c:when test="${empty itemsCarrito}">
                    <div class="cart-empty">
                        <p>Tu carrito está vacío.</p>
                        <a class="secondary-button" href="${pageContext.request.contextPath}/catalogo">Explorar recintos</a>
                    </div>
                </c:when>

                <c:otherwise>
                    <div class="cart-table">
                        <div class="cart-head">
                            <span>Producto</span>
                            <span>Precio</span>
                            <span>Acción</span>
                        </div>

                        <div class="cart-items">
                            <c:forEach var="item" items="${itemsCarrito}">
                                <div class="cart-row">
                                    <div class="product-cell">
                                        <img src="${item.urlFoto}" alt="${item.nombre}">
                                        <div class="product-name">
                                            <strong>${item.nombre}</strong>
                                            <span>${item.ubicacion}</span>
                                        </div>
                                    </div>
                                    <div class="price-cell">
                                        <fmt:formatNumber value="${item.precio}" type="currency" currencySymbol="$" maxFractionDigits="2"/>
                                    </div>
                                    <div class="remove-cell">
                                        <form action="${pageContext.request.contextPath}/eliminarItemCarrito" method="POST" style="margin: 0;">
                                            <input type="hidden" name="idCarrito" value="${item.idCarrito}">
                                            <button class="remove-button" type="submit" title="Eliminar">&times;</button>
                                        </form>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>

            <div class="cart-breakdown">
                <div class="breakdown-row">
                    <span>Cargo por servicio</span>
                    <strong><fmt:formatNumber value="${cargoServicio}" type="currency" currencySymbol="$" maxFractionDigits="2"/></strong>
                </div>
                <div class="breakdown-row">
                    <span>Depósito por daños (30%)</span>
                    <strong><fmt:formatNumber value="${deposito}" type="currency" currencySymbol="$" maxFractionDigits="2"/></strong>
                </div>
                <div class="breakdown-row total">
                    <span>Total</span>
                    <strong><fmt:formatNumber value="${total}" type="currency" currencySymbol="$" maxFractionDigits="2"/></strong>
                </div>
            </div>

            <div class="notice-box">
                <strong>Aviso</strong>
                <p>El depósito de garantía se calcula sobre el total del carrito y cubre posibles daños o incidencias durante el evento.</p>
            </div>

            <div class="actions-row">
                <c:if test="${not empty itemsCarrito}">
                    <form action="${pageContext.request.contextPath}/vaciarCarrito" method="POST" style="margin: 0;" onsubmit="return confirm('¿Estás seguro de que deseas vaciar todo el carrito?');">
                        <button class="secondary-button" type="submit">Vaciar carrito</button>
                    </form>

                    <a class="primary-button" href="${pageContext.request.contextPath}/pago">Proceder a pago</a>
                </c:if>
            </div>
        </article>

        <aside class="summary-panel">
            <p class="eyebrow">Resumen</p>
            <div class="summary-card">
                <div>
                    <span>Recintos seleccionados</span>
                    <strong>${contRecintos}</strong>
                </div>
                <div>
                    <span>Servicios extra</span>
                    <strong>${contServicios}</strong>
                </div>
                <div>
                    <span>Subtotal</span>
                    <strong><fmt:formatNumber value="${subtotal}" type="currency" currencySymbol="$" maxFractionDigits="2"/></strong>
                </div>
            </div>
        </aside>
    </section>
</main>

<footer class="rights-footer">&copy; 2026 Event Online Spaces. Todos los derechos reservados.</footer>
</body>
</html>