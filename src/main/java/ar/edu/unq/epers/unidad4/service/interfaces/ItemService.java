package ar.edu.unq.epers.unidad4.service.interfaces;

import ar.edu.unq.epers.unidad4.model.Item;

import java.util.Collection;

public interface ItemService {
    Item guardar(Item item);
    Item recuperar(Long itemId);
    Collection<Item> getMasPesados(int peso);
    Collection<Item> getItemsPersonajesDebiles(int vida);
    void clearAll();
}
