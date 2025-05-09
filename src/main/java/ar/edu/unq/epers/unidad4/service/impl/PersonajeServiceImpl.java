package ar.edu.unq.epers.unidad4.service.impl;

import ar.edu.unq.epers.unidad4.model.Item;
import ar.edu.unq.epers.unidad4.model.Personaje;
import ar.edu.unq.epers.unidad4.model.PersonajeSQL;
import ar.edu.unq.epers.unidad4.persistence.repositorys.interfaces.ItemRepository;
import ar.edu.unq.epers.unidad4.persistence.repositorys.interfaces.PersonajeRepository;
import ar.edu.unq.epers.unidad4.service.interfaces.PersonajeService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PersonajeServiceImpl implements PersonajeService {

    private final PersonajeRepository personajeRepository;
    private final ItemRepository itemRepository;


    public PersonajeServiceImpl(PersonajeRepository personajeRepository, ItemRepository itemRepository) {
        this.personajeRepository = personajeRepository;
        this.itemRepository = itemRepository;
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
        Personaje personaje = personajeRepository.recuperar(personajeId);
        Item item = itemRepository.recuperar(itemId);

        personaje.recoger(item);

        personajeRepository.actualizar(personaje);
    }

    @Override
    public void amigarse(Long personajeId, Long amigoId) {
        Personaje personaje = personajeRepository.recuperar(personajeId);
        PersonajeSQL personajeSQL = personajeRepository.recuperarSQL(personaje.getNombre());
        Personaje amigo = personajeRepository.recuperar(amigoId);
        PersonajeSQL amigoSQL = personajeRepository.recuperarSQL(amigo.getNombre());

        personaje.amigarse(amigo);
        personajeSQL.amigarse(amigoSQL);

        personajeRepository.actualizar(personaje, personajeSQL);
        personajeRepository.actualizar(amigo, amigoSQL);
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
