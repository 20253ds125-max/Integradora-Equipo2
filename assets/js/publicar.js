const form = document.querySelector("[data-publish-form]");
const statusText = document.querySelector("[data-form-status]");
const draftButton = document.querySelector("[data-save-draft]");
const photoInput = document.querySelector("[data-photo-input]");
const photoGrid = document.querySelector("[data-photo-grid]");

form.addEventListener("submit", (event) => {
  event.preventDefault();
  statusText.textContent = "Recinto enviado a revision. En una version con backend se guardaria en la base de datos.";
  form.reset();
});

draftButton.addEventListener("click", () => {
  statusText.textContent = "Borrador guardado localmente para continuar despues.";
});

photoInput.addEventListener("change", () => {
  const count = photoInput.files.length;
  photoGrid.innerHTML = Array.from({ length: Math.max(3, count) }, (_, index) => {
    return `<div class="photo-thumb ${index < count ? "filled" : ""}"></div>`;
  }).join("");
});
