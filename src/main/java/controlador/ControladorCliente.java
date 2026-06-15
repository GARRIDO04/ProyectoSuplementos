package controlador;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Cliente;    
import modelo.ClienteDAO;

public class ControladorCliente extends HttpServlet {

    ClienteDAO dao = new ClienteDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        
        // Acción por defecto: Listar
        if (accion == null || accion.isEmpty()) {
            List<Cliente> lista = dao.listarClientes();            
            request.setAttribute("misClientes", lista);
            request.getRequestDispatcher("vistas/clientes.jsp").forward(request, response);
            
        // Acción: Eliminar
        } else if ("eliminar".equals(accion)) { 
            int id = Integer.parseInt(request.getParameter("id"));
            dao.eliminarCliente(id);
            response.sendRedirect("ControladorCliente");
            
        // Acción: Preparar la edición
        } else if ("editar".equals(accion)) { 
            int id = Integer.parseInt(request.getParameter("id"));
            Cliente c = dao.buscarCliente(id);
            request.setAttribute("clienteAEditar", c);
            
            // Recargamos la lista para que la tabla no desaparezca
            List<Cliente> lista = dao.listarClientes();            
            request.setAttribute("misClientes", lista);
            
            request.getRequestDispatcher("vistas/clientes.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        
        // Acción: Guardar nuevo
        if ("agregar".equals(accion)) {
            
            String nombre = request.getParameter("nombre");
            String telefono = request.getParameter("telefono");
            String correo = request.getParameter("correo");
            
            Cliente c = new Cliente();
            c.setNombre(nombre);
            c.setTelefono(telefono);
            c.setCorreo(correo);
            
            dao.registrarCliente(c);
            response.sendRedirect("ControladorCliente");
            
        // Acción: Guardar cambios de edición
        } else if ("actualizar".equals(accion)) { 
            
            int id = Integer.parseInt(request.getParameter("idCliente"));
            String nombre = request.getParameter("nombre");
            String telefono = request.getParameter("telefono");
            String correo = request.getParameter("correo");
            
            Cliente c = new Cliente();
            c.setIdCliente(id);
            c.setNombre(nombre);
            c.setTelefono(telefono);
            c.setCorreo(correo);
            
            dao.modificarCliente(c);
            response.sendRedirect("ControladorCliente");
        }
    }
}