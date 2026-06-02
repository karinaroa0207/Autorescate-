package edu.co.udistrital.model;

public class GestorSolicitudes {

    private ColaPrioridad<Solicitud> pendientes;
    private ListaEnlazada<Solicitud> enEjecucion;
    private ListaEnlazada<Solicitud> cerradas;

    public GestorSolicitudes() {
        pendientes = new ColaPrioridad<>();
        enEjecucion = new ListaEnlazada<>();
        cerradas = new ListaEnlazada<>();
    }

    public void registrar(Solicitud solicitud) {
        pendientes.encolar(solicitud);
    }

    public Solicitud obtenerSiguiente() {
        return pendientes.desencolar();
    }

    public void agregarEnEjecucion(Solicitud solicitud, Unidad unidad, Tecnico tecnico, Kit kit) {
        solicitud.asignarRecursos(unidad, tecnico, kit);
    }

    public void cerrar(Solicitud solicitud) {
        cerradas.agregar(solicitud);
    }

    public ColaPrioridad<Solicitud> getPendientes() {
        return pendientes;
    }

    public Lista<Solicitud> getSolicitudesPendientes() {
        Lista<Solicitud> lista = new ArregloLista<>();
        pendientes.recorrer(
                (elemento) -> {
                    lista.add(elemento);
                }
        );
        return lista;
    }

    public Lista<Solicitud> getCasosEnEjecucion() {
        Lista<Solicitud> lista = new ArregloLista<>();
        enEjecucion.recorrer(
                (elemento) -> {
                    lista.add(elemento);
                }
        );
        return lista;
    }

    public Lista<Solicitud> getCasosCerrados() {
        Lista<Solicitud> lista = new ArregloLista<>();
        cerradas.recorrer(
                (elemento) -> {
                    lista.add(elemento);
                }
        );
        return lista;
    }

    public void agregarCasoCerrado(Solicitud sol) {
        cerradas.agregar(sol);
    }

    public void agregarCasoEjucion(Solicitud sol) {
        enEjecucion.agregar(sol);
    }

    public int getCantidadPendientes() {
        return pendientes.getTamano();
    }

    public Solicitud quitarEnEjecucion(String id) {
        return enEjecucion.remover(new Criterio<Solicitud>() {
            @Override
            public boolean cumple(Solicitud dato) {
                return dato.getId().equals(id);
            }
        });
    }

    public Solicitud verSiguiente() {
        return pendientes.peek();
    }

    public void revertirAsignacion(Solicitud sol) {
        sol.revertirAsignacion();
        quitarEnEjecucion(sol.getId());
        pendientes.encolar(sol);
    }
    
    public void revertirCierre(Solicitud sol) {
        cerradas.remover(new Criterio<Solicitud>() {
            @Override
            public boolean cumple(Solicitud dato) {
                return dato.getId().equals(sol.getId());
            }
        });        
        enEjecucion.agregar(sol);
    }
    
    public void revertirCreracion(Solicitud sol) {
        pendientes.eliminar(sol);
    }
}
