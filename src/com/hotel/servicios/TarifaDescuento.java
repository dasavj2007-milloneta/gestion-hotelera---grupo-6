package com.hotel.servicio;

import com.hotel.interfaces.CalculadoraTarifa;
import com.hotel.modelo.Habitacion;

public class TarifaDescuento implements CalculadoraTarifa {

    @Override
    public double calcular(Habitacion habitacion, int dias) {

        double total = habitacion.calcularPrecio() * dias;

        return total * 0.9;
    }
}