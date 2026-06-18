<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Login - Suplementos Perrones</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-dark text-light d-flex align-items-center justify-content-center" style="height: 100vh;">

    <div class="card bg-secondary text-light shadow-lg" style="width: 400px; border-radius: 15px;">
        <div class="card-body p-5 text-center">
            <h3 class="mb-3 fw-bold text-warning text-uppercase">SISTEMA MAESTRO</h3>
            <p class="text-muted mb-4">Introduce tus credenciales de acceso local</p>
            
            <%-- Bloque dinámico para atrapar y pintar errores de login --%>
            <% if (request.getAttribute("error") != null) { %>
                <div class="alert alert-danger p-2 small mb-3" role="alert">
                    <%= request.getAttribute("error") %>
                </div>
            <% } %>

            <form action="ControladorLogin" method="POST">
                <div class="mb-3 text-start">
                    <label class="form-label text-warning fw-bold small text-uppercase">Usuario</label>
                    <input type="text" name="txtuser" class="form-control bg-dark text-light border-secondary" placeholder="Ej. jefe" required>
                </div>
                <div class="mb-4 text-start">
                    <label class="form-label text-warning fw-bold small text-uppercase">Contraseña</label>
                    <input type="password" name="txtpass" class="form-control bg-dark text-light border-secondary" placeholder="********" required>
                </div>
                <button type="submit" name="accion" value="Ingresar" class="btn btn-warning w-100 fw-bold fs-5 shadow-sm py-2">INGRESAR</button>
            </form>
        </div>
    </div>

</body>
</html>