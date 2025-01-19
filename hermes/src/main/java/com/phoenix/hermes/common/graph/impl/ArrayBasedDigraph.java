package com.phoenix.hermes.common.graph.impl;

import com.phoenix.hermes.common.graph.edges.interfaces.DirectedEdge;
import com.phoenix.hermes.common.graph.impl.interfaces.WeightedDigraph;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
public class ArrayBasedDigraph implements WeightedDigraph {
    private final int numberOfVertices;
    private int numberOfEdges;
    private final List<DirectedEdge>[] adjacentEdges;

    public ArrayBasedDigraph(int numberOfVertices) {
        validateNumberOfVertices(numberOfVertices);

        this.numberOfVertices = numberOfVertices;
        adjacentEdges = new ArrayList[numberOfVertices];
        for (int v = 0; v < numberOfVertices; v++)
            adjacentEdges[v] = new ArrayList<>();
    }

    @Override
    public int getNumberOfVertices() {
        return numberOfVertices;
    }

    @Override
    public int getNumberOfEdges() {
        return numberOfEdges;
    }

    @Override
    public Iterable<DirectedEdge> getAdjacentEdges(int vertex) {
        validateVertex(vertex);
        return adjacentEdges[vertex];
    }

    @Override
    public double getWeight(int from, int to) {
        validateVertex(from);
        validateVertex(to);

        for (DirectedEdge e : adjacentEdges[from])
            if (e.getVertexTo() == to)
                return e.getWeight();
        return -1;
    }

    @Override
    public DirectedEdge getEdge(int from, int to) {
        validateVertex(from);
        validateVertex(to);

        for (DirectedEdge edge : adjacentEdges[from])
            if (edge.getVertexTo() == to)
                return edge;
        return null;
    }

    @Override
    public void add(DirectedEdge edge) {
        validateEdge(edge);
        adjacentEdges[edge.getVertexFrom()].add(edge);
        numberOfEdges++;
    }

    @Override
    public void addAll(Iterable<DirectedEdge> edges) {
        validateEdgesList(edges);
        for (DirectedEdge edge : edges)
            add(edge);
    }

    @Override
    public Iterable<DirectedEdge> getAllEdges() {
        List<DirectedEdge> bag = new ArrayList<>();
        for (int v = 0; v < numberOfVertices; v++)
            for (DirectedEdge edge : getAdjacentEdges(v))
                bag.add(edge.reversed());
        return bag;
    }

    @Override
    public WeightedDigraph reversed() {
        ArrayBasedDigraph reversed = new ArrayBasedDigraph(numberOfVertices);
        for (int from = 0; from < numberOfVertices; from++)
            for (DirectedEdge edge : adjacentEdges[from])
                reversed.add(edge.reversed());
        return reversed;
    }

    private void validateVertex(int vertex) {
        if (vertex < 0 || vertex >= numberOfVertices) {
            String message = "Vertex id should be within [0, %d] inclusive".formatted(numberOfVertices - 1);
            throw new IllegalArgumentException(message);
        }
    }

    private void validateNumberOfVertices(int numberOfVertices) {
        if (numberOfVertices <= 0) {
            String message = "Number of vertices should be positive integer value";
            throw new IllegalArgumentException(message);
        }
    }

    private void validateEdge(DirectedEdge edge) {
        if (edge == null) {
            String message = "Edge shouldn't be null";
            throw new IllegalArgumentException(message);
        }

        validateVertex(edge.getVertexFrom());
        validateVertex(edge.getVertexTo());
    }

    private void validateEdgesList(Iterable<DirectedEdge> edges) {
        if (edges == null) {
            String message = "Edges list shouldn't be null";
            throw new IllegalArgumentException(message);
        }
        for (DirectedEdge edge : edges)
            validateEdge(edge);
    }
}
