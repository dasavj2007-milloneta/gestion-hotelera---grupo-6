package com.hotel.interfaces;

import com.hotel.modelo.Habitacion;

public interface CalculadoraTarifa {
    double calcular(Habitacion habitacion, int dias);
}