# Inventario API — GA7-220501096-AA5-EV03, EV01 y EV04

Servicios web REST desarrollados en **Java 17 + Spring Boot 3** como evidencias
del proyecto *"Diseño y desarrollo de servicios web"*. El proyecto cubre tres
evidencias de la misma actividad de aprendizaje (GA7-220501096-AA5):

- **EV03**: gestión de inventario/productos (CRUD completo).
- **EV01**: servicios de registro e inicio de sesión de usuarios.
- **EV04**: pruebas con Postman y documentación de la API.

## Cómo se cubre cada criterio de la evaluación — EV04 (Pruebas y documentación)

| # | Criterio | Cómo se cumple en este proyecto |
|---|----------|----------------------------------|
| 1 | Crea servicios web para métodos reutilizables | Mismos servicios de EV03 y EV01, ahora con documentación Swagger/OpenAPI (`@Operation`, `@ApiResponse`) que describe cada método reutilizable. |
| 2 | Realiza el test de las API's utilizando Postman | Colección `postman/Inventario-API.postman_collection.json`, con casos exitosos y de error para cada endpoint. |
| 3 | Realiza la documentación de las API's | Documento `Documentacion-API.docx` con la descripción de cada endpoint, modelos, ejemplos de petición/respuesta y validaciones; además, documentación interactiva en vivo vía Swagger UI. |

### Documentación interactiva (Swagger UI)

Con el proyecto corriendo, abre en el navegador:
```
http://localhost:8080/swagger-ui.html
```
Ahí puedes ver y probar cada endpoint directamente desde el navegador.

### Pruebas con Postman

1. Abre Postman → **Import** → selecciona `postman/Inventario-API.postman_collection.json`.
2. La colección trae dos carpetas: **Productos** y **Autenticacion**, cada una con peticiones de casos exitosos y de casos que deben fallar a propósito (para comprobar las validaciones).
3. Asegúrate de tener el proyecto corriendo en `http://localhost:8080` antes de probar.

### Documentación formal

El archivo `Documentacion-API.docx` (en la raíz del proyecto) describe en detalle cada endpoint, el modelo de datos, ejemplos de petición/respuesta, las reglas de validación y cómo usar la colección de Postman.

## Cómo se cubre cada criterio de la evaluación — EV03 (Inventario)

| # | Criterio | Cómo se cumple en este proyecto |
|---|----------|----------------------------------|
| 1 | Crea servicios web para métodos reutilizables | Capa `service` (`ProductoService` / `ProductoServiceImpl`): métodos como `crear`, `actualizar`, `ajustarStock`, `calcularValorTotalInventario`, `buscarPorCategoria`, etc., reutilizables desde cualquier controlador u otro módulo. |
| 2 | Realiza API REST según necesidades del proyecto | `ProductoController`: endpoints CRUD + búsquedas + reportes bajo `/api/productos`, usando verbos HTTP correctos (GET, POST, PUT, PATCH, DELETE) y códigos de estado adecuados. |
| 3 | Realiza las validaciones correctamente | Bean Validation (`@NotBlank`, `@DecimalMin`, `@Min`, `@Pattern`, etc.) en `Producto`, activadas con `@Valid` en el controlador, más reglas de negocio (SKU duplicado, stock negativo) y un `GlobalExceptionHandler` que devuelve errores estructurados (JSON) con código HTTP correcto. |
| 4 | Utiliza herramientas de versionamiento | Repositorio Git inicializado con commits incrementales por capa (estructura → repositorio/errores → servicio → controlador → documentación). Ver `git log`. |

## Cómo se cubre cada criterio de la evaluación — EV01 (Registro y Login)

| # | Criterio | Cómo se cumple en este proyecto |
|---|----------|----------------------------------|
| 1 | Servicio para registro | `AuthService.registrar()` / endpoint `POST /api/auth/registro`: crea un usuario nuevo, validando que el correo y el nombre de usuario no existan ya, y guarda la contraseña **encriptada** (nunca en texto plano). |
| 2 | Servicio para inicio de sesión | `AuthService.iniciarSesion()` / endpoint `POST /api/auth/login`: valida las credenciales comparando el hash de la contraseña, sin revelar si el error fue el correo o la contraseña (buena práctica de seguridad). |
| 3 | Validaciones correctas | `RegistroRequest` y `LoginRequest` usan Bean Validation (`@NotBlank`, `@Email`, `@Size`, `@Pattern` para exigir letras+números en la contraseña). Los errores de negocio (correo/usuario duplicado, credenciales inválidas) se manejan con el mismo `GlobalExceptionHandler`. |
| 4 | Herramientas de versionamiento | Mismos commits incrementales de Git, con el prefijo `feat(auth):` para diferenciarlos de los de inventario. Ver `git log`. |

### Endpoints de autenticación

| Método | Ruta | Descripción |
|--------|------|-------------|
| POST | `/api/auth/registro` | Crea una cuenta nueva |
| POST | `/api/auth/login` | Valida credenciales e inicia sesión |

#### Ejemplo de registro (POST /api/auth/registro)

```json
{
  "nombreUsuario": "roberto123",
  "correo": "roberto@correo.com",
  "contrasena": "Clave2026segura"
}
```

Respuesta (201 Created):
```json
{
  "id": 1,
  "nombreUsuario": "roberto123",
  "correo": "roberto@correo.com",
  "mensaje": "Usuario registrado exitosamente"
}
```

#### Ejemplo de login (POST /api/auth/login)

```json
{
  "correo": "roberto@correo.com",
  "contrasena": "Clave2026segura"
}
```

Respuesta si las credenciales son correctas (200 OK):
```json
{
  "id": 1,
  "nombreUsuario": "roberto123",
  "correo": "roberto@correo.com",
  "mensaje": "Inicio de sesion exitoso"
}
```

Respuesta si son incorrectas (409 Conflict):
```json
{
  "estado": 409,
  "error": "Regla de negocio violada",
  "mensajes": ["Correo o contrasena incorrectos"]
}
```

## Estructura del proyecto

```
inventario-api/
├── pom.xml
├── src/main/java/com/sena/inventario/
│   ├── InventarioApiApplication.java
│   ├── model/Producto.java              # Entidad + validaciones
│   ├── repository/ProductoRepository.java
│   ├── service/ProductoService.java      # Contrato de métodos reutilizables
│   ├── service/impl/ProductoServiceImpl.java
│   ├── controller/ProductoController.java # API REST
│   ├── exception/                        # Excepciones + manejador global
│   ├── dto/ErrorResponse.java
│   └── config/DataLoader.java            # Datos de ejemplo al iniciar
└── src/main/resources/application.properties
```

## Requisitos

- JDK 17+
- Maven 3.8+ (o usar el wrapper si lo agregas: `mvn -N io.takari:maven:wrapper`)

## Cómo ejecutar

```bash
cd inventario-api
mvn spring-boot:run
```

La API queda disponible en `http://localhost:8080`.
Consola de la base de datos H2 (en memoria): `http://localhost:8080/h2-console`
(JDBC URL: `jdbc:h2:mem:inventariodb`, usuario `sa`, sin contraseña).

## Endpoints principales

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/productos` | Lista todos los productos |
| GET | `/api/productos/{id}` | Obtiene un producto por id |
| GET | `/api/productos/categoria/{categoria}` | Filtra por categoría |
| GET | `/api/productos/buscar?nombre=xxx` | Búsqueda por nombre |
| GET | `/api/productos/stock-bajo?umbral=5` | Productos con stock igual o menor al umbral |
| GET | `/api/productos/valor-total` | Valor monetario total del inventario |
| POST | `/api/productos` | Crea un producto |
| PUT | `/api/productos/{id}` | Actualiza un producto |
| PATCH | `/api/productos/{id}/stock?cantidad=10` | Ajusta stock (positivo suma, negativo resta) |
| DELETE | `/api/productos/{id}` | Elimina un producto |

### Ejemplo de creación (POST /api/productos)

```json
{
  "nombre": "Silla ergonomica",
  "descripcion": "Silla de oficina con soporte lumbar",
  "categoria": "Mobiliario",
  "precio": 459900.00,
  "cantidadStock": 12,
  "sku": "SIL-004"
}
```

### Ejemplo de respuesta de error de validación (400)

```json
{
  "fecha": "2026-09-02T10:15:30",
  "estado": 400,
  "error": "Error de validacion",
  "mensajes": [
    "nombre: El nombre del producto es obligatorio",
    "precio: El precio debe ser mayor que 0"
  ],
  "ruta": "/api/productos"
}
```

## Control de versiones (Git)

El repositorio ya está inicializado localmente con un historial de commits
por etapas de desarrollo. Para subirlo a GitHub/GitLab:

```bash
git remote add origin https://github.com/tu-usuario/inventario-api.git
git branch -M main
git push -u origin main
```

Buenas prácticas aplicadas:
- Commits pequeños y descriptivos (`feat:`, `fix:`, `docs:`).
- `.gitignore` para excluir `target/` y archivos de IDE.
- Un commit por capa/funcionalidad, permitiendo rastrear la evolución del
  proyecto (`git log --oneline`).

Para continuar el trabajo, se recomienda crear una rama por funcionalidad:

```bash
git checkout -b feature/nueva-funcionalidad
# ... cambios ...
git add .
git commit -m "feat: descripcion del cambio"
git push origin feature/nueva-funcionalidad
```

## Pruebas rápidas con curl

```bash
# Listar productos
curl http://localhost:8080/api/productos

# Crear producto
curl -X POST http://localhost:8080/api/productos \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Silla ergonomica","descripcion":"Silla con soporte lumbar","categoria":"Mobiliario","precio":459900.00,"cantidadStock":12,"sku":"SIL-004"}'

# Probar validacion (falla porque el precio es negativo)
curl -X POST http://localhost:8080/api/productos \
  -H "Content-Type: application/json" \
  -d '{"nombre":"AB","categoria":"","precio":-10,"cantidadStock":-5,"sku":"x"}'
```
