const form = document.getElementById("resetForm");
const successMessage = document.getElementById("successMessage");

form.addEventListener("submit", (e) => {
    e.preventDefault();

    const email = document.getElementById("email").value.trim();

    if (!email) {
        alert("Ingresa un correo válido");
        return;
    }

    // Aquí puedes llamar a tu API
    console.log("Solicitud enviada:", email);

    successMessage.style.display = "block";

    form.reset();
});