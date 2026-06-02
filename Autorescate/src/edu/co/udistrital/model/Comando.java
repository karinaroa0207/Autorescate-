package edu.co.udistrital.model;

public interface Comando {
    void ejecutar();
    void deshacer();
    String getDescripcion();
}
