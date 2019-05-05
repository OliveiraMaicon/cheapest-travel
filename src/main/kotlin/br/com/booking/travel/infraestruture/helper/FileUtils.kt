package br.com.booking.travel.infraestruture.helper

import br.com.booking.travel.domain.model.Route
import java.io.IOException
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.BufferedReader


class FileUtils {

    fun readCSV(file: String): List<Route> {
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
}