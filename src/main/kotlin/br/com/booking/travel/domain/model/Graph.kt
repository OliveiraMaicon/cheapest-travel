package br.com.booking.travel.domain.model

import br.com.booking.travel.infraestructure.exceptions.ProcessException
import java.util.*
import kotlin.collections.HashMap

data class Graph(
        private val edges: List<Route>,
        private val directed: Boolean
) {
    private val graph = HashMap<String, Vertex>(edges.size)

    init {
        completeGraphEdgesValues()

        linkNeighboursEdges()
    }

    private fun linkNeighboursEdges() {
        println("Link edges")
        edges.forEach {
            println(it)
            graph[it.start]!!.neighbours[graph[it.end]!!] = it.value
            println("Atualizado > $graph")
            if (!directed) {
                graph[it.end]!!.neighbours[graph[it.start]!!] = it.value
                println(graph)
            }

        }
    }

    private fun completeGraphEdgesValues() {
        println("Complete Graph")
        edges.forEach {
            println(it)
            if (!graph.containsKey(it.start))
                graph[it.start] = Vertex(it.start)
            if (!graph.containsKey(it.end))
                graph[it.end] = Vertex(it.end)
            println("Atualizado > $graph")
        }
    }

    @Throws(Exception::class)
    fun dijkstra(startName: String) {
        if (!graph.containsKey(startName)) {
            throw ProcessException("Graph doesn't contain start vertex '$startName'")
        }
        val source = graph[startName]
        val q = TreeSet<Vertex>()

        graph.values.forEach {
            it.previous = if (it == source) source else null
            it.dist = if (it == source) 0 else Int.MAX_VALUE
            q.add(it)
        }

        dijkstra(q)
    }

    private fun dijkstra(q: TreeSet<Vertex>) {
        while (!q.isEmpty()) {
            val actualVextex = q.pollFirst()
            if (actualVextex.dist == Int.MAX_VALUE)
                break

            actualVextex.neighbours.forEach {
                val neighboursVertex = it.key

                val alternateDist = actualVextex.dist + it.value
                if (alternateDist < neighboursVertex.dist) {
                    q.remove(neighboursVertex)
                    neighboursVertex.dist = alternateDist
                    neighboursVertex.previous = actualVextex
                    q.add(neighboursVertex)
                }
            }
        }
    }

    @Throws(Exception::class)
    fun tracePath(endName: String): String {
        if (!graph.containsKey(endName)) {
            throw ProcessException("Graph doesn't contain end vertex '$endName'")
        }
        print(if (directed) "Directed   : " else "Undirected : ")

        return graph[endName]!!.getPath()
    }

}
