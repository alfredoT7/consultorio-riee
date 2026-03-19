# Proyecto

## Arquitectura Hexagonal (Ports and Adapters)

Este proyecto usa arquitectura hexagonal para separar la logica de negocio de los detalles tecnicos (HTTP, base de datos, mensajeria, servicios externos).

## Principios

- El `domain` no depende de frameworks.
- `application` orquesta casos de uso.
- `infrastructure` conecta con el mundo exterior.
- Direccion de dependencias:
  `infrastructure -> application -> domain`.

## Estructura recomendada

```text
src/main/java/com/tuempresa/tuapp/
  <modulo>/
    domain/
      entity/        # Entidades y objetos de valor
      repository/    # Puertos de salida (interfaces)
      service/       # Reglas de negocio puras
      exception/     # Excepciones de dominio

    application/
      usecases/      # Contratos de casos de uso (puertos de entrada)
      service/       # Implementacion de casos de uso
      dto/           # Request/Response de aplicacion

    infrastructure/
      rest/          # Controllers (adaptadores de entrada)
      ports/
        adapter/     # Implementaciones de puertos de salida
        persistence/ # Repositorios JPA/SQL/NoSQL
      exception/     # Handlers y mapeo de errores
```

## Flujo de una peticion

1. Un cliente llama al endpoint (`infrastructure/rest`).
2. El controller invoca un `UseCase` (`application/usecases`).
3. El servicio de aplicacion ejecuta reglas y coordinacion.
4. Si necesita guardar/leer datos, usa un puerto de `domain/repository`.
5. Un adaptador en `infrastructure/ports` implementa ese puerto.
6. Se devuelve un DTO de respuesta.

## Ejemplo generico

Caso de uso: crear una orden (`CreateOrder`)

- Entrada: `OrderController` recibe `CreateOrderRequest`.
- Aplicacion: `OrderService` ejecuta `CreateOrderUseCase`.
- Dominio: `OrderDomainService` valida reglas (stock, limites, etc.).
- Puerto: `OrderRepository` define operaciones necesarias.
- Adaptador: `JpaOrderRepositoryAdapter` implementa `OrderRepository`.
- Salida: `CreateOrderResponse`.

## Reglas para mantener la arquitectura limpia

- No usar anotaciones de framework en clases de `domain`.
- No exponer entidades de dominio directamente por API; usar DTOs.
- Toda integracion externa debe vivir en `infrastructure`.
- Cada nuevo modulo debe seguir el mismo patron (`domain/application/infrastructure`).
- Los casos de uso no deben depender de controllers ni repositorios concretos.

## Checklist rapido para nuevos modulos

1. Crear `domain/entity`, `domain/repository`, `domain/service`.
2. Definir casos de uso en `application/usecases`.
3. Implementar casos de uso en `application/service`.
4. Exponer entrada en `infrastructure/rest`.
5. Implementar repositorios/adaptadores en `infrastructure/ports`.
6. Agregar pruebas por capa (dominio, aplicacion, infraestructura).
