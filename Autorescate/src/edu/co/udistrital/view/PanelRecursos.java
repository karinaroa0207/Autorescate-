package edu.co.udistrital.view;

import edu.co.udistrital.model.EstadoUnidad;
import edu.co.udistrital.model.TipoVehiculo;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.ListSelectionModel;

public class PanelRecursos extends JPanel {

    private JComboBox<TipoVehiculo> cmbTipoUnidad;
    private JTextField txtZonaUnidad;
    private JButton btnRegistrarUnidad;
    private JLabel lblId;
    
    private JButton btnModificarUnidad;
    private JButton btnEliminarUnidad;
    private JComboBox<String> cmbEstadoUnidad;
    
    private JLabel lblEstadoUnidad;
    
    private JTable tablaUnidades;

    public PanelRecursos() {
        setLayout(new BorderLayout(10, 10));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(crearFormulario(), BorderLayout.NORTH);
        
        tablaUnidades = TablaFactory.crear(new String[]{"UUID", "Tipo", "Zona", "Estado", "Disponible"});
        tablaUnidades.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tablaUnidades), BorderLayout.CENTER);
    }

    private JPanel crearFormulario() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(5, 5));
        panelPrincipal.setBorder(javax.swing.BorderFactory.createTitledBorder("Gestión de Unidades"));

        lblId = new JLabel();
        lblId.setVisible(false);

        JPanel filaCampos = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

        cmbTipoUnidad = new JComboBox<>(TipoVehiculo.values());
        txtZonaUnidad = new JTextField(14);
        cmbEstadoUnidad = new JComboBox<>(new String[]{EstadoUnidad.DISPONIBLE.toString(), EstadoUnidad.MANTENIMIENTO.toString(), EstadoUnidad.OCUPADA.toString()});

        btnRegistrarUnidad = new JButton("Registrar unidad");
        btnModificarUnidad = new JButton("Modificar");
        btnEliminarUnidad = new JButton("Eliminar");
        lblEstadoUnidad = new JLabel("Estado");

        // Agregamos los elementos a la fila horizontal
        filaCampos.add(new JLabel("Tipo"));
        filaCampos.add(cmbTipoUnidad);
        filaCampos.add(new JLabel("Zona"));
        filaCampos.add(txtZonaUnidad);
        filaCampos.add(lblEstadoUnidad);
        filaCampos.add(cmbEstadoUnidad);
        filaCampos.add(btnRegistrarUnidad);
        filaCampos.add(btnModificarUnidad);
        filaCampos.add(btnEliminarUnidad);

        // Organizados en el panel principal: ID arriba, campos abajo
        panelPrincipal.add(lblId, BorderLayout.NORTH);
        panelPrincipal.add(filaCampos, BorderLayout.CENTER);

        return panelPrincipal;
    }

    public void modoRegistro() {
        btnRegistrarUnidad.setText("Registrar unidad");
        lblId.setVisible(false);
        lblId.setText("");
        cmbTipoUnidad.setEnabled(true);
        revalidate();
        repaint();
    }

    public void modoEdicion() {
        btnRegistrarUnidad.setText("Guardar Cambios");
        lblId.setVisible(true);
        cmbTipoUnidad.setEnabled(false);
        revalidate();
        repaint();
    }
    
    public String getIdUnidad() { return lblId.getText().trim(); }
    public String getTipoUnidad() { return (String) cmbTipoUnidad.getSelectedItem(); }
    public String getZonaUnidad() { return txtZonaUnidad.getText().trim(); }
    public String getEstadoUnidad() { return (String) cmbEstadoUnidad.getSelectedItem(); }

    public void setId(String id) { lblId.setText(id); }
    public void setZonaUnidad(String zona) { txtZonaUnidad.setText(zona); }
    public void setTipoUnidad(TipoVehiculo tipo) { cmbTipoUnidad.setSelectedItem(tipo); }
    public void setEstadoUnidad(String estado) { cmbEstadoUnidad.setSelectedItem(estado); }

    public JButton getBtnRegistrarUnidad() { return btnRegistrarUnidad; }
    public JButton getBtnModificarUnidad() { return btnModificarUnidad; }
    public JButton getBtnEliminarUnidad() { return btnEliminarUnidad; }
    public JTable getTablaUnidades() { return tablaUnidades; }
    
    public JComboBox<TipoVehiculo> getCmbTipoUnidad() { return cmbTipoUnidad; }

    public void limpiarFormularioUnidades() {
        txtZonaUnidad.setText("");
        cmbTipoUnidad.setSelectedIndex(0);
        cmbEstadoUnidad.setSelectedIndex(0);
    }
    
    /**
     * Retorna el UUID (String) de la fila que el usuario seleccionó con el mouse.
     * Si no hay ninguna fila seleccionada, retorna null.
     */
    public String getIdUnidadSeleccionadaEnTabla() {
        int filaSeleccionada = tablaUnidades.getSelectedRow();
        if (filaSeleccionada == -1) {
            return null;
        }
        // Asumiendo que el "UUID" está en la columna 0 según tu TablaFactory
        return tablaUnidades.getValueAt(filaSeleccionada, 0).toString();
    }   
}

