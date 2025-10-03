package ar.edu.unq.epers.unidad4.persistence.repository.implementation;



import ar.edu.unq.epers.unidad4.exception.EntityNotFoundException;
import ar.edu.unq.epers.unidad4.model.Personaje;
import ar.edu.unq.epers.unidad4.persistence.neo.PersonajeNeo4JDAO;
import ar.edu.unq.epers.unidad4.persistence.neo.entity.PersonajeNeo4J;
import ar.edu.unq.epers.unidad4.persistence.repository.PersonajeRepository;
import ar.edu.unq.epers.unidad4.persistence.sql.ItemSQLDAO;
import ar.edu.unq.epers.unidad4.persistence.sql.PersonajeDAOSQL;
import ar.edu.unq.epers.unidad4.persistence.sql.entity.PersonajeSQL;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class PersonajeRepositoryImpl implements PersonajeRepository {

    private final PersonajeDAOSQL personajeDAOSQL;
    private final PersonajeNeo4JDAO personajeNeo4JDAO;

    public PersonajeRepositoryImpl(PersonajeDAOSQL personajeDAOSQL, PersonajeNeo4JDAO personajeNeo4JDAO) {
        this.personajeDAOSQL = personajeDAOSQL;
        this.personajeNeo4JDAO = personajeNeo4JDAO;
    }

    @Override
    public Personaje guardar(Personaje personaje) {
        PersonajeSQL personajeSQL = new PersonajeSQL(personaje);
        personajeDAOSQL.save(personajeSQL);
        personaje.setId(personajeSQL.getId());
        personajeNeo4JDAO.save(new PersonajeNeo4J(personaje));
        return personaje;
    }

    private Collection<PersonajeNeo4J> findAmigosNeo4J(Personaje personaje) {
        return personaje.getAmigos().stream()
                .map(amigo -> personajeNeo4JDAO.findByNombre(amigo.getNombre())
                        .orElseThrow(() -> new EntityNotFoundException(PersonajeSQL.class.getName(), amigo.getId())))
                .toList();
    }

    @Override
    public Personaje recuperar(Long personajeId) {
        PersonajeSQL personajeSQL = personajeDAOSQL.findById(personajeId)
                .orElseThrow(() -> new EntityNotFoundException(PersonajeSQL.class.getName(), personajeId));
        PersonajeNeo4J personajeNeo4J = personajeNeo4JDAO.findById(personajeId)
                .orElseThrow(() -> new EntityNotFoundException(PersonajeSQL.class.getName(), personajeId));
        return Personaje.from(personajeSQL, personajeNeo4J);
    }

    @Override
    public Personaje recuperarPorNombre(String nombre) {
        PersonajeNeo4J personajeNEO4J = personajeNeo4JDAO.findByNombre(nombre)
                .orElseThrow(() -> new EntityNotFoundException(PersonajeSQL.class.getName(), nombre));
        PersonajeSQL personajeSQL = personajeDAOSQL.findById(personajeNEO4J.getId())
                .orElseThrow(() -> new EntityNotFoundException(PersonajeSQL.class.getName(), nombre));
        return Personaje.from(personajeSQL, personajeNEO4J);
    }

    @Override
    public Collection<Personaje> recuperarAmigosDeMisAMigos(String nombre) {
        var amigos = personajeNeo4JDAO.amigosDeMisAmigos(nombre);
        return amigos.stream().map(amigoNeo4J -> {
            PersonajeSQL personajeSQL = personajeDAOSQL.findById(amigoNeo4J.getId())
                    .orElseThrow(() -> new EntityNotFoundException(PersonajeSQL.class.getName(), amigoNeo4J.getId()));
            return Personaje.from(personajeSQL, amigoNeo4J);
        }).toList();
    }

    @Override
    public Collection<Personaje> recuperarTodos() {
        var personajesSql = personajeDAOSQL.findAll();
        var personajesNeo4J = personajeNeo4JDAO.findAll();
        return personajesSql.stream().map(personajeSQL -> {
            var personajeNeo4J = personajesNeo4J.stream()
                    .filter(p -> p.getId().equals(personajeSQL.getId()))
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException(PersonajeSQL.class.getName(), personajeSQL.getId()));
            return Personaje.from(personajeSQL, personajeNeo4J);
        }).toList();
    }


    @Override
    public void clearAll() {
        personajeDAOSQL.deleteAll();
        personajeNeo4JDAO.detachDelete();
    }
}
