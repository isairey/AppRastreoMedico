package modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Evan
 */
public class UsuarioPublico {
    
    
    PreparedStatement stmt;
    // Variables
    private String nombre;
    private int idUsuario;

    public UsuarioPublico() {
    }

    public UsuarioPublico(String nombre, int idUsuario) {
        this.nombre = nombre;
        this.idUsuario = idUsuario;
    }
    
    public ArrayList<UsuarioPublico> listarUsuarios(Connection con) {
        ArrayList<UsuarioPublico> usuarios = new ArrayList<>();
        
        try {
            String query = "SELECT * FROM UsuarioPublico;";
            stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
                UsuarioPublico usuario = new UsuarioPublico();
                usuario.idUsuario = rs.getInt("idUsuario");
                usuario.nombre = rs.getString("nombre");
                
                usuarios.add(usuario);
            }

            rs.close();
            
            return usuarios;
        }catch (Exception e) {
            System.out.println("No se pudo ejecutar listarUsuarios() a la tabla UsuarioPublico" + e);
        }
                
        return null;
    }
    
    public void crear(String nombre, String IDUsuario, Connection con) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        
    public int validar(String IDUsuario, Connection con) {
        try {
            String query = "SELECT * FROM usuario WHERE IDUsuario = ?";
            stmt = con.prepareStatement(query);
            stmt.setString(1, IDUsuario);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) { ///Va al primer registro si lo hay
                int ncuenta = rs.getInt("IDUsuario");
                rs.close();
                return (ncuenta);
            }
        } catch (SQLException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        return 0;
    }
}
