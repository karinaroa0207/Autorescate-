package edu.co.udistrital.model;

import java.time.LocalDateTime;

public class Solicitud implements MiComparable<Solicitud> {

    private static int consecutivo = 1;

    private String id;
    private Cliente cliente;
    private String clienteTexto;
    private String descripcion;
    private String zona;
    private String tipoServicio;
    private int prioridad;
    private boolean esCritica;
    private EstadoSolicitud estado;
    private Unidad unidadAsignada;
    private Tecnico tecnicoAsignado;
    private Kit kitAsignado;
    private Repuesto repuestoAsignado;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaCierre;

    public Solicitud(String cliente, String descripcion, boolean esCritica) {
        this(cliente, descripcion, "General", "Asistencia general", esCritica ? 80 : 0);
    }

    public Solicitud(String cliente, String descripcion, String zona, String tipoServicio, int prioridad) {
        this(null, cliente, descripcion, zona, tipoServicio, prioridad);
    }

    public Solicitud(Cliente cliente, String descripcion, String zona, String tipoServicio, int prioridad) {
        this(cliente, null, descripcion, zona, tipoServicio, prioridad);
    }

    private Solicitud(Cliente cliente, String clienteTexto, String descripcion, String zona, String tipoServicio, int prioridad) {
        this.id = String.format("SOL-%03d", consecutivo++);
        this.cliente = cliente;
        this.clienteTexto = clienteTexto;
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
        if (cliente != null) {
            return cliente.toString();
        }
        return clienteTexto;
    }

    public Cliente getClienteObjeto() {
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
        return kitAsignado;
    }

    public Repuesto getRepuesto() {
        return repuestoAsignado;
    }

    public void asignarRecursos(Unidad unidad, Tecnico tecnico) {
        asignarRecursos(unidad, tecnico, tecnico != null ? tecnico.getKit() : null, null);
    }

    public void asignarRecursos(Unidad unidad, Tecnico tecnico, Kit kit) {
        asignarRecursos(unidad, tecnico, kit, null);
    }

    public void asignarRecursos(Unidad unidad, Tecnico tecnico, Kit kit, Repuesto repuesto) {
        this.unidadAsignada = unidad;
        this.tecnicoAsignado = tecnico;
        this.kitAsignado = kit;
        this.repuestoAsignado = repuesto;
        this.estado = EstadoSolicitud.EN_PROCESO;
    }

    public void marcarComoAtendida() {
        if (unidadAsignada == null || tecnicoAsignado == null || (kitAsignado == null && repuestoAsignado == null)) {
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
        this.kitAsignado = null;
        this.repuestoAsignado = null;
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

}
