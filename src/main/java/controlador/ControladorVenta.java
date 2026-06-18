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

// Importamos nuestros modelos y DAOs
import modelo.Venta;
import modelo.DetalleVenta;
import modelo.VentaDAO;
import modelo.Producto;
import modelo.ProductoDAO;

@WebServlet(name = "ControladorVenta", urlPatterns = {"/ControladorVenta"})
public class ControladorVenta extends HttpServlet {

    VentaDAO vdao = new VentaDAO();
    ProductoDAO pdao = new ProductoDAO();
    
    // Variables para calcular los totales de la venta
    double subtotalGeneral = 0.0;
    double ivaGeneral = 0.0;
    double totalGeneral = 0.0;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        HttpSession session = request.getSession();
        
        // Recuperamos el carrito de la sesión (si no existe, lo creamos)
        List<DetalleVenta> carrito = (List<DetalleVenta>) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new ArrayList<>();
        }

        switch (accion) {
            case "AgregarCarrito":
                int idProducto = Integer.parseInt(request.getParameter("idProducto"));
                int cantidad = Integer.parseInt(request.getParameter("cantidad"));
                
                // Buscamos el producto en la BD para saber su precio actual
                Producto p = pdao.buscarProducto(idProducto); // Asegúrate de tener este método en tu ProductoDAO
                
                if (p != null && p.getStock() >= cantidad) {
                    DetalleVenta detalle = new DetalleVenta();
                    detalle.setIdProducto(p.getIdProducto());
                    detalle.setCantidad(cantidad);
                    detalle.setPrecioUnitario(p.getPrecioVenta());
                    // Puedes guardar el nombre del producto en una variable temporal del objeto o leerlo en la vista
                    
                    carrito.add(detalle);
                    session.setAttribute("carrito", carrito);
                } else {
                    request.setAttribute("errorStock", "Stock insuficiente o producto no encontrado.");
                }
                request.getRequestDispatcher("nuevaVenta.jsp").forward(request, response);
                break;

            case "EliminarDelCarrito":
                int index = Integer.parseInt(request.getParameter("index"));
                carrito.remove(index);
                session.setAttribute("carrito", carrito);
                request.getRequestDispatcher("nuevaVenta.jsp").forward(request, response);
                break;

            case "GenerarVenta":
                // 1. Validamos que el carrito no esté vacío
                if (carrito.isEmpty()) {
                    request.setAttribute("error", "El carrito está vacío.");
                    request.getRequestDispatcher("nuevaVenta.jsp").forward(request, response);
                    break;
                }

                // 2. Recolectamos datos del formulario final
                int idCliente = Integer.parseInt(request.getParameter("idCliente"));
                String regimenFiscal = request.getParameter("regimenFiscal");
                
                // 3. Generamos la fecha actual
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String fechaActual = sdf.format(new Date());

                // 4. Calculamos las matemáticas de la venta completa
                subtotalGeneral = 0.0;
                for (DetalleVenta det : carrito) {
                    subtotalGeneral += (det.getCantidad() * det.getPrecioUnitario());
                }
                ivaGeneral = subtotalGeneral * 0.16; // IVA estándar del 16%
                totalGeneral = subtotalGeneral + ivaGeneral;

                // 5. Armamos el objeto Venta (Cabecera)
                Venta nuevaVenta = new Venta(idCliente, fechaActual, subtotalGeneral, ivaGeneral, totalGeneral, regimenFiscal);

                // 6. ¡Llamamos al motor de transacciones (VentaDAO)!
                boolean exito = vdao.registrarVenta(nuevaVenta, carrito);

                if (exito) {
                    // Venta exitosa: Vaciamos el carrito y mandamos mensaje de éxito
                    session.removeAttribute("carrito");
                    request.setAttribute("mensaje", "¡Venta registrada con éxito y stock actualizado!");
                } else {
                    request.setAttribute("error", "Error al procesar la venta. Intente nuevamente.");
                }
                
                request.getRequestDispatcher("nuevaVenta.jsp").forward(request, response);
                break;

            case "CancelarVenta":
                session.removeAttribute("carrito");
                response.sendRedirect("nuevaVenta.jsp");
                break;

            default:
                request.getRequestDispatcher("nuevaVenta.jsp").forward(request, response);
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