package com.hotel.modelo;

import com.hotel.enums.EstadoHabitacion;

public abstract class Habitacion {

    protected int numero;
    protected double precioBase;
    protected int capacidad;
    protected EstadoHabitacion estado;
    protected Huesped huesped;

    public Habitacion(int numero, double precioBase, int capacidad) {
        this.numero = numero;
        this.precioBase = precioBase;
        this.capacidad = capacidad;
        this.estado = EstadoHabitacion.DISPONIBLE;
        this.huesped = null;
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

    public int getNumero() {
        return numero;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public void setDisponible(boolean disponible) {
        this.estado = disponible ? EstadoHabitacion.DISPONIBLE : EstadoHabitacion.OCUPADA;
    }

    public Huesped getHuesped() {
        return huesped;
    }

    public void setHuesped(Huesped huesped) {
        this.huesped = huesped;
    }
}