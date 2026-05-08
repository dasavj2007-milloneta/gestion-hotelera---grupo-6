package com.hotel.modelo;

import com.hotel.interfaces.Serviciable;

public class ServicioAdicional implements Serviciable {

    private int id;
    private String nombre;
    private double precio;

    public ServicioAdicional(int id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    @Override
    public double calcularCosto() {
        return precio;
    }

    public String obtenerDescripcion() {
        return nombre;
    }
}