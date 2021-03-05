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
        compleGraphEdgesValues()

        linkNeighboursEdges()
    }

    private fun linkNeighboursEdges() {
        edges.forEach {
            graph[it.start]!!.neighbours[graph[it.end]!!] = it.value
            if (!directed) graph[it.end]!!.neighbours[graph[it.start]!!] = it.value
        }
    }

    private fun compleGraphEdgesValues() {
        edges.forEach {
            if (!graph.containsKey(it.start)) graph[it.start] = Vertex(it.start)
            if (!graph.containsKey(it.end)) graph[it.end] = Vertex(it.end)
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
            val u = q.pollFirst()
            if (u.dist == Int.MAX_VALUE) break

            u.neighbours.forEach {
                val v = it.key

                val alternateDist = u.dist + it.value
                if (alternateDist < v.dist) {
                    q.remove(v)
                    v.dist = alternateDist
                    v.previous = u
                    q.add(v)
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
