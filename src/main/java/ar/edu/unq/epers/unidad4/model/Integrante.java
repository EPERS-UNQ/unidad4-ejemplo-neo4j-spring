package ar.edu.unq.epers.unidad4.model;

import lombok.*;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;
import java.time.LocalDateTime;

@ToString
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@RelationshipProperties
public class Integrante {

    @RelationshipId
    private Long id;
    private LocalDateTime fecha = LocalDateTime.now();

    @TargetNode
    private Gremio gremio;

    public Integrante(Gremio gremio){
        this.gremio = gremio;
    }

}
