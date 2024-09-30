package ar.edu.unq.epers.unidad4.service

import ar.edu.unq.epers.unidad4.model.Item

interface ItemService {
    fun guardar(item: Item): Item
    fun recuperar(itemId: Long): Item
    fun getMasPesados(peso: Int): Collection<Item>
    fun getItemsPersonajesDebiles(vida: Int): Collection<Item>
    fun clearAll()
}