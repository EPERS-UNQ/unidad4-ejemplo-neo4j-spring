package ar.edu.unq.epers.unidad4.persistence.repositorys.impl;

import ar.edu.unq.epers.unidad4.exception.EntityNotFoundException;
import ar.edu.unq.epers.unidad4.model.Item;
import ar.edu.unq.epers.unidad4.persistence.DAOs.ItemDAO;
import ar.edu.unq.epers.unidad4.persistence.repositorys.interfaces.ItemRepository;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class ItemRepositoryImpl implements ItemRepository {

    final ItemDAO itemDAO;

    public ItemRepositoryImpl(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    @Override
    public Item guardar(Item item) {
        return itemDAO.save(item);
    }

    @Override
    public Item recuperar(Long itemId) {
        return itemDAO.findById(itemId).orElseThrow(() -> new EntityNotFoundException("item", itemId));
    }

    @Override
    public Collection<Item> getMasPesados(int peso) {
        return itemDAO.getMasPesados(peso);
    }

    @Override
    public Collection<Item> getItemsPersonajesDebiles(int vida) {
        return itemDAO.getItemsPersonajesDebiles(vida);
    }

    @Override
    public void clearAll() {
        itemDAO.detachDelete();
    }
}
