package controlador;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import modelo.Compra;
import modelo.DetalleCompra;
import modelo.CompraDAO;
import modelo.Producto;
import modelo.ProductoDAO;

@WebServlet(name = "ControladorCompra", urlPatterns = {"/ControladorCompra"})
public class ControladorCompra extends HttpServlet {

    CompraDAO cdao = new CompraDAO();
    ProductoDAO pdao = new ProductoDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        HttpSession session = request.getSession();

        // Recuperamos el carrito de compras de la sesión
        List<DetalleCompra> carritoCompra = (List<DetalleCompra>) session.getAttribute("carritoCompra");
        if (carritoCompra == null) {
            carritoCompra = new ArrayList<>();
        }

        switch (accion) {
            case "AgregarCarrito":
                int idProducto = Integer.parseInt(request.getParameter("idProducto"));
                int cantidad = Integer.parseInt(request.getParameter("cantidad"));
                double precioCompra = Double.parseDouble(request.getParameter("precioCompra"));

                Producto p = pdao.buscarProducto(idProducto);

                if (p != null) {
                    DetalleCompra detalle = new DetalleCompra();
                    detalle.setIdProducto(p.getIdProducto());
                    detalle.setCantidad(cantidad);
                    detalle.setPrecioCompra(precioCompra); // Costo del proveedor
                    
                    carritoCompra.add(detalle);
                    session.setAttribute("carritoCompra", carritoCompra);
                } else {
                    request.setAttribute("error", "Producto no encontrado.");
                }
                request.getRequestDispatcher("nuevaCompra.jsp").forward(request, response);
                break;

            case "EliminarDelCarrito":
                int index = Integer.parseInt(request.getParameter("index"));
                carritoCompra.remove(index);
                session.setAttribute("carritoCompra", carritoCompra);
                request.getRequestDispatcher("nuevaCompra.jsp").forward(request, response);
                break;

            case "GenerarCompra":
                if (carritoCompra.isEmpty()) {
                    request.setAttribute("error", "La lista de surtido está vacía.");
                    request.getRequestDispatcher("nuevaCompra.jsp").forward(request, response);
                    break;
                }

                int idProveedor = Integer.parseInt(request.getParameter("idProveedor"));
                
                // Generamos la fecha exacta del servidor
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String fechaActual = sdf.format(new Date());

                // Armamos el objeto Compra (como vimos en tu BD, solo lleva proveedor y fecha)
                Compra nuevaCompra = new Compra(idProveedor, fechaActual);

                // Disparamos la transacción en el DAO
                boolean exito = cdao.registrarCompra(nuevaCompra, carritoCompra);

                if (exito) {
                    session.removeAttribute("carritoCompra"); // Limpiamos la bandeja
                    request.setAttribute("mensaje", "¡Compra registrada! El stock ha sido abastecido.");
                } else {
                    request.setAttribute("error", "Error al procesar la compra en la base de datos.");
                }
                
                request.getRequestDispatcher("nuevaCompra.jsp").forward(request, response);
                break;

            case "CancelarCompra":
                session.removeAttribute("carritoCompra");
                response.sendRedirect("nuevaCompra.jsp");
                break;

            default:
                request.getRequestDispatcher("nuevaCompra.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}