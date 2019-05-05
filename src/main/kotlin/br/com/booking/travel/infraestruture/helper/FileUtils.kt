package br.com.booking.travel.infraestruture.helper

import br.com.booking.travel.domain.model.Route
import java.io.*


class FileUtils {

    companion object{
        const val NAME_CSV_FILE = "routes.csv"
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
            println("Invalid file, try again.")
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
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

    fun writeInCSV(newRoutes: List<Route>){

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

            println("Write CSV successfully!")
        } catch (e: Exception) {
            println("Writing CSV error!")
            e.printStackTrace()
        } finally {
            try {
                fileWriter!!.flush()
                fileWriter.close()
            } catch (e: IOException) {
                println("Flushing/closing error!")
                e.printStackTrace()
            }
        }


    }
}