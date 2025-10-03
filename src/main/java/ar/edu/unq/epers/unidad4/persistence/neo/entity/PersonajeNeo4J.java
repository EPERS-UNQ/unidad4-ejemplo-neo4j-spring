package ar.edu.unq.epers.unidad4.persistence.neo.entity;

import ar.edu.unq.epers.unidad4.model.Personaje;
import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@ToString
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Node(primaryLabel = "Personaje")
public class PersonajeNeo4J {

    @Id
    @GeneratedValue
    private Long id;
    private Long sourceId;
    private String nombre;
    @Relationship(type = "AMIGO")
    private Set<PersonajeNeo4J> amigos = new HashSet<>();


    public PersonajeNeo4J(Personaje model) {
        this.sourceId = model.getId();
        this.amigos = model.getAmigos()
                .stream()
                .filter(amigo -> !amigo.getId().equals(model.getId()))
                .map(PersonajeNeo4J::new)
                .collect(java.util.stream.Collectors.toSet());
        this.nombre = model.getNombre();
    }


}
