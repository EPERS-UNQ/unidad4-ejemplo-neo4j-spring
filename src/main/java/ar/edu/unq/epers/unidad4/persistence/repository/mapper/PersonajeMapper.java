package ar.edu.unq.epers.unidad4.persistence.repository.mapper;

import ar.edu.unq.epers.unidad4.model.Personaje;
import ar.edu.unq.epers.unidad4.persistence.neo.entity.PersonajeNeo4J;
import ar.edu.unq.epers.unidad4.persistence.sql.entity.PersonajeSQL;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PersonajeMapper {

    private ModelMapper modelMapper;

    public PersonajeMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Personaje toModel(PersonajeSQL personajeSQL, PersonajeNeo4J personajeNeo4J) {
        Personaje personaje = modelMapper.map(personajeSQL, Personaje.class);

        Set<Personaje> amigos = personajeNeo4J.getAmigos()
                .stream()
                .filter(amigo -> !amigo.getId().equals(personajeSQL.getId()))
                .map(amigo -> modelMapper.map(amigo, Personaje.class)).collect(Collectors.toSet());

        personaje.setAmigos(amigos);
        return personaje;
    }

    public PersonajeSQL toSQL(Personaje personaje) {
        return modelMapper.map(personaje, PersonajeSQL.class);
    }

    public PersonajeNeo4J toNeo4J(Personaje personaje) {
        return modelMapper.map(personaje, PersonajeNeo4J.class);
    }
}
