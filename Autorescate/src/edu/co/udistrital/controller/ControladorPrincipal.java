package edu.co.udistrital.controller;

import edu.co.udistrital.model.CamionetaAsistencia;
import edu.co.udistrital.model.CentroOperaciones;
import edu.co.udistrital.model.Grua;
import edu.co.udistrital.model.Kit;
import edu.co.udistrital.model.MotoApoyo;
import edu.co.udistrital.model.Nodo;
import edu.co.udistrital.model.Operacion;
import edu.co.udistrital.model.Solicitud;
import edu.co.udistrital.model.Tecnico;
import edu.co.udistrital.model.Unidad;
import edu.co.udistrital.model.VehiculoLiviano;
import edu.co.udistrital.view.VentanaPrincipal;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ControladorPrincipal {

    private VentanaPrincipal vista;
    private CentroOperaciones modelo;

    public ControladorPrincipal(VentanaPrincipal vista, CentroOperaciones modelo) {
        this.vista = vista;
        this.modelo = modelo;
        inicializarDatosPrueba();
        inicializarEventos();
        actualizarVistas();
    }

    private void inicializarDatosPrueba() {
        modelo.agregarUnidad(new Grua("Norte", 5.0));
        modelo.agregarUnidad(new MotoApoyo("Centro", true));
        modelo.agregarUnidad(new CamionetaAsistencia("Sur", true));
        modelo.agregarUnidad(new VehiculoLiviano("Occidente", 4));

        modelo.agregarTecnico(new Tecnico("101", "Helio Ramirez", "Mecanica", "Norte"));
        modelo.agregarTecnico(new Tecnico("102", "Andrea Rojas", "Electrica", "Centro"));
        modelo.agregarTecnico(new Tecnico("103", "Carlos Mena", "Grua", "Sur"));

        modelo.registrarSolicitud(new Solicitud("Cliente particular", "Bateria descargada en parqueadero", "Centro", "Paso de corriente", 0));
        modelo.registrarSolicitud(new Solicitud("Aseguradora Andina", "Bus averiado con pasajeros en carretera", "Norte", "Grua", 95));
        modelo.recibirKit(new Kit("KIT-001"));
        modelo.recibirKit(new Kit("KIT-002"));
        vista.agregarMensaje("Sistema inicializado con datos de prueba.");
    }

    private void inicializarEventos() {
        vista.getBtnRegistrarSolicitud().addActionListener(e -> registrarSolicitud());
        vista.getBtnAsignar().addActionListener(e -> asignarSiguiente());
        vista.getBtnCerrarSolicitud().addActionListener(e -> cerrarSolicitud());
        vista.getBtnRegistrarUnidad().addActionListener(e -> registrarUnidad());
        vista.getBtnRegistrarTecnico().addActionListener(e -> registrarTecnico());
        vista.getBtnRecibirKit().addActionListener(e -> recibirKit());
        vista.getBtnDespacharKit().addActionListener(e -> despacharKit());
        vista.getBtnDeshacer().addActionListener(e -> deshacer());
        vista.getBtnExportar().addActionListener(e -> generarCSV());
        vista.getBtnActualizar().addActionListener(e -> actualizarVistas());
    }

    private void registrarSolicitud() {
        String cliente = vista.getCliente();
        String descripcion = vista.getDescripcion();
        String zona = valorPorDefecto(vista.getZonaSolicitud(), "General");
        String servicio = vista.getTipoServicio();
        int prioridad = parseEntero(vista.getPrioridad(), 0);

        if (cliente.isEmpty() || descripcion.isEmpty()) {
            vista.agregarMensaje("La solicitud debe tener cliente y descripcion.");
            return;
        }

        modelo.registrarSolicitud(new Solicitud(cliente, descripcion, zona, servicio, prioridad));
        vista.limpiarFormularioSolicitud();
        vista.agregarMensaje("Solicitud registrada. Prioridad " + prioridad + ".");
        actualizarVistas();
    }

    private void asignarSiguiente() {
        if (modelo.asignarRecurso()) {
            vista.agregarMensaje("Se asigno la siguiente solicitud segun prioridad y orden de llegada.");
        } else {
            vista.agregarMensaje("No fue posible asignar: faltan solicitudes, unidades o tecnicos disponibles.");
        }
        actualizarVistas();
    }

    private void cerrarSolicitud() {
        int id = parseEntero(vista.getCerrarId(), -1);
        if (id <= 0) {
            vista.agregarMensaje("Ingresa un ID valido para cerrar el caso.");
            return;
        }

        if (modelo.cerrarSolicitud(id)) {
            vista.agregarMensaje("Solicitud #" + id + " cerrada y recursos liberados.");
        } else {
            vista.agregarMensaje("No se encontro una solicitud en ejecucion con ese ID.");
        }
        actualizarVistas();
    }

    private void registrarUnidad() {
        String zona = valorPorDefecto(vista.getZonaUnidad(), "General");
        String tipo = vista.getTipoUnidad();
        Unidad unidad;

        if ("Grua".equals(tipo)) {
            unidad = new Grua(zona, 5.0);
        } else if ("Moto de apoyo".equals(tipo)) {
            unidad = new MotoApoyo(zona, true);
        } else if ("Camioneta".equals(tipo)) {
            unidad = new CamionetaAsistencia(zona, true);
        } else {
            unidad = new VehiculoLiviano(zona, 4);
        }

        modelo.agregarUnidad(unidad);
        vista.agregarMensaje("Unidad registrada con UUID " + unidad.getId() + ".");
        actualizarVistas();
    }

    private void registrarTecnico() {
        String id = vista.getIdTecnico();
        String nombre = vista.getNombreTecnico();
        String especialidad = vista.getEspecialidad();
        String zona = valorPorDefecto(vista.getZonaTecnico(), "General");

        if (id.isEmpty() || nombre.isEmpty() || especialidad.isEmpty()) {
            vista.agregarMensaje("El tecnico debe tener identificacion, nombre y especialidad.");
            return;
        }

        modelo.agregarTecnico(new Tecnico(id, nombre, especialidad, zona));
        vista.agregarMensaje("Tecnico " + nombre + " registrado.");
        actualizarVistas();
    }

    private void recibirKit() {
        String codigo = vista.getCodigoKit();
        if (codigo.isEmpty()) {
            vista.agregarMensaje("Ingresa el codigo del kit.");
            return;
        }
        modelo.recibirKit(new Kit(codigo));
        vista.agregarMensaje("Kit " + codigo + " recibido en revision.");
        actualizarVistas();
    }

    private void despacharKit() {
        Kit kit = modelo.despacharKit();
        if (kit == null) {
            vista.agregarMensaje("No hay kits en revision para despachar.");
        } else {
            vista.agregarMensaje("Se despacho el ultimo kit recibido: " + kit.getCodigo() + ".");
        }
        actualizarVistas();
    }

    private void deshacer() {
        if (modelo.deshacerUltimaOperacion()) {
            vista.agregarMensaje("Operacion reciente revertida.");
        } else {
            vista.agregarMensaje("No hay asignaciones o cierres recientes para revertir.");
        }
        actualizarVistas();
    }

    private void actualizarVistas() {
        llenarPendientes();
        llenarEjecucion();
        llenarCerrados();
        llenarUnidades();
        llenarTecnicos();
        llenarKits();
        llenarHistorial();
    }

    private void llenarPendientes() {
        vista.limpiarTabla(vista.getTablaPendientes());
        Nodo<Solicitud> actual = modelo.getSolicitudesPendientes().getFrente();
        while (actual != null) {
            Solicitud s = actual.getDato();
            vista.agregarFila(vista.getTablaPendientes(), new Object[]{
                s.getId(), s.getCliente(), s.getTipoServicio(), s.getZona(), s.getPrioridad(), s.getEstado()
            });
            actual = actual.getSiguiente();
        }
    }

    private void llenarEjecucion() {
        vista.limpiarTabla(vista.getTablaEjecucion());
        Nodo<Solicitud> actual = modelo.getCasosEnEjecucion().getCabeza();
        while (actual != null) {
            Solicitud s = actual.getDato();
            vista.agregarFila(vista.getTablaEjecucion(), new Object[]{
                s.getId(), s.getCliente(), s.getUnidadAsignada().getTipo(), s.getTecnicoAsignado().getNombre(), s.getEstado()
            });
            actual = actual.getSiguiente();
        }
    }

    private void llenarCerrados() {
        vista.limpiarTabla(vista.getTablaCerrados());
        Nodo<Solicitud> actual = modelo.getCasosCerrados().getCabeza();
        while (actual != null) {
            Solicitud s = actual.getDato();
            vista.agregarFila(vista.getTablaCerrados(), new Object[]{
                s.getId(), s.getCliente(), s.getTipoServicio(), s.getUnidadAsignada().getTipo(),
                s.getTecnicoAsignado().getNombre(), s.getFechaCierre()
            });
            actual = actual.getSiguiente();
        }
    }

    private void llenarUnidades() {
        vista.limpiarTabla(vista.getTablaUnidades());
        Nodo<Unidad> actual = modelo.getUnidades().getCabeza();
        while (actual != null) {
            Unidad u = actual.getDato();
            vista.agregarFila(vista.getTablaUnidades(), new Object[]{
                u.getId(), u.getTipo(), u.getZona(), u.getEstado(), u.isDisponible()
            });
            actual = actual.getSiguiente();
        }
    }

    private void llenarTecnicos() {
        vista.limpiarTabla(vista.getTablaTecnicos());
        Nodo<Tecnico> actual = modelo.getTecnicos().getCabeza();
        while (actual != null) {
            Tecnico t = actual.getDato();
            vista.agregarFila(vista.getTablaTecnicos(), new Object[]{
                t.getIdentificacion(), t.getNombre(), t.getEspecialidad(), t.getZona(), t.getEstado(), t.isLibre()
            });
            actual = actual.getSiguiente();
        }
    }

    private void llenarKits() {
        vista.limpiarTabla(vista.getTablaKits());
        Nodo<Kit> actual = modelo.getKitsEnRevision().getCima();
        int posicion = 1;
        while (actual != null) {
            Kit kit = actual.getDato();
            vista.agregarFila(vista.getTablaKits(), new Object[]{posicion, kit.getCodigo(), kit.isRequiereRevision()});
            posicion++;
            actual = actual.getSiguiente();
        }
    }

    private void llenarHistorial() {
        vista.limpiarTabla(vista.getTablaHistorial());
        Nodo<Operacion> actual = modelo.getHistorial().getCima();
        int orden = 1;
        while (actual != null) {
            Operacion op = actual.getDato();
            Solicitud solicitud = op.getSolicitud();
            Unidad unidad = op.getUnidad();
            Tecnico tecnico = op.getTecnico();
            vista.agregarFila(vista.getTablaHistorial(), new Object[]{
                orden,
                op.getTipo(),
                solicitud == null ? "" : solicitud.getId(),
                unidad == null ? "" : unidad.getTipo(),
                tecnico == null ? "" : tecnico.getNombre()
            });
            orden++;
            actual = actual.getSiguiente();
        }
    }

    private void generarCSV() {
        try (FileWriter writer = new FileWriter("reporte_cierre.csv", StandardCharsets.UTF_8)) {
            writer.append("ID_Caso,Cliente,Descripcion,Servicio,Zona,Prioridad,Unidad,Tecnico,Fecha_Cierre\n");
            Nodo<Solicitud> actual = modelo.getCasosCerrados().getCabeza();

            while (actual != null) {
                Solicitud s = actual.getDato();
                writer.append(s.getId() + ",");
                writer.append(csv(s.getCliente()) + ",");
                writer.append(csv(s.getDescripcion()) + ",");
                writer.append(csv(s.getTipoServicio()) + ",");
                writer.append(csv(s.getZona()) + ",");
                writer.append(s.getPrioridad() + ",");
                writer.append(csv(s.getUnidadAsignada().getTipo()) + ",");
                writer.append(csv(s.getTecnicoAsignado().getNombre()) + ",");
                writer.append(csv(String.valueOf(s.getFechaCierre())) + "\n");
                actual = actual.getSiguiente();
            }
            vista.agregarMensaje("Archivo reporte_cierre.csv generado en la raiz del proyecto.");
        } catch (IOException ex) {
            vista.agregarMensaje("Error al escribir el archivo CSV: " + ex.getMessage());
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

    private String csv(String valor) {
        if (valor == null) {
            return "";
        }
        String limpio = valor.replace("\"", "\"\"");
        if (limpio.indexOf(',') >= 0 || limpio.indexOf('"') >= 0 || limpio.indexOf('\n') >= 0) {
            return "\"" + limpio + "\"";
        }
        return limpio;
    }
}
