package edu.co.udistrital.model;

public class CatalogoRepuestos {

    private Lista<Repuesto> frecuentes;

    public CatalogoRepuestos() {
        frecuentes = new ArregloLista<>();
        frecuentes.add(new Repuesto("REP-BAT", "Bateria auxiliar"));
        frecuentes.add(new Repuesto("REP-FUS", "Fusible de emergencia"));
        frecuentes.add(new Repuesto("REP-CAB", "Cable pasa corriente"));
        frecuentes.add(new Repuesto("REP-SEL", "Sellante para llanta"));
        frecuentes.add(new Repuesto("REP-BOM", "Bombillo de repuesto"));
        frecuentes.add(new Repuesto("REP-COM", "Combustible de emergencia"));
        frecuentes.add(new Repuesto("REP-HER", "Herramienta rapida"));
    }

    public Lista<Repuesto> obtenerFrecuentes() {
        return frecuentes;
    }

    public Repuesto buscarPorNombre(String nombre) {
        if (nombre == null) {
            return null;
        }
        for (int i = 0; i < frecuentes.size(); i++) {
            Repuesto repuesto = frecuentes.get(i);
            if (repuesto.getNombre().equals(nombre)) {
                return new Repuesto(repuesto.getCodigoRepuesto(), repuesto.getNombre());
            }
        }
        return null;
    }
}
