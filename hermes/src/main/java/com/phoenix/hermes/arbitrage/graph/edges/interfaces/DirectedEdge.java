package com.phoenix.hermes.arbitrage.graph.edges.interfaces;

import jakarta.validation.constraints.NotNull;

import java.util.Comparator;

public interface DirectedEdge extends Comparable<DirectedEdge>{
    double getWeight();
    int getVertexTo();
    int getVertexFrom();
    DirectedEdge reversed();
    DirectedEdge copy(int vertexFrom, int vertexTo);
    double goThrough(double startCurrencyAmount);
    String toString();
    String toString(double startCurrencyAmount);
    boolean equals(Object o);
    int hashCode();

    @Override
    default int compareTo(@NotNull DirectedEdge edge) {
        return Comparator.comparing(DirectedEdge::getVertexFrom)
                .thenComparing(DirectedEdge::getVertexTo)
                .thenComparing(DirectedEdge::getWeight)
                .compare(this, edge);
    }
}
