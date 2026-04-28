package modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class Administrador {
   
    // Variables
    private String nombre;
    private String usuario;
    private String password;
    PreparedStatement stmt;

    public Administrador() {
    }

    public Administrador(String nombre, String usuario, String password) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.password = password;
    }

    public void crear(String nombre, String nombreAdmin, String paswd, Connection con) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int validar(String usuario, String password, Connection con) {
        try {
            String query = "SELECT * FROM administrador WHERE usuario = ? and password = ?";
            stmt = con.prepareStatement(query);
            stmt.setString(1, usuario);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) { ///Va al primer registro si lo hay
                int ncuenta = rs.getInt("id");
                rs.close();
                return (ncuenta);
            }
        } catch (SQLException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        return 0;
    }
}
