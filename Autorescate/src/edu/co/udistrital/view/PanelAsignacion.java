package edu.co.udistrital.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class PanelAsignacion extends JPanel {

    private JTextField txtIdSolicitud;
    private JButton btnDespacharServicio;
    private JButton btnRefrescarDisponibilidad;
    private JLabel lblIdValor;
    private JLabel lblClienteValor;
    private JLabel lblServicioValor;
    private JLabel lblZonaValor;
    private JLabel lblPrioridadValor;

    private JTable tablaUnidades;
    private JTable tablaTecnicos;

    private JTabbedPane tabRecursos;

    public PanelAsignacion() {
        setLayout(new BorderLayout(10, 10));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(crearFormularioAsignacion(), BorderLayout.NORTH);
        add(crearSeccionVisualizacion(), BorderLayout.CENTER);
    }

    private JPanel crearFormularioAsignacion() {
        JPanel panelFicha = new JPanel(new GridBagLayout());
        panelFicha.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de la Solicitud Seleccionada"));

        lblIdValor = new JLabel("-");
        lblClienteValor = new JLabel("-");
        lblServicioValor = new JLabel("-");
        lblZonaValor = new JLabel("-");
        lblPrioridadValor = new JLabel("-");

        agregarCampo(panelFicha, "ID Solicitud:", lblIdValor, 0, 0);
        agregarCampo(panelFicha, "Cliente:", lblClienteValor, 2, 0);
        agregarCampo(panelFicha, "Servicio:", lblServicioValor, 0, 1);
        agregarCampo(panelFicha, "Zona:", lblZonaValor, 2, 1);
        agregarCampo(panelFicha, "Prioridad:", lblPrioridadValor, 4, 1);

        btnDespacharServicio = new JButton("Confirmar Despacho");
        btnRefrescarDisponibilidad = new JButton("Actualizar Datos");

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        acciones.add(btnRefrescarDisponibilidad);
        acciones.add(btnDespacharServicio);

        JPanel panelCompleto = new JPanel(new BorderLayout(5, 5));
        panelCompleto.add(panelFicha, BorderLayout.CENTER);
        panelCompleto.add(acciones, BorderLayout.SOUTH);

        return panelCompleto;
    }

    private JTabbedPane crearSeccionVisualizacion() {

        tablaUnidades = TablaFactory.crear(new String[]{
            "ID", "Zona", "Tipo"
        });
        tablaUnidades.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ((DefaultTableModel) tablaUnidades.getModel()).addRow(new String[]{
            "1", "Zona", "Tipo"
        });

        ((DefaultTableModel) tablaUnidades.getModel()).addRow(new String[]{
            "2", "Zona", "Tipo"
        });
        tablaTecnicos = TablaFactory.crear(new String[]{
            "ID", "Nombre", "Especialidad", "Zona"
        });

        ((DefaultTableModel) tablaUnidades.getModel()).addRow(new String[]{
            "1ID", "Nombre", "Especialidad", "Zona"
        });
        ((DefaultTableModel) tablaUnidades.getModel()).addRow(new String[]{
            "2ID", "Nombre", "Especialidad", "Zona"
        });
        tablaTecnicos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabRecursos = new JTabbedPane();
        tabRecursos.addTab("Unidades disponibles", new JScrollPane(tablaUnidades));
        tabRecursos.addTab("Tecnicos disponibles", new JScrollPane(tablaTecnicos));
        return tabRecursos;
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

    public void setTxtIdSolicitud(String id) {
        txtIdSolicitud.setText(id);
    }

    public String getIdSolicitudSeleccionada() {
        return lblIdValor.getText().trim();
    }

    public JButton getBtnDespacharServicio() {
        return btnDespacharServicio;
    }

    public JButton getBtnRefrescarDisponibilidad() {
        return btnRefrescarDisponibilidad;
    }
    

    public void limpiarTablas(JTable tabla) {
        ((DefaultTableModel) tablaTecnicos.getModel()).setRowCount(0);
        ((DefaultTableModel) tablaUnidades.getModel()).setRowCount(0);
    }

    public void agregarFila(String tabla, Object[] datos) {
        if (tabla.equals("tecnicos")) {
            ((DefaultTableModel) tablaTecnicos.getModel()).addRow(datos);
        } else if (tabla.equals("unidades")) {
            ((DefaultTableModel) tablaUnidades.getModel()).addRow(datos);
        }
    }

    public String getTecnicoSeleccionado() {
        int fila = tablaTecnicos.getSelectedRow();
        if (fila == -1) {
            return "";
        }
        return (String) tablaTecnicos.getValueAt(fila, 0);
    }

    public String getUnidadSeleccionado() {
        int fila = tablaUnidades.getSelectedRow();
        if (fila == -1) {
            return "";
        }
        return (String) tablaUnidades.getValueAt(fila, 0);
    }

    public void mostrarSolicitudActual(String id, String cliente, String servicio, String zona, String prioridad) {
        lblIdValor.setText(id);
        lblClienteValor.setText(cliente);
        lblServicioValor.setText(servicio);
        lblZonaValor.setText(zona);
        lblPrioridadValor.setText(prioridad);
    }

    public void limpiarSolicitudActual() {
        lblIdValor.setText("-");
        lblClienteValor.setText("-");
        lblServicioValor.setText("-");
        lblZonaValor.setText("-");
        lblPrioridadValor.setText("-");
    }

    public JTable getTablaUnidades() {
        return tablaUnidades;
    }

    public JTable getTablaTecnicos() {
        return tablaTecnicos;
    }
    
    
}
