package edu.co.udistrital.view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

public class PanelRepuestos extends JPanel {

    private JComboBox<String> cmbTipoFrecuente;
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JButton btnPreparar;
    private JButton btnRetirar;
    private JTable tablaRepuestos;

    public PanelRepuestos() {
        setLayout(new BorderLayout(10, 10));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(crearFormulario(), BorderLayout.NORTH);
        tablaRepuestos = TablaFactory.crear(new String[]{"Codigo", "Nombre", "Cantidad"});
        add(new JScrollPane(tablaRepuestos), BorderLayout.CENTER);
    }

    private JPanel crearFormulario() {
        JPanel formulario = new JPanel(new BorderLayout(8, 5));
        formulario.setBorder(javax.swing.BorderFactory.createTitledBorder("Repuestos preparados"));

        cmbTipoFrecuente = new JComboBox<>();
        cmbTipoFrecuente.addItem("Personalizado");
        txtCodigo = new JTextField(10);
        txtNombre = new JTextField(16);
        btnPreparar = new JButton("Preparar repuesto");
        btnRetirar = new JButton("Retirar ultimo");

        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        JPanel campos = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        campos.add(new JLabel("Tipo"));
        campos.add(cmbTipoFrecuente);
        campos.add(new JLabel("Codigo"));
        campos.add(txtCodigo);
        campos.add(new JLabel("Nombre"));
        campos.add(txtNombre);

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 5));
        acciones.add(btnPreparar);
        acciones.add(btnRetirar);
        acciones.add(btnLimpiar);

        formulario.add(campos, BorderLayout.CENTER);
        formulario.add(acciones, BorderLayout.SOUTH);
        return formulario;
    }

    public String getCodigo() {
        return txtCodigo.getText().trim();
    }

    public String getNombre() {
        return txtNombre.getText().trim();
    }

    public String getTipoFrecuente() {
        return (String) cmbTipoFrecuente.getSelectedItem();
    }

    public JComboBox<String> getCmbTipoFrecuente() {
        return cmbTipoFrecuente;
    }

    public JButton getBtnPreparar() {
        return btnPreparar;
    }

    public JButton getBtnRetirar() {
        return btnRetirar;
    }

    public JTable getTablaRepuestos() {
        return tablaRepuestos;
    }

    public void limpiarFormulario() {
        cmbTipoFrecuente.setSelectedIndex(0);
        txtCodigo.setText("");
        txtNombre.setText("");
    }

    public void cargarTiposFrecuentes(String[] frecuentes) {
        String seleccionado = getTipoFrecuente();
        cmbTipoFrecuente.removeAllItems();
        cmbTipoFrecuente.addItem("Personalizado");
        for (int i = 0; i < frecuentes.length; i++) {
            cmbTipoFrecuente.addItem(frecuentes[i]);
        }
        if (seleccionado != null) {
            cmbTipoFrecuente.setSelectedItem(seleccionado);
        }
    }

    public void cargarDatos(String codigo, String nombre) {
        txtCodigo.setText(codigo);
        txtNombre.setText(nombre);
    }
}
