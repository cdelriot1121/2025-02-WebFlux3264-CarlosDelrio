package services;

import connection.ClassConection;
import entities.Estudiantes;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

    
}
