# Foroalura
Este proyecto consiste en una API RESTful para gestionar tópicos de discusión relacionados con cursos. Permite a los usuarios crear, actualizar y eliminar tópicos, además de autenticar usuarios mediante JWT. Esto como parte del último challenge por parte de Alura.
# Requisitos
- Java 17 o superior
- Spring Boot 2.7.x
- Maven
- PostgreSQL (u otra base de datos compatible)
# Tecnologías utilizadas
- Spring Boot: Framework para la construcción de aplicaciones Java.
- Spring Security: Manejo de autenticación y autorización.
- JWT (JSON Web Token): Generación y validación de tokens para la autenticación.
- Spring Data JPA: Interacción con la base de datos.
- Swagger UI: Documentación de la API.
- Lombok: Anotaciones para simplificar el código.
# Instalación
1. Clonar el repositorio
```
git clone https://github.com/MoisesMGuillen/Foroalura.git
```
2. Configurar las variables de entorno
    - **api.security.secret**: La clave secreta utilizada para firmar los tokens JWT
3. Configura la base de datos
    - Asegúrate de tener una base de datos PostgreSQL corriendo, o cualquier otra de su elección, y configura los parámetros de conexión en el archivo **application.properties**
4. Ejecutar el proyecto
   
5. Acceder a la documentación de la API en Swagger
    - Abre tu navegador y ve a: **http://localhost:8080/swagger-ui.html**
# Endpoints
### Autenticación
***POST/LOGIN***

Permite autenticar a un usuario y obtener un token JWT

**Body**
```
{
  "correo": "usuario@ejemplo.com",
  "contrasena": "contrasenaSegura"
}
```
***Respuesta***
```
{
  "jwTtoken": "tokenGenerado"
}
```
### Tópicos
***POST /topicos***

Crea un nuevo tópico.

**Headers:**
```
Authorization: Bearer <token>
```
**Body:**
```
{
  "titulo": "Título del tópico",
  "mensaje": "Contenido del tópico",
  "nombreCurso": "Nombre del curso"
}
```
**Respuesta:**
```
{
  "id": 1,
  "titulo": "Título del tópico",
  "mensaje": "Contenido del tópico",
  "fechaCreacion": "2025-01-17",
  "status": "Sin resolver",
  "autor": "Nombre del autor",
  "curso": "Nombre del curso"
}
```
***GET /topicos***

Obtiene todos los tópicos paginados.

**Respuesta:**
```
[
  {
    "id": 1,
    "titulo": "Título del tópico",
    "mensaje": "Contenido del tópico",
    "fechaCreacion": "2025-01-17",
    "status": "Sin resolver",
    "autor": "Nombre del autor",
    "curso": "Nombre del curso"
  },
  ...
]
```

***GET /topicos/{id}***

Obtiene los detalles de un tópico por su ID.

**Respuesta:**
```
{
  "id": 1,
  "titulo": "Título del tópico",
  "mensaje": "Contenido del tópico",
  "fechaCreacion": "2025-01-17",
  "status": "Sin resolver",
  "autor": "Nombre del autor",
  "curso": "Nombre del curso"
}
```
***PUT /topicos/actualizar***

Actualiza un tópico existente.

**Headers:**
```
Authorization: Bearer <token>
```
**Body:**
```
{
  "idPost": 1,
  "titulo": "Nuevo título",
  "mensaje": "Nuevo mensaje",
  "status": true
}
```
***DELETE /topicos/{id}***

Elimina un tópico por su ID.

**Headers:**
```
Authorization: Bearer <token>
```

# Manejo de Errores
- 404 Not Found: Si no se encuentra el recurso solicitado (e.g., un tópico o usuario).
- 400 Bad Request: Si el cuerpo de la solicitud no es válido (e.g., parámetros faltantes).
- 403 Forbidden: Si un usuario intenta realizar una acción sin los permisos adecuados.
- 500 Internal Server Error: En caso de un error en el servidor.

# Seguridad
La API utiliza autenticación mediante JWT. Cada solicitud a endpoints protegidos debe incluir el token en el encabezado Authorization.

**Ejemplo del encabezado de autenticación:**
```
Authorization: Bearer <token>
```
