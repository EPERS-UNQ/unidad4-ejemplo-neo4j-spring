package ar.edu.unq.epers.unidad4.service.interfaces;

import ar.edu.unq.epers.unidad4.persistence.sql.entity.ItemSQL;

import java.util.Collection;

public interface ItemService {
    ItemSQL guardar(ItemSQL itemSQL);
    ItemSQL recuperar(Long itemId);
    Collection<ItemSQL> getMasPesados(int peso);
    Collection<ItemSQL> getItemsDePersonajesDebiles(int vida);
    void clearAll();
}
