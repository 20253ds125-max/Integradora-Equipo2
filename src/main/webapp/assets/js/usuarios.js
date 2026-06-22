const usuarios = [
    {
        nombre:"Mariana López",
        correo:"mariana@email.com",
        rol:"Administrador",
        status:"Activo"
    },

    {
        nombre:"Carlos Ruiz",
        correo:"carlos@email.com",
        rol:"Usuario",
        status:"Activo"
    },

    {
        nombre:"Ana Torres",
        correo:"ana@email.com",
        rol:"Usuario",
        status:"Inactivo"
    }
];

const tbody =
    document.getElementById("usuariosBody");

usuarios.forEach(usuario => {

    tbody.innerHTML += `
    
        <tr>

            <td>${usuario.nombre}</td>

            <td>${usuario.correo}</td>

            <td>${usuario.rol}</td>

            <td>
                <span class="status ${
        usuario.status === "Activo"
            ? "active-status"
            : "inactive-status"
    }">
                    ${usuario.status}
                </span>
            </td>

            <td>
                <button class="action-btn">
                    Editar
                </button>
            </td>

        </tr>
    
    `;

});