package ar.edu.unq.epers.unidad4.persistence.neo.entity;

import lombok.*;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@ToString
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@Node(primaryLabel = "Personaje")
public class PersonajeNeo4J {
    @Id
    private Long id;
    private String nombre;
    @Relationship(type = "AMIGO")
    private Set<PersonajeNeo4J> amigos = new HashSet<>();
}
