package ar.edu.unq.epers.unidad4.service.impl;

import ar.edu.unq.epers.unidad4.dao.ItemDAO;
import ar.edu.unq.epers.unidad4.dao.PersonajeDAO;
import ar.edu.unq.epers.unidad4.dao.PersonajeDAOSQL;
import ar.edu.unq.epers.unidad4.exception.EntityNotFoundException;
import ar.edu.unq.epers.unidad4.model.Item;
import ar.edu.unq.epers.unidad4.model.Personaje;
import ar.edu.unq.epers.unidad4.model.PersonajeSQL;
import ar.edu.unq.epers.unidad4.service.interfaces.PersonajeService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Random;

@Service
public class PersonajeServiceImpl implements PersonajeService {

    private final PersonajeDAO personajeDAO;
    private final ItemDAO itemDAO;
    private final PersonajeDAOSQL personajeDAOSQL;

    public PersonajeServiceImpl(PersonajeDAO personajeDAO, ItemDAO itemDAO, PersonajeDAOSQL personajeDAOSQL) {
        this.personajeDAO = personajeDAO;
        this.itemDAO = itemDAO;
        this.personajeDAOSQL = personajeDAOSQL;
    }

    @Override
    public Personaje guardar(Personaje personaje) {
        PersonajeSQL personajeSQL = new PersonajeSQL(personaje.getNombre());
        personajeDAOSQL.save(personajeSQL);
        return personajeDAO.save(personaje);
    }

    @Override
    public Personaje recuperar(Long personajeId) {
        return personajeDAO.findById(personajeId).orElseThrow(() -> new EntityNotFoundException("personaje", personajeId));
    }

    @Override
    public Personaje recuperarPorNombre(String nombre) {
        return personajeDAO.findByNombre(nombre).orElseThrow(() -> new EntityNotFoundException("personaje", nombre));
    }

    @Override
    public void recoger(Long personajeId, Long itemId) {
        Personaje personaje = personajeDAO.findById(personajeId).get();
        Item item = itemDAO.findById(itemId).get();

        personaje.recoger(item);

        personajeDAO.save(personaje);
    }

    @Override
    public void amigarse(Long personajeId, Long amigoId) {
        Personaje personaje = personajeDAO.findById(personajeId).get();
        Personaje amigo = personajeDAO.findById(amigoId).get();

        personaje.amigarse(amigo);

        PersonajeSQL personajeSQL = personajeDAOSQL.findByNombre(personaje.getNombre());
        PersonajeSQL personajeSQLAmigo = personajeDAOSQL.findByNombre(amigo.getNombre());

        personajeSQL.amigarse(personajeSQLAmigo);

        personajeDAO.save(personaje);
        personajeDAOSQL.save(personajeSQL);
    }

    @Override
    public Collection<Personaje> amigosDeMisAmigosNeo4J(String nombre) {
        return personajeDAO.amigosDeMisAmigos(nombre);
    }

    @Override
    public Collection<PersonajeSQL> amigosDeMisAmigosSQL(String nombre) {
        return personajeDAOSQL.amigosDeMisAmigos(nombre);
    }

    @Override
    public Collection<Personaje> recuperarTodos() {
        return personajeDAO.findAll();
    }


    @Override
    public void clearAll() {
        personajeDAOSQL.deleteAll();
        personajeDAO.detachDelete();
    }
}
