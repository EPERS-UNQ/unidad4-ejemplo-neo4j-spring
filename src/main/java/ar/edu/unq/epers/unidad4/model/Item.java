package ar.edu.unq.epers.unidad4.model;


import ar.edu.unq.epers.unidad4.persistence.sql.entity.ItemSQL;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    private Long id;
    private String nombre;
    private int peso;
    private Personaje owner;

    public Item(String nombre, int peso) {
        this.nombre = nombre;
        this.peso = peso;
    }

    public Item(ItemSQL itemSQL) {
        this.nombre = itemSQL.getNombre();
        this.peso = itemSQL.getPeso();
    }
}
