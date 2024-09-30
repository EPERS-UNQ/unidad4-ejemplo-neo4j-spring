package ar.edu.unq.epers.unidad4.model

import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship

@Node
class Item() {

    @Id
    @GeneratedValue
    var id: Long? = null
    var nombre: String = ""
    var peso: Int = 0

    @Relationship(type = "POSEE", direction = Relationship.Direction.INCOMING)
    var owner: Personaje? = null

    constructor(nombre: String, peso: Int) : this() {
        this.nombre = nombre
        this.peso = peso
    }

}