package edu.co.udistrital.model;

public interface Lista<T> extends MiIterable<T>, Clonable<Lista<T>> {

    void add(T elemento);

    T get(int indice);

    void set(int indice, T elemento);

    T remove(int indice);

    int size();

    boolean isEmpty();

    boolean contains(T elemento);

    void clear();    
    
    void ordenar();
            
    void ordenar(MiComparador<T> c);

}
