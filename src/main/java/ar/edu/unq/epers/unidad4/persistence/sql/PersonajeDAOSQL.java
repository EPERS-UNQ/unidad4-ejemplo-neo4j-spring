package ar.edu.unq.epers.unidad4.persistence.sql;

import ar.edu.unq.epers.unidad4.model.Personaje;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonajeDAOSQL extends JpaRepository<Personaje, Long> { }
