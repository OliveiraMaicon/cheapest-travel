package br.com.booking.travel.domain.model


data class Vertex(private val name: String) : Comparable<Vertex> {

    var dist = Int.MAX_VALUE
    var previous: Vertex? = null
    val neighbours = HashMap<Vertex, Int>()

    fun getPath() : String{
        return when (previous) {
            this -> name
            null -> "$name(unreached)"
            else -> {
                previous!!.getPath() + " -> $name($dist)"
            }
        }
    }

    override fun compareTo(other: Vertex): Int {
        if (dist == other.dist) return name.compareTo(other.name)
        return dist.compareTo(other.dist)
    }

    override fun toString() : String {
        return if(previous != null){
            "($name, $dist, ${previous?.dist})"
        }else{
            "($name, $dist)"
        }
    }
}