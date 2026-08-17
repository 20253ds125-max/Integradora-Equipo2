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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/operaciones.css" />
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
        <a class="avatar" href="${pageContext.request.contextPath}/app/perfil" aria-label="Perfil"></a>
    </div>
</header>

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
</body>
</html>
