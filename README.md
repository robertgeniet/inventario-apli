# Inventario API — GA7-220501096-AA5-EV03

Servicios web REST para la **gestión de inventario/productos**, desarrollados en
**Java 17 + Spring Boot 3** como evidencia del proyecto *"Diseño y desarrollo de
servicios web"*.

## Cómo se cubre cada criterio de la evaluación

| # | Criterio | Cómo se cumple en este proyecto |
|---|----------|----------------------------------|
| 1 | Crea servicios web para métodos reutilizables | Capa `service` (`ProductoService` / `ProductoServiceImpl`): métodos como `crear`, `actualizar`, `ajustarStock`, `calcularValorTotalInventario`, `buscarPorCategoria`, etc., reutilizables desde cualquier controlador u otro módulo. |
| 2 | Realiza API REST según necesidades del proyecto | `ProductoController`: endpoints CRUD + búsquedas + reportes bajo `/api/productos`, usando verbos HTTP correctos (GET, POST, PUT, PATCH, DELETE) y códigos de estado adecuados. |
| 3 | Realiza las validaciones correctamente | Bean Validation (`@NotBlank`, `@DecimalMin`, `@Min`, `@Pattern`, etc.) en `Producto`, activadas con `@Valid` en el controlador, más reglas de negocio (SKU duplicado, stock negativo) y un `GlobalExceptionHandler` que devuelve errores estructurados (JSON) con código HTTP correcto. |
| 4 | Utiliza herramientas de versionamiento | Repositorio Git inicializado con commits incrementales por capa (estructura → repositorio/errores → servicio → controlador → documentación). Ver `git log`. |

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
