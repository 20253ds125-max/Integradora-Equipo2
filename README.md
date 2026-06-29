# Event Online - Gestión de Eventos Sociales

GEDS es una plataforma web enfocada en la gestión, exploración y reservación de recintos para eventos sociales.
El sistema permite a los usuarios descubrir espacios, administrar favoritos, realizar reservas, gestionar pagos y organizar invitados de manera interactiva.

---

## Funcionalidades principales

* Exploración de recintos para eventos sociales.
* Sistema de favoritos y carrito de compra.
* Búsqueda avanzada con filtros dinámicos.
* Gestión de perfil de usuario.
* Publicación y administración de recintos.
* Organización de invitados mediante asignación de mesas.
* Flujo de pago con depósito de garantía.
* Panel administrativo para control de recintos.

---

## Vistas disponibles

### Usuario

* `index.html`
  Página principal con recintos destacados y acceso rápido a favoritos.

* `catalogo.html`
  Catálogo de recintos con búsqueda y filtros por:

    * tipo de evento
    * capacidad
    * rango de precio

* `detalle-recinto.html`
  Vista detallada del recinto con:

    * carrusel de imágenes
    * descripción
    * amenidades
    * disponibilidad
    * servicios incluidos

* `perfil.html`
  Perfil del usuario con:

    * datos personales
    * reservas realizadas
    * favoritos
    * carrito de compra

* `mi-carrito-de-compra.html`
  Resumen del recinto y servicios seleccionados antes del pago.

* `pago.html`
  Método de pago con cálculo automático del:

    * subtotal
    * depósito de garantía del 30%
    * total estimado

* `mesas.html`
  Organización de invitados mediante sistema drag and drop para asignación de mesas.

---

### Acceso y autenticación

* `login.html`
  Inicio de sesión de usuarios.

* `registro.html`
  Registro de cuentas para planners o proveedores.

---

### Administración y proveedores

* `publicar-recinto.html`
  Formulario para registrar nuevos recintos.

* `admin.html`
  Panel administrativo para:

    * aceptar recintos
    * editar información
    * eliminar publicaciones

---

## Tecnologías utilizadas

* HTML5
* CSS3
* JavaScript Vanilla

---

## Estructura del proyecto

```plaintext
assets/
│
├── css/
│   ├── estilos de cada vista
│
├── js/
│   ├── lógica e interacciones
│   └── datos de ejemplo
```

---

## Cómo ejecutar el proyecto

1. Descarga o clona el repositorio.
2. Abre el archivo `index.html` en tu navegador.
3. Navega entre las distintas vistas del sistema.

---

## Estado del proyecto

Proyecto académico en desarrollo orientado a la simulación de una plataforma integral para la gestión de eventos sociales.
