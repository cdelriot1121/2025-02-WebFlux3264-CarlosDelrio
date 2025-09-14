package controller;

import entities.EstadoCivil;
import entities.Estudiantes;
import services.EstudianteService;

public class EstudianteController {

    private final EstudianteService estudianteService;

    public EstudianteController(){
        this.estudianteService = new EstudianteService();
    }

    public boolean registrarEstudiante(String nombre, String apellido, String correo, int edad, EstadoCivil estadocivil){
        if (nombre == null || nombre.trim().isEmpty() || apellido == null || apellido.trim().isEmpty()
        || correo == null || correo.trim().isEmpty()|| !correo.contains("@") || edad < 0){
            return false;
        }
        Estudiantes nuevoEstudiante = new Estudiantes(nombre,apellido, correo, edad, estadocivil);

        return estudianteService.insertarEstudiante(nuevoEstudiante);
        
    }



}
