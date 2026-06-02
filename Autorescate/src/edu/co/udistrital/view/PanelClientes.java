package edu.co.udistrital.view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.ListSelectionModel;

public class PanelClientes extends JPanel {

    private JTextField txtDocumento;
    private JTextField txtNombre;
    private JTextField txtTelefono;
    private JTextField txtPlaca;
    private JTextField txtModelo;
    private JButton btnRegistrarCliente;
    private JLabel lblId;
    private JButton btnModificarCliente;
    private JButton btnEliminarCliente;
    private JButton btnLimpiar;
    private JTable tablaClientes;

    public PanelClientes() {
        setLayout(new BorderLayout(10, 10));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(crearFormulario(), BorderLayout.NORTH);
        
        tablaClientes = TablaFactory.crear(new String[]{"ID", "Documento", "Nombre", "Teléfono", "Placa", "Modelo"});
        tablaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tablaClientes), BorderLayout.CENTER);
    }

    private JPanel crearFormulario() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(5, 5));
        panelPrincipal.setBorder(javax.swing.BorderFactory.createTitledBorder("Gestión de Clientes"));

        lblId = new JLabel();
        lblId.setVisible(false);

        JPanel panelCampos = new JPanel(new GridLayout(3, 1, 5, 5));
        JPanel filaUno = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        JPanel filaDos = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        JPanel filaTres = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

        txtDocumento = new JTextField(12);
        txtNombre = new JTextField(12);
        txtTelefono = new JTextField(12);
        txtPlaca = new JTextField(12);
        txtModelo = new JTextField(12);

        btnRegistrarCliente = new JButton("Registrar cliente");
        btnModificarCliente = new JButton("Modificar");
        btnEliminarCliente = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");

        filaUno.add(new JLabel("Documento"));
        filaUno.add(txtDocumento);
        filaUno.add(new JLabel("Nombre"));
        filaUno.add(txtNombre);
        filaUno.add(new JLabel("Teléfono"));
        filaUno.add(txtTelefono);

        filaDos.add(new JLabel("Placa"));
        filaDos.add(txtPlaca);
        filaDos.add(new JLabel("Modelo"));
        filaDos.add(txtModelo);

        filaTres.add(btnRegistrarCliente);
        filaTres.add(btnModificarCliente);
        filaTres.add(btnEliminarCliente);
        filaTres.add(btnLimpiar);

        panelCampos.add(filaUno);
        panelCampos.add(filaDos);
        panelCampos.add(filaTres);

        panelPrincipal.add(lblId, BorderLayout.NORTH);
        panelPrincipal.add(panelCampos, BorderLayout.CENTER);

        return panelPrincipal;
    }

    public void modoRegistro() {
        btnRegistrarCliente.setText("Registrar cliente");
        lblId.setVisible(false);
        lblId.setText("");
        txtDocumento.setEnabled(true);
        revalidate();
        repaint();
    }

    public void modoEdicion() {
        btnRegistrarCliente.setText("Guardar Cambios");
        lblId.setVisible(true);
        txtDocumento.setEnabled(false);
        revalidate();
        repaint();
    }
    
    public String getIdCliente() { return lblId.getText().trim(); }
    public String getDocumento() { return txtDocumento.getText().trim(); }
    public String getNombre() { return txtNombre.getText().trim(); }
    public String getTelefono() { return txtTelefono.getText().trim(); }
    public String getPlaca() { return txtPlaca.getText().trim(); }
    public String getModelo() { return txtModelo.getText().trim(); }

    public void setId(String id) { lblId.setText(id); }
    public void setDocumento(String doc) { txtDocumento.setText(doc); }
    public void setNombre(String nom) { txtNombre.setText(nom); }
    public void setTelefono(String tel) { txtTelefono.setText(tel); }
    public void setPlaca(String placa) { txtPlaca.setText(placa); }
    public void setModelo(String modelo) { txtModelo.setText(modelo); }

    public JButton getBtnRegistrarCliente() { return btnRegistrarCliente; }
    public JButton getBtnModificarCliente() { return btnModificarCliente; }
    public JButton getBtnEliminarCliente() { return btnEliminarCliente; }
    public JButton getBtnLimpiar() { return btnLimpiar; }
    public JTable getTablaClientes() { return tablaClientes; }

    public void limpiarFormularioCliente() {
        txtDocumento.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        txtPlaca.setText("");
        txtModelo.setText("");
    }
    
    public String getIdClienteSeleccionadoEnTabla() {
        int filaSeleccionada = tablaClientes.getSelectedRow();
        if (filaSeleccionada == -1) {
            return null;
        }
        return tablaClientes.getValueAt(filaSeleccionada, 0).toString();
    }
}
