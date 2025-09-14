package services;

import connection.ClassConection;
import entities.Estudiantes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EstudianteService {

    public boolean insertarEstudiante(Estudiantes estudiante){
        String sql = "INSERT INTO estudiantes (nombre, apellido, correo, edad, estado_civil) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ClassConection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            
            stmt.setString(1, estudiante.getNombre());
            stmt.setString(2, estudiante.getApellido());
            stmt.setString(3, estudiante.getCorreo());
            stmt.setInt(4, estudiante.getEdad());
            stmt.setString(5, estudiante.getEstadoCivil().toString());


            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e){
            System.out.println("Ocurrio algun error al insertar estudiante" + e.getMessage());
            return false;
        }

    }


    
    public boolean existeCorreo(String correo) {
        String sql = "SELECT COUNT(*) FROM estudiantes WHERE correo = ?";
        
        try (Connection conn = ClassConection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, correo);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.out.println("Error al verificar correo: " + e.getMessage());
        }
        return false;
    }

    
}
