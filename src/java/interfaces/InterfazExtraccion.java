package interfaces;

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

//Utilizando urlPatterns permite agregar parametros a la anotacion
@WebServlet(urlPatterns = "/Extraccion", 
  initParams = {
     @WebInitParam(name = "class", value = "interfaces.InterfazExtraccion")
  }
)
public class InterfazExtraccion extends HttpServlet {
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
      if (sesion == null) ///El usuario no esta logeado
         thisResponse.sendRedirect("index.html");
            try {
         String query = "SELECT * FROM administrador;";
         PreparedStatement stmt = con.prepareStatement(query);
         
         ResultSet rs = stmt.executeQuery();
         
         if (rs.next()) { ///Va al primer registro si lo hay
            int ncuenta = rs.getInt ("id");
            rs.close();
            System.out.println(ncuenta);
//            stmt.close();
         }
      } catch (SQLException e) {}

//      operacion = thisRequest.getParameter("operacion");
//      if (operacion == null ) ///Es la primera llamada a ExtraerEfectivo (viene de menu.html, con usuario exitosamente logeado)
//         solicitarCantidad();
//      else if(operacion.equals("extraer"))
//         extraerEfectivo();
//      else if (operacion.equals("terminar")){
//         RequestDispatcher rd = request.getRequestDispatcher("Logout");
//         rd.forward(request, response);
//      }
   }

//   private void encabezadoHTML() throws ServletException, IOException {
//      thisResponse.setContentType("text/html");
//      out = thisResponse.getWriter();
//      ///Preparar el encabezado de la pagina Web de respuesta
//      out.println(
//         "<!DOCTYPE html> \n" +
//         "<html> \n" +
//         "<head> \n" +
//         "<meta charset=utf-8> \n" +
//         "</head> \n" +
//         "<body> \n" +
//         "<title>Banco AMSS</title> \n" +
//         "<h2>Cajero Electronico</h2> \n" +
//         "<h3>Extraer efectivo</h3>\n"
//      );
//   }
//  ///Es importante observar que todas las formas (form) definen la accion POST para
//  ///que el metodo doPost sea el que se ejecuta en todos los casos.
//   private void solicitarCantidad() throws ServletException, IOException {
//      encabezadoHTML(); ///Preparar encabezado de la pagina Web
//      out.println("Cantidad a extraer</p> \n" +
//         "<form method=POST action=Extraer> \n" +
//            "<input type=hidden name=operacion value=extraer> \n" +
//            "Cantidad: <input type=text name=cantidad size=15 autofocus></p> \n" +
//            "<input type=submit value=Enviar name=B1></p> \n" +
//         "</form> \n"
//      );
//
//      out.println(
//         "<form method=POST action=Extraer> \n" +
//            "<input type=hidden name=operacion value=terminar> \n" +
//            "<input type=submit value=Cancelar name=B2></p> \n" +
//            "</form> \n" +
//        "</body>" +
//        "</html>"
//      );
//   }
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
       public static ArrayList<HashMap> getResults(ResultSet rs, 
            ResultSetMetaData rsmd) throws SQLException {
        ArrayList<HashMap> data = new ArrayList<>();
        ArrayList<String> heads = new ArrayList<>();
        
        for (int i = 1; i <= rsmd.getColumnCount(); ++i) {
            heads.add(rsmd.getColumnName(i));
        }
        
        while(rs.next()) {
            HashMap row = new HashMap();
            for(int i = 0; i < heads.size(); i++) {
                switch(rsmd.getColumnType(i + 1)) {
                    case Types.VARCHAR:
                        row.put(heads.get(i), rs.getString(i + 1));
                        break;
                    case Types.INTEGER:
                        row.put(heads.get(i), rs.getInt(i + 1));
                        break;
                    case Types.DOUBLE:
                        row.put(heads.get(i), rs.getDouble(i + 1));
                        break;
                    case Types.FLOAT:
                        row.put(heads.get(i), rs.getFloat(i + 1));
                        break;
                    case Types.BIT:
                        row.put(heads.get(i), rs.getBoolean(i + 1));
                        break;
                    case Types.DECIMAL:
                        row.put(heads.get(i), rs.getDouble(i + 1));
                }
            }

            data.add(row);
        }
        
        return data;
    }
}
