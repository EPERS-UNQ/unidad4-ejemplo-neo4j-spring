package ar.edu.unq.epers.unidad4.service

import ar.edu.unq.epers.unidad4.dao.GremioDAO
import ar.edu.unq.epers.unidad4.dao.PersonajeDAO
import ar.edu.unq.epers.unidad4.exception.EntityNotFoundException
import ar.edu.unq.epers.unidad4.model.Gremio
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GremioServiceImpl : GremioService {

    @Autowired lateinit var gremioDAO: GremioDAO
    @Autowired lateinit var personajeDAO: PersonajeDAO

    override fun guardar(gremio: Gremio): Gremio {
        return gremioDAO.save(gremio)
    }

    override fun recuperar(gremioId: Long): Gremio {
        return gremioDAO.findById(gremioId).orElseThrow { EntityNotFoundException("Invalid gremio #$gremioId") }
    }

    override fun ingresarIntegrante(personajeId: Long, gremioId: Long) {
        val personaje = personajeDAO.findById(personajeId).get()
        val gremio = gremioDAO.findById(gremioId).get()

        if (personaje.integrante != null)
            personaje.retirarseDeGremio()

        personaje.ingresar(gremio)

        personajeDAO.save(personaje)
    }

    override fun clearAll() {
        gremioDAO.detachDelete()
    }
}