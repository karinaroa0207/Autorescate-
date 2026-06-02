package edu.co.udistrital.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Solicitud implements MiComparable<Solicitud> {

    private String id;
    private String cliente;
    private String descripcion;
    private String zona;
    private String tipoServicio;
    private int prioridad;
    private boolean esCritica;
    private EstadoSolicitud estado;
    private Unidad unidadAsignada;
    private Tecnico tecnicoAsignado;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaCierre;

    public Solicitud(String cliente, String descripcion, boolean esCritica) {
        this(cliente, descripcion, "General", "Asistencia general", esCritica ? 80 : 0);
    }

    public Solicitud(String cliente, String descripcion, String zona, String tipoServicio, int prioridad) {
        this.id = UUID.randomUUID().toString();
        this.cliente = cliente;
        this.descripcion = descripcion;
        this.zona = zona;
        this.tipoServicio = tipoServicio;
        this.prioridad = prioridad;
        this.esCritica = prioridad > 0;
        this.estado = EstadoSolicitud.PENDIENTE;
        this.fechaRegistro = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getCliente() {
        return cliente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getZona() {
        return zona;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public boolean isEsCritica() {
        return esCritica;
    }

    public EstadoSolicitud getEstado() {
        return estado;
    }

    public Unidad getUnidadAsignada() {
        return unidadAsignada;
    }

    public Tecnico getTecnicoAsignado() {
        return tecnicoAsignado;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public LocalDateTime getFechaCierre() {
        return fechaCierre;
    }

    public Kit getKit() {
        return tecnicoAsignado.getKit();
    }

    public void asignarRecursos(Unidad unidad, Tecnico tecnico) {
        this.unidadAsignada = unidad;
        this.tecnicoAsignado = tecnico;
        this.estado = EstadoSolicitud.EN_PROCESO;
    }

    public void marcarComoAtendida() {
        if (unidadAsignada == null || tecnicoAsignado == null) {
            return;
        }
        tecnicoAsignado.setLibre(true);
        unidadAsignada.setDisponible(true);
        this.estado = EstadoSolicitud.ATENDIDA;
        this.fechaCierre = LocalDateTime.now();
    }

    public void revertirAsignacion() {
        this.unidadAsignada = null;
        this.tecnicoAsignado = null;
        this.estado = EstadoSolicitud.PENDIENTE;
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

    public Object[] toRowEjecucion() {
        return new Object[]{
            id,
            cliente,
            (unidadAsignada != null) ? unidadAsignada.getTipo() : "Sin asignar",
            (tecnicoAsignado != null) ? tecnicoAsignado.getNombre() : "Sin asignar",
            estado
        };
    }

    public Object[] toRowPendiente() {
        return new Object[]{
            id,
            cliente,
            tipoServicio,
            zona,
            prioridad,
            estado
        };
    }

    public Object[] toRowCierre() {
        return new Object[]{
            id,
            cliente,
            tipoServicio,
            unidadAsignada.getTipo(),
            tecnicoAsignado.getNombre(),
            fechaCierre.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
        };
    }

    public String[] toCSVRow() {
        return new String[]{
            String.valueOf(this.id),
            this.cliente,
            this.descripcion,
            this.tipoServicio,
            this.zona,
            String.valueOf(this.prioridad),
            this.unidadAsignada != null ? this.unidadAsignada.getTipo() : "Ninguna",
            this.tecnicoAsignado != null ? this.tecnicoAsignado.getNombre() : "No asignado",
            this.fechaCierre != null ? this.fechaCierre.toString() : ""
        };
    }
}
