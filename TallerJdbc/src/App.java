import connection.ClassConection;
import java.sql.Connection;


public class App {
    public static void main(String[] args) throws Exception {
    
        try(Connection connection = ClassConection.getConnection()){
            System.out.println("Conexion exitosa a la base de datos");
            System.out.println("Acerca de la conexion: " + connection.getMetaData().getDatabaseProductName());
        } catch (Exception e){
            System.out.println( "Error para conectar a la base de datos");
        }
    }
}