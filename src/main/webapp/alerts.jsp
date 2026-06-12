<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

<%
    String errorMsn = (String) request.getAttribute("error");
    if (errorMsn != null) {
%>
<script>
    Swal.fire({
        icon: 'error',
        title: '¡Error!',
        text: '<%= errorMsn %>',
        confirmButtonColor: '#d33',
        confirmButtonText: 'Corregir'
    });
</script>
<%
    }
%>
