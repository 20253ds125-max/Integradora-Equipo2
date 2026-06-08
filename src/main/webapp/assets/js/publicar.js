const adminStorageKey = "gedsAdminVenues";
const defaultImage = "https://images.unsplash.com/photo-1518005020951-eccb494ad742?auto=format&fit=crop&w=300&q=80";

const form = document.querySelector("[data-publish-form]");
const statusText = document.querySelector("[data-form-status]");
const draftButton = document.querySelector("[data-save-draft]");
const photoInput = document.querySelector("[data-photo-input]");
const photoGrid = document.querySelector("[data-photo-grid]");

function getAdminVenues() {
  return JSON.parse(localStorage.getItem(adminStorageKey) || "[]");
}

function saveAdminVenues(venues) {
  localStorage.setItem(adminStorageKey, JSON.stringify(venues));
}

function venueFromForm(status = "pending") {
  const data = Object.fromEntries(new FormData(form).entries());
  const venues = getAdminVenues();
  return {
    id: Date.now(),
    name: data.venueName,
    city: data.location,
    category: data.services ? "Recinto con servicios" : "Recinto social",
    date: new Date().toLocaleDateString("es-MX", { year: "numeric", month: "short", day: "numeric" }),
    status,
    image: defaultImage,
    description: data.description,
    seated: Number(data.seated || 0),
    standing: Number(data.standing || 0),
    services: new FormData(form).getAll("services"),
    order: venues.length + 1
  };
}

if (form) {
  form.addEventListener("submit", (event) => {
    event.preventDefault();
    const venues = getAdminVenues();
    venues.unshift(venueFromForm("pending"));
    saveAdminVenues(venues);
    statusText.textContent = "Recinto enviado a revision. Ya aparece como pendiente en administracion.";
    form.reset();
  });
}

if (draftButton) {
  draftButton.addEventListener("click", () => {
    const venues = getAdminVenues();
    venues.unshift(venueFromForm("pending"));
    saveAdminVenues(venues);
    statusText.textContent = "Borrador guardado localmente. Puedes verlo en administracion como pendiente.";
  });
}

if (photoInput) {
  photoInput.addEventListener("change", () => {
    const count = photoInput.files.length;
    photoGrid.innerHTML = Array.from({ length: Math.max(3, count) }, (_, index) => {
      return `<div class="photo-thumb ${index < count ? "filled" : ""}"></div>`;
    }).join("");
  });
}
