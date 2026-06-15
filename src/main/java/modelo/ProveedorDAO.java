package modelo;

import config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDAO {
    private Connection con;
    private PreparedStatement ps;

    public boolean registrarProveedor(Proveedor prov) {
        String sql = "INSERT INTO proveedores (nombre, correo, direccion, contacto, rfc) VALUES (?, ?, ?, ?, ?)";
        
        try {
            con = Conexion.getInstancia().getConnection();
            ps = con.prepareStatement(sql);
            
            ps.setString(1, prov.getNombre());
            ps.setString(2, prov.getCorreo());
            ps.setString(3, prov.getDireccion());
            ps.setString(4, prov.getContacto());
            ps.setString(5, prov.getRfc());
            
            ps.execute();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error al registrar el proveedor: " + e.getMessage());
            return false;
        }
    }

    public List<Proveedor> listarProveedores() {
        List<Proveedor> lista = new ArrayList<>();
        String sql = "SELECT * FROM proveedores";
        ResultSet rs;

        try {
            con = Conexion.getInstancia().getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Proveedor prov = new Proveedor();
                
                prov.setIdProveedor(rs.getInt("id_proveedor"));
                prov.setNombre(rs.getString("nombre"));
                prov.setCorreo(rs.getString("correo"));
                prov.setDireccion(rs.getString("direccion"));
                prov.setContacto(rs.getString("contacto"));
                prov.setRfc(rs.getString("rfc"));

                lista.add(prov);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar los proveedores: " + e.getMessage());
        }
        
        return lista;
    }

    public Proveedor buscarProveedor(int id) {
        Proveedor prov = new Proveedor();
        String sql = "SELECT * FROM proveedores WHERE id_proveedor=?";
        ResultSet rs;
        
        try {
            con = Conexion.getInstancia().getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                prov.setIdProveedor(rs.getInt("id_proveedor"));
                prov.setNombre(rs.getString("nombre"));
                prov.setCorreo(rs.getString("correo"));
                prov.setDireccion(rs.getString("direccion"));
                prov.setContacto(rs.getString("contacto"));
                prov.setRfc(rs.getString("rfc"));
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar el proveedor: " + e.getMessage());
        }
        return prov;
    }
    
    public boolean modificarProveedor(Proveedor prov) {
        String sql = "UPDATE proveedores SET nombre=?, correo=?, direccion=?, contacto=?, rfc=? WHERE id_proveedor=?";
        
        try {
            con = Conexion.getInstancia().getConnection();
            ps = con.prepareStatement(sql);
            
            ps.setString(1, prov.getNombre());
            ps.setString(2, prov.getCorreo());
            ps.setString(3, prov.getDireccion());
            ps.setString(4, prov.getContacto());
            ps.setString(5, prov.getRfc());
            ps.setInt(6, prov.getIdProveedor());
            
            ps.execute();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error al modificar el proveedor: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarProveedor(int id) {
        String sql = "DELETE FROM proveedores WHERE id_proveedor=?";
        
        try {
            con = Conexion.getInstancia().getConnection();
            ps = con.prepareStatement(sql);
            
            ps.setInt(1, id);
            
            ps.execute();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar el proveedor: " + e.getMessage());
            return false;
        }
    }
}