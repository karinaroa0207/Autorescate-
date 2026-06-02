package edu.co.udistrital.model;

public class ColaPrioridad<T extends MiComparable<T>> implements MiIterable<T> {

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
            while (actual.getSiguiente() != null
                    && dato.compareTo(actual.getSiguiente().getDato()) >= 0) {
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

    public boolean eliminar(T dato) {
        if (estaVacia() || dato == null) {
            return false;
        }

        if (frente.getDato() == dato) {
            frente = frente.getSiguiente();
            if (frente == null) {
                fin = null;
            }
            tamano--;
            return true;
        }

        Nodo<T> actual = frente;
        while (actual.getSiguiente() != null && actual.getSiguiente().getDato() != dato) {
            actual = actual.getSiguiente();
        }

        if (actual.getSiguiente() != null) {
            if (actual.getSiguiente() == fin) {
                fin = actual;
            }
            actual.setSiguiente(actual.getSiguiente().getSiguiente());
            tamano--;
            return true;
        }
        return false;
    }

    public T peek() {
        if (frente == null) {
            return null;
        }
        return frente.getDato();
    }

    @Override
    public MiIterador<T> iterator() {
        return new Iterador();
    }

    private class Iterador implements MiIterador<T> {

        private Nodo<T> actual = frente;

        @Override
        public boolean hasNext() {
            return actual != null;
        }

        @Override
        public T next() {
            T dato = actual.getDato();
            actual = actual.getSiguiente();
            return dato;
        }
    }

    public void recorrer(Consumidor<T> c) {
        Nodo<T> actual = frente;
        while (actual != null) {
            c.aplicar(actual.getDato());
            actual = actual.getSiguiente();
        }
    }
}
