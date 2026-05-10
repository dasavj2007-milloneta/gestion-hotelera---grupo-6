package com.hotel.modelo;

import com.hotel.interfaces.Facturable;

public class Factura implements Facturable {

    private int id;
    private double total;

    private Reserva reserva;

    public Factura(int id, Reserva reserva) {
        this.id = id;
        this.reserva = reserva;
    }

    public void generarFactura() {
        total = calcularTotal();
        System.out.println("Factura generada");
    }

    @Override
    public double calcularTotal() {
        return reserva.calcularTotal();
    }

    public void mostrarDetalle() {
        System.out.println("\n╔══════════════════════════════╗");
        System.out.println("║          FACTURA             ║");
        System.out.println("╚══════════════════════════════╝");
        System.out.println("Factura #: " + id);
        System.out.println("Huésped: " + reserva.getHuesped().getNombre());
        System.out.println("Cédula: " + reserva.getHuesped().getCedula());
        System.out.println("------------------------------");
        System.out.println("HABITACIÓN");
        System.out.println("------------------------------");
        System.out.println("Número: " + reserva.getHabitacion().getNumero());
        System.out.println("Tipo: " + reserva.getHabitacion().getClass().getSimpleName());
        System.out.println("Precio por noche: $" + String.format("%.0f", reserva.getHabitacion().getPrecioBase()));
        System.out.println("Número de noches: " + reserva.calcularDias());
        
        // Calcular subtotal sin descuento
        double precioHabitacion = reserva.getHabitacion().calcularPrecio() * reserva.calcularDias();
        System.out.println("Subtotal habitación: $" + String.format("%.0f", precioHabitacion));
        
        System.out.println("------------------------------");
        System.out.println("SERVICIOS ADICIONALES");
        System.out.println("------------------------------");
        
        double totalServicios = 0;
        if (reserva.getServicios().isEmpty()) {
            System.out.println("Sin servicios adicionales");
        } else {
            for (ServicioAdicional servicio : reserva.getServicios()) {
                double costoServicio = servicio.calcularCosto();
                System.out.println(servicio.getNombre() + ": $" + String.format("%.0f", costoServicio));
                totalServicios += costoServicio;
            }
        }
        System.out.println("Total servicios: $" + String.format("%.0f", totalServicios));
        
        System.out.println("------------------------------");
        double subtotalSinDescuento = precioHabitacion + totalServicios;
        System.out.println("Subtotal: $" + String.format("%.0f", subtotalSinDescuento));
        
        double descuentoAplicado = subtotalSinDescuento - total;
        if (descuentoAplicado > 0) {
            System.out.println("Código de descuento: " + reserva.getCodigoDescuento());
            System.out.println("Descuento (" + String.format("%.0f", reserva.getPorcentajeDescuento()) + "%): -$" + String.format("%.0f", descuentoAplicado));
        }
        
        System.out.println("------------------------------");
        String totalFormato = String.format("%.0f", total);
        String lineaTotal = String.format("║  TOTAL FACTURA: $%-15s║", totalFormato);
        System.out.println(lineaTotal);
        System.out.println("╚══════════════════════════════╝\n");
    }
}