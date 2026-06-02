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

    private JTextField txtNombre;
    private JTextField txtDescripcion;
    private JButton btnCrearKit;
    private JButton btnRevisarKit;
    private JTable tablaKits;

    public PanelKits() {
        setLayout(new BorderLayout(10, 10));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(crearFormulario(), BorderLayout.NORTH);

        tablaKits = TablaFactory.crear(
                new String[]{"Codigo", "Nombre", "Descripcion", "Estado"}
        );
        add(new JScrollPane(tablaKits), BorderLayout.CENTER);
    }

    private JPanel crearFormulario() {
        JPanel formulario = new JPanel(new FlowLayout(FlowLayout.LEFT));
        formulario.setBorder(
                javax.swing.BorderFactory.createTitledBorder("Crear Kit")
        );

        txtNombre = new JTextField(15);
        txtDescripcion = new JTextField(20);
        btnCrearKit = new JButton("Crear");
        btnRevisarKit = new JButton("Revisar Kit");

        formulario.add(new JLabel("Nombre"));
        formulario.add(txtNombre);

        formulario.add(new JLabel("Descripcion"));
        formulario.add(txtDescripcion);

        formulario.add(btnCrearKit);
        formulario.add(btnRevisarKit);

        return formulario;
    }

    public String getNombre() {
        return txtNombre.getText().trim();
    }

    public String getDescripcion() {
        return txtDescripcion.getText().trim();
    }

    public JButton getBtnCrearKit() {
        return btnCrearKit;
    }

    public JTable getTablaKits() {
        return tablaKits;
    }

    public JButton getBtnRevisarKit() {
        return btnRevisarKit;
    }
}