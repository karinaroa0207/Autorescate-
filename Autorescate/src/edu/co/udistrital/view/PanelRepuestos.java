package edu.co.udistrital.view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

public class PanelRepuestos extends JPanel {

    private JComboBox<String> cmbTipoRepuesto;
    private JButton btnPreparar;
    private JTable tablaRepuestos;

    public PanelRepuestos() {
        setLayout(new BorderLayout(10, 10));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(crearFormulario(), BorderLayout.NORTH);
        tablaRepuestos = TablaFactory.crear(new String[]{"Tipo", "Codigo"});
        add(new JScrollPane(tablaRepuestos), BorderLayout.CENTER);
    }

    private JPanel crearFormulario() {
        JPanel formulario = new JPanel(new BorderLayout(8, 5));
        formulario.setBorder(javax.swing.BorderFactory.createTitledBorder("Preparar Repuesto"));

        cmbTipoRepuesto = new JComboBox<>();
        btnPreparar = new JButton("Preparar repuesto");

        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        JPanel campos = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        campos.add(new JLabel("Tipo"));
        campos.add(cmbTipoRepuesto);

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        acciones.add(btnPreparar);
        acciones.add(btnLimpiar);

        formulario.add(campos, BorderLayout.CENTER);
        formulario.add(acciones, BorderLayout.SOUTH);
        return formulario;
    }

    public String getTipoSeleccionado() {
        return (String) cmbTipoRepuesto.getSelectedItem();
    }

    public JButton getBtnPreparar() {
        return btnPreparar;
    }

    public JTable getTablaRepuestos() {
        return tablaRepuestos;
    }

    public void limpiarFormulario() {
        if (cmbTipoRepuesto.getItemCount() > 0) {
            cmbTipoRepuesto.setSelectedIndex(0);
        }
    }

    public void cargarTiposRepuestos(String[] tipos) {
        String sel = getTipoSeleccionado();
        cmbTipoRepuesto.removeAllItems();
        for (int i = 0; i < tipos.length; i++) {
            cmbTipoRepuesto.addItem(tipos[i]);
        }
        if (sel != null) {
            cmbTipoRepuesto.setSelectedItem(sel);
        }
    }

    public void setTipoSeleccionado(String tipo) {
        cmbTipoRepuesto.setSelectedItem(tipo);
    }
}
