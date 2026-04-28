package modelos;
import java.sql.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Datos {
    PreparedStatement stmt;
    int IdEquipo;
    int IdRastreo;
    String Ubicacion;
    Date Fecha;
    
    public Datos() {
        
    }
    
    public Datos(int IdEquipo, int IdRastreo, String Ubicacion, Date Fecha)
    {
        this.IdEquipo = IdEquipo;
        this.IdRastreo = IdRastreo;
        this.Ubicacion = Ubicacion; 
        this.Fecha = Fecha;
    }
        
    
    public void agregaDatos(int IdEquipo, int IdRastreo, String Ubicacion, Date Fecha, Connection con) {
        try { 
            String query = "INSERT INTO datos (IdEquipo, IdRastreo, Ubicacion, Fecha) VALUES (?, ?, ?, ?)";
            stmt = con.prepareStatement(query);
            stmt.setInt(1, IdEquipo);
            stmt.setInt(2, IdRastreo);
            stmt.setString(3, Ubicacion);
            stmt.setDate(4, Fecha);
            stmt.execute();
        }catch (SQLException e) { System.out.println ("No se pudo agregar los datos" + e ); }
    }
    
    public Datos consultaDatos (int IdEquipo, int IdRastreo, Connection con){
        try {
            Datos datos = new Datos();
            String query = "SELECT saldo FROM cuenta WHERE IdEquipo = ? AND IdRastreo = ?";
            stmt = con.prepareStatement(query);
            stmt.setInt(1, IdEquipo);
            stmt.setInt(2, IdRastreo);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            datos.IdEquipo = rs.getInt("IdEquipo");
            datos.IdRastreo = rs.getInt("IdRastreo");
            datos.Ubicacion = rs.getString("Ubicacion");
            datos.Fecha = rs.getDate("Fecha");
            rs.close();
            return datos;
        }catch (Exception e) {System.out.println("No se pudo ejecutar consultaDatos() a la tabla Datos" + e); }
        return null;
    }
}
