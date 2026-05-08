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
        System.out.println("Total factura: " + total);
    }
}