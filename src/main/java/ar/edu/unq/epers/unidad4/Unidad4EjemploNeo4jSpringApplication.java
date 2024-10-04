package ar.edu.unq.epers.unidad4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
@EnableNeo4jRepositories
public class Unidad4EjemploNeo4jSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(Unidad4EjemploNeo4jSpringApplication.class, args);
    }

}