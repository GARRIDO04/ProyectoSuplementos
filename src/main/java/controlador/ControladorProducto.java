package controlador;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Producto;    
import modelo.ProductoDAO;

public class ControladorProducto extends HttpServlet {

    ProductoDAO dao = new ProductoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        
        if (accion == null || accion.isEmpty()) {
            List<Producto> lista = dao.listarProductos();            
            //Guardamos esa lista en el "request" con un nombre clave "misProductos"
            request.setAttribute("misProductos", lista);
            
            request.getRequestDispatcher("vistas/productos.jsp").forward(request, response);
            
        } else if ("eliminar".equals(accion)) {
            //Atrapamos el ID que viene en la URL (viaja como texto, lo convertimos a número)
            int id = Integer.parseInt(request.getParameter("id"));
            
            //Le damos la orden de eliminar ese registro exacto
            dao.eliminarProducto(id);
            
            //Recargamos el Controlador para que vuelva a hacer el SELECT y muestre la tabla actualizada
            response.sendRedirect("ControladorProducto");
        }
        else if ("editar".equals(accion)) {
            //Guardamos el ID del producto a modificar
            int id = Integer.parseInt(request.getParameter("id"));
            
            Producto p = dao.buscarProducto(id);
            
            //Guardamos ese producto con la etiqueta "productoAEditar"
            request.setAttribute("productoAEditar", p);
            
            //Volvemos a cargar toda la lista para que la tabla de abajo no desaparezca
            List<Producto> lista = dao.listarProductos();            
            request.setAttribute("misProductos", lista);
            
            //Mandamos todo a la vista
            request.getRequestDispatcher("vistas/productos.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        
        if ("agregar".equals(accion)) {
            
            String nombre = request.getParameter("nombre");
            String categoria = request.getParameter("categoria");
            String descripcion = request.getParameter("descripcion");
            double precio = Double.parseDouble(request.getParameter("precio"));
            int stock = Integer.parseInt(request.getParameter("stock"));
            
            Producto p = new Producto();
            p.setNombre(nombre);
            p.setCategoria(categoria);
            p.setDescripcion(descripcion);
            p.setPrecioVenta(precio);
            p.setStock(stock);
            
            dao.registrarProducto(p);
            response.sendRedirect("ControladorProducto");
            
        } else if ("actualizar".equals(accion)) {
            
            //Obtenemos textos editados y ID del campo oculto
            int id = Integer.parseInt(request.getParameter("idProducto"));
            String nombre = request.getParameter("nombre");
            String categoria = request.getParameter("categoria");
            String descripcion = request.getParameter("descripcion");
            double precio = Double.parseDouble(request.getParameter("precio"));
            int stock = Integer.parseInt(request.getParameter("stock"));
            
            Producto p = new Producto();
            p.setIdProducto(id);
            p.setNombre(nombre);
            p.setCategoria(categoria);
            p.setDescripcion(descripcion);
            p.setPrecioVenta(precio);
            p.setStock(stock);
            
            //Sobrescribir BD para actualizar datos
            dao.modificarProducto(p);
            
            //Redirigimos al inicio para recargar la tabla ya editada
            response.sendRedirect("ControladorProducto");
        }
    }
}