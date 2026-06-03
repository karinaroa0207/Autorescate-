package edu.co.udistrital.model;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExportadorCSV {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    private static final DateTimeFormatter FORMATO_FECHA_CSV = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static void generarReporteCasosCerrados(Lista<Solicitud> casos) throws IOException {
        String fechaActual = LocalDateTime.now().format(FORMATO_FECHA);
        String nombreArchivo = "reporte_cierre_" + fechaActual + ".csv";

        try (FileWriter writer = new FileWriter(nombreArchivo, StandardCharsets.UTF_8)) {
            writer.append("ID_Caso,Documento_Cliente,Nombre_Cliente,Telefono_Cliente,Placa_Vehiculo,Modelo_Vehiculo,Descripcion,Servicio,Zona_Solicitud,Prioridad,Es_Critica,Estado,ID_Unidad,Tipo_Unidad,Zona_Unidad,ID_Tecnico,Nombre_Tecnico,Especialidad_Tecnico,Zona_Tecnico,Codigo_Kit,Descripcion_Kit,Codigo_Repuesto,Nombre_Repuesto,Fecha_Registro,Fecha_Cierre\n");

            for (int i = 0; i < casos.size(); i++) {
                Solicitud s = casos.get(i);                
                String[] fila = filaCSV(s);
                for (int j = 0; j < fila.length; j++) {
                    writer.append(limpiarTexto(fila[j]));
                    if (j < fila.length - 1) {
                        writer.append(",");
                    }
                }
                writer.append("\n");
            }
        }
    }

    private static String[] filaCSV(Solicitud solicitud) {
        Cliente cliente = solicitud.getClienteObjeto();
        Unidad unidad = solicitud.getUnidadAsignada();
        Tecnico tecnico = solicitud.getTecnicoAsignado();
        Kit kit = solicitud.getKit();
        Repuesto repuesto = solicitud.getRepuesto();

        return new String[]{
            valor(solicitud.getId()),
            cliente != null ? valor(cliente.getDocumento()) : "",
            cliente != null ? valor(cliente.getNombre()) : valor(solicitud.getCliente()),
            cliente != null ? valor(cliente.getTelefono()) : "",
            cliente != null ? valor(cliente.getPlacaVehiculo()) : "",
            cliente != null ? valor(cliente.getModeloVehiculo()) : "",
            valor(solicitud.getDescripcion()),
            valor(solicitud.getTipoServicio()),
            valor(solicitud.getZona()),
            String.valueOf(solicitud.getPrioridad()),
            solicitud.isEsCritica() ? "Si" : "No",
            solicitud.getEstado() != null ? solicitud.getEstado().toString() : "",
            unidad != null ? valor(unidad.getId()) : "",
            unidad != null && unidad.getTipo() != null ? unidad.getTipo().getDescripcion() : "",
            unidad != null ? valor(unidad.getZona()) : "",
            tecnico != null ? valor(tecnico.getIdentificacion()) : "",
            tecnico != null ? valor(tecnico.getNombre()) : "",
            tecnico != null ? valor(tecnico.getEspecialidad()) : "",
            tecnico != null ? valor(tecnico.getZona()) : "",
            kit != null ? valor(kit.getCodigo()) : "Sin kit",
            kit != null ? valor(kit.getDescripcion()) : "",
            repuesto != null ? valor(repuesto.getCodigoRepuesto()) : "",
            repuesto != null ? valor(repuesto.getNombre()) : "",
            solicitud.getFechaRegistro() != null ? solicitud.getFechaRegistro().format(FORMATO_FECHA_CSV) : "",
            solicitud.getFechaCierre() != null ? solicitud.getFechaCierre().format(FORMATO_FECHA_CSV) : ""
        };
    }

    private static String limpiarTexto(String valor) {
        if (valor == null) {
            return "";
        }
        String limpio = valor.replace("\"", "\"\"");
        if (limpio.contains(",") || limpio.contains("\"") || limpio.contains("\n")) {
            return "\"" + limpio + "\"";
        }
        return limpio;
    }

    private static String valor(String texto) {
        return texto != null ? texto : "";
    }
}
