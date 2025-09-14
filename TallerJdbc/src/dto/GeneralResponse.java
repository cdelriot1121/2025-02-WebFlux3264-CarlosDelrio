package dto;

public class GeneralResponse {
    private String mensaje;
    
    public GeneralResponse(String mensaje) {
        this.mensaje = mensaje;
    }
    
    public String getMensaje() {
        return mensaje;
    }
    
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    @Override
    public String toString() {
        return mensaje;
    }
}