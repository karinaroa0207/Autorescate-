package edu.co.udistrital.model;

public class GestorKits {

    private Pila<Kit> kitsEnRevision;
    private Pila<Kit> kitsListos;   
    private Lista<Kit> kits;

    public GestorKits() {
        this.kitsEnRevision = new Pila<>();
        this.kitsListos = new Pila<>();    
        this.kits = new ArregloLista<>();
    }

    public boolean agregarKit(Kit kit) {
        kitsListos.apilar(kit);
        kits.add(kit);
        return true;
    }

    public void recibirKit(Kit kit) {
        kit.setEstado(EstadoKit.EN_REVISION);
        kitsEnRevision.apilar(kit);        
    }

    public Kit despacharKit() {       
        Kit kit = kitsListos.desapilar();
        if (kit != null) {
            kit.setEstado(EstadoKit.ASIGNADO);            
        }
        return kit;
    }
    
    public boolean revisarKit() {
        Kit kit = kitsEnRevision.desapilar();
        if (kit != null) {
            kit.setEstado(EstadoKit.DISPONIBLE);            
            return true;
        }
        return false;
    }    

    public Lista<Kit> getKitsListos() {
        Lista<Kit> resultado = new ArregloLista<>();
        MiIterador<Kit> it = this.kitsListos.iterator();
        
        while (it.hasNext()) {
            Kit kit = it.next();
            resultado.add(kit);
        }
        
        return resultado;
    }

    public Lista<Kit> getKitsEnRevision() {
        Lista<Kit> resultado = new ArregloLista<>();
        MiIterador<Kit> it = this.kitsEnRevision.iterator();
        
        while (it.hasNext()) {
            Kit kit = it.next();
            resultado.add(kit);
        }
        
        return resultado;
    }
    
    public Kit getUltimoListo() {
        return kitsListos.getCima().getDato();
    }
    
    public Lista<Kit> getAllKits() {
        return kits;
    }    
    
    public void revertirAsignacion(Kit kit) {
        kit.revertirAsignacion();        
        kitsListos.apilar(kit);
    }
    
    public void revertirCreacion() {
        Kit kit = kitsListos.desapilar();
        for (int i = 0; i < kits.size(); i++) {
            Kit actual = kits.get(i);
            if(actual == kit) {
                kits.remove(i);
                return;
            }
        }
    }
    
}
