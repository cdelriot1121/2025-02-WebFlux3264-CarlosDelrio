import connection.ClassConection;
import controller.EstudianteController;
import dto.GeneralResponse;
import dto.UpdateEstudianteRequest;
import entities.EstadoCivil;
import entities.Estudiantes;
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        //ojo siempre comprobar la conexion a la base de datos primero antes de runnear el menu
        try (Connection connection = ClassConection.getConnection()) {
            System.out.println("Conexión exitosa a la base de datos");
            System.out.println("Acerca de la conexión: " + connection.getMetaData().getDatabaseProductName());
        } catch (Exception e) {
            System.out.println("Error para conectar a la base de datos");
            return; 
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
                System.out.println("Por favor, ingrese un numero valido");
                continue;
            }


        // este es un switch que llama las funciones que elija el Usuario en la terminal    
            switch (opcion) {
                case 1:
                    registrarEstudiante(scanner, controller);
                    break;
                case 2:
                    actualizarEstudiante(scanner, controller);
                    break;
                case 3:
                    eliminarEstudiante(scanner, controller);
                    break;
                case 4:
                    consultarTodosLosEstudiantes(controller);
                    break;
                case 5:
                    consultarEstudiantePorEmail(scanner, controller);
                    break;
                case 6:
                    System.out.println("Saliendo del sistema....");
                    running = false;
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
        
        scanner.close();
    }
    

    //operacion para registrar un estudiante
    private static void registrarEstudiante(Scanner scanner, EstudianteController controller) {
        System.out.println("\n=== REGISTRO DE NUEVO ESTUDIANTE ===");
        
        
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        
        String correo = "";
        boolean correoValido = false;
        while (!correoValido) {
            System.out.print("Correo electrónico: ");
            correo = scanner.nextLine();
            
            // la verificacion del correo si ya existe, ya que es un atributo unico
            if (controller.existeCorreo(correo)) {
                System.out.println("Ya existe un estudiante con ese correo. Intente con otro.");
            } else {
                
                if (correo.contains("@") && correo.contains(".")) {
                    correoValido = true;
                } else {
                    System.out.println("Formato de correo invalido. Debe contener '@' y '.'");
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
        
        
        Estudiantes nuevoEstudiante = new Estudiantes();
        nuevoEstudiante.setNombre(nombre);
        nuevoEstudiante.setApellido(apellido);
        nuevoEstudiante.setCorreo(correo);
        nuevoEstudiante.setEdad(edad);
        nuevoEstudiante.setEstadoCivil(estadoCivil);
        
        
        boolean resultado = controller.registrarEstudiante(nuevoEstudiante);
        
        if (resultado) {
            System.out.println("¡Estudiante registrado exitosamente!");
        } else {
            System.out.println("Error al registrar el estudiante");
        }
    }
    

    // Operacion para actualizar los atributos de un estudiante de la lista
    private static void actualizarEstudiante(Scanner scanner, EstudianteController controller) {
        System.out.println("\n=== ACTUALIZAR ESTUDIANTE ===");
        
        
        List<Estudiantes> estudiantes = controller.consultarTodos();
        if (estudiantes.isEmpty()) {
            System.out.println("No hay estudiantes registrados para actualizar.");
            return;
        }
        
        System.out.println("Lista de estudiantes:");
        for (Estudiantes e : estudiantes) {
            System.out.println(e);
        }
        
        
        long id = 0;
        boolean idValido = false;
        while (!idValido) {
            try {
                System.out.print("Ingrese el ID del estudiante a actualizar: ");
                id = Long.parseLong(scanner.nextLine());
                
                if (controller.validarId(id)) {
                    idValido = true;
                } else {
                    System.out.println("El ID ingresado no existe en la base de datos.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un numero valido");
            }
        }
        
        
        System.out.println("Ingrese los nuevos datos del estudiante:");
        
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        
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
                System.out.println("Por favor, ingrese un numero valido");
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
        
        
        UpdateEstudianteRequest updateRequest = new UpdateEstudianteRequest(nombre, apellido, edad, estadoCivil);
        
        
        GeneralResponse response = controller.actualizarEstudiante(updateRequest, id);
        System.out.println(response.getMensaje());
    }
    

    //operacion para eliminar un estudiante
    private static void eliminarEstudiante(Scanner scanner, EstudianteController controller) {
        System.out.println("\n=== ELIMINAR ESTUDIANTE ===");
        
        
        List<Estudiantes> estudiantes = controller.consultarTodos();
        if (estudiantes.isEmpty()) {
            System.out.println("No hay estudiantes registrados para eliminar.");
            return;
        }
        
        System.out.println("Lista de estudiantes:");
        for (Estudiantes e : estudiantes) {
            System.out.println(e);
        }
        
        
        long id = 0;
        boolean idValido = false;
        while (!idValido) {
            try {
                System.out.print("Ingrese el ID del estudiante a eliminar: ");
                id = Long.parseLong(scanner.nextLine());
                
                if (controller.validarId(id)) {
                    idValido = true;
                } else {
                    System.out.println("El ID ingresado no existe en la base de datos.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido");
            }
        }
        
        
        System.out.print("¿Está seguro que desea eliminar este estudiante? (S/N): ");
        String confirmacion = scanner.nextLine().trim().toUpperCase();
        
        if (confirmacion.equals("S")) {
            
            GeneralResponse response = controller.eliminarEstudiante(id);
            System.out.println(response.getMensaje());
        } else {
            System.out.println("Operación cancelada");
        }
    }

    //Operaciones para consultar todos los estudiante y consultar estudiante por email
    
    private static void consultarTodosLosEstudiantes(EstudianteController controller) {
        System.out.println("\n=== CONSULTAR TODOS LOS ESTUDIANTES ===");
        List<Estudiantes> lista = controller.consultarTodos();
        
        if (lista.isEmpty()) {
            System.out.println("No hay estudiantes registrados en el sistema.");
        } else {
            System.out.println("Lista de estudiantes:");
            for (Estudiantes e : lista) {
                System.out.println(e);
            }
        }
    }
    
    private static void consultarEstudiantePorEmail(Scanner scanner, EstudianteController controller) {
        System.out.println("\n=== CONSULTAR ESTUDIANTE POR EMAIL ===");
        System.out.print("Ingrese el correo del estudiante: ");
        String email = scanner.nextLine();

        Estudiantes estudiante = controller.consultarPorEmail(email);

        if (estudiante != null) {
            System.out.println("Estudiante encontrado:");
            System.out.println(estudiante);
        } else {
            System.out.println("No se encontró ningún estudiante con ese correo.");
        }
    }
}