package ar.edu.unq.epers.unidad4.service.impl;

import ar.edu.unq.epers.unidad4.persistence.sql.ItemSQLDAO;
import ar.edu.unq.epers.unidad4.exception.EntityNotFoundException;
import ar.edu.unq.epers.unidad4.persistence.sql.entity.ItemSQL;
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
    public ItemSQL guardar(ItemSQL itemSQL) {
        return itemSQLDAO.save(itemSQL);
    }

    @Override
    public ItemSQL recuperar(Long itemId) {
        return itemSQLDAO.findById(itemId).orElseThrow(() -> new EntityNotFoundException("item", itemId));
    }

    @Override
    public Collection<ItemSQL> getMasPesados(int peso) {
        return itemSQLDAO.getMasPesados(peso);
    }

    @Override
    public Collection<ItemSQL> getItemsDePersonajesDebiles(int vida) {
        return itemSQLDAO.getItemsDePersonajesDebiles(vida);
    }

    @Override
    public void clearAll() {
        itemSQLDAO.deleteAll();
    }
}
