package ar.edu.unq.epers.unidad4;

import ar.edu.unq.epers.unidad4.service.interfaces.ItemService;
import ar.edu.unq.epers.unidad4.service.interfaces.PersonajeService;
import ar.edu.unq.epers.unidad4.model.Personaje;
import ar.edu.unq.epers.unidad4.model.Item;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

@SpringBootTest
public class PersonajeServiceTest {

    @Autowired private PersonajeService personajeService;
    @Autowired private ItemService itemService;

    private Personaje personaje1;
    private Personaje personaje2;
    private Personaje personaje3;
    private Item item1;

    @BeforeEach
    void setUp() {
        personaje1 = new Personaje();
        personaje1.setNombre("Gandalf");
        personaje1.setVida(100);
        personaje1.setPesoMaximo(50);

        personaje2 = new Personaje();
        personaje2.setNombre("Frodo");
        personaje2.setVida(80);
        personaje2.setPesoMaximo(30);

        personaje3 = new Personaje();
        personaje3.setNombre("Aragorn");
        personaje3.setVida(120);
        personaje3.setPesoMaximo(60);

        item1 = new Item();
        item1.setNombre("Anillo");
        item1.setPeso(1);
    }

    @Test
    void testGuardarYRecuperarPersonaje() {
        Personaje saved = personajeService.guardar(personaje1);
        assertNotNull(saved.getId());

        Personaje recovered = personajeService.recuperar(saved.getId());
        assertEquals(saved.getNombre(), recovered.getNombre());
        assertEquals(saved.getVida(), recovered.getVida());
        assertEquals(saved.getPesoMaximo(), recovered.getPesoMaximo());
    }

    @Test
    void testRecuperarPorNombre() {
        personajeService.guardar(personaje1);

        Personaje recovered = personajeService.recuperarPorNombre("Gandalf");
        assertNotNull(recovered);
        assertEquals("Gandalf", recovered.getNombre());
    }

    @Test
    void testRecogerItem() {
        Personaje savedPersonaje = personajeService.guardar(personaje1);
        Item savedItem = itemService.guardar(item1);

        personajeService.recoger(savedPersonaje.getId(), savedItem.getId());

        Personaje personajeConItem = personajeService.recuperar(savedPersonaje.getId());
        assertFalse(personajeConItem.getInventario().isEmpty());
        assertEquals(1, personajeConItem.getInventario().size());
    }

    @Test
    void testAmigarse() {
        Personaje savedPersonaje1 = personajeService.guardar(personaje1);
        Personaje savedPersonaje2 = personajeService.guardar(personaje2);

        personajeService.amigarse(savedPersonaje1.getId(), savedPersonaje2.getId());

        Personaje personajeConAmigo = personajeService.recuperar(savedPersonaje1.getId());
        assertFalse(personajeConAmigo.getAmigos().isEmpty());
        assertEquals(1, personajeConAmigo.getAmigos().size());
    }

    @Test
    void testRecuperarAmigosDeMisAmigos() {
        // Crear una cadena de amigos: personaje1 -> personaje2 -> personaje3
        Personaje saved1 = personajeService.guardar(personaje1);
        Personaje saved2 = personajeService.guardar(personaje2);
        Personaje saved3 = personajeService.guardar(personaje3);

        personajeService.amigarse(saved1.getId(), saved2.getId());
        personajeService.amigarse(saved2.getId(), saved3.getId());

        Collection<Personaje> amigosDeAmigos = personajeService.recuperarAmigosDeMisAMigos(saved1.getNombre());
        assertFalse(amigosDeAmigos.isEmpty());
        assertTrue(amigosDeAmigos.stream().anyMatch(p -> p.getNombre().equals(saved3.getNombre())));
    }

    @Test
    void testRecuperarTodos() {
        personajeService.guardar(personaje1);
        personajeService.guardar(personaje2);
        personajeService.guardar(personaje3);

        Collection<Personaje> todos = personajeService.recuperarTodos();
        assertEquals(3, todos.size());
    }

//    @AfterEach
//    void clearAll() {
//        itemService.clearAll();
//        personajeService.clearAll();
//    }
}
