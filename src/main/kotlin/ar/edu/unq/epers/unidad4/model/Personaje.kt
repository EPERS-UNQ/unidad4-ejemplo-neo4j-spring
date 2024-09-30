package ar.edu.unq.epers.unidad4.model

import ar.edu.unq.epers.unidad4.exception.GremioSinEspacioException
import ar.edu.unq.epers.unidad4.exception.MuchoPesoException
import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship

@Node
class Personaje() {

    @Id
    @GeneratedValue
    var id: Long? = null
    var nombre: String = ""
    var vida: Int = 0
    var pesoMaximo: Int = 0

    @Relationship(type = "POSEE")
    var inventario: MutableSet<Item> = HashSet()

    @Relationship(type = "INTEGRANTE")
    var integrante: Integrante? = null

    @Relationship(type = "AMIGO")
    var amigos: MutableSet<Personaje>? = HashSet()

    constructor(nombre: String, vida: Int, pesoMaximo: Int) : this() {
        this.nombre = nombre
        this.vida = vida
        this.pesoMaximo = pesoMaximo
    }

    val pesoActual: Int
        get() = inventario.sumOf { it.peso }

    fun recoger(item: Item) {
        val pesoActual = this.pesoActual
        if (pesoActual + item.peso > this.pesoMaximo) {
            throw MuchoPesoException(this, item)
        }

        this.inventario.add(item)
        item.owner = this
    }

    fun ingresar(gremio: Gremio) {
        if (gremio.integrantes == gremio.capacidad)
            throw GremioSinEspacioException(gremio)
        this.integrante = Integrante(gremio)
        gremio.integrantes += 1
    }

    fun retirarseDeGremio() {
        if (integrante != null) {
            integrante!!.gremio?.retirarIntegrante()
            integrante = null
        }

    }

    fun amigarse(personaje: Personaje) {
        amigos?.add(personaje)
    }

}