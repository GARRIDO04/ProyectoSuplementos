<%@page import="java.util.List"%>
<%@page import="modelo.Cliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Clientes - Suplementos Perrones</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

    <div class="container mt-5">
        
        <div class="text-center mb-4">
            <h1 class="display-5 fw-bold text-uppercase">Gestión de Clientes</h1>
            <p class="text-muted">Administra el directorio de tus compradores estrella.</p>
        </div>
        
        <%
            Cliente cEdit = (Cliente) request.getAttribute("clienteAEditar");
            String accionForm = (cEdit != null) ? "actualizar" : "agregar";
        %>

        <div class="card shadow-sm mb-5">
            <div class="card-header bg-dark text-white">
                <h4 class="mb-0"><%= (cEdit != null) ? "✏️ Editar Cliente" : "➕ Agregar Nuevo Cliente" %></h4>
            </div>
            <div class="card-body">
                <form action="ControladorCliente" method="POST" class="row g-3">
                    <input type="hidden" name="idCliente" value="<%= (cEdit != null) ? cEdit.getIdCliente() : 0 %>">
                    
                    <div class="col-md-12">
                        <label class="form-label fw-bold">Nombre Completo</label>
                        <input type="text" class="form-control" name="nombre" placeholder="Ej. Bruce Wayne" value="<%= (cEdit != null) ? cEdit.getNombre() : "" %>" required>
                    </div>
                    
                    <div class="col-md-6">
                        <label class="form-label fw-bold">Teléfono</label>
                        <input type="text" class="form-control" name="telefono" placeholder="Ej. 555-123-4567" value="<%= (cEdit != null) ? cEdit.getTelefono() : "" %>" required>
                    </div>
                    
                    <div class="col-md-6">
                        <label class="form-label fw-bold">Correo Electrónico</label>
                        <input type="email" class="form-control" name="correo" placeholder="correo@ejemplo.com" value="<%= (cEdit != null) ? cEdit.getCorreo() : "" %>" required>
                    </div>
                    
                    <input type="hidden" name="accion" value="<%= accionForm %>">
                    
                    <div class="col-12 text-end mt-4">
                        <button type="submit" class="btn <%= (cEdit != null) ? "btn-warning" : "btn-success" %> btn-lg fw-bold px-5">
                            <%= (cEdit != null) ? "Actualizar Cliente" : "Guardar Cliente" %>
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
                                <th>Teléfono</th>
                                <th>Correo</th>
                                <th class="text-center">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                List<Cliente> lista = (List<Cliente>) request.getAttribute("misClientes");
                                if(lista != null) {
                                    for(Cliente c : lista) {
                            %>
                            <tr class="align-middle">
                                <td><%= c.getIdCliente() %></td>
                                <td class="fw-bold"><%= c.getNombre() %></td>
                                <td><span class="badge bg-secondary"><%= c.getTelefono() %></span></td>
                                <td><%= c.getCorreo() %></td>
                                <td class="text-center">
                                    <a href="ControladorCliente?accion=editar&id=<%= c.getIdCliente() %>" class="btn btn-sm btn-outline-warning me-1">Editar</a>  
                                    <a href="ControladorCliente?accion=eliminar&id=<%= c.getIdCliente() %>" class="btn btn-sm btn-outline-danger">Eliminar</a>
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