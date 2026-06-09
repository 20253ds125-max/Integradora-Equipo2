const EVENTO_KEY = "eventOnlineEvento";

function obtenerEvento() {

    const evento =
        localStorage.getItem(EVENTO_KEY);

    return evento
        ? JSON.parse(evento)
        : {
            recinto: null,
            servicios: [],
            mesas: [],
            total: 0
        };
}

function guardarEvento(evento) {

    localStorage.setItem(
        EVENTO_KEY,
        JSON.stringify(evento)
    );
}