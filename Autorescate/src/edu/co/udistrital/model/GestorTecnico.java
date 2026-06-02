package edu.co.udistrital.model;

public class GestorTecnico {

    private ListaEnlazada<Tecnico> tecnicos;

    public GestorTecnico() {
        this.tecnicos = new ListaEnlazada<>();
    }

    public void agregarTecnico(Tecnico t) {        
        tecnicos.agregar(t);
    }

    public Lista<Tecnico> obtenerTodos() {
        Lista<Tecnico> lista = new ArregloLista<>();
        Nodo<Tecnico> actual = tecnicos.getCabeza();
        while (actual != null) {
            lista.add(actual.getDato());
            actual = actual.getSiguiente();
        }
        return lista;
    }

    public Lista<Tecnico> obtenerDisponibles() {
        Lista<Tecnico> disponibles = new ArregloLista<>();
        Nodo<Tecnico> actual = tecnicos.getCabeza();
        while (actual != null) {
            Tecnico t = actual.getDato();
            if (t.puedeAsignarse()) {
                disponibles.add(t);
            }
            actual = actual.getSiguiente();
        }
        return disponibles;
    }

    public Lista<Tecnico> obtenerDisponiblesPorZona(String zona) {
        Lista<Tecnico> disponibles = new ArregloLista<>();
        Nodo<Tecnico> actual = tecnicos.getCabeza();
        while (actual != null) {
            Tecnico t = actual.getDato();
            if (t.puedeAsignarse() && zonaCoincide(t.getZona(), zona)) {
                disponibles.add(t);
            }
            actual = actual.getSiguiente();
        }
        return disponibles;
    }

    public Tecnico obtenerDisponible() {
        return tecnicos.buscar(t
                -> t.puedeAsignarse()
        );
    }

    public Tecnico obtenerDisponibleEnZona(String zona) {
        return tecnicos.buscar(t
                -> t.puedeAsignarse() && zonaCoincide(t.getZona(), zona)
        );
    }

    public Tecnico buscarPorId(String id) {
        return tecnicos.buscar(t
                -> t.getIdentificacion().equals(id)
        );
    }

    public Tecnico modificarTecnico(String id, String nuevoNombre, String nuevaEspecialidad, String nuevaZona) {
        Tecnico t = buscarPorId(id);
        if (t == null) {
            return null;
        }
        if (nuevoNombre != null && !nuevoNombre.isEmpty()) {
            t.setNombre(nuevoNombre);
        }
        if (nuevaEspecialidad != null && !nuevaEspecialidad.isEmpty()) {
            t.setEspecialidad(nuevaEspecialidad);
        }
        if (nuevaZona != null && !nuevaZona.isEmpty()) {
            t.setZona(nuevaZona);
        }
        return t;
    }

    public boolean asignarTecnico(String id) {
        Tecnico t = buscarPorId(id);
        if (t != null && t.puedeAsignarse()) {
            t.setLibre(false);
            return true;
        }
        return false;
    }

    public boolean liberarTecnico(String id) {
        Tecnico t = buscarPorId(id);
        if (t != null) {
            t.setLibre(true);
            return true;
        }
        return false;
    }

    public boolean estaDisponible(String id) {
        Tecnico t = buscarPorId(id);
        return t != null && t.puedeAsignarse();
    }

    public Tecnico eliminar(String id) {
        return tecnicos.remover(t
                -> t.getIdentificacion().equals(id)
        );
    }

    private boolean zonaCoincide(String zonaTecnico, String zonaSolicitud) {
        if (zonaTecnico == null || zonaSolicitud == null) {
            return false;
        }
        return zonaTecnico.trim().equalsIgnoreCase(zonaSolicitud.trim());
    }

}
