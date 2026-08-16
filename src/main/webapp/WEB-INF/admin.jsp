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
    <title>Event Online | Administración</title>
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:wght@600;700&family=Montserrat:wght@400;500;600;700&family=Plus+Jakarta+Sans:wght@600;700&family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/operaciones.css?v=1.2.2" />
</head>
<body>
<header class="site-header">
    <div class="brand-group">
        <a class="brand" href="${pageContext.request.contextPath}/">Event Online</a>
    </div>

    <nav class="top-nav" aria-label="Navegación principal">
        <a class="active" href="${pageContext.request.contextPath}/adminRecintos">Administración</a>
        <a href="${pageContext.request.contextPath}/catalogo">Recintos</a>
        <a href="${pageContext.request.contextPath}/app/perfil">Perfil</a>
    </nav>

    <div class="header-actions">
        <a class="avatar" href="" aria-label="Perfil"></a>
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
        <a class="active" href="${pageContext.request.contextPath}/admin">Recintos</a>
        <a href="${pageContext.request.contextPath}/adminUsuarios">Usuarios</a>
        <a href="${pageContext.request.contextPath}/admin-servicios">Servicios Extra</a>
    </aside>

    <section>
        <div class="admin-toolbar">
            <div>
                <h1>Gestión de recintos</h1>
                <p>Aprueba o deniega solicitudes de recintos.</p>
            </div>
        </div>

        <div class="stats-grid">
            <article class="panel stat-card">
                <span>Pendientes</span><strong data-stat-pending>${datosRecintos[0]}</strong>
            </article>
            <article class="panel stat-card">
                <span>Validados</span><strong data-stat-valid>${datosRecintos[1]}</strong>
            </article>
            <article class="panel stat-card">
                <span>Total</span><strong data-stat-total>${datosRecintos[2]}</strong>
            </article>
        </div>
        <table class="venue-table">
            <thead>
            <tr>
                <th>Recinto</th>
                <th>Fecha</th>
                <th>Acciones</th>
            </tr>
            </thead>
            <tbody data-admin-rows>
            <c:if test="${empty salonesPendientes}">
                <tr>
                    <td colspan="3" style="text-align: center; padding: 24px; color: var(--muted);">
                        No hay recintos pendientes por revisar en este momento.
                    </td>
                </tr>
            </c:if>

            <c:forEach var="salon" items="${salonesPendientes}">
                <tr>

                    <td>

                        <div class="venue-cell">
                            <img src="${not empty salon.fotoPrincipal ? fn:trim(salon.fotoPrincipal) : 'https://via.placeholder.com/58x52?text=Sin+Foto'}"
                                 alt="Portada de ${salon.nombre}"
                                 onerror="this.src='https://via.placeholder.com/58x52?text=Sin+Foto';" />
                            <div>
                                <strong style="color: var(--ink); font-size: 1.05rem;">${salon.nombre}</strong><br/>
                                <small style="color: var(--muted);">${salon.ubicacion}</small>
                            </div>
                        </div>
                    </td>
                    <td style="color: var(--muted);">
                            <fmt:formatDate value="${salon.fecha}" pattern="yyy-MM-dd"/>
                    </td>

                    <td>
                        <div class="action-row">
                            <form action="${pageContext.request.contextPath}/AprobarRecintoServlet" method="POST" style="margin: 0;">
                                <input type="hidden" name="idRecinto" value="${salon.idSalonEventos}">
                                <button type="submit" style="color: var(--green); border-color: #dff5e8; background: #dff5e8;">
                                    Aprobar
                                </button>
                            </form>

                            <form action="${pageContext.request.contextPath}/RechazarRecintoServlet" method="POST" style="margin: 0;">
                                <input type="hidden" name="idRecinto" value="${salon.idSalonEventos}">
                                <button type="submit" class="delete" style="background: #ffe0e0; border-color: #ffe0e0;">
                                    Denegar
                                </button>
                            </form>
                            <a class="details-link"
                               href="${pageContext.request.contextPath}/detalleRecinto?id=${salon.idSalonEventos}&modo=review">
                                Ver detalles
                            </a>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
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
