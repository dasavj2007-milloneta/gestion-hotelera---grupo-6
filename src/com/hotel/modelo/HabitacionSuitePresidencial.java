package com.hotel.modelo;

public class HabitacionSuitePresidencial extends Habitacion {

    private boolean jacuzzi;
    private boolean butaca;

    public HabitacionSuitePresidencial(int numero, double precioBase, int capacidad, boolean jacuzzi, boolean butaca) {
        super(numero, precioBase, capacidad);
        this.jacuzzi = jacuzzi;
        this.butaca = butaca;
    }

    @Override
    public double calcularPrecio() {
        double precio = precioBase;
        if (jacuzzi) {
            precio *= 1.3;
        }
        if (butaca) {
            precio *= 1.2;
        }
        return precio;
    }
}
