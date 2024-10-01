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
public class Gremio {

    @Id
    @GeneratedValue
    private Long id;
    private String nombre;
    private int capacidad;
    private int integrantes = 0;

    public Gremio(String nombre, int capacidad) {
        this.nombre = nombre;
        this.capacidad = capacidad;
    }

    public void retirarIntegrante() {
        if (integrantes > 0)
            integrantes -= 1;
    }

}
