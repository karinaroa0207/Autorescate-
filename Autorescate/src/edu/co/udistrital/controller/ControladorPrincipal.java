package edu.co.udistrital.controller;

import edu.co.udistrital.model.CentroOperaciones;
import edu.co.udistrital.model.ExportadorCSV;
import edu.co.udistrital.model.GestorCliente;
import edu.co.udistrital.model.GestorKits;
import edu.co.udistrital.model.GestorSolicitudes;
import edu.co.udistrital.model.GestorTecnico;
import edu.co.udistrital.model.GestorUnidad;
import edu.co.udistrital.model.Kit;
import edu.co.udistrital.model.Lista;
import edu.co.udistrital.model.Operacion;
import edu.co.udistrital.model.Solicitud;
import edu.co.udistrital.model.Tecnico;
import edu.co.udistrital.model.UnidadFactory;
import edu.co.udistrital.view.VentanaPrincipal;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class ControladorPrincipal {

    private VentanaPrincipal vista;
    private CentroOperaciones modelo;
    private ControlInventario cInventario;
    private ControlTecnico cTecnico;
    private ControlSolicitudes cSolicitudes;
    private ControlUnidades cUnidades;
    private ControlClientes cClientes;

    public ControladorPrincipal() {
        GestorKits gkits = new GestorKits();
        GestorTecnico gTec = new GestorTecnico();
        GestorSolicitudes gSol = new GestorSolicitudes();
        GestorUnidad gUni = new GestorUnidad();
        GestorCliente gCli = new GestorCliente();

        this.modelo = new CentroOperaciones(gkits, gTec, gSol, gUni, gCli);
        this.vista = new VentanaPrincipal();

        this.cInventario = new ControlInventario(vista.getPanelKits(), modelo, vista);
        this.cTecnico = new ControlTecnico(vista.getPanelTecnicos(), modelo, vista);
        this.cSolicitudes = new ControlSolicitudes(vista.getPanelSolicitudes(), modelo, vista);
        this.cUnidades = new ControlUnidades(vista.getPanelRecursos(), modelo, vista);
        this.cClientes = new ControlClientes(vista.getPanelClientes(), modelo, vista);
        inicializarDatosPrueba();
        inicializarEventos();
        actualizarVistas();
        vista.getTabs().addChangeListener(e -> {
            int pestañaSeleccionada = vista.getTabs().getSelectedIndex();
            switch (pestañaSeleccionada) {
                case 0:
                    cSolicitudes.actualizarPendientes();
                    cSolicitudes.actualizarEjecucion();
                    cSolicitudes.actualizarCerrados();
                    cSolicitudes.actualizarRecursos();
                    break;
                case 1:
                    cUnidades.actualizarTabla();
                    break;
                case 2:
                    cTecnico.actualizarTabla();
                    break;
                case 3:
                    cInventario.actualizarTabla();
                    break;
                case 4:
                    cClientes.actualizarTabla();
                    break;
                case 5:
                    llenarHistorial();
                    break;
                default:
                    break;
            }
        });
        vista.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                generarCSV();
            }
        });
    }

    private void inicializarDatosPrueba() {
        modelo.agregarUnidad(UnidadFactory.crearUnidad("Grua", "Norte"));
        modelo.agregarUnidad(UnidadFactory.crearUnidad("Moto de apoyo", "Centro"));
        modelo.agregarUnidad(UnidadFactory.crearUnidad("Camioneta", "Sur"));
        modelo.agregarUnidad(UnidadFactory.crearUnidad("VehiculoLiviano", "Occidente"));

        modelo.agregarTecnico(new Tecnico("101", "Helio Ramirez", "Mecanica", "Norte"));
        modelo.agregarTecnico(new Tecnico("102", "Andrea Rojas", "Electrica", "Centro"));
        modelo.agregarTecnico(new Tecnico("103", "Carlos Mena", "Grua", "Sur"));

        modelo.registrarSolicitud(new Solicitud("Cliente particular", "Bateria descargada en parqueadero", "Centro", "Paso de corriente", 0));
        modelo.registrarSolicitud(new Solicitud("Aseguradora Andina", "Bus averiado con pasajeros en carretera", "Norte", "Grua", 95));
        modelo.agregarKit(new Kit("KIT-001", "Herramientas"));
        modelo.agregarKit(new Kit("KIT-002", "Llantas"));
        vista.agregarMensaje("Sistema inicializado con datos de prueba.");
    }

    private void inicializarEventos() {
        vista.getBtnDeshacer().addActionListener(e -> deshacer());
        vista.getBtnExportar().addActionListener(e -> generarCSV());
        vista.getBtnActualizar().addActionListener(e -> actualizarVistas());
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
        cSolicitudes.actualizarPendientes();
        cSolicitudes.actualizarEjecucion();
        cSolicitudes.actualizarCerrados();
        cInventario.actualizarTabla();
        cTecnico.actualizarTabla();
        cSolicitudes.actualizarRecursos();
        cUnidades.actualizarTabla();
        cClientes.actualizarTabla();
        llenarHistorial();
    }

    private void llenarHistorial() {
        vista.limpiarTabla(vista.getTablaHistorial());
        Lista<Operacion> lista = modelo.getHistorial();
        for (int i = 0; i < lista.size(); i++) {
            Operacion actual = lista.get(i);
            vista.agregarFila(vista.getTablaHistorial(), actual.toRow(i + 1));
        }
    }

    private void generarCSV() {
        try {
            Lista<Solicitud> casos = modelo.getCasosCerrados();
            ExportadorCSV.generarReporteCasosCerrados(casos);
            vista.agregarMensaje("Archivo reporte generado con éxito en la raíz del proyecto.");
        } catch (IOException ex) {
            vista.agregarMensaje("Error al escribir el archivo CSV: " + ex.getMessage());
        }
    }
}
