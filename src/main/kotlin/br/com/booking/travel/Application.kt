package br.com.booking.travel

import br.com.booking.travel.domain.model.Route
import br.com.booking.travel.infraestruture.helper.FileUtils
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Application{

    companion object{
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<Application>(*args)
           /* val values = FileUtils().readCSV("teste.csv")

            values?.forEach {
                println(it)
            }*/

            val routes =  listOf(Route("ABC","CDB",10))

            FileUtils().writeInCSV(routes)
        }
    }
}
