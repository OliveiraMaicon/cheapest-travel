package br.com.booking.travel.view

import br.com.booking.travel.application.RouteApplication
import br.com.booking.travel.domain.model.Route
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.websocket.server.PathParam

@RestController
@RequestMapping("/route")
class RouteController {

    @Autowired
    lateinit var routeApplication: RouteApplication

    @GetMapping
    fun betterRoute(@PathParam("start") start : String, @PathParam("end") end : String ): ResponseEntity<*> {

        if(start.isBlank() || end.isBlank()){
            return ResponseEntity.badRequest().body("Params START and END must have values")
        }

        val betterRoute = routeApplication.findBetterRoute(start, end)

        return ResponseEntity.ok().body("")
    }

    @PutMapping
    fun addRoute(routes: List<Route>): ResponseEntity<*> {

        routeApplication.addRoutes(routes)
        return ResponseEntity.ok().body("")
    }

}