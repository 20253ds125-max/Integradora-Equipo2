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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/carrito.css?v=1.1.2" />

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


<main class="shop-shell">


    <h1>Carrito de compras</h1>

    <section class="shop-grid">
        <article class="shop-panel">
            <p class="eyebrow">Espacio de evento</p>
            <h2>Productos añadidos</h2>

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
                                    <input type="date" id="fechaEvento" name="fechaEvento" value="${not empty fechaGuardada ? fechaGuardada : sessionScope.fechaGuardada}" required />
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
                            icon: 'warning',
                            confirmButtonText: 'Entendido',
                            confirmButtonColor: '#855221',
                            borderRadius: '12px'
                        });

                        this.value = "";
                    }
                });
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
        }
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
    });
</script>
<jsp:include page="alerts.jsp" />
</body>
</html>