package edu.co.udistrital.controller;

import edu.co.udistrital.model.CentroOperaciones;
import edu.co.udistrital.model.Lista;
import edu.co.udistrital.model.Repuesto;
import edu.co.udistrital.view.PanelRepuestos;
import edu.co.udistrital.view.TablaUtils;

public class ControlRepuestos {

    private PanelRepuestos vista;
    private CentroOperaciones modelo;
    private VisualizadorMensajes vMensajes;

    public ControlRepuestos(PanelRepuestos vista, CentroOperaciones modelo, VisualizadorMensajes vMensajes) {
        this.vista = vista;
        this.modelo = modelo;
        this.vMensajes = vMensajes;
        vista.cargarTiposFrecuentes(modelo.getRepuestosFrecuentes());
        vista.getBtnPreparar().addActionListener(e -> prepararRepuesto());
        vista.getBtnRetirar().addActionListener(e -> retirarUltimo());
        vista.getCmbTipoFrecuente().addActionListener(e -> cargarTipoFrecuente());
    }

    public void prepararRepuesto() {
        String codigo = vista.getCodigo();
        String nombre = vista.getNombre();

        if (codigo.isEmpty() || nombre.isEmpty()) {
            vMensajes.mostrarMensajeError("El repuesto debe tener codigo y nombre.", "Error Campo");
            return;
        }

        Repuesto repuesto = new Repuesto(codigo, nombre);
        modelo.prepararRepuesto(repuesto);
        vista.limpiarFormulario();
        actualizarTabla();
        vMensajes.agregarMensaje("Repuesto " + codigo + " preparado y agregado a la pila.");
    }

    public void retirarUltimo() {
        Repuesto repuesto = modelo.retirarRepuestoPreparado();
        if (repuesto == null) {
            vMensajes.mostrarMensaje("No hay repuestos preparados.", "Info");
            return;
        }
        actualizarTabla();
        vMensajes.agregarMensaje("Se retiro el ultimo repuesto preparado: " + repuesto.getCodigoRepuesto() + ".");
    }

    public void actualizarTabla() {
        vista.cargarTiposFrecuentes(modelo.getRepuestosFrecuentes());
        TablaUtils.limpiarTabla(vista.getTablaRepuestos());
        Lista<Repuesto> repuestos = modelo.getRepuestosPreparados();
        for (int i = 0; i < repuestos.size(); i++) {
            TablaUtils.agregarFila(vista.getTablaRepuestos(), repuestos.get(i).toRowPreparado());
        }
    }

    private void cargarTipoFrecuente() {
        String nombre = vista.getTipoFrecuente();
        if (nombre == null || nombre.equals("Personalizado")) {
            return;
        }
        Repuesto repuesto = modelo.buscarRepuestoFrecuente(nombre);
        if (repuesto != null) {
            vista.cargarDatos(
                    repuesto.getCodigoRepuesto(),
                    repuesto.getNombre()
            );
        }
    }
}
