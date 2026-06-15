<%@page import="java.util.List"%>
<%@page import="modelo.Producto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Inventario Suplementos Perrones</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light"> <div class="container mt-5">
        
        <div class="text-center mb-4">
            <h1 class="display-5 fw-bold text-uppercase">Gestión de Suplementos Perrones</h1>
            <p class="text-muted">Bienvenido al sistema. Aquí tienes la lista actual de productos:</p>
        </div>
        
        <%
            Producto pEdit = (Producto) request.getAttribute("productoAEditar");
            String accionForm = (pEdit != null) ? "actualizar" : "agregar";
        %>

        <div class="card shadow-sm mb-5">
            <div class="card-header bg-dark text-white">
                <h4 class="mb-0"><%= (pEdit != null) ? "✏️ Editar Suplemento" : "➕ Agregar Nuevo Suplemento" %></h4>
            </div>
            <div class="card-body">
                <form action="ControladorProducto" method="POST" class="row g-3">
                    <input type="hidden" name="idProducto" value="<%= (pEdit != null) ? pEdit.getIdProducto() : 0 %>">
                    
                    <div class="col-md-4">
                        <label class="form-label fw-bold">Nombre</label>
                        <input type="text" class="form-control" name="nombre" placeholder="Ej. Proteína Whey" value="<%= (pEdit != null) ? pEdit.getNombre() : "" %>" required>
                    </div>
                    <div class="col-md-4">
                        <label class="form-label fw-bold">Categoría</label>
                        <input type="text" class="form-control" name="categoria" placeholder="Ej. Proteínas" value="<%= (pEdit != null) ? pEdit.getCategoria() : "" %>" required>
                    </div>
                    <div class="col-md-4">
                        <label class="form-label fw-bold">Descripción</label>
                        <input type="text" class="form-control" name="descripcion" placeholder="Ej. Sabor chocolate 5lbs" value="<%= (pEdit != null) ? pEdit.getDescripcion() : "" %>" required>
                    </div>
                    
                    <div class="col-md-6">
                        <label class="form-label fw-bold">Precio de Venta</label>
                        <div class="input-group">
                            <span class="input-group-text bg-success text-white">$</span>
                            <input type="number" class="form-control" step="0.01" name="precio" placeholder="0.00" value="<%= (pEdit != null) ? pEdit.getPrecioVenta() : "" %>" required>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label fw-bold">Stock Inicial</label>
                        <input type="number" class="form-control" name="stock" placeholder="Cantidad en bodega" value="<%= (pEdit != null) ? pEdit.getStock() : "" %>" required>
                    </div>
                    
                    <input type="hidden" name="accion" value="<%= accionForm %>">
                    
                    <div class="col-12 text-end mt-4">
                        <button type="submit" class="btn <%= (pEdit != null) ? "btn-warning" : "btn-success" %> btn-lg fw-bold px-5">
                            <%= (pEdit != null) ? "Actualizar Datos" : "Guardar Suplemento" %>
                        </button>
                    </div>
                </form>
            </div>
        </div>
        
        <div class="card shadow-sm">
            <div class="card-body p-0">
                <div class="table-responsive">
                    <table class="table table-dark table-striped table-hover mb-0"> 
                        <thead class="table-dark">
                            <tr>
                                <th>ID</th>
                                <th>Nombre</th>
                                <th>Categoría</th>
                                <th>Descripción</th>
                                <th>Precio de Venta</th>
                                <th>Stock</th>
                                <th class="text-center">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                List<Producto> lista = (List<Producto>) request.getAttribute("misProductos");
                                if(lista != null) {
                                    for(Producto p : lista) {
                            %>
                            <tr class="align-middle">
                                <td><%= p.getIdProducto() %></td>
                                <td class="fw-bold"><%= p.getNombre() %></td>
                                <td><span class="badge bg-secondary"><%= p.getCategoria() %></span></td>
                                <td><%= p.getDescripcion() %></td>
                                <td class="text-success fw-bold">$<%= p.getPrecioVenta() %></td>
                                <td><%= p.getStock() %></td>
                                <td class="text-center">
                                    <a href="ControladorProducto?accion=editar&id=<%= p.getIdProducto() %>" class="btn btn-sm btn-outline-warning me-1">Editar</a>  
                                    <a href="ControladorProducto?accion=eliminar&id=<%= p.getIdProducto() %>" class="btn btn-sm btn-outline-danger">Eliminar</a>
                                </td>
                            </tr>
                            <% 
                                    }
                                }
                            %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

    </div>

</body>
</html>