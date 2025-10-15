package ar.edu.unq.epers.unidad4.persistence.repository.implementation;


import ar.edu.unq.epers.unidad4.exception.EntityNotFoundException;
import ar.edu.unq.epers.unidad4.model.Personaje;
import ar.edu.unq.epers.unidad4.persistence.neo.PersonajeNeo4JDAO;
import ar.edu.unq.epers.unidad4.persistence.neo.entity.PersonajeNeo4J;
import ar.edu.unq.epers.unidad4.persistence.repository.PersonajeRepository;
import ar.edu.unq.epers.unidad4.persistence.repository.mapper.PersonajeMapper;
import ar.edu.unq.epers.unidad4.persistence.sql.PersonajeDAOSQL;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class PersonajeRepositoryImpl implements PersonajeRepository {

    private final PersonajeDAOSQL personajeDAOSQL;
    private final PersonajeNeo4JDAO personajeNeo4JDAO;
    private final PersonajeMapper personajeMapper;

    public PersonajeRepositoryImpl(PersonajeDAOSQL personajeDAOSQL, PersonajeNeo4JDAO personajeNeo4JDAO, PersonajeMapper personajeMapper) {
        this.personajeDAOSQL = personajeDAOSQL;
        this.personajeNeo4JDAO = personajeNeo4JDAO;
        this.personajeMapper = personajeMapper;
    }

    @Override
    public Personaje guardar(Personaje personaje) {
        Personaje personajeGuardado = personajeDAOSQL.save(personaje);
        PersonajeNeo4J personajeNeo4J = personajeMapper.toNeo4J(personajeGuardado);
        personajeNeo4JDAO.save(personajeNeo4J);
        return personajeGuardado;
    }

    @Override
    public Personaje recuperar(Long personajeId) {
        Personaje personaje = personajeDAOSQL.findById(personajeId)
                .orElseThrow(() -> new EntityNotFoundException(Personaje.class.getName(), personajeId));
        PersonajeNeo4J personajeNeo4J = personajeNeo4JDAO.findById(personajeId)
                .orElseThrow(() -> new EntityNotFoundException(Personaje.class.getName(), personajeId));
        return personajeMapper.toModel(personaje, personajeNeo4J);
    }

    @Override
    public Personaje recuperarPorNombre(String nombre) {
        PersonajeNeo4J personajeNEO4J = personajeNeo4JDAO.findByNombre(nombre)
                .orElseThrow(() -> new EntityNotFoundException(Personaje.class.getName(), nombre));
        Personaje personaje = personajeDAOSQL.findById(personajeNEO4J.getId())
                .orElseThrow(() -> new EntityNotFoundException(Personaje.class.getName(), nombre));
        return personajeMapper.toModel(personaje, personajeNEO4J);
    }

    @Override
    public Collection<Personaje> recuperarAmigosDeMisAMigos(String nombre) {
        var amigos = personajeNeo4JDAO.amigosDeMisAmigos(nombre);
        return amigos.stream().map(amigoNeo4J -> {
            Personaje personaje = personajeDAOSQL.findById(amigoNeo4J.getId())
                    .orElseThrow(() -> new EntityNotFoundException(Personaje.class.getName(), amigoNeo4J.getId()));
            return personajeMapper.toModel(personaje, amigoNeo4J);
        }).toList();
    }

    @Override
    public Collection<Personaje> recuperarTodos() {
        var personajes = personajeDAOSQL.findAll();
        var personajesNeo4J = personajeNeo4JDAO.findAll();
        return personajes.stream().map(personaje -> {
            var personajeNeo4J = personajesNeo4J.stream()
                    .filter(p -> p.getId().equals(personaje.getId()))
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException(Personaje.class.getName(), personaje.getId()));
            return personajeMapper.toModel(personaje, personajeNeo4J);
        }).toList();
    }


    @Override
    public void clearAll() {
        personajeDAOSQL.deleteAll();
        personajeNeo4JDAO.detachDelete();
    }
}
