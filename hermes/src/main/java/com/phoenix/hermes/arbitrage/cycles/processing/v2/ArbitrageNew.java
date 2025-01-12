package com.phoenix.hermes.arbitrage.cycles.processing.v2;

import com.phoenix.hermes.arbitrage.cycles.impl.ProfitableCycle;
import com.phoenix.hermes.arbitrage.cycles.impl.interfaces.Cycle;
import com.phoenix.hermes.arbitrage.graph.edges.interfaces.DirectedEdge;
import com.phoenix.hermes.arbitrage.graph.impl.interfaces.WeightedDigraph;
import lombok.Getter;

import java.util.*;

public class ArbitrageNew {
    @Getter
    private final Set<Cycle> foundCycles = new HashSet<>();
    private LinkedList<DirectedEdge> currentCycle = new LinkedList<>();
    private final Queue<Integer> workingQueue = new LinkedList<>();

    private final List<DirectedEdge>[] cycleEdgesFrom;
    private final DirectedEdge[] edgeTo;
    private final double[] distTo;
    private final boolean[] isInTheQueue;
    private final boolean[] hasCycle;
    private final boolean[] wasUpdated;
    @Getter
    private final int startVertex;
    @Getter
    private final int numberOfVertices;

    public ArbitrageNew(WeightedDigraph digraph, int startVertex) {
        this.startVertex = startVertex;
        this.numberOfVertices = digraph.getNumberOfVertices();

        cycleEdgesFrom = new ArrayList[numberOfVertices];
        edgeTo = new DirectedEdge[numberOfVertices];
        distTo = new double[numberOfVertices];
        isInTheQueue = new boolean[numberOfVertices];
        hasCycle = new boolean[numberOfVertices];
        wasUpdated = new boolean[numberOfVertices];

        distTo[startVertex] = 1;
        workingQueue.add(startVertex);
        isInTheQueue[startVertex] = true;

        while (!workingQueue.isEmpty()) {
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

    public boolean hasPathTo(int destinationVertex) {
        return distTo[destinationVertex] != 0;
    }

    public List<DirectedEdge> getPathTo(int destinationVertex) {
        if (!hasPathTo(destinationVertex))
            return List.of();

        LinkedList<DirectedEdge> path = new LinkedList<>();
        for (DirectedEdge edge = edgeTo[destinationVertex]; edge != null; edge = edgeTo[edge.getVertexFrom()])
            path.addFirst(edge);
        return path;
    }

    private void relax(WeightedDigraph digraph, int vertexFrom) {
        double initialAmount = distTo[vertexFrom];

        for (DirectedEdge edge : digraph.getAdjacentEdges(vertexFrom)) {
            int vertexTo = edge.getVertexTo();
            double newDist = edge.goThrough(initialAmount);
            if (distTo[vertexTo] < newDist) {
                if (distTo[vertexTo] == 0 || !isMakingNewCycle(vertexFrom, vertexTo, edge)) {
                    updateDistance(vertexTo, edge, newDist);
                } else {
                    markCycle();
                }
            }
        }
    }

    private void markCycle() {
        Cycle cycle = new ProfitableCycle(currentCycle);
        if (foundCycles.contains(cycle)) {
            currentCycle = new LinkedList<>();
            return;
        }
        foundCycles.add(cycle);

        int vertexFrom = -1;
        for (DirectedEdge edge : currentCycle) {
            vertexFrom = edge.getVertexFrom();
            hasCycle[vertexFrom] = true;
            if (cycleEdgesFrom[vertexFrom] == null)
                cycleEdgesFrom[vertexFrom] = new ArrayList<>();
            cycleEdgesFrom[vertexFrom].add(edge);
        }
        cycleEdgesFrom[vertexFrom].remove(cycleEdgesFrom[vertexFrom].size() - 1);

        currentCycle = new LinkedList<>();
    }

    private void updateDistance(int vertexTo, DirectedEdge edge, double newDist) {
        if (hasCycle[vertexTo]) {
            updateCyclePath(vertexTo, newDist);
            return;
        }

        distTo[vertexTo] = newDist;
        edgeTo[vertexTo] = edge;
        if (!isInTheQueue[vertexTo]) {
            workingQueue.add(vertexTo);
            isInTheQueue[vertexTo] = true;
        }
    }

    private void updateCyclePath(int vertex, double newDist) {
        if (wasUpdated[vertex])
            return;

        distTo[vertex] = newDist;
        wasUpdated[vertex] = true;
        if (!isInTheQueue[vertex]) {
            workingQueue.add(vertex);
            isInTheQueue[vertex] = true;
        }

        List<DirectedEdge> edges = cycleEdgesFrom[vertex];
        if (edges == null) {
            wasUpdated[vertex] = false;
            return;
        }

        for (DirectedEdge edge : edges) {
            int nextVertex = edge.getVertexTo();
            double nextDistance = edge.goThrough(newDist);
            updateCyclePath(nextVertex, nextDistance);
        }
        wasUpdated[vertex] = false;
    }

    private boolean isMakingNewCycle(int vertexFrom, int vertexTo, DirectedEdge keyEdge) {
        if (vertexFrom == vertexTo)
            throw new IllegalArgumentException("Cycle could not be a loop");

        for (DirectedEdge edge = edgeTo[vertexFrom]; edge != null; edge = edgeTo[vertexFrom]) {
            vertexFrom = edge.getVertexFrom();
            currentCycle.addFirst(edge);
            if (vertexFrom == vertexTo) {
                currentCycle.addLast(keyEdge);
                return true;
            }
        }

        currentCycle = new LinkedList<>();
        return false;
    }
}
