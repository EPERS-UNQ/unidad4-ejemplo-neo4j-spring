package ar.edu.unq.epers.unidad4.persistence.neo.entity;

import ar.edu.unq.epers.unidad4.model.Personaje;
import lombok.*;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@ToString
@Setter
@Getter
@EqualsAndHashCode

@Node(primaryLabel = "Personaje")
public class PersonajeNeo4J {
    @Id
    Long id;
    String nombre;
    @Relationship(type = "AMIGO")
    Set<PersonajeNeo4J> amigos = new HashSet<>();

    public PersonajeNeo4J() {
    }
}
