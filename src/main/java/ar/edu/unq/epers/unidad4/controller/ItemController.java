package ar.edu.unq.epers.unidad4.controller;

import ar.edu.unq.epers.unidad4.controller.dto.ItemDTO;
import ar.edu.unq.epers.unidad4.model.Item;
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
    public ResponseEntity<ItemDTO> guardar(@RequestBody Item itemSQL) {
        return ResponseEntity.ok(ItemDTO.from(itemService.guardar(itemSQL)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> recuperar(@PathVariable("id") Long itemId) {
        return ResponseEntity.ok(ItemDTO.from(itemService.recuperar(itemId)));
    }

    @GetMapping("/pesados/{peso}")
    public ResponseEntity<Collection<ItemDTO>> getMasPesados(@PathVariable int peso) {
        return ResponseEntity.ok(itemService.getMasPesados(peso).stream()
                .map(ItemDTO::from)
                .toList());
    }

    @GetMapping("/personajes-debiles/{vida}")
    public ResponseEntity<Collection<ItemDTO>> getItemsDePersonajesDebiles(@PathVariable int vida) {
        return ResponseEntity.ok(itemService.getItemsDePersonajesDebiles(vida).stream()
                .map(ItemDTO::from)
                .toList());
    }

    @DeleteMapping
    public ResponseEntity<Void> clearAll() {
        itemService.clearAll();
        return ResponseEntity.ok().build();
    }
}

