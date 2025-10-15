package ar.edu.unq.epers.unidad4.persistence.repository.mapper;

import ar.edu.unq.epers.unidad4.model.Personaje;
import ar.edu.unq.epers.unidad4.persistence.neo.entity.PersonajeNeo4J;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PersonajeMapper {

    public Personaje toModel(Personaje personaje, PersonajeNeo4J personajeNeo4J) {
        Set<Personaje> amigos = personajeNeo4J.getAmigos()
                .stream()
                .filter(amigo -> !amigo.getId().equals(personaje.getId()))
                .map(this::neo4JToPersonajeSimple)
                .collect(Collectors.toSet());

        personaje.setAmigos(amigos);
        return personaje;
    }

    public PersonajeNeo4J toNeo4J(Personaje personaje) {
        PersonajeNeo4J personajeNeo4J = new PersonajeNeo4J();
        personajeNeo4J.setId(personaje.getId());
        personajeNeo4J.setNombre(personaje.getNombre());
        personajeNeo4J.setAmigos(amigosToNeo4J(personaje));
        return personajeNeo4J;
    }

    private static Set<PersonajeNeo4J> amigosToNeo4J(Personaje personaje) {
        return personaje.getAmigos().stream()
                .map(amigo -> {
                    PersonajeNeo4J amigoNeo4J = new PersonajeNeo4J();
                    amigoNeo4J.setId(amigo.getId());
                    amigoNeo4J.setNombre(amigo.getNombre());
                    return amigoNeo4J;
                })
                .filter(amigo -> !amigo.getId().equals(personaje.getId()))
                .collect(Collectors.toSet());
    }

    private Personaje neo4JToPersonajeSimple(PersonajeNeo4J personajeNeo4J) {
        Personaje personaje = new Personaje();
        personaje.setId(personajeNeo4J.getId());
        personaje.setNombre(personajeNeo4J.getNombre());
        return personaje;
    }
}
