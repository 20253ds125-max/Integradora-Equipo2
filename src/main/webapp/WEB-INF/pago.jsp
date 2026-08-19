<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!doctype html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Event Online | Método de pago</title>
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:wght@600;700&family=Montserrat:wght@400;500;600;700;800&display=swap" rel="stylesheet" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/operaciones.css?v=1.2.3" />
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>
<header class="app-header">
    <a class="brand" href="${pageContext.request.contextPath}/">Event Online</a>
    <nav class="top-nav" aria-label="Navegación">
        <a href="${pageContext.request.contextPath}/catalogo">Recintos</a>
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
        <a href="${pageContext.request.contextPath}/cerrarSesion" id="cerrarSe" class="cerrar">Cerrar sesion</a>
    </c:if>
</nav>

<main class="page-shell checkout-layout">
    <section>
        <div style="width: 100%; padding-bottom: 24px;">
            <a href="${pageContext.request.contextPath}/mi-carrito-de-compra"
               class="primary-button"
               style="display: inline-flex; align-items: center; gap: 8px; text-decoration: none; white-space: nowrap; width: fit-content;">
                &larr; Volver al carrito
            </a>
        </div>
        <div class="page-title">
            <h1>Pago seguro</h1>
            <p>Tu información de pago se mantiene protegida. El total incluye depósito de garantía por daños.</p>
        </div>

        <form action="${pageContext.request.contextPath}/procesarPago" method="POST" class="panel checkout-form">
            <input type="hidden" name="idReserva" value="${reservacion.idReserva}" />

            <label>Nombre del titular
                <input type="text" oninput="this.value = this.value.replace(/[0-9]/g, '')" name="nombreTitular" value="${param.nombreTitular}" placeholder="Nombre como aparece en la tarjeta" required />
            </label>
            <label>Número de tarjeta
                <input type="text" name="numeroTarjeta" value="${param.numeroTarjeta}" placeholder="0000 0000 0000 0000" maxlength="19" required />
            </label>
            <div class="field-grid">
                <label>Vencimiento
                    <input type="text" name="vencimiento" value="${param.vencimiento}" placeholder="MM / YY" maxlength="5" required />
                </label>
                <label>CVV
                    <input type="password" id="cvv" name="cvv" value="${param.cvv}" placeholder="123" maxlength="3" pattern="\d*" inputmode="numeric" required />
                </label>
            </div>

            <button class="primary-button" type="submit">
                Confirmar y pagar <fmt:formatNumber value="${reservacion.total}" type="currency" currencySymbol="$" maxFractionDigits="2"/>
            </button>
            <p class="panel-note" data-payment-status>Transacción segura encriptada.</p>
        </form>
    </section>

    <aside class="panel summary-card">
        <h2>Resumen de reserva</h2>
        <div class="summary-venue">
            <div>
                <h2>Reserva #${reservacion.idReserva}</h2>
                <p data-summary-guests><strong>Fecha del Evento:</strong> ${reservacion.fechaEvento}</p>
            </div>
        </div>
        <div class="cost-list">
            <p class="total">
                <span>Total abonado / pagado</span>
                <strong data-total-amount><fmt:formatNumber value="${reservacion.total}" type="currency" currencySymbol="$" maxFractionDigits="2"/></strong>
            </p>
        </div>
        <div class="protection-box">
            <strong>Déposito de garantía</strong><br />
            El 30% cubre posibles daños o problemas ocasionados durante el evento.
        </div>
    </aside>
</main>

<footer class="rights-footer">&copy; 2026 Event Online Spaces. Todos los derechos reservados.</footer>

<script>
    document.addEventListener("DOMContentLoaded", () => {
        // Restricciones y formateo en vivo de los inputs de pago
        const tarjetaInput = document.querySelector('input[name="numeroTarjeta"]');
        const vencimientoInput = document.querySelector('input[name="vencimiento"]');
        const cvvInput = document.querySelector('input[name="cvv"]');

        if (tarjetaInput) {
            tarjetaInput.addEventListener('input', (e) => {
                let val = e.target.value.replace(/\D/g, '');
                val = val.substring(0, 16);
                e.target.value = val.replace(/(.{4})/g, '$1 ').trim();
            });
        }

        if (vencimientoInput) {
            vencimientoInput.addEventListener('input', (e) => {
                const esBorrado = e.inputType === 'deleteContentBackward';
                let val = e.target.value.replace(/\D/g, '');

                if (esBorrado) {
                    e.target.value = val;
                    return;
                }

                if (val.length >= 2) {
                    e.target.value = val.substring(0, 2) + '/' + val.substring(2, 4);
                } else {
                    e.target.value = val;
                }
            });
        }

        if (cvvInput) {
            cvvInput.addEventListener('input', (e) => {
                e.target.value = e.target.value.replace(/\D/g, '').substring(0, 4);
            });
        }

        // Alertas según el estado enviado por el servlet
        const urlParams = new URLSearchParams(window.location.search);
        const errorTipo = urlParams.get('error') || "${error}";
        const errorDetallado = "${errorDetallado}";
        const statusTipo = urlParams.get('status') || "${status}";

        // Muestra el error exacto (Titular, Tarjeta, Vencimiento o CVV)
        if (errorDetallado && errorDetallado.trim() !== "") {
            Swal.fire({
                title: 'Error en los datos',
                text: errorDetallado,
                icon: 'error',
                confirmButtonText: 'Corregir',
                confirmButtonColor: '#855221'
            });
        }

        if (statusTipo === 'pedir_confirmacion') {
            Swal.fire({
                title: '¿Confirmar pago?',
                text: '¿Estás seguro de proceder con el pago de esta reserva?',
                icon: 'question',
                showCancelButton: true,
                confirmButtonText: 'Sí, pagar ahora',
                cancelButtonText: 'Cancelar',
                confirmButtonColor: '#855221',
                cancelButtonColor: '#6c757d',
                borderRadius: '12px'
            }).then((result) => {
                if (result.isConfirmed) {
                    const form = document.querySelector('.checkout-form');
                    const inputConfirm = document.createElement('input');
                    inputConfirm.type = 'hidden';
                    inputConfirm.name = 'confirmado';
                    inputConfirm.value = 'true';
                    form.appendChild(inputConfirm);
                    form.submit();
                }
            });
        }

        if (statusTipo === 'exito') {
            Swal.fire({
                title: '¡Pago Exitoso!',
                text: 'Tu reserva ha sido confirmada correctamente.',
                icon: 'success',
                confirmButtonText: 'Ver ticket',
                confirmButtonColor: '#855221',
                allowOutsideClick: false,
                allowEscapeKey: false
            }).then((result) => {
                if (result.isConfirmed) {
                    const form = document.querySelector('.checkout-form');

                    const inputVerTicket = document.createElement('input');
                    inputVerTicket.type = 'hidden';
                    inputVerTicket.name = 'mostrarTicket';
                    inputVerTicket.value = 'true';

                    form.appendChild(inputVerTicket);
                    form.submit();
                }
            });
        }

        if (errorTipo === 'datos_invalidos') {
            Swal.fire({
                title: 'Datos Inválidos',
                text: 'Por favor verifica el número de tarjeta, expiración y CVV.',
                icon: 'error',
                confirmButtonText: 'Reintentar',
                confirmButtonColor: '#855221'
            });
        }

        if (errorTipo === 'sin_reserva') {
            Swal.fire({
                title: 'Sin Reserva Pendiente',
                text: 'No se encontró ninguna reserva activa para realizar el pago.',
                icon: 'error',
                confirmButtonText: 'Ir al catálogo',
                confirmButtonColor: '#855221'
            }).then(() => {
                window.location.href = '${pageContext.request.contextPath}/catalogo';
            });
        }

        if (errorTipo === 'reserva_expirada') {
            Swal.fire({
                title: 'Reserva Expirada',
                text: 'El tiempo límite de 15 minutos para pagar esta reserva ha finalizado.',
                icon: 'warning',
                confirmButtonText: 'Volver al catálogo',
                confirmButtonColor: '#855221'
            }).then(() => {
                window.location.href = '${pageContext.request.contextPath}/catalogo';
            });
        }

        // Menú desplegable
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
            cerrarSe.addEventListener('click', function (e){
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