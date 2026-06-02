package edu.co.udistrital.model;

public class CamionetaAsistencia extends Unidad {
    
    private boolean tienePlantaElectrica;

    public CamionetaAsistencia(String zona, boolean tienePlantaElectrica) {
        super(zona);
        this.tienePlantaElectrica = tienePlantaElectrica;
    }

    @Override
    public TipoVehiculo getTipo() {
        return TipoVehiculo.CAMIONETA;
    }

    public boolean isTienePlantaElectrica() {
        return tienePlantaElectrica;
    }

    @Override
    public Unidad crearCopiaBase() {
        return new CamionetaAsistencia(this.getZona(), tienePlantaElectrica);
    }
}