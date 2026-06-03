package edu.co.udistrital.view;

public interface VisualizadorMensajes {

    void mostrarMensajeError(String mensaje, String titulo);

    void mostrarMensajeError(String mensaje);

    void mostrarMensaje(String mensaje);

    void mostrarMensaje(String mensaje, String titulo);

    void mostrarMensajeWarning(String mensaje);

    void mostrarMensajeWarning(String mensaje, String titulo);    
    
    void agregarMensaje(String mensaje);
}