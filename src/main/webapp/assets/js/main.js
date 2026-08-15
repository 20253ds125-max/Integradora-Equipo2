
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






