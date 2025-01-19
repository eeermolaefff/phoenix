package com.phoenix.hermes.common.graph.impl.interfaces;

import com.phoenix.hermes.common.graph.edges.interfaces.DirectedEdge;

public interface WeightedDigraph {
    int getNumberOfVertices();
    int getNumberOfEdges();
    Iterable<DirectedEdge> getAdjacentEdges(int vertex);
    double getWeight(int from, int to);
    DirectedEdge getEdge(int from, int to);
    void add(DirectedEdge edge);
    void addAll(Iterable<DirectedEdge> edges);
    Iterable<DirectedEdge> getAllEdges();
    WeightedDigraph reversed();
}
