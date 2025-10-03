package ar.edu.unq.epers.unidad4.service.interfaces;

import ar.edu.unq.epers.unidad4.model.Item;
import ar.edu.unq.epers.unidad4.persistence.sql.entity.ItemSQL;

import java.util.Collection;

public interface ItemService {
    Item guardar(Item itemSQL);
    Item recuperar(Long itemId);
    Collection<Item> getMasPesados(int peso);
    Collection<Item> getItemsDePersonajesDebiles(int vida);
    void clearAll();
}
