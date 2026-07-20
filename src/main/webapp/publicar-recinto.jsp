<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    jakarta.servlet.http.HttpSession sesion = request.getSession(false);
    if (sesion == null || sesion.getAttribute("UsuarioLog") == null) {
        request.setAttribute("error", "Por favor, inicia sesión para publicar tu recinto.");
        request.getRequestDispatcher("login.jsp").forward(request, response);
        return;
    }
%>
<!doctype html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta name="description" content="Publica un recinto nuevo en GEDS." />
    <title>Event Online | Publicar recinto</title>
    <link rel="preconnect" href="https://images.unsplash.com" />
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:wght@600;700&family=Montserrat:wght@400;500;600;700;800&display=swap" rel="stylesheet" />
    <link rel="stylesheet" href="assets/css/publicar.css" />
</head>
<body>
<header class="publish-header">
    <a class="brand" href="index.html">Event Online</a>
    <nav aria-label="Navegacion principal">
        <a href="catalogo.html">Explorar</a>
        <a href="catalogo.html#guardados">Guardados</a>
        <a href="catalogo.html#reservas">Reservas</a>
        <a class="active" href="perfil.html">Perfil</a>
    </nav>
    <div class="header-actions">
        <a href="catalogo.html">Buscar</a>
    </div>
</header>

<main class="publish-shell">
    <section class="publish-intro">
        <h1>Publica tu recinto</h1>
        <p>Comparte la elegancia de tu espacio con nuestra comunidad curada. Completa los detalles para iniciar la revisión del concierge.</p>
    </section>

    <form class="publish-layout" data-publish-form method="post" action="publicar-recinto" enctype="multipart/form-data">
        <section class="form-stack">
            <article class="panel">
                <h2>Identidad del recinto</h2>
                <label class="form-field">
                    <span>Nombre del recinto</span>
                    <input type="text" name="venueName" placeholder="Ej. Villa d'Este Pavilion" required />
                </label>
                <label class="form-field">
                    <span>Ubicación</span>
                    <input type="text" name="location" placeholder="Calle, ciudad, estado" required />
                </label>
                <label class="form-field">
                    <span>Descripción</span>
                    <textarea name="description" placeholder="Describe la historia, estilo arquitectónico y atmósfera..." required></textarea>
                </label>
            </article>

            <article class="panel">
                <h2>Capacidad</h2>
                <div class="capacity-row">
                    <div class="mini-card">
                        <label for="seated">Capacidad máxima del salón:</label>
                        <input type="number" id="seated" name="seated" min="0" placeholder="0" />
                    </div>

                    <div class="mini-card">
                        <label for="precio">Precio base</label>
                        <div class="price-input">
                            <span class="currency-symbol">$</span>
                            <input type="number" id="precio" name="precio" min="0" step="100" placeholder="0" />
                        </div>
                        <small>Por evento</small>
                    </div>
                </div>
            </article>
        </section>

        <aside class="publish-aside">
            <article class="panel photos-panel">
                <h2>Fotos del recinto</h2>
                <label class="upload-box">
                    <input type="file" accept="image/png,image/jpeg" multiple data-photo-input name="photos"/>
                    <strong>Click o arrastra para subir</strong>
                    <span>JPEG o PNG de alta resolución</span>
                </label>
                <div class="photo-grid" data-photo-grid>
                    <div class="photo-thumb filled"></div>
                    <div class="photo-thumb"></div>
                    <div class="photo-thumb"></div>
                </div>
                <button class="primary-button" type="submit">Enviar a revisión</button>
                <p class="aside-note">Tu publicación será revisada por el equipo de planeación en 24 a 48 horas.</p>
            </article>

            <article class="tip">
                <h2>Tip concierge</h2>
                <p>Los recintos con fotografía profesional y descripción clara reciben más solicitudes premium.</p>
            </article>
        </aside>
    </form>
    <p class="form-status" data-form-status role="status" aria-live="polite"></p>
</main>

<footer class="rights-footer">&copy; 2026 Event Online Spaces. Todos los derechos reservados.</footer>
<script src="assets/js/publicar.js"></script>
</body>
<jsp:include page="WEB-INF/alerts.jsp" />
</html>