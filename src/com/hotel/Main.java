package com.hotel;

import java.time.LocalDate;

import com.hotel.modelo.*;
import com.hotel.servicios.*;

public class Main {

    public static void main(String[] args) {

        Huesped huesped = new Huesped(
                1,
                "Juan",
                "12345",
                "300000000",
                "juan@gmail.com"
        );

        Habitacion habitacion = new HabitacionSuite(
                101,
                200000,
                4,
                true
        );

        Reserva reserva = new Reserva(
                1,
                LocalDate.now(),
                LocalDate.now().plusDays(3),
                huesped,
                habitacion,
                new TarifaTemporadaAlta()
        );

        ServicioAdicional spa = new ServicioAdicional(
                1,
                "Spa",
                50000
        );

        reserva.agregarServicio(spa);

        reserva.confirmar();

        Factura factura = new Factura(1, reserva);

        factura.generarFactura();

        factura.mostrarDetalle();
    }
}