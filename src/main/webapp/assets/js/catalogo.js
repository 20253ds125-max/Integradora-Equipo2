document.addEventListener("DOMContentLoaded", () => {
    // Referencias al DOM
    const inputSearch = document.querySelector('[data-city-search]');
    const btnTodos = document.getElementById("btnTodos");
    const activeTagSearch = document.getElementById("activeTagSearch");
    const activeTagText = document.getElementById("activeTagText");
    const btnBorrarTagSearch = document.getElementById("btnBorrarTagSearch");

    const cards = document.querySelectorAll('[data-venue-card]');
    const containerResults = document.querySelector('[data-catalog-results]');


    const state = {
        query: "",
        price: null,
        capacity: null
    };

    // Función para actualizar la etiqueta dinámicamente
    function updateTagSearch() {
        if (state.query && activeTagSearch && activeTagText) {
            activeTagText.textContent = state.query;
            activeTagSearch.style.display = "inline-flex";
        } else if (activeTagSearch) {
            activeTagSearch.style.display = "none";
        }
    }


    function filtrarRecintosBD() {
        let visibles = 0;

        cards.forEach((card) => {
            const name = card.dataset.name || "";
            const location = card.dataset.location || "";
            const price = parseFloat(card.dataset.price) || 0;
            const capacity = parseInt(card.dataset.capacity, 10) || 0;

            // 1. Filtro por búsqueda de texto (nombre o ubicación)
            const q = state.query.toLowerCase().trim();
            const matchesQuery = !q || name.includes(q) || location.includes(q);

            // 2. Filtro por Precio
            let matchesPrice = true;
            if (state.price) {
                if (state.price === "150") matchesPrice = price <= 150;
                else if (state.price === "500") matchesPrice = price <= 500;
                else if (state.price === "900") matchesPrice = price <= 900;
                else if (state.price === "2000") matchesPrice = price > 900;
            }

            // 3. Filtro por Capacidad
            let matchesCapacity = true;
            if (state.capacity) {
                if (state.capacity === "50") matchesCapacity = capacity <= 50;
                else if (state.capacity === "150") matchesCapacity = capacity > 50 && capacity <= 150;
                else if (state.capacity === "300") matchesCapacity = capacity > 150 && capacity <= 300;
                else if (state.capacity === "999") matchesCapacity = capacity > 300;
            }

            // Mostrar u ocultar la tarjeta de la BD
            if (matchesQuery && matchesPrice && matchesCapacity) {
                card.style.display = "";
                visibles++;
            } else {
                card.style.display = "none";
            }
        });

        updateTagSearch();
    }

    // Evento: Escribir en el campo de búsqueda
    if (inputSearch) {
        inputSearch.addEventListener("input", (e) => {
            state.query = e.target.value;
            filtrarRecintosBD();
        });

        // Evento para limpiar la búsqueda con la (X) nativa del input
        inputSearch.addEventListener("search", () => {
            state.query = inputSearch.value;
            filtrarRecintosBD();
        });
    }

    // Evento: Borrar búsqueda desde la etiqueta (X)
    if (btnBorrarTagSearch) {
        btnBorrarTagSearch.addEventListener("click", () => {
            state.query = "";
            if (inputSearch) inputSearch.value = "";
            filtrarRecintosBD();
        });
    }

    // Evento: Botón "Todos los recintos"
    if (btnTodos) {
        btnTodos.addEventListener("click", () => {
            state.query = "";
            state.price = null;
            state.capacity = null;

            if (inputSearch) inputSearch.value = "";

            // Limpiar estilos activos de los botones
            document.querySelectorAll('[data-price-filters] button, [data-capacity-filters] button').forEach((btn) => {
                btn.classList.remove("active");
                btn.style.fontWeight = "";
                btn.style.borderColor = "";
            });

            filtrarRecintosBD();
        });
    }
});