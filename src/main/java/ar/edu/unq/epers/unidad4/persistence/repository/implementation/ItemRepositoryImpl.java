package ar.edu.unq.epers.unidad4.persistence.repository.implementation;

import ar.edu.unq.epers.unidad4.exception.EntityNotFoundException;
import ar.edu.unq.epers.unidad4.model.Item;
import ar.edu.unq.epers.unidad4.persistence.repository.ItemRepository;
import ar.edu.unq.epers.unidad4.persistence.sql.ItemSQLDAO;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class ItemRepositoryImpl implements ItemRepository {
    private final ItemSQLDAO itemSQLDAO;

    public ItemRepositoryImpl(ItemSQLDAO itemSQLDAO) {
        this.itemSQLDAO = itemSQLDAO;
    }

    @Override
    public Item guardar(Item item) {
        return itemSQLDAO.save(item);
    }

    @Override
    public Item recuperar(Long itemId) {
        return itemSQLDAO.findById(itemId).orElseThrow(() -> new EntityNotFoundException("item", itemId));
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
