package com.phoenix.hermes.arbitrage.cycles.impl.interfaces;

import com.phoenix.hermes.arbitrage.graph.edges.interfaces.DirectedEdge;

import java.util.Comparator;
import java.util.List;

public interface Cycle extends Comparable<Cycle> {

    int getSize();
    double getProfitAsPercent();
    List<DirectedEdge> getCycleEdges();
    List<DirectedEdge> getCycleEdges(int firsElementPosition);
    int getPositionOf(int vertexId);
    String toString();
    boolean equals(Object o);
    int hashCode();

    @Override
    default int compareTo(Cycle cycle) {
        return Comparator.comparing(Cycle::getProfitAsPercent)
                .compare(this, cycle);
    }
}
