package ar.edu.unq.epers.unidad4.persistence.repository.implementation;

import ar.edu.unq.epers.unidad4.exception.EntityNotFoundException;
import ar.edu.unq.epers.unidad4.model.Item;
import ar.edu.unq.epers.unidad4.model.Personaje;
import ar.edu.unq.epers.unidad4.persistence.repository.ItemRepository;
import ar.edu.unq.epers.unidad4.persistence.sql.ItemSQLDAO;
import ar.edu.unq.epers.unidad4.persistence.sql.entity.ItemSQL;
import ar.edu.unq.epers.unidad4.persistence.sql.entity.PersonajeSQL;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class ItemRepositoryImpl implements ItemRepository {
    private final ItemSQLDAO itemSQLDAO;

    public ItemRepositoryImpl(ItemSQLDAO itemSQLDAO) {
        this.itemSQLDAO = itemSQLDAO;
    }

    @Override
    public Item guardar(Item item) {
        ItemSQL itemSQL = new ItemSQL(item.getNombre(), item.getPeso());
        itemSQLDAO.save(itemSQL);
        item.setId(itemSQL.getId());
        return item;
    }

    @Override
    public Item recuperar(Long itemId) {
        ItemSQL itemSQL =itemSQLDAO.findById(itemId).orElseThrow(() -> new EntityNotFoundException("item", itemId));
        return new Item(itemSQL);
    }

    @Override
    public Collection<Item> getMasPesados(int peso) {

        var items = itemSQLDAO.getMasPesados(peso);

        return items.stream().map(Item::from).toList();
    }

    @Override
    public Collection<Item> getItemsDePersonajesDebiles(int vida) {
        var items = itemSQLDAO.getItemsDePersonajesDebiles(vida);

        return items.stream().map(Item::from).toList();
    }

    @Override
    public void clearAll() {
        itemSQLDAO.deleteAll();
    }
}
