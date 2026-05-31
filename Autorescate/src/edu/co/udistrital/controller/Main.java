package edu.co.udistrital.controller;

import edu.co.udistrital.model.CentroOperaciones;
import edu.co.udistrital.view.VentanaPrincipal;

import edu.co.udistrital.model.CentroOperaciones;

import edu.co.udistrital.model.CentroOperaciones;

public class Main {
    public static void main(String[] args) {
        CentroOperaciones modelo = new CentroOperaciones();
        VentanaPrincipal vista = new VentanaPrincipal();
        ControladorPrincipal controlador = new ControladorPrincipal(vista, modelo);
        
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
    }
}