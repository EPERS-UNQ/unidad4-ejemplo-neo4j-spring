package ar.edu.unq.epers.unidad4.model

import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node

@Node
class Gremio() {

    @Id
    @GeneratedValue
    var id: Long? = null
    var nombre: String = ""
    var capacidad: Int = 0
    var integrantes: Int = 0

    constructor(nombre: String, capacidad: Int) : this() {
        this.nombre = nombre
        this.capacidad = capacidad
    }

    fun retirarIntegrante() {
        if (integrantes > 0)
            integrantes -= 1
    }

}