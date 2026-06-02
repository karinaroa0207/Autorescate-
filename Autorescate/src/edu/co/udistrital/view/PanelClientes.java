package edu.co.udistrital.view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

public class PanelClientes extends JPanel {

    private JTextField txtDocumento;
    private JTextField txtNombre;
    private JTextField txtTelefono;
    private JTextField txtPlaca;
    private JTextField txtModelo;
    private JButton btnRegistrarCliente;
    private JButton btnLimpiar;
    private JTable tablaClientes;

    public PanelClientes() {
        setLayout(new BorderLayout(10, 10));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(crearFormulario(), BorderLayout.NORTH);
        tablaClientes = TablaFactory.crear(new String[]{"Documento", "Nombre", "Telefono", "Placa", "Modelo"});
        add(new JScrollPane(tablaClientes), BorderLayout.CENTER);
    }

    private JPanel crearFormulario() {
        JPanel formulario = new JPanel(new BorderLayout(8, 5));
        formulario.setBorder(javax.swing.BorderFactory.createTitledBorder("Registrar cliente"));

        txtDocumento = new JTextField(9);
        txtNombre = new JTextField(14);
        txtTelefono = new JTextField(10);
        txtPlaca = new JTextField(8);
        txtModelo = new JTextField(12);
        btnRegistrarCliente = new JButton("Registrar cliente");
        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        JPanel campos = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        campos.add(new JLabel("Documento"));
        campos.add(txtDocumento);
        campos.add(new JLabel("Nombre"));
        campos.add(txtNombre);
        campos.add(new JLabel("Telefono"));
        campos.add(txtTelefono);
        campos.add(new JLabel("Placa"));
        campos.add(txtPlaca);
        campos.add(new JLabel("Modelo"));
        campos.add(txtModelo);

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 5));
        acciones.add(btnRegistrarCliente);
        acciones.add(btnLimpiar);

        formulario.add(campos, BorderLayout.CENTER);
        formulario.add(acciones, BorderLayout.SOUTH);
        return formulario;
    }

    public String getDocumento() {
        return txtDocumento.getText().trim();
    }

    public String getNombre() {
        return txtNombre.getText().trim();
    }

    public String getTelefono() {
        return txtTelefono.getText().trim();
    }

    public String getPlaca() {
        return txtPlaca.getText().trim();
    }

    public String getModelo() {
        return txtModelo.getText().trim();
    }

    public JButton getBtnRegistrarCliente() {
        return btnRegistrarCliente;
    }

    public JTable getTablaClientes() {
        return tablaClientes;
    }

    public void limpiarFormulario() {
        txtDocumento.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        txtPlaca.setText("");
        txtModelo.setText("");
    }
}
