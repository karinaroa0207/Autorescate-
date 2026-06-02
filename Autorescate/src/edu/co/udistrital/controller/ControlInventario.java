package edu.co.udistrital.controller;

import edu.co.udistrital.model.CentroOperaciones;
import edu.co.udistrital.model.Kit;
import edu.co.udistrital.model.Lista;
import edu.co.udistrital.view.PanelKits;
import edu.co.udistrital.view.TablaUtils;

public class ControlInventario {

    private PanelKits vista;
    private CentroOperaciones gestor;
    private VisualizadorMensajes vMensajes;

    public ControlInventario(PanelKits vista, CentroOperaciones gestor, VisualizadorMensajes vMensajes) {
        this.vista = vista;
        this.gestor = gestor;
        this.vMensajes = vMensajes;
        vista.getBtnCrearKit().addActionListener(e -> crearKit());
        vista.getBtnRevisarKit().addActionListener(e -> revisarKit());
    }

    public void crearKit() {
        String nombre = vista.getNombre();
        String descripcion = vista.getDescripcion();
        if (nombre.isEmpty() || descripcion.isEmpty()) {
            vMensajes.mostrarMensajeError("Nombre ni descripcion pueden estar vacios");
            return;
        }
        Kit kit = new Kit(nombre, descripcion);
        gestor.agregarKit(kit);
        TablaUtils.agregarFila(vista.getTablaKits(), kit.toRow());
    }

    public void revisarKit() {
        if (gestor.revisarKit() != null) {
            vMensajes.mostrarMensaje("Kit Listo");
        } else {
            vMensajes.mostrarMensaje("No hay kits para revisar");
        }
    }

    public void actualizarTabla() {
        TablaUtils.limpiarTabla(vista.getTablaKits());
        Lista<Kit> kits = gestor.getKits();
        for (int i = 0; i < kits.size(); i++) {
            Kit kit = kits.get(i);
            TablaUtils.agregarFila(vista.getTablaKits(), kit.toRow());
        }
    }

}
