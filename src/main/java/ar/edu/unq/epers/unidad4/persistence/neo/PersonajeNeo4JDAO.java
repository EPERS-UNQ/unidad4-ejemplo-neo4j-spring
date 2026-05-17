package ar.edu.unq.epers.unidad4.persistence.neo;

import ar.edu.unq.epers.unidad4.persistence.neo.entity.PersonajeNeo4J;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;


public interface PersonajeNeo4JDAO extends Neo4jRepository<PersonajeNeo4J, Long> {

    @Query("MATCH(p: Personaje) DETACH DELETE p")
    void detachDelete();

    @Query("""
        MATCH(p: Personaje {nombre: $nombre })
        MATCH(p)-[:AMIGO*2]->(p2)
        RETURN p2
    """)
    Collection<PersonajeNeo4J> amigosDeMisAmigos(@Param("nombre") String nombre);
}
