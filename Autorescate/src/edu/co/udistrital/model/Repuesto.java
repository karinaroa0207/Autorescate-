package edu.co.udistrital.model;

public class Repuesto {
    
    private String codigoRepuesto;
    private String nombre;
    private double costo;
    private int stockDisponible;

    public Repuesto(String codigoRepuesto, String nombre, double costo, int stockInicial) {
        this.codigoRepuesto = codigoRepuesto;
        this.nombre = nombre;
        this.costo = costo;
        this.stockDisponible = stockInicial;
    }

    public Repuesto(String codigoRepuesto, String nombre, double costo) {
        this.codigoRepuesto = codigoRepuesto;
        this.nombre = nombre;
        this.costo = costo;
        this.stockDisponible = 1;
    }

    public void abastecer(int cantidad) {
        if (cantidad > 0) {
            this.stockDisponible += cantidad;
        }
    }
    
    public boolean consumir(int cantidad) {
        if (cantidad > 0 && this.stockDisponible >= cantidad) {
            this.stockDisponible -= cantidad;
            return true;
        }
        return false;
    }

    public String getCodigoRepuesto() {
        return codigoRepuesto;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public int getStockDisponible() {
        return stockDisponible;
    }

    public void setStockDisponible(int stockDisponible) {
        this.stockDisponible = stockDisponible;
    }
    
    @Override
    public String toString() {
        return "[" + codigoRepuesto + "] " + nombre + " - Costo: $" + costo + " (Stock: " + stockDisponible + ")";
    }
}