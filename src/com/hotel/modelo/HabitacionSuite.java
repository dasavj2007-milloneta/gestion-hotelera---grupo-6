package com.hotel.modelo;

public class HabitacionSuite extends Habitacion {

    private boolean jacuzzi;

    public HabitacionSuite(int numero, double precioBase, int capacidad, boolean jacuzzi) {
        super(numero, precioBase, capacidad);
        this.jacuzzi = jacuzzi;
    }

    @Override
    public double calcularPrecio() {
        return jacuzzi ? precioBase * 1.5 : precioBase;
    }
}