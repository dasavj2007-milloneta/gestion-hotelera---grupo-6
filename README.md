# 📚 Sistema de Gestión Hotelera

## 📖 Descripción del Sistema

Sistema orientado a la administración de un hotel, permitiendo gestionar habitaciones, reservas, huéspedes, empleados, servicios adicionales y facturación.

El sistema permite:

* Registrar huéspedes.
* Crear y gestionar reservas.
* Realizar procesos de check-in y check-out.
* Calcular tarifas según el tipo de habitación y temporada.
* Agregar servicios adicionales (spa, restaurante, lavandería, minibar, etc.).
* Generar facturas automáticamente.

---

# 👥 Integrantes

| Nombre                        | Correo                                                                      |
| ----------------------------- | --------------------------------------------------------------------------- |
| JUAN SEBASTIAN URUEÑA TORRES  | [juseuruena-2025a@corhuila.edu.co](mailto:juseuruena-2025a@corhuila.edu.co) |
| DAVID SANTIAGO VARGAS JIMENEZ | [dasavargas-2025a@corhuila.edu.co](mailto:dasavargas-2025a@corhuila.edu.co) |

---

# 🧩 Relaciones UML

## 🔷 Herencia (Generalización) `──▷`

### HabitacionSimple → Habitacion

### HabitacionSuite → Habitacion

### HabitacionSuitePresidencial → Habitacion

### HabitacionPenthouse → Habitacion

**Justificación:**
Todas las habitaciones comparten atributos comunes como:

* número
* precioBase
* capacidad
* estado

Pero cada una implementa su propia lógica para calcular el precio y posee características específicas.

---

## ◆ Composición

### Reserva ◆── Habitacion

**Multiplicidad:**
1 Reserva → 1 Habitación

### Factura ◆── Reserva

**Multiplicidad:**
1 Factura → 1 Reserva

**Justificación:**
Una reserva no puede existir sin una habitación asignada y una factura depende directamente de una reserva.

---

## ◇ Agregación

### Reserva ◇── ServicioAdicional

**Multiplicidad:**
1 Reserva → 0..* Servicios

**Justificación:**
Los servicios adicionales pueden existir independientemente de la reserva y agregarse cuando sea necesario.

---

## ─ Asociación

### Huesped ─ Reserva

**Multiplicidad:**
1 Huésped → 0..* Reservas

### Empleado ─ Reserva

**Multiplicidad:**
1 Empleado → 0..* Reservas

**Justificación:**
Representan interacción entre entidades del sistema sin dependencia fuerte.

---

## ⇢ Dependencia

### Reserva ⇢ CalculadoraTarifa

**Justificación:**
La clase `Reserva` utiliza la interfaz `CalculadoraTarifa` para calcular el costo de la estadía sin depender de implementaciones concretas.

---

# 📝 Clases Implementadas

| Clase                       | Tipo      | Atributos Principales                      | Métodos Principales                                                                       |
| --------------------------- | --------- | ------------------------------------------ | ----------------------------------------------------------------------------------------- |
| Habitacion                  | Abstracta | numero, precioBase, capacidad, estado      | calcularPrecio(), obtenerDescripcion(), cambiarEstado()                                   |
| HabitacionSimple            | Concreta  | Hereda de Habitacion                       | calcularPrecio()                                                                          |
| HabitacionSuite             | Concreta  | jacuzzi                                    | calcularPrecio()                                                                          |
| HabitacionSuitePresidencial | Concreta  | jacuzzi, balcon, desayuno                  | calcularPrecio()                                                                          |
| HabitacionPenthouse         | Concreta  | jacuzzi, balcon, desayuno, minibar, snacks | calcularPrecio()                                                                          |
| Huesped                     | Concreta  | id, nombre, documento, telefono, email     | registrarse(), actualizarDatos(), consultarReservas()                                     |
| Reserva                     | Concreta  | id, fechaInicio, fechaFin, estado          | calcularTotal(), confirmar(), cancelar(), agregarServicio(), calcularDias(), estaActiva() |
| ServicioAdicional           | Concreta  | id, nombre, precio                         | calcularCosto()                                                                           |
| Factura                     | Concreta  | id, total                                  | generarFactura(), calcularTotal(), exportarPDF(), mostrarDetalle(), checkIn(), checkOut() |
| Empleado                    | Concreta  | id, nombre, cargo                          | gestionarReserva(), registrarCheckIn(), registrarCheckOut(), crearReserva()               |

---

# 🧠 Interfaces y Estrategias

## <<interface>> CalculadoraTarifa

Define el contrato para calcular tarifas dinámicamente.

### Método:

```java
calcular(Habitacion habitacion, int dias): double
```

---

## Implementaciones de Estrategia

### TarifaRegular

Calcula el precio normal de hospedaje.

### TarifaTemporadaAlta

Incrementa el valor en temporadas especiales.

### TarifaDescuento

Aplica descuentos promocionales.

---

# 🏨 Tipos de Habitaciones

## HabitacionSimple

Habitación estándar para hospedaje básico.

## HabitacionSuite

Incluye jacuzzi y mayor comodidad.

## HabitacionSuitePresidencial

Incluye:

* jacuzzi
* balcón
* desayuno incluido

## HabitacionPenthouse

Incluye:

* jacuzzi
* balcón
* desayuno
* minibar
* snacks

---

# ⚙️ Principios SOLID Aplicados

## SRP — Single Responsibility Principle

Cada clase tiene una única responsabilidad.

## OCP — Open/Closed Principle

Es posible agregar nuevos tipos de habitaciones o tarifas sin modificar las clases existentes.

## LSP — Liskov Substitution Principle

Las subclases de `Habitacion` pueden reemplazar a la clase padre sin alterar el funcionamiento.

## ISP — Interface Segregation Principle

La interfaz `CalculadoraTarifa` contiene únicamente métodos necesarios.

## DIP — Dependency Inversion Principle

`Reserva` depende de la abstracción `CalculadoraTarifa` y no de implementaciones concretas.

---

# 📂 Estructura del Proyecto

```text
com
│
hotel
│
├── interfaces
│   └── CalculadoraTarifa.java
│
├── modelo
│   ├── Habitacion.java
│   ├── HabitacionSimple.java
│   ├── HabitacionSuite.java
│   ├── HabitacionSuitePresidencial.java
│   ├── HabitacionPenthouse.java
│   ├── Huesped.java
│   ├── Reserva.java
│   ├── ServicioAdicional.java
│   ├── Factura.java
│   └── Empleado.java
│
├── servicio
│   ├── TarifaRegular.java
│   ├── TarifaTemporadaAlta.java
│   └── TarifaDescuento.java
│
└── Main.java
```

## 📝 Conclusiones

El desarrollo del sistema de gestión hotelera permitió aplicar de manera práctica los fundamentos de la Programación Orientada a Objetos, integrando conceptos como herencia, abstracción, encapsulamiento y polimorfismo en un entorno cercano a un caso real. Gracias a esto, se logró construir una estructura organizada, reutilizable y fácil de mantener.

Además, la implementación de interfaces y principios SOLID ayudó a mejorar la calidad del diseño del software, permitiendo que las clases fueran más independientes y flexibles ante futuros cambios o ampliaciones del sistema. Esto facilita agregar nuevas funcionalidades sin afectar el código existente.

El uso del diagrama UML fue fundamental para visualizar la arquitectura del proyecto y comprender las relaciones entre las diferentes clases, permitiendo una mejor planificación antes de la etapa de codificación y reduciendo posibles errores en la implementación.

Finalmente, el trabajo colaborativo mediante GitHub permitió mantener un control adecuado de versiones, registrar el progreso del proyecto y fortalecer la organización del equipo durante el desarrollo de la aplicación.


