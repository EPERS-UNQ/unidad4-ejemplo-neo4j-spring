package ar.edu.unq.epers.unidad4.controller;

import ar.edu.unq.epers.unidad4.persistence.sql.entity.ItemSQL;
import ar.edu.unq.epers.unidad4.service.interfaces.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<ItemSQL> guardar(@RequestBody ItemSQL itemSQL) {
        return ResponseEntity.ok(itemService.guardar(itemSQL));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemSQL> recuperar(@PathVariable("id") Long itemId) {
        return ResponseEntity.ok(itemService.recuperar(itemId));
    }

    @GetMapping("/pesados/{peso}")
    public ResponseEntity<Collection<ItemSQL>> getMasPesados(@PathVariable int peso) {
        return ResponseEntity.ok(itemService.getMasPesados(peso));
    }

    @GetMapping("/personajes-debiles/{vida}")
    public ResponseEntity<Collection<ItemSQL>> getItemsDePersonajesDebiles(@PathVariable int vida) {
        return ResponseEntity.ok(itemService.getItemsDePersonajesDebiles(vida));
    }

    @DeleteMapping
    public ResponseEntity<Void> clearAll() {
        itemService.clearAll();
        return ResponseEntity.ok().build();
    }
}
