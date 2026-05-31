package edu.co.udistrital.view;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

public class VentanaPrincipal extends JFrame {

    private PanelSolicitudes panelSolicitudes;
    private PanelRecursos panelRecursos;
    private PanelTecnicos panelTecnicos;
    private PanelKits panelKits;
    private PanelHistorial panelHistorial;
    private JButton btnDeshacer;
    private JButton btnExportar;
    private JButton btnActualizar;
    private JTextArea txtMensajes;

    public VentanaPrincipal() {
        setTitle("AutoRescate 24/7 - Centro de Operaciones");
        setSize(1080, 720);
        setMinimumSize(new Dimension(980, 640));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        panelSolicitudes = new PanelSolicitudes();
        panelRecursos = new PanelRecursos();
        panelTecnicos = new PanelTecnicos();
        panelKits = new PanelKits();
        panelHistorial = new PanelHistorial();

        add(crearEncabezado(), BorderLayout.NORTH);
        add(crearPestanas(), BorderLayout.CENTER);
        add(crearBarraInferior(), BorderLayout.SOUTH);
    }

    private JPanel crearEncabezado() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(31, 41, 55));
        panel.setBorder(BorderFactory.createEmptyBorder(14, 18, 14, 18));

        JLabel titulo = new JLabel("AutoRescate 24/7");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));

        JLabel subtitulo = new JLabel("Centro de operaciones");
        subtitulo.setForeground(new Color(209, 213, 219));

        panel.add(titulo, BorderLayout.WEST);
        panel.add(subtitulo, BorderLayout.EAST);
        return panel;
    }

    private JTabbedPane crearPestanas() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Solicitudes", panelSolicitudes);
        tabs.addTab("Recursos", panelRecursos);
        tabs.addTab("Tecnicos", panelTecnicos);
        tabs.addTab("Kits", panelKits);
        tabs.addTab("Historial", panelHistorial);
        return tabs;
    }

    private JPanel crearBarraInferior() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(8, 10, 10, 10));

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnDeshacer = new JButton("Deshacer ultima operacion");
        btnExportar = new JButton("Generar CSV");
        btnActualizar = new JButton("Actualizar");
        acciones.add(btnDeshacer);
        acciones.add(btnExportar);
        acciones.add(btnActualizar);

        txtMensajes = new JTextArea(4, 60);
        txtMensajes.setEditable(false);
        txtMensajes.setFont(new Font("Consolas", Font.PLAIN, 12));

        panel.add(acciones, BorderLayout.NORTH);
        panel.add(new JScrollPane(txtMensajes), BorderLayout.CENTER);
        return panel;
    }

    public void limpiarTabla(JTable tabla) {
        ((DefaultTableModel) tabla.getModel()).setRowCount(0);
    }

    public void agregarFila(JTable tabla, Object[] datos) {
        ((DefaultTableModel) tabla.getModel()).addRow(datos);
    }

    public String getCliente() { return panelSolicitudes.getCliente(); }
    public String getDescripcion() { return panelSolicitudes.getDescripcion(); }
    public String getZonaSolicitud() { return panelSolicitudes.getZonaSolicitud(); }
    public String getTipoServicio() { return panelSolicitudes.getTipoServicio(); }
    public String getPrioridad() { return panelSolicitudes.getPrioridad(); }
    public String getCerrarId() { return panelSolicitudes.getCerrarId(); }
    public String getTipoUnidad() { return panelRecursos.getTipoUnidad(); }
    public String getZonaUnidad() { return panelRecursos.getZonaUnidad(); }
    public String getIdTecnico() { return panelTecnicos.getIdTecnico(); }
    public String getNombreTecnico() { return panelTecnicos.getNombreTecnico(); }
    public String getEspecialidad() { return panelTecnicos.getEspecialidad(); }
    public String getZonaTecnico() { return panelTecnicos.getZonaTecnico(); }
    public String getCodigoKit() { return panelKits.getCodigoKit(); }

    public JButton getBtnRegistrarSolicitud() { return panelSolicitudes.getBtnRegistrarSolicitud(); }
    public JButton getBtnAsignar() { return panelSolicitudes.getBtnAsignar(); }
    public JButton getBtnCerrarSolicitud() { return panelSolicitudes.getBtnCerrarSolicitud(); }
    public JButton getBtnRegistrarUnidad() { return panelRecursos.getBtnRegistrarUnidad(); }
    public JButton getBtnRegistrarTecnico() { return panelTecnicos.getBtnRegistrarTecnico(); }
    public JButton getBtnRecibirKit() { return panelKits.getBtnRecibirKit(); }
    public JButton getBtnDespacharKit() { return panelKits.getBtnDespacharKit(); }
    public JButton getBtnDeshacer() { return btnDeshacer; }
    public JButton getBtnExportar() { return btnExportar; }
    public JButton getBtnActualizar() { return btnActualizar; }

    public JTable getTablaPendientes() { return panelSolicitudes.getTablaPendientes(); }
    public JTable getTablaEjecucion() { return panelSolicitudes.getTablaEjecucion(); }
    public JTable getTablaCerrados() { return panelSolicitudes.getTablaCerrados(); }
    public JTable getTablaUnidades() { return panelRecursos.getTablaUnidades(); }
    public JTable getTablaTecnicos() { return panelTecnicos.getTablaTecnicos(); }
    public JTable getTablaKits() { return panelKits.getTablaKits(); }
    public JTable getTablaHistorial() { return panelHistorial.getTablaHistorial(); }

    public void limpiarFormularioSolicitud() {
        panelSolicitudes.limpiarFormulario();
    }

    public void agregarMensaje(String mensaje) {
        txtMensajes.append("> " + mensaje + "\n");
        txtMensajes.setCaretPosition(txtMensajes.getDocument().getLength());
    }
}
