package edu.co.udistrital.model;

public class ColaPrioridad<T extends MiComparable<T>> {
    
    private Nodo<T> frente;
    private Nodo<T> fin;
    private int tamano;

    public ColaPrioridad() {
        this.frente = null;
        this.fin = null;
        this.tamano = 0;
    }

    public void encolar(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);

        if (estaVacia()) {
            frente = nuevoNodo;
            fin = nuevoNodo;
        } else if (dato.compareTo(frente.getDato()) < 0) {
            nuevoNodo.setSiguiente(frente);
            frente = nuevoNodo;
        } else {
            Nodo<T> actual = frente;
            while (actual.getSiguiente() != null && 
                   dato.compareTo(actual.getSiguiente().getDato()) >= 0) {
                actual = actual.getSiguiente();
            }
            nuevoNodo.setSiguiente(actual.getSiguiente());
            actual.setSiguiente(nuevoNodo);
            
            if (nuevoNodo.getSiguiente() == null) {
                fin = nuevoNodo;
            }
        }
        tamano++;
    }

    public T desencolar() {
        if (estaVacia()) {
            return null;
        }
        T dato = frente.getDato();
        frente = frente.getSiguiente();
        if (frente == null) {
            fin = null;
        }
        tamano--;
        return dato;
    }

    public boolean estaVacia() {
        return frente == null;
    }

    public int getTamano() {
        return tamano;
    }

    public Nodo<T> getFrente() {
        return frente;
    }
}
