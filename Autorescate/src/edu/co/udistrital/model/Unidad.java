package edu.co.udistrital.model;

import java.util.UUID;

public abstract class Unidad {
    
    private String id;
    private String estado;
    private String zona;
    private boolean disponible;

    public Unidad(String zona) {
        this.id = UUID.randomUUID().toString();
        this.zona = zona;
        this.estado = "Disponible";
        this.disponible = true;
    }

    public abstract String getTipo();

    public String getId() { 
        return id; 
    }
    
    public String getEstado() { 
        return estado; 
    }
    
    public void setEstado(String estado) { 
        this.estado = estado;
        if ("Mantenimiento".equals(estado) || "Asignada".equals(estado)) {
            this.disponible = false;
        } else if ("Disponible".equals(estado)) {
            this.disponible = true;
        }
    }
    
    public String getZona() { 
        return zona; 
    }
    
    public boolean isDisponible() { 
        return disponible; 
    }
    
    public void setDisponible(boolean disponible) {
        if ("Mantenimiento".equals(this.estado) && disponible) {
            return;
        }
        this.disponible = disponible;
        this.estado = disponible ? "Disponible" : "Asignada";
    }

    public boolean puedeAsignarse() {
        return disponible && !"Mantenimiento".equals(estado) && !"Asignada".equals(estado);
    }
}
