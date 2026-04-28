package modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 
 */
public class Equipo {
    
    // Variables
    public int idEquipo;
    public String nombre;
    public String estado;
    public PreparedStatement stmt;
    
    public Equipo() {
        
    }

    public Equipo(String nombre, String estado) {
        this.nombre = nombre;
        this.estado = estado;
    }

    public void crearEquipo(String nombreEquipo, String estado, Connection con) {
        try {
            String query = "INSERT INTO equipo (NombreEquipo, Estado) VALUES (?, ?)";
            stmt = con.prepareStatement(query);
            stmt.setString(1, nombreEquipo);
            stmt.setString(2, estado);
            stmt.execute();
        }catch (Exception e) { System.out.println ("No se pudo ejecutar agregar() a la tabla Cliente" + e ); }
    }
    
    public Equipo consultarEquipo(int idEquipo, Connection con) {
        try {
            Equipo equipo = new Equipo();
            String query = "SELECT * FROM equipo WHERE IDEquipo = ?";
            stmt = con.prepareStatement(query);
            stmt.setInt(1, idEquipo);
            ResultSet rs = stmt.executeQuery();
            rs.next(); 
            equipo.idEquipo = rs.getInt("IdEquipo");
            equipo.nombre = rs.getString("NombreEquipo");
            equipo.estado = rs.getString("Estado");
            rs.close();
            
            return equipo;
        }catch (Exception e) {
            System.out.println("No se pudo ejecutar consultarEquipo() a la tabla Equipo" + e);
        }
        
        return null;
    }  
    
    public ArrayList<Equipo> listarEquipos(Connection con) {
        ArrayList<Equipo> equipos = new ArrayList<>();
        
        try {
            String query = "SELECT * FROM equipo;";
            stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
                Equipo equipo = new Equipo();
                equipo.idEquipo = rs.getInt("IdEquipo");
                equipo.nombre = rs.getString("NombreEquipo");
                equipo.estado = rs.getString("Estado");
                
                equipos.add(equipo);
            }

            rs.close();
            
            return equipos;
        }catch (Exception e) {
            System.out.println("No se pudo ejecutar consultarEquipo() a la tabla Equipo" + e);
        }
                
        return null;
    }
}
