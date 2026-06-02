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

    public Cliente modificarCliente(String documento, String nuevoNombre, String nuevoTelefono, String nuevaPlaca, String nuevoModelo) {
        Cliente c = buscarPorDocumento(documento);
        if (c == null) return null;
        if (nuevoNombre != null && !nuevoNombre.isEmpty()) c.setNombre(nuevoNombre);
        if (nuevoTelefono != null && !nuevoTelefono.isEmpty()) c.setTelefono(nuevoTelefono);
        if (nuevaPlaca != null && !nuevaPlaca.isEmpty()) c.setPlacaVehiculo(nuevaPlaca);
        if (nuevoModelo != null && !nuevoModelo.isEmpty()) c.setModeloVehiculo(nuevoModelo);
        return c;
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
