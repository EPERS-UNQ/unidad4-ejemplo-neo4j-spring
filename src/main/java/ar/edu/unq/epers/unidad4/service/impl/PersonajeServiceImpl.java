package ar.edu.unq.epers.unidad4.service.impl;

import ar.edu.unq.epers.unidad4.persistence.ItemDAO;
import ar.edu.unq.epers.unidad4.persistence.PersonajeDAO;
import ar.edu.unq.epers.unidad4.persistence.PersonajeDAOSQL;
import ar.edu.unq.epers.unidad4.exception.EntityNotFoundException;
import ar.edu.unq.epers.unidad4.model.Item;
import ar.edu.unq.epers.unidad4.model.Personaje;
import ar.edu.unq.epers.unidad4.model.PersonajeSQL;
import ar.edu.unq.epers.unidad4.persistence.PersonajeRepository;
import ar.edu.unq.epers.unidad4.service.interfaces.PersonajeService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PersonajeServiceImpl implements PersonajeService {

    private final PersonajeRepository personajeRepository;


    public PersonajeServiceImpl(PersonajeRepository personajeRepository) {
        this.personajeRepository = personajeRepository;
    }

    @Override
    public Personaje guardar(Personaje personaje) {
        return personajeRepository.guardar(personaje);
    }

    @Override
    public Personaje recuperar(Long personajeId) {
        return personajeRepository.recuperar(personajeId);
    }

    @Override
    public Personaje recuperarPorNombre(String nombre) {
        return personajeRepository.recuperarPorNombre(nombre);
    }

    @Override
    public void recoger(Long personajeId, Long itemId) {
        personajeRepository.recoger(personajeId, itemId);
    }

    @Override
    public void amigarse(Long personajeId, Long amigoId) {
        personajeRepository.amigarse(personajeId, amigoId);
    }

    @Override
    public Collection<Personaje> amigosDeMisAmigosNeo4J(String nombre) {
        return personajeRepository.amigosDeMisAmigosNeo4J(nombre);
    }

    @Override
    public Collection<PersonajeSQL> amigosDeMisAmigosSQL(String nombre) {
        return personajeRepository.amigosDeMisAmigosSQL(nombre);
    }

    @Override
    public Collection<Personaje> recuperarTodos() {
        return personajeRepository.recuperarTodos();
    }

    @Override
    public void clearAll() {
        personajeRepository.clearAll();
    }
}
