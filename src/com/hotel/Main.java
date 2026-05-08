package com.hotel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.hotel.modelo.*;
import com.hotel.servicios.*;
import com.hotel.enums.EstadoHabitacion;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Habitacion> habitaciones = new ArrayList<>();
        List<Reserva> reservas = new ArrayList<>();

        // Inicializar habitaciones de ejemplo
        habitaciones.add(new HabitacionSimple(101, 100000.0, 2));
        habitaciones.add(new HabitacionSuite(102, 200000.0, 4, true));
        HabitacionSimple ocupada = new HabitacionSimple(103, 120000.0, 3);
        ocupada.setDisponible(false);
        habitaciones.add(ocupada);

        System.out.println("Bienvenido al Hotel");

        while (true) {
            System.out.println("------------------------------");
            System.out.println("\nSeleccione una opción:");
            System.out.println("------------------------------");
            System.out.println("1. Huésped");
            System.out.println("2. Empleado");
            System.out.println("3. Salir");
            System.out.println("------------------------------");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir newline

            if (opcion == 1) {
                // Modo Usuario
                System.out.println("------------------------------");
                System.out.println("Ingrese su nombre:");
                String nombre = leerInputValido(scanner);
                System.out.println("------------------------------");
                System.out.println("Ingrese su cédula:");
                String cedula = leerInputValido(scanner);
                System.out.println("------------------------------");
                System.out.println("Ingrese su teléfono:");
                String telefono = leerInputValido(scanner);
                System.out.println("------------------------------");
                System.out.println("Ingrese su email:");
                String email = leerInputValido(scanner);

                Huesped huesped = new Huesped(1, nombre, cedula, telefono, email);

                System.out.println("------------------------------");
                System.out.println("Seleccione tipo de habitación:");
                System.out.println("------------------------------");
                System.out.println("1. Habitación Simple");
                System.out.println("2. Habitación Suite");
                System.out.println("------------------------------");
                int tipoHab = scanner.nextInt();
                scanner.nextLine();

                Habitacion habitacionSeleccionada = null;
                for (Habitacion h : habitaciones) {
                    if ((tipoHab == 1 && h instanceof HabitacionSimple && h.estaDisponible()) ||
                        (tipoHab == 2 && h instanceof HabitacionSuite && h.estaDisponible())) {
                        habitacionSeleccionada = h;
                        break;
                    }
                }

                if (habitacionSeleccionada == null) {
                    System.out.println("------------------------------");
                    System.out.println("No hay habitaciones disponibles de ese tipo.");
                    System.out.println("------------------------------");
                    continue;
                }

                System.out.println("------------------------------");
                System.out.println("Fecha de check-in (YYYY-MM-DD):");
                LocalDate checkIn = leerFechaValida(scanner);
                System.out.println("------------------------------");
                System.out.println("Fecha de check-out (YYYY-MM-DD):");
                LocalDate checkOut = leerFechaValida(scanner);

                Reserva reserva = new Reserva(reservas.size() + 1, checkIn, checkOut, huesped, habitacionSeleccionada, new TarifaRegular());
                
                System.out.println("------------------------------");
                System.out.println("¿Desea añadir servicios adicionales? (si/no)");
                if (leerInputValido(scanner).equalsIgnoreCase("si")) {
                    System.out.println("------------------------------");
                    System.out.println("Servicios disponibles:");
                    System.out.println("1. Spa - $50000");
                    System.out.println("2. Desayuno - $30000");
                    System.out.println("------------------------------");
                    int servOpcion = scanner.nextInt();
                    scanner.nextLine();
                    if (servOpcion == 1) {
                        reserva.agregarServicio(new ServicioAdicional(1, "Spa", 50000));
                    } else if (servOpcion == 2) {
                        reserva.agregarServicio(new ServicioAdicional(2, "Desayuno", 30000));
                    }
                }

                reserva.confirmar();
                reservas.add(reserva);

                System.out.println("------------------------------");
                System.out.println("¿Tiene descuento? (si/no)");
                if (leerInputValido(scanner).equalsIgnoreCase("si")) {
                    System.out.println("------------------------------");
                    System.out.println("Ingrese su código de descuento:");
                    String codigoIngresado = leerInputValido(scanner);
                    String[] codigosValidos = {"Taylor Swift", "Black Friday", "Descuento Secreto"};
                    boolean codigoValido = false;
                    for (String codigo : codigosValidos) {
                        if (codigoIngresado.equalsIgnoreCase(codigo)) {
                            reserva.setCalculadoraTarifa(new TarifaDescuento());
                            System.out.println("Código aceptado: " + codigo);
                            codigoValido = true;
                            break;
                        }
                    }
                    if (!codigoValido) {
                        System.out.println("Código inválido.");
                    }
                }

                Factura factura = new Factura(reservas.size(), reserva);
                factura.generarFactura();
                factura.mostrarDetalle();

            } else if (opcion == 2) {
                // Modo Empleado
                System.out.println("Opciones de empleado:");
                System.out.println("1. Ver habitaciones");
                System.out.println("2. Editar habitación");
                int empOpcion = scanner.nextInt();
                scanner.nextLine();

                if (empOpcion == 1) {
                    for (Habitacion h : habitaciones) {
                        System.out.println("Habitación " + h.getNumero() + " - " + (h.estaDisponible() ? "Libre" : "Ocupada") + " - Precio: $" + h.getPrecioBase());
                    }
                } else if (empOpcion == 2) {
                    System.out.println("Ingrese número de habitación a editar:");
                    int numHab = scanner.nextInt();
                    scanner.nextLine();
                    Habitacion habEditar = null;
                    for (Habitacion h : habitaciones) {
                        if (h.getNumero() == numHab) {
                            habEditar = h;
                            break;
                        }
                    }
                    if (habEditar != null) {
                        System.out.println("Nuevo precio:");
                        double nuevoPrecio = scanner.nextDouble();
                        scanner.nextLine();
                        System.out.println("Disponible? (s/n)");
                        boolean disponible = leerInputValido(scanner).equalsIgnoreCase("s");
                        habEditar.setPrecioBase(nuevoPrecio);
                        habEditar.setDisponible(disponible);
                        System.out.println("Habitación editada.");
                    } else {
                        System.out.println("Habitación no encontrada.");
                    }
                }

            } else if (opcion == 3) {
                break;
            } else {
                System.out.println("Opción inválida.");
            }
        }

        scanner.close();
    }

    private static String leerInputValido(Scanner scanner) {
        String input = "";
        while (input.trim().isEmpty()) {
            input = scanner.nextLine();
            if (input.trim().isEmpty()) {
                System.out.println("Por favor ingrese un valor válido:");
            }
        }
        return input;
    }

    private static LocalDate leerFechaValida(Scanner scanner) {
        LocalDate fecha = null;
        while (fecha == null) {
            try {
                String input = leerInputValido(scanner);
                fecha = LocalDate.parse(input);
            } catch (Exception e) {
                System.out.println("Formato inválido. Por favor use YYYY-MM-DD:");
            }
        }
        return fecha;
    }
}