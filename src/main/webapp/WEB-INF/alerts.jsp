<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

<%

    String errorMsn = (String) request.getAttribute("error");

    if (errorMsn == null && session != null) {
        errorMsn = (String) session.getAttribute("error");


        if (errorMsn != null) {
            session.removeAttribute("error");
        }
    }

        if (errorMsn != null) {
%>
<script>
    Swal.fire({
        icon: 'error',
        title: '¡Atención!',
        text: `<%= errorMsn %>`,
        confirmButtonColor: '#7c5315',
        confirmButtonText: 'Entendido',
        backdrop: `rgba(0, 0, 0, 0.4)`
    });
</script>
<%
        }

        String exitoMsn = (String) request.getAttribute("exito");

        if (exitoMsn == null && session != null) {

            exitoMsn = (String) session.getAttribute("exito");

            if (exitoMsn != null) {
                session.removeAttribute("exito");
            }
        }

        if (exitoMsn != null) {
%>
<script>
    Swal.fire({
        icon: 'success',
        title: '¡Operación Exitosa!',
        text: `<%= exitoMsn %>`,
        confirmButtonColor: '#7c5315',
        confirmButtonText: 'Aceptar',
        backdrop: `rgba(0, 0, 0, 0.4)`
    });
</script>
<%
    }
%>