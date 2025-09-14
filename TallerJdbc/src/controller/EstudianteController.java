package controller;

import entities.Estudiantes;
import services.EstudianteService;

public class EstudianteController {
    private EstudianteService estudianteService;

    public EstudianteController() {
        this.estudianteService = new EstudianteService();
    }

    public boolean registrarEstudiante(Estudiantes estudiante) {
        if (estudiante.getNombre() == null || estudiante.getNombre().trim().isEmpty() 
            || estudiante.getApellido() == null || estudiante.getApellido().trim().isEmpty()
            || estudiante.getCorreo() == null || estudiante.getCorreo().trim().isEmpty()
            || !estudiante.getCorreo().contains("@") || estudiante.getEdad() < 0) {
            return false;
        }
        
        // Verificar si el correo ya existe
        if (existeCorreo(estudiante.getCorreo())) {
            System.out.println("El correo ya está registrado en la base de datos");
            return false;
        }
        
        return estudianteService.insertarEstudiante(estudiante);
    }
    
    public boolean existeCorreo(String correo) {
        return estudianteService.existeCorreo(correo);
    }
    
    // Aquí tus compañeros podrán implementar los demás métodos del CRUD:
    // - actualizarEstudiante
    // - eliminarEstudiante
    // - listarEstudiantes
    // - buscarEstudiantePorEmail
}
