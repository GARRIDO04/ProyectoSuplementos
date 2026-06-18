package modelo;

import config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class VentaDAO {
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    // Método maestro para registrar la venta completa (Cabecera, Detalles y Stock)
    public boolean registrarVenta(Venta venta, List<DetalleVenta> listaDetalles) {
        String sqlVenta = "INSERT INTO ventas (id_cliente, fecha, subtotal, iva, total, regimen_fiscal) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlDetalle = "INSERT INTO detalle_ventas (id_venta, id_producto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";
        // Fíjate cómo en el UPDATE le restamos directamente la cantidad al stock actual
        String sqlStock = "UPDATE productos SET stock = stock - ? WHERE id_producto = ?";
        
        boolean exito = false;

        try {
            con = Conexion.getInstancia().getConnection();
            
            // 1. APAGAMOS EL AUTO-GUARDADO (Iniciamos la Transacción)
            con.setAutoCommit(false); 

            // 2. REGISTRAMOS LA VENTA MAESTRA
            // Le pedimos a MySQL que nos devuelva el ID que le acaba de asignar (RETURN_GENERATED_KEYS)
            ps = con.prepareStatement(sqlVenta, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, venta.getIdCliente());
            ps.setString(2, venta.getFecha());
            ps.setDouble(3, venta.getSubtotal());
            ps.setDouble(4, venta.getIva());
            ps.setDouble(5, venta.getTotal());
            ps.setString(6, venta.getRegimenFiscal());
            ps.executeUpdate();

            // 3. RECUPERAMOS EL ID DE LA VENTA
            rs = ps.getGeneratedKeys();
            int idVentaGenerado = 0;
            if (rs.next()) {
                idVentaGenerado = rs.getInt(1);
            }

            // 4. CICLO PARA REGISTRAR CADA DETALLE Y ACTUALIZAR SU STOCK
            for (DetalleVenta detalle : listaDetalles) {
                // A) Guardamos el renglón en detalle_ventas
                ps = con.prepareStatement(sqlDetalle);
                ps.setInt(1, idVentaGenerado); // Amarramos el detalle a la venta principal
                ps.setInt(2, detalle.getIdProducto());
                ps.setInt(3, detalle.getCantidad());
                ps.setDouble(4, detalle.getPrecioUnitario());
                ps.executeUpdate();

                // B) Descontamos el stock de la tabla productos
                ps = con.prepareStatement(sqlStock);
                ps.setInt(1, detalle.getCantidad());
                ps.setInt(2, detalle.getIdProducto());
                ps.executeUpdate();
            }

            // 5. TODO SALIÓ PERFECTO -> CONFIRMAMOS LOS CAMBIOS EN LA BD
            con.commit(); 
            exito = true;

        } catch (Exception e) {
            // SI ALGO FALLÓ (ej. no hay stock o error de red) -> DESHACEMOS TODO
            try {
                if (con != null) {
                    con.rollback(); 
                    System.err.println("Transacción cancelada (Rollback). No se alteró la BD.");
                }
            } catch (Exception ex) {
                System.err.println("Error fatal al hacer rollback: " + ex.getMessage());
            }
            System.err.println("Error al registrar la venta completa: " + e.getMessage());
        } finally {
            // 6. RESTAURAMOS EL COMPORTAMIENTO NORMAL DEL MOTOR
            try {
                if (con != null) con.setAutoCommit(true);
            } catch (Exception e) { }
        }
        
        return exito;
    }
}