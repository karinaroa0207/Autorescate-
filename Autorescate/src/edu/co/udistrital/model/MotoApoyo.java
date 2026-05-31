package edu.co.udistrital.model;

public class MotoApoyo extends Unidad {
    private boolean tieneCajaHerramientas;

    public MotoApoyo(String zona, boolean tieneCajaHerramientas) {
        super(zona);
        this.tieneCajaHerramientas = tieneCajaHerramientas;
    }

    @Override
    public String getTipo() {
        return "Moto de Apoyo";
    }

    public boolean isTieneCajaHerramientas() {
        return tieneCajaHerramientas;
    }
}