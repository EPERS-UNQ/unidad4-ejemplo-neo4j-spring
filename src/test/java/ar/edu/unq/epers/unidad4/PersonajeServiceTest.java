package ar.edu.unq.epers.unidad4;

import ar.edu.unq.epers.unidad4.persistence.sql.entity.ItemSQL;
import ar.edu.unq.epers.unidad4.persistence.neo.entity.PersonajeNeo4J;
import ar.edu.unq.epers.unidad4.service.interfaces.ItemService;
import ar.edu.unq.epers.unidad4.service.interfaces.PersonajeService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class PersonajeServiceTest {

    @Autowired private PersonajeService personajeService;
    @Autowired private ItemService itemService;

    private PersonajeNeo4J maguin;
    private PersonajeNeo4J debilucho;
    private ItemSQL baculo;
    private ItemSQL tunica;

    @BeforeEach
    public void prepare() {
        tunica = new ItemSQL("Tunica", 100);
        baculo = new ItemSQL("Baculo", 50);

        maguin = new PersonajeNeo4J("Maguin", 10, 70);
        debilucho = new PersonajeNeo4J("Debilucho", 1, 1000);

        itemService.guardar(tunica);
        itemService.guardar(baculo);
        personajeService.guardar(maguin);
        personajeService.guardar(debilucho);
    }

    @Test
    public void testRecuperarPersonajePorNombre(){
        PersonajeNeo4J maguito = personajeService.recuperarPorNombre(maguin.getNombre());

        Assertions.assertEquals(maguin.getId(), maguito.getId());
        Assertions.assertEquals(maguin.getVida(), maguito.getVida());
        Assertions.assertEquals(maguin.getPesoMaximo(), maguito.getPesoMaximo());
    }

    @Test
    public void testRecoger(){
        personajeService.recoger(maguin.getId(), baculo.getId());
        PersonajeNeo4J maguito = personajeService.recuperar(maguin.getId());

        Assertions.assertEquals(1, maguito.getInventario().size());

        ItemSQL baculo = maguito.getInventario().iterator().next();
        Assertions.assertEquals("Baculo", baculo.getNombre());
    }


    @Test
    public void testGetMasPesados() {
        Collection<ItemSQL> itemSQLS = itemService.getMasPesados(10);
        Assertions.assertEquals(2, itemSQLS.size());

        Collection<ItemSQL> items2 = itemService.getMasPesados(80);
        Assertions.assertEquals(1, items2.size());
    }

    @Test
    public void testGetItemsPersonajesDebiles() {
        Collection<ItemSQL> itemSQLS = itemService.getItemsDePersonajesDebiles(5);
        Assertions.assertEquals(0, itemSQLS.size());

        personajeService.recoger(maguin.getId(), baculo.getId());
        personajeService.recoger(debilucho.getId(), tunica.getId());

        itemSQLS = itemService.getItemsDePersonajesDebiles(5);
        Assertions.assertEquals(1, itemSQLS.size());
        Assertions.assertEquals("Tunica", itemSQLS.iterator().next().getNombre());
    }

    @Test
    public void testAmigarse(){
        personajeService.amigarse(maguin.getId(), debilucho.getId());
        PersonajeNeo4J maguito = personajeService.recuperar(maguin.getId());
        PersonajeNeo4J debil = personajeService.recuperar(debilucho.getId());

        Assertions.assertEquals(0, debil.getAmigos().size());
        Assertions.assertEquals(1, maguito.getAmigos().size());
        Assertions.assertEquals("Debilucho", maguito.getAmigos().iterator().next().getNombre());
    }

    @Test
    public void testRecuperarAmigosDeMisAMigos(){
        PersonajeNeo4J fuertucho = personajeService.guardar(new PersonajeNeo4J("Fuertucho", 500, 250));
        personajeService.amigarse(debilucho.getId(), maguin.getId());
        personajeService.amigarse(maguin.getId(), fuertucho.getId());

        Collection<PersonajeNeo4J> amigos = personajeService.recuperarAmigosDeMisAMigos(debilucho.getNombre());
        Assertions.assertEquals(1, amigos.size());
        Assertions.assertEquals("Fuertucho", amigos.iterator().next().getNombre());
    }

    // @Test
    void testGenerarMilDatos() {
        // Este test puede romper tu maquina. Estas advertido.
        Random random = new Random();
        for (int i = 1; i <= 1000; i++) {
            PersonajeNeo4J unMago = new PersonajeNeo4J("NPC-" + i);
            unMago.setPesoMaximo(random.nextInt(200, 300));
            unMago.setVida(random.nextInt(50, 200));
            personajeService.guardar(unMago);
        }

        List<PersonajeNeo4J> personajeNeo4JS = (List<PersonajeNeo4J>) personajeService.recuperarTodos();

        for (PersonajeNeo4J personajeNeo4J : personajeNeo4JS) {

            for (int i = 0; i < 5; i++) {
                PersonajeNeo4J candidato = personajeNeo4JS.get(random.nextInt(personajeNeo4JS.size()));
                if (!candidato.equals(personajeNeo4J)) {
                    personajeService.amigarse(personajeNeo4J.getId(), candidato.getId());
                }
            }


        }
    }

    @AfterEach
    public void tearDown(){
        itemService.clearAll();
        personajeService.clearAll();
    }
}
