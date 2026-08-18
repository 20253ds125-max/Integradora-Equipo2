<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!doctype html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Event Online | Ticket de pago</title>
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:wght@600;700&family=Montserrat:wght@400;500;600;700;800&display=swap" rel="stylesheet" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/operaciones.css?v=1.3" />
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

<main class="page-shell ticket-shell">
    <div class="page-title ticket-title">
        <h1>¡Pago confirmado!</h1>
        <p>Guarda este comprobante; también puedes consultarlo desde "Mis reservas" en tu perfil.</p>
    </div>

    <section class="panel ticket-card">
        <div class="ticket-head">
            <div>
                <span>Reserva confirmada</span>
                <h2>${reserva.nombreSalon != null ? reserva.nombreSalon : "Reserva de servicios"}</h2>
            </div>
            <strong>Folio #${reserva.idReserva}</strong>
        </div>

        <div class="ticket-venue">
            <img
                    src="${not empty reserva.urlPortada ? reserva.urlPortada : 'https://via.placeholder.com/130x96?text=Sin+Foto'}"
                    alt="Foto de ${reserva.nombreSalon}"
                    onerror="this.src='https://via.placeholder.com/130x96?text=Sin+Foto';" />
            <div>
                <h3>${reserva.nombreSalon}</h3>
                <p>${reserva.ubicacion}</p>
                <p>Fecha del evento: <strong>${reserva.fechaEvento}</strong></p>
            </div>
        </div>

        <div class="ticket-lines">
            <p><span>Titular de la tarjeta</span> <strong>${titular}</strong></p>
            <p><span>Tarjeta</span> <strong>•••• •••• •••• ${ultimos4}</strong></p>
            <p><span>Fecha y hora de confirmación</span> <strong>${fechaConfirmacion}</strong></p>
            <p><span>Estado de la reserva</span> <strong>${reserva.estado}</strong></p>
            <p class="ticket-total">
                <span>Total pagado</span>
                <strong><fmt:formatNumber value="${reserva.total}" type="currency" currencySymbol="$" maxFractionDigits="2"/></strong>
            </p>
        </div>

        <div class="ticket-note">
            <strong>Siguiente paso</strong>
            <p>Ya puedes organizar las mesas e invitados de tu evento desde tu perfil, en la tarjeta de esta reserva.</p>
        </div>

        <div class="ticket-actions">
            <button type="button" class="outline-button" onclick="window.print()">Imprimir / Guardar PDF</button>
            <c:if test="${reserva.idPublicacion != null}">
                <a class="primary-button" href="${pageContext.request.contextPath}/mesas?idReserva=${reserva.idReserva}">
                    Editar mesas e invitados
                </a>
            </c:if>
            <a class="primary-button" href="${pageContext.request.contextPath}/app/perfil">Ir a mi perfil</a>
        </div>
    </section>
</main>

<footer class="rights-footer">&copy; 2026 Event Online Spaces. Todos los derechos reservados.</footer>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script>
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

    document.addEventListener("DOMContentLoaded", () => {
        // Menú desplegable móvil
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

        // Lógica de verificación de fecha ajax
        const fechaInput = document.getElementById("fechaEvento");
        const btnVerificar = document.getElementById("btnVerificar");
        const btnAnadirCarrito = document.getElementById("btnAnadirCarrito");
        const mensajeDiv = document.getElementById("mensajeDisponibilidad");

        function mostrarMensaje(texto, esExito) {
            if (mensajeDiv) {
                mensajeDiv.innerText = texto;
                mensajeDiv.style.display = "block";
                mensajeDiv.style.color = esExito ? "#2e7d32" : "#202020";
            }
        }

        if (fechaInput && btnVerificar) {
            fechaInput.addEventListener("change", function() {
                if (btnAnadirCarrito) {
                    btnAnadirCarrito.classList.add("disabled-link");
                    btnAnadirCarrito.disabled = true;
                }
                if (mensajeDiv) {
                    mensajeDiv.style.display = "none";
                }
            });

            btnVerificar.addEventListener("click", function() {
                const fechaSeleccionada = fechaInput.value;
                if (!fechaSeleccionada) {
                    mostrarMensaje("Por favor, selecciona una fecha.", false);
                    return;
                }

                const textoOriginal = btnVerificar.innerText;
                btnVerificar.innerText = "Verificando...";
                btnVerificar.disabled = true;

                fetch(`${pageContext.request.contextPath}/verificarFecha?fecha=${fechaSeleccionada}&idRecinto=${idRecinto}`)
                    .then(response => response.json())
                    .then(data => {
                        if (data.disponible) {
                            mostrarMensaje("¡La fecha está disponible! Ya puedes añadir al carrito.", true);
                            if (btnAnadirCarrito) {
                                btnAnadirCarrito.classList.remove("disabled-link");
                                btnAnadirCarrito.disabled = false;
                            }
                        } else {
                            mostrarMensaje("Lo sentimos, esta fecha ya está ocupada.", false);
                            if (btnAnadirCarrito) {
                                btnAnadirCarrito.classList.add("disabled-link");
                                btnAnadirCarrito.disabled = true;
                            }
                        }
                    })
                    .catch(error => {
                        console.error("Error:", error);
                        mostrarMensaje("Error de conexión. Intenta de nuevo.", false);
                    })
                    .finally(() => {
                        btnVerificar.innerText = textoOriginal;
                        btnVerificar.disabled = false;
                    });
            });
        }

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
