package br.com.booking.travel.domain.model

class Route(val start: String, val end: String, val value: Int){

    override fun toString(): String {
        return "Route(start='$start', end='$end', value=$value)"
    }
}