package edu.co.udistrital.view;

import edu.co.udistrital.model.EstadoUnidad;
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
        add(new JScrollPane(tablaUnidades), BorderLayout.CENTER);
    }

    private JPanel crearFormulario() {
        // Contenedor principal del formulario con un borde de título
        JPanel panelPrincipal = new JPanel(new BorderLayout(5, 5));
        panelPrincipal.setBorder(javax.swing.BorderFactory.createTitledBorder("Gestión de Unidades"));

        // Configuración del Label del ID (Estará arriba)
        lblId = new JLabel();
        lblId.setVisible(false); // Oculto al inicio

        // Panel interno para los campos y botones (Fila horizontal antigua)
        JPanel filaCampos = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

        cmbTipoUnidad = new JComboBox<>(new String[]{"Grua", "Moto de apoyo", "Camioneta", "Vehiculo liviano"});
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
        lblId.setText(""); // Limpiamos el texto del ID
        //cmbTipoUnidad.setEnabled(true);

        // IMPORTANTE: Avisarle a Swing que el diseño cambió físicamente para que oculte el espacio vacío
        revalidate();
        repaint();
    }

    public void modoEdicion() {
        btnRegistrarUnidad.setText("Guardar Cambios");
        lblId.setVisible(true);
        //cmbTipoUnidad.setEnabled(false);
        revalidate();
        repaint();
    }
    
    public String getIdUnidad() { return lblId.getText().trim(); }
    public String getTipoUnidad() { return (String) cmbTipoUnidad.getSelectedItem(); }
    public String getZonaUnidad() { return txtZonaUnidad.getText().trim(); }
    public String getEstadoUnidad() { return (String) cmbEstadoUnidad.getSelectedItem(); }

    public void setId(String id) { lblId.setText(id); }
    public void setZonaUnidad(String zona) { txtZonaUnidad.setText(zona); }
    public void setTipoUnidad(String tipo) { cmbTipoUnidad.setSelectedItem(tipo); }
    public void setEstadoUnidad(String estado) { cmbEstadoUnidad.setSelectedItem(estado); }

    public JButton getBtnRegistrarUnidad() { return btnRegistrarUnidad; }
    public JButton getBtnModificarUnidad() { return btnModificarUnidad; }
    public JButton getBtnEliminarUnidad() { return btnEliminarUnidad; }
    public JTable getTablaUnidades() { return tablaUnidades; }
    
    // Permite al controlador bloquear la caja de texto del Tipo al editar
    public JComboBox<String> getCmbTipoUnidad() { return cmbTipoUnidad; }

    /**
     * Limpia los campos de texto y restablece los combos del formulario
     */
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

