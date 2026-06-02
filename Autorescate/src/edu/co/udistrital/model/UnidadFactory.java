package edu.co.udistrital.model;

public class UnidadFactory {
    
    public static Unidad crearUnidad(String tipo, String zona) {
        return switch (tipo) {
            case "Grua" -> new Grua(zona, 5.0);
            case "Moto de apoyo" -> new MotoApoyo(zona, true);
            case "Camioneta" -> new CamionetaAsistencia(zona, true);
            default -> new VehiculoLiviano(zona, 4);
        };
    }
}
