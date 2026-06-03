package edu.co.udistrital.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public String[] toRowEjecucion() {
        return new String[]{
            String.valueOf(id),
            getCliente() != null ? getCliente() : "",
            unidadAsignada != null ? unidadAsignada.getTipo().getDescripcion() : "Sin asignar",
            tecnicoAsignado != null ? tecnicoAsignado.getNombre() : "Sin asignar",
            kitAsignado != null ? kitAsignado.getCodigo() : "Sin kit",
            repuestoAsignado != null ? repuestoAsignado.getCodigoRepuesto() : "Sin repuesto",
            estado != null ? estado.toString() : ""
        };
    }

    public String[] toRowPendiente() {
        return new String[]{
            String.valueOf(id),
            getCliente() != null ? getCliente() : "",
            tipoServicio != null ? tipoServicio : "",
            zona != null ? zona : "",
            String.valueOf(prioridad),
            estado != null ? estado.toString() : ""
        };
    }

    public String[] toRowCierre() {
        return new String[]{
            String.valueOf(id),
            getCliente() != null ? getCliente() : "",
            tipoServicio != null ? tipoServicio : "",
            unidadAsignada != null ? unidadAsignada.getTipo().getDescripcion() : "Ninguna",
            tecnicoAsignado != null ? tecnicoAsignado.getNombre() : "No asignado",
            kitAsignado != null ? kitAsignado.getCodigo() : "Sin kit",
            repuestoAsignado != null ? repuestoAsignado.getCodigoRepuesto() : "Sin repuesto",
            fechaCierre != null ? fechaCierre.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) : ""
        };
    }

    public String[] toCSVRow() {
        return new String[]{
            String.valueOf(id),
            cliente != null ? cliente.getDocumento() : "",
            cliente != null ? cliente.getNombre() : getCliente() != null ? getCliente() : "",
            cliente != null ? cliente.getTelefono() : "",
            cliente != null ? cliente.getPlacaVehiculo() : "",
            cliente != null ? cliente.getModeloVehiculo() : "",
            descripcion != null ? descripcion : "",
            tipoServicio != null ? tipoServicio : "",
            zona != null ? zona : "",
            String.valueOf(prioridad),
            esCritica ? "Si" : "No",
            estado != null ? estado.toString() : "",
            unidadAsignada != null ? unidadAsignada.getId() : "",
            unidadAsignada != null ? unidadAsignada.getTipo().getDescripcion() : "",
            unidadAsignada != null ? unidadAsignada.getZona() : "",
            tecnicoAsignado != null ? tecnicoAsignado.getIdentificacion() : "",
            tecnicoAsignado != null ? tecnicoAsignado.getNombre() : "",
            tecnicoAsignado != null ? tecnicoAsignado.getEspecialidad() : "",
            tecnicoAsignado != null ? tecnicoAsignado.getZona() : "",
            kitAsignado != null ? kitAsignado.getCodigo() : "Sin kit",
            kitAsignado != null ? kitAsignado.getDescripcion() : "",
            repuestoAsignado != null ? repuestoAsignado.getCodigoRepuesto() : "",
            repuestoAsignado != null ? repuestoAsignado.getNombre() : "",
            fechaRegistro != null ? fechaRegistro.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) : "",
            fechaCierre != null ? fechaCierre.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) : "" // Cambiado para que coincida con el formato visual
        };
    }
}
