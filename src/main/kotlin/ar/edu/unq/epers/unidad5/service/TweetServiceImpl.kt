package ar.edu.unq.epers.unidad5.service

import ar.edu.unq.epers.unidad5.dao.RetweetDAO
import ar.edu.unq.epers.unidad5.dao.TweetDAO
import ar.edu.unq.epers.unidad5.exception.TweetNotFound
import ar.edu.unq.epers.unidad5.model.Retweet
import ar.edu.unq.epers.unidad5.model.Tag
import ar.edu.unq.epers.unidad5.model.Tweet
import ar.edu.unq.epers.unidad5.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TweetServiceImpl: TweetService {

    @Autowired
    lateinit var tweetDAO: TweetDAO

    override fun guardar(tweet: Tweet): Tweet {
        tweetDAO.save(tweet)
        return tweet
    }

    override fun recuperar(id: Long): Tweet {
        return tweetDAO.findById(id).orElseThrow { TweetNotFound("Invalid tweet #$id") }
    }

    override fun tweetsDelUsuario(user: User): List<Tweet> {
        return tweetDAO.tweetsDeUnUsuario(user.username)
    }

    override fun tweetsConTag(tag: Tag): List<Tweet> {
        return tweetDAO.tweetsConTag(tag.tag)
    }

    override fun deleteAll() {
        tweetDAO.detachDelete()
    }

}

@Service
class RetweetServiceImpl: RetweetService {

    @Autowired
    lateinit var retweetDAO: RetweetDAO

    override fun guardar(retweet: Retweet): Retweet {
        retweetDAO.save(retweet)
        return retweet
    }

    override fun recuperar(id: Long): Retweet {
        return retweetDAO.findById(id).orElseThrow { TweetNotFound("Invalid tweet #$id") }
    }

}
