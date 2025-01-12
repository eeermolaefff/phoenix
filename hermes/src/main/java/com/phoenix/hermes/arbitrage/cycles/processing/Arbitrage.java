package com.phoenix.hermes.arbitrage.cycles.processing;

import com.phoenix.hermes.arbitrage.graph.edges.interfaces.DirectedEdge;
import com.phoenix.hermes.arbitrage.graph.impl.ArrayBasedDigraph;
import com.phoenix.hermes.arbitrage.graph.impl.interfaces.WeightedDigraph;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Arbitrage {

    private final DirectedEdge[] edgeTo;
    private final double[] distTo;
    private final boolean[] isInTheQueue;
    private final Queue<Integer> workingQueue;
    @Getter
    private final double additionalCommission;
    @Getter
    private final int startVertex;
    @Getter
    private final int numberOfVertices;

    @Getter
    private List<DirectedEdge> cycle;
    private int numberOfIterations = 0;

    public Arbitrage(WeightedDigraph digraph, int startVertex, double additionalCommission) {
        this.additionalCommission = additionalCommission;
        this.startVertex = startVertex;
        this.numberOfVertices = digraph.getNumberOfVertices();

        edgeTo = new DirectedEdge[numberOfVertices];
        distTo = new double[numberOfVertices];
        isInTheQueue = new boolean[numberOfVertices];
        workingQueue = new LinkedList<>();

        distTo[startVertex] = 1;
        workingQueue.add(startVertex);
        isInTheQueue[startVertex] = true;

        while (!workingQueue.isEmpty() && !hasCycle()) {
            int vertex = workingQueue.poll();
            isInTheQueue[vertex] = false;
            relax(digraph, vertex);
        }
    }

    public double getDistanceTo(int destinationVertex) {
        return distTo[destinationVertex];
    }

    public double convert(double startCurrencyAmount, int destinationVertex) {
        List<DirectedEdge> path = getPathTo(destinationVertex);
        if (path.isEmpty())
            return 0;
        double destAmount = startCurrencyAmount;
        for (DirectedEdge edge : path)
            destAmount = edge.goThrough(destAmount);
        return destAmount;
    }

    public boolean hasPathTo(int dest) {
        return distTo[dest] != 0;
    }

    public List<DirectedEdge> getPathTo(int dest) {
        if (!hasPathTo(dest))
            return List.of();

        LinkedList<DirectedEdge> path = new LinkedList<>();
        for (DirectedEdge e = edgeTo[dest]; e != null; e = edgeTo[e.getVertexFrom()])
            path.addFirst(e);
        return path;
    }

    public boolean hasCycle() {
        return cycle != null;
    }

    private void relax(WeightedDigraph digraph, int from) {
        double initialAmount = distTo[from];

        for (DirectedEdge edge : digraph.getAdjacentEdges(from)) {
            if (hasCycle())
                return;

            double newDist = edge.goThrough(initialAmount) * additionalCommission;
            if (distTo[edge.getVertexTo()] < newDist)
                update(edge, newDist);

            if (numberOfIterations++ % digraph.getNumberOfVertices() == 0)
                findCycle(from);
        }
    }

    private void update(DirectedEdge edge, double newDist) {
        int to = edge.getVertexTo();

        distTo[to] = newDist;
        edgeTo[to] = edge;
        if (!isInTheQueue[to]) {
            workingQueue.add(to);
            isInTheQueue[to] = true;
        }
    }

    private void findCycle(int vertexFrom) {
        WeightedDigraph pathGraph = buildPathGraph();
        CycleFinder cycleFinder = new CycleFinder(pathGraph, vertexFrom);
        cycle = cycleFinder.getCycle();
    }

    private WeightedDigraph buildPathGraph() {
        WeightedDigraph pathGraph = new ArrayBasedDigraph(numberOfVertices);
        for (int vertex = 0; vertex < numberOfVertices; vertex++)
            if (edgeTo[vertex] != null)
                pathGraph.add(edgeTo[vertex]);
        return pathGraph;
    }
}
