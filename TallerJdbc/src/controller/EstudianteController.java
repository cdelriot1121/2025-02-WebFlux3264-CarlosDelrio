package controller;

import entities.Estudiantes;
import java.util.List;
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
        
        
        if (existeCorreo(estudiante.getCorreo())) {
            System.out.println("El correo ya está registrado en la base de datos");
            return false;
        }
        
        return estudianteService.insertarEstudiante(estudiante);
    }
    
    public boolean existeCorreo(String correo) {
        return estudianteService.existeCorreo(correo);
    }

      
    public List<Estudiantes> consultarTodos() {
        return estudianteService.consultarTodos();
    }

    
    public Estudiantes consultarPorEmail(String correo) {
        if (correo == null || correo.trim().isEmpty() || !correo.contains("@")) {
            System.out.println("Correo inválido");
            return null;
        }
        return estudianteService.consultarPorEmail(correo);
    }
}
    
