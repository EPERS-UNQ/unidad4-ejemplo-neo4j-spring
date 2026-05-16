package ar.edu.unq.epers.unidad4.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@EqualsAndHashCode(exclude = "owner")
@ToString(exclude = "owner")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nombre;
    private int peso;

    @ManyToOne
    private Personaje owner;

    public Item(String nombre, int peso) {
        this.nombre = nombre;
        this.peso = peso;
    }

}
