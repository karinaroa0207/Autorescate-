package edu.co.udistrital.model;

public class ArregloLista<T extends Clonable<T>> implements Lista<T> {

    private Object[] datos;
    private int tamaño;

    public ArregloLista() {
        datos = new Object[10];
        tamaño = 0;
    }

    @Override
    public void add(T elemento) {
        if (tamaño == datos.length) {
            expandir();
        }

        datos[tamaño++] = elemento;
    }

    @Override
    public T get(int indice) {
        validarIndice(indice);
        return (T) datos[indice];
    }

    @Override
    public int size() {
        return tamaño;
    }

    @Override
    public boolean isEmpty() {
        return tamaño == 0;
    }

    private void expandir() {
        Object[] nuevo = new Object[datos.length * 2];

        for (int i = 0; i < datos.length; i++) {
            nuevo[i] = datos[i];
        }

        datos = nuevo;
    }

    private void validarIndice(int indice) {
        if (indice < 0 || indice >= tamaño) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public void set(int indice, T elemento) {
        validarIndice(indice);
        datos[indice] = elemento;
    }

    @Override
    public T remove(int indice) {
        validarIndice(indice);

        T eliminado = (T) datos[indice];

        for (int i = indice; i < tamaño - 1; i++) {
            datos[i] = datos[i + 1];
        }        
        datos[tamaño - 1] = null;
        tamaño--;

        return eliminado;
    }

    @Override
    public boolean contains(T elemento) {
        for (int i = 0; i < tamaño; i++) {

            if (elemento == null) {
                if (datos[i] == null) {
                    return true;
                }
            } else {
                if (elemento.equals(datos[i])) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void clear() {
        for (int i = 0; i < tamaño; i++) {
            datos[i] = null;
        }
        tamaño = 0;
    }

    @Override
    public MiIterador<T> iterator() {
        return new Iterador();
    }

    @Override
    public void ordenar() {
        
    }

    @Override
    public void ordenar(MiComparador<T> c) {
        
    }

    @Override
    public Lista<T> clonar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    

    private class Iterador implements MiIterador<T> {

        private int posicion = 0;

        @Override
        public boolean hasNext() {
            return posicion < tamaño;
        }

        @Override
        public T next() {
            return (T) datos[posicion++];
        }
    }

}
