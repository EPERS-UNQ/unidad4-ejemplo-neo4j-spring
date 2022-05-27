package ar.edu.unq.epers.unidad5.model

import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node

@Node
class User {
    @Id
    @GeneratedValue
    var id: Long? = null

    var username:String? = null
    var nombre:String? = null
}
