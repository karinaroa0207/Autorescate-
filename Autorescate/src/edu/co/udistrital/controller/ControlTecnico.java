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
        modoRegistro = true;
        vista.getBtnRegistrarTecnico().addActionListener(e -> agregarTecnico());
        vista.getBtnModificarTecnico().addActionListener(e -> cargarParaModificar());
        vista.getBtnEliminarTecnico().addActionListener(e -> eliminarSeleccionado());
        actualizarTabla();
    }

    public void agregarTecnico() {
        String id = vista.getIdTecnico();
        String nombre = vista.getNombreTecnico();
        String especialidad = vista.getEspecialidad();
        String zona = vista.getZonaTecnico().isEmpty() ? "General" : vista.getZonaTecnico();
        if (id.isEmpty() || nombre.isEmpty() || especialidad.isEmpty()) {
            vMensajes.mostrarMensajeError("El tecnico debe tener identificacion, nombre y especialidad.", "Error Campo");
            return;
        }

        if (modoRegistro) {
            if (gestor.buscarTecnicoPorId(id) != null) {
                vMensajes.mostrarMensaje("Identificacion ya existe", "Info");
                return;
            }
            Tecnico t = new Tecnico(id, nombre, especialidad, zona);
            gestor.agregarTecnico(t);
            vMensajes.mostrarMensaje("Tecnico " + nombre + " registrado.", "Exito");
        } else {
            if (!gestor.modificarTecnico(id, nombre, especialidad, zona)) {
                vMensajes.mostrarMensaje("No se pudo modificar el tecnico.", "Error");
                return;
            }
            vMensajes.mostrarMensaje("Tecnico " + nombre + " actualizado.", "Info");
        }

        vista.limpiarFormularioTecnicos();
        vista.modoRegistro();
        modoRegistro = true;
        actualizarTabla();
    }

    public void cargarParaModificar() {
        String idSeleccionado = vista.getIdTecnicoSeleccionadoEnTabla();

        if (idSeleccionado == null || idSeleccionado.isEmpty()) {
            vMensajes.mostrarMensaje("Seleccione un tecnico de la tabla para modificar.", "Info");
            return;
        }
        Tecnico t = gestor.buscarTecnicoPorId(idSeleccionado);
        if (t != null) {
            vista.setIdTecnico(t.getIdentificacion());
            vista.setNombreTecnico(t.getNombre());
            vista.setEspecialidad(t.getEspecialidad());
            vista.setZonaTecnico(t.getZona());
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

        if (gestor.eliminarTecnico(idSeleccionado) != null) {
            vMensajes.mostrarMensaje("Tecnico eliminado con exito", "Info");
            actualizarTabla();
            return;
        }
        vMensajes.mostrarMensaje(
                "Tecnico esta asignado o no existe. Debe estar disponible para eliminar.",
                "Info"
        );
    }
    
    public void actualizarTabla() {
        TablaUtils.limpiarTabla(vista.getTablaTecnicos());
        Lista<Tecnico> tecs = gestor.obtenerTodosLosTecnicos();
        for (int i = 0; i < tecs.size(); i++) {
            Tecnico t = tecs.get(i);
            TablaUtils.agregarFila(vista.getTablaTecnicos(), t.toRow());
        }
    }
}
