<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%
    jakarta.servlet.http.HttpSession sesion = request.getSession(false);
    if (sesion == null || sesion.getAttribute("UsuarioLog") == null) {
        request.setAttribute("error", "Por favor, inicia sesión para publicar tu recinto.");
        request.getRequestDispatcher("login.jsp").forward(request, response);
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Publicar Servicio Extra</title>
    <link rel="preconnect" href="https://images.unsplash.com" />
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link
            href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:wght@600;700&family=Montserrat:wght@400;500;600;700;800&display=swap"
            rel="stylesheet"
    />

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/serviciosExtra.css">
</head>
<body>

<header class="site-header">
    <div class="brand-group">
        <a class="brand" href="${pageContext.request.contextPath}/">Event Online</a>
    </div>

    <nav class="top-nav" aria-label="Navegación">
        <a href="${pageContext.request.contextPath}/catalogo">Recintos</a>
        <a class="active" href="${pageContext.request.contextPath}/extraServices">Servicios</a>
        <a href="${pageContext.request.contextPath}/app/perfil">Perfil</a>
    </nav>
    <div class="header-actions"></div>
</header>

<main class="page">

    <section class="page-header">
        <h1>Publica tus servicios extra</h1>
        <p>
            Complementa la experiencia de los clientes con servicios exclusivos
            para eventos memorables.
        </p>
    </section>

    <form action="${pageContext.request.contextPath}/publicarServicio" method="POST" enctype="multipart/form-data">
        <div class="grid">

            <div class="left-column">

                <div class="card">
                    <h2>Información del servicio</h2>

                    <label for="nombreServicio">Nombre del servicio</label>
                    <input type="text" id="nombreServicio" name="nombreServicio" placeholder="Ej. Fotografía Premium" required>

                    <label for="ubicacionServicio">Ubicacion</label>
                    <input type="text" id="ubicacionServicio" name="ubicacionServicio" placeholder="CDMX" required>

                    <label for="tipoServicio">Tipo de servicio</label>
                    <select id="tipoServicio" name="tipoServicio" required>
                        <option value="Fotografía">Fotografía</option>
                        <option value="Video">Video</option>
                        <option value="DJ">DJ</option>
                        <option value="Música en vivo">Música en vivo</option>
                        <option value="Decoración">Decoración</option>
                        <option value="Catering">Catering</option>
                        <option value="Iluminación">Iluminación</option>
                        <option value="Otro">Otro</option>
                    </select>

                    <label for="descripcion">Descripción</label>
                    <textarea id="descripcion" name="descripcion" placeholder="Describe el servicio..." required></textarea>
                </div>

                <div class="card">
                    <h2>Detalles comerciales</h2>

                    <div class="two-columns">
                        <div>
                            <label for="precio">Precio base</label>
                            <input type="number" id="precio" name="precio" step="0.01" placeholder="$0" required>
                        </div>
                    </div>
                </div>

            </div>

            <div class="right-column">

                <div class="card">
                    <h2>Fotos del servicio</h2>

                    <div class="upload-box" id="uploadBox">
                        <input type="file" id="fileInput" name="fotos" accept="image/jpeg, image/png" hidden>
                        <p>Click o arrastra para subir</p>
                        <small>JPEG o PNG de alta resolución</small>
                    </div>

                    <div class="preview" id="preview"></div>

                    <button type="submit" class="btn-primary">
                        Enviar revisión
                    </button>
                </div>

                <div class="tip">
                    <h3>Tip concierge</h3>
                    <p>
                        Los servicios con fotografías profesionales y descripciones
                        detalladas reciben más solicitudes de reserva.
                    </p>
                </div>

            </div>

        </div>
    </form>

</main>

<footer class="main-footer">© 2026 Event Online. Todos los derechos reservados.</footer>

<script src="${pageContext.request.contextPath}/assets/js/serviciosExtra.js"></script>
<jsp:include page="alerts.jsp" />
</body>
</html>