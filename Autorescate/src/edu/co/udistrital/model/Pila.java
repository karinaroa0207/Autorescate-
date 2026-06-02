package edu.co.udistrital.model;

public class Pila<T> implements MiIterable<T>{
    
    private Nodo<T> cima;
    private int tamano;

    public Pila() {
        this.cima = null;
        this.tamano = 0;
    }

    public void apilar(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        nuevoNodo.setSiguiente(cima);
        cima = nuevoNodo;
        tamano++;
    }

    public T desapilar() {
        if (estaVacia()) {
            return null;
        }
        T dato = cima.getDato();
        cima = cima.getSiguiente();
        tamano--;
        return dato;
    }

    public boolean estaVacia() {
        return cima == null;
    }

    public int getTamano() {
        return tamano;
    }

    public Nodo<T> getCima() {
        return cima;
    }
    
     @Override
    public MiIterador<T> iterator() {
        return new Iterador();
    }

    private class Iterador implements MiIterador<T> {

        private Nodo<T> actual = cima;

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
        Nodo<T> actual = cima;
        while (actual != null) {
            c.aplicar(actual.getDato());            
            actual = actual.getSiguiente();
        }
    }
    
    public T peek() {
        if(cima == null) {
            return null;
        }
        return cima.getDato();
    }
}
