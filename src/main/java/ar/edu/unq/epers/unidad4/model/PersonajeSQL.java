package ar.edu.unq.epers.unidad4.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter @Setter
@Entity
public class PersonajeSQL {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 500, unique = true)
    private String nombre;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "friends",
            joinColumns = @JoinColumn(name = "personaje_id"),
            inverseJoinColumns = @JoinColumn(name = "amigo_id")
    )
    private List<PersonajeSQL> amigos;

    public PersonajeSQL(String nombre) {
        this.nombre = nombre;
        this.amigos = new ArrayList<PersonajeSQL>();
    }

    public PersonajeSQL() {

    }

    public void amigarse(PersonajeSQL amigo) {
        amigos.add(amigo);
    }
}
