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
            <a class="brand" href="../index.html">Event Online</a>
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
                Ingresa tu correo electrónico para recibir un código de recuperación.
            </p>

            <form action="${pageContext.request.contextPath}/correoRecuperacion" method="post">

                <div class="form-group">
                    <label for="email">Email</label>

                    <input
                            type="email"
                            name="correo"
                            id="email"
                            placeholder="ejemplo@correo.com"
                            required
                    >
                </div>

                <button type="submit" class="btn-primary">
                    Enviar código de recuperación
                </button>

            </form>



            <div class="login-link">
                ¿Ya recordaste tu contraseña?
                <a href="${pageContext.request.contextPath}/app/login">Iniciar sesión</a>
            </div>

        </div>

    </section>

</div>
<jsp:include page="alerts.jsp" />

</body>
</html>