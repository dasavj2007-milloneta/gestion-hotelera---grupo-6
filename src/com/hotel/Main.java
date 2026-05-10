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

        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║  ✨ BIENVENIDO AL HOTEL SWIFT ✨   ║");
        System.out.println("╚════════════════════════════════════╝");

        while (true) {
            System.out.println("\n╔════════════════════════════════════╗");
            System.out.println("║       MENÚ PRINCIPAL               ║");
            System.out.println("╠════════════════════════════════════╣");
            System.out.println("║  1. Huésped                        ║");
            System.out.println("║  2. Empleado                       ║");
            System.out.println("║  3. Salir                          ║");
            System.out.println("╚════════════════════════════════════╝");
            int opcion = leerOpcionValida(scanner, 1, 3);

            if (opcion == 1) {
                // Modo Usuario
                System.out.println("\n╔════════════════════════════════════╗");
                System.out.println("║    REGISTRO DE HUÉSPED             ║");
                System.out.println("╚════════════════════════════════════╝");
                System.out.println("\nIngrese su nombre:");
                String nombre = leerInputValido(scanner);
                System.out.println("Ingrese su cédula:");
                String cedula = leerInputValido(scanner);
                System.out.println("Ingrese su teléfono:");
                String telefono = leerInputValido(scanner);
                System.out.println("Ingrese su email:");
                String email = leerInputValido(scanner);

                Huesped huesped = new Huesped(1, nombre, cedula, telefono, email);

                System.out.println("\n╔════════════════════════════════════╗");
                System.out.println("║    SELECCIÓN DE HABITACIÓN         ║");
                System.out.println("╠════════════════════════════════════╣");
                System.out.println("║  1. Habitación Simple ($100,000)   ║");
                System.out.println("║  2. Habitación Suite ($200,000)    ║");
                System.out.println("╚════════════════════════════════════╝");
                int tipoHab = leerOpcionValida(scanner, 1, 2);

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

                System.out.println("\n╔════════════════════════════════════╗");
                System.out.println("║    FECHAS DE ESTANCIA              ║");
                System.out.println("╚════════════════════════════════════╝");
                System.out.println("Fecha de check-in (YYYY-MM-DD):");
                LocalDate checkIn = leerFechaValida(scanner);
                System.out.println("Fecha de check-out (YYYY-MM-DD):");
                LocalDate checkOut = leerFechaValida(scanner);

                Reserva reserva = new Reserva(reservas.size() + 1, checkIn, checkOut, huesped, habitacionSeleccionada, new TarifaRegular());
                
                System.out.println("\n╔════════════════════════════════════╗");
                System.out.println("║    SERVICIOS ADICIONALES           ║");
                System.out.println("╚════════════════════════════════════╝");
                System.out.println("¿Desea añadir servicios adicionales? (si/no)");
                boolean agregarMasServicios = true;
                if (leerInputValido(scanner).equalsIgnoreCase("si")) {
                    while (agregarMasServicios) {
                        System.out.println("\n╔════════════════════════════════════╗");
                        System.out.println("║    SERVICIOS DISPONIBLES           ║");
                        System.out.println("╠════════════════════════════════════╣");
                        System.out.println("║  1. Spa - $50,000                  ║");
                        System.out.println("║  2. Desayuno - $30,000             ║");
                        System.out.println("╚════════════════════════════════════╝");
                        int servOpcion = leerOpcionValida(scanner, 1, 2);
                        
                        ServicioAdicional nuevoServicio = null;
                        if (servOpcion == 1) {
                            nuevoServicio = new ServicioAdicional(1, "Spa", 50000);
                        } else if (servOpcion == 2) {
                            nuevoServicio = new ServicioAdicional(2, "Desayuno", 30000);
                        }
                        
                        // Verificar si el servicio ya existe
                        boolean servicioExistente = false;
                        for (ServicioAdicional s : reserva.getServicios()) {
                            if (s.getNombre().equalsIgnoreCase(nuevoServicio.getNombre())) {
                                servicioExistente = true;
                                System.out.println("\n⚠ El servicio '" + nuevoServicio.getNombre() + "' ya fue agregado.");
                                System.out.println("¿Desea agregarlo nuevamente? (si/no)");
                                if (leerInputValido(scanner).equalsIgnoreCase("si")) {
                                    reserva.agregarServicio(nuevoServicio);
                                    System.out.println(nuevoServicio.getNombre() + " agregado.");
                                }
                                break;
                            }
                        }
                        
                        if (!servicioExistente) {
                            reserva.agregarServicio(nuevoServicio);
                            System.out.println("✓ " + nuevoServicio.getNombre() + " agregado.");
                        }
                        
                        System.out.println("¿Desea agregar otro servicio? (si/no)");
                        agregarMasServicios = leerInputValido(scanner).equalsIgnoreCase("si");
                    }
                }

                reserva.confirmar();
                reservas.add(reserva);
                
                // Marcar la habitación como ocupada
                habitacionSeleccionada.setDisponible(false);

                System.out.println("\n╔════════════════════════════════════╗");
                System.out.println("║    CÓDIGO DE DESCUENTO             ║");
                System.out.println("╚════════════════════════════════════╝");
                boolean descuentoProcessed = false;
                while (!descuentoProcessed) {
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
                                reserva.setCodigoDescuento(codigoIngresado);
                                reserva.setPorcentajeDescuento(10.0);
                                System.out.println("Código aceptado: " + codigo + " (10% de descuento)");
                                codigoValido = true;
                                descuentoProcessed = true;
                                break;
                            }
                        }
                        if (!codigoValido) {
                            System.out.println("Código inválido.");
                            System.out.println("------------------------------");
                        }
                    } else {
                        descuentoProcessed = true;
                    }
                }

                Factura factura = new Factura(reservas.size(), reserva);
                factura.generarFactura();
                factura.mostrarDetalle();

            } else if (opcion == 2) {
                // Modo Empleado - Con autenticación
                System.out.println("\n╔════════════════════════════════════╗");
                System.out.println("║   ACCESO AL PANEL DE EMPLEADO      ║");
                System.out.println("╚════════════════════════════════════╝");
                System.out.println("Ingrese usuario:");
                String usuario = leerInputValido(scanner);
                System.out.println("Ingrese contraseña:");
                String contrasena = leerInputValido(scanner);
                
                if (!usuario.equalsIgnoreCase("Admin") || !contrasena.equals("Admin")) {
                    System.out.println("\n✗ Usuario o contraseña incorrectos.\n");
                    continue;
                }
                
                System.out.println("\n✓ Bienvenido, Admin\n");
                System.out.println("╔════════════════════════════════════╗");
                System.out.println("║    OPCIONES DE EMPLEADO            ║");
                System.out.println("╠════════════════════════════════════╣");
                System.out.println("║  1. Ver habitaciones               ║");
                System.out.println("║  2. Editar habitación              ║");
                System.out.println("╚════════════════════════════════════╝");
                int empOpcion = leerOpcionValida(scanner, 1, 2);

                if (empOpcion == 1) {
                    System.out.println("\n╔════════════════════════════════════╗");
                    System.out.println("║    LISTA DE HABITACIONES           ║");
                    System.out.println("╠════════════════════════════════════╣");
                    for (Habitacion h : habitaciones) {
                        String tipo = h instanceof HabitacionSimple ? "Simple" : "Suite";
                        String estado = h.estaDisponible() ? "✓ Disponible" : "✗ Ocupada";
                        System.out.println("║ Hab " + h.getNumero() + " - " + tipo + " - " + estado);
                    }
                    System.out.println("╚════════════════════════════════════╝\n");
                } else if (empOpcion == 2) {
                    System.out.println("Ingrese número de habitación a editar:");
                    int numHab = leerOpcionValida(scanner, 100, 999);
                    Habitacion habEditar = null;
                    for (Habitacion h : habitaciones) {
                        if (h.getNumero() == numHab) {
                            habEditar = h;
                            break;
                        }
                    }
                    if (habEditar != null) {
                        System.out.println("Nuevo precio:");
                        double nuevoPrecio = leerNumeroValido(scanner);
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
                System.out.println("\n╔════════════════════════════════════╗");
                System.out.println("║  ¡MUCHAS GRACIAS POR SU VISITA!    ║");
                System.out.println("║     ESPERAMOS VERLO PRONTO         ║");
                System.out.println("╚════════════════════════════════════╝\n");
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

    private static int leerOpcionValida(Scanner scanner, int min, int max) {
        int opcion = -1;
        boolean valido = false;
        while (!valido) {
            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir newline
                if (opcion >= min && opcion <= max) {
                    valido = true;
                } else {
                    System.out.println("Por favor ingrese una opción válida (" + min + "-" + max + "):");
                }
            } catch (Exception e) {
                scanner.nextLine(); // Limpiar el buffer
                System.out.println("Entrada inválida. Por favor ingrese un número:");
            }
        }
        return opcion;
    }

    private static double leerNumeroValido(Scanner scanner) {
        double numero = -1;
        boolean valido = false;
        while (!valido) {
            try {
                numero = scanner.nextDouble();
                scanner.nextLine();
                if (numero > 0) {
                    valido = true;
                } else {
                    System.out.println("Por favor ingrese un número positivo:");
                }
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("Entrada inválida. Por favor ingrese un número:");
            }
        }
        return numero;
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