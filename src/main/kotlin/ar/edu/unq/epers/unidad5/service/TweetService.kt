package ar.edu.unq.epers.unidad5.service

import ar.edu.unq.epers.unidad5.model.Retweet
import ar.edu.unq.epers.unidad5.model.Tag
import ar.edu.unq.epers.unidad5.model.Tweet
import ar.edu.unq.epers.unidad5.model.User

interface TweetService {
    fun guardar(tweet:Tweet):Tweet
    fun recuperar(id:Long):Tweet
    fun tweetsDelUsuario(user:User):List<Tweet>
    fun tweetsConTag(tag:Tag):List<Tweet>

    fun deleteAll()
}

interface RetweetService {
    fun guardar(retweet: Retweet):Retweet
    fun recuperar(id:Long):Retweet
}