package ar.edu.unq.epers.unidad4.service.interfaces;

import ar.edu.unq.epers.unidad4.model.Personaje;

import java.util.Collection;

public interface PersonajeService {
    Personaje guardar(Personaje personajeNeo4J);
    Personaje recuperar(Long personajeId);
    Personaje recuperarPorNombre(String nombre);
    void recoger(Long personajeId, Long itemId);
    void amigarse(Long personajeId, Long amigoId);
    Collection<Personaje> recuperarAmigosDeMisAMigos(String nombre);
    Collection<Personaje> recuperarTodos();
    void clearAll();
}
