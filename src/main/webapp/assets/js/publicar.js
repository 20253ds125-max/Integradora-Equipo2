
const photoInput = document.querySelector("[data-photo-input]");
const photoGrid = document.querySelector("[data-photo-grid]");

let almacenArchivos = new DataTransfer();

if (photoInput) {
  photoInput.addEventListener("change", (e) => {
    const archivosNuevos = e.target.files;

    Array.from(archivosNuevos).forEach(file => {
      almacenArchivos.items.add(file);
    });

    photoInput.files = almacenArchivos.files;

    renderizarMiniaturas();
  });
}

function renderizarMiniaturas() {
  photoGrid.innerHTML = "";

  Array.from(photoInput.files).forEach((file, indice) => {
    const div = document.createElement("div");
    div.className = "photo-thumb filled";

    div.style.position = "relative";

    const URLtemporal = URL.createObjectURL(file);
    div.style.backgroundImage = `url('${URLtemporal}')`;
    div.style.backgroundSize = "cover";
    div.style.backgroundPosition = "center";

    const botonBorrar = document.createElement("button");
    botonBorrar.type = "button";
    botonBorrar.innerHTML = "&times;";
    botonBorrar.className = "btn-eliminar-foto";

    botonBorrar.addEventListener("click", () => {
      eliminarFoto(indice);
    });

    div.appendChild(botonBorrar);
    photoGrid.appendChild(div);
  });
}

function eliminarFoto(indiceParaEliminar) {
  const nuevoAlmacen = new DataTransfer();

  Array.from(photoInput.files).forEach((file, indiceActual) => {
    if (indiceActual !== indiceParaEliminar) {
      nuevoAlmacen.items.add(file);
    }
  });

  almacenArchivos = nuevoAlmacen;
  photoInput.files = almacenArchivos.files;

  renderizarMiniaturas();
}

//menu desplegable WUUU :)
document.addEventListener("DOMContentLoaded", () => {
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
});