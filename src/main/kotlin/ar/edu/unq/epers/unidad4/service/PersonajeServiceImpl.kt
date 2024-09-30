package ar.edu.unq.epers.unidad4.service

import ar.edu.unq.epers.unidad4.dao.ItemDAO
import ar.edu.unq.epers.unidad4.dao.PersonajeDAO
import ar.edu.unq.epers.unidad4.exception.EntityNotFoundException
import ar.edu.unq.epers.unidad4.model.Personaje
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PersonajeServiceImpl : PersonajeService {

    @Autowired lateinit var personajeDAO: PersonajeDAO
    @Autowired lateinit var itemDAO: ItemDAO

    override fun guardar(personaje: Personaje): Personaje {
        return personajeDAO.save(personaje)
    }

    override fun recuperar(personajeId: Long): Personaje {
        return personajeDAO.findById(personajeId).orElseThrow { EntityNotFoundException("Invalid personaje #$personajeId") }
    }

    override fun recuperarPorNombre(nombre: String): Personaje? {
        return personajeDAO.findByNombre(nombre).orElseThrow { EntityNotFoundException("Invalid personaje #$nombre") }
    }

    override fun recoger(personajeId: Long, itemId: Long) {
        val personaje = personajeDAO.findById(personajeId).get()
        val item = itemDAO.findById(itemId).get()

        personaje.recoger(item)

        personajeDAO.save(personaje)
    }

    override fun amigarse(personajeId: Long, amigoId: Long) {
        val personaje = personajeDAO.findById(personajeId).get()
        val amigo = personajeDAO.findById(amigoId).get()

        personaje.amigarse(amigo)

        personajeDAO.save(personaje)
    }

    override fun amigosDeMisAmigos(nombre: String): Collection<Personaje> {
        return personajeDAO.amigosDeMisAmigos(nombre)
    }

    override fun clearAll() {
        personajeDAO.detachDelete()
    }
}