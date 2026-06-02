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

    public ControlTecnico(PanelTecnicos vista, CentroOperaciones gestor, VisualizadorMensajes vMensajes) {
        this.vista = vista;
        this.gestor = gestor;
        this.vMensajes = vMensajes;
        vista.getBtnRegistrarTecnico().addActionListener(e -> agregarTecnico());
    }

    public void agregarTecnico() {
        String id = vista.getIdTecnico();
        String nombre = vista.getNombreTecnico();
        String especialidad = vista.getEspecialidad();
        String zona = vista.getZonaTecnico().isEmpty() ? "General": vista.getZonaTecnico();
        if (id.isEmpty() || nombre.isEmpty() || especialidad.isEmpty()) {
            vMensajes.mostrarMensajeError("El tecnico debe tener identificacion, nombre y especialidad.", "Error Campo");
            return;
        }
        if (gestor.buscarTecnicoPorId(id) != null) {
            vMensajes.mostrarMensaje("Identificacion ya existe", "Info");
            return;
        }
        Tecnico t = new Tecnico(id, nombre, especialidad, zona);
        gestor.agregarTecnico(t);
        TablaUtils.agregarFila(vista.getTablaTecnicos(), t.toRow());
        vMensajes.mostrarMensaje("Tecnico " + nombre + " registrado.", "Exito");
    }

    public boolean eliminarTecnico(String id) {
        Tecnico t = gestor.buscarTecnicoPorId(id);
        if (t != null && t.getEstado() == EstadoTecnico.DISPONIBLE) {
            if (gestor.eliminarTecnico(id) != null) {
                vMensajes.mostrarMensaje("Tecnico eliminado con exito", "Info");
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
            TablaUtils.agregarFila(vista.getTablaTecnicos(), t.toRow());
        }
    }
}
