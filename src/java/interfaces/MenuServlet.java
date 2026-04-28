package interfaces;

import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//Utilizando urlPatterns en lugar de urlPattern permite agregar mas parametros a la anotacion
@WebServlet(urlPatterns = "/Menu", 
  initParams = {
     @WebInitParam(name = "class", value = "interfaces.MenuServlet")
  }
)
public class MenuServlet extends HttpServlet {

   //Redirige cualquier GET recibido a POST
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException { 
      doPost(request,response);
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      PrintWriter out = response.getWriter();

      //Solo permitir acceso si existe una sesion
        HttpSession sesion = request.getSession(false);
      if (sesion == null) { ///El usuario no esta logeado
         response.setContentType("text/html");
		     out.println("<font color=red>Favor de proporcionar primero usuario y clave.</font>");      
         RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.html");
			   rd.include(request, response); ///include() permite que el mensaje anterior se incluya en la pagina Web         
      } else {
         String tipoUsuario = (String) sesion.getAttribute("tipo");
         
         if (tipoUsuario.equals("admin")) {
             //Mostrar el menu de opciones
             response.setContentType("text/html");
             RequestDispatcher rd = getServletContext().getRequestDispatcher("/menuAdmin.html");
             rd.include(request, response);
         }
         else if (tipoUsuario.equals("publico")) {
             //Mostrar el menu de opciones
             response.setContentType("text/html");
             RequestDispatcher rd = getServletContext().getRequestDispatcher("/menuPublico.html");
             rd.include(request, response);
         }
      }   
   }
}
         