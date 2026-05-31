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

public class PanelRecursos extends JPanel {

    private JComboBox<String> cmbTipoUnidad;
    private JTextField txtZonaUnidad;
    private JButton btnRegistrarUnidad;
    private JTable tablaUnidades;

    public PanelRecursos() {
        setLayout(new BorderLayout(10, 10));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(crearFormulario(), BorderLayout.NORTH);
        tablaUnidades = TablaFactory.crear(new String[]{"UUID", "Tipo", "Zona", "Estado", "Disponible"});
        add(new JScrollPane(tablaUnidades), BorderLayout.CENTER);
    }

    private JPanel crearFormulario() {
        JPanel formulario = new JPanel(new FlowLayout(FlowLayout.LEFT));
        formulario.setBorder(javax.swing.BorderFactory.createTitledBorder("Registrar unidad"));
        cmbTipoUnidad = new JComboBox<>(new String[]{"Grua", "Moto de apoyo", "Camioneta", "Vehiculo liviano"});
        txtZonaUnidad = new JTextField(14);
        btnRegistrarUnidad = new JButton("Registrar unidad");

        formulario.add(new JLabel("Tipo"));
        formulario.add(cmbTipoUnidad);
        formulario.add(new JLabel("Zona"));
        formulario.add(txtZonaUnidad);
        formulario.add(btnRegistrarUnidad);
        return formulario;
    }

    public String getTipoUnidad() { return (String) cmbTipoUnidad.getSelectedItem(); }
    public String getZonaUnidad() { return txtZonaUnidad.getText().trim(); }
    public JButton getBtnRegistrarUnidad() { return btnRegistrarUnidad; }
    public JTable getTablaUnidades() { return tablaUnidades; }
}
