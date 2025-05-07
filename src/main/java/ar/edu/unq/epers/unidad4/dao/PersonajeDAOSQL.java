package ar.edu.unq.epers.unidad4.dao;

import ar.edu.unq.epers.unidad4.model.PersonajeSQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface PersonajeDAOSQL extends JpaRepository<PersonajeSQL, Long>{
    PersonajeSQL findByNombre(String nombre);

    @Query("""
    SELECT DISTINCT amigoDeAmigo
    FROM PersonajeSQL p
    JOIN p.amigos amigo
    JOIN amigo.amigos amigoDeAmigo
    WHERE p.nombre = :nombre
      AND amigoDeAmigo <> p
""")
    Collection<PersonajeSQL> amigosDeMisAmigos(@Param("nombre") String nombre);
}
