package controles;

import java.sql.Connection;
import modelos.Administrador;

public class ControlLogin {
   Administrador administrador;

   public ControlLogin(){
     administrador = new Administrador();
   }
   
   public void agregarCliente (String nombre, String nombreAdmin, String paswd, int cuenta, Connection con){
      administrador.crear(nombre, nombreAdmin, paswd, con);
   }
    
   ///Valida al cliente en la base de datos
   public int validarCliente(String usuario, String password, Connection con){
      int ncuenta = administrador.validar(usuario, password, con);
      return( ncuenta );
   }

}