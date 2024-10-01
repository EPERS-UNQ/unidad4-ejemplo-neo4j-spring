package ar.edu.unq.epers.unidad4;

import ar.edu.unq.epers.unidad4.model.Gremio;
import ar.edu.unq.epers.unidad4.model.Item;
import ar.edu.unq.epers.unidad4.model.Personaje;
import ar.edu.unq.epers.unidad4.service.interfaces.GremioService;
import ar.edu.unq.epers.unidad4.service.interfaces.ItemService;
import ar.edu.unq.epers.unidad4.service.interfaces.PersonajeService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

@SpringBootTest
public class PersonajeServiceTest {

    @Autowired private PersonajeService personajeService;
    @Autowired private ItemService itemService;
    @Autowired private GremioService gremioService;

    private Personaje maguin;
    private Personaje debilucho;
    private Item baculo;
    private Item tunica;
    private Gremio epersTeam;

    @BeforeEach
    public void prepare() {
        tunica = new Item("Tunica", 100);
        baculo = new Item("Baculo", 50);

        maguin = new Personaje("Maguin", 10, 70);
        debilucho = new Personaje("Debilucho", 1, 1000);

        epersTeam = new Gremio("EpersTeam", 9);

        itemService.guardar(tunica);
        itemService.guardar(baculo);
        personajeService.guardar(maguin);
        personajeService.guardar(debilucho);
        gremioService.guardar(epersTeam);
    }

    @Test
    public void testRecuperarPersonajePorNombre(){
        Personaje maguito = personajeService.recuperarPorNombre(maguin.getNombre());

        Assertions.assertEquals(maguin.getId(), maguito.getId());
        Assertions.assertEquals(maguin.getVida(), maguito.getVida());
        Assertions.assertEquals(maguin.getPesoMaximo(), maguito.getPesoMaximo());
    }

    @Test
    public void testRecoger(){
        personajeService.recoger(maguin.getId(), baculo.getId());
        Personaje maguito = personajeService.recuperar(maguin.getId());

        Assertions.assertEquals(1, maguito.getInventario().size());

        Item baculo = maguito.getInventario().iterator().next();
        Assertions.assertEquals("Baculo", baculo.getNombre());
    }

    @Test
    public void testIngresarAGremio(){
        gremioService.ingresarIntegrante(maguin.getId(), epersTeam.getId());
        Personaje maguito = personajeService.recuperar(maguin.getId());

        Assertions.assertEquals(epersTeam.getNombre(), maguito.getIntegrante().getGremio().getNombre());

        Gremio equipoEpers = gremioService.recuperar(epersTeam.getId());

        Assertions.assertEquals(1, equipoEpers.getIntegrantes());
    }

    @Test
    public void testGetMasPesados() {
        Collection<Item> items = itemService.getMasPesados(10);
        Assertions.assertEquals(2, items.size());

        Collection<Item> items2 = itemService.getMasPesados(80);
        Assertions.assertEquals(1, items2.size());
    }

    @Test
    public void testGetItemsPersonajesDebiles() {
        Collection<Item> items = itemService.getItemsPersonajesDebiles(5);
        Assertions.assertEquals(0, items.size());

        personajeService.recoger(maguin.getId(), baculo.getId());
        personajeService.recoger(debilucho.getId(), tunica.getId());

        items = itemService.getItemsPersonajesDebiles(5);
        Assertions.assertEquals(1, items.size());
        Assertions.assertEquals("Tunica", items.iterator().next().getNombre());
    }

    @Test
    public void testAmigarse(){
        personajeService.amigarse(maguin.getId(), debilucho.getId());
        Personaje maguito = personajeService.recuperar(maguin.getId());
        Personaje debil = personajeService.recuperar(debilucho.getId());

        Assertions.assertEquals(0, debil.getAmigos().size());
        Assertions.assertEquals(1, maguito.getAmigos().size());
        Assertions.assertEquals("Debilucho", maguito.getAmigos().iterator().next().getNombre());
    }

    @Test
    public void testAmigosDeMisAmigos(){
        Personaje fuertucho = personajeService.guardar(new Personaje("Fuertucho", 500, 250));
        personajeService.amigarse(debilucho.getId(), maguin.getId());
        personajeService.amigarse(maguin.getId(), fuertucho.getId());

        Collection<Personaje> amigos = personajeService.amigosDeMisAmigos(debilucho.getNombre());
        Assertions.assertEquals(1, amigos.size());
        Assertions.assertEquals("Fuertucho", amigos.iterator().next().getNombre());
    }

    @AfterEach
    public void tearDown(){
        itemService.clearAll();
        personajeService.clearAll();
        gremioService.clearAll();
    }
}
