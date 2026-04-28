package interfaces;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = "/Logout",
  initParams = {
     @WebInitParam(name = "class", value = "interfaces.LogoutServlet")
  }
)
public class LogoutServlet extends HttpServlet {
   //Redirige cualquier GET recibido a POST
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException { 
      doPost(request,response);
   }
   
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	///Si la sesion existe, invalidarla
    	HttpSession session = request.getSession(false);
    	if(session != null){
    	   session.invalidate();
    	}
    	response.sendRedirect("index.html");
   }

}
