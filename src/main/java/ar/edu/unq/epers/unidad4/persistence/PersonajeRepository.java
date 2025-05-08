package ar.edu.unq.epers.unidad4.persistence;

import ar.edu.unq.epers.unidad4.model.Personaje;
import ar.edu.unq.epers.unidad4.model.PersonajeSQL;

import java.util.Collection;


public interface PersonajeRepository  {
    Personaje guardar(Personaje personaje);
    Personaje recuperar(Long personajeId);
    Personaje recuperarPorNombre(String nombre);
    void recoger(Long personajeId, Long itemId);
    void amigarse(Long personajeId, Long amigoId);
    Collection<Personaje> amigosDeMisAmigosNeo4J(String nombre);
    Collection<PersonajeSQL> amigosDeMisAmigosSQL(String nombre);
    Collection<Personaje> recuperarTodos();
    void clearAll();
}
