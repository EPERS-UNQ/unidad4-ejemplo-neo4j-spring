package ar.edu.unq.epers.unidad4.exception

import ar.edu.unq.epers.unidad4.model.Gremio

class GremioSinEspacioException(private val gremio: Gremio) : RuntimeException() {

    override val message: String?
    get() = "El gremio [${gremio.nombre}] no tiene espacio para nuevos integrantes"

    companion object {

        private val serialVersionUID = 1L
    }

}