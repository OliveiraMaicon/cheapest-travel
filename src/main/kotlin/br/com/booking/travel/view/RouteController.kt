package br.com.booking.travel.view

import br.com.booking.travel.application.RouteApplication
import br.com.booking.travel.domain.model.Route
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/routes")
class RouteController {

    @Autowired
    lateinit var routeApplication: RouteApplication

    @GetMapping
    fun betterRoute(@RequestParam("start") start : String, @RequestParam("end") end : String ): ResponseEntity<*> {

        if(start.isBlank() || end.isBlank()){
            return ResponseEntity.badRequest().body("Params START and END must have values")
        }

        val betterRoute = routeApplication.findCheapestRoute(start.toUpperCase(), end.toUpperCase())

        return ResponseEntity.ok().body("The cheapest route to travel is :$betterRoute")
    }

    @PutMapping
    fun addRoute(routes: List<Route>): ResponseEntity<*> {
        routeApplication.addRoutes(routes)
        return ResponseEntity.ok().body("Success")
    }

}