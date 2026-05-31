package edu.co.udistrital.model;

public class Operacion {
    
    private String tipo;
    private Solicitud solicitud;
    private Unidad unidad;
    private Tecnico tecnico;
    private String detalle;

    public Operacion(String tipo, Solicitud solicitud, Unidad unidad, Tecnico tecnico) {
        this.tipo = tipo;
        this.solicitud = solicitud;
        this.unidad = unidad;
        this.tecnico = tecnico;
        this.detalle = solicitud == null ? tipo : tipo + " solicitud #" + solicitud.getId();
    }

    public String getTipo() { 
        return tipo; 
    }
    
    public Solicitud getSolicitud() { 
        return solicitud; 
    }
    
    public Unidad getUnidad() { 
        return unidad; 
    }
    
    public Tecnico getTecnico() { 
        return tecnico; 
    }

    public String getDetalle() {
        return detalle;
    }
}
