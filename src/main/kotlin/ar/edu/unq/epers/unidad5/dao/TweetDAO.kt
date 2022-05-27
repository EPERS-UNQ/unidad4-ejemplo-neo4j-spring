package ar.edu.unq.epers.unidad5.dao

import ar.edu.unq.epers.unidad5.model.Retweet
import ar.edu.unq.epers.unidad5.model.Tag
import ar.edu.unq.epers.unidad5.model.Tweet
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query
import org.springframework.stereotype.Repository

@Repository
interface TweetDAO : Neo4jRepository<Tweet, Long?>{
    @Query("MATCH(n) DETACH DELETE n")
    fun detachDelete()

    @Query("""
        Match(t:Tweet)
        Match(u:User {username: ${'$'}username })
        Match (u)-[POST]-(t)
        return t
    """)
    fun  tweetsDeUnUsuario(username:String?): List<Tweet>


    @Query("""
        Match(t:Tag {tag: ${'$'}tag })
        Match (t)-[:TAG]-(tweet)
        return tweet
    """)
    fun  tweetsConTag(tag:String?): List<Tweet>
}

@Repository
interface RetweetDAO : Neo4jRepository<Retweet, Long?>