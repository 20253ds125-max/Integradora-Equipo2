<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Restablecer Contraseña</title>

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>

    <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@500;700&family=Inter:wght@400;500;600&display=swap" rel="stylesheet">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/restore.css">
</head>
<body>

<div class="container">

    <section class="left-panel">

        <div class="logo">
            <a class="brand" href="${pageContext.request.contextPath}/index.html">Event Online</a>
        </div>

        <div class="hero-content">
            <h1>
                Recupera tu acceso de forma segura.
            </h1>

            <p>
                Restablece tu contraseña y vuelve a gestionar eventos,
                proveedores y experiencias memorables desde un solo lugar.
            </p>
        </div>

    </section>

    <section class="right-panel">

        <div class="card">

            <h2>Restablecer contraseña</h2>

            <p class="subtitle">
                Ingresa la contraseña nueva. Asegúrate de no olvidarla.
            </p>

            <form method="post" id="resetForm" action="${pageContext.request.contextPath}/cambiarContra">

                <input name="correo" type="hidden" value="${correo}" >
                <div class="input-group">
                    <label for="new-password">Nueva contraseña</label>
                    <div class="password-wrapper">
                        <input type="password" id="new-password" name="password" placeholder="••••••••" required />
                    </div>
                </div>

                <button type="submit" class="btn-primary">
                    Guardar nueva contraseña
                </button>

            </form>

            <div class="login-link">
                ¿Ya recordaste tu contraseña?
                <a href="${pageContext.request.contextPath}/app/login">Iniciar sesión</a>
            </div>

        </div>

    </section>

</div>

</body>
</html>