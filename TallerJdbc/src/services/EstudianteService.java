package services;

import connection.ClassConection;
import dto.GeneralResponse;
import dto.UpdateEstudianteRequest;
import entities.EstadoCivil;
import entities.Estudiantes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<Estudiantes> consultarTodos() {
        List<Estudiantes> lista = new ArrayList<>();
        String sql = "SELECT * FROM estudiantes";

        try (Connection conn = ClassConection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Estudiantes e = new Estudiantes(
                        rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("correo"),
                        rs.getInt("edad"),
                        EstadoCivil.valueOf(rs.getString("estado_civil"))
                );
                lista.add(e);
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar todos: " + e.getMessage());
        }
        return lista;
    }
    
    public Estudiantes consultarPorEmail(String correo) {
        Estudiantes estudiante = null;
        String sql = "SELECT * FROM estudiantes WHERE correo = ?";

        try (Connection conn = ClassConection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, correo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                estudiante = new Estudiantes(
                        rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("correo"),
                        rs.getInt("edad"),
                        EstadoCivil.valueOf(rs.getString("estado_civil"))
                );
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar por email: " + e.getMessage());
        }
        return estudiante;
    }

    public boolean validarId(long id) {
        String sql = "SELECT id FROM estudiantes WHERE id = ?";
        try (Connection conn = ClassConection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException ex) {
            System.out.println("Ocurrió un error al validar el ID: " + ex.getMessage());
            return false;
        }
    }
    
    public GeneralResponse actualizarEstudiante(UpdateEstudianteRequest estudianteRequest, long id) {
        if (!validarId(id)) {
            return new GeneralResponse("Error: el id: '" + id + "' no existe en la base de datos");
        }

        String update = "UPDATE estudiantes SET nombre = ?, apellido = ?, edad = ?, estado_civil = ? WHERE id = ?";
        try (Connection conn = ClassConection.getConnection();
             PreparedStatement ps = conn.prepareStatement(update)) {

            ps.setString(1, estudianteRequest.nombre());
            ps.setString(2, estudianteRequest.apellido());
            ps.setInt(3, estudianteRequest.edad());
            ps.setString(4, estudianteRequest.estadoCivil().name());
            ps.setLong(5, id);

            int filas = ps.executeUpdate();

            if (filas > 0) {
                return new GeneralResponse("Estudiante actualizado exitosamente");
            }

        } catch (SQLException ex) {
            System.out.println("Error al actualizar estudiante: " + ex.getMessage());
        }

        return new GeneralResponse("Error: no se pudo actualizar al estudiante");
    }
    
    public GeneralResponse eliminarEstudiante(long id) {
        String delete = "DELETE FROM estudiantes WHERE id = ?";
        try (Connection conn = ClassConection.getConnection();
             PreparedStatement ps = conn.prepareStatement(delete)) {

            ps.setLong(1, id);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                return new GeneralResponse("Estudiante eliminado exitosamente");
            }
        } catch (SQLException ex) {
            System.out.println("Error al eliminar estudiante: " + ex.getMessage());
        }

        return new GeneralResponse("Error: no se pudo eliminar el registro con id: " + id);
    }
}
