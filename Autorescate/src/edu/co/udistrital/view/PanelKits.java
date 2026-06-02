package edu.co.udistrital.view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JComboBox;

public class PanelKits extends JPanel {

    private JTextField txtNombre;
    private JTextField txtDescripcion;

    private JButton btnCrearKit;
    private JButton btnRevisarKit;

    private JComboBox<String> cmbFiltroEstado;

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

        JPanel panelPrincipal = new JPanel(new BorderLayout(5, 5));
        panelPrincipal.setBorder(
                javax.swing.BorderFactory.createTitledBorder("Crear Kit")
        );

        JPanel filaFormulario = new JPanel(new FlowLayout(FlowLayout.LEFT));

        txtNombre = new JTextField(15);
        txtDescripcion = new JTextField(20);

        btnCrearKit = new JButton("Crear");

        JButton btnLimpiar = new JButton("Limpiar formulario");
        btnLimpiar.addActionListener(e -> {
            txtNombre.setText("");
            txtDescripcion.setText("");
        });

        btnRevisarKit = new JButton("Revisar Kit");

        filaFormulario.add(new JLabel("Nombre"));
        filaFormulario.add(txtNombre);

        filaFormulario.add(new JLabel("Descripción"));
        filaFormulario.add(txtDescripcion);

        filaFormulario.add(btnCrearKit);
        filaFormulario.add(btnLimpiar);
        filaFormulario.add(btnRevisarKit);

        JPanel filaFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT));

        cmbFiltroEstado = new JComboBox<>(
                new String[]{"Todos", "Listos", "En revisión"}
        );

        filaFiltro.add(new JLabel("Mostrar"));
        filaFiltro.add(cmbFiltroEstado);

        panelPrincipal.add(filaFormulario, BorderLayout.NORTH);
        panelPrincipal.add(filaFiltro, BorderLayout.SOUTH);

        return panelPrincipal;
    }

    public String getNombre() {
        return txtNombre.getText().trim();
    }

    public String getDescripcion() {
        return txtDescripcion.getText().trim();
    }

    public String getFiltroEstado() {
        return (String) cmbFiltroEstado.getSelectedItem();
    }

    public JComboBox<String> getCmbFiltroEstado() {
        return cmbFiltroEstado;
    }

    public JButton getBtnCrearKit() {
        return btnCrearKit;
    }

    public JButton getBtnRevisarKit() {
        return btnRevisarKit;
    }

    public JTable getTablaKits() {
        return tablaKits;
    }
}
