package edu.co.udistrital.model;

public class Cliente implements Clonable<Cliente> {
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

    public String getDocumento() { return documento; }
    public String getNombre() { return nombre; }
    public String getTelefono() { return telefono; }
    public String getPlacaVehiculo() { return placaVehiculo; }
    public String getModeloVehiculo() { return modeloVehiculo; }

    public void actualizarDatos(String nombre, String telefono, String placaVehiculo, String modeloVehiculo) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.placaVehiculo = placaVehiculo;
        this.modeloVehiculo = modeloVehiculo;
    }

    @Override
    public Cliente clonar() {
        return new Cliente(documento, nombre, telefono, placaVehiculo, modeloVehiculo);
    }

    @Override
    public String toString() {
        String doc = documento != null && !documento.isBlank() ? " (" + documento + ")" : "";
        return (nombre != null ? nombre : "Cliente") + doc;
    }
}
