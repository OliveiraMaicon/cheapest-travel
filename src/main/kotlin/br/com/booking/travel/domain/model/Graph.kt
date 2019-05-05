package br.com.booking.travel.domain.model

import java.util.*
import kotlin.collections.HashMap

class Graph(
        private val edges: List<Route>,
        private val directed: Boolean,
        private val showAllPaths: Boolean = false
) {
    // mapping of vertex names to Vertex objects, built from a set of Edges
    private val graph = HashMap<String, Vertex>(edges.size)

    init {
        // one pass to find all vertices
        for (e in edges) {
            if (!graph.containsKey(e.start)) graph[e.start] = Vertex(e.start)
            if (!graph.containsKey(e.end)) graph[e.end] = Vertex(e.end)
        }

        // another pass to set neighbouring vertices
        for (e in edges) {
            graph[e.start]!!.neighbours[graph[e.end]!!] = e.value
            // also do this for an undirected graph if applicable
            if (!directed) graph[e.end]!!.neighbours[graph[e.start]!!] = e.value
        }
    }

    /** Runs dijkstra using a specified source vertex */
    fun dijkstra(startName: String) {
        if (!graph.containsKey(startName)) {
            println("Graph doesn't contain start vertex '$startName'")
            return
        }
        val source = graph[startName]
        val q = TreeSet<Vertex>()

        // set-up vertices
        for (v in graph.values) {
            v.previous = if (v == source) source else null
            v.dist = if (v == source)  0 else Int.MAX_VALUE
            q.add(v)
        }

        dijkstra(q)
    }

    /** Implementation of dijkstra's algorithm using a binary heap */
    private fun dijkstra(q: TreeSet<Vertex>) {
        while (!q.isEmpty()) {
            // vertex with shortest distance (first iteration will return source)
            val u = q.pollFirst()
            // if distance is infinite we can ignore 'u' (and any other remaining vertices)
            // since they are unreachable
            if (u.dist == Int.MAX_VALUE) break

            //look at distances to each neighbour
            for (a in u.neighbours) {
                val v = a.key // the neighbour in this iteration

                val alternateDist = u.dist + a.value
                if (alternateDist < v.dist) { // shorter path to neighbour found
                    q.remove(v)
                    v.dist = alternateDist
                    v.previous = u
                    q.add(v)
                }
            }
        }
    }

    /** Prints a path from the source to the specified vertex */
    fun printPath(endName: String) {
        if (!graph.containsKey(endName)) {
            println("Graph doesn't contain end vertex '$endName'")
            return
        }
        print(if (directed) "Directed   : " else "Undirected : ")
        graph[endName]!!.printPath()
        println()
        if (showAllPaths) printAllPaths() else println()
    }

    /** Prints the path from the source to every vertex (output order is not guaranteed) */
    private fun printAllPaths() {
        for (v in graph.values) {
            v.printPath()
            println()
        }
        println()
    }
}
