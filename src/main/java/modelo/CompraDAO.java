package modelo;

import config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class CompraDAO {
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    public boolean registrarCompra(Compra compra, List<DetalleCompra> listaDetalles) {
        String sqlCompra = "INSERT INTO compras (id_proveedor, fecha) VALUES (?, ?)";
        String sqlDetalle = "INSERT INTO detalle_compras (id_compra, id_producto, cantidad, precio_compra) VALUES (?, ?, ?, ?)";
        // ¡OJO AQUÍ! Sumamos al stock en lugar de restar
        String sqlStock = "UPDATE productos SET stock = stock + ? WHERE id_producto = ?";
        
        boolean exito = false;

        try {
            con = Conexion.getInstancia().getConnection();
            con.setAutoCommit(false); // Iniciamos Transacción

            // 1. Guardamos la Compra
            ps = con.prepareStatement(sqlCompra, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, compra.getIdProveedor());
            ps.setString(2, compra.getFecha());
            ps.executeUpdate();

            // 2. Obtenemos el ID generado
            rs = ps.getGeneratedKeys();
            int idCompraGenerada = 0;
            if (rs.next()) {
                idCompraGenerada = rs.getInt(1);
            }

            // 3. Guardamos detalles y SUMAMOS stock
            for (DetalleCompra detalle : listaDetalles) {
                ps = con.prepareStatement(sqlDetalle);
                ps.setInt(1, idCompraGenerada);
                ps.setInt(2, detalle.getIdProducto());
                ps.setInt(3, detalle.getCantidad());
                ps.setDouble(4, detalle.getPrecioCompra());
                ps.executeUpdate();

                ps = con.prepareStatement(sqlStock);
                ps.setInt(1, detalle.getCantidad());
                ps.setInt(2, detalle.getIdProducto());
                ps.executeUpdate();
            }

            con.commit(); // Confirmamos los cambios
            exito = true;

        } catch (Exception e) {
            try {
                if (con != null) {
                    con.rollback(); // Cancelamos todo si hay error
                }
            } catch (Exception ex) {
                System.err.println("Error en rollback de compra: " + ex.getMessage());
            }
            System.err.println("Error al registrar la compra: " + e.getMessage());
        } finally {
            try {
                if (con != null) con.setAutoCommit(true);
            } catch (Exception e) { }
        }
        
        return exito;
    }
}