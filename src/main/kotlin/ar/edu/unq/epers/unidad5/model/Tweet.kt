package ar.edu.unq.epers.unidad5.model

import org.springframework.data.neo4j.core.schema.*
import java.time.LocalDateTime


@Node
class Tweet {
    @Id
    @GeneratedValue
    var id: Long? = null

    var mensaje:String? = null
    var fecha: LocalDateTime= LocalDateTime.now()

    @Relationship(type = "POST", direction = Relationship.Direction.INCOMING)
    var usuario:User? = null

    @Relationship(type = "MENTION")
    var menciones: Set<User>? = null

    @Relationship(type = "REPLAY")
    var replay: Replay? = null

    @Relationship(type = "QUOTE")
    var quote: Tweet? = null

    @Relationship(type = "TAG")
    var tags: Set<Tag>? = null
}
