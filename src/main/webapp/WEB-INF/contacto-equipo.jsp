<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Contactar Equipo</title>

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>

    <link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:wght@600;700&family=Montserrat:wght@400;500;600;700&family=Plus+Jakarta+Sans:wght@600;700&family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/contacto-equipo.css?v=1.2" /></head>
<body>
<header class="site-header">
    <div class="brand-group">
        <a class="brand" href="${pageContext.request.contextPath}/">Event Online</a>
    </div>

    <nav class="top-nav" aria-label="Navegación principal">
        <a href="${pageContext.request.contextPath}/catalogo">Recintos</a>
        <a href="${pageContext.request.contextPath}/extraServices">Servicios</a>
        <a class="active" href="${pageContext.request.contextPath}/app/contacto-equipo">Contactanos</a>
    </nav>

    <div class="header-actions">
        <button class="icon-button menu-toggle" type="button" data-menu-toggle aria-label="Abrir menú">
            <span aria-hidden="true"></span>
        </button>
    </div>

</header>

<nav class="mobile-nav" data-mobile-nav aria-label="Navegación móvil">
    <c:if test="${empty sessionScope.UsuarioLog}">
        <a href="${pageContext.request.contextPath}/app/login">Iniciar sesión o registrarte</a>
    </c:if>
    <a href="${pageContext.request.contextPath}/contacto-equipo">Contacta al equipo</a>
    <c:if test="${sessionScope.UsuarioLog.rol eq 'ADMIN' }">
        <a href="${pageContext.request.contextPath}/adminRecintos">Administrador</a>
    </c:if>
    <c:if test="${not empty sessionScope.UsuarioLog}">
        <a href="${pageContext.request.contextPath}/mi-carrito-de-compra" >Carrito</a>
    </c:if>
    <c:if test="${not empty sessionScope.UsuarioLog}">
        <a href="${pageContext.request.contextPath}/cerrarSesion" id="cerrarSe">Cerrar sesion</a>
    </c:if>

</nav>


<section class="team-section">

    <div class="team-header">
        <span>SOBRE EL EQUIPO</span>

        <h2>
            Conoce al equipo detrás de Event Online
        </h2>

        <p>
            Somos estudiantes apasionados por el desarrollo de software y la creación
            de experiencias digitales modernas para la gestión de eventos y reservas.
        </p>
    </div>

    <div class="team-container">

        <div class="team-card">

            <div class="team-image">
                <img src="${pageContext.request.contextPath}/assets/team/ale1.jpeg" alt="Alejandro Campos">
            </div>

            <div class="team-info">
                <h3>Alejandro Campos</h3>

                <p>Base de datos</p>

                <a href="mailto:campos11@gmail.com">
                    campos11@gmail.com
                </a>
            </div>

        </div>

        <div class="team-card">

            <div class="team-image">
                <img src="${pageContext.request.contextPath}/assets/team/palomita.jpeg" alt="Paloma Del Rio">
            </div>

            <div class="team-info">
                <h3>Paloma Del Rio</h3>

                <p>UI/UX Designer</p>

                <a href="mailto:palomadr@gmail.com">
                    palomadr@gmail.com
                </a>
            </div>

        </div>
        <div class="team-card">

            <div class="team-image">
                <img src="${pageContext.request.contextPath}/assets/team/io2.jpeg" alt="Marina Flores">            </div>

            <div class="team-info">
                <h3>Marina Flores</h3>

                <p>Frontend Developer</p>

                <a href="mailto:mari.sea@gmail.com">
                    mar.sea@gmail.com
                </a>
            </div>

        </div>

        <div class="team-card">

            <div class="team-image">
                <<img src="${pageContext.request.contextPath}/assets/team/jenni.jpeg" alt="Jennifer Martínez">
            </div>

            <div class="team-info">
                <h3>Jennifer Martínez</h3>

                <p>UX</p>

                <a href="mailto:jenni.mtz@gmail.com">
                    jenni.mtz@gmail.com
                </a>
            </div>

        </div>

        <div class="team-card">

            <div class="team-image">
                <img src="${pageContext.request.contextPath}/assets/team/emi.jpeg" alt="Emiliano Hernández">
            </div>

            <div class="team-info">
                <h3>Emiliano Hernández</h3>

                <p>Backend</p>

                <a href="mailto:emiliano.hdz@gmail.com">
                    emiliano.hdz@gmail.com
                </a>
            </div>

        </div>

        <div class="team-card">

            <div class="team-image">
                <img src="${pageContext.request.contextPath}/assets/team/luis.jpeg" alt="Luis Camacho">
            </div>

            <div class="team-info">
                <h3>Luis Camacho</h3>

                <p>Base de Datos & Backend</p>

                <a href="mailto:luis.camd@gmail.com">
                    luis.camd@gmail.com
                </a>
            </div>
        </div>
    </div>

</section>
<footer class="main-footer">© 2026 Event Online. Todos los derechos reservados.</footer>

<script>
    //menu desplegable WUUU :)
    document.addEventListener("DOMContentLoaded", () => {
        const menuToggle = document.querySelector("[data-menu-toggle]");
        const mobileNav = document.querySelector("[data-mobile-nav]");

        if (menuToggle && mobileNav) {
            menuToggle.addEventListener("click", (e) => {
                e.stopPropagation();
                mobileNav.classList.toggle("open");
                document.body.classList.toggle("menu-open");
            });

            mobileNav.addEventListener("click", (e) => {
                if (e.target.tagName === "A") {
                    mobileNav.classList.remove("open");
                    document.body.classList.remove("menu-open");
                }
            });

            document.addEventListener("click", (e) => {
                if (!mobileNav.contains(e.target) && !menuToggle.contains(e.target)) {
                    mobileNav.classList.remove("open");
                    document.body.classList.remove("menu-open");
                }
            });
        }
    });
</script>
</body>
</html>

