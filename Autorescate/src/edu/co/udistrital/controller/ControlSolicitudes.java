package edu.co.udistrital.controller;

import edu.co.udistrital.model.CentroOperaciones;
import edu.co.udistrital.model.Cliente;
import edu.co.udistrital.model.Kit;
import edu.co.udistrital.model.Lista;
import edu.co.udistrital.model.Solicitud;
import edu.co.udistrital.model.Tecnico;
import edu.co.udistrital.model.Unidad;
import edu.co.udistrital.view.PanelAsignacion;
import edu.co.udistrital.view.PanelSolicitudes;
import edu.co.udistrital.view.TablaUtils;

public class ControlSolicitudes {

    private PanelSolicitudes vista;
    private CentroOperaciones modelo;
    private VisualizadorMensajes vMensajes;
    private PanelAsignacion pAsig;

    public ControlSolicitudes(PanelSolicitudes vista, CentroOperaciones modelo, VisualizadorMensajes vMensajes) {
        this.vista = vista;
        this.pAsig = vista.getPanelAsignacion();
        this.modelo = modelo;
        this.vMensajes = vMensajes;
        vista.getBtnRegistrarSolicitud().addActionListener(e -> registrarSolicitud());
        vista.getBtnAsignar().addActionListener(e -> asignarSiguiente());
        vista.getBtnAsignarManual().addActionListener(e
                -> vista.getTabs().setSelectedIndex(1)
        );
        vista.getBtnCerrarSolicitud().addActionListener(e -> cerrarSolicitud());

        vista.getTabTablas().addChangeListener(e -> {
            actualizarTabla();
        });
        vista.getTabs().addChangeListener(e -> {
            int pestañaSeleccionada = vista.getTabs().getSelectedIndex();
            switch (pestañaSeleccionada) {
                case 0:
                    actualizarTabla();
                    break;
                case 1:
                    actualizarAsignacion();
                    break;
                default:
                    break;
            }
        });
        pAsig.getBtnRefrescarDisponibilidad().addActionListener(e -> {
            actualizarAsignacion();
        });
        pAsig.getBtnDespacharServicio().addActionListener(e -> asignarSiguienteManual());
    }

    private void actualizarTabla() {
        int pestañaSeleccionada = vista.getTabTablas().getSelectedIndex();
        switch (pestañaSeleccionada) {
            case 0 ->
                actualizarPendientes();
            case 1 ->
                actualizarEjecucion();
            default ->
                actualizarCerrados();
        }
    }

    public void actualizarRecursos() {
        Solicitud solicitud = modelo.verSiguienteSolicitud();
        Lista<Unidad> u = solicitud != null
                ? modelo.getUnidadesDisponibles(solicitud.getZona())
                : modelo.getUnidadesDisponibles();
        TablaUtils.limpiarTabla(pAsig.getTablaUnidades());
        for (int i = 0; i < u.size(); i++) {
            Unidad s = u.get(i);
            TablaUtils.agregarFila(pAsig.getTablaUnidades(), s.toRowDisponible());
        }
        Lista<Tecnico> t = solicitud != null
                ? modelo.getTecnicosDisponibles(solicitud.getZona())
                : modelo.getTecnicosDisponibles();
        TablaUtils.limpiarTabla(pAsig.getTablaTecnicos());
        for (int i = 0; i < t.size(); i++) {
            Tecnico s = t.get(i);
            TablaUtils.agregarFila(pAsig.getTablaTecnicos(), s.toRowDisponible());
        }
        Lista<Kit> kits = modelo.getKitsDisponibles();
        TablaUtils.limpiarTabla(pAsig.getTablaKits());
        for (int i = 0; i < kits.size(); i++) {
            Kit kit = kits.get(i);
            TablaUtils.agregarFila(pAsig.getTablaKits(), kit.toRowDisponible());
        }
    }

    public void registrarSolicitud() {
        Cliente cliente = vista.getClienteSeleccionado();
        String descripcion = vista.getDescripcion();
        String zona = valorPorDefecto(vista.getZonaSolicitud(), "General");
        String servicio = vista.getTipoServicio();
        int prioridad = parseEntero(vista.getPrioridad(), 0);

        if (cliente == null || descripcion.isEmpty()) {
            vMensajes.agregarMensaje("La solicitud debe tener un cliente registrado y descripcion.");
            vMensajes.mostrarMensajeWarning("Primero registre o seleccione un cliente para la solicitud.", "Cliente requerido");
            return;
        }
        Solicitud s = new Solicitud(cliente, descripcion, zona, servicio, prioridad);
        modelo.registrarSolicitud(s);
        actualizarPendientes();
        vista.limpiarFormulario();
        vMensajes.agregarMensaje("Solicitud registrada. Prioridad " + prioridad + ".");
    }

    public void asignarSiguiente() {
        if (modelo.asignarRecurso()) {
            actualizarTabla();
            vMensajes.agregarMensaje("Se asigno la siguiente solicitud con unidad, tecnico y kit listo.");
            vMensajes.mostrarMensaje("Se asigno la siguiente solicitud con unidad, tecnico y kit listo.");
        } else {
            vMensajes.agregarMensaje("No fue posible asignar: faltan recursos de la misma zona o kits listos.");
            vMensajes.mostrarMensaje("No fue posible asignar: faltan recursos de la misma zona o kits listos.");
        }
    }

    public void asignarSiguienteManual() {
        String id = pAsig.getIdSolicitudSeleccionada();
        String idTecnico = pAsig.getTecnicoSeleccionado();
        String idUnidad = pAsig.getUnidadSeleccionado();      
        if (id.isEmpty() || "-".equals(id)) {
            vMensajes.agregarMensaje("No hay solicitud");
            vMensajes.mostrarMensaje("No hay solicitud");
            return;
        }
        if (idUnidad.isEmpty() || idTecnico.isEmpty()) {
            vMensajes.agregarMensaje("Debe seleccionar una unidad y un tecnico disponibles.");
            vMensajes.mostrarMensaje("Seleccione una unidad y luego un tecnico disponible antes de confirmar el despacho.");
            return;
        } 
        if (modelo.asignarRecurso(id, idUnidad, idTecnico)) {            
            actualizarTabla();
            actualizarAsignacion();
            vMensajes.agregarMensaje("Se confirmo el despacho con unidad, tecnico y kit listo.");
            vMensajes.mostrarMensaje("Se confirmo el despacho con unidad, tecnico y kit listo.");
        } else {
            vMensajes.agregarMensaje("No fue posible asignar: faltan recursos de la misma zona o kits listos.");
            vMensajes.mostrarMensaje("No fue posible asignar: faltan recursos de la misma zona o kits listos.");
        }

    }

    public void cerrarSolicitud() {
        String id = vista.getCerrarId();

        if (modelo.cerrarSolicitud(id)) {
            actualizarTabla();
            vMensajes.agregarMensaje("Solicitud #" + id + " cerrada. Unidad y tecnico liberados; kit enviado a revision.");
            vMensajes.mostrarMensaje("Solicitud #" + id + " cerrada. Unidad y tecnico liberados; kit enviado a revision.");
        } else {
            vMensajes.agregarMensaje("No se encontro una solicitud en ejecucion con ese ID.");
            vMensajes.mostrarMensaje("No se encontro una solicitud en ejecucion con ese ID.");
        }
    }

    private int parseEntero(String texto, int defecto) {
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException ex) {
            return defecto;
        }
    }

    private String valorPorDefecto(String texto, String defecto) {
        if (texto == null || texto.trim().isEmpty()) {
            return defecto;
        }
        return texto.trim();
    }

    private void actualizarAsignacion() {
        actualizarRecursos();
        pAsig.limpiarSolicitudActual();
        Solicitud s = modelo.verSiguienteSolicitud();
        if (s == null) {
            pAsig.limpiarSolicitudActual();
            return;
        }
        pAsig.mostrarSolicitudActual(
                s.getId(),
                s.getCliente(),
                s.getTipoServicio(),
                s.getZona(),
                String.valueOf(s.getPrioridad())
        );
    }

    public void actualizarPendientes() {
        TablaUtils.limpiarTabla(vista.getTablaPendientes());
        Lista<Solicitud> lista = modelo.getSolicitudesPendientes();
        for (int i = 0; i < lista.size(); i++) {
            Solicitud s = lista.get(i);
            TablaUtils.agregarFila(vista.getTablaPendientes(), s.toRowPendiente());
        }
    }

    public void actualizarEjecucion() {
        TablaUtils.limpiarTabla(vista.getTablaEjecucion());
        Lista<Solicitud> lista = modelo.getCasosEnEjecucion();
        for (int i = 0; i < lista.size(); i++) {
            Solicitud s = lista.get(i);
            TablaUtils.agregarFila(vista.getTablaEjecucion(), s.toRowEjecucion());
        }
    }

    public void actualizarCerrados() {
        TablaUtils.limpiarTabla(vista.getTablaCerrados());
        Lista<Solicitud> lista = modelo.getCasosCerrados();
        for (int i = 0; i < lista.size(); i++) {
            Solicitud s = lista.get(i);
            TablaUtils.agregarFila(vista.getTablaCerrados(), s.toRowCierre());
        }
    }    
}
