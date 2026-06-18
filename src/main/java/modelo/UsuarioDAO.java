package modelo;

import config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO {
    // Se elimina la línea: Conexion cn = new Conexion();
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    public Usuario validar(String username, String password) {
        Usuario user = null;
        String sql = "SELECT * FROM usuarios WHERE username = ? AND password = ?";
        try {
            // Llamamos al Singleton exactamente como lo haces en ClienteDAO
            con = Conexion.getInstancia().getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                user = new Usuario();
                user.setIdUsuario(rs.getInt("idUsuario"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRol(rs.getString("rol"));
            }
        } catch (Exception e) {
            System.err.println("Error en el login DAO: " + e.getMessage());
        }
        return user;
    }
}