package com.hotel.servicio;

import com.hotel.interfaces.CalculadoraTarifa;
import com.hotel.modelo.Habitacion;

public class TarifaRegular implements CalculadoraTarifa {

    @Override
    public double calcular(Habitacion habitacion, int dias) {
        return habitacion.calcularPrecio() * dias;
    }
}