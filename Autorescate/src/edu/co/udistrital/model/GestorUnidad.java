package edu.co.udistrital.model;

public class GestorUnidad {

    private ListaEnlazada<Unidad> unidades;

    public GestorUnidad() {
        this.unidades = new ListaEnlazada<>();
    }

    public void agregarUnidad(Unidad u) {        
        unidades.agregar(u);
    }

    public Unidad buscarUnidadDisponible() {
        Nodo<Unidad> actualUnidad = unidades.getCabeza();
        while (actualUnidad != null) {
            if (actualUnidad.getDato().puedeAsignarse()) {
                return actualUnidad.getDato();
            }
            actualUnidad = actualUnidad.getSiguiente();
        }
        return null;
    }

    public Unidad buscarUnidadDisponibleEnZona(String zona) {
        Nodo<Unidad> actualUnidad = unidades.getCabeza();
        while (actualUnidad != null) {
            Unidad unidad = actualUnidad.getDato();
            if (unidad.puedeAsignarse() && zonaCoincide(unidad.getZona(), zona)) {
                return unidad;
            }
            actualUnidad = actualUnidad.getSiguiente();
        }
        return null;
    }

    public Lista<Unidad> obtenerTodas() {
        Lista<Unidad> lista = new ArregloLista<>();
        Nodo<Unidad> actual = unidades.getCabeza();
        while (actual != null) {
            lista.add(actual.getDato());
            actual = actual.getSiguiente();
        }
        return lista;
    }

    public Lista<Unidad> obtenerDisponibles() {
        Lista<Unidad> lista = new ArregloLista<>();
        Nodo<Unidad> actual = unidades.getCabeza();
        while (actual != null) {
            if (actual.getDato().puedeAsignarse()) {
                lista.add(actual.getDato());
            }
            actual = actual.getSiguiente();
        }
        return lista;
    }

    public Lista<Unidad> obtenerDisponiblesPorZona(String zona) {
        Lista<Unidad> lista = new ArregloLista<>();
        Nodo<Unidad> actual = unidades.getCabeza();
        while (actual != null) {
            Unidad unidad = actual.getDato();
            if (unidad.puedeAsignarse() && zonaCoincide(unidad.getZona(), zona)) {
                lista.add(unidad);
            }
            actual = actual.getSiguiente();
        }
        return lista;
    }

    public Unidad modificarUnidad(String id, String nuevaZona, EstadoUnidad nuevoEstado) {
        Unidad encontrada = unidades.buscar(new Criterio<Unidad>() {
            @Override
            public boolean cumple(Unidad dato) {
                return dato.getId().equals(id);
            }
        });

        if (encontrada != null) {
            encontrada.setZona(nuevaZona);
            encontrada.setEstado(nuevoEstado);
        }
        return encontrada;
    }

    public boolean eliminarUnidad(String id) {
        Unidad removida = unidades.remover(new Criterio<Unidad>() {
            @Override
            public boolean cumple(Unidad dato) {
                return dato.getId().equals(id);
            }
        });
        return removida != null;
    }

    public Unidad buscarPorId(String id) {
        return unidades.buscar(new Criterio<Unidad>() {
            @Override
            public boolean cumple(Unidad dato) {
                return dato.getId().equals(id);
            }
        });
    }

    private boolean zonaCoincide(String zonaRecurso, String zonaSolicitud) {
        if (zonaRecurso == null || zonaSolicitud == null) {
            return false;
        }
        return zonaRecurso.trim().equalsIgnoreCase(zonaSolicitud.trim());
    }
}
