<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!doctype html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta name="description" content="Registro de usuarios en GEDS." />
    <title >Event Online | Registro</title>
    <link rel="preconnect" href="https://images.unsplash.com" />
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:wght@600;700&family=Montserrat:wght@400;500;600;700&family=Plus+Jakarta+Sans:wght@600;700&family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/auth.css?v=1.1" />
</head>
<body class="register-page">

<header class="site-header">
    <div class="brand-group">

        <a class="brand" href="${pageContext.request.contextPath}/">Event Online</a>
    </div>

    <nav class="top-nav" aria-label="Navegación">
        <a href="${pageContext.request.contextPath}/catalogo">Recintos</a>
        <a href="app/extraServices">Servicios</a>
        <a href="${pageContext.request.contextPath}/contacto-equipo">Contactanos</a>
    </nav>
</header>


<main class="register-shell">
    <section class="register-visual" aria-label="Inspiracion GEDS">
        <div>
            <h1>Donde el patrimonio encuentra curacion moderna.</h1>
            <p>Unete a una comunidad exclusiva de planners y proveedores dedicados a experiencias memorables.</p>
        </div>
    </section>

    <section class="register-panel" aria-labelledby="registerTitle">
        <div class="register-card">
            <h2 id="registerTitle">Crea tu cuenta</h2>
            <p>Ingresa tus datos para comenzar.</p>

            <form class="auth-form" action="${pageContext.request.contextPath}/registro" method="POST" data-auth-form>
                <label>
                    <span>Nombre completo</span>
                    <input type="text" name="name" placeholder="Ej. Elias Thorne" required />
                </label>
                <label>
                    <span>Email</span>
                    <input type="email" name="email" placeholder="elias@gedsstudio.com" required />
                </label>
                <label>
                    <span>Contraseña</span>
                    <span class="password-field">
                        <input type="password" name="password" placeholder="********" required data-password-input />
                    </span>
                </label>
                <label>
                    <span>Teléfono</span>
                    <input type="tel" name="telefono" placeholder="5586597852"  required>
                </label>
                <label>
                    <span>Ciudad</span>
                    <input type="text" name="ciudad" placeholder="Cuernavaca"  required>
                </label>
                <button class="primary-button" type="submit">Crear cuenta</button>
            </form>
            <p class="switch-copy">Ya tienes una cuenta? <a href="${pageContext.request.contextPath}/app/login">Iniciar Sesión</a></p>
            <p class="form-status" data-form-status role="status" aria-live="polite"></p>
        </div>
    </section>
</main>

<footer class="catalog-footer legal-only">&copy; 2026 Event Online Spaces. Todos los derechos reservados.</footer>


<jsp:include page="alerts.jsp" />
</body>
</html>
