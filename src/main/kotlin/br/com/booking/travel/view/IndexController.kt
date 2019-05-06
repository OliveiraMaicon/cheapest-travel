package br.com.booking.travel.view

import org.apache.logging.log4j.Logger
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/")
@Controller
class IndexController(private val logger: Logger) {

    @GetMapping("")
    fun index(): String {
        logger.info("/docs/index.html")
        return "redirect:/docs/index.html"
    }
}