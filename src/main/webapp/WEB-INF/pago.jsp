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
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/operaciones.css?v=1.2.1" />
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
    <div class="page-title">
      <h1>Pago seguro</h1>
      <p>Tu información de pago se mantiene protegida. El total incluye depósito de garantía por daños.</p>
    </div>

    <form action="${pageContext.request.contextPath}/procesarPago" method="POST" class="panel checkout-form">
      <label>Nombre del titular
        <input type="text" name="nombreTitular" placeholder="Nombre como aparece en la tarjeta" required />
      </label>
      <label>Número de tarjeta
        <input type="text" name="numeroTarjeta" placeholder="0000 0000 0000 0000" maxlength="16" required />
      </label>
      <div class="field-grid">
        <label>Vencimiento
          <input type="text" name="vencimiento" placeholder="MM / YY" maxlength="5" required />
        </label>
        <label>CVV
          <input type="text" id="cvv" name="cvv" placeholder="123" maxlength="4" pattern="\d*" inputmode="numeric" required />
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