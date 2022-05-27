package ar.edu.unq.epers.unidad5

import ar.edu.unq.epers.unidad5.model.*
import ar.edu.unq.epers.unidad5.service.RetweetService
import ar.edu.unq.epers.unidad5.service.TweetService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TweetServiceTest {

    @Autowired
    lateinit var tweetService: TweetService
    @Autowired
    lateinit var retweetService: RetweetService

    lateinit var tweet1:Tweet
    lateinit var rubius:User
    lateinit var nnydjesus: User
    lateinit var viral:Tag

    @BeforeEach
    fun save() {
        viral = Tag("Viral")
        nnydjesus = User().apply {
            username = "nnydjesus"
            nombre = "Ronny"
        }
        rubius = User().apply {
            username = "rubius"
            nombre = "Rubius"
        }
        tweet1 = Tweet().apply {
            mensaje = "Limonada"
            usuario = nnydjesus
            menciones = setOf(rubius)
            tags = setOf(viral)
        }
        tweetService.guardar(tweet1)
    }

    @Test
    fun `guardar un tweet y recuperarlo`(){
        val recuperado = tweetService.recuperar(tweet1.id!!)
        Assertions.assertEquals(tweet1.id, recuperado.id)
    }

    @Test
    fun `guardar un retweet`(){
        val retweet = Retweet().apply {
            tweet = tweet1
            usuario = rubius
        }
        retweetService.guardar(retweet)
        val recuperado = retweetService.recuperar(retweet.id!!)
        Assertions.assertEquals(retweet.id, recuperado.id)
    }

    @Test
    fun `hacer un replay de un tweet`(){
        val tweet2 = Tweet().apply {
            mensaje = "Limonada 2.0"
            usuario = nnydjesus
            replay = Replay().apply { tweet = tweet1 }
            tags = setOf(Tag("Viral" ), Tag("LOL" ))
        }
        tweetService.guardar(tweet2)
        Assertions.assertNotNull(tweet2.id)
    }

    @Test
    fun `devolver tweets de un usuario`(){
        val tweets = tweetService.tweetsDelUsuario(nnydjesus)
        Assertions.assertEquals(tweets.size, 1)
    }

    @Test
    fun `devolver tweets con el tag`(){
        val tweets = tweetService.tweetsConTag(viral)
        Assertions.assertEquals(tweets.size, 1)
    }


    @AfterEach
    fun clean(){
        tweetService.deleteAll()
    }
}