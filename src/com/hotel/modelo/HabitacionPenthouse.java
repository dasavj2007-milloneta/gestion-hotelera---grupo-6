package com.hotel.modelo;

public class HabitacionPenthouse extends Habitacion {

    private boolean piscina;
    private boolean terraza;
    private boolean cine;

    public HabitacionPenthouse(int numero, double precioBase, int capacidad, boolean piscina, boolean terraza, boolean cine) {
        super(numero, precioBase, capacidad);
        this.piscina = piscina;
        this.terraza = terraza;
        this.cine = cine;
    }

    @Override
    public double calcularPrecio() {
        double precio = precioBase;
        if (piscina) {
            precio *= 1.4;
        }
        if (terraza) {
            precio *= 1.3;
        }
        if (cine) {
            precio *= 1.2;
        }
        return precio;
    }
}
