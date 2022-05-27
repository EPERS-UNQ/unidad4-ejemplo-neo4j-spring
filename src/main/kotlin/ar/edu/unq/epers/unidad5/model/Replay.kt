package ar.edu.unq.epers.unidad5.model

import org.springframework.data.neo4j.core.schema.RelationshipId
import org.springframework.data.neo4j.core.schema.RelationshipProperties
import org.springframework.data.neo4j.core.schema.TargetNode
import java.time.LocalDateTime


@RelationshipProperties
class Replay() {
    @RelationshipId
    var id: Long? = null
    var fecha: LocalDateTime = LocalDateTime.now()

    @TargetNode
    var tweet: Tweet? = null

}