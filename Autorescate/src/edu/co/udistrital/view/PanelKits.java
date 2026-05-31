package edu.co.udistrital.view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

public class PanelKits extends JPanel {

    private JTextField txtCodigoKit;
    private JButton btnRecibirKit;
    private JButton btnDespacharKit;
    private JTable tablaKits;

    public PanelKits() {
        setLayout(new BorderLayout(10, 10));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(crearFormulario(), BorderLayout.NORTH);
        tablaKits = TablaFactory.crear(new String[]{"Posicion", "Codigo", "Estado"});
        add(new JScrollPane(tablaKits), BorderLayout.CENTER);
    }

    private JPanel crearFormulario() {
        JPanel formulario = new JPanel(new FlowLayout(FlowLayout.LEFT));
        formulario.setBorder(javax.swing.BorderFactory.createTitledBorder("Kits en revision"));
        txtCodigoKit = new JTextField(12);
        btnRecibirKit = new JButton("Recibir kit");
        btnDespacharKit = new JButton("Despachar ultimo");

        formulario.add(new JLabel("Codigo"));
        formulario.add(txtCodigoKit);
        formulario.add(btnRecibirKit);
        formulario.add(btnDespacharKit);
        return formulario;
    }

    public String getCodigoKit() { return txtCodigoKit.getText().trim(); }
    public JButton getBtnRecibirKit() { return btnRecibirKit; }
    public JButton getBtnDespacharKit() { return btnDespacharKit; }
    public JTable getTablaKits() { return tablaKits; }
}
