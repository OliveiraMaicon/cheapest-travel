package br.com.booking.travel.domain.model


class Vertex(private val name: String) : Comparable<Vertex> {

    var dist = Int.MAX_VALUE
    var previous: Vertex? = null
    val neighbours = HashMap<Vertex, Int>()

    fun printPath() {
        when (previous) {
            this -> print(name)
            null -> print("$name(unreached)")
            else -> {
                previous!!.printPath()
                print(" -> $name($dist)")
            }
        }
    }

    override fun compareTo(other: Vertex): Int {
        if (dist == other.dist) return name.compareTo(other.name)
        return dist.compareTo(other.dist)
    }

    override fun toString() = "($name, $dist)"
}