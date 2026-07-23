const form = document.getElementById("resetForm");
const successMessage = document.getElementById("successMessage");

form.addEventListener("submit", (e) => {
    e.preventDefault();

    const email = document.getElementById("email").value.trim();

    if (!email) {
        alert("Ingresa un correo válido");
        return;
    }

    console.log("Solicitud enviada:", email);

    successMessage.style.display = "block";

    form.reset();
});

document.getElementById('resetForm').addEventListener('submit', function(evento) {
    evento.preventDefault();
    window.location.href = 'login.jsp';
});

document.addEventListener('DOMContentLoaded', function() {

    const toggleButtons = document.querySelectorAll('.toggle-password');

    toggleButtons.forEach(button => {
        button.addEventListener('click', function() {

            const input = this.parentElement.querySelector('input');

            if (input) {
                if (input.type === 'password') {
                    input.type = 'text';
                    this.style.color = 'var(--clay, #9d4f38)';
                } else {
                    input.type = 'password';
                    this.style.color = 'var(--muted, #615d57)';
                }
            }
        });
    });
});