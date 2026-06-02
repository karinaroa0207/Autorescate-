package edu.co.udistrital.view;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TablaUtils {
    
    public static void limpiarTabla(JTable tabla) {
        if (tabla != null && tabla.getModel() instanceof DefaultTableModel) {
            ((DefaultTableModel) tabla.getModel()).setRowCount(0);
        }
    }

    public static void agregarFila(JTable tabla, Object[] datos) {
        if (tabla != null && tabla.getModel() instanceof DefaultTableModel) {
            ((DefaultTableModel) tabla.getModel()).addRow(datos);
        }
    }
}