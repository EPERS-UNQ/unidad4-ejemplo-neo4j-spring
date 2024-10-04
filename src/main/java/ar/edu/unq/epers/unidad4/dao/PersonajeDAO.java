package ar.edu.unq.epers.unidad4.dao;

import ar.edu.unq.epers.unidad4.model.Personaje;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;

public interface PersonajeDAO extends Neo4jRepository<Personaje, Long> {

    @Query("MATCH(p: Personaje) DETACH DELETE p")
    void detachDelete();

    @Query("MATCH(p: Personaje {nombre: $nombre }) RETURN p")
    Optional<Personaje> findByNombre(@Param("nombre") String nombre);

    @Query("""
        MATCH(p: Personaje {nombre: $nombre })
        MATCH(p)-[:AMIGO*2]->(p2)
        RETURN p2
    """)
    Collection<Personaje> amigosDeMisAmigos(@Param("nombre") String nombre);
}
