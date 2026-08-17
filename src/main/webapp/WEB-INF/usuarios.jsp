
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
    <title>Usuarios | Event Online Admin</title>
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:wght@600;700&family=Montserrat:wght@400;500;600;700;800&display=swap" rel="stylesheet" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/operaciones.css" />
</head>
<body>

<header class="app-header">
    <a class="brand" href="index.jsp">Event Online</a>
    <nav>
        <a class="active" href="admin.jsp">Administración</a>
        <a href="catalogo.jsp">Recintos</a>
        <a href="perfil.html">Perfil</a>
    </nav>
    <div class="header-actions">
        <a class="avatar" href="" aria-label="Perfil"></a>
    </div>
</header>

<main class="page-shell admin-layout">

    <aside class="panel admin-sidebar">
        <h2>Administración</h2>
        <a href="${pageContext.request.contextPath}/admin">Recintos</a>
        <a class="active" href="${pageContext.request.contextPath}/adminUsuarios">Usuarios</a>
        <a href="${pageContext.request.contextPath}/admin-servicios">Servicios Extra</a>
    </aside>

    <section>
        <div class="admin-toolbar">
            <div>
                <h1>Gestión de usuarios</h1>
                <p>Administra usuarios registrados en la plataforma.</p>
            </div>
            <button class="primary-button">Nuevo Admin</button>
        </div>

        <div class="stats-grid">
            <article class="panel stat-card">
                <span>Total usuarios</span>
                <strong>${datosUsuarios[0]}</strong>
            </article>
            <article class="panel stat-card">
                <span>Usuarios con publicaciones</span>
                <strong>${datosUsuarios[1]}</strong>
            </article>
            <article class="panel stat-card">
                <span>Administradores</span>
                <strong>${datosUsuarios[2]}</strong>
            </article>
        </div>

        <table class="venue-table">
            <thead>
            <tr>
                <th>Usuario</th>
                <th>Correo</th>
                <th>Ciudad</th>
                <th style="text-align: center; padding-right: 24px;">Acciones</th>
            </tr>
            </thead>
            <tbody id="usuariosBody">
            <c:choose>
                <%-- Verifica si la lista no está vacía --%>
                <c:when test="${not empty listaDeUsuarios}">
                    <%-- Itera sobre la lista de usuarios --%>
                    <c:forEach var="user" items="${listaDeUsuarios}">
                        <tr>
                            <td><c:out value="${user.nombre}" /></td>
                            <td><c:out value="${user.email}" /></td>
                            <td><c:out value="${user.ciudad}" /></td>
                            <td style="text-align: center; padding-right: 24px;">
                                <div class="action-row" style="display: inline-block;">
                                    <form method="post" action="${pageContext.request.contextPath}/borrarUsuario">
                                        <input type="hidden" name="usuarioId" value="${user.idUsuario}">
                                        <button class="delete" type="submit">Eliminar</button>
                                    </form>
                                </div>
                            </td>

                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan="4" style="text-align: center; padding: 20px;">No hay usuarios registrados.</td>
                    </tr>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>

    </section>

</main>

<footer class="rights-footer">
    &copy; 2026 Event Online Spaces. Todos los derechos reservados.
</footer>
<jsp:include page="alerts.jsp" />
</body>
</html>