package ar.edu.unq.epers.unidad4.dao;

import ar.edu.unq.epers.unidad4.model.Item;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface ItemDAO extends Neo4jRepository<Item, Long> {

    @Query("MATCH(i: Item) DETACH DELETE i")
    void detachDelete();

    @Query("""
        MATCH(i: Item)
        WHERE i.peso > $peso
        RETURN i
    """)
    Collection<Item> getMasPesados(@Param("peso") int peso);

    @Query("""
        MATCH(i: Item)
        MATCH (p)-[POSEE]->(i)
        WHERE p.vida < $vida
        RETURN i
    """)
    Collection<Item> getItemsPersonajesDebiles(@Param("vida") int vida);
}
