<%@page import="modelo.Usuario"%>
<%
    // CANDADO DE SEGURIDAD: Si no hay identificador, lo mandamos al login
    Usuario userActivo = (Usuario) session.getAttribute("usuarioLogueado");
    if (userActivo == null) {
        response.sendRedirect("login.jsp");
        return; 
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Inicio - Suplementos Perrones</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-dark text-light">

    <jsp:include page="vistas/navbar.jsp" />

    <div class="container mt-5 text-center">
        <div class="p-5 mb-4 bg-secondary rounded-3 shadow-lg border-0" style="border-radius: 15px;">
            <div class="container-fluid py-3">
                <h1 class="display-4 fw-bold text-warning text-uppercase">Control Suplementos Perrones</h1>
                <p class="col-md-8 mx-auto fs-5 text-light mt-3">
                    Bienvenido, <strong class="text-warning text-uppercase"><%= userActivo.getUsername() %></strong>. 
                    Selecciona un módulo operativo para comenzar.
                </p>
            </div>
        </div>

        <div class="row g-4 justify-content-center">

            <div class="col-md-4">
                <div class="card bg-secondary border-0 shadow text-center h-100" style="border-radius: 12px;">
                    <div class="card-body py-4">
                        <h1 class="display-5 mb-2">🛒</h1>
                        <h5 class="fw-bold text-light">PUNTO DE VENTA</h5>
                        <a href="nuevaVenta.jsp" class="btn btn-warning w-100 fw-bold mt-3">IR A VENTAS</a>
                    </div>
                </div>
            </div>

            <div class="col-md-4">
                <div class="card bg-secondary border-0 shadow text-center h-100" style="border-radius: 12px;">
                    <div class="card-body py-4">
                        <h1 class="display-5 mb-2">👥</h1>
                        <h5 class="fw-bold text-light">CLIENTES</h5>
                        <a href="ControladorCliente?accion=Listar" class="btn btn-info w-100 fw-bold mt-3">VER DIRECTORIO</a>
                    </div>
                </div>
            </div>

            <% if (userActivo.getRol().equalsIgnoreCase("admin")) { %>
                <div class="col-md-4">
                    <div class="card bg-secondary border-0 shadow text-center h-100" style="border-radius: 12px;">
                        <div class="card-body py-4">
                            <h1 class="display-5 mb-2">📦</h1>
                            <h5 class="fw-bold text-light">SURTIR STOCK</h5>
                            <a href="nuevaCompra.jsp" class="btn btn-success w-100 fw-bold mt-3">IR A COMPRAS</a>
                        </div>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="card bg-secondary border-0 shadow text-center h-100" style="border-radius: 12px;">
                        <div class="card-body py-4">
                            <h1 class="display-5 mb-2">🗃</h1>
                            <h5 class="fw-bold text-light">INVENTARIO</h5>
                            <a href="ControladorProducto?accion=Listar" class="btn btn-light w-100 fw-bold mt-3 text-dark">GESTIONAR PRODUCTOS</a>
                        </div>
                    </div>
                </div>
            <% } %>

        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>