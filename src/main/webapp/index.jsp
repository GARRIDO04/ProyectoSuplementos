<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Inicio-Suplementos Perrones</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

    <jsp:include page="vistas/navbar.jsp" />

    <div class="container mt-5 text-center">
        <div class="p-5 mb-4 bg-white rounded-3 shadow-sm">
            <div class="container-fluid py-5">
                <h1 class="display-4 fw-bold text-uppercase">Control Suplementos Perrones</h1>
                <p class="col-md-8 mx-auto fs-4 text-muted">
                    Selecciona un módulo en la barra superior para comenzar a operar.
                </p>
                <a href="ControladorProducto" class="btn btn-warning btn-lg mt-3 fw-bold px-5">Ir al Inventario</a>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>