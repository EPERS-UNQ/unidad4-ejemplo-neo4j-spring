package ar.edu.unq.epers.unidad4.service.impl;

import ar.edu.unq.epers.unidad4.model.Item;
import ar.edu.unq.epers.unidad4.persistence.repository.ItemRepository;
import ar.edu.unq.epers.unidad4.exception.EntityNotFoundException;
import ar.edu.unq.epers.unidad4.persistence.sql.entity.ItemSQL;
import ar.edu.unq.epers.unidad4.service.interfaces.ItemService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Item guardar(Item item) {
        return itemRepository.guardar(item);
    }

    @Override
    public Item recuperar(Long itemId) {
        return itemRepository.recuperar(itemId);
    }

    @Override
    public Collection<Item> getMasPesados(int peso) {
        return itemRepository.getMasPesados(peso);
    }

    @Override
    public Collection<Item> getItemsDePersonajesDebiles(int vida) {
        return itemRepository.getItemsDePersonajesDebiles(vida);
    }

    @Override
    public void clearAll() {
        itemRepository.clearAll();
    }
}
