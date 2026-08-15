<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%
    jakarta.servlet.http.HttpSession sesion = request.getSession(false);

    if (sesion == null || sesion.getAttribute("UsuarioLog") == null) {
        request.setAttribute("error", "Por favor inicia sesión para continuar.");
        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        return;
    }

    com.eventonline.model.Usuario usuarioLogueado = (com.eventonline.model.Usuario) sesion.getAttribute("UsuarioLog");

    if (!"ADMIN".equalsIgnoreCase(usuarioLogueado.getRol())) {
        request.setAttribute("error", "Acceso denegado. Se requiere cuenta de administrador.");
        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        return;
    }
%>
<!doctype html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Event Online | Administración de Servicios</title>
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:wght@600;700&family=Montserrat:wght@400;500;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/operaciones.css?v=1.1.1" />
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


<main class="page-shell admin-layout">
    <aside class="panel admin-sidebar">
        <h2>Administración</h2>
        <a href="${pageContext.request.contextPath}/adminRecintos">Recintos</a>
        <a>Usuarios</a>
        <a class="active" href="${pageContext.request.contextPath}/admin-servicios">Servicios Extra</a>
    </aside>

    <section>
        <div class="admin-toolbar">
            <div>
                <h1>Gestión de servicios extra</h1>
                <p>Modera, aprueba o rechaza los servicios complementarios ofrecidos por los usuarios.</p>
            </div>
        </div>

        <div class="stats-grid">
            <article class="panel stat-card">
                <span>Pendientes</span><strong>${fn:length(pendientes)}</strong>
            </article>
        </div>

        <div>
            <table class="venue-table" style="max-height: 400px; overflow-y: auto; border: 1px solid #f0f0f0; border-radius: 8px; width: 100%;">
                <thead>
                <tr>
                    <th>Servicio</th>
                    <th>Precio</th>
                    <th style="text-align: right; padding-right: 90px;">Acciones</th>
                </tr>
                </thead>
                <tbody>

                <c:forEach var="servicio" items="${pendientes}">
                    <tr>
                        <td style="vertical-align: middle;">
                            <div class="venue-cell" style="display: flex; align-items: center; gap: 12px;">
                                <img src="${servicio.urlFoto}" alt="${servicio.nombreServicio}" style="width: 50px; height: 50px; border-radius: 4px; object-fit: cover;">
                                <div>
                                    <div style="font-family: 'Montserrat', sans-serif; font-weight: 600; color: var(--ink); font-size: 14px; margin-bottom: 3px; letter-spacing: -0.2px;">
                                            ${servicio.nombreServicio}
                                    </div>
                                    <div style="font-family: 'Montserrat', sans-serif; font-weight: 500; color: var(--muted); font-size: 13px;">

                                        ID: #SE-${servicio.idServicio}
                                    </div>
                                </div>
                            </div>
                        </td>

                        <td style="vertical-align: middle; font-family: 'Montserrat', sans-serif; font-weight: 600; color: var(--ink); font-size: 14px; width: 140px; min-width: 140px;">
                            <fmt:formatNumber value="${servicio.precio}" type="currency" currencySymbol="$" maxFractionDigits="2"/>
                        </td>

                        <td style="vertical-align: middle; width: 220px; min-width: 220px;">
                            <div class="action-row" style="display: flex; flex-direction: row; flex-wrap: nowrap; justify-content: flex-end; align-items: center; gap: 8px; padding-right: 20px;">

                                <form action="${pageContext.request.contextPath}/AceptarServicioAdmin" method="POST" style="margin: 0;">
                                    <input type="hidden" name="idServicio" value="${servicio.idServicio}">
                                    <button type="submit" style="font-family: 'Montserrat', sans-serif; white-space: nowrap; cursor: pointer;">Aceptar</button>
                                </form>

                                <form action="${pageContext.request.contextPath}/DenegarServicioAdmin" method="POST" style="margin: 0;">
                                    <input type="hidden" name="idServicio" value="${servicio.idServicio}">
                                    <button type="submit" class="delete" style="font-family: 'Montserrat', sans-serif; white-space: nowrap; cursor: pointer;">Rechazar</button>
                                </form>

                            </div>
                        </td>
                    </tr>
                </c:forEach>

                <c:if test="${empty pendientes}">
                    <tr>
                        <td colspan="3" style="text-align: center; padding: 30px; font-family: 'Montserrat', sans-serif; color: var(--muted);">
                            No hay servicios pendientes por aprobar.
                        </td>
                    </tr>
                </c:if>

                </tbody>
            </table>
        </div>
    </section>
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