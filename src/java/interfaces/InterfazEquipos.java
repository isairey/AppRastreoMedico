package interfaces;

import controles.ControlEquipo;
import java.sql.Connection;
import java.io.PrintWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelos.Equipo;

//Utilizando urlPatterns permite agregar parametros a la anotacion
@WebServlet(urlPatterns = "/Equipos", 
  initParams = {
     @WebInitParam(name = "class", value = "interfaces.InterfazExtraccion")
  }
)
public class InterfazEquipos extends HttpServlet {
  HttpServletResponse thisResponse;
  HttpServletRequest thisRequest;
  PrintWriter out;
  HttpSession sesion;
  Connection con;

   ///Redirige cualquier GET recibido a POST
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException { 
      doPost(request,response);
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      thisRequest = request;
      thisResponse = response;
      String operacion;
      ArrayList<HashMap> data = new ArrayList<>();
      ///La conexion se establece en ContextListener
      con = (Connection) request.getServletContext().getAttribute("DBConnection");

      ///Solo permitir acceso si existe una sesion
      sesion = thisRequest.getSession(false);
//      if (sesion == null) ///El usuario no esta logeado


      operacion = thisRequest.getParameter("operacion");
      if (operacion == null ) ///Es la primera llamada a ExtraerEfectivo (viene de menu.html, con usuario exitosamente logeado)
            listarEquipos();
      else if(operacion.equals("agregar"))
            agregarEquipo();
      else if (operacion.equals("eliminar")){
//            eliminarEquipo();
      }
   }

    private void encabezadoHTML() throws ServletException, IOException {
        thisResponse.setContentType("text/html");
        out = thisResponse.getWriter();
        
        ///Preparar el encabezado de la pagina Web de respuesta
        out.println(
            "<!DOCTYPE html> \n" +
            "<html> \n" +
            "<head> \n" +
            "<meta charset=utf-8> \n" +
            "</head> \n" +
            "<body> \n" +
            "<title>Sistema EMTEC</title> \n" +
            "<h2>Administración de equipos</h2> \n"
        );
    }
    
    ///Es importante observar que todas las formas (form) definen la accion POST para
    ///que el metodo doPost sea el que se ejecuta en todos los casos.
    private void listarEquipos() throws ServletException, IOException {
        encabezadoHTML(); ///Preparar encabezado de la pagina Web
        out.println("Insertar nuevo equipo</p> \n" +
            "<form method=POST action=Equipos> \n" +
               "<input type=hidden name=operacion value=agregar> \n" +
               "Nombre: <input type=text name=nombre size=15 autofocus></p> \n" +
               "Estado: <input type=text name=estado size=15 autofocus></p> \n" +
               "<input type=submit value=Enviar name=B1></p> \n" +
            "</form> \n"
        );

        Equipo equipo = new Equipo();
        
        ArrayList<Equipo> equipos = equipo.listarEquipos(con);
        
        String tablaEquipos = "<table>" 
                + "<tr>"
                + "<th>Id</th>"
                + "<th>Nombre</th>"
                + "<th>Estado</th>"
                + "<tr>";
        
        for (Equipo tmpEquipo : equipos) {
            tablaEquipos += "<tr>"
                    + "<td>" + tmpEquipo.idEquipo + "</td>"
                    + "<td>" + tmpEquipo.nombre + "</td>" 
                    + "<td>" + tmpEquipo.estado + "</td>";
        }
        
        tablaEquipos += "</table>";
        
        out.println(tablaEquipos);
    }
    
    private void agregarEquipo() throws ServletException, IOException {
        encabezadoHTML();
        String nombreEquipo = thisRequest.getParameter("nombre");
        String estado = thisRequest.getParameter("estado");

        ControlEquipo ce = new ControlEquipo();
        
        if (nombreEquipo.length() > 0 && estado.length() > 0) {
            ce.agregarEquipo(nombreEquipo, estado, con);
//            encabezadoHTML();///Preparar encabezado de la pagina Web
            thisResponse.setContentType("text/html");
            listarEquipos();
        }   
    }
    
//
//   private void extraerEfectivo() throws ServletException, IOException {
//      int ncuenta;
//      boolean resultado;
//      String sCuenta;
//      String errorMsg = null;
//      String sCantidad = thisRequest.getParameter("cantidad");
//      if (sCantidad.length() > 0) {
//         float cantidad = Float.valueOf(sCantidad.trim()).floatValue();
//         if (cantidad > 0) {
//            HttpSession sesion = thisRequest.getSession(false);
//            sCuenta =  (String) sesion.getAttribute("cuenta");
//            ncuenta = Integer.parseInt(sCuenta);
//            
//            ControlExtraccion ce = new ControlExtraccion();
//            resultado = ce.extraerEfectivo(ncuenta, cantidad, con);
//            if (resultado == true) { ///Extraccion exitosa
//               encabezadoHTML();///Preparar encabezado de la pagina Web
//               float saldo = ce.consultaSaldo(ncuenta, con);
//               out.println(
//                  "Operacion exitosa.</p> \n" +
//                  "Su saldo actual es: " + saldo +".</p> \n" +
//                  "<form method=POST action='Extraer'> \n" +
//                     "<input type='hidden' name='operacion' value='terminar'> \n" +
//                     "<input type='submit' value='Terminar'></p> \n" +
//                   "</form> \n" +
//                  "</body> \n" +
//                  "</html>"
//               );
//            }else {
//               errorMsg = "Esa cantidad excede a su saldo, o es mayor a lo que puede extraer en una sesion. Por favor indique una cantidad menor.";
//            }
//         } else {
//            errorMsg = "Por favor indique una cantidad mayor que cero.";
//         }
//      } else {
//         errorMsg = "Por favor indique una cantidad.";
//      }
//      if(errorMsg != null) {
//        thisResponse.setContentType("text/html");
//        out.println("<h3><font color=red>" + errorMsg + "</font></h3>");
//        solicitarCantidad();
//      }
//   }
}
