package com.hotel.modelo;

public class Huesped {

    private int id;
    private String nombre;
    private String documento;
    private String telefono;
    private String email;

    public Huesped(int id, String nombre, String documento,
                    String telefono, String email) {
        this.id = id;
        this.nombre = nombre;
        this.documento = documento;
        this.telefono = telefono;
        this.email = email;
    }

    public void registrarse() {
        System.out.println("Huésped registrado");
    }

    public void actualizarDatos() {
        System.out.println("Datos actualizados");
    }
}