const uploadBox = document.getElementById("uploadBox");
const fileInput = document.getElementById("fileInput");
const preview = document.getElementById("preview");

uploadBox.addEventListener("click", () => {
    fileInput.click();
});

fileInput.addEventListener("change", mostrarImagenes);

function mostrarImagenes() {

    preview.innerHTML = "";

    Array.from(fileInput.files).forEach(file => {

        const reader = new FileReader();

        reader.onload = function(e){

            const img = document.createElement("img");

            img.src = e.target.result;

            preview.appendChild(img);

        }

        reader.readAsDataURL(file);

    });

}



//menu desplegable WUUU :)
document.addEventListener("DOMContentLoaded", () => {

    const form = document.querySelector("form");

    if (form && fileInput) {
        form.addEventListener("submit", (e) => {
            if (fileInput.files.length === 0) {
                e.preventDefault();

                Swal.fire({
                    icon: 'error',
                    title: '¡Atención!',
                    text: 'Debes subir una fotografía para el servicio.',
                    confirmButtonText: 'Entendido',
                    confirmButtonColor: '#7a5230'
                });
            }
        });
    }

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
});