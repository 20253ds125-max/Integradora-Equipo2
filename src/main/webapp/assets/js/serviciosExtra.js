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

