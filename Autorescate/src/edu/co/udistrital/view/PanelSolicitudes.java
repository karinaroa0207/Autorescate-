package edu.co.udistrital.view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class PanelSolicitudes extends JPanel {

    private JTextField txtCliente;
    private JTextField txtDescripcion;
    private JTextField txtZonaSolicitud;
    private JTextField txtPrioridad;
    private JComboBox<String> cmbTipoServicio;
    private JButton btnRegistrarSolicitud;
    private JButton btnAsignar;
    private JTextField txtCerrarId;
    private JButton btnCerrarSolicitud;
    private JTable tablaPendientes;
    private JTable tablaEjecucion;
    private JTable tablaCerrados;

    public PanelSolicitudes() {
        setLayout(new BorderLayout(10, 10));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(crearFormulario(), BorderLayout.NORTH);
        add(crearTablas(), BorderLayout.CENTER);
    }

    private JPanel crearFormulario() {
        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBorder(javax.swing.BorderFactory.createTitledBorder("Registrar solicitud"));

        txtCliente = new JTextField(15);
        txtDescripcion = new JTextField(22);
        txtZonaSolicitud = new JTextField(12);
        txtPrioridad = new JTextField("0", 5);
        cmbTipoServicio = new JComboBox<>(new String[]{
            "Cambio de llanta", "Paso de corriente", "Grua", "Apertura de puertas",
            "Combustible", "Revision mecanica", "Emergencia vial"
        });
        btnRegistrarSolicitud = new JButton("Registrar");
        btnAsignar = new JButton("Asignar siguiente");
        txtCerrarId = new JTextField(5);
        btnCerrarSolicitud = new JButton("Cerrar caso");

        agregarCampo(formulario, "Cliente", txtCliente, 0, 0);
        agregarCampo(formulario, "Descripcion", txtDescripcion, 2, 0);
        agregarCampo(formulario, "Zona", txtZonaSolicitud, 0, 1);
        agregarCampo(formulario, "Servicio", cmbTipoServicio, 2, 1);
        agregarCampo(formulario, "Prioridad", txtPrioridad, 4, 1);

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        acciones.add(btnRegistrarSolicitud);
        acciones.add(btnAsignar);
        acciones.add(new JLabel("ID"));
        acciones.add(txtCerrarId);
        acciones.add(btnCerrarSolicitud);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(formulario, BorderLayout.CENTER);
        panel.add(acciones, BorderLayout.SOUTH);
        return panel;
    }

    private JTabbedPane crearTablas() {
        tablaPendientes = TablaFactory.crear(new String[]{"ID", "Cliente", "Servicio", "Zona", "Prioridad", "Estado"});
        tablaEjecucion = TablaFactory.crear(new String[]{"ID", "Cliente", "Unidad", "Tecnico", "Estado"});
        tablaCerrados = TablaFactory.crear(new String[]{"ID", "Cliente", "Servicio", "Unidad", "Tecnico", "Cierre"});
        agregarMenuCopiarId(tablaPendientes);
        agregarMenuCopiarId(tablaEjecucion);
        agregarMenuCopiarId(tablaCerrados);
        JTabbedPane tablas = new JTabbedPane();
        tablas.addTab("Pendientes", new JScrollPane(tablaPendientes));
        tablas.addTab("En ejecucion", new JScrollPane(tablaEjecucion));
        tablas.addTab("Atendidas", new JScrollPane(tablaCerrados));
        return tablas;
    }

    private void agregarCampo(JPanel panel, String etiqueta, java.awt.Component campo, int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = x;
        gbc.gridy = y;
        panel.add(new JLabel(etiqueta), gbc);

        gbc.gridx = x + 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(campo, gbc);
    }        

    public String getCliente() { return txtCliente.getText().trim(); }
    public String getDescripcion() { return txtDescripcion.getText().trim(); }
    public String getZonaSolicitud() { return txtZonaSolicitud.getText().trim(); }
    public String getTipoServicio() { return (String) cmbTipoServicio.getSelectedItem(); }
    public String getPrioridad() { return txtPrioridad.getText().trim(); }
    public String getCerrarId() { return txtCerrarId.getText().trim(); }
    public JButton getBtnRegistrarSolicitud() { return btnRegistrarSolicitud; }
    public JButton getBtnAsignar() { return btnAsignar; }
    public JButton getBtnCerrarSolicitud() { return btnCerrarSolicitud; }
    public JTable getTablaPendientes() { return tablaPendientes; }
    public JTable getTablaEjecucion() { return tablaEjecucion; }
    public JTable getTablaCerrados() { return tablaCerrados; }

    public void limpiarFormulario() {
        txtCliente.setText("");
        txtDescripcion.setText("");
        txtZonaSolicitud.setText("");
        txtPrioridad.setText("0");
    }
    
    private void agregarMenuCopiarId(JTable tabla) {

        JPopupMenu menu = new JPopupMenu();
        JMenuItem copiar = new JMenuItem("Copiar ID");
        menu.add(copiar);

        copiar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();

            if (fila >= 0) {
                String id = tabla.getValueAt(fila, 0).toString();

                Toolkit.getDefaultToolkit()
                        .getSystemClipboard()
                        .setContents(new StringSelection(id), null);
            }
        });

        tabla.setComponentPopupMenu(menu);

        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int fila = tabla.rowAtPoint(e.getPoint());

                if (fila >= 0) {
                    tabla.setRowSelectionInterval(fila, fila);
                }
            }
        });
    }
}
