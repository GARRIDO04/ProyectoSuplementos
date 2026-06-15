package controlador;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Proveedor;    
import modelo.ProveedorDAO;

public class ControladorProveedor extends HttpServlet {

    ProveedorDAO dao = new ProveedorDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        
        // Acción por defecto: Listar
        if (accion == null || accion.isEmpty()) {
            List<Proveedor> lista = dao.listarProveedores();            
            request.setAttribute("misProveedores", lista);
            request.getRequestDispatcher("vistas/proveedores.jsp").forward(request, response);
            
        // Acción: Eliminar
        } else if ("eliminar".equals(accion)) { 
            int id = Integer.parseInt(request.getParameter("id"));
            dao.eliminarProveedor(id);
            response.sendRedirect("ControladorProveedor");
            
        // Acción: Preparar la edición
        } else if ("editar".equals(accion)) { 
            int id = Integer.parseInt(request.getParameter("id"));
            Proveedor p = dao.buscarProveedor(id);
            request.setAttribute("proveedorAEditar", p);
            
            // Recargamos la lista para que la tabla no desaparezca al editar
            List<Proveedor> lista = dao.listarProveedores();            
            request.setAttribute("misProveedores", lista);
            
            request.getRequestDispatcher("vistas/proveedores.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        
        // Acción: Guardar nuevo
        if ("agregar".equals(accion)) {
            
            String nombre = request.getParameter("nombre");
            String correo = request.getParameter("correo");
            String direccion = request.getParameter("direccion");
            String contacto = request.getParameter("contacto");
            String rfc = request.getParameter("rfc");
            
            Proveedor p = new Proveedor();
            p.setNombre(nombre);
            p.setCorreo(correo);
            p.setDireccion(direccion);
            p.setContacto(contacto);
            p.setRfc(rfc);
            
            dao.registrarProveedor(p);
            response.sendRedirect("ControladorProveedor");
            
        // Acción: Guardar cambios de edición
        } else if ("actualizar".equals(accion)) { 
            
            int id = Integer.parseInt(request.getParameter("idProveedor"));
            String nombre = request.getParameter("nombre");
            String correo = request.getParameter("correo");
            String direccion = request.getParameter("direccion");
            String contacto = request.getParameter("contacto");
            String rfc = request.getParameter("rfc");
            
            Proveedor p = new Proveedor();
            p.setIdProveedor(id);
            p.setNombre(nombre);
            p.setCorreo(correo);
            p.setDireccion(direccion);
            p.setContacto(contacto);
            p.setRfc(rfc);
            
            dao.modificarProveedor(p);
            response.sendRedirect("ControladorProveedor");
        }
    }
}