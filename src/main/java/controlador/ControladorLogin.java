package controlador;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.Usuario;
import modelo.UsuarioDAO;

// Nota: Si tu proyecto usa javax.servlet en lugar de jakarta.servlet, cambia los imports.
@WebServlet(name = "ControladorLogin", urlPatterns = {"/ControladorLogin"})
public class ControladorLogin extends HttpServlet {

    UsuarioDAO udao = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion != null && accion.equals("Salir")) {
            HttpSession session = request.getSession();
            session.invalidate(); // Destruye por completo la sesión (Cierre de sesión seguro)
            response.sendRedirect("login.jsp");
        } else {
            response.sendRedirect("login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        
        if (accion != null && accion.equals("Ingresar")) {
            String userStr = request.getParameter("txtuser");
            String passStr = request.getParameter("txtpass");
            
            Usuario u = udao.validar(userStr, passStr);
            
            if (u != null) {
                // El usuario es válido -> Creamos sesión activa
                HttpSession session = request.getSession();
                session.setAttribute("usuarioLogueado", u);
                
                // Redireccionamos directo al Dashboard principal
                response.sendRedirect("index.jsp");
            } else {
                // Falló el acceso -> Mandamos error de regreso al login
                request.setAttribute("error", "Usuario o contraseña inválidos.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        }
    }
}