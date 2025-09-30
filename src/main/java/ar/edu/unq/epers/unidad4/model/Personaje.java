package ar.edu.unq.epers.unidad4.model;


import ar.edu.unq.epers.unidad4.exception.MuchoPesoException;
import ar.edu.unq.epers.unidad4.persistence.neo.entity.PersonajeNeo4J;
import ar.edu.unq.epers.unidad4.persistence.sql.entity.PersonajeSQL;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Personaje {
    private Long id;
    private String nombre;
    private int vida;
    private int pesoMaximo;
    private Set<Item> inventario = new HashSet<>();
    private Set<Personaje> amigos = new HashSet<>();

    public Personaje(PersonajeSQL personajeSQL) {
        this.id = personajeSQL.getId();
        this.nombre = personajeSQL.getNombre();
        this.vida = personajeSQL.getVida();
        this.pesoMaximo = personajeSQL.getPesoMaximo();
        this.inventario = personajeSQL.getInventario()
                .stream()
                .map(Item::new).collect(Collectors.toSet());
    }

    public static Personaje from(PersonajeSQL personajeSQL, PersonajeNeo4J personajeNeo4J) {
        Personaje personaje = new Personaje();
        personaje.setId(personajeSQL.getId());
        personaje.setNombre(personajeSQL.getNombre());
        personaje.setVida(personajeSQL.getVida());
        personaje.setPesoMaximo(personajeSQL.getPesoMaximo());
        personaje.amigos = personajeNeo4J.getAmigos()
                .stream()
                .filter(amigo -> !amigo.getId().equals(personajeSQL.getId()))
                .map(amigo -> {
                    Personaje amigoPersonaje = new Personaje();
                    amigoPersonaje.setId(amigo.getSourceId());
                    return amigoPersonaje;
                })
                .collect(java.util.stream.Collectors.toSet());
        personaje.inventario = personajeSQL.getInventario()
                .stream()
                .map(Item::new).collect(Collectors.toSet());
        return personaje;
    }

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

}
