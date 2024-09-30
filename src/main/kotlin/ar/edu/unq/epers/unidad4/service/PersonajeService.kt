package ar.edu.unq.epers.unidad4.service

import ar.edu.unq.epers.unidad4.model.Personaje

interface PersonajeService {
    fun guardar(personaje: Personaje): Personaje
    fun recuperar(personajeId: Long): Personaje
    fun recuperarPorNombre(nombre: String): Personaje?
    fun recoger(personajeId: Long, itemId: Long)
    fun amigarse(personajeId: Long, amigoId: Long)
    fun amigosDeMisAmigos(nombre: String): Collection<Personaje>
    fun clearAll()
}