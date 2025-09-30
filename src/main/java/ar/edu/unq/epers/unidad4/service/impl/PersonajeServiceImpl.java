package ar.edu.unq.epers.unidad4.service.impl;

import ar.edu.unq.epers.unidad4.model.Item;
import ar.edu.unq.epers.unidad4.model.Personaje;
import ar.edu.unq.epers.unidad4.persistence.sql.ItemSQLDAO;
import ar.edu.unq.epers.unidad4.persistence.neo.PersonajeNeo4JDAO;
import ar.edu.unq.epers.unidad4.persistence.sql.PersonajeDAOSQL;
import ar.edu.unq.epers.unidad4.exception.EntityNotFoundException;
import ar.edu.unq.epers.unidad4.persistence.sql.entity.ItemSQL;
import ar.edu.unq.epers.unidad4.persistence.neo.entity.PersonajeNeo4J;
import ar.edu.unq.epers.unidad4.persistence.sql.entity.PersonajeSQL;
import ar.edu.unq.epers.unidad4.service.interfaces.PersonajeService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PersonajeServiceImpl implements PersonajeService {

    private final PersonajeNeo4JDAO personajeNeo4JDAO;
    private final ItemSQLDAO itemSQLDAO;
    private final PersonajeDAOSQL personajeDAOSQL;

    public PersonajeServiceImpl(PersonajeNeo4JDAO personajeNeo4JDAO, ItemSQLDAO itemSQLDAO, PersonajeDAOSQL personajeDAOSQL) {
        this.personajeNeo4JDAO = personajeNeo4JDAO;
        this.itemSQLDAO = itemSQLDAO;
        this.personajeDAOSQL = personajeDAOSQL;
    }

    @Override
    public Personaje guardar(Personaje personaje) {
        PersonajeSQL personajeSQL = new PersonajeSQL(personaje);
        personajeDAOSQL.save(personajeSQL);
        personaje.setId(personajeSQL.getId());
        personajeNeo4JDAO.save(new PersonajeNeo4J(personaje));
        return personaje;
    }

    @Override
    public Personaje recuperar(Long personajeId) {
        PersonajeSQL personajeSQL = personajeDAOSQL.findById(personajeId)
                .orElseThrow(() -> new EntityNotFoundException(PersonajeSQL.class.getName(), personajeId));
        PersonajeNeo4J personajeNeo4J = personajeNeo4JDAO.findBySourceId(personajeId)
                .orElseThrow(() -> new EntityNotFoundException(PersonajeSQL.class.getName(), personajeId));
        return Personaje.from(personajeSQL, personajeNeo4J);
    }

    @Override
    public Personaje recuperarPorNombre(String nombre) {
        PersonajeNeo4J personajeNEO4J = personajeNeo4JDAO.findByNombre(nombre)
                .orElseThrow(() -> new EntityNotFoundException(PersonajeSQL.class.getName(), nombre));
        PersonajeSQL personajeSQL = personajeDAOSQL.findById(personajeNEO4J.getSourceId())
                .orElseThrow(() -> new EntityNotFoundException(PersonajeSQL.class.getName(), nombre));
        return Personaje.from(personajeSQL, personajeNEO4J);
    }

    @Override
    public void recoger(Long personajeId, Long itemId) {
        PersonajeSQL personajeSQL = personajeDAOSQL.findById(personajeId)
                .orElseThrow(() -> new EntityNotFoundException(PersonajeSQL.class.getName(), personajeId));
        ItemSQL itemSQL = itemSQLDAO.findById(itemId).get();

        Personaje personaje = new Personaje(personajeSQL);
        Item item = new Item(itemSQL);
        personaje.recoger(item);

        PersonajeSQL personajeSQLActualizado = new PersonajeSQL(personaje);

        personajeDAOSQL.save(personajeSQLActualizado);
    }

    @Override
    public void amigarse(Long personajeId, Long amigoId) {
        PersonajeNeo4J personajeNeo4J = personajeNeo4JDAO.findById(personajeId).get();
        PersonajeNeo4J amigo = personajeNeo4JDAO.findById(amigoId).get();

        personajeNeo4J.amigarse(amigo);

        personajeNeo4JDAO.save(personajeNeo4J);
    }

    @Override
    public Collection<Personaje> recuperarAmigosDeMisAMigos(String nombre) {
        var amigos = personajeNeo4JDAO.amigosDeMisAmigos(nombre);
        return amigos.stream().map(amigoNeo4J -> {
            PersonajeSQL personajeSQL = personajeDAOSQL.findById(amigoNeo4J.getSourceId())
                    .orElseThrow(() -> new EntityNotFoundException(PersonajeSQL.class.getName(), amigoNeo4J.getSourceId()));
            return Personaje.from(personajeSQL, amigoNeo4J);
        }).toList();
    }

    @Override
    public Collection<Personaje> recuperarTodos() {
        var personajesSql = personajeDAOSQL.findAll();
        var personajesNeo4J = personajeNeo4JDAO.findAll();
        return personajesSql.stream().map(personajeSQL -> {
            var personajeNeo4J = personajesNeo4J.stream()
                    .filter(p -> p.getSourceId().equals(personajeSQL.getId()))
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
