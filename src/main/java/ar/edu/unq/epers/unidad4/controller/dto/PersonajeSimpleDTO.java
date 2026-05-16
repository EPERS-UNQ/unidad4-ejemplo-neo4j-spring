package ar.edu.unq.epers.unidad4.controller.dto;

import ar.edu.unq.epers.unidad4.model.Personaje;
import lombok.Value;

import java.util.Set;
import java.util.stream.Collectors;

@Value
public class PersonajeSimpleDTO {
    Long id;
    String nombre;
    int vida;
    int pesoMaximo;
    Set<ItemDTO> inventario;
    Set<AmigoDTO> amigos;

    public record ItemDTO(Long id, String nombre, int peso) {}
    public record AmigoDTO(Long id, String nombre) {}

    public static PersonajeSimpleDTO from(Personaje personaje) {
        return new PersonajeSimpleDTO(
                personaje.getId(),
                personaje.getNombre(),
                personaje.getVida(),
                personaje.getPesoMaximo(),
                personaje.getInventario().stream()
                        .map(item -> new ItemDTO(item.getId(), item.getNombre(), item.getPeso()))
                        .collect(Collectors.toSet()),
                personaje.getAmigos().stream()
                        .map(a -> new AmigoDTO(a.getId(), a.getNombre()))
                        .collect(Collectors.toSet())
        );
    }
}
