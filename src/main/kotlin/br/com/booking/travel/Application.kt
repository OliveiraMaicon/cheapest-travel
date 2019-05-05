package br.com.booking.travel

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.*
import kotlin.collections.HashMap

@SpringBootApplication
class Application{

    companion object{
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<Application>(*args)
        }
    }
}
