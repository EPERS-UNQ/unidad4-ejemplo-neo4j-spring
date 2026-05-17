package ar.edu.unq.epers.unidad4.persistence.neo.entity;

import ar.edu.unq.epers.unidad4.model.Personaje;
import lombok.*;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@ToString
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Node(primaryLabel = "Personaje")
public class PersonajeNeo4J {
    @Id // Acá estamos "forzando" a Neo a usar EL MISMO ID en ambas bases, basandonos en nuestra fuente de verdad
    private Long id;
    private String nombre;

    @Relationship(type = "AMIGO")
    private Set<PersonajeNeo4J> amigos = new HashSet<>();

    /**
     * Rehidrata el conjunto de amigos del personaje dado, a partir de los amigos
     * almacenados en esta instancia.
     *
     * La "rehidratación" consiste en tomar los datos persistidos (en este caso,
     * los amigos persistidos en Neo4J) y reconstruir los
     * objetos {@link Personaje} completos, asignándoselos al personaje recibido.
     *
     * @param personaje el {@link Personaje} al que se le rehidratarán (agregaran) los amigos.
     */
    public void proyectarAmigos(Personaje personaje) {
        personaje.setAmigos(this.amigos.stream()
                .map(a -> {
                    var amigo = new Personaje();
                    amigo.setId(a.getId());
                    amigo.setNombre(a.getNombre());
                    return amigo;
                })
                .collect(Collectors.toSet()));
    }

    public PersonajeNeo4J(Personaje personaje) {
        this.id = personaje.getId();
        this.nombre = personaje.getNombre();
        this.amigos = personaje.getAmigos().stream()
                .map(amigo -> {
                    PersonajeNeo4J amigoNeo4J = new PersonajeNeo4J();
                    amigoNeo4J.setId(amigo.getId());
                    amigoNeo4J.setNombre(amigo.getNombre());
                    return amigoNeo4J;
                })
                .filter(amigo -> !amigo.getId().equals(personaje.getId()))
                .collect(Collectors.toSet());
    }
}
