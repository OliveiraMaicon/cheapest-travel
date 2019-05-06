package br.com.booking.travel.view

import br.com.booking.travel.Application.Companion.PATH_ROUTE
import br.com.booking.travel.application.RouteApplication
import br.com.booking.travel.domain.model.Route
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(PATH_ROUTE)
class RouteController(private val routeApplication: RouteApplication) {


    @GetMapping
    fun betterRoute(@RequestParam("start") start : String, @RequestParam("end") end : String ): ResponseEntity<*> {

        if(start.isBlank() || end.isBlank()){
            return ResponseEntity.badRequest().body("Params START and END must have values")
        }

        val betterRoute = routeApplication.findCheapestRoute(start.toUpperCase(), end.toUpperCase())

        return ResponseEntity.ok().body("The cheapest route to travel is :$betterRoute")
    }

    @PutMapping
    fun addRoute(@RequestBody routes: List<Route>): ResponseEntity<*> {
        routeApplication.addRoutes(routes)
        return ResponseEntity.ok().body("Success")
    }

}