package ar.edu.unq.epers.unidad4.model;

import ar.edu.unq.epers.unidad4.exception.MuchoPesoException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"inventario", "amigos"})
@ToString(exclude = {"inventario", "amigos"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Personaje {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nombre;
    private int vida;
    private int pesoMaximo;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Item> inventario = new HashSet<>();

    @Transient
    /* Transient, dado que no queremos persistirlos en SQL,
     * pero los tenemos en la clase que es entidad SQL + objeto de modelo */
    private Set<Personaje> amigos = new HashSet<>();

    public int getPesoActual() {
        return inventario.stream().mapToInt(Item::getPeso).sum();
    }

    public void recoger(Item item) {
        int pesoActual = getPesoActual();
        if (pesoActual + item.getPeso() > this.pesoMaximo) {
            throw new MuchoPesoException(this, item);
        }
        this.inventario.add(item);
        item.setOwner(this);
    }

    public void amigarse(Personaje personaje) {
        this.amigos.add(personaje);
    }

}
