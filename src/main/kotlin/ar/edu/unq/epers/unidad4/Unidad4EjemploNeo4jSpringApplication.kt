package ar.edu.unq.epers.unidad4

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories

@SpringBootApplication
@EnableNeo4jRepositories
class Unidad4EjemploNeo4jSpringApplication

fun main(args: Array<String>) {
    runApplication<Unidad4EjemploNeo4jSpringApplication>(*args)
}
