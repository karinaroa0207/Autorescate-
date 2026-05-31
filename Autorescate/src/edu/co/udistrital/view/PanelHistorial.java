package edu.co.udistrital.view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.BorderLayout;

public class PanelHistorial extends JPanel {

    private JTable tablaHistorial;

    public PanelHistorial() {
        setLayout(new BorderLayout(10, 10));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tablaHistorial = TablaFactory.crear(new String[]{"Orden", "Tipo", "Solicitud", "Unidad", "Tecnico"});
        add(new JScrollPane(tablaHistorial), BorderLayout.CENTER);
    }

    public JTable getTablaHistorial() {
        return tablaHistorial;
    }
}
