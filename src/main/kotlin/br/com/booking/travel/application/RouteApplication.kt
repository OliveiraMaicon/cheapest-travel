package br.com.booking.travel.application

import br.com.booking.travel.domain.model.Graph
import br.com.booking.travel.domain.model.Route
import br.com.booking.travel.domain.service.RouteService
import org.apache.logging.log4j.Logger
import org.springframework.stereotype.Service

@Service
class RouteApplication(private val routeService: RouteService,
                       private val logger: Logger) {

    fun findCheapestRoute(start: String, end: String, directed: Boolean): String {
        val routes = routeService.getRoutes()

        val graph = Graph(routes,directed)

        graph.dijkstra(start)

        val cheapestRoute = graph.tracePath(end)
        logger.info("Cheapest Route = $cheapestRoute")
        return cheapestRoute
    }

    fun addRoutes(routes: List<Route>) {
        routeService.addRoutes(routes)
    }

}