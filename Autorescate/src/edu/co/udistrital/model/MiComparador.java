package edu.co.udistrital.model;

public interface MiComparador<T> {

    int compare(T a, T b);

    static <T extends MiComparable<T>> MiComparador<T> reverseOrder() {
        return (a, b) -> b.compareTo(a);
    }

    static <T extends MiComparable<T>> MiComparador<T> naturalOrder() {
        return (a, b) -> a.compareTo(b);
    }
}