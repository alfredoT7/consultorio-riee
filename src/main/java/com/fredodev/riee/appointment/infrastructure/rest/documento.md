# Modulo REST de Citas (`appointment/infrastructure/rest`)

Este documento explica que hace cada controlador del paquete, que endpoints expone, que parametros requiere y que retorna.

## Convenciones generales

- Base de respuestas: `ApiResponse<T>`
- URL base local completa: `http://localhost:8080/api/v1/riee`
- Estructura de exito:
  - `success`: `true`
  - `status`: codigo HTTP
  - `message`: mensaje descriptivo
  - `data`: payload del endpoint
  - `timestamp`: fecha/hora de respuesta
- Estructura de error:
  - `success`: `false`
  - `status`: codigo HTTP de error
  - `message`: mensaje general de error
  - `errors`: lista con detalle(s) del error
  - `data`: `null`
  - `timestamp`: fecha/hora de respuesta
- CORS: todos los controladores usan `@CrossOrigin(origins = "*")`

---

## 1) `AppointmentController`

**Archivo:** `AppointmentController.java`  
**Base path:** `/appointments`  
**Objetivo:** CRUD de citas + consultas para calendario/filtros.

### Endpoints

### `POST /appointments`
- **Uso:** crear una cita.
- **Body (`AppointmentRequest`):**
  - `fechaCita` (Date, requerido)
  - `horaCita` (Time, requerido)
  - `motivoCita` (String)
  - `estadoCita` (String)
  - `observacionesCita` (String)
  - `duracionEstimada` (Long, minutos)
  - `patientId` (Long, requerido)
  - `appointmentStatusId` (Long, requerido)
- **Respuesta:** `ApiResponse<AppointmentResponse>`
- **HTTP:** `201 CREATED`

### `GET /appointments/{id}`
- **Uso:** obtener cita por id.
- **Path param:** `id` (Long)
- **Respuesta:** `ApiResponse<AppointmentResponse>`
- **HTTP:** `200 OK`

### `GET /appointments`
- **Uso:** listar todas las citas.
- **Respuesta:** `ApiResponse<List<AppointmentResponse>>`
- **HTTP:** `200 OK`

### `GET /appointments/search`
- **Uso:** filtrar citas.
- **Query params (opcionales):**
  - `from` (`yyyy-MM-dd`)
  - `to` (`yyyy-MM-dd`)
  - `patientCi` (Integer)
  - `patientId` (Long)
  - `appointmentStatusId` (Long)
- **Respuesta:** `ApiResponse<List<AppointmentResponse>>`
- **HTTP:** `200 OK`

### `GET /appointments/calendar`
- **Uso:** vista ligera para pintar calendario.
- **Query params (opcionales):** iguales a `/search`
- **Respuesta:** `ApiResponse<List<AppointmentCalendarResponse>>`
- **HTTP:** `200 OK`

### `GET /appointments/day?date=yyyy-MM-dd`
- **Uso:** agenda de un dia.
- **Query param (requerido):** `date` (`yyyy-MM-dd`)
- **Respuesta:** `ApiResponse<List<AppointmentResponse>>`
- **HTTP:** `200 OK`

### `PUT /appointments/{id}`
- **Uso:** actualizar cita completa.
- **Path param:** `id` (Long)
- **Body:** `AppointmentRequest`
- **Respuesta:** `ApiResponse<AppointmentResponse>`
- **HTTP:** `200 OK`

### `DELETE /appointments/{id}`
- **Uso:** eliminar cita.
- **Path param:** `id` (Long)
- **Respuesta:** `ApiResponse<Void>`
- **HTTP:** `200 OK`

### `GET /appointments/patient/{ciPaciente}`
- **Uso:** listar citas por CI de paciente.
- **Path param:** `ciPaciente` (int)
- **Respuesta:** `ApiResponse<List<AppointmentResponse>>`
- **HTTP:** `200 OK`

---

## 2) `AppointmentWorkflowController`

**Archivo:** `AppointmentWorkflowController.java`  
**Base path:** `/appointments/{id}`  
**Objetivo:** operaciones de flujo/estado de una cita (acciones de negocio).

### Endpoints

### `PATCH /appointments/{id}/confirm`
- **Uso:** cambia estado a `CONFIRMADA`.
- **Body opcional:** `AppointmentStatusActionRequest` (`observacionesCita`)
- **Respuesta:** `ApiResponse<AppointmentResponse>`

### `PATCH /appointments/{id}/check-in`
- **Uso:** cambia estado a `EN_ESPERA`.
- **Body opcional:** `AppointmentStatusActionRequest`
- **Respuesta:** `ApiResponse<AppointmentResponse>`

### `PATCH /appointments/{id}/start`
- **Uso:** cambia estado a `EN_CURSO`.
- **Body opcional:** `AppointmentStatusActionRequest`
- **Respuesta:** `ApiResponse<AppointmentResponse>`

### `PATCH /appointments/{id}/complete`
- **Uso:** cambia estado a `COMPLETADA`.
- **Body opcional:** `AppointmentStatusActionRequest`
- **Respuesta:** `ApiResponse<AppointmentResponse>`

### `PATCH /appointments/{id}/cancel`
- **Uso:** cambia estado a `CANCELADA`.
- **Body opcional:** `AppointmentStatusActionRequest`
- **Respuesta:** `ApiResponse<AppointmentResponse>`

### `PATCH /appointments/{id}/no-show`
- **Uso:** cambia estado a `NO_ASISTIO`.
- **Body opcional:** `AppointmentStatusActionRequest`
- **Respuesta:** `ApiResponse<AppointmentResponse>`

### `PATCH /appointments/{id}/reschedule`
- **Uso:** reprogramar cita (fecha/hora) y marcar estado `REPROGRAMADA`.
- **Body requerido (`AppointmentRescheduleRequest`):**
  - `fechaCita` (Date)
  - `horaCita` (Time)
  - `observacionesCita` (String)
- **Respuesta:** `ApiResponse<AppointmentResponse>`

### `PATCH /appointments/{id}/status`
- **Uso:** cambio de estado generico por id de estado.
- **Body requerido (`AppointmentStatusActionRequest`):**
  - `appointmentStatusId` (Long)
  - `observacionesCita` (String)
- **Respuesta:** `ApiResponse<AppointmentResponse>`

**HTTP para todos los endpoints del workflow:** `200 OK`

---

## 3) `AppointmentAvailabilityController`

**Archivo:** `AppointmentAvailabilityController.java`  
**Base path:** `/appointments/availability`  
**Objetivo:** disponibilidad y validacion de conflictos de horario.

### Endpoints

### `GET /appointments/availability/slots`
- **Uso:** obtener bloques disponibles de agenda.
- **Query params:**
  - `date` (`yyyy-MM-dd`, requerido)
  - `durationMinutes` (Integer, opcional, default interno: 30)
  - `slotMinutes` (Integer, opcional, default interno: 30)
- **Respuesta:** `ApiResponse<List<AvailabilitySlotResponse>>`
  - cada slot incluye `startTime` y `endTime` (`HH:mm[:ss]`)
- **HTTP:** `200 OK`

### `GET /appointments/availability/conflict`
- **Uso:** validar si un horario entra en conflicto con citas existentes.
- **Query params:**
  - `date` (`yyyy-MM-dd`, requerido)
  - `time` (`HH:mm[:ss]`, requerido)
  - `durationMinutes` (Integer, opcional)
  - `excludeAppointmentId` (Long, opcional; util para update)
- **Respuesta:** `ApiResponse<Boolean>`
  - `true` = hay conflicto
  - `false` = no hay conflicto
- **HTTP:** `200 OK`

---

## 4) `AppointmentStatusController`

**Archivo:** `AppointmentStatusController.java`  
**Base path:** `/appointment-statuses`  
**Objetivo:** exponer catalogo de estados de cita para combos del frontend.

### Endpoints

### `GET /appointment-statuses`
- **Uso:** listar estados disponibles.
- **Respuesta:** `ApiResponse<List<AppointmentStatusResponse>>`
  - `id`
  - `status`
- **HTTP:** `200 OK`

---

## Reglas funcionales importantes (resumen)

Estas reglas viven en `AppointmentUseCase` y afectan varios endpoints:

- No permite crear/actualizar/reprogramar en fechas pasadas.
- No permite horas pasadas si la fecha es hoy.
- Horario permitido: `08:00` a `18:00`.
- Valida conflictos por traslape de horario usando `duracionEstimada`.
- Transiciones de estado validadas (ejemplo: `PENDIENTE -> CONFIRMADA`, etc.).

---

## Estandar de errores del modulo de citas

Los controladores de este paquete manejan errores con `ApiResponse` a traves de `AppointmentExceptionHandler`.

- `404 NOT_FOUND`: cita no encontrada (`AppointmentNotFoundException`)
- `409 CONFLICT`: conflicto de horario o duplicidad (`DuplicateAppointmentException`)
- `400 BAD_REQUEST`: reglas de validacion de cita (`InvalidAppointmentException`)
- `500 INTERNAL_SERVER_ERROR`: error no controlado

---

## DTOs principales usados por REST

- `AppointmentRequest`
- `AppointmentResponse`
- `AppointmentFilterRequest`
- `AppointmentCalendarResponse`
- `AppointmentStatusActionRequest`
- `AppointmentRescheduleRequest`
- `AvailabilitySlotResponse`
- `AppointmentStatusResponse`

---

## Ejemplos rapidos (frontend)

### Buscar por rango
`GET http://localhost:8080/api/v1/riee/appointments/search?from=2026-05-01&to=2026-05-31&appointmentStatusId=2`

### Agenda del dia
`GET http://localhost:8080/api/v1/riee/appointments/day?date=2026-05-01`

### Horarios disponibles
`GET http://localhost:8080/api/v1/riee/appointments/availability/slots?date=2026-05-01&durationMinutes=30&slotMinutes=30`

### Confirmar cita
`PATCH http://localhost:8080/api/v1/riee/appointments/15/confirm`

### Reprogramar cita
`PATCH http://localhost:8080/api/v1/riee/appointments/15/reschedule`
Body:
```json
{
  "fechaCita": "2026-05-10",
  "horaCita": "10:30:00",
  "observacionesCita": "Paciente solicita cambio por viaje"
}
```

### Crear cita
`POST http://localhost:8080/api/v1/riee/appointments`

