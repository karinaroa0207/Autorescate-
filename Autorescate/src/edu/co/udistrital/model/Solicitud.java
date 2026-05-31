package edu.co.udistrital.model;

import java.time.LocalDateTime;

public class Solicitud implements Comparable<Solicitud> {
    
    private static int contadorId = 1;
    private int id;
    private String cliente;
    private String descripcion;
    private String zona;
    private String tipoServicio;
    private int prioridad;
    private boolean esCritica;
    private String estado;
    private Unidad unidadAsignada;
    private Tecnico tecnicoAsignado;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaCierre;

    public Solicitud(String cliente, String descripcion, boolean esCritica) {
        this(cliente, descripcion, "General", "Asistencia general", esCritica ? 80 : 0);
    }

    public Solicitud(String cliente, String descripcion, String zona, String tipoServicio, int prioridad) {
        this.id = contadorId++;
        this.cliente = cliente;
        this.descripcion = descripcion;
        this.zona = zona;
        this.tipoServicio = tipoServicio;
        this.prioridad = prioridad;
        this.esCritica = prioridad > 0;
        this.estado = "Pendiente";
        this.fechaRegistro = LocalDateTime.now();
    }

    public int getId() { return id; }
    public String getCliente() { return cliente; }
    public String getDescripcion() { return descripcion; }
    public String getZona() { return zona; }
    public String getTipoServicio() { return tipoServicio; }
    public int getPrioridad() { return prioridad; }
    public boolean isEsCritica() { return esCritica; }
    public String getEstado() { return estado; }
    public Unidad getUnidadAsignada() { return unidadAsignada; }
    public Tecnico getTecnicoAsignado() { return tecnicoAsignado; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public LocalDateTime getFechaCierre() { return fechaCierre; }
    
    public void asignarRecursos(Unidad unidad, Tecnico tecnico) {
        this.unidadAsignada = unidad;
        this.tecnicoAsignado = tecnico;
        this.estado = "En ejecucion";
    }

    public void marcarComoAtendida() {
        if (unidadAsignada == null || tecnicoAsignado == null) {
            return;
        }
        this.estado = "Atendida";
        this.fechaCierre = LocalDateTime.now();
    }

    public void revertirAsignacion() {
        this.unidadAsignada = null;
        this.tecnicoAsignado = null;
        this.estado = "Pendiente";
        this.fechaCierre = null;
    }

    @Override
    public int compareTo(Solicitud otra) {
        if (this.prioridad > otra.prioridad) {
            return -1;
        } else if (this.prioridad < otra.prioridad) {
            return 1;
        }
        return 0;
    }
}
