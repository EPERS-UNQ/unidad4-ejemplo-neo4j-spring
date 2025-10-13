package ar.edu.unq.epers.unidad4.model;


import ar.edu.unq.epers.unidad4.exception.MuchoPesoException;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Personaje {
    private Long id;
    private String nombre;
    private int vida;
    private int pesoMaximo;
    private Set<Item> inventario = new HashSet<>();
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
