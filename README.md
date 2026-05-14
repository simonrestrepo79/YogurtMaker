 Yogurt Maker API
API REST desarrollada con Spring Boot para la gestión de producción de yogurt artesanal. Permite administrar recetas, lotes de producción y productos terminados.


Tecnologías:

Java 21

Spring Boot 3.x

Spring Data JPA

H2 

Maven

SpringDoc



Configuración del Proyecto:

bashgit clone https://github.com/simonrestrepo79/YogurtMaker.git.

cd yogurt-maker.

./mvnw spring-boot:run.

La aplicación estará disponible en: http://localhost:8080.

taller 1 – Documentación con SpringDoc OpenAPI


Fase A: Configuración de Dependencias

1. Identificar la dependencia correcta para Spring Boot 3.x.
   
2. Añadir la dependencia en pom.xml
   
xml<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>${springdoc.version}</version>
</dependency>

<img width="782" height="117" alt="image" src="https://github.com/user-attachments/assets/934a0eee-2846-4d8e-bbf3-d6ab50d3e6ce" />


Nota: ponemos este codigo en la version para que busque la version compatible de Springdoc con la App.

3. compilamos el proyecto.
bash./mvnw clean install
./mvnw spring-boot:run
Verificación 
Tras arrancar la aplicación, accede a Swagger UI en:
http://localhost:8080/swagger-ui/index.html
en este link te deberia abrir toda la API en el swagger

<img width="1900" height="967" alt="image" src="https://github.com/user-attachments/assets/d3251a09-e93a-43e4-9269-0fecdb2b749c" />
<img width="1898" height="562" alt="image" src="https://github.com/user-attachments/assets/77a71b48-9beb-412a-ba61-f50a690564f3" />
<img width="1877" height="750" alt="image" src="https://github.com/user-attachments/assets/43bf1c2c-bf93-4184-9fd4-c7c76cad30ea" />




Fase B: Enriquecimiento de la Documentación 
No es suficiente con que aparezcan los endpoints; la documentación debe ser descriptiva. Debes aplicar las siguientes anotaciones en tus Controllers:

1. @tags: Agrupa los endpoints 

@Tag(name = "Lotes de Yogurt", description = "Endpoints para gestionar el ciclo de vida de los lotes de yogurt")
@RestController
@RequestMapping("/api/batches")
public class YogurtBatchController { ... }

@Tag(name = "Recetas", description = "Endpoints para gestionar las recetas de yogurt")
@RestController
@RequestMapping("/api/recipes")
public class RecipeController { ... }

@Tag(name = "Monitoreo", description = "Endpoints para monitorear lotes y temperaturas del yogurt")
@RestController
@RequestMapping("/api/monitoring")
public class MonitoringController { ... }


2. @Operation: Describe cada método

@PostMapping("/{batchId}/incubation")
@Operation(
    summary = "Iniciar incubación",
    description = "Cambia el estado del lote a fase de incubación"
)
public ResponseEntity<YogurtBatch> startIncubation(@PathVariable Long batchId) { ... }

@GetMapping("/dashboard")
@Operation(
    summary = "Dashboard general",
    description = "Muestra un resumen de todos los lotes agrupados por estado"
)
public ResponseEntity<MonitoringDTO.Dashboard> getDashboard() { ... }


3. @ApiResponse: Se documentan al menos dos escenarios por endpoint. uno exitoso (200) y uno de error (400).

Iniciar lote
@PostMapping
@Operation(summary = "Iniciar lote", description = "Crea e inicia un nuevo lote de yogurt basado en una receta")
@ApiResponses({
    @ApiResponse(responseCode = "201", description = "Lote creado correctamente"),
    @ApiResponse(responseCode = "400", description = "Datos del lote inválidos")
})
public ResponseEntity<YogurtBatch> startNewBatch(@RequestBody StartBatchRequest request) { ... }

Buscar receta
@GetMapping("/search")
@Operation(summary = "Buscar recetas", description = "Busca recetas que coincidan con una palabra clave")
@ApiResponses({
    @ApiResponse(responseCode = "200", description = "Búsqueda realizada correctamente"),
    @ApiResponse(responseCode = "400", description = "Parámetro de búsqueda inválido")
})
public ResponseEntity<List<Recipe>> searchRecipes(@RequestParam String keyword) { ... }

Historial de temperatura
@GetMapping("/batches/{batchId}/temperature-logs")
@Operation(summary = "Historial de temperatura", description = "Devuelve registros de temperatura de un lote, con filtro opcional por rango de fechas")
@ApiResponses({
    @ApiResponse(responseCode = "200", description = "Registros obtenidos correctamente"),
    @ApiResponse(responseCode = "400", description = "Parámetros inválidos")
})
public ResponseEntity<List<TemperatureLog>> getTemperatureLogs(...) { ... }


4. @Schema: documentan cada atributo con su descripción en Swagger.

@Entity
@Table(name = "ingredients")
@Schema(description = "Ingrediente usado en una receta")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del ingrediente")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Nombre del ingrediente")
    private String name;

    @Schema(description = "Cantidad del ingrediente")
    private Double quantity;

    @Schema(description = "Unidad de medida (kg, g, ml, cucharadas, etc.)")
    private String unit;

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    @Schema(description = "Receta a la que pertenece")
    private Recipe recipe;

    @Schema(description = "Notas adicionales")
    private String notes;

    @Column(nullable = false)
    @Schema(description = "Indica si el ingrediente es opcional")
    private Boolean optional;
}


taller 2 – Diagrama de Clases UML

Diseño del Diagrama: diseñado con Mermaid.js siguiendo estándares UML:

Separé la logica de esta manera ya que cada parte tiene un uso diferente y asi el sistema queda más claro y fácil
de entender.
En el diagrama, los Controllers solo reciben las peticiones y devuelven respuestas
los servicios se usan para crear recetas
los repositorios toman los datos para guardar o leer entidades 
los DTOs sirven para trasladar datos entre la API y la parte interna del proyecto.

Los simobolos de + se usa para que sea visible mientras que – es para que sea privado.

1 o -- * significa que debe llevar 0 o mas.

Las flechas → significan que una depende de la otra mientras que <l.. significa que implementa a la
otra.


<img width="1222" height="621" alt="image" src="https://github.com/user-attachments/assets/0c48ef11-02b0-48d1-8228-a4af2dc6159a" />


classDiagram
    class RecipeController <<RestController>> {
        +createRecipe()
        +getRecipe()
    }

    class YogurtBatchController <<RestController>> {
        +startNewBatch()
        +startHeating()
    }

    class MonitoringController <<RestController>> {
        +getActiveBatches()
    }

    class RecipeService <<Service>> {
        -recipeRepository
        +createRecipe()
    }

    class YogurtMakingService <<Service>> {
        -batchRepository
        -recipeRepository
        +startNewBatch()
    }

    class TemperatureControlService <<Service>> {
        -temperatureLogRepository
        +getCurrentTemperature()
    }

    class RecipeRepository <<interface>>
    class YogurtBatchRepository <<interface>>
    class TemperatureLogRepository <<interface>>

    class Recipe <<Entity>> {
        -id
        -name
        -active
    }

    class Ingredient <<Entity>> {
        -id
        -name
        -quantity
    }

    class YogurtBatch <<Entity>> {
        -id
        -batchCode
        -status
    }

    class TemperatureLog <<Entity>> {
        -id
        -temperature
        -recordedAt
    }

    class RecipeDTO <<DTO>>
    class StartBatchRequest <<DTO>>

    RecipeController --> RecipeService
    YogurtBatchController --> YogurtMakingService
    MonitoringController --> TemperatureControlService

    RecipeService --> RecipeRepository
    YogurtMakingService --> RecipeRepository
    YogurtMakingService --> YogurtBatchRepository
    TemperatureControlService --> TemperatureLogRepository

    RecipeRepository <|.. JpaRepository
    YogurtBatchRepository <|.. JpaRepository
    TemperatureLogRepository <|.. JpaRepository

    Recipe "1" o-- "*" Ingredient
    Recipe "1" o-- "*" YogurtBatch
    YogurtBatch "1" o-- "*" TemperatureLog

    
Estructura del Proyecto: 


<img width="772" height="602" alt="image" src="https://github.com/user-attachments/assets/419f0fd2-7589-498c-9824-10d82efa617a" />
<img width="772" height="430" alt="image" src="https://github.com/user-attachments/assets/ca867b84-67c9-480d-903a-87767ece276d" />
<img width="770" height="113" alt="image" src="https://github.com/user-attachments/assets/36a0a5b7-d3c0-461f-b8e0-2be4053872dc" />




URLSwagger http://localhost:8080/swagger-ui/index.html
