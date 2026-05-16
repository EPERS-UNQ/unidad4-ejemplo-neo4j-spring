package ar.edu.unq.epers.unidad4.controller.dto;

import ar.edu.unq.epers.unidad4.model.Item;
import ar.edu.unq.epers.unidad4.model.Personaje;
import lombok.Value;

@Value
public class ItemDTO {
    Long id;
    String nombre;
    int peso;
    OwnerDTO owner;

    public record OwnerDTO(Long id, String nombre, int vida, int pesoMaximo) {
        public static OwnerDTO from(Personaje personaje) {
            return new OwnerDTO(personaje.getId(), personaje.getNombre(), personaje.getVida(), personaje.getPesoMaximo());
        }
    }

    public static ItemDTO from(Item item) {
        return new ItemDTO(
                item.getId(),
                item.getNombre(),
                item.getPeso(),
                item.getOwner() != null ? OwnerDTO.from(item.getOwner()) : null
        );
    }
}
