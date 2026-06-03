package edu.co.udistrital.controller;

import edu.co.udistrital.model.CentroOperaciones;
import edu.co.udistrital.model.Lista;
import edu.co.udistrital.model.Repuesto;
import edu.co.udistrital.model.TipoRepuesto;
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
        TipoRepuesto[] tipos = TipoRepuesto.values();
        String[] nombresTipos = new String[tipos.length];
        for (int i = 0; i < tipos.length; i++) {
            nombresTipos[i] = tipos[i].toString();
        }
        vista.cargarTiposRepuestos(nombresTipos);
        vista.getBtnPreparar().addActionListener(e -> prepararRepuesto());
    }

    public void prepararRepuesto() {
        String tipoSeleccionado = vista.getTipoSeleccionado();

        TipoRepuesto tipo = TipoRepuesto.BATERIA;
        if (tipoSeleccionado != null && !tipoSeleccionado.isEmpty()) {
            for (TipoRepuesto t : TipoRepuesto.values()) {
                if (t.toString().equalsIgnoreCase(tipoSeleccionado)) {
                    tipo = t;
                    break;
                }
            }
        }

        Repuesto repuesto = new Repuesto(tipo);
        modelo.prepararRepuesto(repuesto);
        vista.limpiarFormulario();
        actualizarTabla();
        vMensajes.agregarMensaje("Repuesto preparado: " + repuesto.getCodigoRepuesto() + " (" + tipo.toString() + ")");
    }

    public void actualizarTabla() {
        TablaUtils.limpiarTabla(vista.getTablaRepuestos());
        Lista<Repuesto> repuestos = modelo.getRepuestosPreparados();
        for (int i = 0; i < repuestos.size(); i++) {
            TablaUtils.agregarFila(vista.getTablaRepuestos(), FilasTabla.repuestoPreparado(repuestos.get(i)));
        }
    }
}

