package ar.edu.unq.epers.unidad5.model

import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node


@Node
class Tag () {

    constructor(tag:String) : this() {
        this.tag = tag
    }

    @Id
    var tag: String? = null
}