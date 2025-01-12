package com.phoenix.hermes.arbitrage.graph.edges.interfaces;

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
}
