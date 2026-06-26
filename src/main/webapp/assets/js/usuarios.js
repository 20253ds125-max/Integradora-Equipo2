document.addEventListener('DOMContentLoaded', () => {

    const tbody = document.getElementById('usuariosBody');

    const usuarios = [
        { nombre: 'Juan Pérez', correo: 'juan@example.com', rol: 'Administrador' },
        { nombre: 'Mariana López', correo: 'mariana@email.com', rol: 'Administrador' },
        { nombre: 'Carlos Ruiz', correo: 'carlos@email.com', rol: 'Usuario' },
        { nombre: 'Ana Torres', correo: 'ana@email.com', rol: 'Usuario' }
    ];

    let htmlContenido = '';

    usuarios.forEach(usuario => {
        htmlContenido += `
            <tr>
                <td><strong>${usuario.nombre}</strong></td>
                <td>${usuario.correo}</td>
                <td>${usuario.rol}</td>
                <td style="text-align: right; padding-right: 24px;">
                    <div class="action-row" style="display: inline-block;">
                        <button class="delete">Eliminar</button>
                    </div>
                </td>
            </tr>
        `;
    });

    tbody.innerHTML = htmlContenido;
});