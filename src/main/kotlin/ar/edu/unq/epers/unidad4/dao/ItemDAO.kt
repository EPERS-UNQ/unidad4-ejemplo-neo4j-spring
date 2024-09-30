package ar.edu.unq.epers.unidad4.dao

import ar.edu.unq.epers.unidad4.model.Item
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query

interface ItemDAO : Neo4jRepository<Item, Long> {

    @Query("MATCH(i: Item) DETACH DELETE i")
    fun detachDelete()

    @Query("""
        MATCH(i: Item) 
        WHERE i.peso > ${'$'}peso 
        RETURN i
    """)
    fun getMasPesados(peso: Int): Collection<Item>

    @Query("""
        MATCH(i: Item)
        Match (p)-[POSEE]->(i)
        WHERE p.vida < ${'$'}vida 
        RETURN i
    """)
    fun getItemsPersonajesDebiles(vida: Int): Collection<Item>
}