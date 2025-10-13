package ar.edu.unq.epers.unidad4.persistence.sql.entity;

import jakarta.persistence.*;
import lombok.*;

@ToString
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
@Table(name = "Item")
public class ItemSQL {

    @Id
    @GeneratedValue
    private Long id;
    private String nombre;
    private int peso;

    @ManyToOne
    private PersonajeSQL owner;

}
