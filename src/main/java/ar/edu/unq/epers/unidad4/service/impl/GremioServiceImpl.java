package ar.edu.unq.epers.unidad4.service.impl;

import ar.edu.unq.epers.unidad4.dao.GremioDAO;
import ar.edu.unq.epers.unidad4.dao.PersonajeDAO;
import ar.edu.unq.epers.unidad4.exception.EntityNotFoundException;
import ar.edu.unq.epers.unidad4.model.Gremio;
import ar.edu.unq.epers.unidad4.model.Personaje;
import ar.edu.unq.epers.unidad4.service.interfaces.GremioService;
import org.springframework.stereotype.Service;

@Service
public class GremioServiceImpl implements GremioService {

    private final GremioDAO gremioDAO;
    private final PersonajeDAO personajeDAO;

    public GremioServiceImpl(GremioDAO gremioDAO, PersonajeDAO personajeDAO) {
        this.gremioDAO = gremioDAO;
        this.personajeDAO = personajeDAO;
    }

    @Override
    public Gremio guardar(Gremio gremio) {
        return gremioDAO.save(gremio);
    }

    @Override
    public Gremio recuperar(Long gremioId) {
        return gremioDAO.findById(gremioId).orElseThrow(() -> new EntityNotFoundException("gremio", gremioId));
    }

    @Override
    public void ingresarIntegrante(Long personajeId, Long gremioId) {
        Personaje personaje = personajeDAO.findById(personajeId).get();
        Gremio gremio = gremioDAO.findById(gremioId).get();

        if (personaje.getIntegrante() != null)
            personaje.retirarseDeGremio();

        personaje.ingresar(gremio);

        personajeDAO.save(personaje);
    }

    @Override
    public void clearAll() {
        gremioDAO.detachDelete();
    }
}
