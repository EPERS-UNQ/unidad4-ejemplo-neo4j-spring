package ar.edu.unq.epers.unidad4.service.interfaces;

import ar.edu.unq.epers.unidad4.model.Gremio;

public interface GremioService {
    Gremio guardar(Gremio gremio);
    Gremio recuperar(Long gremioId);
    void ingresarIntegrante(Long personajeId, Long gremioId);
    void clearAll();
}
