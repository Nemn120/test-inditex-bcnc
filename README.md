## Test Inditex - BCNC Group

> Aplicación para gestionar precios de productos basados en fechas específicas.
> La implementación ha sido realizada utilizando arquitectura hexagonal basado en casos de uso.

### Estado del código


### Tecnologías necesarias

`Java 17` `Spring Boot` `Spring Data` `MapStruct` `Gradle` `JUnit5` `Mockito` `Sonarcloud`

### Instalación del proyecto

1. Clonar el repositorio en tu equipo, **mediante consola**:

```sh
> cd <folder path>
> git clone https://github.com/Nemn120/test-inditex-bcnc
```

2. Importar el proyecto mediante **IntelliJ IDEA**
    1. **Import Project**, y seleccionar la carpeta del proyecto.
    1. Marcar **Create Project from external model**, elegir **Maven**.
    1. **Next** … **Finish**.

### Funcionalidades

1. **Consulta de Precios**: Permite obtener el precio de un producto en una fecha específica a partir de brandId,
   productId.
2. **Validaciones**:
    - Se valida que la fecha de inicio y la fecha de fin de los precios no se solapen.
    - Se verifica que los precios estén dentro del rango de fechas de los productos.
3. **Manejo de Prioridades**:
    - La aplicación devuelve el precio con la mayor prioridad.
    - Si hay más de un precio con la misma prioridad, se lanza un error.

### Ejecución

Para levantar el proyecto es necesario Java17 y Gradle 7.3 como mínimo. Puede ejecutarlo directamente desde el IDE. 

```
./gradlew bootRun
```

#### Request:

```
curl --location 'http://localhost:8080/api/v1/prices/brand/1/product/35455?applicationDate=2020-06-14T10:00:00.000-05:00'
```

#### Response:

```
HTTP/1.1 200 OK
Content-Type: application/json

{
    "productId": 35455,
    "brandId": 1,
    "priceList": 1,
    "startDate": "2020-06-14T00:00:00",
    "endDate": "2020-12-31T23:59:59",
    "finalPrice": "35.50 €"
}
```
### Pruebas
- Las cinco pruebas del enunciado están desarrolladas en la clase de test GetPriceProductByDateControllerTest. 
- Adicional a ello tambien se realizó las pruebas en las distintas capas de la arquitectura.
- Para los servicios rest se utilizó WebTestClient. La configuración está centralizada en la anotación @RestTestConfig 
- Para los servicios de la capa de aplicación se utilizó Mockito con Junit.

Ejecutar los test:
```
./gradlew test
```

### Estructura del Proyecto

```bash
 \---com.achavez.bcnc.inditextest.product
		    +---application
                    |   +---port
                    |   |   +---in
                    |   |   |       GetPriceProductByDateUseCase.java
                    |   |   |
                    |   |   \---out
                    |   |           PriceRepository.java
                    |   |
                    |   \---service
                    |           GetPriceProductByDateService.java
                    |
                    +---domain
                    |       Currency.java
                    |       DomainErrorMessage.java
                    |       DomainValidationException.java
                    |       Price.java
                    |       PriceNotFoundException.java
                    |
                    \---infrastructure
                        +---adapter
                        |   +---in
                        |   |   \---rest
                        |   |       +---controller
                        |   |       |       GetPriceProductByDateController.java
                        |   |       |
                        |   |       +---dto
                        |   |       |       ProductPriceRateResponseDTO.java
                        |   |       |
                        |   |       +---error
                        |   |       |       ErrorResponse.java
                        |   |       |       RestExceptionHandler.java
                        |   |       |
                        |   |       \---mapper
                        |   |               PriceRestMapper.java
                        |   |
                        |   \---out
                        |       \---persistence
                        |           \---db
                        |               \---jpa
                        |                   +---entity
                        |                   |       PriceJpaEntity.java
                        |                   |
                        |                   +---mapper
                        |                   |       PriceJpaMapper.java
                        |                   |
                        |                   \---repository
                        |                           PriceJpaRepository.java
                        |                           PriceSpringDataRepository.java
                        |
                        \---config
                                ApplicationServiceConfig.java
                                SpringBootRun.java
                                SwaggerConfig.java

```

## Contacto

[![image](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/fernando-chavez-chavez/) 
[![image](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/Nemn120/)
