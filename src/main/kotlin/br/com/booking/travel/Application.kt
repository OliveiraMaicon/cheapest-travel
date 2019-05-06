package br.com.booking.travel

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.InjectionPoint
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Scope
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@SpringBootApplication
class Application : WebMvcConfigurer {


    companion object{

        const val PATH_ROUTE = "/routes"
        private val CLASSPATH_RESOURCE_LOCATIONS = arrayOf("classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/", "classpath:/public/")

        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<Application>(*args)
        }
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/**")
                .addResourceLocations(*CLASSPATH_RESOURCE_LOCATIONS)
    }

    @Bean
    @Scope("prototype")
    fun logger(injectionPoint: InjectionPoint): Logger = LogManager.getLogger(injectionPoint.methodParameter?.containingClass)
}
