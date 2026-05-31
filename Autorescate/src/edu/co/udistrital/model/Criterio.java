package edu.co.udistrital.model;

public interface Criterio<T> {
    boolean cumple(T dato);
}
