package com.hotel.modelo;

import com.hotel.enums.EstadoHabitacion;

public abstract class Habitacion {

    protected int numero;
    protected double precioBase;
    protected int capacidad;
    protected EstadoHabitacion estado;

    public Habitacion(int numero, double precioBase, int capacidad) {
        this.numero = numero;
        this.precioBase = precioBase;
        this.capacidad = capacidad;
        this.estado = EstadoHabitacion.DISPONIBLE;
    }

    public abstract double calcularPrecio();

    public String obtenerDescripcion() {
        return "Habitación #" + numero;
    }

    public boolean estaDisponible() {
        return estado == EstadoHabitacion.DISPONIBLE;
    }

    public void cambiarEstado(EstadoHabitacion estado) {
        this.estado = estado;
    }
}