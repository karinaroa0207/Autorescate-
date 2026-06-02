package edu.co.udistrital.model;

public class CamionetaAsistencia extends Unidad {
    
    private boolean tienePlantaElectrica;

    public CamionetaAsistencia(String zona, boolean tienePlantaElectrica) {
        super(zona);
        this.tienePlantaElectrica = tienePlantaElectrica;
    }

    @Override
    public String getTipo() {
        return "Camioneta de Asistencia";
    }

    public boolean isTienePlantaElectrica() {
        return tienePlantaElectrica;
    }

    @Override
    public Unidad clonar() {
        return new CamionetaAsistencia(this.getZona(), tienePlantaElectrica);
    }
}