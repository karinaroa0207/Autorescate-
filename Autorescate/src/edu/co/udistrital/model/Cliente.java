package edu.co.udistrital.model;

public class Cliente {
    private String documento;
    private String nombre;
    private String telefono;
    private String placaVehiculo;
    private String modeloVehiculo;

    public Cliente(String documento, String nombre, String telefono, String placaVehiculo, String modeloVehiculo) {
        this.documento = documento;
        this.nombre = nombre;
        this.telefono = telefono;
        this.placaVehiculo = placaVehiculo;
        this.modeloVehiculo = modeloVehiculo;
    }

    public String getNombre() { return nombre; }
    public String getPlacaVehiculo() { return placaVehiculo; }
}