package edu.co.udistrital.view;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TablaFactory {

    private TablaFactory() {
    }

    public static JTable crear(String[] columnas) {
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(24);
        return tabla;
    }
}
