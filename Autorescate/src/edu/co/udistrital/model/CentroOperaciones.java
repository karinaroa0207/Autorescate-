package edu.co.udistrital.model;

public class CentroOperaciones {

    private ColaPrioridad<Solicitud> solicitudesPendientes;
    private ListaEnlazada<Unidad> unidades;
    private ListaEnlazada<Tecnico> tecnicos;
    private ListaEnlazada<Solicitud> casosEnEjecucion;
    private ListaEnlazada<Solicitud> casosCerrados;
    private Pila<Operacion> historial;
    private Pila<Kit> kitsEnRevision;

    public CentroOperaciones() {
        this.solicitudesPendientes = new ColaPrioridad<>();
        this.unidades = new ListaEnlazada<>();
        this.tecnicos = new ListaEnlazada<>();
        this.casosEnEjecucion = new ListaEnlazada<>();
        this.casosCerrados = new ListaEnlazada<>();
        this.historial = new Pila<>();
        this.kitsEnRevision = new Pila<>();
    }

    public void agregarUnidad(Unidad unidad) {
        unidades.agregar(unidad);
    }

    public void agregarTecnico(Tecnico tecnico) {
        tecnicos.agregar(tecnico);
    }

    public void registrarSolicitud(Solicitud solicitud) {
        solicitudesPendientes.encolar(solicitud);
    }

    public void recibirKit(Kit kit) {
        kitsEnRevision.apilar(kit);
        historial.apilar(new Operacion("KIT_RECIBIDO", null, null, null));
    }

    public Kit despacharKit() {
        Kit kit = kitsEnRevision.desapilar();
        if (kit != null) {
            kit.setRequiereRevision(false);
            historial.apilar(new Operacion("KIT_DESPACHADO", null, null, null));
        }
        return kit;
    }

    public boolean asignarRecurso() {
        if (solicitudesPendientes.estaVacia()) {
            return false;
        }

        Unidad unidadDisponible = buscarUnidadDisponible();
        Tecnico tecnicoDisponible = buscarTecnicoDisponible();

        if (unidadDisponible == null || tecnicoDisponible == null) {
            return false;
        }

        Solicitud solicitud = solicitudesPendientes.desencolar();
        unidadDisponible.setDisponible(false);
        tecnicoDisponible.setLibre(false);
        solicitud.asignarRecursos(unidadDisponible, tecnicoDisponible);
        casosEnEjecucion.agregar(solicitud);
        historial.apilar(new Operacion("ASIGNACION", solicitud, unidadDisponible, tecnicoDisponible));
        return true;
    }

    public boolean cerrarSolicitud(int idSolicitud) {
        Solicitud solicitud = casosEnEjecucion.remover(new Criterio<Solicitud>() {
            @Override
            public boolean cumple(Solicitud dato) {
                return dato.getId() == idSolicitud;
            }
        });

        if (solicitud == null || solicitud.getUnidadAsignada() == null || solicitud.getTecnicoAsignado() == null) {
            return false;
        }

        solicitud.marcarComoAtendida();
        solicitud.getUnidadAsignada().setDisponible(true);
        solicitud.getTecnicoAsignado().setLibre(true);
        casosCerrados.agregar(solicitud);
        historial.apilar(new Operacion("CIERRE", solicitud, solicitud.getUnidadAsignada(), solicitud.getTecnicoAsignado()));
        return true;
    }

    public boolean deshacerUltimaOperacion() {
        while (!historial.estaVacia()) {
            Operacion ultimaOp = historial.desapilar();

            if ("ASIGNACION".equals(ultimaOp.getTipo())) {
                Solicitud solicitud = ultimaOp.getSolicitud();
                casosEnEjecucion.remover(new Criterio<Solicitud>() {
                    @Override
                    public boolean cumple(Solicitud dato) {
                        return dato.getId() == solicitud.getId();
                    }
                });
                ultimaOp.getUnidad().setDisponible(true);
                ultimaOp.getTecnico().setLibre(true);
                solicitud.revertirAsignacion();
                solicitudesPendientes.encolar(solicitud);
                return true;
            }

            if ("CIERRE".equals(ultimaOp.getTipo())) {
                Solicitud solicitud = ultimaOp.getSolicitud();
                casosCerrados.remover(new Criterio<Solicitud>() {
                    @Override
                    public boolean cumple(Solicitud dato) {
                        return dato.getId() == solicitud.getId();
                    }
                });
                solicitud.asignarRecursos(ultimaOp.getUnidad(), ultimaOp.getTecnico());
                ultimaOp.getUnidad().setDisponible(false);
                ultimaOp.getTecnico().setLibre(false);
                casosEnEjecucion.agregar(solicitud);
                return true;
            }
        }
        return false;
    }

    private Unidad buscarUnidadDisponible() {
        Nodo<Unidad> actualUnidad = unidades.getCabeza();
        while (actualUnidad != null) {
            if (actualUnidad.getDato().puedeAsignarse()) {
                return actualUnidad.getDato();
            }
            actualUnidad = actualUnidad.getSiguiente();
        }
        return null;
    }

    private Tecnico buscarTecnicoDisponible() {
        Nodo<Tecnico> actualTecnico = tecnicos.getCabeza();
        while (actualTecnico != null) {
            if (actualTecnico.getDato().puedeAsignarse()) {
                return actualTecnico.getDato();
            }
            actualTecnico = actualTecnico.getSiguiente();
        }
        return null;
    }

    public ListaEnlazada<Unidad> getUnidades() {
        return unidades;
    }

    public ListaEnlazada<Tecnico> getTecnicos() {
        return tecnicos;
    }

    public ColaPrioridad<Solicitud> getSolicitudesPendientes() {
        return solicitudesPendientes;
    }

    public ListaEnlazada<Solicitud> getCasosEnEjecucion() {
        return casosEnEjecucion;
    }

    public ListaEnlazada<Solicitud> getCasosCerrados() {
        return casosCerrados;
    }

    public Pila<Operacion> getHistorial() {
        return historial;
    }

    public Pila<Kit> getKitsEnRevision() {
        return kitsEnRevision;
    }
}
