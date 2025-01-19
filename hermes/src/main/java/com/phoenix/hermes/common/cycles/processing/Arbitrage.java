package com.phoenix.hermes.common.cycles.processing;

import com.phoenix.hermes.common.graph.edges.interfaces.DirectedEdge;

import java.util.List;

public interface Arbitrage {
    double getDistanceTo(int destinationVertex);
    double convert(double startCurrencyAmount, int destinationVertex);
    boolean hasPathTo(int destinationVertex);
    List<DirectedEdge> getPathTo(int destinationVertex);
    int getStartVertex();
}

