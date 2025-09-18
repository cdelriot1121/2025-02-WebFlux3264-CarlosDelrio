# Documentación del Taller JDBC

## Descripcion del Proyecto general

El proyecto implementa un sistema básico de gestión de estudiantes utilizando JDBC para conectarse a una base de datos. La arquitectura sigue un patrón MVC (Modelo-Vista-Controlador) simplificado:

1. **Controlador**: Maneja las solicitudes y valida datos
2. **Servicio**: Contiene la lógica de negocio y operaciones de base de datos
3. **Entidades**: Representan los objetos del dominio
4. **DTO**: Transfiere datos entre capas

## Clases y cada una de sus funcionalidades :D

### 1. Entidades (`entities`)

#### `Estudiantes.java`
- Representa la entidad estudiante con campos como nombre, apellido, correo, edad y estado civil
- Contiene getters, setters y constructores para manipular los datos

#### `EstadoCivil.java`
- Enumeración que define los posibles estados civiles de un estudiante
- Utilizada para estandarizar los valores permitidos

### 2. Controlador (`controller`)

#### EstudianteController.java
- Actúa como intermediario entre la interfaz de usuario y el servicio
- Valida datos antes de enviarlos al servicio
- Funciones principales:
  - `registrarEstudiante`: Valida y registra un nuevo estudiante
  - `consultarTodos`: Obtiene todos los estudiantes
  - `consultarPorEmail`: Busca un estudiante por su correo electrónico
  - `actualizarEstudiante`: Valida y actualiza datos de un estudiante
  - `eliminarEstudiante`: Elimina un estudiante por su ID

### 3. Servicio (`services`)

#### EstudianteService.java
- Contiene la lógica de negocio e interactúa directamente con la base de datos
- Implementa operaciones CRUD usando JDBC
- Funciones principales:
  - `insertarEstudiante`: Crea un nuevo registro en la base de datos
  - `consultarTodos`: Recupera todos los estudiantes
  - `consultarPorEmail`: Busca un estudiante específico por correo
  - `actualizarEstudiante`: Modifica datos de un estudiante existente
  - `eliminarEstudiante`: Elimina un registro de estudiante

### 4. Paquete de DTOs (`dto`)

#### `GeneralResponse.java`
- Clase de respuesta genérica utilizada principalmente por las operaciones actualizar y eliminar
- Encapsula mensajes de éxito o error para devolverlos al usuario

#### `UpdateEstudianteRequest.java`
- Encapsula los datos necesarios para actualizar un estudiante
- Es un registro de Java (record) que contiene nombre, apellido, edad y estado civil
- Permite separar los datos de entrada de la entidad completa

### 5. El paquete Conexión (`connection`)

#### `ClassConection.java`
- Maneja la conexión a la base de datos
- Proporciona un método estático para obtener conexiones

## Sobre los DTOs (Data Transfer Objects)

Los DTOs que implementó José (como `UpdateEstudianteRequest` y `GeneralResponse`) sirven para:

1. **Separar la capa de presentación de la capa de persistencia**: Permiten transferir solo los datos necesarios sin exponer toda la entidad.

2. **Flexibilidad en actualizaciones parciales**: `UpdateEstudianteRequest` permite actualizar solo ciertos campos del estudiante sin necesidad de proporcionar todos los datos.

3. **Respuestas estandarizadas**: `GeneralResponse` proporciona un formato consistente para devolver mensajes al usuario, especialmente útil para operaciones como actualización y eliminación.

4. **Inmutabilidad y seguridad**: Al ser un record de Java, `UpdateEstudianteRequest` es inmutable, lo que previene modificaciones accidentales durante el proceso.

## Flujo de Trabajo que tiene el Proyecto

1. El usuario interactúa con `App.java`
2. Las solicitudes se dirigen al controlador (`EstudianteController`)
3. El controlador valida los datos y llama al servicio apropiado
4. El servicio ejecuta operaciones de base de datos usando JDBC
5. Los resultados se devuelven a través del controlador al usuario
