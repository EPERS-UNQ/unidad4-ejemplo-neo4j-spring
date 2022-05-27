package ar.edu.unq.epers.unidad5

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories

@SpringBootApplication
@EnableNeo4jRepositories
class Unidad5EjemploNeo4jSpringApplication

fun main(args: Array<String>) {
    runApplication<Unidad5EjemploNeo4jSpringApplication>(*args)
}
