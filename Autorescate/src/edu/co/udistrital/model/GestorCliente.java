package edu.co.udistrital.model;

public class GestorCliente {

    private ListaEnlazada<Cliente> clientes;

    public GestorCliente() {
        this.clientes = new ListaEnlazada<>();
    }

    public void agregarCliente(Cliente c) {
        clientes.agregar(c);
    }

    public Lista<Cliente> obtenerTodos() {
        Lista<Cliente> lista = new ArregloLista<>();
        Nodo<Cliente> actual = clientes.getCabeza();
        while (actual != null) {
            lista.add(actual.getDato());
            actual = actual.getSiguiente();
        }
        return lista;
    }

    public Cliente buscarPorDocumento(String documento) {
        return clientes.buscar(new Criterio<Cliente>() {
            @Override
            public boolean cumple(Cliente dato) {
                return dato.getDocumento().equals(documento);
            }
        });
    }

    public Cliente buscarPorId(String id) {
        return clientes.buscar(new Criterio<Cliente>() {
            @Override
            public boolean cumple(Cliente dato) {
                return dato.getId().equals(id);
            }
        });
    }

    public Cliente modificarCliente(String id, String documento, String nombre, String telefono, String placa, String modelo) {
        Cliente encontrado = buscarPorId(id);
        if (encontrado != null) {
            encontrado.setDocumento(documento);
            encontrado.setNombre(nombre);
            encontrado.setTelefono(telefono);
            encontrado.setPlacaVehiculo(placa);
            encontrado.setModeloVehiculo(modelo);
        }
        return encontrado;
    }

    public boolean eliminarCliente(String id) {
        Cliente removido = clientes.remover(new Criterio<Cliente>() {
            @Override
            public boolean cumple(Cliente dato) {
                return dato.getId().equals(id);
            }
        });
        return removido != null;
    }
}
