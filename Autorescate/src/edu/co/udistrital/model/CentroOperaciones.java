package edu.co.udistrital.model;

public class CentroOperaciones {

    private Pila<Operacion> historial;
    private GestorKits gKits;
    private GestorTecnico gTecs;
    private GestorSolicitudes gSol;
    private GestorUnidad gUni;

    public CentroOperaciones(GestorKits gKits, GestorTecnico gTecs, GestorSolicitudes gSol, GestorUnidad gUni) {
        this.historial = new Pila<>();
        this.gKits = gKits;
        this.gTecs = gTecs;
        this.gSol = gSol;
        this.gUni = gUni;
    }

    public void agregarUnidad(Unidad u) {
        gUni.agregarUnidad(u);
        historial.apilar(
                new Operacion(
                        TipoOperacion.UNIDAD_CREADO,
                        "Unidad " + u.getId() + " creada",
                        u
                )
        );
    }

    public void agregarTecnico(Tecnico tecnico) {
        historial.apilar(
                new Operacion(
                        TipoOperacion.TECNICO_CREADO,
                        "Tecnico " + tecnico.getNombre() + " creado",
                        tecnico
                )
        );
        gTecs.agregarTecnico(tecnico);
    }

    public void registrarSolicitud(Solicitud solicitud) {
        gSol.registrar(solicitud);
        historial.apilar(
                new Operacion(
                        TipoOperacion.SOLICITUD_CREADO,
                        "Solicituda creada",
                        solicitud
                )
        );
    }

    public boolean agregarKit(Kit kit) {
        historial.apilar(
                new Operacion(
                        TipoOperacion.KIT_CREADO,
                        "Kit " + kit.getId() + " creado",
                        kit
                ));
        return gKits.agregarKit(kit);
    }

    public Kit revisarKit() {
        boolean rta = gKits.revisarKit();
        if (rta) {
            historial.apilar(
                    new Operacion(
                            TipoOperacion.KIT_REVISADO,
                            "Kit revisado y listo para uso",
                            null
                    ));
            return gKits.getUltimoListo();
        }
        return null;
    }

    public boolean asignarRecurso() {
        if (gSol.getCantidadPendientes() == 0) {
            return false;
        }

        Unidad unidadDisponible = buscarUnidadDisponible();
        Tecnico tecnicoDisponible = gTecs.obtenerDisponible();
        Kit kitDisponible = gKits.despacharKit();

        if (unidadDisponible == null || tecnicoDisponible == null || kitDisponible == null) {
            if (kitDisponible != null) {
                gKits.recibirKit(kitDisponible);
                gKits.revisarKit();
            }
            return false;
        }
        tecnicoDisponible.asignarTrabajo(kitDisponible);
        unidadDisponible.setDisponible(false);
        Solicitud solicitud = gSol.obtenerSiguiente();
        gSol.agregarEnEjecucion(solicitud, unidadDisponible, tecnicoDisponible);
        gSol.agregarCasoEjucion(solicitud);
        historial.apilar(
                new Operacion(
                        TipoOperacion.ASIGNACION,
                        solicitud,
                        unidadDisponible,
                        tecnicoDisponible,
                        kitDisponible,
                        "Asignado a caso " + solicitud.getId()
                        + " Unidad " + unidadDisponible.getTipo()
                        + ", Tecnico " + tecnicoDisponible.getNombre()
                        + " y Kit " + kitDisponible.getCodigo()
                ));
        return true;
    }

    public boolean asignarRecurso(String idSolicitud, String idUnidad, String idTecnica) {
        Solicitud solicitud = gSol.obtenerSiguiente();
        if (solicitud == null) {
            return false;
        }
        Unidad unidadDisponible = gUni.buscarPorId(idUnidad);
        Tecnico tecnicoDisponible = gTecs.buscarPorId(idTecnica);
        Kit kitDisponible = gKits.despacharKit();

        if (unidadDisponible == null || tecnicoDisponible == null || kitDisponible == null) {
            if (kitDisponible != null) {
                gKits.recibirKit(kitDisponible);
                gKits.revisarKit();
            }
            return false;
        }
        tecnicoDisponible.asignarTrabajo(kitDisponible);
        unidadDisponible.setDisponible(false);
        gSol.agregarEnEjecucion(solicitud, unidadDisponible, tecnicoDisponible);
        gSol.agregarCasoEjucion(solicitud);
        historial.apilar(
                new Operacion(
                        TipoOperacion.ASIGNACION,
                        solicitud,
                        unidadDisponible,
                        tecnicoDisponible,
                        kitDisponible,
                        "Asignado a caso " + solicitud.getId()
                        + " Unidad " + unidadDisponible.getTipo()
                        + ", Tecnico " + tecnicoDisponible.getNombre()
                        + " y Kit " + kitDisponible.getCodigo()
                ));
        return true;
    }

    public boolean cerrarSolicitud(String idSolicitud) {
        Solicitud solicitud = gSol.quitarEnEjecucion(idSolicitud);

        if (solicitud == null || solicitud.getUnidadAsignada() == null || solicitud.getTecnicoAsignado() == null) {
            return false;
        }
        gKits.recibirKit(solicitud.getKit());
        solicitud.marcarComoAtendida();
        gSol.agregarCasoCerrado(solicitud);
        historial.apilar(
                new Operacion(
                        TipoOperacion.CIERRE,
                        solicitud,
                        solicitud.getUnidadAsignada(),
                        solicitud.getTecnicoAsignado(),
                        solicitud.getKit(),
                        "Cerrada solicitud " + idSolicitud
                ));
        return true;
    }

    public boolean eliminarUnidad(String id) {
        Unidad seleccionada = gUni.buscarPorId(id);

        if (seleccionada == null || !seleccionada.puedeAsignarse()) {
            return false;
        }

        boolean exito = gUni.eliminarUnidad(id);
        if (exito) {
            historial.apilar(
                    new Operacion(
                            TipoOperacion.UNIDAD_ELIMINADO,
                            "Eliminada unidad " + seleccionada.getId(),
                            seleccionada                        
                    ));
        }
        return exito;
    }

    public boolean modificarUnidad(String id, String nuevaZona, EstadoUnidad nuevoEstado) {
        Unidad anterior = gUni.buscarPorId(id).clonar();        
        Unidad exito = gUni.modificarUnidad(id, nuevaZona, nuevoEstado);
        if (exito != null) {
            historial.apilar(
                    new Operacion(
                            TipoOperacion.UNIDAD_EDITADO,
                            "Unidad " + exito.getTipo() + " Modificada",
                            anterior,
                            exito
                    ));
        }
        return exito != null;
    }

    public Unidad buscarUnidad(String id) {
        return gUni.buscarPorId(id);
    }

    public boolean deshacerUltimaOperacion() {

        while (!historial.estaVacia()) {
            Operacion ultimaOp = historial.desapilar();

            switch (ultimaOp.getTipo()) {
                case SOLICITUD_CREADO: {
                    gSol.revertirCreracion((Solicitud) ultimaOp.getEstadoAnterior());
                    return true;
                }
                case ASIGNACION: {
                    Solicitud solicitud = ultimaOp.getSolicitud();
                    Unidad unidad = ultimaOp.getUnidad();
                    Tecnico tecnico = ultimaOp.getTecnico();
                    Kit kit = ultimaOp.getKit();
                    unidad.revertirAsignacion();
                    tecnico.revertirAsignar();
                    gKits.revertirAsignacion(kit);                   
                    gSol.revertirAsignacion(solicitud);
                    return true;
                }
                case CIERRE: {
                    Solicitud solicitud = ultimaOp.getSolicitud();
                    Unidad unidad = ultimaOp.getUnidad();
                    Tecnico tecnico = ultimaOp.getTecnico();
                    unidad.setDisponible(false);
                    gKits.revisarKit();
                    Kit kit = gKits.despacharKit();
                    tecnico.asignarTrabajo(kit);
                    solicitud.asignarRecursos(ultimaOp.getUnidad(), ultimaOp.getTecnico());
                    gSol.revertirCierre(solicitud);
                    return true;
                }              
                case TECNICO_CREADO: {
                    gTecs.eliminar(((Tecnico) ultimaOp.getEstadoAnterior()).getIdentificacion());
                    return true;
                }
                case UNIDAD_CREADO: {
                    gUni.eliminarUnidad(((Unidad) ultimaOp.getEstadoAnterior()).getId());
                    return true;
                }       
                case KIT_CREADO: {
                    gKits.revertirCreacion();
                    return true;
                }       
                case UNIDAD_ELIMINADO: {
                    gUni.agregarUnidad((Unidad) ultimaOp.getEstadoAnterior());
                    return true;
                }
                case UNIDAD_EDITADO: {                
                    Unidad anterior = (Unidad) ultimaOp.getEstadoAnterior();
                    Unidad actual = (Unidad) ultimaOp.getEstadoActual();
                    actual.setEstado(anterior.getEstado());
                    actual.setZona(anterior.getZona());
                    return true;
                }
                default:
                    break;
            }
        }

        return false;
    }

    private Unidad buscarUnidadDisponible() {
        return gUni.buscarUnidadDisponible();
    }

    public Lista<Unidad> getUnidades() {
        return gUni.obtenerTodas();
    }

    public Lista<Unidad> getUnidadesDisponibles() {
        return gUni.obtenerDisponibles();
    }

    public Lista<Tecnico> getTecnicos() {
        return gTecs.obtenerTodos();
    }

    public Lista<Tecnico> getTecnicosDisponibles() {
        return gTecs.obtenerDisponibles();
    }

    public Lista<Solicitud> getSolicitudesPendientes() {
        return gSol.getSolicitudesPendientes();
    }

    public Lista<Solicitud> getCasosEnEjecucion() {
        return gSol.getCasosEnEjecucion();
    }

    public Lista<Solicitud> getCasosCerrados() {
        return gSol.getCasosCerrados();
    }

    public Lista<Operacion> getHistorial() {
        Lista<Operacion> lista = new ArregloLista<>();
        historial.recorrer(
                (elemento) -> {
                    lista.add(elemento);
                });
        return lista;
    }

    public Lista<Kit> getKits() {
        return gKits.getAllKits();
    }

    public Lista<Kit> getKitsDisponibles() {
        return gKits.getKitsListos();
    }

    public Solicitud verSiguienteSolicitud() {
        return gSol.verSiguiente();
    }

    public Tecnico buscarTecnicoPorId(String id) {
        return gTecs.buscarPorId(id);
    }

    public Tecnico eliminarTecnico(String id) {
        Tecnico seleccionada = gTecs.buscarPorId(id);

        if (seleccionada == null || !seleccionada.puedeAsignarse()) {
            return null;
        }

        Tecnico exito = gTecs.eliminar(id);
        if (exito != null) {
            historial.apilar(
                    new Operacion(
                            TipoOperacion.TECNICO_ELIMINADO,
                            "Eliminada unidad " + seleccionada.getIdentificacion(),
                            exito
                    ));
        }
        return exito;
    }

    public boolean asignarTecnico(String id) {
        return gTecs.asignarTecnico(id);
    }

    public boolean liberarTecnico(String id) {
        return gTecs.liberarTecnico(id);
    }

    public boolean estaTecnicoDisponible(String id) {
        return gTecs.estaDisponible(id);
    }

    public Lista<Tecnico> obtenerTecnicosDisponibles() {
        return gTecs.obtenerDisponibles();
    }

    public Lista<Tecnico> obtenerTodosLosTecnicos() {
        return gTecs.obtenerTodos();
    }
}
