package edu.co.udistrital.view;

import edu.co.udistrital.model.Cliente;
import edu.co.udistrital.model.Lista;
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
import javax.swing.BoxLayout;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.text.DefaultEditorKit;

public class PanelSolicitudes extends JPanel {

    private JComboBox<Cliente> cmbCliente;
    private JTextField txtDescripcion;
    private JTextField txtZonaSolicitud;
    private JTextField txtPrioridad;
    private JComboBox<String> cmbTipoServicio;
    private JButton btnRegistrarSolicitud;
    private JButton btnAsignar;
    private JButton btnAsignarManual;
    private JTextField txtCerrarId;
    private JButton btnCerrarSolicitud;
    private JButton btnLimpiarCerrrar;
    private JTable tablaPendientes;
    private JTable tablaEjecucion;
    private JTable tablaCerrados;
    private PanelAsignacion panelAsignacion;
    private JTabbedPane tablas;
    private JTabbedPane tabs;

    public PanelSolicitudes() {
        setLayout(new BorderLayout(10, 10));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.panelAsignacion = new PanelAsignacion();

        tabs = new JTabbedPane();
        JPanel registro = new JPanel();

        registro.setLayout(new BorderLayout(10, 10));

        registro.add(crearFormulario(), BorderLayout.NORTH);
        registro.add(crearTablas(), BorderLayout.CENTER);

        tabs.addTab("Registro", registro);
        tabs.addTab("Asignacion", panelAsignacion);
        add(tabs, BorderLayout.CENTER);
    }

    private JPanel crearFormulario() {
        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBorder(javax.swing.BorderFactory.createTitledBorder("Registrar solicitud"));

        cmbCliente = new JComboBox<>();
        cmbCliente.setPrototypeDisplayValue(new Cliente("0000000000", "Cliente seleccionado", "", "", ""));
        txtDescripcion = new JTextField(20);
        txtZonaSolicitud = new JTextField(12);
        txtPrioridad = new JTextField("0", 5);
        cmbTipoServicio = new JComboBox<>(new String[]{
            "Cambio de llanta", "Paso de corriente", "Grua", "Apertura de puertas",
            "Combustible", "Revision mecanica", "Emergencia vial"
        });
        btnRegistrarSolicitud = new JButton("Registrar");
        btnAsignar = new JButton("Asignar siguiente automatico");
        btnAsignarManual = new JButton("Asignar siguiente manual");
        txtCerrarId = new JTextField(22);
        btnCerrarSolicitud = new JButton("Cerrar caso");
        btnLimpiarCerrrar = new JButton("Limpiar");
        btnLimpiarCerrrar.addActionListener(e -> txtCerrarId.setText(""));
        agregarMenuContextual(txtCerrarId);

        agregarCampo(formulario, "Cliente", cmbCliente, 0, 0);
        agregarCampo(formulario, "Descripcion", txtDescripcion, 2, 0);

        agregarCampo(formulario, "Zona", txtZonaSolicitud, 0, 1);
        agregarCampo(formulario, "Servicio", cmbTipoServicio, 2, 1);
        agregarCampo(formulario, "Prioridad", txtPrioridad, 4, 1);

        GridBagConstraints gbcBtn = new GridBagConstraints();
        gbcBtn.insets = new Insets(4, 10, 4, 4);
        gbcBtn.gridx = 6;
        gbcBtn.gridy = 1;
        gbcBtn.fill = GridBagConstraints.HORIZONTAL;
        formulario.add(btnRegistrarSolicitud, gbcBtn);

        JPanel acciones = new JPanel();
        acciones.setLayout(new BoxLayout(acciones, BoxLayout.Y_AXIS));
        acciones.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JPanel filaAsignar = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        filaAsignar.add(btnAsignar);
        filaAsignar.add(btnAsignarManual);

        JPanel filaCerrar = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        filaCerrar.add(new JLabel("ID a cerrar:"));
        filaCerrar.add(txtCerrarId);
        filaCerrar.add(btnCerrarSolicitud);
        filaCerrar.add(btnLimpiarCerrrar);

        acciones.add(filaAsignar);
        acciones.add(filaCerrar);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(formulario, BorderLayout.CENTER);
        panel.add(acciones, BorderLayout.SOUTH);

        return panel;
    }

    private JTabbedPane crearTablas() {
        tablaPendientes = TablaFactory.crear(new String[]{"ID", "Cliente", "Servicio", "Zona", "Prioridad", "Estado"});
        tablaEjecucion = TablaFactory.crear(new String[]{"ID", "Cliente", "Unidad", "Tecnico", "Kit", "Estado"});
        tablaCerrados = TablaFactory.crear(new String[]{"ID", "Cliente", "Servicio", "Unidad", "Tecnico", "Kit", "Cierre"});
        agregarMenuCopiarId(tablaPendientes);
        agregarMenuCopiarId(tablaEjecucion);
        agregarMenuCopiarId(tablaCerrados);
        tablas = new JTabbedPane();
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

    public Cliente getClienteSeleccionado() {
        return (Cliente) cmbCliente.getSelectedItem();
    }

    public String getDescripcion() {
        return txtDescripcion.getText().trim();
    }

    public String getZonaSolicitud() {
        return txtZonaSolicitud.getText().trim();
    }

    public String getTipoServicio() {
        return (String) cmbTipoServicio.getSelectedItem();
    }

    public String getPrioridad() {
        return txtPrioridad.getText().trim();
    }

    public String getCerrarId() {
        return txtCerrarId.getText().trim();
    }

    public JButton getBtnRegistrarSolicitud() {
        return btnRegistrarSolicitud;
    }

    public JButton getBtnAsignar() {
        return btnAsignar;
    }

    public JButton getBtnCerrarSolicitud() {
        return btnCerrarSolicitud;
    }     

    public JButton getBtnAsignarManual() {
        return btnAsignarManual;
    }

    public JTable getTablaPendientes() {
        return tablaPendientes;
    }

    public JTable getTablaEjecucion() {
        return tablaEjecucion;
    }

    public JTable getTablaCerrados() {
        return tablaCerrados;
    }

    public PanelAsignacion getPanelAsignacion() {
        return panelAsignacion;
    }

    public JTabbedPane getTabTablas() {
        return tablas;
    }

    public JTabbedPane getTabs() {
        return tabs;
    }

    public void limpiarFormulario() {
        txtDescripcion.setText("");
        txtZonaSolicitud.setText("");
        txtPrioridad.setText("0");
    }

    public void cargarClientes(Lista<Cliente> clientes) {
        Cliente seleccionado = getClienteSeleccionado();
        String documentoSeleccionado = seleccionado != null ? seleccionado.getDocumento() : null;

        cmbCliente.removeAllItems();
        for (int i = 0; i < clientes.size(); i++) {
            Cliente cliente = clientes.get(i);
            cmbCliente.addItem(cliente);
            if (documentoSeleccionado != null && documentoSeleccionado.equals(cliente.getDocumento())) {
                cmbCliente.setSelectedItem(cliente);
            }
        }
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

    private void agregarMenuContextual(JTextField campo) {
        JPopupMenu menu = new JPopupMenu();

        JMenuItem cortar = new JMenuItem(new DefaultEditorKit.CutAction());
        cortar.setText("Cortar");

        JMenuItem copiar = new JMenuItem(new DefaultEditorKit.CopyAction());
        copiar.setText("Copiar");

        JMenuItem pegar = new JMenuItem(new DefaultEditorKit.PasteAction());
        pegar.setText("Pegar");

        menu.add(cortar);
        menu.add(copiar);
        menu.add(pegar);

        campo.setComponentPopupMenu(menu);
    }
}
