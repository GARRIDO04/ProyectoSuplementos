package modelo;

import config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private Connection con;
    private PreparedStatement ps;

    public boolean registrarCliente(Cliente cli) {
        String sql = "INSERT INTO clientes (nombre, telefono, correo) VALUES (?, ?, ?)";
        
        try {
            con = Conexion.getInstancia().getConnection();
            ps = con.prepareStatement(sql);
            
            ps.setString(1, cli.getNombre());
            ps.setString(2, cli.getTelefono());
            ps.setString(3, cli.getCorreo());
            
            ps.execute();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error al registrar el cliente: " + e.getMessage());
            return false;
        }
    }

    public List<Cliente> listarClientes() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        ResultSet rs;

        try {
            con = Conexion.getInstancia().getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Cliente cli = new Cliente();
                
                cli.setIdCliente(rs.getInt("id_cliente"));
                cli.setNombre(rs.getString("nombre"));
                cli.setTelefono(rs.getString("telefono"));
                cli.setCorreo(rs.getString("correo"));

                lista.add(cli);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar los clientes: " + e.getMessage());
        }
        
        return lista;
    }

    public Cliente buscarCliente(int id) {
        Cliente cli = new Cliente();
        String sql = "SELECT * FROM clientes WHERE id_cliente=?";
        ResultSet rs;
        
        try {
            con = Conexion.getInstancia().getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                cli.setIdCliente(rs.getInt("id_cliente"));
                cli.setNombre(rs.getString("nombre"));
                cli.setTelefono(rs.getString("telefono"));
                cli.setCorreo(rs.getString("correo"));
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar el cliente: " + e.getMessage());
        }
        return cli;
    }
    
    public boolean modificarCliente(Cliente cli) {
        String sql = "UPDATE clientes SET nombre=?, telefono=?, correo=? WHERE id_cliente=?";
        
        try {
            con = Conexion.getInstancia().getConnection();
            ps = con.prepareStatement(sql);
            
            ps.setString(1, cli.getNombre());
            ps.setString(2, cli.getTelefono());
            ps.setString(3, cli.getCorreo());
            ps.setInt(4, cli.getIdCliente());
            
            ps.execute();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error al modificar el cliente: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarCliente(int id) {
        String sql = "DELETE FROM clientes WHERE id_cliente=?";
        
        try {
            con = Conexion.getInstancia().getConnection();
            ps = con.prepareStatement(sql);
            
            ps.setInt(1, id);
            
            ps.execute();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar el cliente: " + e.getMessage());
            return false;
        }
    }
}