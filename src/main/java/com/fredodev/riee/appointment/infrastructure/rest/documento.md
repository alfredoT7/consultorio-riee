# Endpoints REST de Citas

Base path: `/api/v1/riee/appointments`

## Convenciones

- Respuestas exitosas usan `ApiResponse<T>`, excepto `DELETE` que retorna `204 No Content` sin body.
- Fechas: `yyyy-MM-dd`
- Horas: `HH:mm:ss`
- Las citas pueden programarse a cualquier hora del dia
- Granularidad fija de slots: `15` minutos

## 1. GET `/appointments`

Lista citas y permite filtros opcionales.

### Query params

- `from`: fecha inicial opcional
- `to`: fecha final opcional
- `date`: fecha exacta opcional
- `status`: nombre de estado opcional, por ejemplo `PROGRAMADA`, `CONFIRMADA`, `CANCELADA`

### Reglas

- No se puede combinar `date` con `from` o `to`
- Si `from > to`, responde `400`

### Response `200`

```json
{
  "success": true,
  "status": 200,
  "message": "Citas encontradas",
  "data": [
    {
      "id": 15,
      "fechaCita": "2026-05-10",
      "horaCita": "10:30:00",
      "motivoCita": "Control",
      "observacionesCita": "Traer radiografia",
      "duracionEstimada": 30,
      "patient": {
        "id": 4,
        "nombre": "Ana",
        "apellido": "Lopez",
        "ciPaciente": 1234567,
        "email": "ana@email.com",
        "direccion": "Zona Sur"
      },
      "status": {
        "id": 2,
        "name": "CONFIRMADA"
      }
    }
  ]
}
```

## 2. POST `/appointments`

Crea una cita.

### Body

```json
{
  "patientId": 4,
  "fechaCita": "2026-05-10",
  "horaCita": "10:30:00",
  "motivoCita": "Control",
  "duracionEstimada": 30,
  "observacionesCita": "Traer radiografia"
}
```

### Response `201`

Retorna la cita creada con la misma estructura nested de `GET /appointments`.
Si no se envia `appointmentStatusId`, el backend asigna automaticamente `PROGRAMADA`.

## 3. PUT `/appointments/{id}`

Actualiza parcialmente una cita.

### Body

Todos los campos son opcionales. Debe venir al menos uno.

```json
{
  "horaCita": "11:00:00",
  "duracionEstimada": 45,
  "appointmentStatusId": 3,
  "observacionesCita": "Paciente solicito ajuste"
}
```

### Response `200`

Retorna la cita actualizada con la misma estructura nested de `GET /appointments`.

## 4. DELETE `/appointments/{id}`

Elimina una cita.

### Response `204`

Sin body.

## 5. GET `/appointments/slots`

Devuelve horarios disponibles para una fecha.

### Query params

- `date`: requerido
- `duration`: opcional, default `30`

### Response `200`

```json
{
  "success": true,
  "status": 200,
  "message": "Horarios disponibles encontrados",
    "data": [
      {
      "startTime": "00:00",
      "endTime": "00:30"
    },
    {
      "startTime": "00:15",
      "endTime": "00:45"
    }
  ]
}
```

## Reglas de negocio

- No se crean ni actualizan citas en el pasado
- Si la fecha es hoy, la hora no puede ser anterior a la actual
- Se valida solapamiento usando `duracionEstimada`
- En creacion, si no se envia `appointmentStatusId`, se usa `PROGRAMADA`
- En update, `appointmentStatusId` sigue siendo opcional y solo cambia estado si viene informado
- Cambios de estado siguen reglas de transición del backend

## Errores

- `400 Bad Request`: filtros inválidos, formato inválido, transición inválida, datos incompletos
- `404 Not Found`: cita inexistente
- `409 Conflict`: solapamiento de horario
- `500 Internal Server Error`: error no controlado
