package edu.co.udistrital.model;

public class CentroOperaciones {

    private Pila<Operacion> historial;
    private GestorKits gKits;
    private GestorTecnico gTecs;
    private GestorSolicitudes gSol;
    private GestorUnidad gUni;
    private GestorCliente gClientes;

    public CentroOperaciones(GestorKits gKits, GestorTecnico gTecs, GestorSolicitudes gSol, GestorUnidad gUni, GestorCliente gClientes) {
        this.historial = new Pila<>();
        this.gKits = gKits;
        this.gTecs = gTecs;
        this.gSol = gSol;
        this.gUni = gUni;
        this.gClientes = gClientes;
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

    public boolean modificarCliente(String documento, String nuevoNombre, String nuevoTelefono, String nuevaPlaca, String nuevoModelo) {
        Cliente anterior = gClientes.buscarPorDocumento(documento);
        if (anterior == null) {
            return false;
        }
        Cliente copiaAnterior = new Cliente(anterior.getDocumento(), anterior.getNombre(), anterior.getTelefono(), anterior.getPlacaVehiculo(), anterior.getModeloVehiculo());
        Cliente actualizado = gClientes.modificarCliente(documento, nuevoNombre, nuevoTelefono, nuevaPlaca, nuevoModelo);
        if (actualizado != null) {
            historial.apilar(
                    new Operacion(
                            TipoOperacion.CLIENTE_EDITADO,
                            "Cliente " + actualizado.getDocumento() + " modificado",
                            copiaAnterior,
                            actualizado
                    )
            );
            return true;
        }
        return false;
    }

    public Cliente eliminarCliente(String documento) {
        Cliente seleccionada = gClientes.buscarPorDocumento(documento);
        if (seleccionada == null) {
            return null;
        }
        Cliente exito = gClientes.eliminarCliente(documento);
        if (exito != null) {
            historial.apilar(
                    new Operacion(
                            TipoOperacion.CLIENTE_ELIMINADO,
                            "Cliente " + seleccionada.getDocumento() + " eliminado",
                            seleccionada
                    )
            );
        }
        return exito;
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
        if (kitDisponible == null) {
            return false;
        }
        tecnicoDisponible.asignarTrabajo(kitDisponible);
        unidadDisponible.setDisponible(false);
        solicitud = gSol.obtenerSiguiente();
        gSol.agregarEnEjecucion(solicitud, unidadDisponible, tecnicoDisponible, kitDisponible);
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
        if (kitDisponible == null) {
            return false;
        }

        solicitud = gSol.obtenerSiguiente();
        tecnicoDisponible.asignarTrabajo(kitDisponible);
        unidadDisponible.setDisponible(false);
        gSol.agregarEnEjecucion(solicitud, unidadDisponible, tecnicoDisponible, kitDisponible);
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
                    solicitud.asignarRecursos(ultimaOp.getUnidad(), ultimaOp.getTecnico(), kit);
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
                case TECNICO_EDITADO: {
                    Tecnico anterior = (Tecnico) ultimaOp.getEstadoAnterior();
                    Tecnico actual = (Tecnico) ultimaOp.getEstadoActual();
                    actual.setNombre(anterior.getNombre());
                    actual.setEspecialidad(anterior.getEspecialidad());
                    actual.setZona(anterior.getZona());
                    return true;
                }
                case TECNICO_ELIMINADO: {
                    gTecs.agregarTecnico((Tecnico) ultimaOp.getEstadoAnterior());
                    return true;
                }
                case CLIENTE_EDITADO: {
                    Cliente anterior = (Cliente) ultimaOp.getEstadoAnterior();
                    Cliente actual = (Cliente) ultimaOp.getEstadoActual();
                    actual.setNombre(anterior.getNombre());
                    actual.setTelefono(anterior.getTelefono());
                    actual.setPlacaVehiculo(anterior.getPlacaVehiculo());
                    actual.setModeloVehiculo(anterior.getModeloVehiculo());
                    return true;
                }
                case CLIENTE_ELIMINADO: {
                    gClientes.agregarCliente((Cliente) ultimaOp.getEstadoAnterior());
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

    public boolean modificarTecnico(String id, String nombre, String especialidad, String zona) {
        Tecnico anterior = gTecs.buscarPorId(id);
        if (anterior == null) {
            return false;
        }
        Tecnico copiaAnterior = anterior.clonar();
        Tecnico actualizado = gTecs.modificarTecnico(id, nombre, especialidad, zona);
        if (actualizado != null) {
            historial.apilar(
                    new Operacion(
                            TipoOperacion.TECNICO_EDITADO,
                            "Tecnico " + actualizado.getIdentificacion() + " modificado",
                            copiaAnterior,
                            actualizado
                    ));
            return true;
        }
        return false;
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
}
