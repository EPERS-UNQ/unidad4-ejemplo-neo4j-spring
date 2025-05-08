package ar.edu.unq.epers.unidad4.persistence;


import ar.edu.unq.epers.unidad4.exception.EntityNotFoundException;
import ar.edu.unq.epers.unidad4.model.Item;
import ar.edu.unq.epers.unidad4.model.Personaje;
import ar.edu.unq.epers.unidad4.model.PersonajeSQL;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class PersonajeRepositoryImpl implements PersonajeRepository {

    private final PersonajeDAOSQL personajeDAOSQL;
    private final PersonajeDAO personajeDAO;
    private final ItemDAO itemDAO;

    public PersonajeRepositoryImpl(PersonajeDAOSQL personajeDAOSQL, PersonajeDAO personajeDAO, ItemDAO itemDAO) {
        this.personajeDAOSQL = personajeDAOSQL;
        this.personajeDAO = personajeDAO;
        this.itemDAO = itemDAO;
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
