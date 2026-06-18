<%@page import="modelo.Producto"%>
<%@page import="modelo.ProductoDAO"%>
<%@page import="modelo.Proveedor"%>
<%@page import="modelo.ProveedorDAO"%>
<%@page import="modelo.DetalleCompra"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Surtir Inventario - Sistema Maestro</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-dark text-light">

    <div class="container-fluid p-4">
        <div class="d-flex justify-content-between align-items-center mb-4 border-bottom border-secondary pb-3">
            <h2 class="fw-bold text-success text-uppercase m-0">📦 MÓDULO DE COMPRAS (SURTIDO)</h2>
            <div>
                <a href="index.jsp" class="btn btn-outline-light me-2">Volver al Panel</a>
            </div>
        </div>

        <%-- Alertas Dinámicas --%>
        <% if (request.getAttribute("mensaje") != null) { %>
            <div class="alert alert-success alert-dismissible fade show bg-success text-light border-0">
                <%= request.getAttribute("mensaje") %>
            </div>
        <% } %>
        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-danger alert-dismissible fade show bg-danger text-light border-0">
                <%= request.getAttribute("error") %>
            </div>
        <% } %>

        <div class="row g-4">
            <div class="col-md-4">
                
                <div class="card bg-secondary text-light mb-4 shadow border-0" style="border-radius: 12px;">
                    <div class="card-body p-4">
                        <h5 class="fw-bold text-success mb-3 text-uppercase">1. Agregar Mercancía</h5>
                        <form action="ControladorCompra" method="POST">
                            <div class="mb-3">
                                <label class="form-label small text-uppercase fw-bold">Producto a surtir</label>
                                <select name="idProducto" class="form-select bg-dark text-light border-secondary" required>
                                    <option value="">-- Elige el producto --</option>
                                    <%
                                        ProductoDAO prodDAO = new ProductoDAO();
                                        List<Producto> listaProd = prodDAO.listarProductos();
                                        for (Producto p : listaProd) {
                                    %>
                                        <option value="<%= p.getIdProducto() %>">
                                            <%= p.getNombre() %> (Stock Actual: <%= p.getStock() %>)
                                        </option>
                                    <% } %>
                                </select>
                            </div>
                            <div class="row mb-3">
                                <div class="col-6">
                                    <label class="form-label small text-uppercase fw-bold">Cant. Entrante</label>
                                    <input type="number" name="cantidad" class="form-control bg-dark text-light border-secondary" min="1" value="1" required>
                                </div>
                                <div class="col-6">
                                    <label class="form-label small text-uppercase fw-bold">Costo U. ($)</label>
                                    <input type="number" step="0.01" name="precioCompra" class="form-control bg-dark text-light border-secondary" min="0" placeholder="Ej. 150.50" required>
                                </div>
                            </div>
                            <button type="submit" name="accion" value="AgregarCarrito" class="btn btn-success w-100 fw-bold">
                                + AÑADIR A LA LISTA
                            </button>
                        </form>
                    </div>
                </div>

                <div class="card bg-secondary text-light shadow border-0" style="border-radius: 12px;">
                    <div class="card-body p-4">
                        <h5 class="fw-bold text-success mb-3 text-uppercase">2. Proveedor</h5>
                        <form action="ControladorCompra" method="POST">
                            <div class="mb-4">
                                <label class="form-label small text-uppercase fw-bold">Selecciona Proveedor</label>
                                <select name="idProveedor" class="form-select bg-dark text-light border-secondary" required>
                                    <option value="">-- Proveedor --</option>
                                    <%
                                        ProveedorDAO provDAO = new ProveedorDAO();
                                        List<Proveedor> listaProv = provDAO.listarProveedores(); // Asegúrate de tener este método
                                        for (Proveedor pr : listaProv) {
                                    %>
                                        <option value="<%= pr.getIdProveedor() %>"><%= pr.getNombre() %></option>
                                    <% } %>
                                </select>
                            </div>
                            
                            <div class="row g-2">
                                <div class="col-6">
                                    <a href="ControladorCompra?accion=CancelarCompra" class="btn btn-outline-danger w-100 fw-bold">CANCELAR</a>
                                </div>
                                <div class="col-6">
                                    <button type="submit" name="accion" value="GenerarCompra" class="btn btn-primary w-100 fw-bold">INGRESAR STOCK</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

            </div>

            <div class="col-md-8">
                <div class="card bg-secondary text-light h-100 shadow border-0" style="border-radius: 12px;">
                    <div class="card-body p-4 d-flex flex-column justify-content-between">
                        <div>
                            <h5 class="fw-bold text-success mb-3 text-uppercase">📋 Lista de Ingresos</h5>
                            <div class="table-responsive">
                                <table class="table table-dark table-hover table-striped align-middle m-0">
                                    <thead>
                                        <tr class="text-success small text-uppercase">
                                            <th>#</th>
                                            <th>Producto</th>
                                            <th class="text-center">Cant. Nueva</th>
                                            <th class="text-end">Costo Compra</th>
                                            <th class="text-end">Subtotal</th>
                                            <th class="text-center">Quitar</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <%
                                            List<DetalleCompra> car = (List<DetalleCompra>) session.getAttribute("carritoCompra");
                                            double totalInversion = 0;
                                            int cont = 0;
                                            if (car != null && !car.isEmpty()) {
                                                for (DetalleCompra det : car) {
                                                    double importe = det.getCantidad() * det.getPrecioCompra();
                                                    totalInversion += importe;
                                                    
                                                    Producto prodAux = prodDAO.buscarProducto(det.getIdProducto());
                                                    String nombreP = (prodAux != null) ? prodAux.getNombre() : "Prod #" + det.getIdProducto();
                                        %>
                                        <tr>
                                            <td><%= ++cont %></td>
                                            <td><%= nombreP %></td>
                                            <td class="text-center"><span class="badge bg-success text-dark fw-bold px-3">+<%= det.getCantidad() %></span></td>
                                            <td class="text-end">$<%= String.format("%.2f", det.getPrecioCompra()) %></td>
                                            <td class="text-end fw-bold text-info">$<%= String.format("%.2f", importe) %></td>
                                            <td class="text-center">
                                                <a href="ControladorCompra?accion=EliminarDelCarrito&index=<%= cont - 1 %>" class="btn btn-sm btn-outline-danger py-0 px-2">×</a>
                                            </td>
                                        </tr>
                                        <% 
                                                }
                                            } else {
                                        %>
                                        <tr>
                                            <td colspan="6" class="text-center text-muted py-4">No hay productos en la lista de surtido</td>
                                        </tr>
                                        <% } %>
                                    </tbody>
                                </table>
                            </div>
                        </div>

                        <div class="border-top border-secondary pt-3 mt-4 text-end">
                            <span class="fw-bold text-success text-uppercase me-3">Total Inversión:</span>
                            <span class="fs-4 fw-bold text-light">$<%= String.format("%.2f", totalInversion) %></span>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>