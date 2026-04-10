# 📚 Sistema de Gestión de Hotel

## 📖 Descripción del Sistema

Sistema para gestionar la administración de un hotel: habitaciones,
reservas, huéspedes, empleados y servicios adicionales. Permite
realizar check-in, check-out, consumo de servicios (spa, restaurante,
lavandería) y generación de facturas.

---

## 👥 Integrantes

| Nombre                        | Correo                                                                      |
| ----------------------------- | --------------------------------------------------------------------------- |
| JUAN SEBASTIAN URUEÑA TORRES  | [juseuruena-2025a@corhuila.edu.co](mailto:juseuruena-2025a@corhuila.edu.co) |
| DAVID SANTIAGO VARGAS JIMENEZ | [dasavargas-2025a@corhuila.edu.co](mailto:dasavargas-2025a@corhuila.edu.co) |

---

## 🔗 Explicación de Relaciones

### Herencia (──▷)

* **HabitacionSimple** y **HabitacionSuite** heredan de **Habitacion**
  (clase abstracta).
* Justificación: Ambas representan tipos específicos de habitación que
  comparten atributos comunes (número, precio base, capacidad, estado),
  pero difieren en sus características y en la forma de calcular el precio.

---

### Composición (◆───)

* **Reserva** contiene una **Habitacion**.

  * Multiplicidad: 1 Reserva → 1 Habitación
* **Factura** contiene una **Reserva**.

  * Multiplicidad: 1 Factura → 1 Reserva
* Justificación: Una reserva no tiene sentido sin una habitación asignada
  y una factura depende directamente de una reserva realizada.

---

### Asociación (─────)

* **Huesped** se asocia con **Reserva**.

  * Multiplicidad: 1 Huésped → 0..* Reservas
* **Reserva** se asocia con **ServicioAdicional**.

  * Multiplicidad: 1 Reserva → 0..* Servicios
* **Empleado** se asocia con **Reserva**.

  * Multiplicidad: 1 Empleado → 0..* Reservas
* Justificación: Estas relaciones representan interacción entre clases,
  pero pueden existir de forma independiente dentro del sistema.

---

## 📝 Clases Implementadas

| Clase             | Tipo      | Atributos                                              | Descripción                |
| ----------------- | --------- | ------------------------------------------------------ | -------------------------- |
| Habitacion        | Abstracta | numero, precioBase, capacidad, estado                  | Clase base de habitaciones |
| HabitacionSimple  | Concreta  | (hereda de Habitacion)                                 | Habitación estándar        |
| HabitacionSuite   | Concreta  | jacuzzi                                                | Habitación de lujo         |
| Huesped           | Concreta  | id, nombre, documento, telefono, email                 | Cliente del hotel          |
| Reserva           | Concreta  | id, fechaInicio, fechaFin, estado, huesped, habitacion | Gestión de reservas        |
| ServicioAdicional | Concreta  | id, nombre, precio                                     | Servicios del hotel        |
| Factura           | Concreta  | id, total, reserva                                     | Facturación                |
| Empleado          | Concreta  | id, nombre, cargo                                      | Personal del hotel         |

---

*Proyecto de Programación y Diseño Orientado a Objetos — Corhuila 2026*
