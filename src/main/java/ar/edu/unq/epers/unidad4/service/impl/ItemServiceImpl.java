package ar.edu.unq.epers.unidad4.service.impl;

import ar.edu.unq.epers.unidad4.model.Item;
import ar.edu.unq.epers.unidad4.persistence.sql.ItemDAO;
import ar.edu.unq.epers.unidad4.service.interfaces.ItemService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemDAO itemDAO;

    public ItemServiceImpl(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    @Override
    public Item guardar(Item item) {
        return itemDAO.save(item);
    }

    @Override
    public Item recuperar(Long itemId) {
        return itemDAO.findById(itemId).orElseThrow(() -> new EntityNotFoundException("No se encontro un item con id " + itemId));
    }

    @Override
    public Collection<Item> getMasPesados(int peso) {
        return itemDAO.getMasPesados(peso);
    }

    @Override
    public Collection<Item> getItemsDePersonajesDebiles(int vida) {
        return itemDAO.getItemsDePersonajesDebiles(vida);
    }

    @Override
    public void clearAll() {
        itemDAO.deleteAll();
    }
}
