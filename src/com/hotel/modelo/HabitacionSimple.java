package com.hotel.modelo;

public class HabitacionSimple extends Habitacion {

    public HabitacionSimple(int numero, double precioBase, int capacidad) {
        super(numero, precioBase, capacidad);
    }

    @Override
    public double calcularPrecio() {
        return precioBase;
    }
}