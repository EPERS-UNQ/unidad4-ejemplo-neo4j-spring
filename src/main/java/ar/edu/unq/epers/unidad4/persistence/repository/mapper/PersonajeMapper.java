package ar.edu.unq.epers.unidad4.persistence.repository.mapper;

import ar.edu.unq.epers.unidad4.model.Personaje;
import ar.edu.unq.epers.unidad4.persistence.neo.entity.PersonajeNeo4J;
import ar.edu.unq.epers.unidad4.persistence.sql.entity.PersonajeSQL;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PersonajeMapper {

    private ModelMapper modelMapper = new ModelMapper();

    public PersonajeMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Personaje toModel(PersonajeSQL personajeSQL, PersonajeNeo4J personajeNeo4J) {
        Personaje personaje = modelMapper.map(personajeSQL, Personaje.class);
        personaje.setAmigos(personajeNeo4J.getAmigos()
                .stream()
                .filter(amigo -> !amigo.getId().equals(personajeSQL.getId()))
                .map(amigo -> {
                    Personaje amigoPersonaje = new Personaje();
                    amigoPersonaje.setId(amigo.getId());
                    amigoPersonaje.setNombre(amigo.getNombre());
                    return amigoPersonaje;
                })
                .collect(java.util.stream.Collectors.toSet()));
        return personaje;
    }

    public PersonajeSQL toSQL(Personaje personaje) {
        return modelMapper.map(personaje, PersonajeSQL.class);
    }

    public PersonajeNeo4J toNeo4J(Personaje personaje) {
        PersonajeNeo4J personajeNeo4J = new PersonajeNeo4J();
        personajeNeo4J.setId(personaje.getId());
        personajeNeo4J.setNombre(personaje.getNombre());
        personajeNeo4J.setAmigos(personaje.getAmigos().stream()
                .map(amigo -> {
                    PersonajeNeo4J amigoNeo4J = new PersonajeNeo4J();
                    amigoNeo4J.setId(amigo.getId());
                    amigoNeo4J.setNombre(amigo.getNombre());
                    return amigoNeo4J;
                })
                .filter(amigo -> !amigo.getId().equals(personaje.getId()))
                .collect(java.util.stream.Collectors.toSet()));
        return personajeNeo4J;
    }

}
