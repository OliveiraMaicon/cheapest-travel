package br.com.booking.travel.domain.service

import br.com.booking.travel.domain.model.Route
import br.com.booking.travel.infraestruture.helper.FileUtils
import org.apache.logging.log4j.Logger
import org.springframework.stereotype.Service

@Service
class RouteService(private val logger: Logger,
private val fileUtils: FileUtils) {
    fun addRoutes(routes: List<Route>) {
        logger.info("new routes to add.")
        fileUtils.writeInCSV(routes)
        logger.info("Routes added successfully!")
    }


}