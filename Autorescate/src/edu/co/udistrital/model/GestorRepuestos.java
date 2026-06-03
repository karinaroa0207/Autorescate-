package edu.co.udistrital.model;

public class GestorRepuestos {

    private Pila<Repuesto> repuestosPreparados;
    private Lista<Repuesto> repuestosRegistrados;

    public GestorRepuestos() {
        repuestosPreparados = new Pila<>();
        repuestosRegistrados = new ArregloLista<>();
    }

    public void prepararRepuesto(Repuesto repuesto) {
        repuestosPreparados.apilar(repuesto);
        repuestosRegistrados.add(repuesto);
    }

    public Repuesto despacharRepuesto() {
        return repuestosPreparados.desapilar();
    }

    public void revertirDespacho(Repuesto repuesto) {
        if (repuesto != null) {
            repuestosPreparados.apilar(repuesto);
        }
    }

    public void revertirPreparacion() {
        Repuesto repuesto = repuestosPreparados.desapilar();
        if (repuesto == null) {
            return;
        }
        for (int i = 0; i < repuestosRegistrados.size(); i++) {
            if (repuestosRegistrados.get(i) == repuesto) {
                repuestosRegistrados.remove(i);
                return;
            }
        }
    }

    public Lista<Repuesto> obtenerPreparados() {
        Lista<Repuesto> lista = new ArregloLista<>();
        repuestosPreparados.recorrer((repuesto) -> {
            lista.add(repuesto);
        });
        return lista;
    }

    public int getCantidadPreparados() {
        return repuestosPreparados.getTamano();
    }
}
