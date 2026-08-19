
const menuToggle = document.querySelector("[data-menu-toggle]");
const mobileNav = document.querySelector("[data-mobile-nav]");


menuToggle?.addEventListener("click", () => {
    mobileNav.classList.toggle("open");
    document.body.classList.toggle("menu-open");
});

mobileNav?.addEventListener("click", (e) => {
    if (e.target.tagName === "A") {
        mobileNav.classList.remove("open");
        document.body.classList.remove("menu-open");
    }
});

const cerrarSe = document.getElementById("cerrarSe");

if(cerrarSe){
    cerrarSe.addEventListener('click',function (e){
        e.preventDefault();

        const direccion = this.getAttribute("href");
        Swal.fire({
            title: '¿Cerrar sesión?',
            text: '¿Estás seguro de que deseas salir de tu cuenta?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Sí, salir',
            cancelButtonText: 'Cancelar',
            confirmButtonColor: '#855221',
            cancelButtonColor: '#6c757d',
            borderRadius: '12px'
        }).then((result) => {
            if (result.isConfirmed) {
                window.location.href = direccion;
            }
        });
    });
}

if (fechaInput) {
    ["change", "input"].forEach(nombreEvento => {
        fechaInput.addEventListener(nombreEvento, function () {
            const valor = this.value.trim();

            if (!valor) return;

            let fechaSeleccionada;

            if (valor.includes("-")) {
                const partes = valor.split("-");
                fechaSeleccionada = new Date(partes[0], partes[1] - 1, partes[2]);
            } else if (valor.includes("/")) {
                const partes = valor.split("/");
                fechaSeleccionada = new Date(partes[2], partes[1] - 1, partes[0]);
            } else {
                fechaSeleccionada = new Date(valor);
            }

            if (isNaN(fechaSeleccionada.getTime())) return;

            const manana = new Date();
            manana.setDate(manana.getDate() + 1);
            manana.setHours(0, 0, 0, 0);

            if (fechaSeleccionada < manana) {
                Swal.fire({
                    title: '¡Atención!',
                    text: 'La fecha del evento debe ser a partir del día de mañana. Por favor, selecciona una fecha válida.',
                    icon: 'warning',
                    confirmButtonText: 'Entendido',
                    confirmButtonColor: '#855221',
                    borderRadius: '12px'
                });

                this.value = "";
            }
        });
    });
}






