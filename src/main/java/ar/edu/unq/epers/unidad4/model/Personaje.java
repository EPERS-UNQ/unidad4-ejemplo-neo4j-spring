package ar.edu.unq.epers.unidad4.model;

import lombok.*;
import ar.edu.unq.epers.unidad4.exception.GremioSinEspacioException;
import ar.edu.unq.epers.unidad4.exception.MuchoPesoException;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@ToString
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Node
public class Personaje {

    @Id
    @GeneratedValue
    private Long id;
    private String nombre;
    private int vida;
    private int pesoMaximo;

    @Relationship(type = "POSEE")
    private Set<Item> inventario = new HashSet<>();

    @Relationship(type = "INTEGRANTE")
    private Integrante integrante;

    @Relationship(type = "AMIGO")
    private Set<Personaje> amigos = new HashSet<>();

    public Personaje(String nombre, int vida, int pesoMaximo) {
        this.nombre = nombre;
        this.vida = vida;
        this.pesoMaximo = pesoMaximo;
    }

    public int getPesoActual() {
        return this.inventario.stream().mapToInt(Item::getPeso).sum();
    }

    public void recoger(Item item) {
        int pesoActual = getPesoActual();
        if (pesoActual + item.getPeso() > this.pesoMaximo) {
            throw new MuchoPesoException(this, item);
        }

        this.inventario.add(item);
    }

    public void ingresar(Gremio gremio) {
        if (gremio.getIntegrantes() == gremio.getCapacidad())
            throw new GremioSinEspacioException(gremio);
        this.integrante = new Integrante(gremio);
        gremio.setIntegrantes(gremio.getIntegrantes() + 1);
    }

    public void retirarseDeGremio() {
        if (integrante != null) {
            this.integrante.getGremio().retirarIntegrante();
            this.integrante = null;
        }
    }

    public void amigarse(Personaje personaje) {
        this.amigos.add(personaje);
    }

}
