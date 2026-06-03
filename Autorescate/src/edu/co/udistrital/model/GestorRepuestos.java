package edu.co.udistrital.model;

public class GestorRepuestos {

    private Pila<Repuesto>[] estantes;
    private String[] nombresTipos;

    @SuppressWarnings("unchecked")
    public GestorRepuestos() {
        TipoRepuesto[] tipos = TipoRepuesto.values();
        estantes = new Pila[tipos.length];
        nombresTipos = new String[tipos.length];
        for (int i = 0; i < tipos.length; i++) {
            estantes[i] = new Pila<>();
            nombresTipos[i] = tipos[i].toString();
        }
    }

    public void prepararRepuesto(Repuesto repuesto) {
        if (repuesto == null || repuesto.getTipo() == null) {
            return;
        }
        int idx = repuesto.getTipo().ordinal();
        estantes[idx].apilar(repuesto);
    }

    public boolean revertirDespacho(Repuesto repuesto) {
        if (repuesto == null || repuesto.getTipo() == null) {
            return false;
        }
        int idx = repuesto.getTipo().ordinal();
        estantes[idx].apilar(repuesto);
        return true;
    }  

    public Repuesto despacharPorTipo(TipoRepuesto tipo) {
        if (tipo == null) {
            return null;
        }
        int idx = tipo.ordinal();
        if (idx < 0 || idx >= estantes.length) {
            return null;
        }
        Pila<Repuesto> est = estantes[idx];
        if (est.estaVacia()) {
            return null;
        }
        return est.desapilar();
    }

    public boolean revertirPreparacion(Repuesto repuesto) {
        if (repuesto == null || repuesto.getTipo() == null) {
            return false;
        }
        int idx = repuesto.getTipo().ordinal();
        if (idx < 0 || idx >= estantes.length) {
            return false;
        }
        estantes[idx].desapilar();
        return true;
    }

    public Lista<Repuesto> obtenerPreparados() {
        Lista<Repuesto> lista = new ArregloLista<>();
        for (int i = 0; i < estantes.length; i++) {
            Pila<Repuesto> est = estantes[i];
            est.recorrer((r) -> lista.add(r));
        }
        return lista;
    }

    public int getCantidadPreparados() {
        int total = 0;
        for (int i = 0; i < estantes.length; i++) {
            total += estantes[i].getTamano();
        }
        return total;
    }

    public String[] obtenerNombresTipos() {
        return nombresTipos;
    }
}
