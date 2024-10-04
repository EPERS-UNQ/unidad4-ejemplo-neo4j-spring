package ar.edu.unq.epers.unidad4.dao;

import ar.edu.unq.epers.unidad4.model.Gremio;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface GremioDAO extends Neo4jRepository<Gremio, Long> {

    @Query("MATCH(g: Gremio) DETACH DELETE g")
    void detachDelete();
}
