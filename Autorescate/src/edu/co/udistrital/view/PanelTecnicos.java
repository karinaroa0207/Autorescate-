package edu.co.udistrital.view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

public class PanelTecnicos extends JPanel {

    private JTextField txtIdTecnico;
    private JTextField txtNombreTecnico;
    private JTextField txtEspecialidad;
    private JTextField txtZonaTecnico;
    private JButton btnRegistrarTecnico;
    private JTable tablaTecnicos;

    public PanelTecnicos() {
        setLayout(new BorderLayout(10, 10));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(crearFormulario(), BorderLayout.NORTH);
        tablaTecnicos = TablaFactory.crear(new String[]{"ID", "Nombre", "Especialidad", "Zona", "Estado", "Libre"});
        add(new JScrollPane(tablaTecnicos), BorderLayout.CENTER);
    }

    private JPanel crearFormulario() {
        JPanel formulario = new JPanel(new FlowLayout(FlowLayout.LEFT));
        formulario.setBorder(javax.swing.BorderFactory.createTitledBorder("Registrar tecnico"));
        txtIdTecnico = new JTextField(8);
        txtNombreTecnico = new JTextField(12);
        txtEspecialidad = new JTextField(12);
        txtZonaTecnico = new JTextField(10);
        btnRegistrarTecnico = new JButton("Registrar tecnico");

        formulario.add(new JLabel("ID"));
        formulario.add(txtIdTecnico);
        formulario.add(new JLabel("Nombre"));
        formulario.add(txtNombreTecnico);
        formulario.add(new JLabel("Especialidad"));
        formulario.add(txtEspecialidad);
        formulario.add(new JLabel("Zona"));
        formulario.add(txtZonaTecnico);
        formulario.add(btnRegistrarTecnico);
        return formulario;
    }

    public String getIdTecnico() { return txtIdTecnico.getText().trim(); }
    public String getNombreTecnico() { return txtNombreTecnico.getText().trim(); }
    public String getEspecialidad() { return txtEspecialidad.getText().trim(); }
    public String getZonaTecnico() { return txtZonaTecnico.getText().trim(); }
    public JButton getBtnRegistrarTecnico() { return btnRegistrarTecnico; }
    public JTable getTablaTecnicos() { return tablaTecnicos; }
}
