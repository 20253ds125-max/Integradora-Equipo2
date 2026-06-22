function obtenerPaginaActual() {

    const ruta = window.location.pathname;

    return ruta.substring(
        ruta.lastIndexOf("/") + 1
    );
}

function generarMenu() {

    const nav =
        document.getElementById("mainNav");

    if (!nav) return;

    const paginaActual =
        obtenerPaginaActual();

    const enlaces = [

        {
            nombre: "Explorar",
            href: "catalogo.html"
        },

        {
            nombre: "Servicios",
            href: "extraServices.html"
        },


        {
            nombre: "Tickets",
            href: "ticket.html"
        },

        {
            nombre: "Favoritos",
            href: "favoritos.html"
        }
    ];

    nav.innerHTML = enlaces.map(enlace => `

        <a
            href="${enlace.href}"
            class="${
        paginaActual === enlace.href
            ? "active"
            : ""
    }">

            ${enlace.nombre}

        </a>

    `).join("");
}

document.addEventListener(
    "DOMContentLoaded",
    async () => {

        const container =
            document.getElementById(
                "navbar-container"
            );

        if (!container) return;

        const response =
            await fetch(
                "components/navbar.html"
            );

        const html =
            await response.text();

        container.innerHTML =
            html;

        generarMenu();
    }
);