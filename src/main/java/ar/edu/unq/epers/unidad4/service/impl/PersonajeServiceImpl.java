package ar.edu.unq.epers.unidad4.service.impl;

import ar.edu.unq.epers.unidad4.dao.ItemDAO;
import ar.edu.unq.epers.unidad4.dao.PersonajeDAO;
import ar.edu.unq.epers.unidad4.exception.EntityNotFoundException;
import ar.edu.unq.epers.unidad4.model.Item;
import ar.edu.unq.epers.unidad4.model.Personaje;
import ar.edu.unq.epers.unidad4.service.interfaces.PersonajeService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PersonajeServiceImpl implements PersonajeService {

    private final PersonajeDAO personajeDAO;
    private final ItemDAO itemDAO;

    public PersonajeServiceImpl(PersonajeDAO personajeDAO, ItemDAO itemDAO) {
        this.personajeDAO = personajeDAO;
        this.itemDAO = itemDAO;
    }

    @Override
    public Personaje guardar(Personaje personaje) {
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

        personajeDAO.save(personaje);
    }

    @Override
    public Collection<Personaje> amigosDeMisAmigos(String nombre) {
        return personajeDAO.amigosDeMisAmigos(nombre);
    }

    @Override
    public void clearAll() {
        personajeDAO.detachDelete();
    }
}
