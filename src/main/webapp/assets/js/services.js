const servicios = [
    {
        id: 1,
        nombre: "DJ Profesional",
        categoria: "musica",
        descripcion: "Música personalizada durante todo tu evento.",
        precio: 15000,
        imagen: "https://picsum.photos/600/400?random=1"
    },
    {
        id: 2,
        nombre: "Mariachi",
        categoria: "musica",
        descripcion: "Show tradicional mexicano para bodas y eventos.",
        precio: 12000,
        imagen: "https://picsum.photos/600/400?random=2"
    },
    {
        id: 3,
        nombre: "Banquete Premium",
        categoria: "catering",
        descripcion: "Menú gourmet para eventos exclusivos.",
        precio: 22000,
        imagen: "https://picsum.photos/600/400?random=3"
    },
    {
        id: 4,
        nombre: "Fotografía Profesional",
        categoria: "foto",
        descripcion: "Cobertura completa de tu evento.",
        precio: 18000,
        imagen: "https://picsum.photos/600/400?random=4"
    },
    {
        id: 5,
        nombre: "Arreglos Florales",
        categoria: "decoracion",
        descripcion: "Centros de mesa y decoración personalizada.",
        precio: 10000,
        imagen: "https://picsum.photos/600/400?random=5"
    },
    {
        id: 6,
        nombre: "Iluminación Ambiental",
        categoria: "decoracion",
        descripcion: "Ambientes elegantes para eventos nocturnos.",
        precio: 16000,
        imagen: "https://picsum.photos/600/400?random=6"
    }
];

const contenedor =
    document.getElementById("contenedorServicios");

function renderizarServicios(lista){

    contenedor.innerHTML = "";

    lista.forEach(servicio => {

        contenedor.innerHTML += `

       <article class="card">

    <div class="imagen-container">

        <img src="${servicio.imagen}"
             alt="${servicio.nombre}">

        <button class="btn-favorito"
                onclick="toggleFavorito(${servicio.id}, this)">
            ♡
        </button>

    </div>

    <div class="card-body">

        <span class="badge">
            ${servicio.categoria}
        </span>

        <h3>${servicio.nombre}</h3>

        <p>${servicio.descripcion}</p>

        <div class="card-footer">

            <strong>
                $${servicio.precio.toLocaleString()}
            </strong>

            <div class="acciones-card">

                <button
                    class="btn-detalles"
                    onclick="verDetalles(${servicio.id})">
                    Detalles
                </button>

                <button
                    class="btn-agregar"
                    onclick="agregarEvento(${servicio.id})">
                    Agregar
                </button>

            </div>

        </div>

    </div>

</article>

        `;
    });
}

function toggleFavorito(id, boton){

    boton.classList.toggle("activo");

    if(boton.classList.contains("activo")){
        boton.innerHTML = "♥";
    }else{
        boton.innerHTML = "♡";
    }

    console.log("Favorito:", id);
}

function verDetalles(id){

    const servicio =
        servicios.find(s => s.id === id);

    alert(
        `
${servicio.nombre}

${servicio.descripcion}

Precio:
$${servicio.precio.toLocaleString()}
        `
    );
}

function agregarEvento(id){

    const servicio =
        servicios.find(
            s => s.id === id
        );

    const evento =
        obtenerEvento();

    const existe =
        evento.servicios.some(
            s => s.id === id
        );

    if(existe){

        alert(
            "Este servicio ya fue agregado al evento."
        );

        return;
    }

    evento.servicios.push(servicio);

    guardarEvento(evento);

    actualizarPanel();

    mostrarToast(
        "Servicio agregado",
        servicio.nombre,
        "$" +
        servicio.precio.toLocaleString()
    );
}

function actualizarPanel(){

    const evento =
        obtenerEvento();

    const lista =
        document.getElementById(
            "listaServicios"
        );

    const totalElemento =
        document.getElementById(
            "totalServicio"
        );

    if(!lista || !totalElemento) return;

    lista.innerHTML = "";
    if(evento.servicios.length === 0){

        lista.innerHTML = `

        <div class="carrito-vacio">

            <p>
                Aún no has agregado servicios.
            </p>

        </div>

    `;
    }

    let total = 0;

    evento.servicios.forEach(servicio => {

        total += servicio.precio;

        lista.innerHTML += `

    <div class="item-carrito">

        <div>

            <h4>
                ${servicio.nombre}
            </h4>

            <span>
                $${servicio.precio.toLocaleString()}
            </span>

        </div>

        <button
            class="btn-eliminar"
            onclick="eliminarServicio(${servicio.id})">

            ✖

        </button>

    </div>

`;
    });

    totalElemento.textContent =
        "$" + total.toLocaleString();
}

function eliminarServicio(id){

    const evento =
        obtenerEvento();

    const servicio =
        evento.servicios.find(
            s => s.id === id
        );

    evento.servicios =
        evento.servicios.filter(
            s => s.id !== id
        );

    guardarEvento(evento);

    actualizarPanel();

    if(servicio){

        mostrarToast(
            "Servicio eliminado",
            servicio.nombre
        );
    }
}
function vaciarEvento(){

    const confirmar =
        confirm(
            "¿Deseas eliminar todos los servicios del evento?"
        );

    if(!confirmar) return;

    const evento =
        obtenerEvento();

    evento.servicios = [];

    guardarEvento(evento);

    actualizarPanel();

    mostrarToast(
        "Evento vaciado",
        "Todos los servicios fueron eliminados"
    );
}

function mostrarToast(
    titulo,
    mensaje,
    precio = ""
){

    const toast =
        document.getElementById("toast");

    toast.innerHTML = `

        <div class="toast-header">

            <div class="toast-icon">
                ✓
            </div>

            <div class="toast-title">
                ${titulo}
            </div>

        </div>

        <div class="toast-message">
            ${mensaje}
        </div>

        ${
        precio
            ?
            `
            <div class="toast-price">
                ${precio}
            </div>
            `
            :
            ""
    }

        <div class="toast-progress"></div>

    `;

    toast.classList.add("show");

    clearTimeout(
        toast.timeout
    );

    toast.timeout =
        setTimeout(() => {

            toast.classList.remove(
                "show"
            );

        },3000);
}
renderizarServicios(servicios);

actualizarPanel();