document.addEventListener("DOMContentLoaded", () => {
    // Seleccionamos los inputs dentro de .code-container
    const inputs = document.querySelectorAll(".code-container input");

    if (!inputs.length) return; // Validación por seguridad si el formulario no existe

    inputs.forEach((input, index) => {
        // 1. Pasar al siguiente input automáticamente
        input.addEventListener("input", (e) => {

            // Si se escribió un número y no es el último campo, salta al siguiente
            if (e.target.value && index < inputs.length - 1) {
                inputs[index + 1].focus();
            }
        });

        // 2. Retroceder al input anterior al presionar la tecla Borrar (Backspace)
        input.addEventListener("keydown", (e) => {
            if (e.key === "Backspace" && !input.value && index > 0) {
                inputs[index - 1].focus();
            }
        });

        // 3. Soporte para pegar el código completo (Ctrl + V)
        input.addEventListener("paste", (e) => {
            e.preventDefault();
            const pasteData = e.clipboardData.getData("text").trim();

            // Si los datos pegados contienen solo dígitos
            if (/^\d+$/.test(pasteData)) {
                const digits = pasteData.split("");

                inputs.forEach((inp, idx) => {
                    if (digits[idx]) {
                        inp.value = digits[idx];
                    }
                });

                // Posiciona el cursor en el último casillero rellenado
                const lastFilledIndex = Math.min(digits.length, inputs.length) - 1;
                if (lastFilledIndex >= 0) {
                    inputs[lastFilledIndex].focus();
                }
            }
        });
    });
});