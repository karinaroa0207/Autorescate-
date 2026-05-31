package edu.co.udistrital.model;

public interface MiIterador<T> {
    boolean hasNext();
    T next();
}
