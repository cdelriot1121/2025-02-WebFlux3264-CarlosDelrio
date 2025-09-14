import connection.ClassConection;
import controller.EstudianteController;
import entities.EstadoCivil;
import entities.Estudiantes;
import java.sql.Connection;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        //ojo siempre comprobar la conexion a la base de datos primero antes de runnear el menu
        try (Connection connection = ClassConection.getConnection()) {
            System.out.println("Conexión exitosa a la base de datos");
            System.out.println("Acerca de la conexión: " + connection.getMetaData().getDatabaseProductName());
        } catch (Exception e) {
            System.out.println("Error para conectar a la base de datos");
            return; // Salimos si no hay conexión
        }
        
        // tenemos que instanciar el controlador que va a interactuar y recibir peticiones a la vista (terminal en este caso)
        EstudianteController controller = new EstudianteController();
        
        // instanciar un objeto de libreria scanner para poder pedir valores al input
        Scanner scanner = new Scanner(System.in);
        
        boolean running = true;
        while (running) {
            System.out.println("\n====== SISTEMA DE GESTIÓN DE ESTUDIANTES ======");
            System.out.println("1. Insertar Estudiante");
            System.out.println("2. Actualizar Estudiante");
            System.out.println("3. Eliminar Estudiante");
            System.out.println("4. Consultar todos los estudiantes");
            System.out.println("5. Consultar Estudiante por email");
            System.out.println("6. Salir del programa");
            System.out.print("Seleccione una opción: ");
            
            int opcion = 0;
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido");
                continue;
            }
            
            switch (opcion) {
                case 1:
                    registrarEstudiante(scanner, controller);
                    break;
                case 2:
                    System.out.println("Opción pendiente de implementar por tus compañeros");
                    break;
                case 3:
                    System.out.println("Opción pendiente de implementar por tus compañeros");
                    break;
                case 4:
                    System.out.println("Opción pendiente de implementar por tus compañeros");
                    break;
                case 5:
                    System.out.println("Opción pendiente de implementar por tus compañeros");
                    break;
                case 6:
                    System.out.println("¡Gracias por utilizar el sistema!");
                    running = false;
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
        
        scanner.close();
    }
    
    private static void registrarEstudiante(Scanner scanner, EstudianteController controller) {
        System.out.println("\n=== REGISTRO DE NUEVO ESTUDIANTE ===");
        
        // Recopilar información del estudiante
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        
        String correo = "";
        boolean correoValido = false;
        while (!correoValido) {
            System.out.print("Correo electrónico: ");
            correo = scanner.nextLine();
            
            // Verificar si el correo ya existe
            if (controller.existeCorreo(correo)) {
                System.out.println("¡Error! Ya existe un estudiante con ese correo. Intente con otro.");
            } else {
                // Validación básica de formato de correo
                if (correo.contains("@") && correo.contains(".")) {
                    correoValido = true;
                } else {
                    System.out.println("Formato de correo inválido. Debe contener '@' y '.'");
                }
            }
        }
        
        int edad = 0;
        boolean edadValida = false;
        while (!edadValida) {
            try {
                System.out.print("Edad: ");
                edad = Integer.parseInt(scanner.nextLine());
                if (edad > 0 && edad < 120) {
                    edadValida = true;
                } else {
                    System.out.println("La edad debe estar entre 1 y 120 años");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido");
            }
        }
        
        EstadoCivil estadoCivil = null;
        while (estadoCivil == null) {
            System.out.println("Estado Civil: ");
            System.out.println("1. SOLTERO");
            System.out.println("2. CASADO");
            System.out.println("3. VIUDO");
            System.out.println("4. UNION_LIBRE");
            System.out.println("5. DIVORCIADO");
            System.out.print("Seleccione una opción: ");
            
            try {
                int opcionEstado = Integer.parseInt(scanner.nextLine());
                switch (opcionEstado) {
                    case 1: estadoCivil = EstadoCivil.SOLTERO; break;
                    case 2: estadoCivil = EstadoCivil.CASADO; break;
                    case 3: estadoCivil = EstadoCivil.VIUDO; break;
                    case 4: estadoCivil = EstadoCivil.UNION_LIBRE; break;
                    case 5: estadoCivil = EstadoCivil.DIVORCIADO; break;
                    default: System.out.println("Opción no válida");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido");
            }
        }
        
        // Crear estudiante con los datos recopilados
        Estudiantes nuevoEstudiante = new Estudiantes();
        nuevoEstudiante.setNombre(nombre);
        nuevoEstudiante.setApellido(apellido);
        nuevoEstudiante.setCorreo(correo);
        nuevoEstudiante.setEdad(edad);
        nuevoEstudiante.setEstadoCivil(estadoCivil);
        
        // Registrar el estudiante
        boolean resultado = controller.registrarEstudiante(nuevoEstudiante);
        
        if (resultado) {
            System.out.println("¡Estudiante registrado exitosamente!");
        } else {
            System.out.println("Error al registrar el estudiante");
        }
    }
}