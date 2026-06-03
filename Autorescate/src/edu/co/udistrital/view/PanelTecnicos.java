package edu.co.udistrital.view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.ListSelectionModel;

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
        tablaTecnicos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
        btnModificarTecnico = new JButton("Modificar");
        btnEliminarTecnico = new JButton("Eliminar");

        JButton btnLimpiar = new JButton("Limpiar formulario");
        btnLimpiar.addActionListener(
                e -> {
                    limpiarFormulario();
                    modoRegistro();
                }
        );

        formulario.add(new JLabel("ID"));
        formulario.add(txtIdTecnico);
        formulario.add(new JLabel("Nombre"));
        formulario.add(txtNombreTecnico);
        formulario.add(new JLabel("Especialidad"));
        formulario.add(txtEspecialidad);
        formulario.add(new JLabel("Zona"));
        formulario.add(txtZonaTecnico);
        formulario.add(btnRegistrarTecnico);
        formulario.add(btnModificarTecnico);
        formulario.add(btnEliminarTecnico);
        formulario.add(btnLimpiar);
        return formulario;
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

    public void modoRegistro() {
        btnRegistrarTecnico.setText("Registrar tecnico");
        txtIdTecnico.setEnabled(true);
        revalidate();
        repaint();
    }

    public void modoEdicion() {
        btnRegistrarTecnico.setText("Guardar cambios");
        txtIdTecnico.setEnabled(false);
        revalidate();
        repaint();
    }

    public void limpiarFormulario() {
        txtIdTecnico.setText("");
        txtNombreTecnico.setText("");
        txtEspecialidad.setText("");
        txtZonaTecnico.setText("");
    }

    public String getIdTecnicoSeleccionadoEnTabla() {
        int filaSeleccionada = tablaTecnicos.getSelectedRow();
        if (filaSeleccionada == -1) {
            return null;
        }
        return tablaTecnicos.getValueAt(filaSeleccionada, 0).toString();
    }
}
