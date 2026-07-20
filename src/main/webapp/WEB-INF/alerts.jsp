<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

<%
    String errorMsn = (String) request.getAttribute("error");
    if (errorMsn != null) {
%>
<script>
    Swal.fire({
        icon: 'error',
        title: '¡Atención!',
        text: '<%= errorMsn %>',
        confirmButtonColor: '#7c5315',
        confirmButtonText: 'Entendido',
        backdrop: `rgba(0, 0, 0, 0.4)`
    });
</script>
<%
    }
%>
