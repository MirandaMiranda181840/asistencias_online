/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

/**
 *
 * @author miran
 */

import java.sql.*;

public class Conexion {
   private static Connection cnx = null;
   
   public static Connection obtener() throws SQLException, ClassNotFoundException {
      if (cnx == null) {
         try {
            Class.forName("com.mysql.jdbc.Driver");

            //modificar con tus params
            //Cris:"jdbc:mysql://localhost:3306/asistencias", "root", ""
            //Miranda:"jdbc:mysql://localhost:3306/asistencias", "root", "1234"
            cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/asistencias", "root", "1234");
         } catch (SQLException ex) {
            throw new SQLException(ex);
         } catch (ClassNotFoundException ex) {
            throw new ClassCastException(ex.getMessage());
         }
      }
      return cnx;
      
      
   }
   public static void cerrar() throws SQLException {
      if (cnx != null) {
         cnx.close();
      }
   }
   
   public boolean ifExists(String sSQL, String nombre, String periodo, String dias, String hora) throws SQLException {
    PreparedStatement ps = cnx.prepareStatement(sSQL);
    ps.setString(1, nombre);
    ps.setString(2, periodo);
    ps.setString(3, dias);
    ps.setString(4, hora);
    ResultSet rs = ps.executeQuery();
    return rs.next();
    }
   
   
   
}
