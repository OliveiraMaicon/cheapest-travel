package br.com.booking.travel.infraestructure.component

import org.apache.logging.log4j.Logger
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.reactive.function.server.*
import java.net.URI

@Component
class WebRouter(private val logger: Logger) {

    @Bean
    fun staticRouter(): RouterFunction<ServerResponse> {
        return RouterFunctions.resources("/**", ClassPathResource("/"))
    }

    @Bean
    fun routes() = RouterFunctions.route(
            RequestPredicates.GET("/"),
            HandlerFunction {
                ServerResponse.permanentRedirect(URI("/docs/index.html")).build()
            }
    )
}