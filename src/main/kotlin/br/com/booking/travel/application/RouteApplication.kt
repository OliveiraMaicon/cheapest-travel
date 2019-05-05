package br.com.booking.travel.application

import br.com.booking.travel.domain.model.Route
import br.com.booking.travel.domain.service.RouteService
import org.apache.logging.log4j.Logger
import org.springframework.stereotype.Service

@Service
class RouteApplication(private val routeService: RouteService,
                       private val logger: Logger) {


    fun findBetterRoute(start: String, end: String): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun addRoutes(routes: List<Route>) {

        routeService.addRoutes(routes)
    }


}