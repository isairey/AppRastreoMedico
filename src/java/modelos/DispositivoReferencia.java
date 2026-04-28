package modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Evan
 */
class DispositivoReferencia {
    PreparedStatement stmt;
    
    // Variables
    public int idReferencia;
    public String ubicacion;

    public DispositivoReferencia() {
    }

    public DispositivoReferencia(int idReferencia, String ubicacion) {
        this.idReferencia = idReferencia;
        this.ubicacion = ubicacion;
    }
    
     public void eliminaDispositivoReferencia(DispositivoReferencia dr, Connection con) {
         try {
         String query = "DELETE FROM DispositivoReferencia WHERE idReferencia = ?";
         stmt = con.prepareStatement(query);
         stmt.setInt(1, dr.idReferencia);
         stmt.execute();
      } catch (SQLException e) {System.out.println ("No se pudo ejecutar eliminaDispositivoReferencia() a la tabla DispositivoReferencia" + e);}
    }
    
    public void modificaDispositivoReferencia(DispositivoReferencia dr, Connection con) {
         try {
         String query = "UPDATE DispositivoReferencia SET ubicacion = ? WHERE idReferencia = ?";
         stmt = con.prepareStatement(query);
         stmt.setInt(1, dr.idReferencia);
         stmt.setString(2, dr.ubicacion);
         stmt.execute();
      } catch (SQLException e) {System.out.println ("No se pudo ejecutar modificaDispositivoReferencia() a la tabla DispositivoReferencia" + e);}
    }
  
    public void agregaDispositivoReferencia(int idReferencia, String ubicacion, Connection con) {
      try {
         String query = "INSERT INTO DispositivoReferencia (IDReferencia, Ubicacion) VALUES (?, ?)";
         stmt = con.prepareStatement(query);
         stmt.setInt(1, idReferencia);
         stmt.setString(2, ubicacion);
         stmt.execute();
      }
      catch (Exception e) 
      {
          System.out.println ("No se pudo ejecutar agregarDispositivoReferencia() a la tabla DispositivoReferencia" + e );
      }
   }
    public ArrayList<DispositivoReferencia> listarDispositivosdeReferencia(Connection con) 
    {
        ArrayList<DispositivoReferencia> dispositivos = new ArrayList<>();
        try 
        {
            String query = "SELECT * FROM DispositivoReferencia;";
            stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
                DispositivoReferencia dr = new DispositivoReferencia();
                dr.idReferencia = rs.getInt("IdReferencia");
                dr.ubicacion = rs.getString("Ubicacion");
                
                dispositivos.add(dr);
            }

            rs.close();
            
            return dispositivos;
        }
        catch (Exception e) {
            System.out.println("No se pudo ejecutar listarDispositivoReferencia() a la tabla DispositivoReferencia" + e);
        }
        return null;
    }
}
