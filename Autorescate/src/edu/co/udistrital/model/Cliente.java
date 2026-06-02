package edu.co.udistrital.model;

import java.util.UUID;

public class Cliente {
    private String id;
    private String documento;
    private String nombre;
    private String telefono;
    private String placaVehiculo;
    private String modeloVehiculo;

    public Cliente(String documento, String nombre, String telefono, String placaVehiculo, String modeloVehiculo) {
        this.id = UUID.randomUUID().toString();
        this.documento = documento;
        this.nombre = nombre;
        this.telefono = telefono;
        this.placaVehiculo = placaVehiculo;
        this.modeloVehiculo = modeloVehiculo;
    }

    public String getId() { return id; }
    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public String getPlacaVehiculo() { return placaVehiculo; }
    public void setPlacaVehiculo(String placaVehiculo) { this.placaVehiculo = placaVehiculo; }
    
    public String getModeloVehiculo() { return modeloVehiculo; }
    public void setModeloVehiculo(String modeloVehiculo) { this.modeloVehiculo = modeloVehiculo; }

    public String[] toRow() {
        return new String[]{
            id != null ? id : "",
            documento != null ? documento : "",
            nombre != null ? nombre : "",
            telefono != null ? telefono : "",
            placaVehiculo != null ? placaVehiculo : "",
            modeloVehiculo != null ? modeloVehiculo : ""
        };
    }
}