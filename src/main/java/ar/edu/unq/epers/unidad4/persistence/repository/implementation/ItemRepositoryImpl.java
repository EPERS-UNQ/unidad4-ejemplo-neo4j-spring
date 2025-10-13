package ar.edu.unq.epers.unidad4.persistence.repository.implementation;

import ar.edu.unq.epers.unidad4.exception.EntityNotFoundException;
import ar.edu.unq.epers.unidad4.model.Item;
import ar.edu.unq.epers.unidad4.persistence.repository.ItemRepository;
import ar.edu.unq.epers.unidad4.persistence.sql.ItemSQLDAO;
import ar.edu.unq.epers.unidad4.persistence.sql.entity.ItemSQL;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class ItemRepositoryImpl implements ItemRepository {
    private final ItemSQLDAO itemSQLDAO;
    private final ModelMapper modelMapper;

    public ItemRepositoryImpl(ItemSQLDAO itemSQLDAO, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.itemSQLDAO = itemSQLDAO;
    }

    @Override
    public Item guardar(Item item) {
        ItemSQL itemSQL = modelMapper.map(item, ItemSQL.class);
        itemSQLDAO.save(itemSQL);
        item.setId(itemSQL.getId());
        return item;
    }

    @Override
    public Item recuperar(Long itemId) {
        ItemSQL itemSQL = itemSQLDAO.findById(itemId).orElseThrow(() -> new EntityNotFoundException("item", itemId));
        return modelMapper.map(itemSQL, Item.class);
    }

    @Override
    public Collection<Item> getMasPesados(int peso) {

        var items = itemSQLDAO.getMasPesados(peso);

        return items.stream().map((element) -> modelMapper.map(element, Item.class)).collect(Collectors.toSet());
    }

    @Override
    public Collection<Item> getItemsDePersonajesDebiles(int vida) {
        var items = itemSQLDAO.getItemsDePersonajesDebiles(vida);

        return items.stream().map((element) -> modelMapper.map(element, Item.class)).collect(Collectors.toSet());
    }

    @Override
    public void clearAll() {
        itemSQLDAO.deleteAll();
    }
}
