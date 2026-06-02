package edu.co.udistrital.model;

import java.util.UUID;

public class Kit implements Clonable<Kit> {

    private String id;
    private String codigo;
    private String descripcion;
    private EstadoKit estado;

    public Kit(String codigo, String descripcion) {
        this.id = UUID.randomUUID().toString();
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.estado = EstadoKit.DISPONIBLE;
    }

    public String getCodigo() {
        return codigo;
    }

    public boolean isRequiereRevision() {
        return estado == EstadoKit.EN_REVISION;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getId() {
        return id;
    }

    public EstadoKit isEstado() {
        return estado;
    }

    public void setEstado(EstadoKit estado) {
        this.estado = estado;
    }
    
    public void revertirAsignacion() {
        if(estado == EstadoKit.ASIGNADO) {
            estado = EstadoKit.DISPONIBLE;
        }
    }

    public Object[] toRowDisponible() {
        return new Object[]{id, codigo, descripcion};
    }
    
    public Object[] toRow() {
        return new Object[]{id, codigo, descripcion, estado};
    }

    @Override
    public Kit clonar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
