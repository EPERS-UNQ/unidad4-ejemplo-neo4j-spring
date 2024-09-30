package ar.edu.unq.epers.unidad4.dao

import ar.edu.unq.epers.unidad4.model.Personaje
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query
import java.util.Optional

interface PersonajeDAO : Neo4jRepository<Personaje, Long> {

    @Query("MATCH(p: Personaje) DETACH DELETE p")
    fun detachDelete()

    @Query("MATCH(p: Personaje {nombre: ${'$'}nombre }) RETURN p")
    fun findByNombre(nombre: String): Optional<Personaje>

    @Query("""
        MATCH(p: Personaje {nombre: ${'$'}nombre })
        MATCH(p)-[:AMIGO*2]->(p2)
        RETURN p2
    """)
    fun amigosDeMisAmigos(nombre: String): Collection<Personaje>
}