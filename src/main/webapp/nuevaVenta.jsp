<%@page import="modelo.Producto"%>
<%@page import="modelo.ProductoDAO"%>
<%@page import="modelo.Cliente"%>
<%@page import="modelo.ClienteDAO"%>
<%@page import="modelo.DetalleVenta"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Punto de Venta - Sistema Maestro</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-dark text-light">

    <div class="container-fluid p-4">
        <div class="d-flex justify-content-between align-items-center mb-4 border-bottom border-secondary pb-3">
            <h2 class="fw-bold text-warning text-uppercase m-0">⚡ MÓDULO DE VENTAS</h2>
            <div>
                <a href="index.jsp" class="btn btn-outline-light me-2">Volver al Panel</a>
                <a href="ControladorLogin?accion=Salir" class="btn btn-danger">Cerrar Sesión</a>
            </div>
        </div>

        <%-- Alertas de Éxito o Errores --%>
        <% if (request.getAttribute("mensaje") != null) { %>
            <div class="alert alert-success alert-dismissible fade show bg-success text-light border-0" role="alert">
                <%= request.getAttribute("mensaje") %>
            </div>
        <% } %>
        <% if (request.getAttribute("error") != null || request.getAttribute("errorStock") != null) { %>
            <div class="alert alert-danger alert-dismissible fade show bg-danger text-light border-0" role="alert">
                <%= (request.getAttribute("error") != null) ? request.getAttribute("error") : request.getAttribute("errorStock") %>
            </div>
        <% } %>

        <div class="row g-4">
            <div class="col-md-4">
                
                <div class="card bg-secondary text-light mb-4 shadow border-0" style="border-radius: 12px;">
                    <div class="card-body p-4">
                        <h5 class="fw-bold text-warning mb-3 text-uppercase">1. Seleccionar Producto</h5>
                        <form action="ControladorVenta" method="POST">
                            <div class="mb-3">
                                <label class="form-label small text-uppercase fw-bold">Producto</label>
                                <select name="idProducto" class="form-select bg-dark text-light border-secondary" required>
                                    <option value="">-- Elige un suplemento --</option>
                                    <%
                                        ProductoDAO prodDAO = new ProductoDAO();
                                        List<Producto> listaProd = prodDAO.listarProductos(); // Asegúrate de tener este método idéntico
                                        for (Producto p : listaProd) {
                                    %>
                                        <option value="<%= p.getIdProducto() %>">
                                            <%= p.getNombre() %> (Stock: <%= p.getStock() %>) - $<%= p.getPrecioVenta() %>
                                        </option>
                                    <% } %>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label class="form-label small text-uppercase fw-bold">Cantidad a vender</label>
                                <input type="number" name="cantidad" class="form-control bg-dark text-light border-secondary" min="1" value="1" required>
                            </div>
                            <button type="submit" name="accion" value="AgregarCarrito" class="btn btn-warning w-100 fw-bold">
                                + AGREGAR AL CARRITO
                            </button>
                        </form>
                    </div>
                </div>

                <div class="card bg-secondary text-light shadow border-0" style="border-radius: 12px;">
                    <div class="card-body p-4">
                        <h5 class="fw-bold text-warning mb-3 text-uppercase">2. Datos de Facturación</h5>
                        <form action="ControladorVenta" method="POST">
                            <div class="mb-3">
                                <label class="form-label small text-uppercase fw-bold">Cliente</label>
                                <select name="idCliente" class="form-select bg-dark text-light border-secondary" required>
                                    <option value="">-- Selecciona el cliente --</option>
                                    <%
                                        ClienteDAO cliDAO = new ClienteDAO();
                                        List<Cliente> listaCli = cliDAO.listarClientes();
                                        for (Cliente c : listaCli) {
                                    %>
                                        <option value="<%= c.getIdCliente() %>"><%= c.getNombre() %></option>
                                    <% } %>
                                </select>
                            </div>
                            <div class="mb-4">
                                <label class="form-label small text-uppercase fw-bold">Régimen Fiscal</label>
                                <select name="regimenFiscal" class="form-select bg-dark text-light border-secondary" required>
                                    <option value="General de Ley Personas Morales">General de Ley Personas Morales</option>
                                    <option value="Simplificado de Confianza (RESICO)">Simplificado de Confianza (RESICO)</option>
                                    <option value="Sueldos y Salarios">Sueldos y Salarios</option>
                                    <option value="Personas Físicas con Actividades Empresariales">Personas Físicas con Actividades Empresariales</option>
                                    <option value="Sin obligaciones fiscales">Sin obligaciones fiscales</option>
                                </select>
                            </div>
                            
                            <div class="row g-2">
                                <div class="col-6">
                                    <a href="ControladorVenta?accion=CancelarVenta" class="btn btn-outline-danger w-100 fw-bold">VACIAR</a>
                                </div>
                                <div class="col-6">
                                    <button type="submit" name="accion" value="GenerarVenta" class="btn btn-success w-100 fw-bold">COBRAR</button>
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
                            <h5 class="fw-bold text-warning mb-3 text-uppercase">🛒 Resumen de la Orden</h5>
                            <div class="table-responsive">
                                <table class="table table-dark table-hover table-striped align-middle m-0">
                                    <thead>
                                        <tr class="text-warning small text-uppercase">
                                            <th>#</th>
                                            <th>ID Prod</th>
                                            <th>Nombre Producto</th>
                                            <th class="text-center">Cantidad</th>
                                            <th class="text-end">Precio U.</th>
                                            <th class="text-end">Importe</th>
                                            <th class="text-center">Acción</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <%
                                            List<DetalleVenta> car = (List<DetalleVenta>) session.getAttribute("carrito");
                                            double subtotal = 0;
                                            int cont = 0;
                                            if (car != null && !car.isEmpty()) {
                                                for (DetalleVenta det : car) {
                                                    double importe = det.getCantidad() * det.getPrecioUnitario();
                                                    subtotal += importe;
                                                    
                                                    // Buscamos el nombre del producto sobre la marcha para mostrarlo bonito
                                                    Producto prodAux = prodDAO.buscarProducto(det.getIdProducto());
                                                    String nombreP = (prodAux != null) ? prodAux.getNombre() : "Producto #" + det.getIdProducto();
                                        %> <%-- Renglon de Tabla --%>
                                        <tr>
                                            <td><%= ++cont %></td>
                                            <td><%= det.getIdProducto() %></td>
                                            <td><%= nombreP %></td>
                                            <td class="text-center"><span class="badge bg-warning text-dark fw-bold px-3"><%= det.getCantidad() %></span></td>
                                            <td class="text-end">$<%= String.format("%.2f", det.getPrecioUnitario()) %></td>
                                            <td class="text-end fw-bold text-info">$<%= String.format("%.2f", importe) %></td>
                                            <td class="text-center">
                                                <a href="ControladorVenta?accion=EliminarDelCarrito&index=<%= cont - 1 %>" class="btn btn-sm btn-outline-danger py-0 px-2">×</a>
                                            </td>
                                        </tr>
                                        <% 
                                                }
                                            } else {
                                        %>
                                        <tr>
                                            <td colspan="7" class="text-center text-muted py-4">El carrito de compras está vacío</td>
                                        </tr>
                                        <% } 
                                            double iva = subtotal * 0.16;
                                            double total = subtotal + iva;
                                        %>
                                    </tbody>
                                </table>
                            </div>
                        </div>

                        <div class="border-top border-secondary pt-3 mt-4">
                            <div class="row justify-content-end text-end">
                                <div class="col-md-4">
                                    <div class="d-flex justify-content-between mb-1">
                                        <span class="text-muted small text-uppercase">Subtotal:</span>
                                        <span class="fw-bold">$<%= String.format("%.2f", subtotal) %></span>
                                    </div>
                                    <div class="d-flex justify-content-between mb-2">
                                        <span class="text-muted small text-uppercase">IVA (16%):</span>
                                        <span class="fw-bold text-danger">$<%= String.format("%.2f", iva) %></span>
                                    </div>
                                    <div class="d-flex justify-content-between border-top border-warning pt-2">
                                        <span class="fw-bold text-warning text-uppercase">Total Neto:</span>
                                        <span class="fs-4 fw-bold text-success">$<%= String.format("%.2f", total) %></span>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>