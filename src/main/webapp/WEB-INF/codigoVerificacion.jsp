<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Confirmar Código - Event Online</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/codigoVerificacion.css">

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@500;600;700&family=Inter:wght@400;500;600&display=swap" rel="stylesheet">
</head>
<body>

<div class="container">

    <section class="left-panel">
        <div class="overlay"></div>

        <div class="logo">
            <a href="${pageContext.request.contextPath}/index.html">Event Online</a>
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

</body>
</html>