package ar.edu.unq.epers.unidad4.persistence.repositorys.impl;


import ar.edu.unq.epers.unidad4.exception.EntityNotFoundException;
import ar.edu.unq.epers.unidad4.model.Item;
import ar.edu.unq.epers.unidad4.model.Personaje;
import ar.edu.unq.epers.unidad4.model.PersonajeSQL;
import ar.edu.unq.epers.unidad4.persistence.DAOs.ItemDAO;
import ar.edu.unq.epers.unidad4.persistence.DAOs.PersonajeDAO;
import ar.edu.unq.epers.unidad4.persistence.DAOs.PersonajeDAOSQL;
import ar.edu.unq.epers.unidad4.persistence.repositorys.interfaces.PersonajeRepository;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class PersonajeRepositoryImpl implements PersonajeRepository {

    private final PersonajeDAOSQL personajeDAOSQL;
    private final PersonajeDAO personajeDAO;

    public PersonajeRepositoryImpl(PersonajeDAOSQL personajeDAOSQL, PersonajeDAO personajeDAO) {
        this.personajeDAOSQL = personajeDAOSQL;
        this.personajeDAO = personajeDAO;
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
    public PersonajeSQL recuperarSQL(String nombre) {
        return personajeDAOSQL.findByNombre(nombre);
    }

    @Override
    public Personaje recuperarPorNombre(String nombre) {
        return personajeDAO.findByNombre(nombre).orElseThrow(() -> new EntityNotFoundException("personaje", nombre));
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
    public void actualizar(Personaje personaje, PersonajeSQL personajeSQL) {
        personajeDAO.save(personaje);
        personajeDAOSQL.save(personajeSQL);
    }

    @Override
    public void actualizar(Personaje personaje) {
        personajeDAO.save(personaje);
    }

    @Override
    public void clearAll() {
        personajeDAOSQL.deleteAll();
        personajeDAO.detachDelete();
    }
}
