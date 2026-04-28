package interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;  
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
                                     
@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
       ServletContext context = servletContextEvent.getServletContext();
       try {
         //Sintaxis de DBConnectionManager(String dbURL, String user, String pwd)
        DBConnectionManager connectionManager = new DBConnectionManager(
            "jdbc:mysql://10.15.125.144:3306/sistema_rastreo", "root", ""
         );
        
        // Set de la conexion como atributo 
        context.setAttribute("DBConnection", connectionManager.getConnection());

        } catch (ClassNotFoundException e) {
                e.printStackTrace();
        } catch (SQLException e) {
                e.printStackTrace();
       }
    }

   @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
   	 Connection conn = (Connection) servletContextEvent.getServletContext().getAttribute("DBConnection");
    	 /*try {
                //conn.close();
        } catch (SQLException e) {
                e.printStackTrace();
        }*/
    }
}
