package edu.co.udistrital.controller;

import edu.co.udistrital.model.CentroOperaciones;
import edu.co.udistrital.model.EstadoUnidad;
import edu.co.udistrital.model.Lista;
import edu.co.udistrital.model.Unidad;
import edu.co.udistrital.model.UnidadFactory;
import edu.co.udistrital.view.PanelRecursos;
import edu.co.udistrital.view.TablaUtils;

public class ControlUnidades {

    private PanelRecursos vista;
    private CentroOperaciones gestor;
    private VisualizadorMensajes vMensajes;
    private boolean modoRegistro;

    public ControlUnidades(PanelRecursos vista, CentroOperaciones gestor, VisualizadorMensajes vMensajes) {
        this.vista = vista;
        this.gestor = gestor;
        this.vMensajes = vMensajes;
        modoRegistro = true;
        vista.getBtnRegistrarUnidad().addActionListener(e -> guardarUnidad());
        vista.getBtnModificarUnidad().addActionListener(e -> cargarParaModificar());
        vista.getBtnEliminarUnidad().addActionListener(e -> eliminarSeleccionado());
        actualizarTabla();
    }

    public void guardarUnidad() {
        String zona = vista.getZonaUnidad().isEmpty() ? "General" : vista.getZonaUnidad();
        String tipo = vista.getTipoUnidad();
        String estado = vista.getEstadoUnidad();

        if (tipo.isEmpty()) {
            vMensajes.mostrarMensajeError("Debe seleccionar un tipo de unidad.", "Error Campo");
            return;
        }
        if (modoRegistro) {
            Unidad aux = UnidadFactory.crearUnidad(tipo, zona);
            aux.setEstado(EstadoUnidad.valueOf(estado));
            gestor.agregarUnidad(aux);
            vista.limpiarFormularioUnidades();
            vMensajes.mostrarMensaje("Unidad tipo " + tipo + " registrada con éxito.", "Éxito");            
            vMensajes.agregarMensaje("Unidad tipo " + tipo + " registrada con éxito.");            
        } else {
            if(gestor.modificarUnidad(vista.getIdUnidad(), zona, EstadoUnidad.valueOf(estado))) {
                vista.limpiarFormularioUnidades();
                vMensajes.mostrarMensaje("Unidad tipo " + tipo + " actualizada.", "Info");                                
                vMensajes.agregarMensaje("Unidad tipo " + tipo + " actualizada.");                                
            }                       
        }        
        vista.modoRegistro();
        modoRegistro = true;
        actualizarTabla();
    }

    public void cargarParaModificar() {
        String idSeleccionado = vista.getIdUnidadSeleccionadaEnTabla();

        if (idSeleccionado == null || idSeleccionado.isEmpty()) {
            vMensajes.mostrarMensaje("Seleccione una unidad de la tabla para modificar.", "Info");
            return;
        }
        Unidad u = gestor.buscarUnidad(idSeleccionado);
        if (u != null) {
            vista.setZonaUnidad(u.getZona());
            vista.setTipoUnidad(u.getTipo().toString());
            vista.setEstadoUnidad(u.getEstado().toString());
            vista.setId(idSeleccionado);
            vista.modoEdicion();
            modoRegistro = false;
        }
    }

    public void eliminarSeleccionado() {
        String idSeleccionado = vista.getIdUnidadSeleccionadaEnTabla();

        if (idSeleccionado == null || idSeleccionado.isEmpty()) {
            vMensajes.mostrarMensaje("Seleccione una unidad de la tabla para eliminar.", "Info");
            return;
        }

        boolean exito = gestor.eliminarUnidad(idSeleccionado);
        if (exito) {
            vMensajes.mostrarMensaje("Unidad eliminada del sistema con éxito.", "Info");
            vMensajes.agregarMensaje("Unidad eliminada del sistema con éxito.");
            actualizarTabla();
            return;
        }
        vMensajes.mostrarMensaje(
                "La unidad está en servicio activo o no existe. Debe estar Disponible o en Mantenimiento para eliminarse.",
                "Info"
        );
    }

    public void actualizarTabla() {
        TablaUtils.limpiarTabla(vista.getTablaUnidades());
        Lista<Unidad> lista = gestor.getUnidades();
        for (int i = 0; i < lista.size(); i++) {
            Unidad obj = lista.get(i);
            TablaUtils.agregarFila(vista.getTablaUnidades(), FilasTabla.unidad(obj));
        }
    }
}
