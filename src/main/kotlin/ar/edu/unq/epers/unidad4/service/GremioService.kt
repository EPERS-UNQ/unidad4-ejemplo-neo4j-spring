package ar.edu.unq.epers.unidad4.service

import ar.edu.unq.epers.unidad4.model.Gremio

interface GremioService {
    fun guardar(gremio: Gremio): Gremio
    fun recuperar(gremioId: Long): Gremio
    fun ingresarIntegrante(personajeId: Long, gremioId: Long)
    fun clearAll()
}