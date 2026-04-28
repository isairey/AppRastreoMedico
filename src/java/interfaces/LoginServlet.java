/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;
    
import java.io.IOException ;
import java.io.PrintWriter ;
import java.sql.Connection ;
import javax.servlet.RequestDispatcher ;
import javax.servlet.ServletException ;
import javax.servlet.ServletContext ;
import javax.servlet.http.HttpServlet ;
import javax.servlet.http.HttpServletRequest ;
import javax.servlet.http.HttpServletResponse ;
import javax.servlet.http.HttpSession ;
import controles.ControlLogin ;
import controles.ControlLogin ;
import controles.ControlLogin_Publico ;
import controles.ControlLogin_Publico;

public class LoginServlet extends HttpServlet {

    ///Redirige cualquier GET recibido a POST
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String user = request.getParameter("usuario");
        String pwd = request.getParameter("password");
        String idPublica = request.getParameter("idPublica");



        ///La conexion se establecio en ContextListener
        Connection conn = (Connection) getServletContext().getAttribute("DBConnection");

        ControlLogin cLogin = new ControlLogin();
        int idAdmin = cLogin.validarCliente(user, pwd, conn);

        
        ControlLogin_Publico cLoginPublico = new ControlLogin_Publico();
        int idPublico = cLoginPublico.validarUsuarioPublico(idPublica, conn);
            
        if (idAdmin != 0) { ///El usuario o clave son incorrectos
            
            ///Crea una sesion que expirara en 30 minutos
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(30 * 60);

            String sCuenta = Integer.toString(idAdmin);
            session.setAttribute("tipo", "admin");
            session.setAttribute("idAdmin", sCuenta);
            session.setAttribute("usuario", user);
            session.setAttribute("password", pwd);
            RequestDispatcher rd = request.getRequestDispatcher("Menu");
            rd.forward(request, response);
        }
        if (idPublico !=0) {
            ///Crea una sesion que expirara en 30 minutos
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(30 * 60);

            String sCuenta = Integer.toString(idPublico);
            session.setAttribute("tipo", "publico");
            session.setAttribute("idUsuario", sCuenta);
            RequestDispatcher rd = request.getRequestDispatcher("Menu");
            rd.forward(request, response);
        }
        else {
            PrintWriter out = response.getWriter();
            response.setContentType("text/html");
            out.println("<h3><font color=red>El usuario o la clave son incorrectos.</font></h3>");
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.html");
            rd.include(request, response); ///include() permite que el mensaje anterior se incluya en la pagina Web
        }
    }

}


