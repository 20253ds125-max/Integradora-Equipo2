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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/operaciones.css?v=1.1" />
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

    </div>
</header>


<main class="page-shell admin-layout">
    <aside class="panel admin-sidebar">
        <h2>Administración</h2>
        <a class="active" href="${pageContext.request.contextPath}/admin">Recintos</a>
        <a href="${pageContext.request.contextPath}/usuarios">Usuarios</a>
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
                <span>Pendientes</span><strong data-stat-pending>12</strong>
            </article>
            <article class="panel stat-card">
                <span>Validados</span><strong data-stat-valid>84</strong>
            </article>
            <article class="panel stat-card">
                <span>Total</span><strong data-stat-total>142</strong>
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
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </section>
</main>

<footer class="rights-footer">&copy; 2026 Event Online Spaces. Todos los derechos reservados.</footer>
<jsp:include page="alerts.jsp" />
</body>
</html>
