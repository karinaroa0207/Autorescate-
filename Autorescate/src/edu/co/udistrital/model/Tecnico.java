package edu.co.udistrital.model;

public class Tecnico {
    
    private String identificacion;
    private String nombre;
    private String especialidad;
    private String zona;
    private String estado;
    private boolean libre;

    public Tecnico(String identificacion, String nombre, String especialidad) {
        this(identificacion, nombre, especialidad, "General");
    }

    public Tecnico(String identificacion, String nombre, String especialidad, String zona) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.zona = zona;
        this.estado = "Disponible";
        this.libre = true; 
    }

    public String getIdentificacion() { 
        return identificacion; 
    }
    
    public String getNombre() { 
        return nombre; 
    }
    
    public String getEspecialidad() { 
        return especialidad; 
    }

    public String getZona() {
        return zona;
    }

    public String getEstado() {
        return estado;
    }
    
    public boolean isLibre() { 
        return libre; 
    }
    
    public void setLibre(boolean libre) { 
        this.libre = libre; 
        this.estado = libre ? "Disponible" : "Asignado";
    }

    public boolean puedeAsignarse() {
        return libre && "Disponible".equals(estado);
    }
}
