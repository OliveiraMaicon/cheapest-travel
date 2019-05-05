package br.com.booking.travel.infraestruture.helper

import br.com.booking.travel.domain.model.Route
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.*


@Component
class FileUtils(var logger: Logger) {

    companion object {
        private const val NAME_CSV_FILE = "routes.csv"

    }

    fun readCSV(file: String): MutableList<Route> {
        var buffer: BufferedReader? = null
        var line = ""
        var routes: MutableList<Route> = arrayListOf()

        try {
            buffer = BufferedReader(FileReader(file))
            line = buffer.readLine()
            while (line != null) {
                val tokens = line.split(",")
                if (tokens.isNotEmpty()) {
                    routes.add(Route(tokens[0], tokens[1], Integer.parseInt(tokens[2])))
                }

                line = buffer.readLine()
            }

        } catch (e: FileNotFoundException) {
            logger.error("Invalid file, try again.", e)
        } catch (e: IOException) {
            logger.error(e)
        } finally {
            if (buffer != null) {
                try {
                    buffer.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return routes
        }
    }

    fun writeInCSV(newRoutes: List<Route>) {

        var fileWriter: FileWriter? = null

        try {
            val oldRoutes = readCSV(NAME_CSV_FILE)

            oldRoutes.addAll(newRoutes)

            fileWriter = FileWriter(NAME_CSV_FILE)

            for (route in oldRoutes) {
                fileWriter.append(route.start)
                fileWriter.append(',')
                fileWriter.append(route.end)
                fileWriter.append(',')
                fileWriter.append(route.value.toString())
                fileWriter.append('\n')
            }

            logger.info("Write CSV successfully!")
        } catch (e: Exception) {
            logger.error("Writing CSV error!", e)
        } finally {
            try {
                fileWriter!!.flush()
                fileWriter.close()
            } catch (e: IOException) {
                logger.error("Flushing/closing error!", e)
            }
        }


    }


}