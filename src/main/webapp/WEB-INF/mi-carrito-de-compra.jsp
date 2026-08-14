<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!doctype html>
<html lang="es">
<head>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Mi carrito de compra | Event Online</title>
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:wght@600;700&family=Montserrat:wght@400;500;600;700;800&display=swap" rel="stylesheet" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/carrito.css" />
    <style>

        .back-navigation {
            max-width: 1400px;
            margin: 15px auto 20px auto;
            padding: 0 32px;
        }

        .btn-back {
            display: inline-flex;
            align-items: center;
            gap: 8px;
            padding: 10px 18px 10px 14px;
            border-radius: 999px;
            background-color: var(--paper-warm, #f2e8e2);
            color: var(--clay, #9d4f38);
            text-decoration: none;
            font-family: var(--sans, 'Montserrat', sans-serif);
            font-size: 0.9rem;
            font-weight: 700;
            transition: all 0.2s ease;
        }

        .btn-back:hover {
            background-color: var(--clay, #9d4f38);
            color: #ffffff;
            transform: translateX(-4px);
            box-shadow: 0 4px 12px rgba(157, 79, 56, 0.2);
        }

        @media (max-width: 760px) {
            .back-navigation {
                padding: 0 18px;
                margin: 10px auto 15px auto;
            }
        }
        .event-details-box {
            background-color: #f9f6f0;
            border: 1px solid #e0d8cc;
            border-radius: 8px;
            padding: 20px;
            margin: 20px 0;
        }
        .event-details-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 15px;
            margin-top: 10px;
        }
        .form-group {
            display: flex;
            flex-direction: column;
            gap: 5px;
        }
        .form-group label {
            font-size: 0.85rem;
            font-weight: 600;
            color: #4a4a4a;
            text-transform: uppercase;
        }
        .form-group input {
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-family: inherit;
            font-size: 0.95rem;
        }
        .alert-error {
            background-color: #f8d7da;
            color: #721c24;
            padding: 12px 15px;
            border-radius: 6px;
            border: 1px solid #f5c6cb;
            margin-bottom: 20px;
        }
    </style>
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

    <div class="back-navigation">
        <a href="${pageContext.request.contextPath}/catalogo" class="btn-back">
            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round">
                <line x1="19" y1="12" x2="5" y2="12"></line>
                <polyline points="12 19 5 12 12 5"></polyline>
            </svg>
            Regresar al catálogo
        </a>
    </div>

    <h1>Carrito de compras</h1>

    <section class="shop-grid">
        <article class="shop-panel">
            <p class="eyebrow">Espacio de evento</p>
            <h2>Productos añadidos</h2>

            <%-- Mensaje si la fecha elegida ya está ocupada --%>
            <c:if test="${not empty errorDisponibilidad}">
                <div class="alert-error">
                    <strong>¡Atención!</strong> ${errorDisponibilidad}
                </div>
            </c:if>

            <c:choose>
                <c:when test="${empty itemsCarrito}">
                    <div class="cart-empty">
                        <p>Tu carrito está vacío.</p>
                        <a class="secondary-button" href="${pageContext.request.contextPath}/catalogo">Explorar recintos</a>
                    </div>
                </c:when>

                <c:otherwise>
                    <form action="${pageContext.request.contextPath}/iniciarReserva" method="POST" id="checkoutForm">

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
                                            <button class="remove-button" type="submit" formnovalidate
                                                    formaction="${pageContext.request.contextPath}/eliminarItemCarrito"
                                                    name="idCarrito" value="${item.idCarrito}" title="Eliminar">&times;</button>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>

                        <div class="event-details-box">
                            <h3>Programación del Evento</h3>
                            <div class="event-details-grid">
                                <div class="form-group">
                                    <label for="fechaEvento">Fecha del Evento *</label>
                                    <input type="date" id="fechaEvento" name="fechaEvento" required />
                                </div>
                            </div>
                        </div>

                        <div class="cart-breakdown">

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
                            <p>
                                Al hacer clic en "Proceder a pago", la fecha quedará bloqueada temporalmente durante 15 minutos para que completes tu transacción.<br><br>
                                Se cobra un 30% adicional al costo del recinto como preventivo de daños; este se reembolsará en caso de que no se registren daños a la propiedad.
                            </p>
                        </div>

                        <div class="actions-row">
                            <button class="secondary-button" type="submit" formnovalidate
                                    formaction="${pageContext.request.contextPath}/vaciarCarrito"
                                    onclick="return confirm('¿Estás seguro de que deseas vaciar todo el carrito?');">
                                Vaciar carrito
                            </button>

                            <button class="primary-button" type="submit">Proceder a pago</button>
                        </div>
                    </form>
                </c:otherwise>
            </c:choose>
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


<script>
    document.addEventListener("DOMContentLoaded", function () {
        const fechaInput = document.getElementById("fechaEvento");

        if (fechaInput) {
            ["change", "input"].forEach(nombreEvento => {
                fechaInput.addEventListener(nombreEvento, function () {
                    const valor = this.value.trim();
                    if (!valor) return;

                    let fechaSeleccionada;

                    if (valor.includes("-")) {
                        const partes = valor.split("-");
                        fechaSeleccionada = new Date(partes[0], partes[1] - 1, partes[2]);
                    } else if (valor.includes("/")) {
                        const partes = valor.split("/");
                        fechaSeleccionada = new Date(partes[2], partes[1] - 1, partes[0]);
                    } else {
                        fechaSeleccionada = new Date(valor);
                    }

                    if (isNaN(fechaSeleccionada.getTime())) return;

                    // Fecha de mañana
                    const manana = new Date();
                    manana.setDate(manana.getDate() + 1);
                    manana.setHours(0, 0, 0, 0);

                    // Validación con Modal Personalizado
                    if (fechaSeleccionada < manana) {
                        Swal.fire({
                            title: '¡Atención!',
                            text: 'La fecha del evento debe programarse a partir del día de mañana. Por favor, selecciona una fecha válida.',
                            icon: 'warning', // Puedes cambiarlo por 'error' si prefieres la X roja
                            confirmButtonText: 'Entendido',
                            confirmButtonColor: '#855221', // Color café acorde a tu diseño
                            borderRadius: '12px'
                        });

                        this.value = ""; // Limpia el campo
                    }
                });
            });
        }
    });
</script>
<jsp:include page="alerts.jsp" />
</body>
</html>