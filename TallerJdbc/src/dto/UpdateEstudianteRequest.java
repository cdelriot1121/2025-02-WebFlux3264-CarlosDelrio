package dto;

import entities.EstadoCivil;

public class UpdateEstudianteRequest {
    private String nombre;
    private String apellido;
    private int edad;
    private EstadoCivil estadoCivil;
    
    public UpdateEstudianteRequest(String nombre, String apellido, int edad, EstadoCivil estadoCivil) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.estadoCivil = estadoCivil;
    }
    
    public String nombre() {
        return nombre;
    }
    
    public String apellido() {
        return apellido;
    }
    
    public int edad() {
        return edad;
    }
    
    public EstadoCivil estadoCivil() {
        return estadoCivil;
    }
}