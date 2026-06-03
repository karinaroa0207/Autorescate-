package edu.co.udistrital.controller;

import edu.co.udistrital.model.CentroOperaciones;
import edu.co.udistrital.model.EstadoTecnico;
import edu.co.udistrital.model.Lista;
import edu.co.udistrital.model.Tecnico;
import edu.co.udistrital.view.PanelTecnicos;
import edu.co.udistrital.view.TablaUtils;

public class ControlTecnico {

    private PanelTecnicos vista;
    private CentroOperaciones gestor;
    private VisualizadorMensajes vMensajes;
    private boolean modoRegistro;

    public ControlTecnico(PanelTecnicos vista, CentroOperaciones gestor, VisualizadorMensajes vMensajes) {
        this.vista = vista;
        this.gestor = gestor;
        this.vMensajes = vMensajes;
        this.modoRegistro = true;
        vista.getBtnRegistrarTecnico().addActionListener(e -> guardarTecnico());
        vista.getBtnModificarTecnico().addActionListener(e -> cargarParaModificar());
        vista.getBtnEliminarTecnico().addActionListener(e -> eliminarSeleccionado());
    }

    public void guardarTecnico() {
        String id = vista.getIdTecnico();
        String nombre = vista.getNombreTecnico();
        String especialidad = vista.getEspecialidad();
        String zona = vista.getZonaTecnico().isEmpty() ? "General" : vista.getZonaTecnico();
        if (id.isEmpty() || nombre.isEmpty() || especialidad.isEmpty()) {
            vMensajes.mostrarMensajeError("El tecnico debe tener identificacion, nombre y especialidad.", "Error Campo");
            return;
        }
        if (modoRegistro) {
            agregarTecnico(id, nombre, especialidad, zona);
        } else {
            modificarTecnico(id, nombre, especialidad, zona);
        }
    }

    public void agregarTecnico(String id, String nombre, String especialidad, String zona) {
        if (gestor.buscarTecnicoPorId(id) != null) {
            vMensajes.mostrarMensaje("Identificacion ya existe", "Info");
            return;
        }
        Tecnico t = new Tecnico(id, nombre, especialidad, zona);
        gestor.agregarTecnico(t);
        vista.limpiarFormulario();
        actualizarTabla();
        vMensajes.mostrarMensaje("Tecnico " + nombre + " registrado.", "Exito");
        vMensajes.agregarMensaje("Tecnico " + nombre + " registrado.");
    }

    public void modificarTecnico(String id, String nombre, String especialidad, String zona) {
        if (gestor.modificarTecnico(id, nombre, especialidad, zona)) {
            vista.limpiarFormulario();
            vista.modoRegistro();
            modoRegistro = true;
            actualizarTabla();
            vMensajes.mostrarMensaje("Tecnico " + nombre + " actualizado.", "Info");
            vMensajes.agregarMensaje("Tecnico " + nombre + " actualizado.");
            return;
        }
        vMensajes.mostrarMensaje("No se encontro el tecnico seleccionado.", "Info");
    }

    public void cargarParaModificar() {
        String idSeleccionado = vista.getIdTecnicoSeleccionadoEnTabla();
        if (idSeleccionado == null || idSeleccionado.isEmpty()) {
            vMensajes.mostrarMensaje("Seleccione un tecnico de la tabla para modificar.", "Info");
            return;
        }
        Tecnico tecnico = gestor.buscarTecnicoPorId(idSeleccionado);
        if (tecnico != null) {
            vista.setIdTecnico(tecnico.getIdentificacion());
            vista.setNombreTecnico(tecnico.getNombre());
            vista.setEspecialidad(tecnico.getEspecialidad());
            vista.setZonaTecnico(tecnico.getZona());
            vista.modoEdicion();
            modoRegistro = false;
        }
    }

    public void eliminarSeleccionado() {
        String idSeleccionado = vista.getIdTecnicoSeleccionadoEnTabla();
        if (idSeleccionado == null || idSeleccionado.isEmpty()) {
            vMensajes.mostrarMensaje("Seleccione un tecnico de la tabla para eliminar.", "Info");
            return;
        }
        if (eliminarTecnico(idSeleccionado)) {
            vista.limpiarFormulario();
            vista.modoRegistro();
            modoRegistro = true;
            actualizarTabla();
        }
    }

    public boolean eliminarTecnico(String id) {
        Tecnico t = gestor.buscarTecnicoPorId(id);
        if (t != null && t.getEstado() == EstadoTecnico.DISPONIBLE) {
            if (gestor.eliminarTecnico(id) != null) {
                vMensajes.mostrarMensaje("Tecnico eliminado con exito", "Info");
                vMensajes.agregarMensaje("Tecnico eliminado con exito");
            }
            return true;
        }
        vMensajes.mostrarMensaje(
                "Tecnico esta asignado, debe estar en estado disponible para eliminar",
                "Info"
        );
        return false;
    }
    
    public void actualizarTabla() {
        TablaUtils.limpiarTabla(vista.getTablaTecnicos());
        Lista<Tecnico> tecs = gestor.obtenerTodosLosTecnicos();
        for (int i = 0; i < tecs.size(); i++) {
            Tecnico t = tecs.get(i);
            TablaUtils.agregarFila(vista.getTablaTecnicos(), FilasTabla.tecnico(t));
        }
    }
}
