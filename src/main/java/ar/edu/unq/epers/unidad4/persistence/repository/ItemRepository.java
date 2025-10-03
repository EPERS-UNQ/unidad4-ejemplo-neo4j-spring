package ar.edu.unq.epers.unidad4.persistence.repository;

import ar.edu.unq.epers.unidad4.model.Item;

import java.util.Collection;

public interface ItemRepository {
    Item guardar(Item item);
    Item recuperar(Long itemId);
    Collection<Item> getMasPesados(int peso);
    Collection<Item> getItemsDePersonajesDebiles(int vida);
    void clearAll();
}
