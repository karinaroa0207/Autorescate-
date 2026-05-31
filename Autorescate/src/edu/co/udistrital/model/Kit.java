package edu.co.udistrital.model;

public class Kit {
    private String codigo;
    private boolean requiereRevision;

    public Kit(String codigo) {
        this.codigo = codigo;
        this.requiereRevision = true;
    }

    public String getCodigo() { 
        return codigo; 
    }
    
    public boolean isRequiereRevision() { 
        return requiereRevision; 
    }
    
    public void setRequiereRevision(boolean requiereRevision) { 
        this.requiereRevision = requiereRevision; 
    }
}