package ar.edu.unq.epers.unidad4.persistence.sql.entity;
import ar.edu.unq.epers.unidad4.model.Personaje;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "Personaje")
public class PersonajeSQL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500, unique = true)
    private String nombre;
    private int vida;
    private int pesoMaximo;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ItemSQL> inventario = new HashSet<>();

    public PersonajeSQL(Personaje model) {
        this.id = model.getId();
        this.nombre = model.getNombre();
        this.vida = model.getVida();
        this.pesoMaximo = model.getPesoMaximo();
        model.getInventario().forEach(item -> this.inventario.add(new ItemSQL(item.getNombre(), item.getPeso())));
    }
}