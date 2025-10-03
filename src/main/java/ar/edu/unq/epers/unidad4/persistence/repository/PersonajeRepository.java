package ar.edu.unq.epers.unidad4.persistence.repository;

import ar.edu.unq.epers.unidad4.model.Personaje;

import java.util.Collection;

public interface PersonajeRepository {
    Personaje guardar(Personaje personaej);
    Personaje recuperar(Long personajeId);
    Personaje recuperarPorNombre(String nombre);
    Collection<Personaje> recuperarAmigosDeMisAMigos(String nombre);
    Collection<Personaje> recuperarTodos();
    void clearAll();
}
