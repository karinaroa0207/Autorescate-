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

    public ControlClientes(PanelClientes vista, PanelSolicitudes panelSolicitudes, CentroOperaciones modelo, VisualizadorMensajes vMensajes) {
        this.vista = vista;
        this.panelSolicitudes = panelSolicitudes;
        this.modelo = modelo;
        this.vMensajes = vMensajes;
        this.vista.getBtnRegistrarCliente().addActionListener(e -> registrarCliente());
    }

    public void registrarCliente() {
        String documento = vista.getDocumento();
        String nombre = vista.getNombre();
        String telefono = valorPorDefecto(vista.getTelefono(), "Sin telefono");
        String placa = valorPorDefecto(vista.getPlaca(), "Sin placa");
        String modeloVehiculo = valorPorDefecto(vista.getModelo(), "Sin modelo");

        if (documento.isEmpty() || nombre.isEmpty()) {
            vMensajes.mostrarMensajeError("El cliente debe tener documento y nombre.", "Error Campo");
            return;
        }
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

    public void actualizarTabla() {
        TablaUtils.limpiarTabla(vista.getTablaClientes());
        Lista<Cliente> clientes = modelo.getClientes();
        for (int i = 0; i < clientes.size(); i++) {
            TablaUtils.agregarFila(vista.getTablaClientes(), clientes.get(i).toRow());
        }
    }

    public void actualizarSelectorSolicitudes() {
        panelSolicitudes.cargarClientes(modelo.getClientes());
    }

    private String valorPorDefecto(String texto, String defecto) {
        if (texto == null || texto.trim().isEmpty()) {
            return defecto;
        }
        return texto.trim();
    }
}
