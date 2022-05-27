package ar.edu.unq.epers.unidad5.model

import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship


@Node
class Retweet {
    @Id
    @GeneratedValue
    var id: Long? = null

    @Relationship(type = "RT")
    var tweet:Tweet? = null

    @Relationship(type = "POST", direction = Relationship.Direction.INCOMING)
    var usuario:User? = null
}