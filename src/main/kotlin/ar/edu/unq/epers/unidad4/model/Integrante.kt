package ar.edu.unq.epers.unidad4.model

import org.springframework.data.neo4j.core.schema.RelationshipId
import org.springframework.data.neo4j.core.schema.RelationshipProperties
import org.springframework.data.neo4j.core.schema.TargetNode
import java.time.LocalDateTime

@RelationshipProperties
class Integrante() {

    @RelationshipId
    var id: Long? = null
    var fecha: LocalDateTime = LocalDateTime.now()

    @TargetNode
    var gremio: Gremio? = null

    constructor(gremio: Gremio) : this() {
        this.gremio = gremio
    }

}