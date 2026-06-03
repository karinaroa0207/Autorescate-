package edu.co.udistrital.controller;

import edu.co.udistrital.model.Cliente;
import edu.co.udistrital.model.Kit;
import edu.co.udistrital.model.Operacion;
import edu.co.udistrital.model.Repuesto;
import edu.co.udistrital.model.Solicitud;
import edu.co.udistrital.model.Tecnico;
import edu.co.udistrital.model.Unidad;
import java.time.format.DateTimeFormatter;

public class FilasTabla {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private FilasTabla() {
    }

    public static String[] cliente(Cliente cliente) {
        return new String[]{
            valor(cliente.getDocumento()),
            valor(cliente.getNombre()),
            valor(cliente.getTelefono()),
            valor(cliente.getPlacaVehiculo()),
            valor(cliente.getModeloVehiculo())
        };
    }

    public static String[] tecnico(Tecnico tecnico) {
        return new String[]{
            valor(tecnico.getIdentificacion()),
            valor(tecnico.getNombre()),
            valor(tecnico.getEspecialidad()),
            valor(tecnico.getZona()),
            tecnico.getEstado() != null ? tecnico.getEstado().toString() : "",
            tecnico.isLibre() ? "Si" : "No"
        };
    }

    public static String[] tecnicoDisponible(Tecnico tecnico) {
        return new String[]{
            valor(tecnico.getIdentificacion()),
            valor(tecnico.getNombre()),
            valor(tecnico.getEspecialidad()),
            valor(tecnico.getZona())
        };
    }

    public static String[] unidad(Unidad unidad) {
        return new String[]{
            valor(unidad.getId()),
            unidad.getTipo() != null ? unidad.getTipo().getDescripcion() : "",
            valor(unidad.getZona()),
            unidad.getEstado() != null ? unidad.getEstado().toString() : "",
            unidad.puedeAsignarse() ? "Si" : "No"
        };
    }

    public static String[] unidadDisponible(Unidad unidad) {
        return new String[]{
            valor(unidad.getId()),
            valor(unidad.getZona()),
            unidad.getTipo() != null ? unidad.getTipo().getDescripcion() : ""
        };
    }

    public static String[] kit(Kit kit) {
        return new String[]{
            valor(kit.getId()),
            valor(kit.getCodigo()),
            valor(kit.getDescripcion()),
            kit.isEstado() != null ? kit.isEstado().toString() : ""
        };
    }

    public static String[] kitDisponible(Kit kit) {
        return new String[]{
            valor(kit.getId()),
            valor(kit.getCodigo()),
            valor(kit.getDescripcion())
        };
    }

    public static String[] repuestoPreparado(Repuesto repuesto) {
        return new String[]{
            repuesto.getTipo() != null ? repuesto.getTipo().toString() : "",
            valor(repuesto.getCodigoRepuesto())
        };
    }

    public static String[] solicitudPendiente(Solicitud solicitud) {
        return new String[]{
            valor(solicitud.getId()),
            nombreCliente(solicitud),
            valor(solicitud.getTipoServicio()),
            valor(solicitud.getZona()),
            String.valueOf(solicitud.getPrioridad()),
            solicitud.getEstado() != null ? solicitud.getEstado().toString() : ""
        };
    }

    public static String[] solicitudEjecucion(Solicitud solicitud) {
        return new String[]{
            valor(solicitud.getId()),
            nombreCliente(solicitud),
            solicitud.getUnidadAsignada() != null ? solicitud.getUnidadAsignada().getTipo().getDescripcion() : "Sin asignar",
            solicitud.getTecnicoAsignado() != null ? solicitud.getTecnicoAsignado().getNombre() : "Sin asignar",
            solicitud.getKit() != null ? solicitud.getKit().getCodigo() : "Sin kit",
            solicitud.getRepuesto() != null ? solicitud.getRepuesto().getCodigoRepuesto() : "Sin repuesto",
            solicitud.getEstado() != null ? solicitud.getEstado().toString() : ""
        };
    }

    public static String[] solicitudCierre(Solicitud solicitud) {
        return new String[]{
            valor(solicitud.getId()),
            nombreCliente(solicitud),
            valor(solicitud.getTipoServicio()),
            solicitud.getUnidadAsignada() != null ? solicitud.getUnidadAsignada().getTipo().getDescripcion() : "Ninguna",
            solicitud.getTecnicoAsignado() != null ? solicitud.getTecnicoAsignado().getNombre() : "No asignado",
            solicitud.getKit() != null ? solicitud.getKit().getCodigo() : "Sin kit",
            solicitud.getRepuesto() != null ? solicitud.getRepuesto().getCodigoRepuesto() : "Sin repuesto",
            solicitud.getFechaCierre() != null ? solicitud.getFechaCierre().format(FORMATO_FECHA) : ""
        };
    }

    public static String[] operacion(Operacion operacion, int orden) {
        return new String[]{
            String.valueOf(orden),
            operacion.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
            operacion.getTipo().toString(),
            valor(operacion.getDetalle())
        };
    }

    private static String nombreCliente(Solicitud solicitud) {
        Cliente cliente = solicitud.getClienteObjeto();
        if (cliente != null) {
            return cliente.toString();
        }
        return valor(solicitud.getCliente());
    }

    private static String valor(String texto) {
        return texto != null ? texto : "";
    }
}
