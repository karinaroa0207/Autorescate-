package edu.co.udistrital.model;

public class Operacion {

    private TipoOperacion tipo;
    private Solicitud solicitud;
    private Unidad unidad;
    private Tecnico tecnico;
    private Kit kit;
    private Repuesto repuesto;
    private String detalle;
    private Object estadoAnterior;
    private Object estadoActual;

    public Operacion(TipoOperacion tipo, Solicitud solicitud, Unidad unidad, Tecnico tecnico, Kit kit, String detalle) {
        this.tipo = tipo;
        this.solicitud = solicitud;
        this.unidad = unidad;
        this.tecnico = tecnico;
        this.kit = kit;
        this.detalle = detalle;
    }

    public Operacion(TipoOperacion tipo, Solicitud solicitud, Unidad unidad, Tecnico tecnico, Kit kit, Repuesto repuesto, String detalle) {
        this.tipo = tipo;
        this.solicitud = solicitud;
        this.unidad = unidad;
        this.tecnico = tecnico;
        this.kit = kit;
        this.repuesto = repuesto;
        this.detalle = detalle;
    }

    public Operacion(TipoOperacion tipo, Solicitud solicitud, Unidad unidad, Tecnico tecnico, Kit kit, String detalle, Object estadoAnterior) {
        this.tipo = tipo;
        this.solicitud = solicitud;
        this.unidad = unidad;
        this.tecnico = tecnico;
        this.kit = kit;
        this.detalle = detalle;
        this.estadoAnterior = estadoAnterior;
    }

    public Operacion(TipoOperacion tipo, String detalle, Object estadoAnterior) {
        this.tipo = tipo;
        this.detalle = detalle;
        this.estadoAnterior = estadoAnterior;
    }

    public Operacion(TipoOperacion tipo, String detalle, Object estadoAnterior, Object estadoActual) {
        this.tipo = tipo;
        this.detalle = detalle;
        this.estadoAnterior = estadoAnterior;
        this.estadoActual = estadoActual;
    }

    public TipoOperacion getTipo() {
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

    public Object getEstadoAnterior() {
        return estadoAnterior;
    }

    public Object getEstadoActual() {
        return estadoActual;
    }

    public Kit getKit() {
        return kit;
    }

    public Repuesto getRepuesto() {
        return repuesto;
    }

}
