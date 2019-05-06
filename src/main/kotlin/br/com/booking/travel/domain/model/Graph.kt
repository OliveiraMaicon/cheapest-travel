package br.com.booking.travel.domain.model

import br.com.booking.travel.infraestructure.exceptions.ProcessException
import java.util.*
import kotlin.collections.HashMap

class Graph(
        edges: List<Route>,
        private val directed: Boolean
) {
    private val graph = HashMap<String, Vertex>(edges.size)

    init {
        for (e in edges) {
            if (!graph.containsKey(e.start)) graph[e.start] = Vertex(e.start)
            if (!graph.containsKey(e.end)) graph[e.end] = Vertex(e.end)
        }

        for (e in edges) {
            graph[e.start]!!.neighbours[graph[e.end]!!] = e.value
            if (!directed) graph[e.end]!!.neighbours[graph[e.start]!!] = e.value
        }
    }

    @Throws(Exception::class)
    fun dijkstra(startName: String) {
        if (!graph.containsKey(startName)) {
            throw ProcessException("Graph doesn't contain start vertex '$startName'")
        }
        val source = graph[startName]
        val q = TreeSet<Vertex>()

        for (v in graph.values) {
            v.previous = if (v == source) source else null
            v.dist = if (v == source) 0 else Int.MAX_VALUE
            q.add(v)
        }

        dijkstra(q)
    }

    private fun dijkstra(q: TreeSet<Vertex>) {
        while (!q.isEmpty()) {
            val u = q.pollFirst()
            if (u.dist == Int.MAX_VALUE) break

            for (a in u.neighbours) {
                val v = a.key

                val alternateDist = u.dist + a.value
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
