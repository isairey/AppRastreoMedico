package controles;

import java.sql.Connection;
import modelos.UsuarioPublico;

public class ControlLogin_Publico {
   UsuarioPublico usuarioPublico;

   public ControlLogin_Publico(){
      usuarioPublico = new UsuarioPublico();
   }
   public void agregarUsuarioPublico (String nombre, String IDUsuario, Connection con){
      usuarioPublico.crear(nombre, IDUsuario, con);
   }
   ///Valida al cliente en la base de datos
   public int validarUsuarioPublico(String IDUsuario, Connection con){
      int ncuenta = usuarioPublico.validar(IDUsuario, con);
      return( ncuenta );
   }

}
