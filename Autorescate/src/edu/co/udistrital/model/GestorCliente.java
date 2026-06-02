package edu.co.udistrital.model;

public class GestorCliente {

    private ListaEnlazada<Cliente> clientes;

    public GestorCliente() {
        clientes = new ListaEnlazada<>();
    }

    public boolean agregarCliente(Cliente cliente) {
        if (cliente == null || buscarPorDocumento(cliente.getDocumento()) != null) {
            return false;
        }
        clientes.agregar(cliente);
        return true;
    }

    public Cliente buscarPorDocumento(String documento) {
        if (documento == null) {
            return null;
        }
        return clientes.buscar(new Criterio<Cliente>() {
            @Override
            public boolean cumple(Cliente dato) {
                return dato.getDocumento() != null && dato.getDocumento().equals(documento);
            }
        });
    }

    public Cliente eliminarCliente(String documento) {
        if (documento == null) {
            return null;
        }
        return clientes.remover(new Criterio<Cliente>() {
            @Override
            public boolean cumple(Cliente dato) {
                return dato.getDocumento() != null && dato.getDocumento().equals(documento);
            }
        });
    }

    public Lista<Cliente> obtenerTodos() {
        Lista<Cliente> lista = new ArregloLista<>();
        clientes.recorrer((cliente) -> {
            lista.add(cliente);
        });
        return lista;
    }

    public int getCantidadClientes() {
        return clientes.getTamano();
    }
}
