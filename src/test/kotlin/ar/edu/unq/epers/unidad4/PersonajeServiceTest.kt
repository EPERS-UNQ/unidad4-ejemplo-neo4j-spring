package ar.edu.unq.epers.unidad4

import ar.edu.unq.epers.unidad4.model.*
import ar.edu.unq.epers.unidad4.service.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class PersonajeServiceTest {

    @Autowired lateinit var personajeService: PersonajeService
    @Autowired lateinit var itemService: ItemService
    @Autowired lateinit var gremioService: GremioService

    lateinit var maguin: Personaje
    lateinit var debilucho: Personaje
    lateinit var baculo: Item
    lateinit var tunica: Item
    lateinit var epersTeam: Gremio

    @BeforeEach
    fun prepare() {
        tunica = Item("Tunica", 100)
        baculo = Item("Baculo", 50)

        maguin = Personaje("Maguin", 10, 70)
        debilucho = Personaje("Debilucho", 1, 1000)

        epersTeam = Gremio("EpersTeam", 9)

        itemService.guardar(tunica)
        itemService.guardar(baculo)
        personajeService.guardar(maguin)
        personajeService.guardar(debilucho)
        gremioService.guardar(epersTeam)
    }

    @Test
    fun testRecuperarPersonajePorNombre(){
        val maguito = personajeService.recuperarPorNombre(maguin.nombre!!)

        Assertions.assertEquals(maguin.id, maguito!!.id)
        Assertions.assertEquals(maguin.vida, maguito.vida)
        Assertions.assertEquals(maguin.pesoMaximo, maguito.pesoMaximo)
    }

    @Test
    fun testRecoger(){
        personajeService.recoger(maguin.id!!, baculo.id!!)
        val maguito = personajeService.recuperar(maguin.id!!)

        Assertions.assertEquals(1, maguito.inventario.size)

        val baculo = maguito.inventario.iterator().next()
        Assertions.assertEquals("Baculo", baculo.nombre)
    }

    @Test
    fun testIngresarAGremio(){
        gremioService.ingresarIntegrante(maguin.id!!, epersTeam.id!!)
        val maguito = personajeService.recuperar(maguin.id!!)

        Assertions.assertEquals(epersTeam.nombre, maguito.integrante!!.gremio!!.nombre)

        val equipoEpers = gremioService.recuperar(epersTeam.id!!)

        Assertions.assertEquals(1, equipoEpers.integrantes)
    }

    @Test
    fun testGetMasPesados() {
        val items = itemService.getMasPesados(10)
        Assertions.assertEquals(2, items.size.toLong())

        val items2 = itemService.getMasPesados(80)
        Assertions.assertEquals(1, items2.size.toLong())
    }

    @Test
    fun testGetItemsPersonajesDebiles() {
        var items = itemService.getItemsPersonajesDebiles(5)
        Assertions.assertEquals(0, items.size.toLong())

        personajeService.recoger(maguin.id!!, baculo.id!!)
        personajeService.recoger(debilucho.id!!, tunica.id!!)

        items = itemService.getItemsPersonajesDebiles(5)
        Assertions.assertEquals(1, items.size.toLong())
        Assertions.assertEquals("Tunica", items.iterator().next().nombre)
    }

    @Test
    fun testAmigarse(){
        personajeService.amigarse(maguin.id!!, debilucho.id!!)
        val maguito = personajeService.recuperar(maguin.id!!)
        val debil = personajeService.recuperar(debilucho.id!!)

        Assertions.assertEquals(0, debil.amigos!!.size)
        Assertions.assertEquals(1, maguito.amigos!!.size)
        Assertions.assertEquals("Debilucho", maguito.amigos!!.iterator().next().nombre)
    }

    @Test
    fun testAmigosDeMisAmigos(){
        val fuertucho = personajeService.guardar(Personaje("Fuertucho", 500, 250))
        personajeService.amigarse(debilucho.id!!, maguin.id!!)
        personajeService.amigarse(maguin.id!!, fuertucho.id!!)

        val amigos = personajeService.amigosDeMisAmigos(debilucho.nombre!!)
        Assertions.assertEquals(1, amigos.size)
        Assertions.assertEquals("Fuertucho", amigos.iterator().next().nombre)
    }

    @AfterEach
    fun tearDown(){
        itemService.clearAll()
        personajeService.clearAll()
        gremioService.clearAll()
    }
}