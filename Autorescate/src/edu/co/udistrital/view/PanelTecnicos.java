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
    private JButton btnModificarTecnico;
    private JButton btnEliminarTecnico;
    private JTable tablaTecnicos;

    public PanelTecnicos() {
        setLayout(new BorderLayout(10, 10));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(crearFormulario(), BorderLayout.NORTH);
        tablaTecnicos = TablaFactory.crear(new String[]{"ID", "Nombre", "Especialidad", "Zona", "Estado", "Libre"});
        tablaTecnicos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tablaTecnicos), BorderLayout.CENTER);
    }

    private JPanel crearFormulario() {
        JPanel formulario = new JPanel(new BorderLayout(5, 5));
        formulario.setBorder(javax.swing.BorderFactory.createTitledBorder("Registrar tecnico"));
        txtIdTecnico = new JTextField(8);
        txtNombreTecnico = new JTextField(12);
        txtEspecialidad = new JTextField(12);
        txtZonaTecnico = new JTextField(10);
        btnRegistrarTecnico = new JButton("Registrar tecnico");
        btnModificarTecnico = new JButton("Modificar");
        btnEliminarTecnico = new JButton("Eliminar");

        JButton btnLimpiar = new JButton("Limpiar formulario");
        btnLimpiar.addActionListener(
                e -> limpiarFormularioTecnicos()
        );

        JPanel campos = new JPanel(new FlowLayout(FlowLayout.LEFT));
        campos.add(new JLabel("ID"));
        campos.add(txtIdTecnico);
        campos.add(new JLabel("Nombre"));
        campos.add(txtNombreTecnico);
        campos.add(new JLabel("Especialidad"));
        campos.add(txtEspecialidad);
        campos.add(new JLabel("Zona"));
        campos.add(txtZonaTecnico);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botones.add(btnRegistrarTecnico);
        botones.add(btnModificarTecnico);
        botones.add(btnEliminarTecnico);
        botones.add(btnLimpiar);

        formulario.add(campos, BorderLayout.NORTH);
        formulario.add(botones, BorderLayout.SOUTH);
        return formulario;
    }

    public void modoRegistro() {
        btnRegistrarTecnico.setText("Registrar tecnico");
        txtIdTecnico.setEditable(true);
        btnModificarTecnico.setEnabled(true);
        btnEliminarTecnico.setEnabled(true);
        revalidate();
        repaint();
    }

    public void modoEdicion() {
        btnRegistrarTecnico.setText("Guardar cambios");
        txtIdTecnico.setEditable(false);
        revalidate();
        repaint();
    }

    public String getIdTecnico() {
        return txtIdTecnico.getText().trim();
    }

    public String getNombreTecnico() {
        return txtNombreTecnico.getText().trim();
    }

    public String getEspecialidad() {
        return txtEspecialidad.getText().trim();
    }

    public String getZonaTecnico() {
        return txtZonaTecnico.getText().trim();
    }

    public void setIdTecnico(String id) {
        txtIdTecnico.setText(id);
    }

    public void setNombreTecnico(String nombre) {
        txtNombreTecnico.setText(nombre);
    }

    public void setEspecialidad(String especialidad) {
        txtEspecialidad.setText(especialidad);
    }

    public void setZonaTecnico(String zona) {
        txtZonaTecnico.setText(zona);
    }

    public void limpiarFormularioTecnicos() {
        txtIdTecnico.setText("");
        txtNombreTecnico.setText("");
        txtEspecialidad.setText("");
        txtZonaTecnico.setText("");
        modoRegistro();
    }

    public JButton getBtnRegistrarTecnico() {
        return btnRegistrarTecnico;
    }

    public JButton getBtnModificarTecnico() {
        return btnModificarTecnico;
    }

    public JButton getBtnEliminarTecnico() {
        return btnEliminarTecnico;
    }

    public JTable getTablaTecnicos() {
        return tablaTecnicos;
    }

    public String getIdTecnicoSeleccionadoEnTabla() {
        int fila = tablaTecnicos.getSelectedRow();
        if (fila == -1) {
            return null;
        }
        return tablaTecnicos.getValueAt(fila, 0).toString();
    }
}
