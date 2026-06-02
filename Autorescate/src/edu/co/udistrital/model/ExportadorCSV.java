package edu.co.udistrital.model;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExportadorCSV {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    public static void generarReporteCasosCerrados(Lista<Solicitud> casos) throws IOException {
        String fechaActual = LocalDateTime.now().format(FORMATO_FECHA);
        String nombreArchivo = "reporte_cierre_" + fechaActual + ".csv";

        try (FileWriter writer = new FileWriter(nombreArchivo, StandardCharsets.UTF_8)) {
            writer.append("ID_Caso,Documento_Cliente,Nombre_Cliente,Telefono_Cliente,Placa_Vehiculo,Modelo_Vehiculo,Descripcion,Servicio,Zona_Solicitud,Prioridad,Es_Critica,Estado,ID_Unidad,Tipo_Unidad,Zona_Unidad,ID_Tecnico,Nombre_Tecnico,Especialidad_Tecnico,Zona_Tecnico,Codigo_Kit,Descripcion_Kit,Fecha_Registro,Fecha_Cierre\n");

            for (int i = 0; i < casos.size(); i++) {
                Solicitud s = casos.get(i);                
                String[] fila = s.toCSVRow();
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
}
