package ar.edu.unq.epers.unidad4.service.impl;

import ar.edu.unq.epers.unidad4.model.Item;
import ar.edu.unq.epers.unidad4.model.Personaje;
import ar.edu.unq.epers.unidad4.persistence.repository.PersonajeRepository;
import ar.edu.unq.epers.unidad4.persistence.sql.ItemSQLDAO;
import ar.edu.unq.epers.unidad4.service.interfaces.PersonajeService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Transactional
public class PersonajeServiceImpl implements PersonajeService {

    private final PersonajeRepository personajeRepository;
    private final ItemSQLDAO itemSQLDAO;

    public PersonajeServiceImpl(PersonajeRepository personajeRepository, ItemSQLDAO itemSQLDAO) {
        this.itemSQLDAO = itemSQLDAO;
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
        Personaje personaje = personajeRepository.recuperar(personajeId);
        Item item = itemSQLDAO.getById(itemId);
        personaje.recoger(item);
        personajeRepository.guardar(personaje);
        itemSQLDAO.save(item);
    }

    @Override
    public void amigarse(Long personajeId, Long amigoId) {
        Personaje personaje = personajeRepository.recuperar(personajeId);
        Personaje amigo = personajeRepository.recuperar(amigoId);

        personaje.amigarse(amigo);

        personajeRepository.guardar(personaje);
        personajeRepository.guardar(amigo);
    }

    @Override
    public Collection<Personaje> recuperarAmigosDeMisAMigos(String nombre) {
        return personajeRepository.recuperarAmigosDeMisAMigos(nombre);
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
