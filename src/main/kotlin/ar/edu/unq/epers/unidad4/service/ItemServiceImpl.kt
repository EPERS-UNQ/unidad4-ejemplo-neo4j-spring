package ar.edu.unq.epers.unidad4.service

import ar.edu.unq.epers.unidad4.dao.ItemDAO
import ar.edu.unq.epers.unidad4.exception.EntityNotFoundException
import ar.edu.unq.epers.unidad4.model.Item
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ItemServiceImpl : ItemService {

    @Autowired lateinit var itemDAO: ItemDAO

    override fun guardar(item: Item): Item {
        return itemDAO.save(item)
    }

    override fun recuperar(itemId: Long): Item {
        return itemDAO.findById(itemId).orElseThrow { EntityNotFoundException("Invalid item #$itemId") }
    }

    override fun getMasPesados(peso: Int): Collection<Item> {
        return itemDAO.getMasPesados(peso)
    }

    override fun getItemsPersonajesDebiles(vida: Int): Collection<Item> {
        return itemDAO.getItemsPersonajesDebiles(vida)
    }

    override fun clearAll() {
        itemDAO.detachDelete()
    }
}