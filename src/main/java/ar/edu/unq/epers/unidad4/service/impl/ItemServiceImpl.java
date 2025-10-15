package ar.edu.unq.epers.unidad4.service.impl;

import ar.edu.unq.epers.unidad4.model.Item;
import ar.edu.unq.epers.unidad4.persistence.sql.ItemSQLDAO;
import ar.edu.unq.epers.unidad4.service.interfaces.ItemService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemSQLDAO itemSQLDAO;

    public ItemServiceImpl(ItemSQLDAO itemSQLDAO) {
        this.itemSQLDAO = itemSQLDAO;
    }

    @Override
    public Item guardar(Item item) {
        return itemSQLDAO.save(item);
    }

    @Override
    public Item recuperar(Long itemId) {
        return itemSQLDAO.getById(itemId);
    }

    @Override
    public Collection<Item> getMasPesados(int peso) {
        return itemSQLDAO.getMasPesados(peso);
    }

    @Override
    public Collection<Item> getItemsDePersonajesDebiles(int vida) {
        return itemSQLDAO.getItemsDePersonajesDebiles(vida);
    }

    @Override
    public void clearAll() {
        itemSQLDAO.deleteAll();
    }
}
