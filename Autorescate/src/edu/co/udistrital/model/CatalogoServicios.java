package edu.co.udistrital.model;

public class CatalogoServicios {

    private Lista<String> tiposServicio;

    public CatalogoServicios() {
        tiposServicio = new ArregloLista<>();
        tiposServicio.add("Cambio de llanta");
        tiposServicio.add("Paso de corriente");
        tiposServicio.add("Grua");
        tiposServicio.add("Apertura de puertas");
        tiposServicio.add("Combustible");
        tiposServicio.add("Revision mecanica");
        tiposServicio.add("Emergencia vial");
    }

    public Lista<String> obtenerTiposServicio() {
        return tiposServicio;
    }
}
