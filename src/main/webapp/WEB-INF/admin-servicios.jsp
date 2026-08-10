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
    <link rel="stylesheet" href="assets/css/operaciones.css" />
</head>
<body>

<header class="app-header">
    <a class="brand" href="${pageContext.request.contextPath}/">Event Online</a>
    <nav>
        <a class="active" href="${pageContext.request.contextPath}/admin-servicios">Administración</a>
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
        <a href="${pageContext.request.contextPath}/adminRecintos">Recintos</a>
        <a>Usuarios</a>
        <a class="active" href="admin-servicios.html">Servicios Extra</a>
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
<jsp:include page="alerts.jsp" />
</body>
</html>