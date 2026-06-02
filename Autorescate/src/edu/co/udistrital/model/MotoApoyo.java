package edu.co.udistrital.model;

public class MotoApoyo extends Unidad {
    private boolean tieneCajaHerramientas;

    public MotoApoyo(String zona, boolean tieneCajaHerramientas) {
        super(zona);
        this.tieneCajaHerramientas = tieneCajaHerramientas;
    }

    @Override
    public TipoVehiculo getTipo() {
        return TipoVehiculo.MOTO_DE_APOYO;
    }

    public boolean isTieneCajaHerramientas() {
        return tieneCajaHerramientas;
    }

    @Override
    public Unidad crearCopiaBase() {
        return new MotoApoyo(this.getZona(), tieneCajaHerramientas);
    }
}