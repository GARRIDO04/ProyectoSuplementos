package modelo;

import config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    private Connection con;
    private PreparedStatement ps;

    public boolean registrarProducto(Producto pro) {
        String sql = "INSERT INTO productos (nombre, categoria, descripcion, precio_venta, stock) VALUES (?, ?, ?, ?, ?)";
        
        try {
            con = Conexion.getInstancia().getConnection();
            
            ps = con.prepareStatement(sql);
            
            ps.setString(1, pro.getNombre());
            ps.setString(2, pro.getCategoria());
            ps.setString(3, pro.getDescripcion());
            ps.setDouble(4, pro.getPrecioVenta());
            ps.setInt(5, pro.getStock());
            
            ps.execute();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error al registrar el producto: " + e.getMessage());
            return false;
        }
    }

    public List<Producto> listarProductos() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM productos";
        ResultSet rs;

        try {
            con = Conexion.getInstancia().getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Producto pro = new Producto();
                
                pro.setIdProducto(rs.getInt("id_producto"));
                pro.setNombre(rs.getString("nombre"));
                pro.setCategoria(rs.getString("categoria"));
                pro.setDescripcion(rs.getString("descripcion"));
                pro.setPrecioVenta(rs.getDouble("precio_venta"));
                pro.setStock(rs.getInt("stock"));

                lista.add(pro);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar los productos: " + e.getMessage());
        }
        
        return lista;
    }
    
    public Producto buscarProducto(int id) {
        Producto p = new Producto();
        String sql = "SELECT * FROM productos WHERE id_producto=?";
        
        try {
            con = Conexion.getInstancia().getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                p.setIdProducto(rs.getInt("id_producto"));
                p.setNombre(rs.getString("nombre"));
                p.setCategoria(rs.getString("categoria"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecioVenta(rs.getDouble("precio_venta"));
                p.setStock(rs.getInt("stock"));
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar el producto: " + e.getMessage());
        }
        return p;
    }
    
    public boolean modificarProducto(Producto pro) {
        String sql = "UPDATE productos SET nombre=?, categoria=?, descripcion=?, precio_venta=?, stock=? WHERE id_producto=?";
        
        try {
            con = Conexion.getInstancia().getConnection();
            ps = con.prepareStatement(sql);
            
            ps.setString(1, pro.getNombre());
            ps.setString(2, pro.getCategoria());
            ps.setString(3, pro.getDescripcion());
            ps.setDouble(4, pro.getPrecioVenta());
            ps.setInt(5, pro.getStock());
            ps.setInt(6, pro.getIdProducto());
            
            ps.execute();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error al modificar el producto: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarProducto(int idProducto) {
        String sql = "DELETE FROM productos WHERE id_producto=?";
        
        try {
            con = Conexion.getInstancia().getConnection();
            ps = con.prepareStatement(sql);
            
            ps.setInt(1, idProducto);
            
            ps.execute();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar el producto: " + e.getMessage());
            return false;
        }
    }
}