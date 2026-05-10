package com.hotel.modelo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import com.hotel.interfaces.Reservable;
import com.hotel.interfaces.Facturable;
import com.hotel.interfaces.CalculadoraTarifa;

public class Reserva implements Reservable, Facturable {
    
    private int id;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado;
    private Huesped huesped;
    private Habitacion habitacion;
    private List<ServicioAdicional> servicios;
    private CalculadoraTarifa calculadoraTarifa;
    private String codigoDescuento;
    private double porcentajeDescuento;

    public Reserva(int id, LocalDate fechaInicio,
                   LocalDate fechaFin,
                   Huesped huesped,
                   Habitacion habitacion,
                   CalculadoraTarifa calculadoraTarifa) {

        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.huesped = huesped;
        this.habitacion = habitacion;
        this.calculadoraTarifa = calculadoraTarifa;
        this.servicios = new ArrayList<>();
        this.codigoDescuento = null;
        this.porcentajeDescuento = 0.0;
    }

    @Override
    public double calcularTotal() {
        // Calcular precio base de la habitación sin descuento
        double totalHabitacion = habitacion.calcularPrecio() * calcularDias();

        // Sumar servicios
        double totalServicios = 0;
        for (ServicioAdicional servicio : servicios) {
            totalServicios += servicio.calcularCosto();
        }

        // Total sin descuento
        double totalSinDescuento = totalHabitacion + totalServicios;

        // Aplicar descuento si existe
        if (porcentajeDescuento > 0) {
            return totalSinDescuento * (1 - (porcentajeDescuento / 100));
        }

        return totalSinDescuento;
    }

    @Override
    public void confirmar() {
        estado = "CONFIRMADA";
        System.out.println("Reserva confirmada");
    }

    @Override
    public void cancelar() {
        estado = "CANCELADA";
        System.out.println("Reserva cancelada");
    }

    public void checkIn() {
        System.out.println("Check-in realizado");
    }

    public void checkOut() {
        System.out.println("Check-out realizado");
    }

    public int calcularDias() {
        return (int) ChronoUnit.DAYS.between(fechaInicio, fechaFin);
    }

    public void agregarServicio(ServicioAdicional servicio) {
        servicios.add(servicio);
    }

    public void removerServicio(ServicioAdicional servicio) {
        servicios.remove(servicio);
    }

    public void setCalculadoraTarifa(CalculadoraTarifa calculadoraTarifa) {
        this.calculadoraTarifa = calculadoraTarifa;
    }

    public boolean estaActiva() {
        return estado != null && estado.equals("CONFIRMADA");
    }

    public Huesped getHuesped() {
        return huesped;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public CalculadoraTarifa getCalculadoraTarifa() {
        return calculadoraTarifa;
    }

    public List<ServicioAdicional> getServicios() {
        return servicios;
    }

    public void setCodigoDescuento(String codigoDescuento) {
        this.codigoDescuento = codigoDescuento;
    }

    public String getCodigoDescuento() {
        return codigoDescuento;
    }

    public void setPorcentajeDescuento(double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }
}