<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta name="description" content="Acceso de usuarios a Event Online." />
    <title>Event Online | Iniciar Sesión</title>

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>

    <link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:wght@500;600;700&family=Montserrat:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="assets/css/auth.css">
</head>
<body class="login-page">

<header class="catalog-header">

    <div class="brand-group">
        <a class="brand" href="index.html">Event Online</a>
    </div>

    <nav class="top-nav" aria-label="Navegación">
        <a href="catalogo.html">Recintos</a>
        <a href="extraServices.html">Servicios</a>
    </nav>

    <div class="header-actions">
        <a class="host-button" href="register.html">Crear cuenta</a>
    </div>

</header>


<main class="login-shell">
    <section class="login-card" aria-labelledby="loginTitle">
        <h1 id="loginTitle">Bienvenido</h1>
        <p>Ingresa tus credenciales para acceder a tu cuenta.</p>

        <form class="auth-form" data-auth-form method="post" action="login">
            <label>
                <span>Email</span>
                <input type="email" name="email" placeholder="nombre@ejemplo.com" required />
            </label>

            <label>
          <span class="split-label">
            Contraseña
            <a href="restore.html">¿Olvidaste tu contraseña?</a>
          </span>
                <span class="password-field">
            <input type="password" name="password" placeholder="••••••••" required data-password-input />
          </span>
            </label>

            <button class="primary-button" type="submit">Iniciar Sesión</button>
        </form>

        <div class="divider"><span>O continúa con</span></div>

        <p class="switch-copy">¿Aún no tienes una cuenta?</p>
        <a class="outline-button" href="registro.jsp">Crear cuenta</a>

        <p class="form-status" data-form-status role="status" aria-live="polite"></p>
    </section>
</main>

<footer class="rights-footer">
    &copy; 2026 Event Online Spaces. Todos los derechos reservados.
</footer>

<script src="assets/js/auth.js"></script>
<jsp:include page="WEB-INF/alerts.jsp" />

</body>
</html>