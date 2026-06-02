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
    private JButton btnModificarCliente;
    private JButton btnEliminarCliente;
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

        JPanel filaCampos1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        filaCampos1.add(new JLabel("Documento"));
        filaCampos1.add(txtDocumento);
        filaCampos1.add(new JLabel("Nombre"));
        filaCampos1.add(txtNombre);
        filaCampos1.add(new JLabel("Telefono"));
        filaCampos1.add(txtTelefono);

        JPanel filaCampos2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        filaCampos2.add(new JLabel("Placa"));
        filaCampos2.add(txtPlaca);
        filaCampos2.add(new JLabel("Modelo"));
        filaCampos2.add(txtModelo);

        JPanel campos = new JPanel(new BorderLayout(0, 5));
        campos.add(filaCampos1, BorderLayout.NORTH);
        campos.add(filaCampos2, BorderLayout.SOUTH);

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 5));
        btnModificarCliente = new JButton("Modificar");
        btnEliminarCliente = new JButton("Eliminar");
        acciones.add(btnRegistrarCliente);
        acciones.add(btnModificarCliente);
        acciones.add(btnEliminarCliente);
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

    public JButton getBtnModificarCliente() { return btnModificarCliente; }

    public JButton getBtnEliminarCliente() { return btnEliminarCliente; }

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

    public void modoRegistro() {
        btnRegistrarCliente.setText("Registrar cliente");
        txtDocumento.setEditable(true);
        revalidate();
        repaint();
    }

    public void modoEdicion() {
        btnRegistrarCliente.setText("Guardar cambios");
        txtDocumento.setEditable(false);
        revalidate();
        repaint();
    }

    public void setDocumento(String documento) { txtDocumento.setText(documento); }
    public void setNombre(String nombre) { txtNombre.setText(nombre); }
    public void setTelefono(String telefono) { txtTelefono.setText(telefono); }
    public void setPlaca(String placa) { txtPlaca.setText(placa); }
    public void setModelo(String modelo) { txtModelo.setText(modelo); }

    public String getDocumentoSeleccionadoEnTabla() {
        int fila = tablaClientes.getSelectedRow();
        if (fila == -1) return null;
        return tablaClientes.getValueAt(fila, 0).toString();
    }
}
