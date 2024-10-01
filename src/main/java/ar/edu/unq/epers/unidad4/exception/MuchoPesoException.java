package ar.edu.unq.epers.unidad4.exception;

import ar.edu.unq.epers.unidad4.model.Item;
import ar.edu.unq.epers.unidad4.model.Personaje;

final public class MuchoPesoException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private final Personaje personaje;
    private final Item item;

    public MuchoPesoException(Personaje personaje, Item item) {
        this.personaje = personaje;
        this.item = item;
    }

    @Override
    public String getMessage() {
        return "El personaje [" + personaje.getNombre() + "] no puede recoger [" + item.getNombre() + "] porque cargar mucho peso ya";
    }

}
