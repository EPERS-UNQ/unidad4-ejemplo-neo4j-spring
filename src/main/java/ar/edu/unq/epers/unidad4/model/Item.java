package ar.edu.unq.epers.unidad4.model;

import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@ToString
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Node
public class Item {

    @Id
    @GeneratedValue
    private Long id;
    private String nombre;
    private int peso;

    public Item(String nombre, int peso) {
        this.nombre = nombre;
        this.peso = peso;
    }

}
