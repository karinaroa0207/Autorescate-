package edu.co.udistrital.model;

public class CentroOperaciones {

    private Pila<Operacion> historial;
    private GestorKits gKits;
    private GestorTecnico gTecs;
    private GestorSolicitudes gSol;
    private GestorUnidad gUni;
    private GestorCliente gClientes;
    private GestorRepuestos gRepuestos;
    private CatalogoRepuestos catalogoRepuestos;
    private CatalogoServicios catalogoServicios;
    private String ultimoDetalleDespacho;

    public CentroOperaciones(GestorKits gKits, GestorTecnico gTecs, GestorSolicitudes gSol, GestorUnidad gUni, GestorCliente gClientes, GestorRepuestos gRepuestos) {
        this.historial = new Pila<>();
        this.gKits = gKits;
        this.gTecs = gTecs;
        this.gSol = gSol;
        this.gUni = gUni;
        this.gClientes = gClientes;
        this.gRepuestos = gRepuestos;
        this.catalogoRepuestos = new CatalogoRepuestos();
        this.catalogoServicios = new CatalogoServicios();
        this.ultimoDetalleDespacho = "";
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

    public boolean registrarCliente(Cliente cliente) {
        boolean exito = gClientes.agregarCliente(cliente);
        if (exito) {
            historial.apilar(
                    new Operacion(
                            TipoOperacion.CLIENTE_CREADO,
                            "Cliente " + cliente.getNombre() + " creado",
                            cliente
                    )
            );
        }
        return exito;
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
                            gKits.getUltimoListo()
                    ));
            return gKits.getUltimoListo();
        }
        return null;
    }

    public void prepararRepuesto(Repuesto repuesto) {
        gRepuestos.prepararRepuesto(repuesto);
        historial.apilar(
                new Operacion(
                        TipoOperacion.REPUESTO_PREPARADO,
                        "Repuesto " + repuesto.getCodigoRepuesto() + " preparado",
                        repuesto
                )
        );
    }

    public Repuesto retirarRepuestoPreparado() {
        Repuesto repuesto = gRepuestos.despacharRepuesto();
        if (repuesto != null) {
            historial.apilar(
                    new Operacion(
                            TipoOperacion.REPUESTO_RETIRADO,
                            "Repuesto " + repuesto.getCodigoRepuesto() + " retirado",
                            repuesto
                    )
            );
        }
        return repuesto;
    }

    public boolean asignarRecurso() {
        Solicitud solicitud = gSol.verSiguiente();
        if (solicitud == null) {
            return false;
        }

        Unidad unidadDisponible = gUni.buscarUnidadDisponibleEnZona(solicitud.getZona());
        Tecnico tecnicoDisponible = gTecs.obtenerDisponibleEnZona(solicitud.getZona());

        if (unidadDisponible == null || tecnicoDisponible == null) {
            return false;
        }

        Kit kitDisponible = gKits.despacharKit();
        Repuesto repuestoDisponible = null;
        if (kitDisponible == null) {
            repuestoDisponible = gRepuestos.despacharRepuesto();
        }
        if (kitDisponible == null && repuestoDisponible == null) {
            return false;
        }
        ultimoDetalleDespacho = detalleRecursoRapido(kitDisponible, repuestoDisponible);
        tecnicoDisponible.asignarTrabajo(kitDisponible);
        unidadDisponible.setDisponible(false);
        solicitud = gSol.obtenerSiguiente();
        gSol.agregarEnEjecucion(solicitud, unidadDisponible, tecnicoDisponible, kitDisponible, repuestoDisponible);
        gSol.agregarCasoEjucion(solicitud);
        historial.apilar(
                new Operacion(
                        TipoOperacion.ASIGNACION,
                        solicitud,
                        unidadDisponible,
                        tecnicoDisponible,
                        kitDisponible,
                        repuestoDisponible,
                        "Asignado a caso " + solicitud.getId()
                        + " Unidad " + unidadDisponible.getTipo()
                        + ", Tecnico " + tecnicoDisponible.getNombre()
                        + " y " + ultimoDetalleDespacho
                ));
        return true;
    }

    public boolean asignarRecurso(String idSolicitud, String idUnidad, String idTecnica) {
        Solicitud solicitud = gSol.verSiguiente();
        if (solicitud == null || idSolicitud == null || !solicitud.getId().equals(idSolicitud)) {
            return false;
        }
        Unidad unidadDisponible = gUni.buscarPorId(idUnidad);
        Tecnico tecnicoDisponible = gTecs.buscarPorId(idTecnica);

        if (unidadDisponible == null || tecnicoDisponible == null
                || !unidadDisponible.puedeAsignarse() || !tecnicoDisponible.puedeAsignarse()
                || !zonaCoincide(unidadDisponible.getZona(), solicitud.getZona())
                || !zonaCoincide(tecnicoDisponible.getZona(), solicitud.getZona())) {
            return false;
        }

        Kit kitDisponible = gKits.despacharKit();
        Repuesto repuestoDisponible = null;
        if (kitDisponible == null) {
            repuestoDisponible = gRepuestos.despacharRepuesto();
        }
        if (kitDisponible == null && repuestoDisponible == null) {
            return false;
        }
        ultimoDetalleDespacho = detalleRecursoRapido(kitDisponible, repuestoDisponible);

        solicitud = gSol.obtenerSiguiente();
        tecnicoDisponible.asignarTrabajo(kitDisponible);
        unidadDisponible.setDisponible(false);
        gSol.agregarEnEjecucion(solicitud, unidadDisponible, tecnicoDisponible, kitDisponible, repuestoDisponible);
        gSol.agregarCasoEjucion(solicitud);
        historial.apilar(
                new Operacion(
                        TipoOperacion.ASIGNACION,
                        solicitud,
                        unidadDisponible,
                        tecnicoDisponible,
                        kitDisponible,
                        repuestoDisponible,
                        "Asignado a caso " + solicitud.getId()
                        + " Unidad " + unidadDisponible.getTipo()
                        + ", Tecnico " + tecnicoDisponible.getNombre()
                        + " y " + ultimoDetalleDespacho
                ));
        return true;
    }

    public boolean cerrarSolicitud(String idSolicitud) {
        Solicitud solicitud = gSol.quitarEnEjecucion(idSolicitud);

        if (solicitud == null || solicitud.getUnidadAsignada() == null || solicitud.getTecnicoAsignado() == null) {
            return false;
        }
        if (solicitud.getKit() != null) {
            gKits.recibirKit(solicitud.getKit());
        }
        solicitud.marcarComoAtendida();
        gSol.agregarCasoCerrado(solicitud);
        historial.apilar(
                new Operacion(
                        TipoOperacion.CIERRE,
                        solicitud,
                        solicitud.getUnidadAsignada(),
                        solicitud.getTecnicoAsignado(),
                        solicitud.getKit(),
                        solicitud.getRepuesto(),
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
        Unidad anterior = gUni.buscarPorId(id);      
        if (anterior == null) {
            return false;
        }
        anterior = anterior.clonar();
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
                    Repuesto repuesto = ultimaOp.getRepuesto();
                    unidad.revertirAsignacion();
                    tecnico.revertirAsignar();
                    if (kit != null) {
                        gKits.revertirAsignacion(kit);
                    }
                    if (repuesto != null) {
                        gRepuestos.revertirDespacho(repuesto);
                    }
                    gSol.revertirAsignacion(solicitud);
                    return true;
                }
                case CIERRE: {
                    Solicitud solicitud = ultimaOp.getSolicitud();
                    Unidad unidad = ultimaOp.getUnidad();
                    Tecnico tecnico = ultimaOp.getTecnico();
                    unidad.setDisponible(false);
                    Kit kit = null;
                    if (ultimaOp.getKit() != null) {
                        gKits.revisarKit();
                        kit = gKits.despacharKit();
                    }
                    tecnico.asignarTrabajo(kit);
                    solicitud.asignarRecursos(ultimaOp.getUnidad(), ultimaOp.getTecnico(), kit, ultimaOp.getRepuesto());
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
                case KIT_REVISADO: {
                    gKits.revertirRevision((Kit) ultimaOp.getEstadoAnterior());
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
                case CLIENTE_CREADO: {
                    gClientes.eliminarCliente(((Cliente) ultimaOp.getEstadoAnterior()).getDocumento());
                    return true;
                }
                case REPUESTO_PREPARADO: {
                    gRepuestos.revertirPreparacion();
                    return true;
                }
                case REPUESTO_RETIRADO: {
                    gRepuestos.revertirDespacho((Repuesto) ultimaOp.getEstadoAnterior());
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

    public Lista<Unidad> getUnidadesDisponibles(String zona) {
        return gUni.obtenerDisponiblesPorZona(zona);
    }

    public Lista<Tecnico> getTecnicos() {
        return gTecs.obtenerTodos();
    }

    public Lista<Tecnico> getTecnicosDisponibles() {
        return gTecs.obtenerDisponibles();
    }

    public Lista<Tecnico> getTecnicosDisponibles(String zona) {
        return gTecs.obtenerDisponiblesPorZona(zona);
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
    
    public Lista<Kit> getKitsRevision() {
        return gKits.getKitsEnRevision();
    }

    public Lista<Repuesto> getRepuestosPreparados() {
        return gRepuestos.obtenerPreparados();
    }

    public Lista<Repuesto> getRepuestosFrecuentes() {
        return catalogoRepuestos.obtenerFrecuentes();
    }

    public Repuesto buscarRepuestoFrecuente(String nombre) {
        return catalogoRepuestos.buscarPorNombre(nombre);
    }

    public Lista<String> getTiposServicio() {
        return catalogoServicios.obtenerTiposServicio();
    }

    public String getUltimoDetalleDespacho() {
        return ultimoDetalleDespacho;
    }

    public Lista<Cliente> getClientes() {
        return gClientes.obtenerTodos();
    }

    public Cliente buscarClientePorDocumento(String documento) {
        return gClientes.buscarPorDocumento(documento);
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

    private boolean zonaCoincide(String zonaRecurso, String zonaSolicitud) {
        if (zonaRecurso == null || zonaSolicitud == null) {
            return false;
        }
        return zonaRecurso.trim().equalsIgnoreCase(zonaSolicitud.trim());
    }

    private String detalleRecursoRapido(Kit kit, Repuesto repuesto) {
        if (kit != null) {
            return "Kit " + kit.getCodigo();
        }
        if (repuesto != null) {
            return "Repuesto de contingencia " + repuesto.getCodigoRepuesto();
        }
        return "Sin recurso rapido";
    }
}
