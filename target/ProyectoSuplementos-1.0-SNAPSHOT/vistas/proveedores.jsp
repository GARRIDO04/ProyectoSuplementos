<%@page import="java.util.List"%>
<%@page import="modelo.Proveedor"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Proveedores - Suplementos Perrones</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

    <div class="container mt-5">
        
        <div class="text-center mb-4">
            <h1 class="display-5 fw-bold text-uppercase">Gestión de Proveedores</h1>
            <p class="text-muted">Administra los contactos de quienes surten el inventario.</p>
        </div>
        
        <%
            Proveedor pEdit = (Proveedor) request.getAttribute("proveedorAEditar");
            String accionForm = (pEdit != null) ? "actualizar" : "agregar";
        %>

        <div class="card shadow-sm mb-5">
            <div class="card-header bg-dark text-white">
                <h4 class="mb-0"><%= (pEdit != null) ? "✏️ Editar Proveedor" : "➕ Agregar Nuevo Proveedor" %></h4>
            </div>
            <div class="card-body">
                <form action="ControladorProveedor" method="POST" class="row g-3">
                    <input type="hidden" name="idProveedor" value="<%= (pEdit != null) ? pEdit.getIdProveedor() : 0 %>">
                    
                    <div class="col-md-6">
                        <label class="form-label fw-bold">Nombre / Empresa</label>
                        <input type="text" class="form-control" name="nombre" placeholder="Ej. Laboratorios X" value="<%= (pEdit != null) ? pEdit.getNombre() : "" %>" required>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label fw-bold">RFC</label>
                        <input type="text" class="form-control" name="rfc" placeholder="Ej. XAXX010101000" value="<%= (pEdit != null) ? pEdit.getRfc() : "" %>" required>
                    </div>
                    
                    <div class="col-md-4">
                        <label class="form-label fw-bold">Correo Electrónico</label>
                        <input type="email" class="form-control" name="correo" placeholder="correo@empresa.com" value="<%= (pEdit != null) ? pEdit.getCorreo() : "" %>" required>
                    </div>
                    <div class="col-md-4">
                        <label class="form-label fw-bold">Persona de Contacto</label>
                        <input type="text" class="form-control" name="contacto" placeholder="Nombre del agente" value="<%= (pEdit != null) ? pEdit.getContacto() : "" %>" required>
                    </div>
                    <div class="col-md-4">
                        <label class="form-label fw-bold">Dirección</label>
                        <input type="text" class="form-control" name="direccion" placeholder="Calle, Colonia, Ciudad" value="<%= (pEdit != null) ? pEdit.getDireccion() : "" %>" required>
                    </div>
                    
                    <input type="hidden" name="accion" value="<%= accionForm %>">
                    
                    <div class="col-12 text-end mt-4">
                        <button type="submit" class="btn <%= (pEdit != null) ? "btn-warning" : "btn-success" %> btn-lg fw-bold px-5">
                            <%= (pEdit != null) ? "Actualizar Proveedor" : "Guardar Proveedor" %>
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
                                <th>RFC</th>
                                <th>Correo</th>
                                <th>Contacto</th>
                                <th>Dirección</th>
                                <th class="text-center">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                List<Proveedor> lista = (List<Proveedor>) request.getAttribute("misProveedores");
                                if(lista != null) {
                                    for(Proveedor p : lista) {
                            %>
                            <tr class="align-middle">
                                <td><%= p.getIdProveedor() %></td>
                                <td class="fw-bold"><%= p.getNombre() %></td>
                                <td><span class="badge bg-secondary"><%= p.getRfc() %></span></td>
                                <td><%= p.getCorreo() %></td>
                                <td><%= p.getContacto() %></td>
                                <td><%= p.getDireccion() %></td>
                                <td class="text-center">
                                    <a href="ControladorProveedor?accion=editar&id=<%= p.getIdProveedor() %>" class="btn btn-sm btn-outline-warning me-1">Editar</a>  
                                    <a href="ControladorProveedor?accion=eliminar&id=<%= p.getIdProveedor() %>" class="btn btn-sm btn-outline-danger">Eliminar</a>
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