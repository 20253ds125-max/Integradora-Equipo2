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
    <meta name="description" content="Publica un recinto nuevo en Event Online." />
    <title>Event Online | Publicar recinto</title>
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&family=Playfair+Display:wght@600;700&display=swap" rel="stylesheet" />
    <link rel="stylesheet" href="assets/css/serviciosExtra.css" />
</head>
<body>


<header class="site-header" data-header>
    <a class="brand" href="index.html" aria-label="Ir al inicio">Event Online</a>

    <nav class="desktop-nav" aria-label="Navegación principal">
        <a href="catalogo.html">Recintos</a>
        <a href="extraServices.html">Servicios</a>
        <a href="perfil.html">Perfil</a>
    </nav>


</header>


<main class="page">
    <section class="page-header">
        <h1>Publica tu recinto</h1>
        <p>Comparte la elegancia de tu espacio con nuestra comunidad curada. Completa los detalles para iniciar la revisión del concierge.</p>
    </section>

    <form class="grid" method="post" action="publicar-recinto" enctype="multipart/form-data">

        <section class="form-stack">

            <article class="card">
                <h2>Identidad del recinto</h2>
                <label for="venueName">Nombre del recinto</label>
                <input type="text" id="venueName" name="venueName" placeholder="Ej. Villa d'Este Pavilion" required />

                <label for="location">Ubicación</label>
                <input type="text" id="location" name="location" placeholder="Calle, ciudad, estado" required />

                <label for="description">Descripción</label>
                <textarea id="description" name="description" placeholder="Describe la historia, estilo arquitectónico y atmósfera..." required></textarea>
            </article>

            <article class="card">
                <h2>Capacidad y Precio</h2>
                <div class="two-columns">
                    <div>
                        <label for="seated">Capacidad máxima del salón</label>
                        <input type="number" id="seated" name="seated" min="0" placeholder="0 personas" />
                    </div>
                    <div>
                        <label for="precio">Precio base (Por evento)</label>
                        <input type="number" id="precio" name="precio" min="0" step="100" placeholder="$ 0.00" />
                    </div>
                </div>
            </article>

        </section>

        <aside>
            <article class="card">
                <h2>Fotos del recinto</h2>

                <div class="upload-box" id="uploadBox">
                    <input type="file" id="photos" name="photos" accept="image/png,image/jpeg" multiple style="display: none;" />
                    <p>Click o arrastra para subir</p>
                    <small>JPEG o PNG de alta resolución</small>
                </div>

                <div class="preview" id="photoPreview">
                </div>

                <button class="btn-primary" type="submit">Enviar a revisión</button>
                <p style="margin-top: 15px; font-size: 0.85rem; color: var(--muted); text-align: center;">Tu publicación será revisada por el equipo de planeación en 24 a 48 horas.</p>
            </article>

            <article class="tip">
                <h3>Tip concierge</h3>
                <p>Los recintos con fotografía profesional y descripción clara reciben más solicitudes premium.</p>
            </article>
        </aside>

    </form>
</main>

<footer class="main-footer">
    &copy; 2026 Event Online Spaces. Todos los derechos reservados.
</footer>

<script src="assets/js/publicar.js"></script>
<jsp:include page="alerts.jsp" />

</body>
</html>