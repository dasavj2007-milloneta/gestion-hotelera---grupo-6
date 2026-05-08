package com.hotel.servicios;

import com.hotel.interfaces.CalculadoraTarifa;
import com.hotel.modelo.Habitacion;

public class TarifaTemporadaAlta implements CalculadoraTarifa {

    @Override
    public double calcular(Habitacion habitacion, int dias) {

        double total = habitacion.calcularPrecio() * dias;

        return total * 1.3;
    }
}