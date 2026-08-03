<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Confirmar Código - Event Online</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/codigoVerificacion.css?v=1.1">

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:wght@600;700&family=Plus+Jakarta+Sans:wght@400;600;700;800&display=swap" rel="stylesheet">
</head>
<body>
<header class="site-header">
    <div class="brand-group">
        <a class="brand" href="${pageContext.request.contextPath}/">Event Online</a>
    </div>

    <nav class="top-nav" aria-label="Navegación principal">
        <a href="${pageContext.request.contextPath}/catalogo">Recintos</a>
        <a href="${pageContext.request.contextPath}/app/extraServices">Servicios</a>
        <a href="${pageContext.request.contextPath}/app/contacto-equipo">Contactanos</a>
    </nav>

    <div class="header-actions"></div>
</header>


<div class="container">

    <section class="left-panel">
        <div class="overlay"></div>

        <div class="logo">
            <a href="${pageContext.request.contextPath}/">Event Online</a>
        </div>

        <div class="content">
            <h1>Verifica tu identidad de forma segura</h1>
            <p>
                Hemos enviado un código de recuperación a tu correo electrónico.
                Ingresa el código para continuar con el restablecimiento de tu cuenta.
            </p>
        </div>
    </section>

    <section class="right-panel">
        <div class="card">
            <h2>Confirmar código</h2>
            <p class="subtitle">Ingresa el código de 6 dígitos a continuación</p>

            <form action="${pageContext.request.contextPath}/verificarCodigo" method="POST">

                <input type="hidden" name="correo" value="${correo}" >

                <div class="code-container">
                    <input name="digito1" type="text" maxlength="1" autofocus required>
                    <input name="digito2" type="text" maxlength="1" required>
                    <input name="digito3" type="text" maxlength="1" required>
                    <input name="digito4" type="text" maxlength="1" required>
                    <input name="digito5" type="text" maxlength="1" required>
                    <input name="digito6" type="text" maxlength="1" required>
                </div>

                <button type="submit" class="btn">Enviar código</button>

            </form>

            <div class="footer-links">
                ¿No recibiste código? <a href="${pageContext.request.contextPath}/app/restablecer-correo">Reenviar código</a>
            </div>
        </div>
    </section>

</div>
<footer class="catalog-footer legal-only">&copy; 2026 Event Online Spaces. Todos los derechos reservados.</footer>

<jsp:include page="alerts.jsp" />

<script src="${pageContext.request.contextPath}/assets/js/codigoVerificacion.js"></script>

</body>
</html>