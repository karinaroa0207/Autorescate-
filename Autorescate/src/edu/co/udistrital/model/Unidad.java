package edu.co.udistrital.model;

import java.util.UUID;

public abstract class Unidad implements Clonable<Unidad> {
    
    private String id;
    private EstadoUnidad estado;
    private String zona;
    private boolean disponible;

    public Unidad(String zona) {
        this.id = UUID.randomUUID().toString();
        this.zona = zona;
        this.estado = EstadoUnidad.DISPONIBLE;
        this.disponible = true;
    }

    public abstract String getTipo();

    public String getId() { 
        return id; 
    }
    
    public EstadoUnidad getEstado() { 
        return estado; 
    }
    
    public void setEstado(EstadoUnidad estado) { 
        this.estado = estado;
        if (EstadoUnidad.MANTENIMIENTO == estado || EstadoUnidad.OCUPADA == estado) {
            this.disponible = false;
        } else if (EstadoUnidad.DISPONIBLE == estado) {
            this.disponible = true;
        }
    }
    
    public String getZona() { 
        return zona; 
    }

    public void setZona(String zona) {
        this.zona = zona;
    }        
    
    public boolean isDisponible() { 
        return disponible; 
    }
    
    public void setDisponible(boolean disponible) {
        if (EstadoUnidad.MANTENIMIENTO == estado && disponible) {
            return;
        }
        this.disponible = disponible;
        this.estado = disponible ? EstadoUnidad.DISPONIBLE : EstadoUnidad.OCUPADA;
    }

    public boolean puedeAsignarse() {
        return disponible && !(EstadoUnidad.MANTENIMIENTO == estado) && !(EstadoUnidad.OCUPADA == estado);
    }

    public void revertirAsignacion() {
        setDisponible(true);
    }
    
    public Object[] toRowDisponible() {
        return new Object[]{id, zona, getTipo()};
    }
    
    public Object[] toRow() {
        return new Object[]{id, getTipo(), zona, estado, puedeAsignarse() ? "Si" : "No"};
    }
}
