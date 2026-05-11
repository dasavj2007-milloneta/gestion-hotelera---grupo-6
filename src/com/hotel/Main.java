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
        habitaciones.add(new HabitacionSuite(102, 250000.0, 4, true));
        habitaciones.add(new HabitacionSuitePresidencial(103, 350000.0, 4, true, true));
        habitaciones.add(new HabitacionPenthouse(104, 500000.0, 6, true, true, true));

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

                System.out.println("\n╔═════════════════════════════════════════════════════════════════════════════════════╗");
                System.out.println("║                               SELECCIÓN DE HABITACIÓN                               ║");
                System.out.println("╠═════════════════════════════════════════════════════════════════════════════════════╣");
                System.out.println("║  1. Simple - $100,000              - Sin servicios adicionales                      ║");
                System.out.println("║  2. Suite - $250,000               - Jacuzzi                                        ║");
                System.out.println("║  3. Suite Presidencial - $350,000  - Jacuzzi, Balcón, Desayuno                      ║");
                System.out.println("║  4. Penthouse - $500,000           - Jacuzzi, Balcón, Bar Libre, Desayuno, Snacks   ║");
                System.out.println("╚═════════════════════════════════════════════════════════════════════════════════════╝");
                int tipoHab = leerOpcionValida(scanner, 1, 4);

                Habitacion habitacionSeleccionada = null;
                for (Habitacion h : habitaciones) {
                    if ((tipoHab == 1 && h instanceof HabitacionSimple && !(h instanceof HabitacionSuitePresidencial) && h.estaDisponible()) ||
                        (tipoHab == 2 && h instanceof HabitacionSuite && !(h instanceof HabitacionSuitePresidencial) && !(h instanceof HabitacionPenthouse) && h.estaDisponible()) ||
                        (tipoHab == 3 && h instanceof HabitacionSuitePresidencial && h.estaDisponible()) ||
                        (tipoHab == 4 && h instanceof HabitacionPenthouse && h.estaDisponible())) {
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
                
                // Asignar el huésped a la habitación
                habitacionSeleccionada.setHuesped(huesped);
                
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
                
                // Crear empleado con datos de prueba
                Empleado empleado = new Empleado(1, "Juan", "1075242815", "Gerente");
                
                System.out.println("\n✓ Bienvenido, " + empleado.getNombre() + "\n");
                System.out.println("╔════════════════════════════════════╗");
                System.out.println("║    INFORMACIÓN DEL EMPLEADO        ║");
                System.out.println("╠════════════════════════════════════╣");
                System.out.println("║ Nombre: " + empleado.getNombre());
                System.out.println("║ Cédula: " + empleado.getCedula());
                System.out.println("║ Cargo: " + empleado.getCargo());
                System.out.println("╚════════════════════════════════════╝\n");
                
                // Bucle del menú de empleado
                boolean enMenuEmpleado = true;
                while (enMenuEmpleado) {
                    System.out.println("╔════════════════════════════════════╗");
                    System.out.println("║    OPCIONES DE EMPLEADO            ║");
                    System.out.println("╠════════════════════════════════════╣");
                    System.out.println("║  1. Ver habitaciones               ║");
                    System.out.println("║  2. Editar habitación              ║");
                    System.out.println("║  3. Salir del menú de Empleado     ║");
                    System.out.println("╚════════════════════════════════════╝");
                    int empOpcion = leerOpcionValida(scanner, 1, 3);

                    if (empOpcion == 1) {
                        System.out.println("\n╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
                        System.out.println("║                                       LISTA DE HABITACIONES                                             ║");
                        System.out.println("╠══════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
                        for (Habitacion h : habitaciones) {
                            String tipo;
                            String servicios;
                            if (h instanceof HabitacionPenthouse) {
                                tipo = "Penthouse";
                                servicios = "Jacuzzi, Balcón, Bar Libre, Desayuno, Snacks";
                            } else if (h instanceof HabitacionSuitePresidencial) {
                                tipo = "Suite Presidencial";
                                servicios = "Jacuzzi, Balcón, Desayuno";
                            } else if (h instanceof HabitacionSuite) {
                                tipo = "Suite";
                                servicios = "Jacuzzi";
                            } else {
                                tipo = "Simple";
                                servicios = "Sin servicios adicionales";
                            }
                            String estado = h.estaDisponible() ? "✓ Disponible" : "✗ Ocupada";
                            String precio = String.format("$%,.0f", h.getPrecioBase());
                            System.out.printf("║ Hab %-3d - %-18s - %-10s - %-25s - %s%n",
                                h.getNumero(), tipo, precio, servicios, estado);
                            
                            // Mostrar información del huésped si la habitación está ocupada
                            if (!h.estaDisponible() && h.getHuesped() != null) {
                                System.out.printf("║       └─ Huésped: %s (Cédula: %s)%n", h.getHuesped().getNombre(), h.getHuesped().getCedula());
                            }
                        }
                        System.out.println("╚══════════════════════════════════════════════════════════════════════════════════════════════════════════════╝\n");
                    } else if (empOpcion == 2) {
                        System.out.println("\n╔════════════════════════════════════╗");
                        System.out.println("║   EDITAR HABITACIÓN                ║");
                        System.out.println("╚════════════════════════════════════╝");
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
                            boolean editandoHab = true;
                            while (editandoHab) {
                                System.out.println("\n╔════════════════════════════════════╗");
                                System.out.println("║   OPCIONES DE EDICIÓN              ║");
                                System.out.println("╠════════════════════════════════════╣");
                                System.out.println("║  1. Editar precio                  ║");
                                System.out.println("║  2. Editar disponibilidad          ║");
                                System.out.println("║  3. Check-in (marcar como ocupada) ║");
                                System.out.println("║  4. Check-out (marcar disponible)  ║");
                                System.out.println("║  5. Volver al menú anterior        ║");
                                System.out.println("╚════════════════════════════════════╝");
                                int editOpcion = leerOpcionValida(scanner, 1, 5);
                                
                                if (editOpcion == 1) {
                                    System.out.println("Nuevo precio:");
                                    double nuevoPrecio = leerNumeroValido(scanner);
                                    habEditar.setPrecioBase(nuevoPrecio);
                                    System.out.println("✓ Precio actualizado a $" + String.format("%,.0f", nuevoPrecio));
                                } else if (editOpcion == 2) {
                                    System.out.println("¿Disponible? (s/n)");
                                    boolean disponible = leerInputValido(scanner).equalsIgnoreCase("s");
                                    habEditar.setDisponible(disponible);
                                    String estado = disponible ? "Disponible" : "Ocupada";
                                    System.out.println("✓ Habitación marcada como " + estado);
                                } else if (editOpcion == 3) {
                                    habEditar.setDisponible(false);
                                    System.out.println("✓ Check-in realizado - Habitación marcada como ocupada");
                                } else if (editOpcion == 4) {
                                    habEditar.setDisponible(true);
                                    habEditar.setHuesped(null);
                                    System.out.println("✓ Check-out realizado - Habitación marcada como disponible");
                                } else if (editOpcion == 5) {
                                    editandoHab = false;
                                }
                            }
                        } else {
                            System.out.println("✗ Habitación no encontrada.");
                        }
                    } else if (empOpcion == 3) {
                        enMenuEmpleado = false;
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