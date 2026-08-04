<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
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

    <nav class="top-nav" aria-label="Navegacion principal">
        <a href="${pageContext.request.contextPath}/app/catalogo">Recintos</a>
        <a href="${pageContext.request.contextPath}/extraServices">Servicios</a>
        <a href="${pageContext.request.contextPath}/">Inicio</a>
    </nav>

    <div class="header-actions">
        <a class="cart-pill" href="perfil.html">Perfil</a>
    </div>
</header>

<main class="shop-shell">


    <h1>Carrito de compras</h1>

    <section class="shop-grid">
        <article class="shop-panel">
            <p class="eyebrow">Espacio de evento</p>
            <h2>Productos añadidos</h2>

            <div class="cart-table">
                <div class="cart-head">
                    <span>Producto</span>
                    <span>Fecha seleccionada</span>
                    <span>Precio</span>
                </div>
                <div class="cart-items" data-cart-items></div>
            </div>

            <div class="cart-empty" data-cart-empty hidden>
                <p>Tu carrito está vacío.</p>
                <a class="secondary-button" href="WEB-INF/catalogo.jsp">Explorar recintos</a>
            </div>

            <div class="cart-breakdown">
                <div class="breakdown-row">
                    <span>Cargo por servicio</span>
                    <strong data-cart-service-fee>$0.00</strong>
                </div>
                <div class="breakdown-row">
                    <span>Depósito por daños (30%)</span>
                    <strong data-cart-deposit>$0.00</strong>
                </div>
                <div class="breakdown-row total">
                    <span>Total</span>
                    <strong data-cart-total>$0.00</strong>
                </div>
            </div>

            <div class="notice-box">
                <strong>Aviso</strong>
                <p>El depósito de garantía se calcula sobre el total del carrito y cubre posibles daños o incidencias durante el evento.</p>
            </div>

            <div class="actions-row">
                <button class="secondary-button" type="button" data-clear-cart>Vaciar carrito</button>
                <a class="primary-button" href="pago.html" data-checkout>Proceder a pago</a>
            </div>
        </article>

        <aside class="summary-panel">
            <p class="eyebrow">Resumen</p>
            <div class="summary-card">
                <div>
                    <span>Recinto seleccionado</span>
                    <strong data-summary-venue>Sin recinto</strong>
                </div>
                <div>
                    <span>Servicios extra</span>
                    <strong data-summary-services>0</strong>
                </div>
                <div>
                    <span>Subtotal</span>
                    <strong data-summary-subtotal>$0.00</strong>
                </div>
            </div>
        </aside>
    </section>
</main>
<footer class="rights-footer">&copy; 2026 Event Online Spaces. Todos los derechos reservados.</footer>
<script src="assets/js/cart.js"></script>
<script src="assets/js/carrito.js"></script>
</body>
</html>
