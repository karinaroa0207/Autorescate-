package edu.co.udistrital.controller;

import edu.co.udistrital.model.CentroOperaciones;
import edu.co.udistrital.model.Cliente;
import edu.co.udistrital.model.Lista;
import edu.co.udistrital.view.PanelClientes;
import edu.co.udistrital.view.TablaUtils;

public class ControlClientes {

    private PanelClientes vista;
    private CentroOperaciones gestor;
    private VisualizadorMensajes vMensajes;
    private boolean modoRegistro;

    public ControlClientes(PanelClientes vista, CentroOperaciones gestor, VisualizadorMensajes vMensajes) {
        this.vista = vista;
        this.gestor = gestor;
        this.vMensajes = vMensajes;
        modoRegistro = true;
        
        vista.getBtnRegistrarCliente().addActionListener(e -> guardarCliente());
        vista.getBtnModificarCliente().addActionListener(e -> cargarParaModificar());
        vista.getBtnEliminarCliente().addActionListener(e -> eliminarSeleccionado());
        vista.getBtnLimpiar().addActionListener(e -> limpiarFormulario());
        actualizarTabla();
    }

    public void guardarCliente() {
        String documento = vista.getDocumento().trim();
        String nombre = vista.getNombre().trim();
        String telefono = vista.getTelefono().trim();
        String placa = vista.getPlaca().trim();
        String modelo = vista.getModelo().trim();

        if (documento.isEmpty() || nombre.isEmpty()) {
            vMensajes.mostrarMensajeError("Documento y nombre son requeridos.", "Error Campo");
            return;
        }

        if (modoRegistro) {
            Cliente nuevoCliente = new Cliente(documento, nombre, telefono, placa, modelo);
            gestor.agregarCliente(nuevoCliente);
            vista.limpiarFormularioCliente();
            vMensajes.mostrarMensaje("Cliente " + nombre + " registrado con éxito.", "Éxito");
        } else {
            Cliente modificado = gestor.modificarCliente(vista.getIdCliente(), documento, nombre, telefono, placa, modelo);
            if (modificado != null) {
                vista.limpiarFormularioCliente();
                vMensajes.mostrarMensaje("Cliente " + nombre + " actualizado.", "Info");
            }
        }
        vista.modoRegistro();
        modoRegistro = true;
        actualizarTabla();
    }

    public void cargarParaModificar() {
        String idSeleccionado = vista.getIdClienteSeleccionadoEnTabla();

        if (idSeleccionado == null || idSeleccionado.isEmpty()) {
            vMensajes.mostrarMensaje("Seleccione un cliente de la tabla para modificar.", "Info");
            return;
        }
        
        Cliente c = gestor.buscarClientePorId(idSeleccionado);
        if (c != null) {
            vista.setDocumento(c.getDocumento());
            vista.setNombre(c.getNombre());
            vista.setTelefono(c.getTelefono());
            vista.setPlaca(c.getPlacaVehiculo());
            vista.setModelo(c.getModeloVehiculo());
            vista.setId(idSeleccionado);
            vista.modoEdicion();
            modoRegistro = false;
        }
    }

    public void eliminarSeleccionado() {
        String idSeleccionado = vista.getIdClienteSeleccionadoEnTabla();

        if (idSeleccionado == null || idSeleccionado.isEmpty()) {
            vMensajes.mostrarMensaje("Seleccione un cliente de la tabla para eliminar.", "Info");
            return;
        }

        boolean exito = gestor.eliminarCliente(idSeleccionado);
        if (exito) {
            vMensajes.mostrarMensaje("Cliente eliminado del sistema con éxito.", "Info");
            actualizarTabla();
            return;
        }
        vMensajes.mostrarMensaje("No se pudo eliminar el cliente.", "Error");
    }

    public void actualizarTabla() {
        TablaUtils.limpiarTabla(vista.getTablaClientes());
        Lista<Cliente> lista = gestor.obtenerClientes();
        for (int i = 0; i < lista.size(); i++) {
            Cliente obj = lista.get(i);
            TablaUtils.agregarFila(vista.getTablaClientes(), obj.toRow());
        }
    }

    public void limpiarFormulario() {
        vista.limpiarFormularioCliente();
        vista.modoRegistro();
        modoRegistro = true;
    }
}
