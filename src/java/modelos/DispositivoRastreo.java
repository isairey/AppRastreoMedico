package modelos;

import java.sql.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DispositivoRastreo {
    
    PreparedStatement stmt;

    public int CodigoRastreo;
    
    public void agregaDispositivoRastreo(int CodigoRastreo, Connection con) {
        try {
            String query = "INSERT INTO (CodigoRastreo, Datos) VALUES (?)";
            stmt = con.prepareStatement(query);
            stmt.setInt(1, CodigoRastreo);
            stmt.execute();
        }catch (Exception e) { System.out.println ("No se pudo agregar el Dispositivo de Rastreo" + e); }
    }
    
    public void eliminaDispositivoRastreo(int CodigoRastreo, Connection con)
    {
        try {
            String query = "DELETE FROM dispositivoderastreo WHERE CodigoRastreo = ?";
            stmt = con.prepareStatement(query);
            stmt.setInt(1, CodigoRastreo);
            stmt.execute();
        }catch (Exception e) { System.out.println ("No se pudo eliminar el Dispositivo de Rastreo" + e); } 
    }
    public ArrayList<DispositivoRastreo> listarDispositivosdeRastreo(Connection con) 
    {
        ArrayList<DispositivoRastreo> dispositivos = new ArrayList<>();
        try 
        {
            String query = "SELECT * FROM DispositivoRastreo;";
            stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
                DispositivoRastreo dr = new DispositivoRastreo();
                dr.CodigoRastreo = rs.getInt("CodigoRastreo");
                
                dispositivos.add(dr);
            }

            rs.close();
            
            return dispositivos;
        }
        catch (Exception e) {
            System.out.println("No se pudo ejecutar listarDispositivosdeRastreo() a la tabla DispositivoRastreo" + e);
        }
        return null;
    }
}


