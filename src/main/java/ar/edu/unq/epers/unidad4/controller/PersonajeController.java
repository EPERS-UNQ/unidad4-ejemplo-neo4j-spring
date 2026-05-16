package ar.edu.unq.epers.unidad4.controller;

import ar.edu.unq.epers.unidad4.controller.dto.PersonajeSimpleDTO;
import ar.edu.unq.epers.unidad4.model.Personaje;
import ar.edu.unq.epers.unidad4.service.interfaces.PersonajeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/personajes")
public class PersonajeController {

    private final PersonajeService personajeService;

    public PersonajeController(PersonajeService personajeService) {
        this.personajeService = personajeService;
    }

    @PostMapping
    public ResponseEntity<PersonajeSimpleDTO> guardar(@RequestBody Personaje personaje) {
        return ResponseEntity.ok(PersonajeSimpleDTO.from(personajeService.guardar(personaje)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonajeSimpleDTO> recuperar(@PathVariable("id") Long personajeId) {
        return ResponseEntity.ok(PersonajeSimpleDTO.from(personajeService.recuperar(personajeId)));
    }

    @PostMapping("/{personajeId}/items/{itemId}")
    public ResponseEntity<Void> recoger(@PathVariable Long personajeId, @PathVariable Long itemId) {
        personajeService.recoger(personajeId, itemId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{personajeId}/amigos/{amigoId}")
    public ResponseEntity<Void> amigarse(@PathVariable Long personajeId, @PathVariable Long amigoId) {
        personajeService.amigarse(personajeId, amigoId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/amigos-de-amigos/{nombre}")
    public ResponseEntity<Collection<PersonajeSimpleDTO>> recuperarAmigosDeMisAMigos(@PathVariable String nombre) {
        return ResponseEntity.ok(personajeService.recuperarAmigosDeMisAMigos(nombre).stream()
                .map(PersonajeSimpleDTO::from)
                .toList());
    }

    @GetMapping
    public ResponseEntity<Collection<PersonajeSimpleDTO>> recuperarTodos() {
        return ResponseEntity.ok(personajeService.recuperarTodos().stream()
                .map(PersonajeSimpleDTO::from)
                .toList());
    }

    @DeleteMapping
    public ResponseEntity<Void> clearAll() {
        personajeService.clearAll();
        return ResponseEntity.ok().build();
    }
}
