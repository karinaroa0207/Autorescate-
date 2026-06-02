package edu.co.udistrital.model;

public class Tecnico implements Clonable<Tecnico> {

    private String identificacion;
    private String nombre;
    private String especialidad;
    private String zona;
    private EstadoTecnico estado;
    private boolean libre;
    private Kit kit;

    public Tecnico(String identificacion, String nombre, String especialidad) {
        this(identificacion, nombre, especialidad, "General");
    }

    public Tecnico(String identificacion, String nombre, String especialidad, String zona) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.zona = zona;
        this.estado = EstadoTecnico.DISPONIBLE;
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

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public EstadoTecnico getEstado() {
        return estado;
    }

    public boolean isLibre() {
        return libre;
    }

    public void setLibre(boolean libre) {
        this.libre = libre;
        this.estado = libre ? EstadoTecnico.DISPONIBLE : EstadoTecnico.ASIGNADO;
        kit = null;
    }

    public boolean puedeAsignarse() {
        return libre && EstadoTecnico.DISPONIBLE == estado;
    }

    public Kit getKit() {
        return kit;
    }

    public void setKit(Kit kit) {
        this.kit = kit;
    }

    public void asignarTrabajo(Kit kit) {
        this.kit = kit;
        this.libre = false;
        this.estado = EstadoTecnico.ASIGNADO;
    }

    public void revertirAsignar() {
        this.kit = null;
        this.libre = true;
        this.estado = EstadoTecnico.DISPONIBLE;
    }

    public String[] toRowDisponible() {
        return new String[]{
            identificacion != null ? identificacion : "",
            nombre != null ? nombre : "",
            especialidad != null ? especialidad : "",
            zona != null ? zona : ""
        };
    }

    public String[] toRow() {
        return new String[]{
            identificacion != null ? identificacion : "",
            nombre != null ? nombre : "",
            especialidad != null ? especialidad : "",
            zona != null ? zona : "",
            estado != null ? estado.toString() : "",
            isLibre() ? "Si" : "No"
        };
    }

    @Override
    public Tecnico clonar() {
        Tecnico clon = new Tecnico(this.identificacion, this.nombre, this.especialidad, this.zona);
        clon.libre = this.libre;
        clon.estado = this.estado;
        if (this.kit != null) {
            clon.kit = this.kit.clonar();
        } else {
            clon.kit = null;
        }
        return clon;
    }
}
