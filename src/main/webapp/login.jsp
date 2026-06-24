<!doctype html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta name="description" content="Acceso de usuarios a Event Online." />
    <title>Event Online | Login</title>
    <link rel="preconnect" href="https://images.unsplash.com" />
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:wght@600;700&family=Montserrat:wght@400;500;600;700;800&display=swap" rel="stylesheet" />
    <link rel="stylesheet" href="assets/css/auth.css" />
</head>
<header>
    <a class="brand" href="index.html">Event Online</a>
</header>
<body class="login-page">
<main class="login-shell">
    <section class="login-card" aria-labelledby="loginTitle">
        <h1 id="loginTitle">Bienvenido</h1>
        <p>Ingresa tus credenciales para acceder a EO.</p>
        <form class="auth-form" data-auth-form>
            <label>
                <span>Email</span>
                <input type="email" name="email" placeholder="name@ejemplo.com" required />
            </label>
            <label>
                <span class="split-label">Password <a href="#">Olvide mi contraseña</a></span>
                <span class="password-field">
              <input type="password" name="password" placeholder="********" required data-password-input />

            </span>
            </label>
            <button class="primary-button" type="submit">Iniciar Sesión</button>
        </form>
        <div class="divider"><span>O continua con</span></div>
        <p class="switch-copy">No tienes una cuenta?</p>
        <a class="outline-button" href="registro.html">Crear cuenta</a>
        <p class="form-status" data-form-status role="status" aria-live="polite"></p>
    </section>
</main>

<footer class="rights-footer">&copy; 2026 Event Online Spaces. Todos los derechos reservados.</footer>
<script src="assets/js/auth.js"></script>
</body>
</html>

