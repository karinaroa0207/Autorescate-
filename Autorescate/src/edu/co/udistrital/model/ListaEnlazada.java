package edu.co.udistrital.model;

public class ListaEnlazada<T> implements MiIterable<T>{

    private Nodo<T> cabeza;
    private int tamano;

    public ListaEnlazada() {
        this.cabeza = null;
        this.tamano = 0;
    }

    public void agregar(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        if (cabeza == null) {
            cabeza = nuevoNodo;
        } else {
            Nodo<T> actual = cabeza;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevoNodo);
        }
        tamano++;
    }

    public T buscar(Criterio<T> criterio) {
        Nodo<T> actual = cabeza;
        while (actual != null) {
            if (criterio.cumple(actual.getDato())) {
                return actual.getDato();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    public T remover(Criterio<T> criterio) {
        Nodo<T> actual = cabeza;
        Nodo<T> anterior = null;

        while (actual != null) {
            if (criterio.cumple(actual.getDato())) {
                if (anterior == null) {
                    cabeza = actual.getSiguiente();
                } else {
                    anterior.setSiguiente(actual.getSiguiente());
                }
                tamano--;
                return actual.getDato();
            }
            anterior = actual;
            actual = actual.getSiguiente();
        }
        return null;
    }        

    public boolean estaVacia() {
        return cabeza == null;
    }

    public int getTamano() {
        return tamano;
    }

    public Nodo<T> getCabeza() {
        return cabeza;
    }

    @Override
    public MiIterador<T> iterator() {
        return new Iterador();
    }

    private class Iterador implements MiIterador<T> {

        private Nodo<T> actual = cabeza;

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
        Nodo<T> actual = cabeza;
        while (actual != null) {
            c.aplicar(actual.getDato());            
            actual = actual.getSiguiente();
        }
    }
}
