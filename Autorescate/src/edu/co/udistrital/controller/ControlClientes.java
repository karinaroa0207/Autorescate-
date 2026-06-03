package edu.co.udistrital.controller;

import edu.co.udistrital.model.CentroOperaciones;
import edu.co.udistrital.model.Cliente;
import edu.co.udistrital.model.Lista;
import edu.co.udistrital.view.PanelClientes;
import edu.co.udistrital.view.PanelSolicitudes;
import edu.co.udistrital.view.TablaUtils;

public class ControlClientes {

    private PanelClientes vista;
    private PanelSolicitudes panelSolicitudes;
    private CentroOperaciones modelo;
    private VisualizadorMensajes vMensajes;
    private boolean modoRegistro;

    public ControlClientes(PanelClientes vista, PanelSolicitudes panelSolicitudes, CentroOperaciones modelo, VisualizadorMensajes vMensajes) {
        this.vista = vista;
        this.panelSolicitudes = panelSolicitudes;
        this.modelo = modelo;
        this.vMensajes = vMensajes;
        this.modoRegistro = true;
        this.vista.getBtnRegistrarCliente().addActionListener(e -> guardarCliente());
        this.vista.getBtnModificarCliente().addActionListener(e -> cargarParaModificar());
        this.vista.getBtnEliminarCliente().addActionListener(e -> eliminarSeleccionado());
    }

    public void guardarCliente() {
        String documento = vista.getDocumento();
        String nombre = vista.getNombre();
        String telefono = valorPorDefecto(vista.getTelefono(), "Sin telefono");
        String placa = valorPorDefecto(vista.getPlaca(), "Sin placa");
        String modeloVehiculo = valorPorDefecto(vista.getModelo(), "Sin modelo");

        if (documento.isEmpty() || nombre.isEmpty()) {
            vMensajes.mostrarMensajeError("El cliente debe tener documento y nombre.", "Error Campo");
            return;
        }

        if (modoRegistro) {
            registrarCliente(documento, nombre, telefono, placa, modeloVehiculo);
        } else {
            modificarCliente(documento, nombre, telefono, placa, modeloVehiculo);
        }
    }

    public void registrarCliente(String documento, String nombre, String telefono, String placa, String modeloVehiculo) {
        if (modelo.buscarClientePorDocumento(documento) != null) {
            vMensajes.mostrarMensaje("Ya existe un cliente con ese documento.", "Info");
            return;
        }
        Cliente cliente = new Cliente(documento, nombre, telefono, placa, modeloVehiculo);
        modelo.registrarCliente(cliente);
        vista.limpiarFormulario();
        actualizarTabla();
        actualizarSelectorSolicitudes();
        vMensajes.mostrarMensaje("Cliente " + nombre + " registrado.", "Exito");
    }

    public void modificarCliente(String documento, String nombre, String telefono, String placa, String modeloVehiculo) {
        if (modelo.modificarCliente(documento, nombre, telefono, placa, modeloVehiculo)) {
            vista.limpiarFormulario();
            vista.modoRegistro();
            modoRegistro = true;
            actualizarTabla();
            actualizarSelectorSolicitudes();
            vMensajes.mostrarMensaje("Cliente " + nombre + " actualizado.", "Info");
            return;
        }
        vMensajes.mostrarMensaje("No se encontro el cliente seleccionado.", "Info");
    }

    public void cargarParaModificar() {
        String documentoSeleccionado = vista.getDocumentoSeleccionadoEnTabla();
        if (documentoSeleccionado == null || documentoSeleccionado.isEmpty()) {
            vMensajes.mostrarMensaje("Seleccione un cliente de la tabla para modificar.", "Info");
            return;
        }
        Cliente cliente = modelo.buscarClientePorDocumento(documentoSeleccionado);
        if (cliente != null) {
            vista.setDocumento(cliente.getDocumento());
            vista.setNombre(cliente.getNombre());
            vista.setTelefono(cliente.getTelefono());
            vista.setPlaca(cliente.getPlacaVehiculo());
            vista.setModelo(cliente.getModeloVehiculo());
            vista.modoEdicion();
            modoRegistro = false;
        }
    }

    public void eliminarSeleccionado() {
        String documentoSeleccionado = vista.getDocumentoSeleccionadoEnTabla();
        if (documentoSeleccionado == null || documentoSeleccionado.isEmpty()) {
            vMensajes.mostrarMensaje("Seleccione un cliente de la tabla para eliminar.", "Info");
            return;
        }
        Cliente eliminado = modelo.eliminarCliente(documentoSeleccionado);
        if (eliminado != null) {
            vista.limpiarFormulario();
            vista.modoRegistro();
            modoRegistro = true;
            actualizarTabla();
            actualizarSelectorSolicitudes();
            vMensajes.mostrarMensaje("Cliente eliminado con exito.", "Info");
            return;
        }
        vMensajes.mostrarMensaje("No se encontro el cliente seleccionado.", "Info");
    }

    public void actualizarTabla() {
        TablaUtils.limpiarTabla(vista.getTablaClientes());
        Lista<Cliente> clientes = modelo.getClientes();
        for (int i = 0; i < clientes.size(); i++) {
            TablaUtils.agregarFila(vista.getTablaClientes(), FilasTabla.cliente(clientes.get(i)));
        }
    }

    public void actualizarSelectorSolicitudes() {
        Lista<Cliente> clientes = modelo.getClientes();
        String[] opciones = new String[clientes.size()];
        for (int i = 0; i < clientes.size(); i++) {
            Cliente cliente = clientes.get(i);
            opciones[i] = cliente.getDocumento() + " - " + cliente.getNombre();
        }
        panelSolicitudes.cargarClientes(opciones);
    }

    private String valorPorDefecto(String texto, String defecto) {
        if (texto == null || texto.trim().isEmpty()) {
            return defecto;
        }
        return texto.trim();
    }
}
