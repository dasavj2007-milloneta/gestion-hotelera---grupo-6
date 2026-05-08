package com.hotel.modelo;

public class Empleado {

    private int id;
    private String nombre;
    private String cargo;

    public Empleado(int id, String nombre, String cargo) {
        this.id = id;
        this.nombre = nombre;
        this.cargo = cargo;
    }

    public void gestionarReserva() {
        System.out.println("Gestionando reserva");
    }

    public void registrarCheckIn() {
        System.out.println("Check-in registrado");
    }

    public void registrarCheckOut() {
        System.out.println("Check-out registrado");
    }

    public void crearReserva() {
        System.out.println("Reserva creada");
    }
}