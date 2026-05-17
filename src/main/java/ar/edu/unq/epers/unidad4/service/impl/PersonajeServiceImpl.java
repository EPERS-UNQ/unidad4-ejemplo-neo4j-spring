package ar.edu.unq.epers.unidad4.service.impl;

import ar.edu.unq.epers.unidad4.model.Item;
import ar.edu.unq.epers.unidad4.model.Personaje;
import ar.edu.unq.epers.unidad4.persistence.neo.PersonajeNeo4JDAO;
import ar.edu.unq.epers.unidad4.persistence.neo.entity.PersonajeNeo4J;
import ar.edu.unq.epers.unidad4.persistence.sql.ItemDAO;
import ar.edu.unq.epers.unidad4.persistence.sql.PersonajeDAOSQL;
import ar.edu.unq.epers.unidad4.service.interfaces.PersonajeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Transactional
public class PersonajeServiceImpl implements PersonajeService {

    private final PersonajeDAOSQL personajeDAOSQL;
    private final PersonajeNeo4JDAO personajeNeo4JDAO;
    private final ItemDAO itemDAO;

    public PersonajeServiceImpl(PersonajeDAOSQL personajeDAOSQL, PersonajeNeo4JDAO personajeNeo4JDAO, ItemDAO itemDAO) {
        this.personajeDAOSQL = personajeDAOSQL;
        this.personajeNeo4JDAO = personajeNeo4JDAO;
        this.itemDAO = itemDAO;
    }

    @Override
    public Personaje guardar(Personaje personaje) {
        personajeDAOSQL.save(personaje);
        personajeNeo4JDAO.save(new PersonajeNeo4J(personaje));
        return personaje;
    }

    @Override
    public Personaje recuperar(Long personajeId) {
        Personaje personaje = personajeDAOSQL.findById(personajeId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro un personaje con id " + personajeId));
        personajeNeo4JDAO.findById(personajeId)
                .ifPresent(neo -> neo.proyectarAmigos(personaje));
        return personaje;
    }

    @Override
    public void recoger(Long personajeId, Long itemId) {
        Personaje personaje = personajeDAOSQL.findById(personajeId).orElseThrow(() -> new EntityNotFoundException("No se encontro un personaje con id " + personajeId));
        Item item = itemDAO.findById(itemId).orElseThrow(() -> new EntityNotFoundException("No se encontro un item con id " + itemId));
        personaje.recoger(item);
        personajeDAOSQL.save(personaje);
        itemDAO.save(item);
    }

    @Override
    public void amigarse(Long personajeId, Long amigoId) {
        Personaje personaje = personajeDAOSQL.findById(personajeId).orElseThrow(() -> new EntityNotFoundException("No se encontro un personaje con id " + personajeId));
        Personaje amigo = personajeDAOSQL.findById(amigoId).orElseThrow(() -> new EntityNotFoundException("No se encontro un personaje con id " + amigoId));
        personaje.amigarse(amigo);
        personajeDAOSQL.save(personaje);
        personajeDAOSQL.save(amigo);
        personajeNeo4JDAO.save(new PersonajeNeo4J(personaje));
    }

    @Override
    public Collection<Personaje> recuperarAmigosDeMisAMigos(String nombre) {
        var ids = personajeNeo4JDAO.amigosDeMisAmigos(nombre).stream()
                .map(PersonajeNeo4J::getId)
                .toList();
        return personajeDAOSQL.findAllById(ids);
    }

    @Override
    public Collection<Personaje> recuperarTodos() {
        var personajesSql = personajeDAOSQL.findAll();
        var personajesNeo4J = personajeNeo4JDAO.findAll();
        return personajesSql.stream().map(sql -> {
            var neo = personajesNeo4J.stream()
                    .filter(p -> p.getId().equals(sql.getId()))
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException("No se encontro un personaje con id " + sql.getId()));
            neo.proyectarAmigos(sql);
            return sql;
        }).toList();
    }


    @Override
    public void clearAll() {
        personajeDAOSQL.deleteAll();
        personajeNeo4JDAO.detachDelete();
    }
}
